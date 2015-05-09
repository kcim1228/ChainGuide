package edu.ubbcluj.backend.repository.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import edu.ubbcluj.backend.model.Rating;
import edu.ubbcluj.backend.model.Services;
import edu.ubbcluj.backend.model.Users;
import edu.ubbcluj.backend.repository.RatingDAO;

@SuppressWarnings("rawtypes")
public class HibernateRatingDAO extends HibernateDAO implements RatingDAO {

	@Override
	public Rating insertRating(Rating r) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.persist(r);
			session.getTransaction().commit();
			
			return r;
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Rating insertation failed! ",ex);	
		}		
	}

	@Override
	public void deleteRating(Rating r) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(r);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Rating delete failed!",ex);	
		}		
		
		
		
	}

	@Override
	public void updateRating(Rating r) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(r);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Rating update failed!",ex);	
		}		
		
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rating> getAllRatingByService(Services serv) {
		List<Rating> ratingList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Rating.class).add(Restrictions.eq("services",serv));
			ratingList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException(ex.getMessage(),ex);			
		}
	
		
		return ratingList;
	}
	
	@Override
	public Rating getRatingByUserAndService(Users user, Services serv){
		Rating rating = new Rating();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Rating.class).add(Restrictions.eq("users",user)).add(Restrictions.eq("services",serv));
			rating = (Rating) this.getQueryResult(c).get(0);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Rating by service and user selection failed!",ex);			
		}
	
		
		return rating;
	}

}
