/**
 * Author: Liguo Chen
 * Student ID: 851090
 * Description: This file contains the logic of the server GUI
 */

package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

public class Windows extends JFrame {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;
    private static final int FONT_SIZE = 20;
    private static final int FIELD_WIDTH = 150;
    private static final int FIELD_HEIGHT = 30;
    private static final int BUTTON_FONT_SIZE = 17;

    private JTextField concurrentUser;

    private JButton launch;
    private JButton shutdown;

    private boolean launchButtonPressed = false;
    private boolean shutdownButtonPressed = false;

    private ServerSocket server;

    public Windows(ServerSocket server) {
        super("Dict Server");

        this.server = server;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(Windows.WIDTH, Windows.HEIGHT);
        // position the windows to the center of the screen
        setLocationRelativeTo(null);

        /* --------------------------- init server -------------------------- */

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();

        JLabel l1 = new JLabel();
        l1.setText("Max concurrent users:");
        // change font size
        l1.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        p1.add(l1);

        concurrentUser = new JTextField();
        // set default value
        concurrentUser.setText("2");
        // change input text font size
        concurrentUser.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        // change input field box size
        concurrentUser.setPreferredSize(new Dimension(Windows.FIELD_WIDTH, Windows.FIELD_HEIGHT));
        p1.add(concurrentUser);

        launch = new JButton();
        launch.setText("Start Server");
        // change button text font size
        launch.setFont(new Font("", Font.PLAIN, Windows.BUTTON_FONT_SIZE));
        // click listener
        launch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchButtonPressed = true;
            }
        });
        p2.add(launch);

        add(p1);
        add(p2);

        /* ------------------------------------------------------------------ */

        JSeparator s1 = new JSeparator(SwingConstants.HORIZONTAL);
        s1.setPreferredSize(new Dimension(Windows.WIDTH, 1));
        add(s1);

        /* ---------------------------- shutdown ---------------------------- */

        JPanel p3 = new JPanel();

        shutdown = new JButton();
        shutdown.setText("Close Server");
        // change button text font size
        shutdown.setFont(new Font("", Font.PLAIN, Windows.BUTTON_FONT_SIZE));
        // change input field box size
        shutdown.setPreferredSize(new Dimension(Windows.FIELD_WIDTH, Windows.FIELD_HEIGHT));
        // click listener
        shutdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            server.close();
                        }
                        catch (IOException ioe) {
                            System.err.println("Server failed to shut down.\n");
                        }
                    }
                });
                t.start();
            }
        });
        p3.add(shutdown);

        add(p3);


        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setVisible(true);
    }

    public boolean isLaunchButtonPressed() {
        return this.launchButtonPressed;
    }

    public boolean isShutdownButtonPressed() {
        return this.shutdownButtonPressed;
    }

    public void resetLaunchButton() {
        this.launchButtonPressed = false;
    }

    public void resetShutdownButton() {
        this.shutdownButtonPressed = false;
    }

    public int getConcurrentUser() {
        return Integer.parseInt(concurrentUser.getText());
    }
}
