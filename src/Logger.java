import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeMap;

/*
 * class Logger
 * reading files from the folder, finding all transactions IDs (writing Log.txt), 
 * counting the number of transactions for two-week periods (writing Week_Log.txt)
 * 
 * authors Denys Matolikov, Vladyslav Petrov
 */

public class Logger {
	
    public void log(String from, String toForAll, String toForWeek) {
    	//create an ordered map
    	TreeMap<Date, String> newLog = new TreeMap<Date, String>();						
    	//specify the date format for log.txt
    	DateFormat formatDay = new SimpleDateFormat("MMM  d HH:mm:ss", Locale.ENGLISH); 	
    	//specify the date format for Week_log.txt
    	DateFormat formatWeek = new SimpleDateFormat("dd-MM-YYYY", Locale.ENGLISH);
    	//specify the date format for year determination 
    	DateFormat formatYear = new SimpleDateFormat("yyyy MMM  d HH:mm:ss", Locale.ENGLISH); 

    	
    	//reading files from the folder and creating TreeMap<Date, String> newLog    	
    	newLog=LogReader.readingFiles_creatingMap (from, formatYear);

     	//counting the number of transactions for two-week periods        
    	TreeMap<Date, HashMap<String, Integer>> WeekLog = WeekLogCreator.creation(newLog);       
        
	    //writing Log.txt file and information in the console   
	    LogWriter.writingLogFile(newLog, toForAll, formatDay);
	    
		//writing Week_Log.txt file and information in the console   
	    LogWriter.writingWeekLogFile(WeekLog,toForWeek,formatWeek);    
    }
}