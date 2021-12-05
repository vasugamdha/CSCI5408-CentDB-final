package Executor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ConstantFileValues.FileConstants;
import Database.Database;
import File.FileFormatHandler;

public class ExecuteInsertTable {

	private String tableName;
	private Map<String, Object> values;

	public ExecuteInsertTable(String tableName, Map<String, Object> mapvalues) {
		super();
		this.tableName = tableName;
		this.values = mapvalues;
	}

	public void InsertIntoTable(Database db) throws Exception {
		try {
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
				System.err.println(tableName + "\t" + "does not exist");
				throw new Exception();

			} else {

				FileFormatHandler fileformatHandler = new FileFormatHandler();

				Map<String, String> columnNames = fileformatHandler.getColumnNamesInTable(db.getDatabase(), tableName);

				List<String> columns = new ArrayList<String>(columnNames.keySet());
				String columnArray[] = new String[columns.size()];
				columns.toArray(columnArray);

				for (Map.Entry<String, Object> mapentry : values.entrySet()) {
					if (!Arrays.stream(columnArray).anyMatch(mapentry.getKey()::equalsIgnoreCase)) {
						System.err.println("Column is not present in your table!");
						throw new Exception();
					}
				}

				fileformatHandler.InsetIntoTable(db.getDatabase(), tableName, values);
				System.out.println("Values inserted into" + "\t" + tableName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
