package ERD;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ERDWriter {
	public void writeFile(String path, String contents) {

		File file = new File(path);
		
		if (file.exists()) {
			file.delete();
		}
		
		try {
			file.createNewFile();
			FileWriter fileWritter = new FileWriter(file.getName(), false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(contents);
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
