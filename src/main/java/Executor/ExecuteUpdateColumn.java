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


public class ExecuteUpdateColumn {
	private String tableName;
	private String columnName;
	private String columnValue;
	private String conditionColumnName;
	private String conditionColumnValue;
	
	public ExecuteUpdateColumn(String tableName, String columnName, String columnValue, String conditionColumnName,
			String conditionColumnValue ) {
		super();
		this.tableName = tableName;
		this.columnName = columnName;
		this.columnValue = columnValue;
		this.conditionColumnName = conditionColumnName;
		this.conditionColumnValue = conditionColumnValue;
	}
	public void UpdateTable(Database db) throws Exception {

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
		else {

			Map<String, String> columnNamesinTable = fileformatHandler.getColumnNamesInTable(db.getDatabase(), tableName);

			List<String> columns = new ArrayList<String>(columnNamesinTable.keySet());

			if (columns.contains(conditionColumnName)) {


					if (!(columns.contains(columnName))) {

						System.err.println("Column is not present in your table!");
						throw new Exception();
					}

			} else {
				System.err.println("check if your Column name in where condition is correct!");
				throw new Exception();
			}
		}

		fileformatHandler.updateTable(db.getDatabase(), tableName, columnName, columnValue, conditionColumnValue);
		System.out.println("Sucessfully updated!!");

			


	}

	
	
	
	
	
	
}
