package il.ac.haifa.is.datacomms.hw2.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;

/**
 * Main class used to start control server up.
 *///
public final class Main {
	protected static File logFolder;
	protected static PrintStream prntstrm;

	private Main() {
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		//Initiate the log files
		initiateLogs();
		Main.Log("Starting Controller Server . . .");
		Main.Log("Binding RemoteControl");
		Naming.rebind("//127.0.0.1:3000/RemoteControl", RemoteControlImpl.getInstance());
		Main.Log("Bind successfull");
		Main.Log("Controller Server is Up!\n");

	}

	/**
	 * The log method prints a given string to the file needed and syso
	 * 
	 * @param str
	 */
	public static void Log(String str) {
		prntstrm.println(LocalTime.now() + " - " + str);
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
			prntstrm = new PrintStream(new File(logFolder.getAbsolutePath() + "/MainLog_Controller.log"));
			System.setErr(prntstrm);
          //  System.setOut(prntstrm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
