package Transaction;

import Queries.WriteQueries;

import java.util.List;

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
