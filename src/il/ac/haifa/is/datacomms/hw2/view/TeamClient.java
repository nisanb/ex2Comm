package il.ac.haifa.is.datacomms.hw2.view;

/**
 *	Class representation of Amazing Race Application.
 */
public final class TeamClient implements Runnable {
	
	//-------------------------------------------------------------------
	//-----------------------------fields--------------------------------
	//-------------------------------------------------------------------
	
	/**auto-numbers the clients.*/
	private static int nextId;
	
	/**client id.*/
	private final int teamId;
	
	//-------------------------------------------------------------------
	//-------------------------constructors------------------------------
	//-------------------------------------------------------------------
	
	protected TeamClient() {
		this.teamId = nextId++;
	}
	
	//-------------------------------------------------------------------
	//-------------------------functionality-----------------------------
	//-------------------------------------------------------------------
	
	// must follow the following storyline: 
	// 1. obtain list of flights relevant to airport, destination & time window; obtain team.
	// 2. if couldn't obtain team, print something and exit.	(assume always returns a flights list in this simulation).
	// 3. for each returned flight, check locally if has enough seats, is time relevant, and destination relevant.
	// 4. if so, try booking the flight.	if not, don't do anything, continue to next flight.
	// 5. if successful, print something and exit.
	// 6. if unsuccessful, wait 3 seconds and try the next flight.
	// 7. if failed booking all flights, or no flights returned, print something and exit.
	@Override
	public void run() {
		// TODO
	}
	
	//-------------------------------------------------------------------
	//----------------------------utility--------------------------------
	//-------------------------------------------------------------------
	
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
