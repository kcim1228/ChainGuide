package edu.ubbcluj.backend.repository.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubbcluj.backend.model.Messages;
import edu.ubbcluj.backend.model.Users;
import edu.ubbcluj.backend.repository.MessagesDAO;

@SuppressWarnings("rawtypes")
public class HibernateMessagesDAO extends HibernateDAO implements MessagesDAO{

	Logger LOG = LoggerFactory.getLogger(HibernateMessagesDAO.class);
	@Override
	public Messages insertMessage(Messages m) {
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.persist(m);
			session.getTransaction().commit();
			LOG.debug("Message succefully inserted! ");
			return m;
			
		} catch (final HibernateException ex) {
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
				LOG.error("Message insertation failed!");
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
	
	@Override
	public List<Messages> getAllUnreadMessagesBySender(Users u) {
		List<Messages> messageList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Messages.class).add(Restrictions.eq("usersBySenderId",u)).add(Restrictions.eq("flag",0));
			messageList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Unread-Messages selection by senderId failed!",ex);			
		}
	
		
		return messageList;
	}
	
	@Override
	public List<Messages> getAllReadMessagesBySender(Users u) {
		List<Messages> messageList = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionManager.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			final Criteria c = session.createCriteria(Messages.class).add(Restrictions.eq("usersBySenderId",u)).add(Restrictions.eq("flag",1));
			messageList = this.getQueryResult(c);
			
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			
			if (session != null && session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException("Read- Messages selection by senderId failed!",ex);			
		}
	
		
		return messageList;
	}

	

}
