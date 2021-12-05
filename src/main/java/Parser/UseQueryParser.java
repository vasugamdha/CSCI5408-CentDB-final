package Parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import ConstantFileValues.FileConstants;
import Database.Database;


public class UseQueryParser {

	private String inputQuery;
	private List<String> query;

	public UseQueryParser(String inputQuery, List<String> query) {
		super();
		this.inputQuery = inputQuery;
		this.query = query;
	}

	public Database parse(Database db) throws Exception {
		try {
		String DB_Name = query.get(1);
		DB_Name = DB_Name.replace(";", "");
		Path path_of_file = Paths.get(FileConstants.FilePath, DB_Name);
		if (!Files.exists(path_of_file)) {
			System.err.println("Database doesn't exist!");
			return db;

		} else {
			System.out.println("You are in" + "\t" + DB_Name + "\t" + "database");
		}
		db = new Database(DB_Name);
		return db;

	}catch (Exception e){
		}
		return db;
	}

}
