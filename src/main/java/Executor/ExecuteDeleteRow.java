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


public class ExecuteDeleteRow {
	private String tableName;
	private String conditionColumnName;
	private String conditionColumnValue;

	public ExecuteDeleteRow(String tableName, String conditionColumnName, String conditionColumnValue) {
		super();
		this.tableName = tableName;
		this.conditionColumnName = conditionColumnName;
		this.conditionColumnValue = conditionColumnValue;
	}

	public void deletefromTable(Database db) throws Exception {

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
		} else {
			Map<String, String> columnNamesinTable = fileformatHandler.getColumnNamesInTable(db.getDatabase(), tableName);

			List<String> columns = new ArrayList<String>(columnNamesinTable.keySet());

			if (!(columns.contains(conditionColumnName))) {

				System.err.println("check if your Column name in where condition is correct!");
				throw new Exception();

			}

			fileformatHandler.deleteFromTable(db.getDatabase(), tableName, conditionColumnName,conditionColumnValue);
			System.out.println("Row succesfully deleted");
		}

	}

}
