package edu.ubbcluj.backend.repository;

import java.util.List;

import edu.ubbcluj.backend.model.Users;

public interface UsersDAO {
	Users insertUser(Users user);
	void deleteUser(Users user);
	void updateUser(Users user);
	List<Users> getAllUsers();
	Users getUserByName(String name);
	List<Users> getUsersByName(String name);
	List<Users> getUsersByType(String type);

}
