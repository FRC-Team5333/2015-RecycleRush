package frc.team5333.driver.control.mapper.xbox;

import frc.team5333.driver.control.ControllerManager;
import frc.team5333.driver.control.PollData;
import frc.team5333.driver.control.drive.DriveController;
import frc.team5333.driver.control.mapper.AbstractControlMapper;
import net.java.games.input.Component;
import net.java.games.input.Controller;

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
    public void handleInput(PollData data, Component component, Controller controller, ControllerManager manager) {
        switch (component.getIdentifier().getName()) {
            case "y":
                //sendMessage(NetIDs.DRIVE_LEFT, (float) -ThrottleScale.scale(data.lastPollData));
            	DriveController.setLeft(data.lastPollData);
                break;
            case "ry":
                //sendMessage(NetIDs.DRIVE_RIGHT, (float) -ThrottleScale.scale(data.lastPollData));
            	DriveController.setRight(data.lastPollData);
                break;
        }
    }

}
