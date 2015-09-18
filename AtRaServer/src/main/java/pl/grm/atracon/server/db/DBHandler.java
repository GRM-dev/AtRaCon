/**
 * 
 */
package pl.grm.atracon.server.db;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.service.ServiceRegistry;

import pl.grm.atracon.lib.ARCLogger;
import pl.grm.atracon.lib.conf.ConfigDB;
import pl.grm.atracon.server.conf.ConfigParams;
import pl.grm.atracon.server.devices.RaspPi;

/**
 * @author Levvy055
 *
 */
public class DBHandler {

	private ConfigDB configDB;
	private SessionFactory factory;

	public DBHandler(ConfigDB configDB) {
		this.configDB = configDB;
	}

	public void initConnection() throws HibernateException {
		if (factory == null || factory.isClosed()) {
			String url = configDB.get(ConfigParams.DB_HOST.toString()).getValue();
			String user = configDB.get(ConfigParams.DB_USERNAME.toString()).getValue();
			String passwd = configDB.get(ConfigParams.DB_PASSWORD.toString()).getValue();

			Configuration configuration = new Configuration().configure();
			configuration.setProperty("hibernate.connection.url", url);
			configuration.setProperty("hibernate.connection.username", user);
			configuration.setProperty("hibernate.connection.password", passwd);
			Properties properties = configuration.getProperties();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();
			Metadata metadata = new MetadataSources(serviceRegistry).buildMetadata();
			Collection<PersistentClass> list = metadata.getEntityBindings();
			System.out.println("Entities amount: " + list.size());
			for (PersistentClass c : list) {
				System.out.println(c.getClassName());
			}
			Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = it.next();
				System.out.println("P: " + ((String) entry.getKey()) + " -- " + ((String) entry.getValue()));
			}
			factory = metadata.buildSessionFactory();
		}
	}

	public void closeConnection() {
		try {
			if (factory != null && !factory.isClosed()) {
				Session session;
				if ((session = factory.getCurrentSession()) != null && session.isConnected()) {
					session.disconnect();
				}
				factory.close();
			}
		}
		catch (Exception e) {}
	}

	public static void closeSession(Session session) {
		if (session != null && session.isConnected()) {
			session.close();
		}
	}

	public List<RaspPi> getRaspPiDevices() {
		ArrayList<RaspPi> devs = new ArrayList<>();
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List devsL = session.createQuery("FROM RaspPi").list();
			for (Iterator it = devsL.iterator(); it.hasNext();) {
				RaspPi dev = (RaspPi) it.next();
				devs.add(dev);
			}
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			ARCLogger.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		finally {
			closeSession(session);
		}
		return devs;
	}
}
