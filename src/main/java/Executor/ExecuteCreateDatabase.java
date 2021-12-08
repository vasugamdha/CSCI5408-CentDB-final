package Executor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import ConstantFileValues.FileConstants;

public class ExecuteCreateDatabase {

	private String dbname;
	private String query;

	public ExecuteCreateDatabase(String dbname, String query) {
		super();
		this.dbname = dbname;
		this.query = query;

	}

	public void executeCreateDb() throws Exception {
		try {
			Path path1 = Paths.get(FileConstants.FilePath, dbname);
			if (Files.exists(path1)) {
				System.err.println("Database already exists!");
				throw new Exception();
			} else {
				File DB = new File(FileConstants.FilePath +"/"+ dbname);
				DB.mkdir();
				Path path2 = Paths.get(FileConstants.FilePath, dbname);
				if (!Files.exists(path2)) {
					System.out.println("Trouble creating the database!");
					throw new Exception();
				} else {
					System.out.println("Database - " + dbname + "is created");
				}
			}
		} catch (Exception e) {
			
		}
	}
}