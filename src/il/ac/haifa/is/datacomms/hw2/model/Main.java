package il.ac.haifa.is.datacomms.hw2.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

/**
 * Main class used to start model server up. 
 */
public final class Main {

	protected static File logFolder;
	protected static PrintStream prntstrm;

	private Main() {}
	
	public static void main(String[] args) {
		initiateLogs();
		
		
		Main.Log(LocalTime.now() + " model server is Up!\n"); // XXX
		
		try {
			Main.Log("Binding AmazingRace Instance to registry");
			Naming.rebind("//"+Consts.LOCALHOST+":"+Consts.PORT+"/AmazingRace", AmazingRaceImpl.getInstance());
			Main.Log("Biding BGAirport instance to registry");
			Naming.rebind("//"+Consts.LOCALHOST+":"+Consts.PORT+"/BGAirport", BenGurionAirport.getInstance());
			Main.Log("Binding success.");
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * The log method prints a given string to the file needed and syso
	 * 
	 * @param str
	 */
	public static void Log(String str) {
		//prntstrm.println(LocalTime.now() + " - " + str);
		System.out.println(LocalTime.now() + " - " + str);
	}

	/**
	 * This method initiates the log files
	 */
	private static void initiateLogs() {
		String dateTime = (new SimpleDateFormat("ddMM_hhmm")).format(new Date());
		try {
			logFolder = new File("logs_" + dateTime);
			logFolder.mkdir();
			prntstrm = new PrintStream(new File(logFolder.getAbsolutePath() + "/MainLog_Model.log"));
			System.setErr(prntstrm);
          //  System.setOut(prntstrm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
