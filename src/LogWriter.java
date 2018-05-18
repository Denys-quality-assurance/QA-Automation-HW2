import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/*
 * class LogWriter
 * writing files and information in the console 
 * 
 * authors Vladyslav Petrov, Denys Matolikov
 */

public class LogWriter {
	
    //writing Log.txt file and information in the console 
	public static void writingLogFile(TreeMap<Date, String> newLog, String toForAll, DateFormat formatDay){

		try {
			BufferedWriter writerAllLog;
	        //BufferedWriter - class of record of a stream, Constructs a FileWriter object given a file name with a boolean indicating whether or not to append the data written.
	        writerAllLog = new BufferedWriter(new FileWriter(toForAll));					
	        
	        
	        //The entrySet() method is used to return a Set view of the mappings contained in this map. The set's iterator returns the entries in ascending key order.
	        for(Map.Entry<Date, String> item : newLog.entrySet()){					 
	        	//System.getProperty("line.separator") returns the OS dependent line separator
	        	writerAllLog.write(formatDay.format(item.getKey())+" : "+ item.getValue() + System.getProperty("line.separator")); 
		        }
		    writerAllLog.close();
		   	} catch (IOException e) {
				e.printStackTrace();
			}
	       
	System.out.println("==================================================");
	System.out.println("log.txt is created");       
	}
	       
	//writing Week_Log.txt file and information in the console     
	public static void writingWeekLogFile(TreeMap<Date, HashMap<String, Integer>> WeekLog,String toForWeek, DateFormat formatWeek){
	       
		try {       
			BufferedWriter writerByWeek;
	        writerByWeek = new BufferedWriter(new FileWriter(toForWeek));					
	        
	        
	        //The entrySet() method is used to return a Set view of the mappings contained in this map. The set's iterator returns the entries in ascending key order.
	        for(Map.Entry<Date, HashMap<String, Integer>> item : WeekLog.entrySet()){
	            //create calendar and set minimal date from newLog
	            Calendar calendar = Calendar.getInstance();
	            //save start date of a two-week period
	            Date StartDateOf2WeekPeriod = item.getKey();
	            calendar.setTime(StartDateOf2WeekPeriod);
	            //date offset for 13 days ahead
	            calendar.add(Calendar.DAY_OF_MONTH, 13);
	            //save finish date of a two-week period
	            Date FinishDateOf2WeekPeriod = calendar.getTime();
	            writerByWeek.write("=================================================="+System.getProperty("line.separator"));
	            writerByWeek.write(formatWeek.format(StartDateOf2WeekPeriod)+ " - " + formatWeek.format(FinishDateOf2WeekPeriod) + ":"+ System.getProperty("line.separator")+ System.getProperty("line.separator"));        	
		        	for(Map.Entry<String, Integer> item1 : item.getValue().entrySet()){
		        		writerByWeek.write(item1.getKey() + ": "+ item1.getValue() + ", "); 
		        	}
	        	//System.getProperty("line.separator") returns the OS dependent line separator
	        	writerByWeek.write(System.getProperty("line.separator")+"=================================================="+System.getProperty("line.separator"));
	        }
	    writerByWeek.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	       
	System.out.println("==================================================");
	System.out.println("Week_log.txt is created");
	System.out.println("==================================================");
	System.out.println("Process completed!"); 
}
}
