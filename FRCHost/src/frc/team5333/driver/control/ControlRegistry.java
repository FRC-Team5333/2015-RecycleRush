package frc.team5333.driver.control;

import frc.team5333.driver.control.mapper.AbstractControlMapper;
import frc.team5333.driver.control.mapper.xbox.XMapperSplit;
import frc.team5333.driver.control.mapper.xbox.XMapperUnified;
import frc.team5333.driver.gui.GuiDriverPanel;

import java.util.ArrayList;

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
