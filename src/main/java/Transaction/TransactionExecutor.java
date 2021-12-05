package Transaction;

import java.util.List;

import Queries.WriteQueries;

public class TransactionExecutor {
	
	public void execute(List<String> queries) {
		
		WriteQueries writeQueries = new WriteQueries();
		
		for (String query : queries) {
			try {
				writeQueries.Write_Queries(query);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
