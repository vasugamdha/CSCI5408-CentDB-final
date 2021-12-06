package Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.Database;
import Executor.ExecuteCreateDatabase;
import Executor.ExecuteCreateTable;
import LogManagement.LogController;
import LogManagement.LogType;
import LogManagement.Status;
import org.json.JSONObject;

public class CreateQueryParser {

	LogController lc = new LogController();/////
	JSONObject logEntry = new JSONObject();/////
	StringBuilder sb = new StringBuilder();
	long start;/////

	String DB_Name;

	public void createquery(String inputQuery, List<String> query, Database db) {

		start = System.currentTimeMillis();/////
		logEntry.put("Query", inputQuery);/////

		sb.append(String.format("User: %s ### ", "userid"));

		try {
			if (query.get(1).equalsIgnoreCase("database")) {

				DB_Name = query.get(2);
				DB_Name = DB_Name.replace(";", "");
				ExecuteCreateDatabase executecreateDb = new ExecuteCreateDatabase(DB_Name, inputQuery);
				executecreateDb.executeCreateDb();

				logEntry.put("Database", DB_Name);/////
				logEntry.put("Status",Status.SUCCESSFUL);/////
				logEntry.put("Execution time",System.currentTimeMillis()-start);/////
				lc.log(LogType.QUERY, logEntry);/////

				sb.append(String.format("Database: %s ### ", DB_Name));
				sb.append(String.format("Status: %s ### ",Status.SUCCESSFUL));
				sb.append(String.format("Query: %s\n", inputQuery));
				Files.write(Path.of("bin/analytics/logs.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

			} else if (query.get(1).equalsIgnoreCase("table")) {

				String value = query.get(2);
				int index = value.indexOf("(");
				if (index < 0) {
					index = value.length();
				}
				String tableName = value.substring(0, index).strip();

				int i = inputQuery.indexOf("(");
				String properties = inputQuery.substring(i + 1, inputQuery.length() - 2);

				List<String> columns = Arrays.asList(properties.split(","));

				Map<String, String> Map = new HashMap<>();
				for (String str : columns) {
					str = str.trim();
					List<String> individualColumns = Arrays.asList(str.split("\\s+"));
					Map.put(individualColumns.get(0), individualColumns.get(1));
				}

				ExecuteCreateTable executecreateTable = new ExecuteCreateTable(Map, tableName);
				executecreateTable.CreateTable(db);

				logEntry.put("Database", DB_Name);/////
				logEntry.put("Status",Status.SUCCESSFUL);/////
				logEntry.put("Execution time",System.currentTimeMillis()-start);/////
				lc.log(LogType.QUERY, logEntry);/////

				sb.append(String.format("Database: %s ### ", DB_Name));
				sb.append(String.format("Status: %s ### ",Status.SUCCESSFUL));
				sb.append(String.format("Query: %s\n", inputQuery));
				Files.write(Path.of("bin/analytics/logs.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

			} else {
				logEntry.put("Status",Status.ERROR);/////
				logEntry.put("Execution time",System.currentTimeMillis()-start);/////
				lc.log(LogType.QUERY, logEntry);/////

				sb.append(String.format("Status: %s ### ",Status.ERROR));
				sb.append(String.format("Query: %s\n", inputQuery));
				Files.write(Path.of("bin/analytics/logs.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

				System.err.println("Invalid Keyword!!");
			}

		} catch (Exception e) {

			logEntry.put("Status",Status.ERROR);/////
			logEntry.put("Execution time",System.currentTimeMillis()-start);/////
			lc.log(LogType.QUERY, logEntry);/////

			sb.append(String.format("Status: %s ### ",Status.ERROR));
			sb.append(String.format("Query: %s\n", inputQuery));
			try {
				Files.write(Path.of("bin/analytics/logs.txt"), sb.toString().getBytes(),  StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			System.err.println("Check your Syntax");
		}
	}
}
