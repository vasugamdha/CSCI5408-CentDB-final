package File;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ConstantFileValues.FileConstants;
import Database.Database;

public class FileFormatHandler {

	public void TableCreation(String Database, String Table, Map<String, String> val) throws Exception {

		String data = "";

		File metaDataFile = new File(FileConstants.FilePath + "/" + Database + "/" + Table + "-meta_data_file.txt");
		metaDataFile.createNewFile();
		BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(metaDataFile));

		data = String.join(FileConstants.delimiter, "Table_Name", "Column_Name", "Datatype");
		bufferedwriter.write(data + System.getProperty("line.separator"));

		for (Map.Entry<String, String> mapobject : val.entrySet()) {
			String key = mapobject.getKey();
			String value = mapobject.getValue();
			data = String.join(FileConstants.delimiter, Table, key, value);
			bufferedwriter.write(data + System.getProperty("line.separator"));
		}

		bufferedwriter.flush();
		bufferedwriter.close();

		File fileforTable = new File(FileConstants.FilePath + "/" + Database + "/" + Table + ".txt");
		fileforTable.createNewFile();
		bufferedwriter = new BufferedWriter(new FileWriter(fileforTable));

		List<String> columnsInTable = new ArrayList<String>(val.keySet());
		data = String.join(FileConstants.delimiter, columnsInTable);
		bufferedwriter.write(data + System.getProperty("line.separator"));

		bufferedwriter.flush();
		bufferedwriter.close();
	}

	public void InsetIntoTable(String Database, String Table, Map<String, Object> val) throws Exception {

		ArrayList<String> orderofColumn = new ArrayList<>();
		int no = 0;
		String line;
		String data = "";

		File fileforTable = new File(FileConstants.FilePath + "/" + Database + "/" + Table + ".txt");
		File metaData = new File(FileConstants.FilePath + "/" + Database + "/" + Table + "-meta_data_file.txt");

		BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(fileforTable, true));
		BufferedReader bufferedreader = new BufferedReader(new FileReader(metaData));

		while ((line = bufferedreader.readLine()) != null) {
			if (no == 0) {

			} else {
				List<String> fieldsinTable = Arrays.asList(line.split(FileConstants.InsertDelimiter));
				orderofColumn.add(fieldsinTable.get(1));
			}
			no = no + 1;
		}

		List<String> orderofData = new ArrayList<>();
		for (int i = 0; i < val.size(); i++) {
			orderofData.add(val.get(orderofColumn.get(i)).toString());
		}

		data = String.join(FileConstants.delimiter, orderofData);
		bufferedwriter.write(data + System.getProperty("line.separator"));
		bufferedwriter.flush();
		bufferedwriter.close();
		bufferedreader.close();

	}

	public void selectFromTable(String Database, String tableName, List<String> columnNames, List<String> query,
			String conditionColumnValue) throws Exception {
		try {
			Map<String, List<String>> valuesofRowandColumn = new HashMap<>();
			Map<String, String> map = new HashMap<>();
			List<String> columnNamesinTable = null;
			List<String> columnValuesinTable = null;

			String line;
			int no = 0;

			File fileforTable = new File(FileConstants.FilePath + "/" + Database + "/" + tableName + ".txt");
			BufferedReader bufferreader = new BufferedReader(new FileReader(fileforTable));

			while ((line = bufferreader.readLine()) != null) {
				if (no == 0) {

					columnNamesinTable = Arrays.asList(line.split(FileConstants.delimiter));

				} else {

					columnValuesinTable = Arrays.asList(line.split(FileConstants.delimiter));

					ArrayList<String> tableRow = new ArrayList<>();

					String Queryvalue = query.get(1);
					if (Queryvalue.equalsIgnoreCase("*")) {

						for (int i = 0; i < columnNamesinTable.size(); i++) {
							tableRow.add(columnValuesinTable.get(i));

						}

					} else {
						if (columnValuesinTable.contains(conditionColumnValue)) {
							for (String column : columnNames) {
								int i = columnNamesinTable.indexOf(column);
								tableRow.add(columnValuesinTable.get(i));
							}
						}
					}

					valuesofRowandColumn.put("Answer for your query", tableRow);

				}
				no = no + 1;

				String Queryvalue = query.get(1);
				if (Queryvalue.equalsIgnoreCase("*")) {
					for (Map.Entry<String, List<String>> mapentry : valuesofRowandColumn.entrySet()) {
						System.out.println(mapentry.getKey() + ": " + mapentry.getValue() + "\n");
					}
				} else {

					for (Map.Entry<String, List<String>> mapentry : valuesofRowandColumn.entrySet()) {
						if (!(mapentry.getValue().isEmpty())) {
							System.out.println(mapentry.getKey() + ": " + mapentry.getValue() + "\n");
						}
					}

				}

			}
			bufferreader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateTable(String Database, String tableName, String column, String value, String conditionColumnValue)
			throws Exception {

		String line;
		int no = 0;

		List<String> columnNamesinTable = new ArrayList<>();
		List<String> columnValuesinTable = new ArrayList<>();
		String oldDatainFile = "";

		File fileforTable = new File(FileConstants.FilePath + "/" + Database + "/" + tableName + ".txt");
		BufferedReader bufferreader = new BufferedReader(new FileReader(fileforTable));
		FileWriter writer = null;

		while ((line = bufferreader.readLine()) != null) {

			if (no == 0) {

				columnNamesinTable = Arrays.asList(line.split(FileConstants.delimiter));

			} else {
				columnValuesinTable = Arrays.asList(line.split(FileConstants.delimiter));

				if (columnValuesinTable.contains(conditionColumnValue)) {

					int i = columnNamesinTable.indexOf(column);
					line = line.replace(columnValuesinTable.get(i), value);

				}

			}
			oldDatainFile += line + System.getProperty("line.separator");

			no = no + 1;
		}
		writer = new FileWriter(fileforTable);
		writer.write(oldDatainFile);
		bufferreader.close();
		writer.close();

	}

	public void deleteFromTable(String databaseName, String tableName, String conditionColumnName,
			String conditionColumnValue) throws Exception {

		String line;
		int no = 0;

		List<String> columnNamesinTable = new ArrayList<>();
		List<String> columnValuesinTable = new ArrayList<>();
		String oldDatainFile = "";

		File fileforTable = new File(FileConstants.FilePath + "/" + databaseName + "/" + tableName + ".txt");
		BufferedReader bufferreader = new BufferedReader(new FileReader(fileforTable));
		FileWriter writer = null;

		while ((line = bufferreader.readLine()) != null) {

			if (no == 0) {

				columnNamesinTable = Arrays.asList(line.split(FileConstants.delimiter));

			} else {

				columnValuesinTable = Arrays.asList(line.split(FileConstants.delimiter));

				if (columnValuesinTable.contains(conditionColumnValue)) {
					line = line.replace(line, "");

				}

			}
			if (!(line == "")) {
				oldDatainFile += line + System.getProperty("line.separator");
			}

			no = no + 1;
		}
		writer = new FileWriter(fileforTable);
		writer.write(oldDatainFile);
		bufferreader.close();
		writer.close();
	}

	public void dropMetaandDataFile(Database db, String Table) throws Exception {

		Path path1 = Paths.get(FileConstants.FilePath, db.getDatabase(), Table + "-meta_data_file.txt");
		Files.deleteIfExists(path1);

		Path path2 = Paths.get(FileConstants.FilePath, db.getDatabase(), Table + ".txt");
		Files.deleteIfExists(path2);

	}

	public Map<String, String> getColumnNamesInTable(String Database, String Table) throws Exception {

		String line;
		int no = 0;

		File metaData = new File(FileConstants.FilePath + "/" + Database + "/" + Table + "-meta_data_file.txt");
		BufferedReader bufferedreader = new BufferedReader(new FileReader(metaData));

		Map<String, String> dataTypesofColumns = new HashMap<>();
		while ((line = bufferedreader.readLine()) != null) {
			if (no == 0) {

			} else {
				List<String> fieldsinTable = Arrays.asList(line.split(FileConstants.InsertDelimiter));
				dataTypesofColumns.put(fieldsinTable.get(1), fieldsinTable.get(2));
			}
			no = no + 1;
		}
		bufferedreader.close();
		return dataTypesofColumns;
	}

}
