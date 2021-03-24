/**
 * Author: Liguo Chen
 * Student ID: 851090
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Windows extends JFrame {

    private static final int WIDTH  = 300;
    private static final int HEIGHT = 400;

    private JLabel word;
    private JTextField entry;
    private JButton search;
    private JTextArea explanation;

    private boolean buttonPressed = false;

    public Windows() {
        super("Dictionary");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(Windows.WIDTH, Windows.HEIGHT);
        // position the windows to the center of the screen
        setLocationRelativeTo(null);

        word = new JLabel();
        word.setText("Word:");
        word.setName("inputFieldName");
        // change font size
        word.setFont(new Font("", Font.PLAIN, 18));
        add(word);

        entry = new JTextField();
        // change input field box size
        entry.setPreferredSize(new Dimension(100, 25));
        add(entry);

        search = new JButton();
        search.setText("Search");
        // click listener
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                buttonPressed = true;
            }
        });
        add(search);

        // use text area to wrap long text
        explanation = new JTextArea("no explanation", 0, 18);
        // change font size
        explanation.setFont(new Font("", Font.PLAIN, 18));
        explanation.setWrapStyleWord(true);
        explanation.setLineWrap(true);
        add(explanation);

        setLayout(new FlowLayout());
        setVisible(true);
    }

    public boolean isButtonPressed() {
        return this.buttonPressed;
    }

    public void resetButton() {
        this.buttonPressed = false;
    }

    public String getQuery() {
        return this.entry.getText();
    }

    public void setResult(String result) {
        explanation.setText(result);
    }
}
