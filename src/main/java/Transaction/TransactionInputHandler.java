package Transaction;

import LogManagement.LogController;
import LogManagement.LogType;
import login.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

public class TransactionInputHandler {

	static LogController lc = new LogController();
	static JSONObject logEntry = new JSONObject();
	static JSONArray queries = new JSONArray();
	static long start;

	private static void log(long executionTime, String status){
		logEntry.put("User", Constants.userid);
		logEntry.put("Status",status);
		logEntry.put("Queries",queries);
		logEntry.put("Transaction execution time (seconds)",(int) (executionTime/100));
		lc.log(LogType.EVENT, logEntry);
	}

	public static void handleTransactionInput(Scanner object, String input) {

		start = System.currentTimeMillis();
		System.out.println("Transaction begin, type exit to exit");
		TransactionParser transactionParser = new TransactionParser();

		while (true) {
			
			input = object.nextLine();
//			System.out.println("111 " + input1);
			if (input.charAt(input.length() - 1) == ';') {
				input = input.substring(0, input.length() - 1);
			}
			input = input.toLowerCase();
//			System.out.println("222 " + input1);
			
			if (input.equals("commit")) {
				transactionParser.commit();
				System.out.println("Transaction committed");
				log(System.currentTimeMillis()-start,"Transaction committed");
				break;
			}
			
			if (input.equals("exit")) {
				System.out.println("exiting");
				log(System.currentTimeMillis()-start, "Transaction Completed");
				break;
			}
			
			if (input.equals("rollback")) {
				transactionParser.rollback();
				System.out.println("All queries rolled back");
				log(System.currentTimeMillis()-start,"All queries rolled back");
			} 
			
			else if (input.startsWith("rollback to")) {
				String[] words = input.split(" ");
				String savepointName = words[words.length - 1];
				transactionParser.rollbackToSavepoint(savepointName);
				System.out.println("Roll back to " + savepointName);
				log(System.currentTimeMillis()-start,"Roll back to " + savepointName);
			}
			
			else if (input.startsWith("savepoint")) {
				String[] words = input.split(" ");
				String savepointName = words[words.length - 1];
				transactionParser.savepoint(savepointName);
				System.out.println("Add savepoint " + savepointName);
				log(System.currentTimeMillis()-start,"Added savepoint " + savepointName);
			} 
			
			else {
				queries.put(input);
				transactionParser.addQuery(input);
			}
			
		}
	}
}
