package frc.team5333.driver.control.mapper;

import frc.team5333.driver.control.ControllerManager;
import frc.team5333.driver.control.PollData;
import frc.team5333.driver.control.drive.ThrottleScale;
import frc.team5333.driver.gui.GuiDriverPanel;
import net.java.games.input.Component;
import net.java.games.input.Controller;

/**
 * A standard class that is called when input is received from the controller.
 * This implementation allows for custom 'control-schemes', binding different buttons
 * and axis to different tasks
 *
 * @author Jaci
 */
public abstract class AbstractControlMapper {

    public abstract String getFriendlyName();
    public abstract String getFriendlyDescription();

    public void preHandleInput(PollData data, Component component, Controller controller, ControllerManager manager) {
        switch (component.getIdentifier().getName()) {
            case "4":
                if (data.lastPollData >= 0.5F) {
                    ThrottleScale.decrement();
                    GuiDriverPanel.instance.refresh();
                }
                break;
            case "5":
                if (data.lastPollData >= 1F) {
                    ThrottleScale.increment();
                    GuiDriverPanel.instance.refresh();
                }
                break;
        }
        handleInput(data, component, controller, manager);
    }

    public abstract void handleInput(PollData data, Component component, Controller controller, ControllerManager manager);
    public void finalizeInput(ControllerManager manager) {}

    public String toString() {
        return getFriendlyName();
    }

}
