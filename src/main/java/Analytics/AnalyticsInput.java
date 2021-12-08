package Analytics;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnalyticsInput {

    public void analytics() throws Exception {

        System.out.println("Enter query for analytics:");
        Scanner sc1 = new Scanner(System.in);
        String userInput = sc1.nextLine();
        if(userInput.equals(";;")){
            throw new Exception();
        }
        IDatabaseAnalytics databaseAnalytics = new DatabaseAnalytics();
        ITableAnalytics tableAnalytics = new TableAnalytics();

        String query = userInput.split(" ")[1];

        if(query.equals("queries")){
            databaseAnalytics.databaseAnalysis(userInput);
        }
        else{
            tableAnalytics.tableAnalysis(userInput);
        }

    }
}
