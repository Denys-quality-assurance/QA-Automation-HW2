import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * class IDsCounter
 * version 1.01
 * search for matches in the ID list, count the amount of matches, delete the counted ID
 * author Denys Matolikov
 */

public class IDsCounter {

	//search for matches in the ID list, count the amount of matches, delete the counted ID
	public static void id_Search_Count_Delete(String transactions, HashMap<String, Integer> IDsNumber) {																				
    	
		//until all IDs have been deleted from the transaction list 
		while (transactions.length()>1){ 
			//pattern to search the name of the first ID in a string 
			Pattern nameSearch = Pattern.compile("^,(.*?),");
			//Matcher object match string of IDs against the pattern
			Matcher matcher = nameSearch.matcher(transactions);			
			//search of match
			matcher.find();
			//idName returns the input subsequence matched by the previous match
			String idName = matcher.group(1);
			//pattern to search in a string is the name of the first ID
			nameSearch = Pattern.compile(idName);
			//Matcher object match string of IDs against the name of the first ID
			matcher = nameSearch.matcher(transactions);
			//count - number of matches in the current transactions list
			int count = 0;
				
				//counting matches in the ID list: while matches are found
				while (matcher.find()){
					count +=1;
					} 
				
		    	//for each of the items in the map IDsNumber
				//counting the amount of matches, taking into account the repetition of the current ID in the map IDsNumber
				for(Map.Entry<String, Integer> item1 : IDsNumber.entrySet()){		 
                 	//if such idName is already brought in map
                    if (item1.getKey().equals(idName)) {			 
                 		//add new value
                 		count=(item1.getValue()+count);		
                 		break;
                 		}
                 	}
             
            //add current idName and number of transactions to the map IDsNumber 
            IDsNumber.put(idName, count);
			//Matcher object match string of IDs against the name of the first ID            
            matcher = nameSearch.matcher(transactions);
			//replacement of ",idName," by a comma "," in transactions list: while matches are found            
            while (matcher.find()){
	            transactions=transactions.replaceAll(","+idName+",",",");
            }
		}           
	}	
}
