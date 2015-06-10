package edu.ubbcluj.backend.repository.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubbcluj.backend.model.Services;
import edu.ubbcluj.backend.model.Servicetype;
import edu.ubbcluj.backend.model.Type;
import edu.ubbcluj.backend.repository.ServicetypeDAO;

@SuppressWarnings("rawtypes")
public class HibernateServicetypeDAO extends HibernateDAO implements ServicetypeDAO{

	private static final Logger LOG = LoggerFactory.getLogger(HibernateServicetypeDAO.class);
	@Override
	public Servicetype insertServicetype(Servicetype servicetype) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.persist(servicetype);
			session.merge(servicetype);
			session.getTransaction().commit();
			
			return servicetype;
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("ServiceType insertation failed! ");
			}
			
			throw new RuntimeException("Service-Type insertation failed! ",ex);	
		}		
		
	}

	@Override
	public void deleteServicetype(Servicetype servicetype) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(servicetype);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("ServiceType delete failed! ");
			}
			
			throw new RuntimeException("Service-type delete failed!",ex);	
		}		
		
		
	}

	@Override
	public void updateServicetype(Servicetype servicetype) {
Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(servicetype);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("ServiceType update failed! ");
			}
			
			throw new RuntimeException("Service-type update failed!",ex);	
		}		
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Servicetype> getAllServiceTypesByType(Type type) {
		List<Servicetype> serviceList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Servicetype.class).add(Restrictions.eq("type",type));
			serviceList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("getAllServiceTypesByType failed! ");
			}
			
			throw new RuntimeException("Servicetypes by type selection failed!",ex);			
		}
	
		
		return serviceList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Servicetype> getAllServiceTypesByService(Services serv) {
		List<Servicetype> serviceList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Servicetype.class).add(Restrictions.eq("services",serv));
			serviceList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("getAllServiceTypesByService failed! ");
			}
			
			throw new RuntimeException("Servicetypes by service selection failed!",ex);			
		}
	
		
		return serviceList;
	}

}
