package edu.ubbcluj.backend.repository;

import edu.ubbcluj.backend.repository.hibernate.HibernateDAOFactory;

public abstract class DAOFactory {
	
	public static DAOFactory getInstance() {
		return new HibernateDAOFactory();
	}
	
	public abstract PlacesDAO getPlacesDAO();
	public abstract TypeDAO getTypeDAO();
	public abstract UsersDAO getUsersDAO();
	public abstract ServicesDAO getServicesDAO();
	public abstract MessagesDAO getMessagesDAO();
	public abstract ServicetypeDAO getServicetypeDAO();
	public abstract OpenhoursDAO getOpenhoursDAO();
	public abstract RatingDAO getRatingDAO();

}
