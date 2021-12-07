package ERD;

import ConstantFileValues.FileConstants;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ERDExecutor {

	public void generateERD(File dbDir) {
		
		File[] files = dbDir.listFiles();
		
		if (files == null) {
			System.out.println("No table found!");
			return;
		}

		FileReader reader;
		List<String> metaDatas = new ArrayList<>();

		for (File file : files) {
			if (file.getName().contains("meta_data")) {
				try {
					reader = new FileReader(file);
					char[] chars = new char[(int) file.length()];
					reader.read(chars);
					String metaData = new String(chars);
					metaDatas.add(metaData);
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (String metaData : metaDatas) {
			sb.append(metaData + "\n");
		}
		
		String contents = sb.toString();
		
		ERDWriter writer = new ERDWriter();
		writer.writeFile(FileConstants.ERDPath, contents);
		System.out.println("export to ERD.txt");
	}
}
