package il.ac.haifa.is.datacomms.hw2.view;

import java.time.LocalTime;

/**
 * some constants.
 */
public final class Consts {
	private Consts() {}
	
	/**time of simulation.*/
	public static final LocalTime NOW = LocalTime.of(15, 0, 0, 0);
	
	/**next race leg starts in this location.*/
	public static final String DESTINATION = "Miami, FL";
	
	/**airport teams are at.*/
	public static final String AIRPORT = "Ben Gurion";
	
	/**default password used for team logins.*/
	public static final String DEFAULT_PASSWORD = "123";
}
