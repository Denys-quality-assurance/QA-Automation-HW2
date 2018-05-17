import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/*
 * class Logger
 * version 1.05
 * authors Denys Matolikov, Vladyslav Petrov
 */

public class Logger {
	
    public void log(String from, String toForAll, String toForWeek) {
    	//add the source folder
    	File dir = new File(from);														
    	//create a list of files in the source folder
    	File[] filesInDir = dir.listFiles();											
    	//create an ordered map
    	TreeMap<Date, String> newLog = new TreeMap<Date, String>();						
    	//specify the date format for log.txt
    	DateFormat formatLog = new SimpleDateFormat("MMM  d HH:mm:ss", Locale.ENGLISH); 	
    	//specify the date format for Week_log.txt
    	DateFormat formatWeek = new SimpleDateFormat("dd-MM-YYYY", Locale.ENGLISH); 	
    	//line with a list of transaction IDs
    	String logID = null; 															
    	//transaction date
    	Date date;																		
    	//total number of files and folders in the directory
    	int numberOfFiles = filesInDir.length;
    	//number of the file being processed
    	int fileNumber = 1;
    	
    	//for each of the files in the directory
        for (File file : filesInDir){													
        	//output file name (for tracking the process)
        	System.out.print(file);														
        	//check whether the folder is the current file
        	if (!file.isDirectory()){													
                try {
                	//BufferedReader - flow reading class
                	BufferedReader reader = new BufferedReader(new FileReader(file));	

                	//line in which the line-by-line reading from the files is stored
                	String line = null;													
                	//until found the zero line (to the end of the file)
                	while ((line = reader.readLine()) != null) {						
                    		if (line.contains(":  ")){   
                    			//translate the first 15 characters of the line to the date
                    			date = formatLog.parse(line.substring(0,15));				
                    			//from line we leave everything from the third character after ":  " and remove a new line character
                    			logID = line.substring(line.indexOf(":  ")+3).replaceAll(System.getProperty("line.separator"),"");      
                             	
                    			//The Map.entrySet method returns a collection-view of the map, whose elements are of this class.
                    			//entrySet() method is used to return a Set view of the mappings contained in this map. The set's iterator returns the entries in ascending key order
                             	for(Map.Entry<Date, String> item : newLog.entrySet()){		 
                             		//if such date is already brought in map
                             		if (item.getKey().getTime()==date.getTime()) {			 
                             			//add new value with a divider ","
                             			logID=(item.getValue()+","+logID);					
                             			break;
                             		}
                             	}
                             	//add date and logID to the map 
                     			newLog.put(date, logID);								 						
                               	
    	                        }
                    }
                    reader.close();

                } catch (IOException e){
                    System.err.println("Error: " +e.getMessage());
                } catch (ParseException e) {
					e.printStackTrace();
				}
                System.out.println(" - " + fileNumber++ + "/" + numberOfFiles + " files");
            } else {
            	System.out.println(" - is Directory (not processed) - "  + fileNumber++ + "/" + numberOfFiles + " files");
            }
        }

//====================================================================================================================         
//        counting the number of transactions for two-week periods        
//====================================================================================================================         
        //================================================
        //preparation of the calendar, StartDateOf2WeekPeriod and FinishDateOf2WeekPeriod determination
        //================================================     
        //create calendar and set minimal date from newLog
        Calendar calendar = Calendar.getInstance();
    	calendar.setTime(Collections.min(newLog.keySet()));
      	//save start date of a two-week period
    	Date StartDateOf2WeekPeriod = calendar.getTime();
    	//date offset for 14 days ahead
    	calendar.add(Calendar.DAY_OF_MONTH, 14);
    	//save finish date of a two-week period
    	Date FinishDateOf2WeekPeriod = calendar.getTime();
 
        //================================================
        //preparation of the maps
        //================================================ 
    	//create  map: start date of a two-week period; ID name; number of transactions with this name in two weeks
    	TreeMap<Date, HashMap<String, Integer>> WeekLog = new TreeMap<Date, HashMap<String, Integer>>();		
    	//create  map: ID name; number of transactions with this name in two weeks
		HashMap<String, Integer> IDsNumber = new HashMap<String, Integer>();
		
        //================================================
        //data collection for filling the WeekLog from each newLog items
        //================================================		
    	//The entrySet() method is used to return a Set view of the mappings contained in this map. The set's iterator returns the entries in ascending key order.
        for(Map.Entry<Date, String> item : newLog.entrySet()){
        	//if the current date from newLog enters the framework of a two-week period
    		if (item.getKey().before(FinishDateOf2WeekPeriod)){

            //extraction string of transactions list by current date from newLog with addition "," at the end of the line
            String transactions = ","+item.getValue()+",";
            //search for matches in the ID list, count the amount of matches, delete the counted ID
            IDsCounter.id_Search_Count_Delete(transactions, IDsNumber);																					

            //if the current date from newLog does not enter the framework of a two-week period        		
        	} else {
                //================================================
                //filling the WeekLog; StartDateOf2WeekPeriod, FinishDateOf2WeekPeriod and map IDsNumber REdetermination for a NEW two-week period
                //================================================     
        		//until the current date from newLog enters the framework of a new two-week period keep EMPTY map IDsNumber
        		do {
        			//add current start date of a two-week period and IDsNumber to the map 
        			WeekLog.put(StartDateOf2WeekPeriod, IDsNumber);
        			//save start date of a NEW two-week period
	            	StartDateOf2WeekPeriod=FinishDateOf2WeekPeriod;
	            	//date offset for 14 days ahead
	        		calendar.add(Calendar.DAY_OF_MONTH, 14);
	        		//save NEW finish date of a two-week period
	        		FinishDateOf2WeekPeriod = calendar.getTime();
	            	//create NEW map: ID name; number of transactions with this name in two weeks
	        		IDsNumber = new HashMap<String, Integer>();
	        		} while (!item.getKey().before(FinishDateOf2WeekPeriod));
        		
                //extraction string of transactions list by current date from newLog with addition "," at the end of the line
                String transactions = ","+item.getValue()+",";
                //search for matches in the ID list, count the amount of matches, delete the counted ID
                IDsCounter.id_Search_Count_Delete(transactions, IDsNumber);		
    			//add current start date of a two-week period and IDsNumber to the map 
    			WeekLog.put(StartDateOf2WeekPeriod, IDsNumber);

        	}     	
        }
        
//========================================================================================================        
        
        
       try {
        BufferedWriter writerAllLog;
        //BufferedWriter - class of record of a stream, Constructs a FileWriter object given a file name with a boolean indicating whether or not to append the data written.
        writerAllLog = new BufferedWriter(new FileWriter(toForAll));					
        //The entrySet() method is used to return a Set view of the mappings contained in this map. The set's iterator returns the entries in ascending key order.
        for(Map.Entry<Date, String> item : newLog.entrySet()){					 
        	//System.getProperty("line.separator") returns the OS dependent line separator
        	writerAllLog.write(formatLog.format(item.getKey())+" : "+ item.getValue() + System.getProperty("line.separator")); 
        }
        writerAllLog.close();
   	} catch (IOException e) {
		e.printStackTrace();
	}
       
       System.out.println("==================================================");
       System.out.println("log.txt is created");       

       try {       
        BufferedWriter writerByWeek;
        writerByWeek = new BufferedWriter(new FileWriter(toForWeek));					
        //The entrySet() method is used to return a Set view of the mappings contained in this map. The set's iterator returns the entries in ascending key order.
        for(Map.Entry<Date, HashMap<String, Integer>> item : WeekLog.entrySet()){
            
        	calendar = Calendar.getInstance();
            //save start date of a two-week period
        	StartDateOf2WeekPeriod = item.getKey();
            calendar.setTime(StartDateOf2WeekPeriod);
            //date offset for 13 days ahead
            calendar.add(Calendar.DAY_OF_MONTH, 13);
            //save finish date of a two-week period
            FinishDateOf2WeekPeriod = calendar.getTime();
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
       System.out.println("Proces is done! Week_log.txt is created");        
    }
}