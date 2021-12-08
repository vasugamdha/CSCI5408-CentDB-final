package login;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static Map<Integer, String> securityQuestions = new HashMap<>(){{
        put(1,"What is your birth city?");
        put(2,"What is name of your bestfriends mother?");
        put(3,"What is your high school name abbreviation?");
    }};
    public static String userid = "";
}
