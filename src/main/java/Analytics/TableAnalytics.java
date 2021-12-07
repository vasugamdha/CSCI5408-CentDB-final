package Analytics;

import Queries.WriteQueries;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TableAnalytics {

    //HomePage homePage = new HomePage();

    public void tableAnalysis(String userInput) throws FileNotFoundException {

        String query = userInput.split(" ")[1];
        String databaseDB = userInput.split(" ")[2];

        File file = new File("queryLogs.txt");
        Scanner sc;

        WriteQueries writeQueries = new WriteQueries();

        //to be included when everything's integrated
        //ArrayList<String> listOfTableInDb = writeQueries.returnListOfTables(databaseDB);


        ArrayList<String> listOfTableInDb = new ArrayList<>(){{
            add("Employee");
            add("Department");
        }};

        for(String table : listOfTableInDb){
            int count = 0;
            sc = new Scanner(file);
            while(sc.hasNextLine()){
                String lineInFile = sc.nextLine();
                if(lineInFile.contains(databaseDB) && lineInFile.contains(query) && lineInFile.contains(table)){
                    count++;
                }
            }
            System.out.println("Total "+count+" "+query+" operations are performed on "+table);
        }
    }
}
