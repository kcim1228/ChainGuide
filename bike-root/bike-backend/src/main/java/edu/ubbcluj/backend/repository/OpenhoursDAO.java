package edu.ubbcluj.backend.repository;

import java.util.List;

import edu.ubbcluj.backend.model.Openhours;

public interface OpenhoursDAO {
	
	Openhours insertOpehour(Openhours openh);
	void deleteOpenhour(Openhours openh);
	void updateOpenhour(Openhours openh);
	List<Openhours> getAllOpenhours();
	List<Openhours> getAllOpenNow();

}
