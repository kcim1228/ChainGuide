package edu.ubbcluj.backend.repository.hibernate;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubbcluj.backend.model.Services;
import edu.ubbcluj.backend.model.Servicetype;
import edu.ubbcluj.backend.model.Type;
import edu.ubbcluj.backend.repository.DAOFactory;
import edu.ubbcluj.backend.repository.ServicesDAO;
import edu.ubbcluj.backend.repository.ServicetypeDAO;

@SuppressWarnings("rawtypes")
public class HibernateServicesDAO extends HibernateDAO implements ServicesDAO{
	
	private static final Logger LOG = LoggerFactory.getLogger(HibernateServicesDAO.class);
	DAOFactory daoFactory = DAOFactory.getInstance();
	ServicetypeDAO servicetypeDAO = daoFactory.getServicetypeDAO();
	List<Servicetype> servicetypes = null;

	@Override
	public Services insertService(Services service) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.persist(service);
			session.getTransaction().commit();
			
			return service;
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("Service insert failed!");
			}
			
			throw new RuntimeException("Service insertation failed! ",ex);	
		}		
		
	}

	@Override
	public void deleteService(Services service) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(service);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("Service delete failed!");
			}
			
			throw new RuntimeException("Service delete failed!",ex);	
		}		
		
	}

	@Override
	public void updateService(Services service) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(service);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("Service update failed!");
			}
			
			throw new RuntimeException("Service update failed!",ex);	
		}		
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Services> getAllServices() {
		List<Services> servicesList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Services.class);
			servicesList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("GetAllServices failed!");
			}
			
			throw new RuntimeException("Services selection failed!",ex);			
		}
	
		
		return servicesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Services> getAllServicesByName(String name) {
		List<Services> servicesList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Services.class).add(Restrictions.ilike("name",name,MatchMode.START));
			servicesList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("GetAllServicesByName failed!");
			}
			
			throw new RuntimeException("Services selection by name failed!",ex);			
		}
	
		
		return servicesList;
	}

	@Override
	public Services getServiceById(int id) {
		Services service = new Services();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Services.class).add(Restrictions.eq("id",id));
			service = (Services) this.getQueryResult(c).get(0);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("GetServiceById  failed!");
			}
			
			throw new RuntimeException("Service by ID selection failed!",ex);			
		}
	
		
		return service;
	}

	
	@Override
	public List<Services> getAllServicesByType(Type t) {
		
		servicetypes = servicetypeDAO.getAllServiceTypesByType(t);
		List<Services> services = new ArrayList<Services>();
		
		int serviceSize = servicetypes.size();
		
		for(int i=0;i<serviceSize;i++){
			Servicetype st = servicetypes.get(i);
			Services s = this.getServiceById(st.getServices().getId());
			services.add(i, s);
			
		}
		LOG.debug("getAllServicesByType went successfully ");
		
		return services;
	}

	@Override
	public List<Services> toAplhabeticOrder(List<Services> list) {
	
		Collections.sort(list, HibernateServicesDAO.ServiceNameComparator);
		return list;
	}

	public static Comparator<Services> ServiceNameComparator 
		    = new Comparator<Services>() {
		

		@Override
		public int compare(Services o1, Services o2) {
			String name1 = o1.getName();
			String name2 = o2.getName();
			
			//ascending order
			return name1.compareTo(name2);
		}
	
	};
	
	

}
