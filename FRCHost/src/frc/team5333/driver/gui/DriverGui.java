package frc.team5333.driver.gui;

import javax.swing.*;

/**
 * The JFrame container for the DriverStation GUI
 *
 * @author Jaci
 */
public class DriverGui extends JFrame {

    public DriverGui() {
        this.setTitle("Team 5333 Driver Station");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.add(new GuiDriverPanel());

        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

}
