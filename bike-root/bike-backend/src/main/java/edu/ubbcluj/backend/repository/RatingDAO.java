package edu.ubbcluj.backend.repository;

import java.util.List;

import edu.ubbcluj.backend.model.Rating;
import edu.ubbcluj.backend.model.Services;

public interface RatingDAO {
	Rating insertRating(Rating r);
	void deleteRating(Rating r);
	void updateRating(Rating r);
	List<Rating> getAllRatingByService(Services serv);

}
