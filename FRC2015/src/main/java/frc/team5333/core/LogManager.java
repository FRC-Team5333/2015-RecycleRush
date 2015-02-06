package frc.team5333.core;

import frc.team5333.core.net.EnumDispatchers;
import jaci.openrio.toast.lib.log.LogHandler;

public class LogManager implements LogHandler {

    @Override
    public void onLog(String level, String message) {
        EnumDispatchers.DEBUG.get().broadcast(message);
    }

}
