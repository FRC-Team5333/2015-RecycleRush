package frc.team5333.driver.control.mapper.xbox;

import frc.team5333.driver.control.PollData;
import frc.team5333.driver.control.mapper.AbstractControlMapper;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import static frc.team5333.driver.net.NetworkController.*;

public class XMapperSplit extends AbstractControlMapper {

    @Override
    public String getFriendlyName() {
        return "Xbox - Split";
    }

    @Override
    public String getFriendlyDescription() {
        return "Uses the two analog sticks separately to control both sides of the Tank Drive";
    }

    @Override
    public void handleInput(PollData data, Component component, Controller controller) {
        switch (component.getName()) {
            case "y":
                sendMessage("leftThrottle", data.lastPollData);
                break;
            case "ry":
                sendMessage("rightThrottle", data.lastPollData);
                break;
        }
    }

}
