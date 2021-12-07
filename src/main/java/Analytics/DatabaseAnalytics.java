package Analytics;

import registration.HomePage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class DatabaseAnalytics {

    //HomePage homePage = new HomePage();

    public void databaseAnalysis(String userInput) throws FileNotFoundException {

        String databaseDB = userInput.split(" ")[2];

        File file = new File("queryLogs.txt");
        Scanner sc = new Scanner(file);
        HashMap<String, Integer> users = new HashMap<String, Integer>();
        HashSet<String> usersList = new HashSet<String>();

        while (sc.hasNextLine()) {
            String lineInFile = sc.nextLine();
            usersList.add(lineInFile.split(" ")[1]);
        }

        for (String user : usersList) {
            int count = 0;
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String lineInFile = sc.nextLine();
                if (lineInFile.contains(user) && lineInFile.contains(databaseDB)) {
                    count++;
                    users.put(user, count);
                }
            }
        }
        for (String object: users.keySet()) {
            String userId = object.toString();
            String queriesCount = users.get(object).toString();
            System.out.println("user "+userId + " submitted " + queriesCount+" queries on "+databaseDB);
        }

    }


}



