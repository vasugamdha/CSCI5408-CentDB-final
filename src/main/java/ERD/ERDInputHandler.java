package ERD;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import ConstantFileValues.FileConstants;

public class ERDInputHandler {

	public static void handleERDInput(Scanner scanner) {
		
		System.out.println("Do you want to export the ERD of which database?");
		
		String db = scanner.nextLine();
		
		db = db.replace(";", "").toLowerCase();
		
		db = FileConstants.FilePath + "/" + db;
		
		File dir = new File(db);
		
		if ((!dir.exists()) && (!dir.isDirectory())) {
			System.err.println("Database doesn't exist!");
			return;
		}
		
		ERDExecutor executor = new ERDExecutor();
		executor.generateERD(dir);
		
	}
	
}
