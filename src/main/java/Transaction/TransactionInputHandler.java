package Transaction;

import java.util.Scanner;

public class TransactionInputHandler {
	
	public static void handleTransactionInput(Scanner object, String input) {
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
				break;
			}
			
			if (input.equals("exit")) {
				System.out.println("exiting");
				break;
			}
			
			if (input.equals("rollback")) {
				transactionParser.rollback();
				System.out.println("All queries rolled back");
			} 
			
			else if (input.startsWith("rollback to")) {
				String[] words = input.split(" ");
				String savepointName = words[words.length - 1];
				transactionParser.rollbackToSavepoint(savepointName);
				System.out.println("Roll back to " + savepointName);
			} 
			
			else if (input.startsWith("savepoint")) {
				String[] words = input.split(" ");
				String savepointName = words[words.length - 1];
				transactionParser.savepoint(savepointName);
				System.out.println("Add savepoint " + savepointName);
			} 
			
			else {
				transactionParser.addQuery(input);
			}
			
		}
	}
}
