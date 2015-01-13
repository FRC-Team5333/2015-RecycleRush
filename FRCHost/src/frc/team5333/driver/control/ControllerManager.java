package frc.team5333.driver.control;

import frc.team5333.driver.gui.GuiMainPanel;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ControllerManager {

    Controller _controller;
    public HashMap<Component.Identifier, PollData> _pollData;

    ArrayList<String> ignoredIDs = new ArrayList<>();
    public static ControllerManager instance;

    public static ArrayList<Component> buttons = new ArrayList<Component>();

    public ControllerManager(Controller controller) {
        _controller = controller;
        instance = this;
    }

    public void init() {
        _controller.poll();
        _pollData = new HashMap<Component.Identifier, PollData>();
        for (Component c : _controller.getComponents()) {
            if (ignoredIDs.contains(c.getName()))
                continue;

            System.err.println("Input Found: " + c.getName());

            if (c.getIdentifier() instanceof Component.Identifier.Button)
                buttons.add(c);

            PollData data = new PollData();
            data.poll(c.getPollData());
            _pollData.putIfAbsent(c.getIdentifier(), data);
        }
    }

    public synchronized void poll() {
        _controller.poll();
        for (Component c : _controller.getComponents()) {
            if (ignoredIDs.contains(c.getName()))
                continue;

            boolean changed = _pollData.get(c.getIdentifier()).poll(c.getPollData());
            if (changed) {
                if (ControlRegistry.activeMapper != null) {
                    ControlRegistry.activeMapper.handleInput(_pollData.get(c.getIdentifier()), c, _controller);
                }
                GuiMainPanel.instance.refresh();
            }
        }
    }

    public PollData getData(String id) {
        for (Map.Entry<Component.Identifier, PollData> entry : _pollData.entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(id)) return entry.getValue();
        }
        return null;
    }

}
