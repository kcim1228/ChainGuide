package edu.ubbcluj.backend.repository;

import java.util.List;

import edu.ubbcluj.backend.model.Type;

public interface TypeDAO {
	
	Type insertType(Type type);
	void updateType(Type type);
	void deleteType(Type type);
	List<Type> getAllType();
	Type getTypeById(int id);
	Type getTypeByName(String name);

}
