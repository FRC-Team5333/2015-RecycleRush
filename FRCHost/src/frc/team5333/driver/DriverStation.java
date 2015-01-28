package frc.team5333.driver;

import frc.team5333.driver.control.ControllerManager;
import frc.team5333.driver.gui.DriverGui;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * The root class for our custom Driver Station software, allowing
 * us to gain more control over the robot and see more information
 *
 * @author Jaci
 */
public class DriverStation {

    public static ArrayList<ControllerManager> managers = new ArrayList<ControllerManager>();
    public static ControllerManager activeManager;

    public static void main(String[] args) {
        initControllers();

        DriverGui gui = new DriverGui();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (ControllerManager manager : managers)
                        manager.poll();

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void initControllers() {
        Controller[] controllers = createDefaultEnvironment().getControllers();
        for (Controller c : controllers) {
            if (c.getType() == Controller.Type.GAMEPAD) {
                ControllerManager manager = new ControllerManager(c);

                boolean replaced = false;
                for (ControllerManager man : managers) {
                    if (man.isEqual(manager)) {
                        replaced = true;
                        manager = man;
                        break;
                    }
                }

                if (!replaced) {
                    managers.add(manager);
                }
            }
        }

        ArrayList<ControllerManager> newControllers = new ArrayList<ControllerManager>();
        for (ControllerManager man : managers) {
            boolean found = false;
            for (Controller c : controllers)
                if (man._controller == c)
                    found = true;

            if (found)
                newControllers.add(man);

            if (man.isNew)
                man.init();
        }

        managers = newControllers;
    }

    @SuppressWarnings("unchecked")
    public static ControllerEnvironment createDefaultEnvironment() {
        try {
            Constructor<ControllerEnvironment> constructor = (Constructor<ControllerEnvironment>)
                    Class.forName("net.java.games.input.DefaultControllerEnvironment").getDeclaredConstructors()[0];

            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch(Exception e) {}

        return null;
    }

}
