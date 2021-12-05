package Parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.Database;
import Executor.ExecuteCreateDatabase;
import Executor.ExecuteCreateTable;

public class CreateQueryParser {

	public void createquery(String inputQuery, List<String> query, Database db) throws Exception {
		try {
			if (query.get(1).equalsIgnoreCase("database")) {

				String DB_Name = query.get(2);
				DB_Name = DB_Name.replace(";", "");
				ExecuteCreateDatabase executecreateDb = new ExecuteCreateDatabase(DB_Name, inputQuery);
				executecreateDb.executeCreateDb();
			} else if (query.get(1).equalsIgnoreCase("table")) {

				String value = query.get(2);
				int index = value.indexOf("(");
				if (index < 0) {
					index = value.length();
				}
				String tableName = value.substring(0, index).strip();

				int i = inputQuery.indexOf("(");
				String properties = inputQuery.substring(i + 1, inputQuery.length() - 2);

				List<String> columns = Arrays.asList(properties.split(","));

				Map<String, String> Map = new HashMap<>();
				for (String str : columns) {
					str = str.trim();
					List<String> individualColumns = Arrays.asList(str.split("\\s+"));
					Map.put(individualColumns.get(0), individualColumns.get(1));
				}

				ExecuteCreateTable executecreateTable = new ExecuteCreateTable(Map, tableName);
				executecreateTable.CreateTable(db);
			} else {
				System.err.println("Invalid Keyword!!");
			}

		} catch (Exception e) {
			System.err.println("Check your Syntax");
		}
	}
}
