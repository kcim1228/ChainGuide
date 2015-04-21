package edu.ubbcluj.backend.repository.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import edu.ubbcluj.backend.model.Messages;
import edu.ubbcluj.backend.model.Users;
import edu.ubbcluj.backend.repository.MessagesDAO;

@SuppressWarnings("rawtypes")
public class HibernateMessagesDAO extends HibernateDAO implements MessagesDAO{

	@Override
	public Messages insertMessage(Messages m) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.persist(m);
			session.getTransaction().commit();
			
			return m;
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Message insertation failed! ",ex);	
		}		
	}

	@Override
	public void deleteMessage(Messages m) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(m);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Message delete failed!",ex);	
		}		
		
		
	}

	@Override
	public void updateMessage(Messages m) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(m);
			session.getTransaction().commit();
			
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Message update failed!",ex);	
		}		
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Messages> getAllMessagesByReceiver(Users u) {
		List<Messages> messageList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Messages.class).add(Restrictions.eq("usersByReceiverId",u));
			messageList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException(ex.getMessage(),ex);			
		}
	
		
		return messageList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Messages> getAllMessagesBySender(Users u) {
		List<Messages> messageList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Messages.class).add(Restrictions.eq("usersBySenderId",u));
			messageList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Messages selection by senderId failed!",ex);			
		}
	
		
		return messageList;
	}

	

}
