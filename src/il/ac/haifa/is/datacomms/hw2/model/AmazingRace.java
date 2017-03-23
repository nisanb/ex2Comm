package il.ac.haifa.is.datacomms.hw2.model;

/**
 * interface representing the race itself & its available functionality.
 */
public interface AmazingRace {
	/**
	 * @param teamId team's id.
	 * @param password team's login password.
	 * @return requested team. or null if doesn't exist or wrong password.
	 */
	public Team getTeam(int teamId, String password);
}
