import java.io.IOException;
import java.util.Scanner;
import registration.registration;

public class LandingPage {

    public static void main(String args[]){

        registration reg = new registration();

        System.out.println("New to the system? Register here by clicking 1.");
        System.out.println("New user? Login here by clicking 2.");
        Scanner sc = new Scanner(System.in);
        if(sc.nextInt()==1){
            try {
                reg.registerUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
