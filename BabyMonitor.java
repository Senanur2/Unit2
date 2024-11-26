import java.util.ArrayList;

public class BabyMonitor {
    // Thresholds
    private static final int MIN_CRY_DURATION = 10; // seconds
    private static final int MIN_FREQUENCY = 500;   // Hz
    private static final int MAX_PAUSE_DURATION = 5; // seconds

    public static void main(String[] args) {
        // Datasets
        double[][] cryingData = {
                {530, 6}, {530, 6}, {-1, -1} // Total duration = 12s
        };
        double[][] nonCryingData = {
                {480, 8}, {200, 6}, {-1, -1}
        };

        // Process datasets
        System.out.println("Processing Crying Data:");
        processSoundData(cryingData);

        System.out.println("\nProcessing Non-Crying Data:");
        processSoundData(nonCryingData);
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
            System.out.println("🔊 Frequency: " + (int)soundData[0][0] + " Hz");
            System.out.println("⏸ Pauses: " + pauseDuration + "s");
        } else {
            System.out.println("🟢 No Action Needed");
            System.out.println("Sound detected: " + cryDuration + "s");
            System.out.println("Frequency: " + (int)soundData[0][0] + " Hz");
            System.out.println("Pauses: " + pauseDuration + "s");
        }
    }
}