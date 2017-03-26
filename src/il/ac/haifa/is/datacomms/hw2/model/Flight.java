package il.ac.haifa.is.datacomms.hw2.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Class representation of a Flight.
 */
public final class Flight implements Serializable{
	
	//-------------------------------------------------------------------
	//-----------------------------fields--------------------------------
	//-------------------------------------------------------------------
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**flight's id.*/
	private final int id;
	
	/**flight's destination.*/
	private String destination;
	
	/**flight's time.*/
	private LocalTime time;
	
	/**flight's ticket price.*/
	private BigDecimal ticketPrice;
	
	/**flight's seats left (not booked).*/
	private short seatsLeft; 
	
	/**list of teams who were able to book tickets on this flight.*/
	private ArrayList<Team> teamsOnFlight;
	
	//-------------------------------------------------------------------
	//-------------------------constructors------------------------------
	//-------------------------------------------------------------------
	
	/**
	 * @param id flight's id.
	 */
	protected Flight(int id) {
		this.id = id;
		teamsOnFlight = new ArrayList<Team>();
	}
	
	//-------------------------------------------------------------------
	//-------------------------functionality-----------------------------
	//-------------------------------------------------------------------
	
	/**
	 * books flight tickets for given team.
	 * <p>tests if team did not already booked given flight, team can afford the tickets, 
	 * and if there are enough (2) tickets left.
	 * <br>If all checks passed: updates tickets left, charges team's budget, and adds team to flight.
	 * @param team team to book for.
	 * @return true if succeeded, false otherwise.
	 */
	protected boolean bookTicketsFor(Team team) {
		
		//Check if there is space on flight
		if(this.getSeatsLeft()<2){
			Main.Log("Team "+team.getId()+" does not have tickets left to purchase on flight "+this.getId());
			return false;
		}
		//Check if team already booked this flight
		if(teamsOnFlight.contains(team)){
			Main.Log("Team "+team.getId()+" already booked a ticket for flight #"+this.getId());
			return false;
		}
		
		if(!team.canAfford(getTicketPrice().multiply(new BigDecimal(2.0)))){
			Main.Log("Team "+team.getId()+" with budget "+team.getBudget()+" cannot afford tickets for flight "+this.getId());
			return false;
		}
		
		
		team.spendMoney(getTicketPrice().multiply(new BigDecimal(2)));
		teamsOnFlight.add(team);
		Main.Log("Team "+team.getId()+" successfully booked flight #"+this.getId());
		return true;
	}
	
	/**
	 * @return If flight was booked for the show, a string with teams who were able to book tickets for this flight.
	 */
	protected String getBookingReport() {
		String out = "";
		
		
		
			out += this + "\thas been booked by the following teams (" + 
											teamsOnFlight.size() + "):\n";
			for (Team team : teamsOnFlight)
				out += "\t" + team;
				out += "\n";
				
		
				
		return out;
	}
		
	//-------------------------------------------------------------------
	//----------------------------utility--------------------------------
	//-------------------------------------------------------------------
	
	//-------------------------------------------------------------------
	//----------------------------getters--------------------------------
	//-------------------------------------------------------------------
	
	/**
	 * @return flight's id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return flight's destination.
	 */
	public String getDestination() {
		return destination;
	}
	
	/**
	 * @return flight's time.
	 */
	public LocalTime getTime() {
		return time;
	}
	
	/**
	 * @return flight's ticket price.
	 */
	public BigDecimal getTicketPrice() {
		return ticketPrice;
	}
	
	/**
	 * @return flight's seats left (not booked).
	 */
	public synchronized short getSeatsLeft() {
		return seatsLeft;
	}
	
	//-------------------------------------------------------------------
	//----------------------------setters--------------------------------
	//-------------------------------------------------------------------
	
	/**
	 * @param seats seats to be set.
	 * @return reference to this instance.
	 */
	protected Flight setSeatsLeft(short seats) {
		seatsLeft = seats;
		return this;
	}
	
	/**
	 * @param ticketPrice ticket price to be set.
	 * @return reference to this instance.
	 */
	protected Flight setTicketPrice(BigDecimal ticketPrice) {
		if (ticketPrice == null)
			throw new NullPointerException();
		this.ticketPrice = ticketPrice;
		return this;
	}
	
	/**
	 * @param time time to be set.
	 * @return reference to this instance.
	 */
	protected Flight setTime(LocalTime time) {
		if (time == null)
			throw new NullPointerException();
		this.time = time;
		return this;
	}
	
	/**
	 * @param destination destination to be set.
	 * @return reference to this instance.
	 */
	protected Flight setDestination(String destination) {
		if (destination == null)
			throw new NullPointerException();
		this.destination = destination;
		return this;
	}
	
	//-------------------------------------------------------------------
	//---------------------------overrides-------------------------------
	//-------------------------------------------------------------------
	
	@Override
	public String toString() {
		return String.format("Flight [ id=%d, destination='%s', time=%s, ticketPrice=%s, seatsLeft=%d ]\n", 
				id, (destination != null) ? destination : "N/A", (time != null) ? time : "N/A", 
				(ticketPrice != null) ? NumberFormat.getCurrencyInstance().format(ticketPrice) : "N/A", seatsLeft);
	}
}
