package frc.team5333.core.net.command;

import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.core.net.NetworkedClient;
import groovy.lang.GroovyObject;
import jaci.openrio.toast.core.loader.groovy.GroovyLoader;

import static frc.team5333.core.RobotImpl.log;

public class CommandAutoRecord implements ICommand {

    @Override
    public String getCommandPrefix() {
        return "record";
    }

    @Override
    public void runCommand(String[] args, NetworkedClient client) {
        new Thread() {
            public void run() {
                try {
                    GroovyObject object = (GroovyObject) GroovyLoader.getObject("Routines").invokeMethod("getContext", args[0]);
                    object.invokeMethod("setControllers", RobotDriveTracker.getControllers());
                    log().info("Autonomous Recording in: ");
                    Thread.sleep(1000);
                    log().info("3");
                    Thread.sleep(1000);
                    log().info("2");
                    Thread.sleep(1000);
                    log().info("1");
                    Thread.sleep(1000);
                    log().info("GO");
                    object.invokeMethod("startRecording", null);
                } catch (InterruptedException e) {
                    log().exception(e);
                }
            }
        }.start();
    }

}
