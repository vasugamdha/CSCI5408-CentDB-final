package Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import Database.Database;
import Executor.ExecuteDropTable;
import LogManagement.LogController;
import LogManagement.LogType;
import LogManagement.Status;
import login.Constants;
import org.json.JSONObject;


public class DropQueryParser {

	LogController lc = new LogController();
	JSONObject logEntry = new JSONObject();
	StringBuilder sb = new StringBuilder();
	long start;

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


	public void droptable(String inputQuery, List<String> query, Database db) throws Exception {

		start = System.currentTimeMillis();
		logEntry.put("User", Constants.userid);
		logEntry.put("Query", inputQuery);
		logEntry.put("Database", db.getDatabase());

		sb.append(String.format("User: %s ### ", Constants.userid));
		sb.append(String.format("Database: %s ### ", db.getDatabase()));

		try {
			if (query.get(1).equalsIgnoreCase("table")) {

				String table_Name = query.get(2);
				table_Name = table_Name.replace(";", "");
				ExecuteDropTable executedroptable = new ExecuteDropTable(table_Name, db);
				executedroptable.DropTable(db);
				log(inputQuery,Status.SUCCESSFUL,System.currentTimeMillis()-start);

			} else {
				System.out.println("Invalid Keywords!!");
				throw new Exception();
			}

		} catch (Exception e) {
			log(inputQuery,Status.ERROR,System.currentTimeMillis()-start);
		}
	}

}
