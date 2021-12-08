package login;

import app.Main;
import com.google.common.hash.Hashing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class login implements Ilogin{

    public void loginUser() throws Exception {

        System.out.println("-----------Login Page----------\n");
        System.out.println("Enter user id:");
        Scanner sc = new Scanner(System.in);
        String userId = sc.nextLine();
        System.out.println("Enter password:");
        String password = sc.nextLine();
        System.out.println("What is your birth city?");
        String security1 = sc.nextLine();

        String hashedUserId = Hashing.sha256()
                .hashString(userId, StandardCharsets.UTF_8)
                .toString();

        String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

            String array[] = {""};
            FileInputStream f;
            Scanner sc1;
            f = new FileInputStream("user.txt");
            sc1=new Scanner(f);
            int loginSuccess=0;
            while(sc1.hasNextLine())
            {
                String lineInFile = sc1.nextLine();
                if(lineInFile.contains(hashedUserId) && lineInFile.contains(hashedPassword)){
                    System.out.println("Login successful");
                    loginSuccess=1;
                }
            }
            if(loginSuccess==0) {
                System.out.println("Login failed");
            }
            if(loginSuccess==1){
                Main.main(array);
            }
            sc1.close();
        }

}
