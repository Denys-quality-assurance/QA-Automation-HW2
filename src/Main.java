/*
 * Program LogCollector: take patches to Logs folder, log.txt and Week_log.txt, reading files from the folder, 
 * finding all transactions IDs (writing Log.txt), counting the number of transactions for two-week periods (writing Week_Log.txt)
 * 
 * version 2.01
 * 
 * class Main
 * inserting patches to Logs folder, log.txt and Week_log.txt
 * 
 * author Denys Matolikov, Vladyslav Petrov
*/

public class Main {
    public static void main(String[] args) {

        Logger logger = new Logger();
        logger.log("Logs", "log.txt", "Week_log.txt");
    }
}
