package edu.ubbcluj.backend.repository.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubbcluj.backend.model.Places;
import edu.ubbcluj.backend.repository.PlacesDAO;

@SuppressWarnings("rawtypes")
public class HibernatePlacesDAO extends HibernateDAO implements PlacesDAO {
	
	private static final Logger LOG = LoggerFactory.getLogger(HibernatePlacesDAO.class);
	public Places insertPlace(Places place) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.persist(place);
			session.getTransaction().commit();
			
			return place;
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("Place insertation failed! ");
			}
			
			throw new RuntimeException("Place insertation failed! ",ex);	
		}		
		
	}

	@Override
	public void deletePlace(Places place) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(place);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("Place delete failed! ");
			}
			
			throw new RuntimeException("Place delete failed!",ex);	
		}		
		
	}

	@Override
	public void updatePlace(Places place) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(place);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("Place update failed! ");
			}
			
			throw new RuntimeException("Place update failed!",ex);	
		}		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Places> getPlacesByName(String PlaceName) {
		List<Places> placesList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Places.class).add(Restrictions.ilike("name",PlaceName,MatchMode.START));
			placesList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("GetPlacesByName failed! ");
			}
			
			throw new RuntimeException("Places selection by name failed!",ex);			
		}
	
		
		return placesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Places> getAllPlaces() {
		List<Places> placesList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Places.class);
			placesList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("GetAllPlaces failed! ");
			}
			
			throw new RuntimeException("Places selection failed!",ex);			
		}
	
		
		return placesList;
	}

	@Override
	public Places getPlaceById(int id) {
		Places place = new Places();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Places.class).add(Restrictions.eq("id",id));
			place = (Places) this.getQueryResult(c).get(0);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("Place by Id selection failed! ");
			}
			
			throw new RuntimeException("Place by ID selection failed!",ex);			
		}
	
		
		return place;
	}

}
