package frc.team5333.driver.control;

/**
 * A container class for data being polled from a controller.
 * This allows us to detect change with a certain 'dead-zone'
 *
 * @author Jaci
 */
public class PollData {

    public float lastPollData;
    public float highest;
    public float lowest;

    public boolean poll(float data) {
        //boolean changed = lastPollData != data;
        boolean changed = false;
        if (Math.abs(lastPollData - data) > 0.05)
            changed = true;
        highest = Math.max(data, highest);
        lowest = Math.min(data, lowest);

        if (changed)
            lastPollData = data;

        return changed;
    }

    /**
     * Get a scaled value between the lowest and the highest,
     * based off of the last pollData
     */
    public float scaled() {
        return (float)(lastPollData - lowest) / (float)(highest - lowest);
    }
}
