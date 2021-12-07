package Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionParser {
	
	List<String> queries;
	Map<String, Integer> savepoints;
	
	public TransactionParser() {
		this.queries = new ArrayList<>();
		this.savepoints = new HashMap<>();
	}
	
	public void addQuery(String query) {
		this.queries.add(query);
	}
	
	public List<String> getQueries() {
		return this.queries;
	}
	
	public void rollback() {
		queries.clear();
	}
	
	public void savepoint(String savepointName) {
		savepoints.put(savepointName, queries.size());
	}
	
	public void rollbackToSavepoint(String savepointName) {
		int rollbackIdx = savepoints.get(savepointName);
		if (rollbackIdx < queries.size()) {
			queries.subList(rollbackIdx, queries.size()).clear();
		}
	}
	
	public void commit() {
		System.out.println("Executing following queries:");
		for (String query : queries) {
			System.out.println(query);
		}
		TransactionThread thread = new TransactionThread(queries);
		thread.start();
	}
}
