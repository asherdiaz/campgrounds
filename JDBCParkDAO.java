package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> results = new ArrayList<>();
		
		String sqlGetAllParks = "SELECT * FROM park";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		
		while(rows.next()) {
			Park park = new Park();
			park.setId(rows.getLong("park_id"));
			park.setName(rows.getString("name"));
			
			results.add(park);
		}
		return results;
	}

	@Override
	public List<Park> selectParkByName(String nameSearch) {
		List<Park> parks = new ArrayList<>();
		
		String sqlSelectParkById = "SELECT * FROM park WHERE upper(name) LIKE ? OR lower(name) LIKE ? OR name LIKE ?"
				+" ORDER BY name";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlSelectParkById, "%"+nameSearch+"%", "%"+nameSearch+"%","%"+nameSearch+"%");
		
		while(rows.next()){
			Park park = new Park();
			park.setName(rows.getString("name"));
			park.setLocation(rows.getString("location"));
			park.setEstablishedDate(rows.getDate("establish_date").toLocalDate());
			park.setArea(rows.getLong("area"));
			park.setVisitors(rows.getLong("visitors"));
			park.setDescription(rows.getString("description"));
			parks.add(park);
		}
		return parks;
	}
	
	public Park createPark(String name, String location, LocalDate establishedDate, Long area, Long visitors, String description) {
		String sqlGetNextInt = "SELECT nextval('park_park_id_seq')";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetNextInt);
		results = jdbcTemplate.queryForRowSet(sqlGetNextInt);
		results.next();
		Long id = results.getLong(1);
		
		String sqlCreatePark = "INSERT INTO park(park_id, name, location, established_date, area, visitors, description)"
								+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
		
		Park newPark = new Park();
		jdbcTemplate.update(sqlCreatePark, id, name, location, establishedDate, area, visitors, description);
		
		newPark.setId(id);
		newPark.setName("name");
		newPark.setEstablishedDate(establishedDate);
		newPark.setArea(area);
		newPark.setVisitors(visitors);
		newPark.setDescription("description");
		
		return newPark;
	}
 
}
