package mental_health_sim;

// mental_health_sim/MoodTracker.java


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MoodTracker {

    /**
     * Evaluates the user's mood by asking a series of questions.
     * This method is designed for a console-based interaction.
     * @param sc The Scanner for user input.
     * @param user The User object.
     * @return The evaluated mood as a String (e.g., "Happy", "Sad", "Anxious").
     */
    public String evaluateMoodByQuestions(Scanner sc, User user) {
        System.out.println("\nHi " + user.getName() + ", I'm going to ask a few questions to see how you're doing.");
        
        // Scores for different moods
        Map<String, Integer> moodScores = new HashMap<>();
        moodScores.put("Happy", 0);
        moodScores.put("Sad", 0);
        moodScores.put("Anxious", 0);
        moodScores.put("Tired", 0);

        // --- Question 1 ---
        int choice = askQuestion(sc, 
            "How would you describe your energy level today?",
            new String[]{"High and vibrant", "A bit low or drained", "Restless and on-edge"});
        if (choice == 1) moodScores.merge("Happy", 1, Integer::sum);
        if (choice == 2) moodScores.merge("Sad", 1, Integer::sum);
        if (choice == 2) moodScores.merge("Tired", 1, Integer::sum);
        if (choice == 3) moodScores.merge("Anxious", 1, Integer::sum);

        // --- Question 2 ---
        choice = askQuestion(sc,
            "How have you been feeling about things you usually enjoy?",
            new String[]{"Interested and engaged", "Not really interested", "Too distracted to enjoy them"});
        if (choice == 1) moodScores.merge("Happy", 1, Integer::sum);
        if (choice == 2) moodScores.merge("Sad", 1, Integer::sum);
        if (choice == 3) moodScores.merge("Anxious", 1, Integer::sum);

        // --- Question 3 ---
        choice = askQuestion(sc,
            "Which statement best describes your thoughts recently?",
            new String[]{"Calm and positive", "My mind is racing with worries", "Mostly self-critical or pessimistic"});
        if (choice == 1) moodScores.merge("Happy", 1, Integer::sum);
        if (choice == 2) moodScores.merge("Anxious", 2, Integer::sum); // Racing thoughts are a strong indicator of anxiety
        if (choice == 3) moodScores.merge("Sad", 1, Integer::sum);

        // --- Determine the final mood ---
        String finalMood = "Okay"; // Default mood if scores are tied or low
        int maxScore = 0;
        
        // Prioritize negative feelings if scores are high
        if (moodScores.get("Anxious") > 1) finalMood = "Anxious";
        else if (moodScores.get("Sad") > 1) finalMood = "Sad";
        else {
             // Find the mood with the highest score
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
    
    /**
     * Helper method to ask a single multiple-choice question and get a valid answer.
     */
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