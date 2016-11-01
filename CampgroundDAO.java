package com.techelevator;

import java.util.List;

public interface CampgroundDAO {
	
	public List<Campground> getCampgrounds(String nameSearch);
	public List<Campground> searchCampgroundByName(String nameSearch);
	

}
