package mental_health_sim;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MoodTracker {

    public String evaluateMoodByQuestions(Scanner sc, User user) {
        System.out.println("\nHi " + user.getName() + ", I'm going to ask a few questions to see how you're doing.");
        
        Map<String, Integer> moodScores = new HashMap<>();
        moodScores.put("Happy", 0);
        moodScores.put("Sad", 0);
        moodScores.put("Anxious", 0);
        moodScores.put("Tired", 0);

        int choice = askQuestion(sc, 
            "How would you describe your energy level today?",
            new String[]{"High and vibrant", "A bit low or drained", "Restless and on-edge"});
        if (choice == 1) moodScores.merge("Happy", 1, Integer::sum);
        if (choice == 2) moodScores.merge("Sad", 1, Integer::sum);
        if (choice == 2) moodScores.merge("Tired", 1, Integer::sum);
        if (choice == 3) moodScores.merge("Anxious", 1, Integer::sum);

        choice = askQuestion(sc,
            "How have you been feeling about things you usually enjoy?",
            new String[]{"Interested and engaged", "Not really interested", "Too distracted to enjoy them"});
        if (choice == 1) moodScores.merge("Happy", 1, Integer::sum);
        if (choice == 2) moodScores.merge("Sad", 1, Integer::sum);
        if (choice == 3) moodScores.merge("Anxious", 1, Integer::sum);

        choice = askQuestion(sc,
            "Which statement best describes your thoughts recently?",
            new String[]{"Calm and positive", "My mind is racing with worries", "Mostly self-critical or pessimistic"});
        if (choice == 1) moodScores.merge("Happy", 1, Integer::sum);
        if (choice == 2) moodScores.merge("Anxious", 2, Integer::sum);
        if (choice == 3) moodScores.merge("Sad", 1, Integer::sum);

        String finalMood = "Okay";
        int maxScore = 0;
        
        if (moodScores.get("Anxious") > 1) finalMood = "Anxious";
        else if (moodScores.get("Sad") > 1) finalMood = "Sad";
        else {
            for (Map.Entry<String, Integer> entry : moodScores.entrySet()) {
                if (entry.getValue() > maxScore) {
                    maxScore = entry.getValue();
                    finalMood = entry.getKey();
                }
            }
        }
       
        System.out.println("\nThank you for sharing. It sounds like you might be feeling: " + finalMood);
        return finalMood;
    }
    
    private int askQuestion(Scanner sc, String question, String[] options) {
        System.out.println("\n" + question);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s\n", i + 1, options[i]);
        }
        
        int choice = -1;
        while (true) {
            System.out.print("Enter choice (1-" + options.length + "): ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice >= 1 && choice <= options.length) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and " + options.length + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
