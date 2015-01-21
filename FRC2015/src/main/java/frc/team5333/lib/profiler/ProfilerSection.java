package frc.team5333.lib.profiler;

/**
 * A section for the Profiler object, used for tracking segmented loads
 *
 * @author Jaci
 */
public class ProfilerSection {

    private String name;
    private boolean started;
    private boolean stopped;
    private long startTime;
    private long stopTime;

    public ProfilerSection(String profile) {
        this.name = profile;
    }

    public void start(long time) {
        started = true;
        startTime = time;
    }

    public void stop(long time) {
        if (!started)
            throw new UnsupportedOperationException("Section not started!");
        stopped = true;
        stopTime = time;
    }

    public long elapsedTime() {
        if (!completed())
            throw new UnsupportedOperationException("Section not completed!");
        return stopTime - startTime;
    }

    public long elapsedTimeMS() {
        return elapsedTime() / 1000000;
    }

    public boolean started() {
        return started;
    }

    public boolean completed() {
        return started && stopped;
    }

    public long startTime() {
        return startTime;
    }

    public long startTimeMS() {
        return startTime() / 1000000;
    }

    public long stopTime() {
        return stopTime;
    }

    public long stopTimeMS() {
        return stopTime() / 1000;
    }

    public String getSectionName() {
        return name;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProfilerSection[name=");
        builder.append(getSectionName());
        if (started()) {
            builder.append(",start="+startTimeMS());
        }
        if (completed()) {
            builder.append(",stop="+stopTimeMS()+",elapsedNano="+elapsedTime()+",elapsedMS="+elapsedTimeMS());
        }
        builder.append("]");
        return builder.toString();
    }
}