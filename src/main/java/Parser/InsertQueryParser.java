package Parser;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Database.Database;
import Executor.ExecuteInsertTable;


public class InsertQueryParser {

	private String inputQuery;
	private List<String> query;

	public InsertQueryParser(String inputQuery, List<String> query) {
		super();
		this.inputQuery = inputQuery;
		this.query = query;
	}

	public void parse(Database db) throws Exception {
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

		} catch (Exception e) {

		}
	}

}
