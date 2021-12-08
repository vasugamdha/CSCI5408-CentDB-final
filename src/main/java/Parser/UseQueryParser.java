package Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import ConstantFileValues.FileConstants;
import Database.Database;
import LogManagement.LogController;
import LogManagement.LogType;
import LogManagement.Status;
import login.Constants;
import org.json.JSONObject;


public class UseQueryParser {

	private String inputQuery;
	private List<String> query;

	LogController lc = new LogController();
	JSONObject logEntry = new JSONObject();
	StringBuilder sb = new StringBuilder();
	long start;

	String DB_Name = null;

	public UseQueryParser(String inputQuery, List<String> query) {
		super();
		this.inputQuery = inputQuery;
		this.query = query;
	}

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
//			e.printStackTrace();
		}
	}

	public Database parse(Database db) throws Exception {

		start = System.currentTimeMillis();
		logEntry.put("User", Constants.userid);
		logEntry.put("Query", inputQuery);

		sb.append(String.format("User: %s ### ", Constants.userid));

		try {
			DB_Name = query.get(1);
			DB_Name = DB_Name.replace(";", "");
			Path path_of_file = Paths.get(FileConstants.FilePath, DB_Name);
			if (!Files.exists(path_of_file)) {
				System.err.println("Database doesn't exist!");
				log(inputQuery,db.getDatabase(), Status.ERROR, System.currentTimeMillis()-start);
				return db;
			} else {
				System.out.println("You are in" + "\t" + DB_Name + "\t" + "database");
				try {
					log(inputQuery, db.getDatabase(), Status.SUCCESSFUL, System.currentTimeMillis() - start);
				}catch (Exception e) {}
			}
			db = new Database(DB_Name);
			return db;

		} catch (Exception e){
			try {
				log(inputQuery,db.getDatabase(), Status.ERROR, System.currentTimeMillis()-start);
			}catch (Exception ex) {}
		}
		return db;
	}
}
