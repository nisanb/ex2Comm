package il.ac.haifa.is.datacomms.hw2.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public final class AmazingRaceImpl extends UnicastRemoteObject implements AmazingRace{
	
	//-------------------------------------------------------------------
	//-----------------------------fields--------------------------------
	//-------------------------------------------------------------------
	
	/**singleton instance.*/
	private volatile static AmazingRaceImpl instance;
	
	/**teams participating in the race.*/
	private ArrayList<Team> teams;
	
	//-------------------------------------------------------------------
	//-------------------------constructors------------------------------
	//-------------------------------------------------------------------
	
	/**
	 * private c'tor for singelton use.
	 */
	private AmazingRaceImpl() throws RemoteException{
		initTeams();
	}
	
	/**
	 * singleton getter.
	 * @return only instance of this class.
	 */
	public synchronized static AmazingRaceImpl getInstance() {
		if(instance==null)
			try {
				instance = new AmazingRaceImpl();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return instance;
	}
	
	//-------------------------------------------------------------------
	//-------------------------functionality-----------------------------
	//-------------------------------------------------------------------
	
	//-------------------------------------------------------------------
	//----------------------------utility--------------------------------
	//-------------------------------------------------------------------	
	
	/**
	 * initializes teams.
	 */
	@SuppressWarnings("unchecked")
	private void initTeams() {
		teams = new ArrayList<>();
		try (InputStream is = getClass().getResourceAsStream("/teams.json");
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			Iterator<JSONObject> outerIterator = 
					((JSONArray) new JSONParser().parse(reader)).iterator();
			while (outerIterator.hasNext()) {
				JSONObject obj = (JSONObject) outerIterator.next();
				teams.add(new Team(((Number) obj.get("id")).intValue())
								.setPassword((String) obj.get("password"))
								.setBudget(BigDecimal.valueOf(((Number) obj.get("budget")).doubleValue())));
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(LocalTime.now() + 
				" teams data fetched from file:\n\n" + teams + "\n"); // XXX
	}
	
	//-------------------------------------------------------------------
	//----------------------------getters--------------------------------
	//-------------------------------------------------------------------
	
	/**
	 * @param team requested team's instance.
	 * @return requested team's instance stored in teams list, or null if requested team does not exist in the list.
	 */
	protected Team getTeam(Team team) {
		final int index = teams.indexOf(team);
		return (index == -1) ? null : teams.get(index);
	}

	@Override
	public Team getTeam(int teamId, String password) throws RemoteException {
		Team t = new Team(teamId);
		return t;
	}
	
	//-------------------------------------------------------------------
	//----------------------------setters--------------------------------
	//-------------------------------------------------------------------
	
	//-------------------------------------------------------------------
	//---------------------------overrides-------------------------------
	//-------------------------------------------------------------------
}
