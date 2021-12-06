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
import org.json.JSONObject;


public class DeleteQueryParser {
	private List<String> query;
	private String inputQuery;

	LogController lc = new LogController();/////
	JSONObject logEntry = new JSONObject();/////
	long start;/////
	StringBuilder sb = new StringBuilder();

	public DeleteQueryParser(String inputQuery, List<String> query) {
		super();
		this.query = query;
		this.inputQuery=inputQuery;
	}

	public void parse(Database db) {

		start = System.currentTimeMillis();/////
		logEntry.put("Query", inputQuery);/////
		logEntry.put("Database", db.getDatabase());/////

		sb.append(String.format("User: %s ### ", "userid"));
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
