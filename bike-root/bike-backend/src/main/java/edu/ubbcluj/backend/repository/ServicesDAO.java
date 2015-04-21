package edu.ubbcluj.backend.repository;

import java.util.List;

import edu.ubbcluj.backend.model.Services;
import edu.ubbcluj.backend.model.Type;

public interface ServicesDAO {
	Services insertService(Services service);
	void deleteService(Services service);
	void updateService(Services service);
	List<Services> getAllServices();
	List<Services> getAllServicesByName(String name);
	Services getServiceById(int id);
	List<Services> getAllServicesByType(Type t);
	List<Services> toAplhabeticOrder(List<Services> list);
	

}
