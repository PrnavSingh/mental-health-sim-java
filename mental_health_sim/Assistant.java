// mental_health_sim/Assistant.java
package mental_health_sim;

import java.util.Scanner;

public class Assistant {

    private final User user;
    private final CalmActivity ca;
    private final QuoteProvider qp;
    private final MoodTracker mt;

    public Assistant(User user) {
        this.user = user;
        ca = new CalmActivity();
        qp = new QuoteProvider();
        mt = new MoodTracker();
    }

    public void startSession(Scanner sc) {
        // 1. Evaluate the mood using the new question-based method
        String mood = mt.evaluateMoodByQuestions(sc, user);
        
        System.out.println("Logging your mood: " + mood);
        user.addMoodHistory(mood);

        // 2. Provide a mood-specific quote
        System.out.println("\nHere's something for you:");
        System.out.println("\"" + qp.getQuoteForMood(mood) + "\"\n");

        // 3. Evaluate mood and suggest a relevant activity
        evaluateMoodAndSuggestActivity(mood.toLowerCase().trim(), sc);

        System.out.println("\nThank you for checking in with yourself!");
    }

    private void evaluateMoodAndSuggestActivity(String mood, Scanner sc) {
        System.out.print("Would you like to try a short exercise based on how you're feeling? (yes/no): ");
        String ans = sc.nextLine().trim().toLowerCase();
        if (!ans.equals("yes")) {
            System.out.println("That's perfectly okay. Take care!");
            return;
        }

        switch (mood.toLowerCase()) {
            case "sad":
                ca.positiveJournalingPrompt();
                break;
            case "anxious":
                ca.groundingExercise();
                break;
            case "angry":
                ca.releasingAngerExercise();
                break;
            case "happy":
            case "excited":
                System.out.println("That's great to hear! No specific exercise needed, just keep enjoying the feeling!");
                break;
            case "tired":
            case "okay":
            default:
                System.out.println("A simple breathing exercise can help you feel more centered.");
                ca.breathingExercise();
                break;
        }
    }

    public void showMoodHistory() {
        if (user.getMoodHistory().isEmpty()) {
            System.out.println("No mood history recorded yet.");
            return;
        }
        System.out.println("--- Your Mood History ---");
        for (String mood : user.getMoodHistory()) {
            System.out.println("Mood: " + mood);
        }
        System.out.println("-------------------------");
    }
}