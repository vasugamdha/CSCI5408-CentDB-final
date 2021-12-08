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
import login.Constants;
import org.json.JSONObject;

public class CreateQueryParser {

	LogController lc = new LogController();
	JSONObject logEntry = new JSONObject();
	StringBuilder sb = new StringBuilder();
	long start;

	String DB_Name;

	private void log(String query, String DB_Name, Status status, long executionTime){
		logEntry.put("Database", DB_Name);
		logEntry.put("Status", status);
		logEntry.put("Execution time (in ms)",executionTime);
		lc.log(LogType.QUERY, logEntry);

		sb.append(String.format("Database: %s ### ", DB_Name));
		sb.append(String.format("Status: %s ### ", status));
		sb.append(String.format("Query: %s\n", query));
		try {
			Files.write(Path.of("queryLogs.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createquery(String inputQuery, List<String> query, Database db) throws Exception {

		start = System.currentTimeMillis();
		logEntry.put("User", Constants.userid);
		logEntry.put("Query", inputQuery);

		sb.append(String.format("User: %s ### ", Constants.userid));

		try {
			if (query.get(1).equalsIgnoreCase("database")) {

				DB_Name = query.get(2);
				DB_Name = DB_Name.replace(";", "");
				ExecuteCreateDatabase executecreateDb = new ExecuteCreateDatabase(DB_Name, inputQuery);
				executecreateDb.executeCreateDb();
				log(inputQuery,db.getDatabase(), Status.SUCCESSFUL, System.currentTimeMillis()-start);
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
				log(inputQuery,db.getDatabase(), Status.SUCCESSFUL, System.currentTimeMillis()-start);
			} else {
				System.err.println("Invalid Keyword!!");
				log(inputQuery,db.getDatabase(), Status.ERROR, System.currentTimeMillis()-start);
			}

		} catch (Exception e) {
			System.err.println("Check your Syntax");
			log(inputQuery,db.getDatabase(), Status.ERROR, System.currentTimeMillis()-start);
		}
	}
}
