package com.techelevator;

public class Campground {

	private Long campgroundId;
	private Long parkId;
	private String name;
	private String openFrom;
	private String openTo;
	private String dailyFee;
	
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public Long getParkId() {
		return parkId;
	}
	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpenFrom() {
		return openFrom;
	}
	public void setOpenFrom(String openFrom) {
		this.openFrom = openFrom;
	}
	public String getOpenTo() {
		return openTo;
	}
	public void setOpenTo(String openTo) {
		this.openTo = openTo;
	}
	public String getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(String dailyFee) {
		this.dailyFee = dailyFee;
	}
}

