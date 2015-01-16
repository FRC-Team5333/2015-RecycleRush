package frc.team5333.driver.control.mapper;

import frc.team5333.NetIDs;
import frc.team5333.driver.control.ControllerManager;
import frc.team5333.driver.control.PollData;
import frc.team5333.driver.control.drive.ThrottleScale;
import frc.team5333.driver.gui.GuiDriverPanel;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import static frc.team5333.driver.net.NetworkController.sendMessage;

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

    public String toString() {
        return getFriendlyName();
    }

}
