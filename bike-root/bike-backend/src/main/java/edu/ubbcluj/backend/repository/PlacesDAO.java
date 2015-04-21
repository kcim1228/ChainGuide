package edu.ubbcluj.backend.repository;

import java.util.List;

import edu.ubbcluj.backend.model.Places;

public interface PlacesDAO {
	Places insertPlace(Places place);
	void deletePlace(Places place);
	void updatePlace(Places place);
	List<Places> getPlacesByName(String PlaceName);
	List<Places> getAllPlaces();
}
