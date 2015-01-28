package frc.team5333.driver.control;

import frc.team5333.driver.control.mapper.AbstractControlMapper;
import frc.team5333.driver.control.mapper.xbox.XMapperSplit;
import frc.team5333.driver.control.mapper.xbox.XMapperUnified;

import java.util.ArrayList;

/**
 * A simple registry for the {@link frc.team5333.driver.control.mapper.AbstractControlMapper}
 * class objects
 *
 * @author Jaci
 */
public class ControlRegistry {

    public static ArrayList<AbstractControlMapper> mappers;

    static {
        mappers = new ArrayList<AbstractControlMapper>();
        mappers.add(new XMapperSplit());
        mappers.add(new XMapperUnified());
    }

    public static AbstractControlMapper getDefault() {
        return mappers.get(0);
    }

}
