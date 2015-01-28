package frc.team5333.core.io;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team5333.lib.Ports;

public interface IOCallback {

    public void tick(Ports port);

}
