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
import org.json.JSONObject;

public class DropQueryParser {

	LogController lc = new LogController();/////
	JSONObject logEntry = new JSONObject();/////
	StringBuilder sb = new StringBuilder();
	long start;/////

	public void droptable(String inputQuery, List<String> query, Database db) {

		start = System.currentTimeMillis();/////
		logEntry.put("Query", inputQuery);/////
		logEntry.put("Database", db.getDatabase());/////

		sb.append(String.format("User: %s ### ", "userid"));
		sb.append(String.format("Database: %s ### ", db.getDatabase()));

		try {
			if (query.get(1).equalsIgnoreCase("table")) {

				String table_Name = query.get(2);
				table_Name = table_Name.replace(";", "");
				ExecuteDropTable executedroptable = new ExecuteDropTable(table_Name, db);
				executedroptable.DropTable(db);

				logEntry.put("Status", Status.SUCCESSFUL);/////
				logEntry.put("Execution time",System.currentTimeMillis()-start);/////
				lc.log(LogType.QUERY, logEntry);/////

				sb.append(String.format("Status: %s ### ",Status.SUCCESSFUL));
				sb.append(String.format("Query: %s\n", inputQuery));
				Files.write(Path.of("bin/analytics/logs.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

			} else {
				System.out.println("Invalid Keywords!!");

				logEntry.put("Status",Status.ERROR);/////
				logEntry.put("Execution time",System.currentTimeMillis()-start);/////
				lc.log(LogType.QUERY, logEntry);/////

				sb.append(String.format("Status: %s ### ",Status.ERROR));
				sb.append(String.format("Query: %s\n", inputQuery));
				Files.write(Path.of("bin/analytics/logs.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

				throw new Exception();
			}

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
