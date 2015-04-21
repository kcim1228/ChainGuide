package edu.ubbcluj.backend.repository.hibernate;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import edu.ubbcluj.backend.model.Openhours;
import edu.ubbcluj.backend.repository.OpenhoursDAO;

@SuppressWarnings("rawtypes")
public class HibernateOpenhoursDAO extends HibernateDAO implements OpenhoursDAO{

	@Override
	public Openhours insertOpehour(Openhours openh) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.persist(openh);
			session.getTransaction().commit();
			
			return openh;
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Openhour insertation failed! ",ex);	
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Openhours> getAllOpenhours() {
		List<Openhours> openhList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Openhours.class);
			openhList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Openhours selection failed!",ex);			
		}
	
		
		return openhList;
	}

	@Override
	public void deleteOpenhour(Openhours openh) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(openh);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Openhour delete failed!",ex);	
		}		
		
	}

	@Override
	public void updateOpenhour(Openhours openh) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(openh);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Openhour update failed!",ex);	
		}		
		
		
	}

	
	
	@Override
	public List<Openhours> getAllOpenNow() {
		Date date = new Date();
		int day = date.getDay();
		int hours = date.getHours();
		
		
		List<Openhours> openhList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Openhours.class).add(Restrictions.ge("close", hours)).add(Restrictions.le("open", hours)).add(Restrictions.eq("day", day));
			openhList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Openhours selection failed!",ex);			
		}
	
		
		return openhList;
	}

}
