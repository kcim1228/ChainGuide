package edu.ubbcluj.backend.repository.hibernate;

import edu.ubbcluj.backend.repository.DAOFactory;
import edu.ubbcluj.backend.repository.MessagesDAO;
import edu.ubbcluj.backend.repository.OpenhoursDAO;
import edu.ubbcluj.backend.repository.PlacesDAO;
import edu.ubbcluj.backend.repository.RatingDAO;
import edu.ubbcluj.backend.repository.ServicesDAO;
import edu.ubbcluj.backend.repository.ServicetypeDAO;
import edu.ubbcluj.backend.repository.TypeDAO;
import edu.ubbcluj.backend.repository.UsersDAO;

public class HibernateDAOFactory extends DAOFactory{

	@Override
	public PlacesDAO getPlacesDAO() {
		return new HibernatePlacesDAO();
	}
	
	public TypeDAO getTypeDAO(){
		return new HibernateTypeDAO();
	}

	public UsersDAO getUsersDAO() {
		return new HibernateUsersDAO();
	}

	@Override
	public ServicesDAO getServicesDAO() {
		return new HibernateServicesDAO();
	}

	@Override
	public MessagesDAO getMessagesDAO() {
		return new HibernateMessagesDAO();
	}

	@Override
	public ServicetypeDAO getServicetypeDAO() {
		return new HibernateServicetypeDAO();
	}

	@Override
	public OpenhoursDAO getOpenhoursDAO() {
		return new HibernateOpenhoursDAO();
	}

	@Override
	public RatingDAO getRatingDAO() {
		return new HibernateRatingDAO();
	}
	
	
	
	

}
