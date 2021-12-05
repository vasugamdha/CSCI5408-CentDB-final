package Parser;

import java.util.List;

import Database.Database;
import Executor.ExecuteDropTable;


public class DropQueryParser {
	public void droptable(String inputQuery, List<String> query, Database db) throws Exception {
		try {
			if (query.get(1).equalsIgnoreCase("table")) {

				String table_Name = query.get(2);
				table_Name = table_Name.replace(";", "");
				ExecuteDropTable executedroptable = new ExecuteDropTable(table_Name, db);
				executedroptable.DropTable(db);
			} else {
				System.out.println("Invalid Keywords!!");
				throw new Exception();
			}

		} catch (Exception e) {
		}
	}

}
