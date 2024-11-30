import java.util.ArrayList;
import java.util.Random;

public class BabyMonitor {
    // Thresholds
    private static final int MIN_CRY_DURATION = 10; // seconds
    private static final int MIN_FREQUENCY = 2000;   // Hz
    private static final int MAX_PAUSE_DURATION = 5; // seconds

    public static void main(String[] args) {
        // Simulating real-time data from a sound sensor
        System.out.println("Starting Baby Monitor...");
        simulateSensorInput();
    }

    private static void simulateSensorInput() {
        ArrayList<double[]> soundDataList = new ArrayList<>();
        Random random = new Random();

        // Simulate data from the sensor in real-time
        for (int i = 0; i < 20; i++) { // Simulate 20 sensor readings
            double frequency = random.nextInt(4000); // Random frequency between 0 and 800 Hz
            double duration = random.nextInt(10) + 1; // Random duration between 1 and 10 seconds

            // Simulate the sensor sending -1,-1 after certain intervals (end of input)
            if (i % 5 == 0) {
                frequency = -1;
                duration = -1;
            }

            // Add simulated data to the list
            soundDataList.add(new double[]{frequency, duration});

            // Process the sound data after every 5 readings
            if (i % 5 == 0) {
                System.out.println("Processing Data...");
                processSoundData(soundDataList.toArray(new double[0][0]));
                soundDataList.clear(); // Clear the list for new batch
            }

            try {
                Thread.sleep(1000); // Simulate a delay of 1 second between readings
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void processSoundData(double[][] soundData) {
        // Variables to track crying patterns
        int cryDuration = 0; // Continuous crying duration
        int pauseDuration = 0; // Pause between cries
        boolean isCrying = false; // Tracks if baby is crying

        for (double[] sound : soundData) {
            double frequency = sound[0];
            double duration = sound[1];

            // Exit condition for -1 in sample data
            if (frequency == -1) {
                break;
            }

            if (frequency >= MIN_FREQUENCY) {
                // Baby is crying
                cryDuration += duration;
                pauseDuration = 0; // Reset pause duration
                isCrying = true;
            } else {
                // Baby is not crying
                if (isCrying) {
                    pauseDuration += duration;
                    // If pause exceeds threshold, reset cry duration
                    if (pauseDuration > MAX_PAUSE_DURATION) {
                        cryDuration = 0;
                        isCrying = false;
                    }
                }
            }
        }

        // Output results based on thresholds
        if (cryDuration >= MIN_CRY_DURATION) {
            System.out.println("Baby Needs Attention!");
            System.out.println("⏱ Duration: " + cryDuration + "s");
            System.out.println("🔊 Frequency: " + (int) soundData[0][0] + " Hz");
            System.out.println("⏸ Pauses: " + pauseDuration + "s");
        } else {
            System.out.println("🟢 No Action Needed");
            System.out.println("Sound detected: " + cryDuration + "s");
            System.out.println("Frequency: " + (int) soundData[0][0] + " Hz");
            System.out.println("Pauses: " + pauseDuration + "s");
        }
    }
}