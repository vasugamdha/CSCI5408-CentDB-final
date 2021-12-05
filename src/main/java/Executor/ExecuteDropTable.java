package Executor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import ConstantFileValues.FileConstants;
import Database.Database;
import File.FileFormatHandler;


public class ExecuteDropTable {

	private Database db;
	private String tableName;

	public ExecuteDropTable(String tablename, Database db) {
		super();
		this.db = db;
		this.tableName = tablename;
	}

	public void DropTable(Database db) throws Exception {
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

			path = Paths.get(FileConstants.FilePath, db.getDatabase(), tableName + ".txt");
			if (Files.exists(path)) {
				
				FileFormatHandler fileformathandler = new FileFormatHandler();
				fileformathandler.dropMetaandDataFile(db, tableName);
				System.out.println("Dropped table" + "\t" + tableName);
				throw new Exception();

			}

		} catch (Exception e) {

		}

	}

}
