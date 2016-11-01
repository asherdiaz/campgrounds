package com.techelevator;

import java.time.LocalDate;

public class Reservation {
	
	private Long reservationId;
	private Long siteId;
	private String name;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate createDate;
	private String dailyFee;
	private Long maxCapicity;
	private Boolean accessible;
	private Long maxRVLength;
	private Boolean utilites;
	private Long campground_id;
	private Long siteNumber;
	private Long occupancy;
	
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	public String getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(String dailyFee) {
		this.dailyFee = dailyFee;
	}
	public Long getMaxCapicity() {
		return maxCapicity;
	}
	public void setMaxCapicity(Long maxCapicity) {
		this.maxCapicity = maxCapicity;
	}
	public Boolean getAccessible() {
		return accessible;
	}
	public void setAccessible(Boolean accessible) {
		this.accessible = accessible;
	}
	public Long getMaxRVLength() {
		return maxRVLength;
	}
	public void setMaxRVLength(Long maxRVLength) {
		this.maxRVLength = maxRVLength;
	}
	public Boolean getUtilites() {
		return utilites;
	}
	public void setUtilites(Boolean utilites) {
		this.utilites = utilites;
	}
	public Long getCampground_id() {
		return campground_id;
	}
	public void setCampground_id(Long campground_id) {
		this.campground_id = campground_id;
	}
	public Long getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(Long siteNumber) {
		this.siteNumber = siteNumber;
	}
	public Long getOccupancy() {
		return occupancy;
	}
	public void setOccupancy(Long occupancy) {
		this.occupancy = occupancy;
	}
	
}
