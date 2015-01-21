package frc.team5333.lib.profiler;

/**
 * An entry for the ProfilerMath class
 *
 * @author Jaci
 */
public class ProfilerEntry {

    private ProfilerSection section;
    private float load;

    protected ProfilerEntry() {}

    public ProfilerSection getSection() {
        return section;
    }

    protected void setSection(ProfilerSection section) {
        this.section = section;
    }

    public float getLoad() {
        return load;
    }

    public float getLoadPerc() {
        return getLoad() * 100F;
    }

    protected void setLoad(float load) {
        this.load = load;
    }

}
