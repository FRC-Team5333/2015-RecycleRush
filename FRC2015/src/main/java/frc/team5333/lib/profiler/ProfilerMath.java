package frc.team5333.lib.profiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used for calculating load and comparing ProfilerSections
 *
 * @author Jaci
 */
public class ProfilerMath {

    ArrayList<ProfilerSection> sections = new ArrayList<ProfilerSection>();
    ArrayList<ProfilerEntry> entries = new ArrayList<ProfilerEntry>();

    long totalMSTime;

    public ProfilerMath() {}

    public void loadProfiler(Profiler p) {
        sections = new ArrayList<ProfilerSection>(p.getAllSections());
    }

    public void loadSections(ProfilerSection... sc) {
        sections.addAll(Arrays.asList(sc));
    }

    public void clear() {
        sections.clear();
    }

    public void calculate() {
        for (ProfilerSection sec : sections) {
            totalMSTime += sec.elapsedTimeMS();
        }

        for (ProfilerSection sec : sections) {
            ProfilerEntry entry = new ProfilerEntry();
            entry.setSection(sec);
            entry.setLoad((float)sec.elapsedTimeMS() / (float)totalMSTime);
            entries.add(entry);
        }
    }

    public ProfilerEntry getEntryFor(String sectionName) {
        for (ProfilerEntry entry : entries)
            if (entry.getSection().getSectionName().equals(sectionName)) return entry;

        return null;
    }

    public List<ProfilerEntry> getAllEntries() {
        return entries;
    }

}