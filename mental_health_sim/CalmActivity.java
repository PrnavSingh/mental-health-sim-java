
package mental_health_sim;

public class CalmActivity {
    

    public void breathingExercise() {
        System.out.println("Let's do a simple breathing exercise to calm your mind.");
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("Breathe in deeply...");
                Thread.sleep(3000);
                System.out.println("Hold your breath...");
                Thread.sleep(2000);
                System.out.println("Breathe out slowly...");
                Thread.sleep(4000);
            }
            System.out.println("\nI hope that helped you feel a bit more centered.");
        } catch (InterruptedException e) {
            System.out.println("Exercise interrupted.");
            Thread.currentThread().interrupt(); 
        }
    }


    public void groundingExercise() {
        System.out.println("Let's try a grounding exercise to connect with the present moment.");
        try {
            Thread.sleep(1000);
            System.out.println("Look around and name 5 things you can see.");
            Thread.sleep(5000);
            System.out.println("Now, listen and identify 4 things you can hear.");
            Thread.sleep(5000);
            System.out.println("Next, focus on 3 things you can feel (like your feet on the floor).");
            Thread.sleep(5000);
            System.out.println("Identify 2 things you can smell.");
            Thread.sleep(5000);
            System.out.println("Finally, name 1 thing you can taste.");
            Thread.sleep(3000);
            System.out.println("\nThis helps bring you back to the present. You are here, you are safe.");
        } catch (InterruptedException e) {
            System.out.println("Exercise interrupted.");
            Thread.currentThread().interrupt();
        }
    }
    

    public void positiveJournalingPrompt() {
        System.out.println("Let's try a small reflection exercise.");
        System.out.println("Think about one small thing that brought you a moment of comfort or joy today.");
        System.out.println("It could be anything - the taste of your coffee, a kind word, or a nice song.");
        System.out.println("Hold onto that feeling for a moment. Acknowledging small positives can make a big difference.");
    }
    

    public void releasingAngerExercise() {
        System.out.println("Let's try a quick exercise to release that tension.");
        System.out.println("Imagine you are holding that anger in your fists. Clench them tightly for 5 seconds.");
        try {
            Thread.sleep(5000);
            System.out.println("Now, take a deep breath in, and as you breathe out, open your hands and let all that tension go.");
            Thread.sleep(3000);
            System.out.println("Repeat this a couple of times. You are in control of your response.");
        } catch (InterruptedException e) {
            System.out.println("Exercise interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}