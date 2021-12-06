package Executor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ConstantFileValues.FileConstants;
import Database.Database;
import File.FileFormatHandler;


public class ExecuteSelectFromTable {

	private String tableName;
	private List<String> columnNames;
	private List<String> query;
	private String conditionColumnName;
	private String conditionColumnValue;

	public ExecuteSelectFromTable(String tableName, List<String> columnNames, String conditionColumnName,
			String conditionColumnValue, List<String> query) {
		super();
		this.tableName = tableName;
		this.columnNames = columnNames;
		this.query = query;
		this.conditionColumnName = conditionColumnName;
		this.conditionColumnValue = conditionColumnValue;
	}

	public void SelectTable(Database db) throws Exception {

		FileFormatHandler fileformatHandler = new FileFormatHandler();

		if (db.getDatabase() == null || db == null) {
			System.err.print("Select Database");
			throw new Exception();
		}
		Path path = Paths.get(FileConstants.FilePath, db.getDatabase());

		if (!Files.exists(path)) {
			System.err.println("Database does not exist!");
			throw new Exception();
		}
		path = Paths.get(FileConstants.FilePath, db.getDatabase(), tableName + ".txt");
		if (!Files.exists(path)) {
			System.err.println("Table does not exist");
			throw new Exception();

		}
		String Queryvalue = query.get(1);
		if (!(Queryvalue.equalsIgnoreCase("*"))) {

			Map<String, String> columnNamesinTable = fileformatHandler.getColumnNamesInTable(db.getDatabase(), tableName);

			List<String> columns = new ArrayList<String>(columnNamesinTable.keySet());

			if (columns.contains(conditionColumnName)) {

				for (String columnname : columnNames) {

					if (!(columns.contains(columnname))) {

						System.err.println("Column is not present in your table!");
						throw new Exception();
					}
				}
			} else {
				System.err.println("check if your Column name in where condition is correct!");
				throw new Exception();
			}
		}

		fileformatHandler.selectFromTable(db.getDatabase(), tableName,
				columnNames, query, conditionColumnValue);

	}

}
