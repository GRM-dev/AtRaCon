/**
 * 
 */
package pl.grm.atracon.server.db;

import java.util.*;
import java.util.logging.Level;

import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
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
			String db = configDB.get(ConfigParams.DB_NAME.toString()).getValue();
			String user = configDB.get(ConfigParams.DB_USERNAME.toString()).getValue();
			String passwd = configDB.get(ConfigParams.DB_PASSWORD.toString()).getValue();

			Configuration configuration = new Configuration().configure();
			configuration.setProperty("hibernate.connection.url", url + "/" + db);
			configuration.setProperty("hibernate.connection.username", user);
			configuration.setProperty("hibernate.connection.password", passwd);

			Properties properties = configuration.getProperties();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();

			MetadataSources metadata = new MetadataSources(serviceRegistry);
			metadata.addAnnotatedClass(RaspPi.class);
			factory = metadata.buildMetadata().buildSessionFactory();
		}
	}

	public void closeConnection() {

		if (factory != null) {
			Session session;
			try {
				if ((session = factory.getCurrentSession()) != null && session.isConnected()) {
					session.disconnect();
					session.close();
				}
			}
			catch (Exception e) {}
			factory.close();
		}

	}

	public static void closeSession(Session session) {
		if (session != null && (session.isConnected() || session.isOpen())) {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<RaspPi> getRaspPiDevices() {
		ArrayList<RaspPi> devs = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			devs = (ArrayList<RaspPi>) session.createQuery("FROM RaspPi").list();
			devs.get(1).setActivated(true);;
			session.update(devs.get(1));
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
