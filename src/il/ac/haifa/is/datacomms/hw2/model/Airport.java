package il.ac.haifa.is.datacomms.hw2.model;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 *	interface representing an Airport in the system and it's available functionality.
 */
public interface Airport {
	/**
	 * @param destination flight destination.
	 * @param startTime lower bound of flight times. i.e. 15:00 will return all flights today taking off at 15:00 and later.
	 * @return list of relevant flights.
	 */
	public ArrayList<Flight> getFlightsTo(String destination, LocalTime startTime);
	
	/**
	 * @see Flight#bookTicketsFor(Team)
	 * @param flight flight to book.
	 * @param team team booking the flight.
	 * @return true if succeeded, false otherwise.
	 */
	public boolean book(Flight flight, Team team);
	
	/**
	 * @return a string with flights & teams who were able to book tickets for them.
	 */
	public String getBookingReport();
}
