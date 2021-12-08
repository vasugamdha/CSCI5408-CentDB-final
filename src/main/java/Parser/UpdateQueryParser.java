package Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import ConstantFileValues.FileConstants;
import Database.Database;
import Executor.ExecuteUpdateColumn;
import LogManagement.LogController;
import LogManagement.LogType;
import LogManagement.Status;
import login.Constants;
import org.json.JSONObject;


public class UpdateQueryParser {

	private String inputQuery;
	private List<String> query;
	LogController lc = new LogController();
	JSONObject logEntry = new JSONObject();
	StringBuilder sb = new StringBuilder();
	long start;

	public UpdateQueryParser(String inputQuery, List<String> query) {
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
			Path path = Paths.get(FileConstants.FilePath, db.getDatabase());

			if (!Files.exists(path)) {
				System.err.println("Database does not exist!");
				throw new Exception();
			}

			String tablename = query.get(1);

			String keyword1 = query.get(2);

			if (!keyword1.equalsIgnoreCase("set")) {
				System.err.println("Keyword missing or Invalid keyword in the Query!");
				throw new Exception();
			}

			String value1 = query.get(3);
			int index1 = value1.indexOf("=");
			int index2 = inputQuery.indexOf("'");

			if (index1 < 0) {
				index1 = value1.length();
			}

			String Columnname = value1.substring(0, index1).strip();
			value1 = inputQuery.substring(index2);
		

			int index3 = value1.indexOf("'", 2);
			String Columnvalue = value1.substring(0, index3 + 1);
			

			int index5 = inputQuery.indexOf("where");
			String value2 = inputQuery.substring(index5);
		

			String[] splittedquery = value2.split("\\s");
			List<String> query1 = Arrays.asList(splittedquery);

			String keyword2 = query1.get(0);
		
			if (!keyword2.equalsIgnoreCase("where")) {
				System.err.println("Invalid keyword in the Query!");
				throw new Exception();
			}

			value1 = query1.get(1);
			int index6 = value1.indexOf("=");

			if (index6 < 0) {
				index6 = value1.length();
			}
			String conditionColumnName = value1.substring(0, index6).strip();
		

			int index7 = value2.indexOf("'");

			int index8 = value2.indexOf(";");

			String conditionColumnValue = value2.substring(index7, index8);
			

			ExecuteUpdateColumn executeupdatecolumn = new ExecuteUpdateColumn(tablename, Columnname, Columnvalue,
					conditionColumnName, conditionColumnValue);
			executeupdatecolumn.UpdateTable(db);

			log(inputQuery,Status.SUCCESSFUL,System.currentTimeMillis()-start);
		} catch (Exception e) {
			log(inputQuery,Status.ERROR,System.currentTimeMillis()-start);
		}

	}

}
