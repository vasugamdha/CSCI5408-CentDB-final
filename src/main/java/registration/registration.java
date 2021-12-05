package registration;

import com.google.common.hash.Hashing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class registration {

    public static void main(String[] args) throws IOException {
    //public void registerUser() throws IOException {

        System.out.println("Enter user id:");
        Scanner sc = new Scanner(System.in);
        String userId = sc.nextLine();
        System.out.println("Enter password:");
        String password = sc.nextLine();
        System.out.println("What is your birth city?");
        String security1 = sc.nextLine();
        System.out.println("What is name of your bestfriends mother?");
        String security2 = sc.nextLine();
        System.out.println("What is your high school name abbreviation?");
        String security3 = sc.nextLine();

        String hashedUserId = Hashing.sha256()
                .hashString(userId, StandardCharsets.UTF_8)
                .toString();

        String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

        System.out.println(userId);
        System.out.println(hashedUserId);

        String stro = "\n"+hashedUserId + "///" + hashedPassword + "///" + security1 + "///" + security2 + "///" + security3;
        try(FileWriter write = new FileWriter("user.txt", true);){
            write.append(stro);
        }catch (Exception e){
            e.printStackTrace();
        }

//        File file = new File("user.txt");
//
//        file.createNewFile();
//
//        FileWriter writer = new FileWriter(file);
//        writer.write(hashedUserId + "///" + hashedPassword + "///" + security1 + "///" + security2 + "///" + security3);
//        writer.flush();
//        writer.close();

    }

}
