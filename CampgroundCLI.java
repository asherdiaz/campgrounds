package com.techelevator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	
	private static final String MAIN_MENU_OPTION_LIST_PARKS = "View Park List";
	private static final String MAIN_MENU_OPTION_SELECT_PARKS = "Select a Park";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_OPTION_LIST_PARKS,
																	 MAIN_MENU_OPTION_SELECT_PARKS,
																	 MAIN_MENU_OPTION_EXIT };
	
	private static final String MAIN_MENU_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String MAIN_MENU_OPTION_SEARCH_RESERVATIONS = "Search for Available Reservations";
	private static final String MAIN_MENU_OPTION_RETURN_FROM_CAMPGROUNDS = "Return to Main Menu";
	private static final String[] MAIN_MENU_OPTION_CAMPGROUNDS = new String[] { MAIN_MENU_OPTION_VIEW_CAMPGROUNDS,
																	 MAIN_MENU_OPTION_SEARCH_RESERVATIONS,
																	 MAIN_MENU_OPTION_RETURN_FROM_CAMPGROUNDS };


	private static final String MAIN_MENU_OPTION_SEARCH_AVAILABLE_RESERVATIONS = "Search for Available Reservation";
	private static final String MAIN_MENU_OPTION_RETURN_FROM_RESERVATIONS = "Return to Main Menu";
	private static final String[] MAIN_MENU_OPTION_RESERVATIONS = new String[] {MAIN_MENU_OPTION_SEARCH_AVAILABLE_RESERVATIONS,
																	 MAIN_MENU_OPTION_RETURN_FROM_RESERVATIONS };
	
	private static final String MAIN_MENU_OPTION_MAKE_RESERVATIONS = "Make a Campsite Reservation";
	private static final String MAIN_MENU_OPTION_RETURN_FROM_CREATE_RESERVATIONS = "Return to Main Menu";
	private static final String[] MAIN_MENU_OPTION_CREATE_RESERVATIONS = new String[] {MAIN_MENU_OPTION_MAKE_RESERVATIONS,
																	 MAIN_MENU_OPTION_RETURN_FROM_CREATE_RESERVATIONS };

	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private ReservationDAO reservationDAO;
	private Menu menu;
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource dataSource) {
		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
		this.menu = new Menu(System.in, System.out); 
	}
	
	private void run() {
		while(true) {
			printFancyHeading("WELCOME TO THE NATIONAL PARK SYSTEM");
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(choice.equals(MAIN_MENU_OPTION_LIST_PARKS)) {
				handleParkList();
			}
			else if (choice.equals(MAIN_MENU_OPTION_SELECT_PARKS)) {
				handleSearchParks();
				handleCampgroundMenu();
			}
			else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.exit(0);
			}
			
		}
	}

	
	private void handleParkList() {
		List<Park> allParks = parkDAO.getAllParks();
		listParkNames(allParks);
	}
	
	private void handleSearchParks() {
		printFancyHeading("Select a Park:");
		String parkSearch = getUserInput("Search for Park by Name");
		List<Park> parks = parkDAO.selectParkByName(parkSearch);
		listParkDetails(parks);
		
	}
	
	private void handleCampgroundMenu() {
		while(true) {
			printFancyHeading("Select a Command");
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTION_CAMPGROUNDS);
			if(choice.equals(MAIN_MENU_OPTION_VIEW_CAMPGROUNDS)) {
				handleCampgroundList();
			}
			else if (choice.equals(MAIN_MENU_OPTION_SEARCH_RESERVATIONS)) {
				handleSearchReservation();
				handleCreateReservation(); 
			}
			else if (choice.equals(MAIN_MENU_OPTION_RETURN_FROM_CAMPGROUNDS)) {
				run();
			}
		}
	}
	
	private void handleCampgroundList() {
	    printFancyHeading("Enter Park Name to Display its Campgrounds (Enter 0 to Exit): ");
		String parkCampgrounds = getUserInput("");
		if(parkCampgrounds.equals("0")){
			handleCampgroundMenu();
		}
		List<Campground> allCampgrounds = campgroundDAO.getCampgrounds(parkCampgrounds);
		listCampgroundNames(allCampgrounds);
		
	}
	
	private void printFancyHeading(String headingText) {
		System.out.println("\n"+headingText);
		for(int i = 0; i < headingText.length(); i++) {
			System.out.print("_");
		}
		System.out.println();
	}
	
	private void listParkNames(List<Park> parks) {
		System.out.println();
		if (parks.size() > 0) {
			for (Park park : parks) {
				System.out.println(park.getName());
			}
		}
		else {
			System.out.println("\n***NO RESULTS***");
		}
	}
	
	private void listParkDetails(List<Park> parks) {
		System.out.println();
		if (parks.size() > 0) {
			for (Park park : parks) {
				System.out.println("Name:   "+park.getName());
				System.out.println("Location:   "+park.getLocation());
				System.out.println("Established:   "+park.getEstablishedDate());
				System.out.println("Area:   "+park.getArea());
				System.out.println("Visitors:   "+park.getVisitors());
				System.out.println("Description:   "+park.getDescription());
			}
		}
		else {
			System.out.println("\n***NO RESULTS***");
			String input = getUserInput("Enter 'T' to try again OR 'E' to exit to main menu:").toUpperCase();
			if (input.equals("T")) {
				handleSearchParks();
			} else if (input.equals("E")) {
				run();
			} else {
				System.out.println("Please enter valid input.");
				handleSearchParks();
			}
		}
	}

	private void handleCreateReservation() {
		System.out.println();
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			printFancyHeading("Create a Reservation");
			String siteId = getUserInput("What Site Do You Want To Reserve (Enter 0 to Cancel)  : ");
			if (siteId.equals("0")) {
				handleCampgroundMenu();
			}
			Long longSiteId = Long.parseLong(siteId);
			String reservationName = getUserInput("What Name Do You Want the Reservation Under? ");
			String startingDate = getUserInput("What was the arrival Date? (YYYY-MM-DD) ");
			String endingDate = getUserInput("What was the departure Date? (YYYY-MM-DD) ");
			Reservation newReservation = reservationDAO.createReservation(reservationName, longSiteId,
					LocalDate.parse(startingDate, formatter), LocalDate.parse(endingDate, formatter));
			System.out.println("\n***" + newReservation.getName() + " created! **** ");
			System.out.println("*** Your Confirmation Number is: " + newReservation.getReservationId());
		} catch (Exception e) {
			System.out.println("Please enter correct date format: YYYY-MM-DD.");
			handleCreateReservation();
		}

	}
	
	private void handleSearchReservation() {
		handleCampgroundList();
		try {
			printFancyHeading("Enter Campground Name to Search For Reservation (Enter 0 to Exit): ");
			String campground = getUserInput("");
			if (campground.equals("0")) {
				handleCampgroundMenu();
			}
			String startingDate = getUserInput("What is the arrival Date? (YYYY-MM-DD) ");
			String endingDate = getUserInput("What is the departure Date? (YYYY-MM-DD) ");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			List<Reservation> reservations = reservationDAO.availableSites(campground,
					LocalDate.parse(startingDate, formatter), LocalDate.parse(endingDate, formatter));
			listAvailableReservations(reservations);
		} catch (Exception e) {
			System.out.println("Please enter correct date format: YYYY-MM-DD.");
			handleCampgroundList();
		}
	}
	
	private void listAvailableReservations(List<Reservation> rev){
		System.out.println();
		System.out.print("Site No. \t \t Max Occup. \t\t Accessible \t\t  Max RV Length \t\t Utility \t\t Cost");
		if(rev.size() > 0){
			for(Reservation reservation : rev){
				System.out.println();
				System.out.print(reservation.getSiteId() + "\t\t");
				System.out.print(reservation.getOccupancy() + "\t\t");
				System.out.print(reservation.getAccessible() + "\t\t");
				System.out.print(reservation.getMaxRVLength() + "\t\t");
				System.out.print(reservation.getUtilites() + "\t\t");
				System.out.print("$"+reservation.getDailyFee()+"0\t\t");
			}
		}
	}
	
	private void listCampgroundNames(List<Campground> camp) {
		System.out.println();
		System.out.println("Name\tOpen\tClose\tDaily Fee");
		if (camp.size() > 0) {
			for (Campground campground : camp) {
				System.out.println(campground.getName()+"\t"+campground.getOpenFrom()+"\t"+campground.getOpenTo()+"\t$"+campground.getDailyFee()+"0");
			}
		}
		else {
			System.out.println("\n***NO RESULTS***");
			String input = getUserInput("Enter 'T' to try again OR 'E' to exit to main menu:").toUpperCase();
			if (input.equals("T")) {
				handleCampgroundList();
			} else if (input.equals("E")) {
				run();
			} else {
				System.out.println("Please enter valid input.");
				handleCampgroundList();
			}
		}
		
	}
	
	
	private String getUserInput(String prompt) {
		System.out.print(prompt + ">>>");
		return new Scanner(System.in).nextLine();
	}
}
