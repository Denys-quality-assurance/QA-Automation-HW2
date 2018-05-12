import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Logger {
    public void log(String from, String to) {
        File dir = new File(from);														//add the source folder
        File[] filesInDir = dir.listFiles();											//create a list of files in the source folder
        TreeMap<Date, String> newLog = new TreeMap<Date, String>();						//create an ordered map
        DateFormat format = new SimpleDateFormat("MMM  d HH:mm:ss", Locale.ENGLISH); 	//specify the date format
        String logID = null; 															//line with a list of transaction IDs
        Date date;																		//transaction date
        
        for (File file : filesInDir){													//for each of the files in the directory
        	System.out.print(file);														//output file name (for tracking the process)
            if (!file.isDirectory()){													//check whether the folder is the current file
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));	//BufferedReader - flow reading class

                    String line = null;													//line in which the line-by-line reading from the files is stored
                    while ((line = reader.readLine()) != null) {						//until found the zero line (to the end of the file)
                    		if (line.contains(":  ")){   
                    			date = format.parse(line.substring(0,15));				//translate the first 15 characters of the line to the date
                             	logID = line.substring(line.indexOf(":  ")+3).replaceAll(System.getProperty("line.separator"),"");      //from line we leave everything from the third character after ":" and remove a new line character
                             																//The Map.entrySet method returns a collection-view of the map, whose elements are of this class.
                             	for(Map.Entry<Date, String> item : newLog.entrySet()){		//entrySet() method is used to return a Set view of the mappings contained in this map. The set's iterator returns the entries in ascending key order 
                             		if (item.getKey().getTime()==date.getTime()) {			//if such date is already brought in map 
                             			logID=(item.getValue()+","+logID);					//add new value with a divider ","
                             			break;
                             		}
                             	}
                             	
                     			newLog.put(date, logID);								//add date and logID to the map  						
                               	
    	                        }
                    }
                    reader.close();

                } catch (IOException e){
                    System.err.println("Error: " +e.getMessage());
                } catch (ParseException e) {
					e.printStackTrace();
				}
                System.out.println(" - Done");
            } else {
            	System.out.println(" - Directory (not processed)");
            }
        }

       try {
        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter(to, true));					//BufferedWriter - class of record of a stream, Constructs a FileWriter object given a file name with a boolean indicating whether or not to append the data written.
        for(Map.Entry<Date, String> item : newLog.entrySet()){					//The entrySet() method is used to return a Set view of the mappings contained in this map. The set's iterator returns the entries in ascending key order. 
        writer.write(format.format(item.getKey())+" : "+ item.getValue() + System.getProperty("line.separator")); //System.getProperty("line.separator") returns the OS dependent line separator
        }			
        writer.close();
	} catch (IOException e) {
		e.printStackTrace();
	}	
       System.out.println("==================================================");
       System.out.println("Proces is done!");   
        
    }
}