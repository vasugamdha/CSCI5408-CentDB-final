package LogManagement;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneralLogs {
    Path path;
    int databases=0,tables=0;

    public GeneralLogs() {
        path = Path.of(Constants.GENERAL_LOGS_PATH);
    }

    private JSONObject getData(){
        try {
            String data = Files.readString(path, StandardCharsets.US_ASCII);
            return new JSONObject(data);
        } catch (IOException e) {
            return new JSONObject();
        }
    }

    public void log(){
        JSONObject main = getData();
        JSONObject element= new JSONObject();
        count();
        element.put("Databases", databases);
        element.put("Tables", tables);
        main.put(new Date().toString(),element);
        try {
            Files.write(path, main.toString(5).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void count() {
        String mainPath = "src/main/java/Collect_Data";
        try (Stream<Path> walk = Files.walk(Paths.get(mainPath))) {
            List<String> directories = walk.filter(Files::isDirectory)
                    .map(Path::toString).collect(Collectors.toList());

            databases = directories.size()-2;

            for(String path: directories){
                if(path.contains("SQLdump") || path.equals(mainPath))
                    continue;
                try (Stream<Path> run = Files.walk(Paths.get(path))) {
                    List<String> files = run.filter(Files::isRegularFile)
                            .map(Path::toString).collect(Collectors.toList());
                    files.forEach(System.out::println);
                    for (String file: files){
                        if(file.contains("meta_data_file")){
                            tables++;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
