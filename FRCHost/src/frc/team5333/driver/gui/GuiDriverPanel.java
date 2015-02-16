package frc.team5333.driver.gui;

import frc.team5333.NetIDs;
import frc.team5333.driver.DriverStation;
import frc.team5333.driver.control.ControlRegistry;
import frc.team5333.driver.control.ControllerManager;
import frc.team5333.driver.control.drive.DriveController;
import frc.team5333.driver.control.drive.ThrottleScale;
import frc.team5333.driver.control.mapper.AbstractControlMapper;
import frc.team5333.driver.net.EnumNetworkControllers;
import frc.team5333.driver.net.NetworkController;
import frc.team5333.kinect.JointGUI;
import frc.team5333.kinect.KiNETct;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main JPanel for the {@link frc.team5333.driver.gui.DriverGui}
 *
 * @author Jaci
 */
public class GuiDriverPanel extends JPanel {

    Image teamLogo;
    JComboBox<AbstractControlMapper> mapperBox;
    JComboBox<ControllerManager> managerBox;
    public static GuiDriverPanel instance;
    public JTextField hostnameField;
    ControllerManager manager;
    JTextArea consoleLog;
    JScrollPane consoleScroll;

    JointGUI kinectGUI;
    public JTextField kinectHostname;


    public GuiDriverPanel() {
        this.setLayout(null);
        instance = this;
        try {
            teamLogo = ImageIO.read(GuiDriverPanel.class.getClassLoader().getResourceAsStream("frc/team5333/assets/logo.jpg")).getScaledInstance(100, 50, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(teamLogo));
            imageLabel.setBounds(7, 5, 100, 50);
            this.add(imageLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initElements();

        this.setBackground(new Color(255, 255, 255));
        this.setPreferredSize(new Dimension(600, 500));
        this.setEnabled(true);
        this.setVisible(true);
    }

    public void log(String s) {
        consoleLog.append(s + "\n");
        consoleScroll.getVerticalScrollBar().setValue(consoleScroll.getVerticalScrollBar().getMaximum());
    }

    public void initElements() {
        JLabel controllerLabel = new JLabel("Controller: ");
        controllerLabel.setFont(new Font("Arial", Font.ITALIC, 15));
        controllerLabel.setBounds(7, 100, 120, 20);
        this.add(controllerLabel);

        managerBox = new JComboBox<ControllerManager>(DriverStation.managers.toArray(new ControllerManager[0]));
        managerBox.setBounds(127, 100, 200, 25);
        this.manager = (ControllerManager) managerBox.getSelectedItem();
        managerBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DriverStation.activeManager = (ControllerManager) managerBox.getSelectedItem();
                select((ControllerManager) managerBox.getSelectedItem());
            }
        });
        this.add(managerBox);

        JLabel controlLabel = new JLabel("Control Scheme: ");
        controlLabel.setFont(new Font("Arial", Font.ITALIC, 15));
        controlLabel.setBounds(7, 70, 120, 20);
        this.add(controlLabel);

        mapperBox = new JComboBox<AbstractControlMapper>(ControlRegistry.mappers.toArray(new AbstractControlMapper[0]));
        mapperBox.setBounds(127, 70, 200, 25);
        mapperBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manager == null)
                    return;
                manager.activeMapper = (AbstractControlMapper) mapperBox.getSelectedItem();
            }
        });
        this.add(mapperBox);

        JLabel hostLabel = new JLabel("RIO Hostname: ");
        hostLabel.setBounds(7, 202, 120, 20);
        this.add(hostLabel);

        hostnameField = new JTextField("roboRIO-5333.local");
        if (System.getProperty("os.name").toUpperCase().startsWith("MAC")) {
            hostnameField.setText("10.53.33.20");
            NetworkController.setData("10.53.33.20");
        } else
            NetworkController.setData("roboRIO-5333.local");

        hostnameField.setBounds(100, 200, 200, 25);
        this.add(hostnameField);

        JButton connectRIO = new JButton("Connect");
        connectRIO.setBounds(7, 230, 130, 25);
        connectRIO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    public void run() {
                        NetworkController.setData(hostnameField.getText());
                        EnumNetworkControllers.connectAll();
                    }
                }.start();
            }
        });
        this.add(connectRIO);

        JButton refreshControllers = new JButton("Refresh Controllers");
        refreshControllers.setBounds(7, 130, 200, 25);
        refreshControllers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reinitControllers();
            }
        });
        this.add(refreshControllers);

        consoleLog = new JTextArea();
        consoleLog.setEditable(false);

        consoleScroll = new JScrollPane(consoleLog);
        consoleScroll.setBounds(7, 350, 585, 115);
        consoleScroll.setBackground(new Color(240, 240, 240));
        this.add(consoleScroll);

        JTextField commandInput = new JTextField();
        commandInput.setBounds(7, 470, 585, 20);
        commandInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (EnumNetworkControllers.DEBUG.connected()) {
                    EnumNetworkControllers.DEBUG.getController().sendMessage(NetIDs.COMMAND, commandInput.getText());
                    commandInput.setText("");
                }
            }
        });
        this.add(commandInput);

        kinectGUI = new JointGUI();
        kinectGUI.setBounds(370, 70, 220, 270);
        this.add(kinectGUI);

        JLabel kinectHostLabel = new JLabel("Kinect Host:");
        kinectHostLabel.setBounds(7, 262, 120, 20);
        this.add(kinectHostLabel);

        kinectHostname = new JTextField("localhost");
        kinectHostname.setBounds(100, 260, 200, 25);
        this.add(kinectHostname);

        JButton kinectConnect = new JButton("Kinect");
        kinectConnect.setBounds(165, 230, 130, 25);
        kinectConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new KiNETct(kinectGUI).start();
            }
        });
        this.add(kinectConnect);

        JButton kinectCalibrate = new JButton("Calibrate");
        kinectCalibrate.setBounds(165, 290, 130, 25);
        kinectCalibrate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    public void run() {
                        try {
                            Color bg = kinectGUI.getBackground();
                            kinectGUI.setBackground(new Color(105, 50, 105));
                            Thread.sleep(3000);
                            KiNETct.calibrateOffsetAll();
                            kinectGUI.setBackground(new Color(50, 105, 105));
                            Thread.sleep(3000);
                            KiNETct.calibrateScaleAll();
                            kinectGUI.setBackground(bg);
                        } catch (InterruptedException e1) {
                        }
                    }
                }.start();
            }
        });
        this.add(kinectCalibrate);

        JButton kinectSelect = new JButton("Select");
        kinectSelect.setBounds(165, 315, 130, 25);
        kinectSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    public void run() {
                        try {
                            Color bg = kinectGUI.getBackground();
                            kinectGUI.setBackground(new Color(0, 80, 0));
                            Thread.sleep(3000);
                            KiNETct.selectActive();
                            kinectGUI.setBackground(bg);
                        } catch (InterruptedException e1) {
                        }
                    }
                }.start();
            }
        });
        this.add(kinectSelect);

    }

    public void reinitControllers() {
        DriverStation.initControllers();
        managerBox.setModel(new DefaultComboBoxModel<ControllerManager>(DriverStation.managers.toArray(new ControllerManager[0])));
    }

    public void select(ControllerManager manager) {
        if (manager == null)
            return;

        mapperBox.setSelectedItem(manager.activeMapper);
        this.manager = manager;
        refresh();
    }

    public void refresh() {
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;

        paintController(gr);
    }

    public void paintController(Graphics2D gr) {
        gr.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 15));
        gr.drawString("Throttle Scale: " + ThrottleScale.getScale() * 100 + "%", 7, 180);
        
        for (int i = 0; i < EnumNetworkControllers.values().length; i++) {
            EnumNetworkControllers cont = EnumNetworkControllers.values()[i];
            gr.setColor(cont.connected() ? Color.green : Color.red);
            gr.fillOval(8 + (i * 30), 325, 15, 15);
            gr.setColor(Color.black);
            gr.fillOval(11 + (i * 30), 328, 9, 9);
            gr.setFont(new Font("Arial", Font.ITALIC, 11));
            gr.drawString(cont.getShortName(), 7 + (i * 30), 320);
        }

        gr.setFont(new Font("Arial", Font.BOLD, 14));
        gr.setColor(DriveController.isLocked() ? Color.RED : Color.GREEN);
        gr.fillRect(7, 290, 50, 20);

        gr.setColor(Color.BLACK);
        gr.drawString(DriveController.isLocked() ? "Lock" : "Drive", 10, 305);
    }

}