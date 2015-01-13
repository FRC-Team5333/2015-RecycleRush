package frc.team5333.driver.control;

import frc.team5333.driver.control.mapper.AbstractControlMapper;
import frc.team5333.driver.control.mapper.xbox.XMapperSplit;
import frc.team5333.driver.control.mapper.xbox.XMapperUnified;
import frc.team5333.driver.gui.DriverGui;
import frc.team5333.driver.gui.GuiMainPanel;

import java.util.ArrayList;

public class ControlRegistry {

    public static ArrayList<AbstractControlMapper> mappers;
    public static AbstractControlMapper activeMapper;

    static {
        mappers = new ArrayList<AbstractControlMapper>();
        mappers.add(new XMapperSplit());
        mappers.add(new XMapperUnified());

        activeMapper = mappers.get(0);
    }

    public static void setActiveMap(AbstractControlMapper mapper) {
        activeMapper = mapper;
        GuiMainPanel.instance.refresh();
    }

}
