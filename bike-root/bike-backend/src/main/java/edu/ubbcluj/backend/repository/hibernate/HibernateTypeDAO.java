package edu.ubbcluj.backend.repository.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import edu.ubbcluj.backend.model.Type;
import edu.ubbcluj.backend.repository.TypeDAO;

@SuppressWarnings("rawtypes")
public class HibernateTypeDAO extends HibernateDAO implements TypeDAO{

	@Override
	public Type insertType(Type type) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.persist(type);
			session.getTransaction().commit();
			
			return type;
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Type insertation failed!",ex);	
		}		
	}

	@Override
	public void updateType(Type type) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(type);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Type update failed!",ex);	
		}		
		
	}

	@Override
	public void deleteType(Type type) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(type);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Type delete failed!",ex);	
		}		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Type> getAllType() {
		List<Type> typeList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Type.class);
			typeList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Types selection failed!",ex);			
		}
	
		
		return typeList;
	}

	@Override
	public Type getTypeById(int id) {
		Type type = new Type();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Type.class).add(Restrictions.eq("typeId",id));
			type = (Type) this.getQueryResult(c).get(0);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Type by ID selection failed!",ex);			
		}
	
		
		return type;
	}

	@Override
	public Type getTypeByName(String name) {
		Type type = new Type();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Type.class).add(Restrictions.eq("name",name));
			type = (Type) this.getQueryResult(c).get(0);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Type by Name selection failed!",ex);			
		}
	
		
		return type;
	}

}
