package Analytics;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnalyticsInput {

    public void analytics() throws FileNotFoundException {
    //public static void main(String args[]) throws FileNotFoundException {

        System.out.println("Enter query for analytics:");
        Scanner sc1 = new Scanner(System.in);
        String userInput = sc1.nextLine();
        DatabaseAnalytics databaseAnalytics = new DatabaseAnalytics();
        TableAnalytics tableAnalytics = new TableAnalytics();

        String query = userInput.split(" ")[1];

        if(query.equals("queries")){
            databaseAnalytics.databaseAnalysis(userInput);
        }
        else{
            tableAnalytics.tableAnalysis(userInput);
        }

    }
}
