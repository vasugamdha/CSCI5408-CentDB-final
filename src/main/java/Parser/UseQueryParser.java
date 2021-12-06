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
import org.json.JSONObject;


public class UseQueryParser {

	LogController lc = new LogController();/////
	JSONObject logEntry = new JSONObject();/////
	StringBuilder sb = new StringBuilder();
	long start;/////

	private String inputQuery;
	private List<String> query;

	public UseQueryParser(String inputQuery, List<String> query) {
		super();
		this.inputQuery = inputQuery;
		this.query = query;
	}

	public Database parse(Database db) {

		start = System.currentTimeMillis();/////
		logEntry.put("Query", inputQuery);/////

		sb.append(String.format("User: %s ### ", "userid"));

		try {
			String DB_Name = query.get(1);
			DB_Name = DB_Name.replace(";", "");
			Path path_of_file = Paths.get(FileConstants.FilePath, DB_Name);
			if (!Files.exists(path_of_file)) {
				System.err.println("Database doesn't exist!");
				return db;
			} else {
				System.out.println("You are in" + "\t" + DB_Name + "\t" + "database");
			}
			db = new Database(DB_Name);

			logEntry.put("Database", DB_Name);/////
			logEntry.put("Status", Status.SUCCESSFUL);/////
			logEntry.put("Execution time",System.currentTimeMillis()-start);/////
			lc.log(LogType.QUERY, logEntry);/////

			sb.append(String.format("Database: %s ### ", DB_Name));
			sb.append(String.format("Status: %s ### ",Status.SUCCESSFUL));
			sb.append(String.format("Query: %s\n", inputQuery));
			Files.write(Path.of("bin/analytics/logs.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

			return db;

		}catch (Exception e){

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
		return db;
	}

}
