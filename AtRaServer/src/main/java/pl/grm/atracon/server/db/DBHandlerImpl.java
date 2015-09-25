/**
 * 
 */
package pl.grm.atracon.server.db;

import java.util.*;

import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.github.fluent.hibernate.*;

import pl.grm.atracon.lib.conf.ConfigDB;
import pl.grm.atracon.lib.devices.*;
import pl.grm.atracon.lib.rmi.DBHandler;
import pl.grm.atracon.server.conf.ConfigParams;
import pl.grm.atracon.server.devices.*;

/**
 * @author Levvy055
 *
 */
public class DBHandlerImpl implements DBHandler {

	private ConfigDB configDB;
	private SessionFactory factory;

	public DBHandlerImpl(ConfigDB configDB) {
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
			metadata.addAnnotatedClass(RaspPiImpl.class);
			metadata.addAnnotatedClass(AtmegaImpl.class);
			metadata.addAnnotatedClass(RegisterImpl.class);

			factory = metadata.buildMetadata().buildSessionFactory();

			try {
				HibernateSessionFactory.Builder.configureFromExistingSessionFactory(factory);
			}
			catch (Throwable th) {
				th.printStackTrace();
			}
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
			finally {
				HibernateSessionFactory.closeSessionFactory();
			}
		}
	}

	public static void closeSession(Session session) {
		if (session != null && (session.isConnected() || session.isOpen())) {
			session.close();
		}
	}

	@Override
	public List<RaspPi> getRaspPiDevices() {
		return H.<RaspPi> request(RaspPiImpl.class).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.lib.rmi.DBHandler#getRaspPiDevice(int)
	 */
	@Override
	public RaspPi getRaspPiDevice(int id) {
		return H.<RaspPiImpl> getById(RaspPiImpl.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.lib.rmi.DBHandler#getAtmegaDevices()
	 */
	@Override
	public List<Atmega> getAtmegaDevices() {
		return H.<Atmega> request(AtmegaImpl.class).list();
	}

	@Override
	public List<Atmega> getAtmegaDevices(int raspPiId) {
		return H.<Atmega> request(AtmegaImpl.class).eq("raspPi.id", raspPiId).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.lib.rmi.DBHandler#getAtmegaDevice(int, int)
	 */
	@Override
	public Atmega getAtmegaDevice(int raspPiId, int atmegaPort) {
		return H.<Atmega> request(AtmegaImpl.class).eq("raspPi.id", raspPiId).eq("port", atmegaPort).first();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.lib.rmi.DBHandler#getAtmegaDevice(int)
	 */
	@Override
	public Atmega getAtmegaDevice(int atmegaId) {
		return H.<AtmegaImpl> getById(AtmegaImpl.class, atmegaId);
	}

	@Override
	public List<Register> getAllRegistry() {
		return H.<Register> request(RegisterImpl.class).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.lib.rmi.DBHandler#getRegister(int)
	 */
	@Override
	public Register getRegister(int regId) {
		return H.<RegisterImpl> getById(RegisterImpl.class, regId);
	}
}
