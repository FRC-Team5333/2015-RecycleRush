package frc.team5333.driver.control;

import frc.team5333.driver.control.mapper.AbstractControlMapper;
import frc.team5333.driver.gui.GuiDriverPanel;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The object responsible for receiving and dealing with input from a controller.
 * This is useful in storing data and binding it to a controller, as well as customizing
 * all of the data associated with the controller and how it is displayed
 *
 * @author Jaci
 */
public class ControllerManager {

    public Controller _controller;
    public HashMap<Component.Identifier, PollData> _pollData;

    ArrayList<String> ignoredIDs = new ArrayList<>();

    public ArrayList<Component> buttons = new ArrayList<Component>();

    public AbstractControlMapper activeMapper = ControlRegistry.getDefault();

    String name;

    public boolean isNew = true;

    public ControllerManager(Controller controller) {
        this(controller.getName(), controller);
    }

    public ControllerManager(String name, Controller controller) {
        _controller = controller;
        this.name = name;
    }

    public String getName() {
        return name;
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
        isNew = false;
    }

    public synchronized void poll() {
        _controller.poll();
        for (Component c : _controller.getComponents()) {
            if (ignoredIDs.contains(c.getName()))
                continue;

            boolean changed = _pollData.get(c.getIdentifier()).poll(c.getPollData());
            if (changed) {
                if (activeMapper != null) {
                    activeMapper.preHandleInput(_pollData.get(c.getIdentifier()), c, _controller, this);
                }
                GuiDriverPanel.instance.refresh();
            }
        }
        if (activeMapper != null)
            activeMapper.finalizeInput(this);
    }

    public PollData getData(String id) {
        for (Map.Entry<Component.Identifier, PollData> entry : _pollData.entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(id)) return entry.getValue();
        }
        return null;
    }

    public String toString() {
        return getName();
    }

    public boolean isEqual(ControllerManager compare) {
        return compare._controller.getPortNumber() == this._controller.getPortNumber() && Objects.equals(this.getName(), compare.getName());
    }

}
