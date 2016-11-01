package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Campground> getCampgrounds(String campSearch) {
		List<Campground> campgrounds = new ArrayList<>();
		
		String sqlGetAllCampgrounds = "SELECT * FROM campground c JOIN park p ON p.park_id=c.park_id"
				+ "  WHERE upper(p.name) LIKE ? OR lower(p.name) LIKE ? OR p.name LIKE ?"
				+ " ORDER BY c.name";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlGetAllCampgrounds, "%"+campSearch+"%", "%"+campSearch+"%", "%"+campSearch+"%");
		
		while(rows.next()) {
			Campground camp = new Campground();
			camp.setCampgroundId(rows.getLong("campground_id"));
			camp.setParkId(rows.getLong("park_id"));
			camp.setName(rows.getString("name"));
			camp.setOpenFrom(rows.getString("open_from_mm"));
			camp.setOpenTo(rows.getString("open_to_mm"));
			camp.setDailyFee(rows.getString("daily_fee"));
			
			campgrounds.add(camp);
		}
		return campgrounds;
	}


	
	@Override
	public List<Campground> searchCampgroundByName(String nameSearch) {
		List<Campground> campgrounds = new ArrayList<>();
		
		String sqlSearchCampgroundByName = "SELECT * FROM campground WHERE name LIKE ? OR upper(name) LIKE ? OR lower(name) LIKE ?"; 
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlSearchCampgroundByName, "%"+nameSearch+"%", "%"+nameSearch+"%", "%"+nameSearch+"%");
		
		while (rows.next()) {
			Campground camp = new Campground();
			camp.setCampgroundId(rows.getLong("campground_id"));
			camp.setParkId(rows.getLong("park_id"));
			camp.setName(rows.getString("name"));
			camp.setOpenFrom(rows.getString("open_from_mm"));
			camp.setOpenTo(rows.getString("open_to_mm"));
			camp.setDailyFee(rows.getString("daily_fee"));
			
			campgrounds.add(camp);
			
		}
		
		return campgrounds;
	}

}
