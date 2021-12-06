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
import org.json.JSONObject;


public class SelectQueryParser {

	LogController lc = new LogController();/////
	JSONObject logEntry = new JSONObject();/////
	StringBuilder sb = new StringBuilder();
	long start;/////

	private String inputQuery;
	private List<String> query;

	public SelectQueryParser(String inputQuery, List<String> query) {
		super();
		this.inputQuery = inputQuery;
		this.query = query;
	}

	public void parse(Database db) {

		start = System.currentTimeMillis();/////
		logEntry.put("Query", inputQuery);/////
		logEntry.put("Database", db.getDatabase());/////

		sb.append(String.format("User: %s ### ", "userid"));
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

			logEntry.put("Status", Status.SUCCESSFUL);/////
			logEntry.put("Execution time",System.currentTimeMillis()-start);/////
			lc.log(LogType.QUERY, logEntry);/////

			sb.append(String.format("Status: %s ### ",Status.SUCCESSFUL));
			sb.append(String.format("Query: %s\n", inputQuery));
			Files.write(Path.of("bin/analytics/logs.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

		} catch (Exception e) {

			logEntry.put("Status",Status.ERROR);/////
			logEntry.put("Execution time",System.currentTimeMillis()-start);/////
			lc.log(LogType.QUERY, logEntry);/////

			sb.append(String.format("Status: %s ### ",Status.ERROR));
			sb.append(String.format("Query: %s\n", inputQuery));
			try {
				Files.write(Path.of("bin/analytics/logs.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	}
}
