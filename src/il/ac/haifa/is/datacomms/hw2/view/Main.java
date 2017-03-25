package il.ac.haifa.is.datacomms.hw2.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

/**
 * Main class used to start view server up.
 */
public final class Main {
	protected static File logFolder;
	protected static PrintStream prntstrm;
	protected static Thread threads[];

	private Main() {
	}

	public static void main(String[] args) throws InterruptedException {
		initiateLogs();
		Main.Log("View Server is Up!\n");
		threads = new Thread[10];

		// Initiate teams
		for (int i = 0; i < 2; i++) {
			threads[i] = new Thread(new TeamClient());
			threads[i].start();
		}
		for (Thread th : threads)
			if (th != null)
				th.join();

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
			// System.setErr(prntstrm);
			// System.setOut(prntstrm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
