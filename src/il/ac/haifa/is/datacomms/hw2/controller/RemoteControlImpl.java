package il.ac.haifa.is.datacomms.hw2.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.ArrayList;

import il.ac.haifa.is.datacomms.hw2.model.AmazingRace;
import il.ac.haifa.is.datacomms.hw2.model.AmazingRaceImpl;
import il.ac.haifa.is.datacomms.hw2.model.Flight;
import il.ac.haifa.is.datacomms.hw2.model.Team;

/**
 * Implementation of RemoteControl Interface.
 */
public final class RemoteControlImpl extends UnicastRemoteObject implements RemoteControl{
	
	//-------------------------------------------------------------------
	//-----------------------------fields--------------------------------
	//-------------------------------------------------------------------
	
	/**singleton instance.*/
	private static volatile RemoteControlImpl instance;
	
	//-------------------------------------------------------------------
	//-------------------------constructors------------------------------
	//-------------------------------------------------------------------
	
	/**
	 * private c'tor used in singleton getter.
	 */
	private RemoteControlImpl() throws RemoteException{}
	
	/**
	 * singleton getter.
	 * @return only instance of RemoteControl.
	 * @throws RemoteException 
	 */
	public synchronized static RemoteControlImpl getInstance() throws RemoteException {
		if(instance==null)
			instance = new RemoteControlImpl();
		return instance;
	}

	@Override
	public ArrayList<Flight> getFlightsTo(String destination, String from, LocalTime startTime) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team getTeam(int teamId, String password) throws RemoteException {
		return AmazingRaceImpl.getInstance().getTeam(teamId, password);
	}

	@Override
	public boolean bookFlight(String airport, Flight flight, Team team) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getBookingReport(String airport) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	//-------------------------------------------------------------------
	//-------------------------functionality-----------------------------
	//-------------------------------------------------------------------
	
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
