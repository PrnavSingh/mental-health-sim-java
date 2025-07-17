package mental_health_sim;

import java.util.ArrayList;

public class User {
    private String name;
    private ArrayList<String> moodHistory;

    public User(String name) {
        this.name = name;
        this.moodHistory = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getMoodHistory() {
        return this.moodHistory;
    }

    public void addMoodHistory(String currentMood) {
        this.moodHistory.add(currentMood);
    }
}