package Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Database.Database;
import Executor.ExecuteSelectFromTable;
import LogManagement.LogController;
import LogManagement.LogType;
import LogManagement.Status;
import login.Constants;
import org.json.JSONObject;


public class SelectQueryParser {
	private String inputQuery;
	private List<String> query;

	LogController lc = new LogController();
	JSONObject logEntry = new JSONObject();
	StringBuilder sb = new StringBuilder();
	long start;

	public SelectQueryParser(String inputQuery, List<String> query) {
		super();
		this.inputQuery = inputQuery;
		this.query = query;
	}

	private void log(String query, Status status, long executionTime){
		logEntry.put("Status", status);
		logEntry.put("Execution time (in ms)",executionTime);
		lc.log(LogType.QUERY, logEntry);

		sb.append(String.format("Status: %s ### ", status));
		sb.append(String.format("Query: %s\n", query));
		try {
			Files.write(Path.of("queryLogs.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parse(Database db) {

		start = System.currentTimeMillis();
		logEntry.put("User", Constants.userid);
		logEntry.put("Query", inputQuery);
		logEntry.put("Database", db.getDatabase());

		sb.append(String.format("User: %s ### ", Constants.userid));
		sb.append(String.format("Database: %s ### ", db.getDatabase()));

		try {

			String TableName = null;
			List<String> columnFieldsList = new ArrayList<>();
			String conditionColumnName = null;
			String conditionColumnValue = null;

			String keyword1 = query.get(1);

			if (keyword1.equalsIgnoreCase("*")) {

				String keyword2 = query.get(2);

				if (keyword2.equalsIgnoreCase("from")) {

					TableName = query.get(3);
					TableName = TableName.replace(";", "");

				} else {
					System.err.println("Invalid keyword in the Query!");
					throw new Exception();
				}

			} else {
				String Columnfields = query.get(1);
				columnFieldsList = Arrays.asList(Columnfields.split(","));

				String keyword2 = query.get(2);
				if (keyword2.equalsIgnoreCase("from")) {

					TableName = query.get(3);
					TableName = TableName.replace(";", "");
				}

				String keyword3 = query.get(4);

				if (keyword3.equals("where")) {

					String value = query.get(5);
					int i = value.indexOf("=");
					int j = inputQuery.indexOf("'");
					int k = inputQuery.indexOf(";");

					if (i < 0) {
						i = value.length();
					}

					conditionColumnName = value.substring(0, i).strip();
					
				
					conditionColumnValue = inputQuery.substring(j, k).strip();

				} else {
					System.err.println("Invalid keyword in the Query!");
					throw new Exception();
				}
			}

			ExecuteSelectFromTable executeselecttable = new ExecuteSelectFromTable(TableName, columnFieldsList,
					conditionColumnName, conditionColumnValue, query);
			executeselecttable.SelectTable(db);

			log(inputQuery,Status.SUCCESSFUL,System.currentTimeMillis()-start);
		} catch (Exception e) {
			log(inputQuery,Status.ERROR,System.currentTimeMillis()-start);
		}
	}
}
