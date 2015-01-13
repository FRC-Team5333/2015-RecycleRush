package frc.team5333.driver.control.mapper;

import frc.team5333.driver.control.PollData;
import net.java.games.input.Component;
import net.java.games.input.Controller;

public abstract class AbstractControlMapper {

    public abstract String getFriendlyName();
    public abstract String getFriendlyDescription();

    public abstract void handleInput(PollData data, Component component, Controller controller);

    public String toString() {
        return getFriendlyName();
    }

}
