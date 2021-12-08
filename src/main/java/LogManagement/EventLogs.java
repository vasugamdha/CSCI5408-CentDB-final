package LogManagement;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Date;

public class EventLogs {
    private final Path path;

    public EventLogs() {
        path = Path.of(Constants.EVENT_LOGS_PATH);
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
}
