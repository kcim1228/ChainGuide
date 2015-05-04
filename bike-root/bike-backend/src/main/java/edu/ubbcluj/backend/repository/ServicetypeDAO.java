package edu.ubbcluj.backend.repository;


import java.util.List;

import edu.ubbcluj.backend.model.Services;
import edu.ubbcluj.backend.model.Servicetype;
import edu.ubbcluj.backend.model.Type;

public interface ServicetypeDAO {
	Servicetype insertServicetype(Servicetype servicetype);
	void deleteServicetype(Servicetype servicetype);
	void updateServicetype(Servicetype servicetype);
	List<Servicetype> getAllServiceTypesByType(Type type);
	List<Servicetype> getAllServiceTypesByService(Services serv);

}
