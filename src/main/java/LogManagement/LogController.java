package LogManagement;

import LogManagement.EventLogs;
import org.json.JSONObject;

public class LogController {
    private final GeneralLogs generalLogs;
    private final EventLogs eventLogs;
    private final QueryLogs queryLogs;

    public LogController() {
        generalLogs = new GeneralLogs();
        eventLogs = new EventLogs();
        queryLogs = new QueryLogs();
    }

    public void log(LogType type, JSONObject element) {
        if(type == LogType.GENERAL){
            generalLogs.log(element);
        }else if(type == LogType.EVENT){
            eventLogs.log(element);
        }else if(type == LogType.QUERY){
            queryLogs.log(element);
        }
    }

}
