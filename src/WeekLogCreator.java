import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/*
 * class WeekLogCreator
 * counting the number of transactions for two-week periods, creating TreeMap<Date, HashMap<String, Integer>> WeekLog
 * 
 * author Denys Matolikov
 */

public class WeekLogCreator {

	public static TreeMap<Date, HashMap<String, Integer>> creation(TreeMap<Date, String> newLog){	
		//preparation of the calendar, StartDateOf2WeekPeriod and FinishDateOf2WeekPeriod determination
		//create calendar and set minimal date from newLog
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Collections.min(newLog.keySet()));
		//save start date of a two-week period
		Date StartDateOf2WeekPeriod = calendar.getTime();
		//date offset for 14 days ahead
		calendar.add(Calendar.DAY_OF_MONTH, 14);
		//save finish date of a two-week period
		Date FinishDateOf2WeekPeriod = calendar.getTime();
		//preparation of the maps
		//create  map: start date of a two-week period; ID name; number of transactions with this name in two weeks
		TreeMap<Date, HashMap<String, Integer>> WeekLog = new TreeMap<Date, HashMap<String, Integer>>();		
		//create  map: ID name; number of transactions with this name in two weeks
		HashMap<String, Integer> IDsNumber = new HashMap<String, Integer>();
	

			//data collection for filling the WeekLog from each newLog items
			//The entrySet() method is used to return a Set view of the mappings contained in this map. The set's iterator returns the entries in ascending key order.
		  	for(Map.Entry<Date, String> item : newLog.entrySet()){
		  	//if the current date from newLog enters the framework of a two-week period
				if (item.getKey().before(FinishDateOf2WeekPeriod)){
					//extraction string of transactions list by current date from newLog with addition "," at the end of the line
					String transactions = ","+item.getValue()+",";
					//searching for matches in the ID list, count the amount of matches, delete the counted ID
					IDsCounter.id_Search_Count_Delete(transactions, IDsNumber);																					
					//if the current date from newLog does not enter the framework of a two-week period        		
				} else {
						//filling the WeekLog; StartDateOf2WeekPeriod, FinishDateOf2WeekPeriod and map IDsNumber REdetermination for a NEW two-week period
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
						//searching for matches in the ID list, count the amount of matches, delete the counted ID
						IDsCounter.id_Search_Count_Delete(transactions, IDsNumber);		
						//add current start date of a two-week period and IDsNumber to the map 
						WeekLog.put(StartDateOf2WeekPeriod, IDsNumber);
					}     	
		  	}
	return WeekLog;
	}
}
