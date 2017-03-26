package il.ac.haifa.is.datacomms.hw2.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import il.ac.haifa.is.datacomms.hw2.controller.RemoteControl;

/**
 * Main class used to start view server up.
 */
public final class Main {
	protected static File logFolder;
	protected static PrintStream prntstrm;
	protected static Thread threads[];

	private Main() {
	}

	public static void main(String[] args)  {
		initiateLogs();
		Main.Log("View Server is Up!\n");
		
		threads = new Thread[11];

		//Initiate teams threads
		for (int i = 0; i < 11; i++) {
			threads[i] = new Thread(new TeamClient());
			threads[i].start();
		}
		
		//Join threads to main
		for (Thread th : threads)
			if (th != null)
				try {
					th.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		//All teams finished booking the flights
		RemoteControl rem = null;
		try {
			rem = (RemoteControl) Naming.lookup("//127.0.0.1:3000/RemoteControl");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Main.Log(rem.getBookingReport(Consts.AIRPORT));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
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
			prntstrm = new PrintStream(new File(logFolder.getAbsolutePath() + "/MainLog_View.log"));
			 System.setErr(prntstrm);
			// System.setOut(prntstrm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
