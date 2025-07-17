package mental_health_sim;

import java.util.Scanner;

public class Executer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Hi, what is your name? ");
        String name = sc.nextLine();
        User user = new User(name);
        Assistant assistant = new Assistant(user);

        while (true) {
            System.out.println("\n*** Checkin Menu ***");
            System.out.println("1. Start a session");
            System.out.println("2. Show mood history");
            System.out.println("3. Terminate the session");
            System.out.print("Enter choice: ");

            String ch = sc.nextLine();

            switch (ch) {
                case "1":
                    assistant.startSession(sc);
                    break;
                case "2":
                    assistant.showMoodHistory();
                    break;
                case "3":
                    System.out.println("I hope you had a great session...");
                    return; // Exits the main method and terminates the program
                default:
                    System.out.println("Wrong input try again.");
                    break;
            }
        }
    }
}
