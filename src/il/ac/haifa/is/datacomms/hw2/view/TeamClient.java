package il.ac.haifa.is.datacomms.hw2.view;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.ArrayList;

import il.ac.haifa.is.datacomms.hw2.controller.RemoteControl;
import il.ac.haifa.is.datacomms.hw2.model.AmazingRace;
import il.ac.haifa.is.datacomms.hw2.model.Flight;
import il.ac.haifa.is.datacomms.hw2.model.Team;

/**
 * Class representation of Amazing Race Application.
 */
public final class TeamClient implements Runnable {

	// -------------------------------------------------------------------
	// -----------------------------fields--------------------------------
	// -------------------------------------------------------------------

	/** auto-numbers the clients. */
	private static int nextId;

	/** client id. */
	private final int teamId;

	// -------------------------------------------------------------------
	// -------------------------constructors------------------------------
	// -------------------------------------------------------------------

	protected TeamClient() {
		this.teamId = nextId++;
	}

	// -------------------------------------------------------------------
	// -------------------------functionality-----------------------------
	// -------------------------------------------------------------------

	// must follow the following storyline:
	// 1. obtain list of flights relevant to airport, destination & time window;
	// obtain team.
	// 2. if couldn't obtain team, print something and exit. (assume always
	// returns a flights list in this simulation).
	// 3. for each returned flight, check locally if has enough seats, is time
	// relevant, and destination relevant.
	// 4. if so, try booking the flight. if not, don't do anything, continue to
	// next flight.
	// 5. if successful, print something and exit.
	// 6. if unsuccessful, wait 3 seconds and try the next flight.
	// 7. if failed booking all flights, or no flights returned, print something
	// and exit.
	@Override
	public void run() {
		Main.Log("Attemping to login - team " + this.teamId);
		
		
		RemoteControl rem = null;
		Boolean hold = false;
		
		
		
		try {
			Main.Log("Team " + teamId + " receiving RemoteControl object reference");
			rem = (RemoteControl) Naming.lookup("//127.0.0.1:3000/RemoteControl");

			// Log into the system
			Main.Log("Team " + teamId + " attemps to log in");
			Object team = rem.getTeam(teamId, Consts.DEFAULT_PASSWORD);
			if (team != null)
				Main.Log("Team " + teamId + " logged in successfully!");
			else {
				Main.Log("Login unsuccessfull. Killing Thread for team " + teamId + "..");
				return;
			}

			// Obtain flight list to airport, destination & time window
			LocalTime lt = LocalTime.of(15, 0);
			Main.Log("Team " + teamId + " obtaining flight list for: " + Consts.AIRPORT + " to " + Consts.DESTINATION
					+ " from " + lt);
			ArrayList<Flight> flightList = rem.getFlightsTo(Consts.DESTINATION, Consts.AIRPORT, lt);
			Main.Log("Team " + teamId + " obtained " + flightList.size() + " flights possible.");

			

			for (Flight f : flightList) {
				if (hold){
					Main.Log("Team "+teamId+" is Waiting 3 seconds before attempting to book another flight..");
					Thread.sleep(3000);
				}
				hold = true;
				// Check if destination is relevant
				if (!f.getDestination().equals(Consts.DESTINATION))
					continue;

				// Check if time relevant
				if (f.getTime().isBefore(LocalTime.of(15, 0)))
					continue;

				//Check if flight has seats left
				if (f.getSeatsLeft() < 2) {
					Main.Log("Flight #" + f.getId() + " has no available sits for team " + teamId);
					continue;
				}

				//Attempt to book the flight at the remote server
				if (rem.bookFlight(Consts.AIRPORT, f, (Team) team)) {
					Main.Log("Successfully booked flight #" + f.getId() + " for team: " + teamId);
					hold = false;
					break;
				}

			}

		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(hold)
			Main.Log("Team "+teamId+" could not book a flight at all!");
		

	}

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
