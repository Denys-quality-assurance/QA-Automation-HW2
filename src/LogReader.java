import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/*
 * class LogReader
 * reading files from the folder and creating TreeMap<Date, String> newLog  
 * 
 * authors Vladyslav Petrov, Denys Matolikov
 */

public class LogReader {
	
	//reading files and creating map newLog   	
	public static TreeMap<Date, String> readingFiles_creatingMap(String from, DateFormat formatLog) {
            	
    	//add the source folder
    	File dir = new File(from);														
    	//create a list of files in the source folder
    	File[] filesInDir = dir.listFiles();   	
    	//transaction date
    	Date date;	
    	//line with a list of transaction IDs
    	String logID = null;
    	//create an ordered map
    	TreeMap<Date, String> newLog = new TreeMap<Date, String>();	
    	//total number of files and folders in the directory
    	int numberOfFiles = filesInDir.length;
    	//number of the file being processed
    	int fileNumber = 1;
    	//regex for searching data and separator in string
    	RegexStrings regexStrings;
    	
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
		                	//line - target for regex searching
		                	regexStrings = new RegexStrings(line);
		                	if (line.contains(":  ")){   
		                    		//use regex to grab data from the line
		                			date = formatLog.parse(regexStrings.comparedDate);				
		                    		//comparedSeparator returns all the data after semicolon and two whitespaces, and remove a new line character
		                			logID = regexStrings.comparedSeparator.replaceAll(System.getProperty("line.separator"),"");
		                             	
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
	return newLog;
	}
}
