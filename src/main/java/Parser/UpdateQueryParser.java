package Parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import ConstantFileValues.FileConstants;
import Database.Database;
import Executor.ExecuteUpdateColumn;


public class UpdateQueryParser {

	private String inputQuery;
	private List<String> query;

	public UpdateQueryParser(String inputQuery, List<String> query) {
		super();
		this.inputQuery = inputQuery;
		this.query = query;
	}

	public void parse(Database db) {
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

		} catch (Exception e) {
			
		}

	}

}
