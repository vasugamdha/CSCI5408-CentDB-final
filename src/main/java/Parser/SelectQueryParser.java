package Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Database.Database;
import Executor.ExecuteSelectFromTable;


public class SelectQueryParser {
	private String inputQuery;
	private List<String> query;

	public SelectQueryParser(String inputQuery, List<String> query) {
		super();
		this.inputQuery = inputQuery;
		this.query = query;
	}

	public void parse(Database db) {
		try {

			String TableName = null;
			List<String> columnFieldsList = new ArrayList<>();
			String conditionColumnName = null;
			String conditionColumnValue = null;

			String keyword1 = query.get(1);

			if (keyword1.equalsIgnoreCase("*")) {

				String keyword2 = query.get(2);

				if (keyword2.equalsIgnoreCase("from")) {

					TableName = query.get(3);
					TableName = TableName.replace(";", "");

				} else {
					System.err.println("Invalid keyword in the Query!");
					throw new Exception();
				}

			} else {
				String Columnfields = query.get(1);
				columnFieldsList = Arrays.asList(Columnfields.split(","));

				String keyword2 = query.get(2);
				if (keyword2.equalsIgnoreCase("from")) {

					TableName = query.get(3);
					TableName = TableName.replace(";", "");
				}

				String keyword3 = query.get(4);

				if (keyword3.equals("where")) {

					String value = query.get(5);
					int i = value.indexOf("=");
					int j = inputQuery.indexOf("'");
					int k = inputQuery.indexOf(";");

					if (i < 0) {
						i = value.length();
					}

					conditionColumnName = value.substring(0, i).strip();
					
				
					conditionColumnValue = inputQuery.substring(j, k).strip();

				} else {
					System.err.println("Invalid keyword in the Query!");
					throw new Exception();
				}
			}

			ExecuteSelectFromTable executeselecttable = new ExecuteSelectFromTable(TableName, columnFieldsList,
					conditionColumnName, conditionColumnValue, query);
			executeselecttable.SelectTable(db);

		} catch (Exception e) {
			

		}
	}
}
