package Transaction;

import java.util.Scanner;

public class TransactionInputHandler {
	
	public static void handleTransactionInput(Scanner object, String input1) {
		System.out.println("Transaction begin, type exit to exit");
		
		TransactionParser transactionParser = new TransactionParser();
		
		while (true) {
			
			input1 = object.nextLine();
//			System.out.println("111 " + input1);
			if (input1.charAt(input1.length() - 1) == ';') {
				input1 = input1.substring(0, input1.length() - 1);
			}
			input1 = input1.toLowerCase();
//			System.out.println("222 " + input1);
			
			if (input1.equals("commit")) {
				transactionParser.commit();
				System.out.println("Transaction committed");
				break;
			}
			
			if (input1.equals("exit")) {
				System.out.println("exiting");
				break;
			}
			
			if (input1.equals("rollback")) {
				transactionParser.rollback();
				System.out.println("All queries rolled back");
			} 
			
			else if (input1.startsWith("rollback to")) {
				String[] words = input1.split(" ");
				String savepointName = words[words.length - 1];
				transactionParser.rollbackToSavepoint(savepointName);
				System.out.println("Roll back to " + savepointName);
			} 
			
			else if (input1.startsWith("savepoint")) {
				String[] words = input1.split(" ");
				String savepointName = words[words.length - 1];
				transactionParser.savepoint(savepointName);
				System.out.println("Add savepoint " + savepointName);
			} 
			
			else {
				transactionParser.addQuery(input1);
			}
			
		}
	}
}
