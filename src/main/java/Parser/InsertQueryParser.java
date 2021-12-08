package Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Database.Database;
import Executor.ExecuteInsertTable;
import LogManagement.LogController;
import LogManagement.LogType;
import LogManagement.Status;
import login.Constants;
import org.json.JSONObject;


public class InsertQueryParser {

	private String inputQuery;
	private List<String> query;

	LogController lc = new LogController();
	JSONObject logEntry = new JSONObject();
	StringBuilder sb = new StringBuilder();
	long start;

	public InsertQueryParser(String inputQuery, List<String> query) {
		super();
		this.inputQuery = inputQuery;
		this.query = query;
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

			if (!keyword1.equalsIgnoreCase("into")) {
				System.err.println("Invalid keyword in the Query!");
				throw new Exception();
			}

			String value = query.get(2);

			int index = value.indexOf("(");

			if (index < 0) {
				index = value.length();
			}

			String tableName = value.substring(0, index).strip();

			int i = inputQuery.indexOf("(");
			int j = inputQuery.indexOf(")");

			if (index == -1 || j == -1) {
				System.err.println("Invalid Query syntax! Try again!");
				throw new Exception();
			}

			String Columnfields = inputQuery.substring(i + 1, j).strip();

			String values = inputQuery.substring(j + 1);

			i = values.indexOf("(");
			j = values.indexOf(")");

			String keyword2 = values.substring(0, i).strip();

			if (!keyword2.equalsIgnoreCase("values")) {
				System.err.println("Invalid keyword");
				throw new Exception();
			}

			values = values.substring(i + 1, j).strip();

			List<String> fieldcolumnlist = Arrays.asList(Columnfields.split(","));
			List<String> valueColumnlist = Arrays.asList(values.split(","));

			if (valueColumnlist.size() != fieldcolumnlist.size()) {
				System.err.println("Invalid Query-value and field doesnt match!");
				throw new Exception();
			}
			String Columnfield = "";
			String Columnvalue = "";

			Map<String, Object> linkfieldvalue = new LinkedHashMap<>();
			for (int q = 0; q < fieldcolumnlist.size(); q++) {
				Columnfield = fieldcolumnlist.get(q).strip();
				Columnvalue = valueColumnlist.get(q).replace("\"", "").strip();
				linkfieldvalue.put(Columnfield, Columnvalue);
			}

			ExecuteInsertTable executeinserttable = new ExecuteInsertTable(tableName, linkfieldvalue);
			executeinserttable.InsertIntoTable(db);

			log(inputQuery,Status.SUCCESSFUL,System.currentTimeMillis()-start);
		} catch (Exception e) {
			log(inputQuery,Status.ERROR,System.currentTimeMillis()-start);
		}
	}

}
