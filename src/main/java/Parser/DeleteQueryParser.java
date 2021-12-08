package Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import Database.Database;
import Executor.ExecuteDeleteRow;
import LogManagement.LogController;
import LogManagement.LogType;
import LogManagement.Status;
import login.Constants;
import org.json.JSONObject;


public class DeleteQueryParser {
	private List<String> query;
	private String inputQuery;

	LogController lc = new LogController();
	JSONObject logEntry = new JSONObject();
	long start;
	StringBuilder sb = new StringBuilder();

	public DeleteQueryParser(String inputQuery, List<String> query) {
		super();
		this.query = query;
		this.inputQuery=inputQuery;
	}

	private void log(String query, Status status, long executionTime){
		logEntry.put("Status", status);
		logEntry.put("Execution time (in ms)",executionTime);
		lc.log(LogType.QUERY, logEntry);
		lc.log(LogType.GENERAL, logEntry);

		sb.append(String.format("Status: %s ### ", status));
		sb.append(String.format("Query: %s\n", query));
		try {
			Files.write(Path.of("queryLogs.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parse(Database db) throws Exception {

		start = System.currentTimeMillis();
		logEntry.put("User", Constants.userid);
		logEntry.put("Query", inputQuery);
		logEntry.put("Database", db.getDatabase());

		sb.append(String.format("User: %s ### ", Constants.userid));
		sb.append(String.format("Database: %s ### ", db.getDatabase()));

		try {

			String keyword1 = query.get(1);
		
			if (!keyword1.equalsIgnoreCase("from")) {
				System.err.println("Invalid keyword in the Query!");
				throw new Exception();
			}

			String tablename = query.get(2);
		

			String keyword2 = query.get(3);
		
			
			if (!keyword2.equalsIgnoreCase("where")) {
				System.err.println("Invalid keyword in the Query!");
				throw new Exception();
			}

			String value = query.get(4);

			int index1 = value.indexOf("=");
			int index2 = inputQuery.indexOf("'");
			int index3 = inputQuery.indexOf(";");
			
			if (index1 < 0) {
				index1 = value.length();
			}

			String conditionColumnName = value.substring(0, index1).strip();
			
			String conditionColumnValue = inputQuery.substring(index2, index3);
			

			ExecuteDeleteRow executedeleterow = new ExecuteDeleteRow(tablename,conditionColumnName,conditionColumnValue);
			executedeleterow.deletefromTable(db);

			log(inputQuery,Status.SUCCESSFUL,System.currentTimeMillis()-start);
		} catch (Exception e) {
			log(inputQuery,Status.ERROR,System.currentTimeMillis()-start);
		}
	}
}
