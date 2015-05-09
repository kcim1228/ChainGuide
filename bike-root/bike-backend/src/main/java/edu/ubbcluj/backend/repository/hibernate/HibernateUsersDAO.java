package edu.ubbcluj.backend.repository.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import edu.ubbcluj.backend.model.Users;
import edu.ubbcluj.backend.repository.UsersDAO;

@SuppressWarnings("rawtypes")
public class HibernateUsersDAO extends HibernateDAO implements UsersDAO{

	@Override
	public Users insertUser(Users user) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.persist(user);
			session.getTransaction().commit();
			
			return user;
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("User insertation failed! ",ex);	
		}		
	}

	@Override
	public void deleteUser(Users user) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(user);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("User delete failed!",ex);	
		}		
		
	}

	@Override
	public void updateUser(Users user) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(user);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("User update failed!",ex);	
		}		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getAllUsers() {
		List<Users> userList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Users.class);
			userList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Users selection failed!",ex);			
		}
	
		
		return userList;
	}

	@Override
	public Users getUserByName(String name) {
		Users user = null;
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			final Criteria c = session.createCriteria(Users.class)
					.add(Restrictions.eq("username", name));
			
			if (!this.getQueryResult(c).isEmpty()) {
				user = (Users) this.getQueryResult(c).get(0);
			} else {
				throw new RuntimeException("Wrong username!");				
			}
			
			
			session.getTransaction().commit();
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("User selection failed!",ex);			
		}
		
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getUsersByName(String name) {
		List<Users> userList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Users.class).add(Restrictions.ilike("username",name,MatchMode.START));
			userList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Users selection by name failed!",ex);			
		}
	
		
		return userList;
	}
	
	@Override
	public List<Users> getUsersByType(String type) {
		List<Users> userList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Users.class).add(Restrictions.eq("usertype",type));
			userList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Users selection by type failed!",ex);			
		}
	
		
		return userList;
	}

}
