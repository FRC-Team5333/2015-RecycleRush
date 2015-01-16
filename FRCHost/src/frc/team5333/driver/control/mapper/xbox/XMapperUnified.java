package frc.team5333.driver.control.mapper.xbox;

import frc.team5333.driver.control.ControllerManager;
import frc.team5333.driver.control.PollData;
import frc.team5333.driver.control.mapper.AbstractControlMapper;
import net.java.games.input.Component;
import net.java.games.input.Controller;

public class XMapperUnified extends AbstractControlMapper {

    @Override
    public String getFriendlyName() {
        return "Xbox - Unified";
    }

    @Override
    public String getFriendlyDescription() {
        return "Uses the left analog stick for motor balancing and right trigger for throttle";
    }

    @Override
    public void handleInput(PollData data, Component component, Controller controller, ControllerManager manager) {
        switch (component.getName()) {

        }
    }

}
