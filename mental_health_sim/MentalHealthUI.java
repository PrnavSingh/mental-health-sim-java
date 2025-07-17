
package mental_health_sim;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MentalHealthUI extends JFrame {

    private final User user;
    private final QuoteProvider quoteProvider;

    private final JTextArea outputArea;

    @SuppressWarnings("unused")
    public MentalHealthUI() {
        setTitle("Mental Health Assistant");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        String name = JOptionPane.showInputDialog(this, "Hi, what is your name?");
        user = new User(name == null || name.trim().isEmpty() ? "User" : name);
        quoteProvider = new QuoteProvider();

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setMargin(new Insets(10, 10, 10, 10));

        JButton startSessionButton = new JButton("Start New Session");
        JButton showHistoryButton = new JButton("Show Mood History");
        JLabel promptLabel = new JLabel("Welcome, " + user.getName() + "! What would you like to do?", SwingConstants.CENTER);
        promptLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JPanel topPanel = new JPanel();
        topPanel.add(promptLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(startSessionButton);
        bottomPanel.add(showHistoryButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        startSessionButton.addActionListener(e -> startSessionInBackground());

        showHistoryButton.addActionListener(e -> {
            outputArea.setText("");
            if (user.getMoodHistory().isEmpty()) {
                outputArea.append("No mood history recorded yet.");
            } else {
                outputArea.append("--- Your Mood History ---\n");
                for (String mood : user.getMoodHistory()) {
                    outputArea.append("Mood: " + mood + "\n");
                }
            }
        });
    }

    /**
     * Runs the entire session flow, starting with the mood quiz.
     */
    private void startSessionInBackground() {
        String mood = runMoodEvaluationQuiz();

        if (mood != null) { // User completed the quiz
            user.addMoodHistory(mood);
            outputArea.setText("Based on your answers, it seems you're feeling: " + mood + "\n\n");
            outputArea.append("Here's a quote for you:\n\"" + quoteProvider.getQuoteForMood(mood) + "\"\n\n");

            int choice = JOptionPane.showConfirmDialog(this, "Would you like to try a recommended exercise?", "Exercise Recommendation", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                ExerciseWorker worker = new ExerciseWorker(mood.toLowerCase());
                worker.execute();
            } else {
                outputArea.append("That's okay. Remember to be kind to yourself today!");
            }
        }
    }

    /**
     * Manages the GUI-based quiz to evaluate the user's mood.
     * @return The evaluated mood string, or null if the user cancels.
     */
    private String runMoodEvaluationQuiz() {
        Map<String, Integer> moodScores = new HashMap<>();
        moodScores.put("Happy", 0);
        moodScores.put("Sad", 0);
        moodScores.put("Anxious", 0);
        moodScores.put("Tired", 0);

        // --- Question 1 ---
        String[] q1Options = {"High and vibrant", "A bit low or drained", "Restless and on-edge"};
        int choice = JOptionPane.showOptionDialog(this, "How would you describe your energy level today?", "Question 1", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, q1Options, q1Options[0]);
        if (choice == -1) return null; // User closed dialog
        if (choice == 0) moodScores.merge("Happy", 1, Integer::sum);
        if (choice == 1) {moodScores.merge("Sad", 1, Integer::sum); moodScores.merge("Tired", 1, Integer::sum);}
        if (choice == 2) moodScores.merge("Anxious", 1, Integer::sum);

        // --- Question 2 ---
        String[] q2Options = {"Interested and engaged", "Not really interested", "Too distracted to enjoy them"};
        choice = JOptionPane.showOptionDialog(this, "How have you been feeling about things you usually enjoy?", "Question 2", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, q2Options, q2Options[0]);
        if (choice == -1) return null;
        if (choice == 0) moodScores.merge("Happy", 1, Integer::sum);
        if (choice == 1) moodScores.merge("Sad", 1, Integer::sum);
        if (choice == 2) moodScores.merge("Anxious", 1, Integer::sum);

        // --- Question 3 ---
        String[] q3Options = {"Calm and positive", "My mind is racing with worries", "Mostly self-critical or pessimistic"};
        choice = JOptionPane.showOptionDialog(this, "Which statement best describes your thoughts recently?", "Question 3", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, q3Options, q3Options[0]);
        if (choice == -1) return null;
        if (choice == 0) moodScores.merge("Happy", 1, Integer::sum);
        if (choice == 1) moodScores.merge("Anxious", 2, Integer::sum); // Strong indicator
        if (choice == 2) moodScores.merge("Sad", 1, Integer::sum);

        // --- Determine final mood ---
        String finalMood = "Okay";
        if (moodScores.get("Anxious") > 1) finalMood = "Anxious";
        else if (moodScores.get("Sad") > 1) finalMood = "Sad";
        else {
            int maxScore = 0;
            for (Map.Entry<String, Integer> entry : moodScores.entrySet()) {
                if (entry.getValue() > maxScore) {
                    maxScore = entry.getValue();
                    finalMood = entry.getKey();
                }
            }
        }
        return finalMood;
    }

    // Inner class for handling background exercises (remains the same)
    private class ExerciseWorker extends SwingWorker<Void, String> {
        // ... This class does not need any changes ...
        private final String mood;

        public ExerciseWorker(String mood) {
            this.mood = mood;
        }
        @Override
        protected Void doInBackground() throws Exception {
            switch (mood) {
                case "sad":
                    publish("Starting a reflection exercise...\n\n");
                    Thread.sleep(1500);
                    publish("Think about one small thing that brought you a moment of comfort or joy today.\n");
                    Thread.sleep(4000);
                    publish("It could be a kind word, a nice song, or the warmth of the sun.\n");
                    Thread.sleep(4000);
                    publish("\nHolding onto small positives can make a big difference.");
                    break;
                case "anxious":
                    publish("Starting a grounding exercise to connect with the present...\n\n");
                    Thread.sleep(1500);
                    publish("Look around and name 5 things you can see...\n");
                    Thread.sleep(5000);
                    publish("Listen and identify 4 things you can hear...\n");
                    Thread.sleep(5000);
                    publish("Focus on 3 things you can feel (e.g., your feet on the floor)...\n");
                    Thread.sleep(5000);
                    publish("\nThis helps bring you back to the present. You are here and safe.");
                    break;
                case "angry":
                     publish("Starting an exercise to release tension...\n\n");
                     Thread.sleep(1500);
                     publish("Imagine the anger in your fists. Clench them tightly for 5 seconds...\n");
                     Thread.sleep(5000);
                     publish("Now, take a deep breath in... and as you breathe out, release your fists and let the tension go.\n");
                     Thread.sleep(4000);
                     publish("\nYou are in control of your response.");
                     break;
                case "happy":
                case "excited":
                    publish("That's wonderful! Let's take a moment to embrace it.\n");
                    Thread.sleep(2000);
                    publish("Take a deep, happy breath and smile!");
                    break;
                default:
                    publish("A simple breathing exercise can be very centering. Let's begin...\n\n");
                    Thread.sleep(1000);
                    for (int i = 0; i < 3; i++) {
                        publish("Breathe in...\n");
                        Thread.sleep(3000);
                        publish("Hold...\n");
                        Thread.sleep(2000);
                        publish("Breathe out...\n");
                        Thread.sleep(3000);
                    }
                    publish("\nExercise complete. I hope you feel a bit more refreshed!");
                    break;
            }
            return null;
        }
        @Override
        protected void process(List<String> chunks) {
            for (String text : chunks) {
                outputArea.append(text);
            }
        }
        @Override
        protected void done() {
             outputArea.append("\n\nSession check-in complete!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MentalHealthUI().setVisible(true));
    }
}