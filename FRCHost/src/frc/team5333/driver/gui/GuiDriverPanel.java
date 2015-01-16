package frc.team5333.driver.gui;

import frc.team5333.driver.DriverStation;
import frc.team5333.driver.control.ControlRegistry;
import frc.team5333.driver.control.ControllerManager;
import frc.team5333.driver.control.PollData;
import frc.team5333.driver.control.drive.ThrottleScale;
import frc.team5333.driver.control.mapper.AbstractControlMapper;
import frc.team5333.driver.net.NetworkController;
import net.java.games.input.Component;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiDriverPanel extends JPanel {

    Image teamLogo;
    JComboBox<AbstractControlMapper> mapperBox;
    JComboBox<ControllerManager> managerBox;
    public static GuiDriverPanel instance;
    JTextField hostnameField;
    JTextField portField;
    ControllerManager manager;

    public boolean connected = false;

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

        JLabel portLabel = new JLabel("RIO Port: ");
        portLabel.setBounds(7, 232, 120, 20);
        this.add(portLabel);

        hostnameField = new JTextField("roboRIO-5333.local");
        hostnameField.setBounds(100, 200, 200, 25);
        this.add(hostnameField);

        portField = new JTextField("5801");
        portField.setBounds(100, 230, 70, 25);
        this.add(portField);

        JButton connectRIO = new JButton("Connect");
        connectRIO.setBounds(170, 230, 115, 25);
        connectRIO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NetworkController.setData(hostnameField.getText(), Integer.parseInt(portField.getText()));
                NetworkController.connect();
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
        connected = NetworkController.socket != null && !NetworkController.socket.isClosed();
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
        
        gr.setColor(connected ? Color.green : Color.red);
        gr.fillOval(285,  236,  10, 10);
    }

}