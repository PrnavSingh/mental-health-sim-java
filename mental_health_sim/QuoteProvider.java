package mental_health_sim;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QuoteProvider {
    private static final Map<String, String[]> moodQuotes = new HashMap<>();
    private static final String[] defaultQuotes = {
        "You are stronger than you think.",
        "Take it one step at a time.",
        "This too shall pass.",
        "Breathe. You're doing okay.",
        "It's okay to ask for help.",
        "You matter. Your feelings are valid.",
        "Progress, not perfection."
    };

    static {
        moodQuotes.put("sad", new String[]{
            "It's okay to not be okay. This feeling will pass.",
            "Every day may not be good, but there is something good in every day.",
            "You are not alone in this. Reach out if you need to."
        });
        moodQuotes.put("anxious", new String[]{
            "Focus on your breath. In this moment, you are safe.",
            "Worrying does not take away tomorrow's troubles, it takes away today's peace.",
            "This anxiety is a feeling, not a fact."
        });
        moodQuotes.put("angry", new String[]{
            "Responding with anger is a choice. You can choose peace.",
            "Feel the anger, but don't let it become your response.",
            "Take a deep breath before you say or do anything."
        });
        moodQuotes.put("happy", new String[]{
            "Hold on to that happiness and let it shine!",
            "Share your joy with others. It's contagious!",
            "Awesome! Keep that positive energy flowing."
        });
        moodQuotes.put("tired", new String[]{
            "Rest is not weakness. It's essential for strength.",
            "Listen to your body. It's okay to take a break.",
            "Allow yourself this time to recharge."
        });
    }

    public String getQuoteForMood(String mood) {
        Random rand = new Random();
        String normalizedMood = mood.toLowerCase().trim();
        String[] quotes = moodQuotes.getOrDefault(normalizedMood, defaultQuotes);
        return quotes[rand.nextInt(quotes.length)];
    }

    public String getRandomQuote() {
        Random rand = new Random();
        return defaultQuotes[rand.nextInt(defaultQuotes.length)];
    }
}