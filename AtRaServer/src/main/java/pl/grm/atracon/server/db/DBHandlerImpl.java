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

import pl.grm.atracon.lib.ARCLogger;
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

	@Override
	@SuppressWarnings("unchecked")
	public List<RaspPi> getRaspPiDevices() {
		List<RaspPi> devs = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			devs = session.createQuery("FROM RaspPiImpl").list();
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			ARCLogger.error(e);
		}
		finally {
			closeSession(session);
		}
		return devs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.lib.rmi.DBHandler#getRaspPiDevice(int)
	 */
	@Override
	public RaspPi getRaspPiDevice(int ID) {
		RaspPi dev = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dev = (RaspPi) session.createQuery("FROM RaspPiImpl pi WHERE pi.id=" + ID).uniqueResult();
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			ARCLogger.error(e);
		}
		finally {
			closeSession(session);
		}
		return dev;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.lib.rmi.DBHandler#getAtmegaDevices()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Atmega> getAtmegaDevices() {
		List<Atmega> devs = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			devs = session.createQuery("FROM AtmegaImpl").list();
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			ARCLogger.error(e);
		}
		finally {
			closeSession(session);
		}
		return devs;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Atmega> getAtmegaDevices(int raspPiId) {
		List<Atmega> devs = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			devs = session.createQuery("FROM AtmegaImpl atmega WHERE atmega.raspPi = " + raspPiId).list();
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			ARCLogger.error(e);
		}
		finally {
			closeSession(session);
		}
		return devs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.lib.rmi.DBHandler#getAtmegaDevice(int, int)
	 */
	@Override
	public Atmega getAtmegaDevice(int raspPiId, int atmegaPort) {
		Atmega dev = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String query = "FROM AtmegaImpl atm WHERE atm.raspPi = " + raspPiId + " AND atm.id = " + atmegaPort;
			dev = (Atmega) session.createQuery(query).uniqueResult();
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			ARCLogger.error(e);
		}
		finally {
			closeSession(session);
		}
		return dev;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.lib.rmi.DBHandler#getAtmegaDevice(int)
	 */
	@Override
	public Atmega getAtmegaDevice(int atmegaId) {
		Atmega dev = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String query = "FROM AtmegaImpl atmega WHERE atmega.id = " + atmegaId;
			dev = (Atmega) session.createQuery(query).uniqueResult();
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			ARCLogger.error(e);
		}
		finally {
			closeSession(session);
		}
		return dev;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Register> getAllRegistry() {
		List<Register> devs = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			devs = session.createQuery("FROM RegisterImpl").list();
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			ARCLogger.error(e);
		}
		finally {
			closeSession(session);
		}
		return devs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.grm.atracon.lib.rmi.DBHandler#getRegister(int)
	 */
	@Override
	public Register getRegister(int regId) {
		Register dev = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dev = (Register) session.createQuery("FROM RegisterImpl reg WHERE reg.id = " + regId).uniqueResult();
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			ARCLogger.error(e);
		}
		finally {
			closeSession(session);
		}
		return dev;
	}
}
