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
                if (!DriveController.isLocked())
            	    DriveController.setLeft(data.lastPollData);
                break;
            case "ry":
                //sendMessage(NetIDs.DRIVE_RIGHT, (float) -ThrottleScale.scale(data.lastPollData));
                if (!DriveController.isLocked())
            	    DriveController.setRight(data.lastPollData);
                break;
            case "0":
                if (data.lastPollData > 0.5)
                    DriveController.toggleLock();
                break;

            case "x":
                if (DriveController.isLocked())
                    DriveController.setClamp(data.lastPollData);
                break;

            case "rx":
                if (DriveController.isLocked())
                    DriveController.setLift(data.lastPollData);
                break;

        }
    }

}
