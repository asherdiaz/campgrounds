package com.techelevator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Reservation> searchByAvailableReservation(String campground, LocalDate startDate, LocalDate endDate) {
		List<Reservation> reservation = new ArrayList<>();
		
		String sqlGetAllReservations = "SELECT * FROM reservation r JOIN site s ON s.site_id=r.site_id "
				+ "JOIN site s ON c.campground_id=s.campground_id"
				+ "  WHERE c.name LIKE ? AND from_date=? AND end_date=?";
		Date dateStartDate = Date.valueOf(startDate);
		Date dateEndDate = Date.valueOf(endDate);
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlGetAllReservations, "%"+campground+"%", dateStartDate, dateEndDate);
		
		while(rows.next()) {
			Reservation rev = new Reservation();
			rev.setReservationId(rows.getLong("reservation_id"));
			rev.setSiteId(rows.getLong("site_id"));
			rev.setName(rows.getString("name"));
			rev.setFromDate(rows.getDate("from_date").toLocalDate());
			rev.setToDate(rows.getDate("to_date").toLocalDate());
			rev.setCreateDate(rows.getDate("create_date").toLocalDate());
			reservation.add(rev);
		}
		return reservation;
	}



	@Override
	public Reservation createReservation(String reservationName, Long siteId, LocalDate reenterStartDate, LocalDate reenterEndDate) {
		Reservation newReservation = new Reservation();
		
		String sqlGetNextInt = "SELECT nextval('reservation_reservation_id_seq')";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetNextInt);
		result.next();
		Long id = result.getLong(1);
		
		String sqlCreateReservation = "INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date)"
				+ " VALUES (?, ?, ?, ?, ?, ? )";
		
		jdbcTemplate.update(sqlCreateReservation, id, siteId, reservationName, reenterStartDate, reenterEndDate, LocalDate.now());
		newReservation.setReservationId(id);
		newReservation.setSiteId(siteId);
		newReservation.setName(reservationName);
		newReservation.setFromDate(reenterStartDate);
		newReservation.setToDate(reenterEndDate);
		newReservation.setCreateDate(LocalDate.now());
		return newReservation;
	}
	
	@Override
	public List<Reservation> availableSites(String campground, LocalDate startDate, LocalDate endDate){
		List<Reservation> site = new ArrayList<>();
		
		String sqlGetAllReservations = "SELECT s.*, cg.daily_fee FROM site s JOIN campground cg ON s.campground_id=cg.campground_id "
				+ " WHERE site_id NOT IN ( "
				+ " SELECT r.site_id FROM reservation r "
				+ " WHERE (r.from_date <=? AND r.to_date >=?)"
				+ "	OR (r.from_date >=? AND r.to_date >=?)"
				+ "	OR (r.from_date <=? AND r.to_date <=? )"
				+ " OR (r.from_date >=? AND r.to_date <=?)"
				+ ")   AND ("
				+ "SELECT cg.campground_id FROM campground cg WHERE cg.name LIKE ?) = s.campground_id ORDER BY s.site_id LIMIT 5;";  
		Date dateStartDate = Date.valueOf(startDate);
		Date dateEndDate = Date.valueOf(endDate);
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlGetAllReservations, dateStartDate, dateEndDate, dateStartDate, dateEndDate, dateStartDate, dateEndDate, dateStartDate, dateEndDate,"%"+campground+"%");
		
		while(rows.next()) {
			Reservation rev = new Reservation();
			//rev.setReservationId(rows.getLong("reservation_id"));
			rev.setSiteId(rows.getLong("site_id"));
			//rev.setName(rows.getString("name"));
			//rev.setFromDate(rows.getDate("from_date").toLocalDate());
			//rev.setToDate(rows.getDate("to_date").toLocalDate());
			//rev.setCreateDate(rows.getDate("create_date").toLocalDate());
			
			rev.setCampground_id(rows.getLong("campground_id"));
			rev.setSiteNumber(rows.getLong("site_number"));
			rev.setOccupancy(rows.getLong("max_occupancy"));
			rev.setAccessible(rows.getBoolean("accessible"));
			rev.setMaxRVLength(rows.getLong("max_rv_length"));
			rev.setUtilites(rows.getBoolean("utilities"));
			rev.setDailyFee(rows.getString("daily_fee"));
			site.add(rev);
		}
		return site;
	}
}
