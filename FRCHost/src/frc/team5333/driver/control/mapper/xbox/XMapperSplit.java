package frc.team5333.driver.control.mapper.xbox;

import frc.team5333.driver.control.ControllerManager;
import frc.team5333.driver.control.PollData;
import frc.team5333.driver.control.drive.DriveController;
import frc.team5333.driver.control.drive.ThrottleScale;
import frc.team5333.driver.control.mapper.AbstractControlMapper;
import net.java.games.input.Component;
import net.java.games.input.Controller;

public class XMapperSplit extends AbstractControlMapper {

    float z;

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
                if (!DriveController.isLocked())
            	    DriveController.setLeft(-data.lastPollData);
                break;
            case "ry":
                if (!DriveController.isLocked())
            	    DriveController.setRight(-data.lastPollData);
                break;
            case "0":
                if (data.lastPollData > 0.5)
                    DriveController.toggleLock();
                break;
            case "1":
                if (data.lastPollData > 0.5) {
                    DriveController.setRight(0);
                    DriveController.setLeft(0);
                    DriveController.toggleLock();
                }
                break;
            case "2":
                if (data.lastPollData > 0.5)
                    ThrottleScale.setScale(50);
                break;
        }
    }

    public void finalizeInput(ControllerManager manager) {
        if (manager.getData("z").changed || (manager.getData("rz") != null && manager.getData("rz").changed)) {
            z = manager.getData("z").lastPollData;
            if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
                z = -(z + 1) / 2;
                z += (manager.getData("rz").lastPollData + 1) / 2;
            }
            DriveController.setLift(z);
        }

        if (manager.getData("6").changed || manager.getData("7").changed) {
            float clamp = 0F;
            clamp += manager.getData("6").lastPollData;
            clamp -= manager.getData("7").lastPollData;
            DriveController.setClamp(clamp);
        }
    }

}
