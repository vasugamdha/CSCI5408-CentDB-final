package Parser;

import java.util.List;

import Database.Database;
import Executor.ExecuteDeleteRow;


public class DeleteQueryParser {
	private List<String> query;
private String inputQuery;

	public DeleteQueryParser(String inputQuery, List<String> query) {
		super();
		this.query = query;
		this.inputQuery=inputQuery;
	}

	public void parse(Database db) throws Exception {
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

		} catch (Exception e) {
			
		}
	}
}
