package app;

import java.util.Scanner;


<<<<<<< HEAD
import Queries.WriteQueries;
import Export.ExportModule;

=======
>>>>>>> develop
public class Input {

	public void getInput() throws Exception {
		String input = "";
		Scanner object = new Scanner(System.in);

		while (true) {

			try {
				System.out.println("1. Write Queries 2. Export 3. Data Model 4. Analytics 5. Exit");
				System.out.println("Enter the number of the operation you would like to perform: ");
				try {
					input = object.nextLine();
				} catch (Exception e1) {
				}

				Integer number = 0;
				try {
					number = Integer.valueOf(input);
				} catch (NumberFormatException e) {
					System.out.println("Please try again. Zero is not a valid option");
					continue;
				}

				if (number == 5) {
					System.out.println("Exit done!!!");
					break;
				}

				switch (number) {
				case 1:
					String input1 = "";
					while (true) {
						System.out.println("\nWrite your Query:");
						input1 = object.nextLine();
						while (!input1.substring(input1.length() - 1).equalsIgnoreCase(";")) {
							System.out.println("Missing Semicolon");
							System.out.println("Write your query again:");
							input1 = object.nextLine();
						}
						if (input1.equalsIgnoreCase("exit;")) {
							System.out.println("Exit done!!");
							break;
						} else {
							WriteQueries writequery = new WriteQueries();
							writequery.Write_Queries(input1);
						}
					}
					break;

				case 2:
					while (true) {
						System.out.println("[1] With Values \n[2] Without Values \n[3] Exit");
						System.out.print(">> ");
						
						ExportModule exportModel = new ExportModule();
						switch (object.nextLine()) {
						case "1":
							System.out.println("\nWrite the database name!");
							System.out.print(">> ");
							exportModel.ExportWithValues(object.nextLine().toLowerCase());
							break;
						case "2":
							System.out.println("\nWrite the database name!");
							System.out.print(">> ");
							exportModel.ExportWithoutValues(object.nextLine().toLowerCase());
							break;
						case "3":
							System.out.print("System Closed !");
							System.exit(0);
							break;
						default:
							System.out.println("\nUnknown Input. Please try again!\n");
							break;
						}
					}
				default:
					System.out.println(" Please try again!\n");
					break;
				}
			} catch (Exception e) {
				System.out.println(" Please try again!\n");
			}

		}

		object.close();

	}

}
