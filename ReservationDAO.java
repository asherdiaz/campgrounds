package com.techelevator;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {

	public List<Reservation> searchByAvailableReservation(String campground, LocalDate startDate, LocalDate endDate);
	public Reservation createReservation(String reservationName, Long siteId, LocalDate reenterStartDate, LocalDate reenterEndDate);	
	public List<Reservation> availableSites(String campground, LocalDate startDate, LocalDate endDate);
}
