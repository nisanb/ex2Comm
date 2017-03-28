package il.ac.haifa.is.datacomms.hw2.controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.SpinnerNumberModel;

import il.ac.haifa.is.datacomms.hw2.model.Airport;
import il.ac.haifa.is.datacomms.hw2.model.AmazingRace;
import il.ac.haifa.is.datacomms.hw2.model.AmazingRaceImpl;
import il.ac.haifa.is.datacomms.hw2.model.BenGurionAirport;
import il.ac.haifa.is.datacomms.hw2.model.Flight;
import il.ac.haifa.is.datacomms.hw2.model.Team;

/**
 * Implementation of RemoteControl Interface.
 */
public final class RemoteControlImpl extends UnicastRemoteObject implements RemoteControl {

	// -------------------------------------------------------------------
	// -----------------------------fields--------------------------------
	// -------------------------------------------------------------------

	/** singleton instance. */
	private static volatile RemoteControlImpl instance;
	

	// -------------------------------------------------------------------
	// -------------------------constructors------------------------------
	// -------------------------------------------------------------------

	/**
	 * private c'tor used in singleton getter.
	 */
	private RemoteControlImpl() throws RemoteException {
	}

	/**
	 * singleton getter.
	 * 
	 * @return only instance of RemoteControl.
	 * @throws RemoteException
	 */
	public synchronized static RemoteControlImpl getInstance() throws RemoteException {
		if (instance == null)
			instance = new RemoteControlImpl();
		return instance;
	}

	@Override
	public ArrayList<Flight> getFlightsTo(String destination, String from, LocalTime startTime) throws RemoteException {
		Main.Log("Getting flight list from " + from + " to " + destination + " from starting time: " + startTime);
		Airport bgAirport = null;
		// Aquire BGAirport Instance
		try {
			bgAirport = (Airport) Naming.lookup("//"+Main.IP+":"+Main.PORT+"/BGAirport");
			return bgAirport.getFlightsTo(destination, startTime);

		} catch (MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Flight>();

	}

	@Override
	public Team getTeam(int teamId, String password) throws RemoteException {
		AmazingRace amzRace = null;
		try {
			amzRace = (AmazingRace) Naming.lookup("//127.0.0.1:3000/AmazingRace");
		} catch (MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Main.Log("Log in attempt for team: " + teamId + " " + password);
		Team t = amzRace.getTeam(teamId, password);
		if (t != null)
			Main.Log("Team " + t + " found !");
		else
			Main.Log("Team " + teamId + " not found!");
		return t;
	}

	@Override
	public boolean bookFlight(String airport, Flight flight, Team team) throws RemoteException {

		synchronized (this) {
			// Using syncronized because it is counting on dynamic data
			try {
				Main.Log("Attemping to book flight #" + flight.getId() + " to " + flight.getDestination() + " for team "
						+ team.getId());
				Airport bgAirport = (Airport) Naming.lookup("//127.0.0.1:3000/BGAirport");
				if (bgAirport.book(flight, team)) {
					Main.Log("Authorized to book flight #" + flight.getId() + " to " + flight.getDestination()
							+ " for team " + team.getId());
					return true;
				}
			} catch (MalformedURLException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Main.Log("Couldn't book tickets for team " + team.getId() + " for flight #" + flight.getId() + " to "
					+ flight.getDestination());
			return false;
		}
	}

	@Override
	public String getBookingReport(String airport) throws RemoteException {
		Main.Log("Get Booking Log Requested");
		try {
			return ((Airport) Naming.lookup("//127.0.0.1:3000/BGAirport")).getBookingReport();
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
		Main.Log("Returning NULL");
		return null;
	}

	// -------------------------------------------------------------------
	// -------------------------functionality-----------------------------
	// -------------------------------------------------------------------

	// -------------------------------------------------------------------
	// ----------------------------utility--------------------------------
	// -------------------------------------------------------------------

	// -------------------------------------------------------------------
	// ----------------------------getters--------------------------------
	// -------------------------------------------------------------------

	// -------------------------------------------------------------------
	// ----------------------------setters--------------------------------
	// -------------------------------------------------------------------

	// -------------------------------------------------------------------
	// ---------------------------overrides-------------------------------
	// -------------------------------------------------------------------
}
