package frc.team5333.driver.gui;

import frc.team5333.driver.control.ControlRegistry;
import frc.team5333.driver.control.ControllerManager;
import frc.team5333.driver.control.PollData;
import frc.team5333.driver.control.mapper.AbstractControlMapper;
import frc.team5333.driver.net.NetworkController;
import net.java.games.input.*;
import net.java.games.input.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiMainPanel extends JPanel {

    Image teamLogo;
    JComboBox<AbstractControlMapper> mapperBox;
    public static GuiMainPanel instance;
    JTextField hostnameField;
    JTextField portField;

    public boolean connected = false;

    public GuiMainPanel() {
        this.setLayout(null);
        instance = this;
        try {
            teamLogo = ImageIO.read(GuiMainPanel.class.getClassLoader().getResourceAsStream("frc/team5333/assets/logo.jpg")).getScaledInstance(100, 50, Image.SCALE_SMOOTH);
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
        JLabel controlLabel = new JLabel("Control Scheme: ");
        controlLabel.setFont(new Font("Arial", Font.ITALIC, 15));
        controlLabel.setBounds(7, 70, 120, 20);
        this.add(controlLabel);

        mapperBox = new JComboBox<AbstractControlMapper>(ControlRegistry.mappers.toArray(new AbstractControlMapper[0]));
        mapperBox.setBounds(127, 70, 200, 25);
        mapperBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControlRegistry.setActiveMap((AbstractControlMapper) mapperBox.getSelectedItem());
            }
        });
        this.add(mapperBox);

        JLabel hostLabel = new JLabel("RIO Hostname: ");
        hostLabel.setBounds(7, 202, 120, 20);
        this.add(hostLabel);

        JLabel portLabel = new JLabel("RIO Port: ");
        portLabel.setBounds(7, 232, 120, 20);
        this.add(portLabel);

        hostnameField = new JTextField("roboRIO-5333");
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
    }

    public void refresh() {
        this.repaint();
        connected = NetworkController.socket != null && NetworkController.socket.isConnected();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;

        gr.setColor(Color.black);

        gr.drawOval(285, 236, 10, 10);

        gr.drawOval(40, 100, 50, 50);
        gr.drawOval(100, 100, 50, 50);

        PollData ldatax = ControllerManager.instance.getData("x");
        PollData ldatay = ControllerManager.instance.getData("y");

        PollData rdatax = ControllerManager.instance.getData("rx");
        PollData rdatay = ControllerManager.instance.getData("ry");

        for (int i = 0; i < ControllerManager.buttons.size(); i++) {
            Component comp = ControllerManager.buttons.get(i);
            int ind = Integer.parseInt(comp.getName());
            boolean pressed = comp.getPollData() == 1F;
            gr.drawRect(25 * ind + 20, 160, 20, 20);
            gr.setColor(pressed ? Color.green : Color.red);
            gr.fillRect(25 * ind + 20, 160, 20, 20);

            gr.setColor(Color.black);
            gr.drawString(comp.getName(), 25 * ind + 24, 175);
        }

        PollData ltrigger = ControllerManager.instance.getData("z");
        PollData rtrigger = ControllerManager.instance.getData("rz");

        gr.setColor(Color.red);
        gr.fillRect(165, ltrigger.lastPollData > 0 ? 125 : (int) (125 + (ltrigger.lastPollData * 25)), 10, (int) Math.abs(ltrigger.lastPollData * 25));
        gr.fillRect(205, rtrigger.lastPollData > 0 ? 125 : (int) (125 + (rtrigger.lastPollData * 25)), 10, (int) Math.abs(rtrigger.lastPollData * 25));

        gr.setColor(Color.black);
        gr.drawLine(160, 125, 180, 125);
        gr.drawLine(200, 125, 220, 125);

        gr.setColor(Color.blue);
        gr.fillOval(60 + (int) (ldatax.lastPollData * 20), 120 + (int) (ldatay.lastPollData * 20), 10, 10);
        gr.fillOval(120 + (int)(rdatax.lastPollData * 20), 120 + (int)(rdatay.lastPollData * 20), 10, 10);

        gr.setColor(connected ? Color.green : Color.red);
        gr.fillOval(285, 236, 10, 10);
    }

}