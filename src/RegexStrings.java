import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * class RegexStrings
 * Search date and separator in string  
 * 
 * authors Vladyslav Petrov, Denys Matolikov
 */

public class RegexStrings {
	//Regex searches date in string: "Feb<Tab><Tab>04 00:00:01"
    String dateRegex = "^[A-Z][a-z]{2}[ \\s]{2}[0-9]{1,2}\\s+\\d{2}:\\d{2}:\\d{2}";
    //Regex searches separator in string: ":< >< >......"
    String logIdSeparator = ":[ \\s]{2}[^\\n]+";
    //Regex searches date with year in string: "Log time: Sun, 04 Feb 2018"
    String yearRegex = "Log time:[ \\s][A-Z][a-z]{2},[ \\s][0-9]{2}[ \\s][A-Z][a-z]{2}[ \\s]([0-9]{4})";   
    String comparedDate;
    String comparedSeparator;
    String comparedYear;

    public RegexStrings(String line){
        Pattern pattern1 = Pattern.compile(dateRegex);
        Matcher matcher1 = pattern1.matcher(line);

        Pattern pattern2 = Pattern.compile(logIdSeparator);
        Matcher matcher2 = pattern2.matcher(line);

        Pattern pattern3 = Pattern.compile(yearRegex);
        Matcher matcher3 = pattern3.matcher(line);
        
        // check all occurance
        while (matcher1.find()){
            comparedDate = matcher1.group();
        }

        while (matcher2.find()){
            comparedSeparator = matcher2.group();
            comparedSeparator = comparedSeparator.replaceAll(":[ \\s]{2}", "");
        }
        
        while (matcher3.find()){
        	comparedYear = matcher3.group();
        	comparedYear = comparedYear.replaceAll("Log time:[ \\s][A-Z][a-z]{2},[ \\s][0-9]{2}[ \\s][A-Z][a-z]{2}[ \\s]", "");
        }
        
    }
}
