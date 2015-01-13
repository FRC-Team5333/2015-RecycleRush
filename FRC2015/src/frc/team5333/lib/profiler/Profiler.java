package frc.team5333.lib.profiler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A profiler debugging tool for seeing how long different pieces of code take to execute
 *
 * @author Jaci
 */
public class Profiler {

    String name;

    LinkedHashMap<String, ProfilerSection> sections = new LinkedHashMap<String, ProfilerSection>();

    public Profiler(String name) {
        this.name = name;
    }

    public void beginSection(String name) {
        long time = System.nanoTime();
        ProfilerSection section = new ProfilerSection(name);
        section.start(time);
        sections.put(name, section);
    }

    public long endSection(String name) {
        long time = System.nanoTime();
        ProfilerSection section = sections.get(name);
        section.stop(time);
        return section.elapsedTime();
    }

    public List<ProfilerSection> getAllSections() {
        ArrayList<ProfilerSection> list = new ArrayList<ProfilerSection>();
        for (Map.Entry<String, ProfilerSection> entry : sections.entrySet())
            list.add(entry.getValue());
        return list;
    }

    public ProfilerSection getSection(String name) {
        return sections.get(name);
    }

    public void reset() {
        sections.clear();
    }
}