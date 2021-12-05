package Executor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

import ConstantFileValues.FileConstants;
import Database.Database;
import File.FileFormatHandler;

public class ExecuteCreateTable {

	private Map<String, String> map;
	private String TableName;

	public ExecuteCreateTable(Map<String, String> map, String tableName) {
		super();
		this.map = map;
		this.TableName = tableName;
	}

	public void CreateTable(Database db) throws Exception {
		try {
			if (db == null || db.getDatabase() == null) {
				System.out.print("Select Database");
				throw new Exception();
			}
			Path path = Paths.get(FileConstants.FilePath, db.getDatabase());

			if (!Files.exists(path)) {
				System.err.println("Database does not exist!");
				throw new Exception();
			}

			path = Paths.get(FileConstants.FilePath, db.getDatabase(), TableName + ".txt");
			if (Files.exists(path)) {
				System.out.println(TableName + " table already exist!");
				throw new Exception();

			}

			String[] data_type = { "int", "varchar", "date", "datetime", "text", "longtext", "decimal", "boolean",
					"double", "timestamp", "float" };
			for (String field : map.values()) {
				if (!Arrays.stream(data_type).anyMatch(field::equalsIgnoreCase)) {
					System.err.println("Incorrect datatypes!");
					throw new Exception();
				}
			}
			FileFormatHandler fileformatHandler = new FileFormatHandler();
			fileformatHandler.TableCreation(db.getDatabase(), TableName, map);
			System.out.println("Table created");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
