import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * class RegexStrings
 * Search data and separator in string  
 * 
 * author Vladyslav Petrov
 */

public class RegexStrings {
	//Search data in string: "Aaa<Tab><Tab>1000:00:01"
    String dateRegex = "^[A-Z][a-z]{2}[ \\t]{2}[0-9]{1,2}\\s+\\d{2}:\\d{2}:\\d{2}";
    //Search separator in string: ":< >< >......"
    String logIdSeparator = ":[ \\s]{2}[^\\n]+";
    String comparedDate;
    String comparedSeparator;

    public RegexStrings(String line){
        Pattern pattern1 = Pattern.compile(dateRegex);
        Matcher matcher1 = pattern1.matcher(line);

        Pattern pattern2 = Pattern.compile(logIdSeparator);
        Matcher matcher2 = pattern2.matcher(line);

        // check all occurance
        while (matcher1.find()){
            comparedDate = matcher1.group();
        }

        while (matcher2.find()){
            comparedSeparator = matcher2.group();
            comparedSeparator = comparedSeparator.replaceAll(":[ \\s]{2}", "");

        }
    }
}
