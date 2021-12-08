package Queries;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ConstantFileValues.FileConstants;
import Database.Database;
import Parser.CreateQueryParser;
import Parser.DeleteQueryParser;
import Parser.DropQueryParser;
import Parser.InsertQueryParser;
import Parser.SelectQueryParser;
import Parser.UpdateQueryParser;
import Parser.UseQueryParser;

public class WriteQueries {

	static Database db = null;

	public void Write_Queries(String val) throws Exception {
		String[] s1 = val.split("\\s");
		List<String> query = Arrays.asList(s1);

		String queryType = query.get(0);

		switch (queryType) {
		case "create":
			CreateQueryParser createquery = new CreateQueryParser();
			createquery.createquery(val, query, db);
			break;
		case "use":
			UseQueryParser usequery = new UseQueryParser(val, query);
			db = usequery.parse(db);
			break;
		case "insert":
			InsertQueryParser insertparser = new InsertQueryParser(val, query);
			insertparser.parse(db);
			break;
		case "select":
			SelectQueryParser selectparser = new SelectQueryParser(val, query);
			selectparser.parse(db);
			break;
		case "update":
			UpdateQueryParser updateparser = new UpdateQueryParser(val, query);
			updateparser.parse(db);
			break;
		case "delete":
			DeleteQueryParser deleterowparser = new DeleteQueryParser(val, query);
			deleterowparser.parse(db);
			break;
		case "drop":
			DropQueryParser dropparser = new DropQueryParser();
			dropparser.droptable(val, query, db);
			break;
		default:
			System.err.print("Invalid keyword given!");
		}

	}

//	public List<String> returnListOfTables(String databaseName) throws Exception {
//		List<String> result = new ArrayList<>();
//		File file = new File(FileConstants.FilePath + "/" + databaseName);
//		File[] filesArray = file.listFiles();
//
//		for (int i = 0; i < filesArray.length; i++) {
//			if (filesArray[i].isFile()) {
//				if (!filesArray[i].getName().contains("meta_data_file")) {
//					result.add(filesArray[i].getName());
//				}
//			}
//		}
//		return result;
//	}

	public ArrayList<String> returnListOfTables(String databaseName) throws Exception {
//		List<String> result = new ArrayList<>();
//		File file = new File(FileConstants.FilePath + "/" + databaseName);
//		File[] filesArray = file.listFiles();

		ArrayList<String> listOfTableInDb = new ArrayList<>(){{
			add("dalhousie");
			add("halifax");
		}};
		return listOfTableInDb;
	}

}
