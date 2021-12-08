import java.util.Scanner;

import login.login;
import registration.registration;

public class LandingPage {

    public static void main(String args[]){

        registration reg = new registration();
        login login = new login();

        System.out.println("New to the system? Register here by clicking 1.");
        System.out.println("Existing user? Login here by clicking 2.");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if(input==1){
            try {
                reg.registerUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(input==2){
            try {
                login.loginUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
