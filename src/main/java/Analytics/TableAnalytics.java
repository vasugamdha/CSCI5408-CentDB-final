package Analytics;

import Queries.WriteQueries;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TableAnalytics implements ITableAnalytics{

    //HomePage homePage = new HomePage();

    public void tableAnalysis(String userInput) throws Exception {

        String query = userInput.split(" ")[1];
        String databaseDB = userInput.split(" ")[2];

        File file = new File("queryLogs.txt");
        Scanner sc;

        WriteQueries writeQueries = new WriteQueries();

        List<String> listOfTableInDb = writeQueries.returnListOfTables(databaseDB);

        for(String table : listOfTableInDb){
            int count = 0;
            sc = new Scanner(file);
            while(sc.hasNextLine()){
                String lineInFile = sc.nextLine();
                if(lineInFile.contains(databaseDB) && lineInFile.contains(query) && lineInFile.contains(table) && lineInFile.contains("SUCCESSFUL")){
                    count++;
                }
            }
            System.out.println("Total "+count+" "+query+" operations are performed on "+table);
        }
    }
}
