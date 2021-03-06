package il.ac.haifa.is.datacomms.hw2.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Class representation of Ben Gurion Airport in Amazing Race's Application.
 */
public final class BenGurionAirport extends UnicastRemoteObject implements Airport, Serializable {
	
	//-------------------------------------------------------------------
	//-----------------------------fields--------------------------------
	//-------------------------------------------------------------------
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**singleton instance.*/
	private volatile static BenGurionAirport instance;
	
	/**flights taking off from this airport.*/
	private volatile ArrayList<Flight> flights;
	
	private String airportName;
	//-------------------------------------------------------------------
	//-------------------------constructors------------------------------
	//-------------------------------------------------------------------
	
	/**
	 * private c'tor for singleton use.
	 */
	private BenGurionAirport() throws RemoteException {
		this.airportName = "Ben Gurion";
		initFlights();
	}
	
	/**
	 * singleton getter.
	 * @return only instance of given class.
	 */
	public static BenGurionAirport getInstance() throws RemoteException{
		if(instance==null)
			instance = new BenGurionAirport();
		
		return instance;
	}
	
	//-------------------------------------------------------------------
	//-------------------------functionality-----------------------------
	//-------------------------------------------------------------------
	
	@Override
	public String getBookingReport() {
		
		Main.Log(LocalTime.now() + " get booking report"); // XXX
		String out = "Booking Report:\n\n";
		
		for(Flight f : flights)
			out += f.getBookingReport();
		
		
		return out;
	}
	
	//-------------------------------------------------------------------
	//----------------------------utility--------------------------------
	//-------------------------------------------------------------------	
	
	/**
	 * initializes flights list. reads flights data from file.
	 */
	@SuppressWarnings("unchecked")
	private void initFlights() {
		flights = new ArrayList<>();
		try (InputStream is = getClass().getResourceAsStream("/flights.json");
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			Iterator<JSONObject> iterator = 
					((JSONArray) new JSONParser().parse(reader)).iterator();
			while (iterator.hasNext()) {
				JSONObject obj = (JSONObject) iterator.next();
				String[] timeComponents = ((String) obj.get("time")).trim().split(":");
				flights.add(new Flight(((Number) obj.get("id")).intValue())
									.setDestination(obj.get("departingTo").toString())
									.setTime(LocalTime.of(Integer.parseInt(timeComponents[0]), 
															Integer.parseInt(timeComponents[1])))
									.setTicketPrice(BigDecimal.valueOf(
											((Number) obj.get("price")).doubleValue()))
									.setSeatsLeft(((Number) obj.get("seats")).shortValue()));
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(LocalTime.now() + 
				" flights data fetched from file:\n\n" + flights + "\n"); // XXX
	}

	@Override
	public ArrayList<Flight> getFlightsTo(String destination, LocalTime startTime) throws RemoteException {
		Main.Log("Aquiring flight list to "+destination+" from starting time: "+startTime);
		ArrayList<Flight> toReturn = new ArrayList<Flight>();
		for(Flight f : flights){
			if(f.getDestination().equals(destination) && startTime.isBefore(f.getTime())){
				toReturn.add(f);
				System.err.println("Adding flight #"+f.getId()+" with "+f.getSeatsLeft()+" seats.");
			}
				
		}
		Main.Log("Found "+toReturn.size()+" flights.");
		return toReturn;
	}
	

	@Override
	public boolean book(Flight flight, Team team){
		Main.Log("Trying to book flight #"+flight.getId()+" for team "+team.getId());
		Boolean getBool =  flights.get(flights.indexOf(flight)).bookTicketsFor(team);
		return getBool;

	}

	/**
	 * @return the airportName
	 */
	public String getAirportName() throws RemoteException{
		return airportName;
	}

	/**
	 * @param airportName the airportName to set
	 */
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	
	
	
	//-------------------------------------------------------------------
	//----------------------------getters--------------------------------
	//-------------------------------------------------------------------
	
	//-------------------------------------------------------------------
	//----------------------------setters--------------------------------
	//-------------------------------------------------------------------
	
	//-------------------------------------------------------------------
	//---------------------------overrides-------------------------------
	//-------------------------------------------------------------------
}
