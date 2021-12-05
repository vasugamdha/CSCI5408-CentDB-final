package LogManagement;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public class QueryLogs {
    Path path;

    public QueryLogs() {
        path = Path.of(Constants.QUERY_LOGS_PATH);
    }

    private JSONObject getData(){
        try {
            String data = Files.readString(path, StandardCharsets.US_ASCII);
            return new JSONObject(data);
        } catch (IOException e) {
            return new JSONObject();
        }
    }

    public void log(JSONObject element){
        JSONObject main = getData();
        main.put(new Date().toString(),element);
        try {
            Files.write(path, main.toString(5).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String text, long time, Status status) {
        JSONObject main = getData();
        JSONObject element = new JSONObject();

        element.put("Query", text);
        element.put("Execution Time", String.format("%s ms",time));
        element.put("Status", status);
        main.put(new Date().toString(),element);

        System.out.println(main.toString(5));
        try {
            Files.write(path, main.toString(5).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        QueryLogs q = new QueryLogs();

        long start = System.currentTimeMillis();
        Thread.sleep(200);
        long end = System.currentTimeMillis();

        q.log("text",end-start, Status.SUCCESSFUL);
    }
}

