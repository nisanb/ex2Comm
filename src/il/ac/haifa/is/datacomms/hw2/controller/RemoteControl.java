package il.ac.haifa.is.datacomms.hw2.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.ArrayList;
import il.ac.haifa.is.datacomms.hw2.model.Airport;
import il.ac.haifa.is.datacomms.hw2.model.AmazingRace;
import il.ac.haifa.is.datacomms.hw2.model.Flight;
import il.ac.haifa.is.datacomms.hw2.model.Team;

/**
 *	Controller layer interface.
 */
public interface RemoteControl extends Remote {
	/**
	 * @param from airport flights are departing from. i.e. 'LAX'
	 * @param destination flight destination. i.e. 'Houston, TX'
	 * @param startTime lower bound of flight times. i.e. 17:00, all flights taking off between this time and 23:59 on the same day.
	 * @return list of flights to given destination from given startTime. null if unrecognized airport.
	 * @see Airport#getFlightsTo(String, LocalTime)
	 */
	public ArrayList<Flight> getFlightsTo(String destination, String from, LocalTime startTime) throws RemoteException;
	
	/**
	 * @param teamId team's id.
	 * @param password team's login password.
	 * @return requested team. or null if doesn't exist or wrong password.
	 * @see AmazingRace#getTeam(int, String)
	 */
	public Team getTeam(int teamId, String password) throws RemoteException;
	
	/**
	 * @param airport airport flight departs from.
	 * @param flight flight to book.
	 * @param team team booking the flight.
	 * @return true if succeeded, false otherwise.
	 * @see Airport#book(Flight, Team)
	 */
	public boolean bookFlight(String airport, Flight flight, Team team) throws RemoteException;
	
	/**
	 * @param airport airport flights are departing from.
	 * @return Text with flights & teams who were able to book tickets for them.
	 * @see Airport#getBookingReport()
	 */
	public String getBookingReport(String airport) throws RemoteException;
}
