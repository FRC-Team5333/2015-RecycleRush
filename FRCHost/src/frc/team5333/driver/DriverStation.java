package frc.team5333.driver;

import frc.team5333.driver.control.ControllerManager;
import frc.team5333.driver.gui.DriverGui;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class DriverStation {

    public static void main(String[] args) {
        Controller[] conts = ControllerEnvironment.getDefaultEnvironment().getControllers();
        ControllerManager manager = null;
        for (Controller cont : conts) {
            System.err.println("Found Controller: " + cont.getName());
            if (cont.getName().contains("Xbox") || cont.getName().contains("XBOX")) {
                System.err.println("Selecting Controller: " + cont.getName());
                manager = new ControllerManager(cont);
            }
        }

        manager.init();
        new DriverGui();
        while (true) {
            manager.poll();
        }

    }

}
