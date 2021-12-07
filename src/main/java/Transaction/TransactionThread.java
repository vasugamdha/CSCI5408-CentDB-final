package Transaction;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionThread extends Thread{
	
	TransactionExecutor executor;
	List<String> queries;
	static Lock lock = new ReentrantLock();
	
	public TransactionThread(List<String> queries) {
		executor = new TransactionExecutor();
		this.queries = queries;
	}
	
	@Override
	public void run() {
		lock.lock();
		try {
		executor.execute(queries);
		} finally {
			lock.unlock();
		}
	}
}