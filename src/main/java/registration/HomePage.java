package registration;

import Analytics.AnalyticsInput;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class HomePage {

    //public void homepage() throws FileNotFoundException {
    public static void main(String args[]) throws FileNotFoundException {
        System.out.println("Enter your choice:");
        System.out.println("1. Write Queries");
        System.out.println("2. Export");
        System.out.println("3. Data Model");
        System.out.println("4. Analytics");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();

        switch(input){
            case 1:
                System.out.println("You chose Write Queries\n");
                //enter your function here
                break;

            case 2:
                System.out.println("You chose Export\n");
                //enter your function here
                break;

            case 3:
                System.out.println("You chose Data Model\n");
                //enter your function here
                break;

            case 4:
                System.out.println("You chose Analytics\n");
                AnalyticsInput analyticsInput = new AnalyticsInput();
                try {
                    analyticsInput.analytics();
                } catch (Exception e) {
                    break;
                }
                break;
        }

    }

}
