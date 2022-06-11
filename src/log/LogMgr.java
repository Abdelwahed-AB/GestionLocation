package log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

import javax.swing.JOptionPane;

import connectionManager.Config;

public class LogMgr {
	//global logger object
	public final static Logger logr = Logger.getGlobal();
	
	static {
		
		//La date d'aujourdui
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
		LocalDateTime now = LocalDateTime.now();
		String today = dtf.format(now);
		
		try {
			//handler qui gere les donnees de fichier
			FileHandler handler = new FileHandler("logs/log-"+today+".txt", true);
			handler.setFormatter(new SimpleFormatter());
			handler.setLevel(Level.SEVERE);
			logr.addHandler(handler);
			
		} catch (SecurityException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur LOG", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur LOG", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void error(String message, Exception e) {
		if(Config.canLog)
			logr.log(Level.SEVERE, message, e);
		else
			System.out.println("hey");
	}
}
