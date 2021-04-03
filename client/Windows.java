/**
 * Author: Liguo Chen
 * Student ID: 851090
 * Description: This file contains the logic of the GUI
 */

package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Windows extends JFrame {

    private static final int WIDTH  = 350;
    private static final int HEIGHT = 400;
    private static final String DEFAULT_STATUS = "Status: N/A";

    private JTextField searchEntry;
    private JTextField addEntry;
    private JTextField meaning;
    private JTextField removeEntry;
    private JButton search;
    private JButton add;
    private JButton remove;
    private JTextArea explanation;
    private JTextArea addStatus;
    private JTextArea removeStatus;

    private boolean searchButtonPressed = false;
    private boolean addButtonPressed = false;
    private boolean removeButtonPressed = false;

    public Windows() {
        super("Dictionary");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(Windows.WIDTH, Windows.HEIGHT);
        // position the windows to the center of the screen
        setLocationRelativeTo(null);

        /* ----------------------------- search ----------------------------- */

        // container for search section
        JPanel searchp = new JPanel();
        JPanel searchm = new JPanel();

        JLabel w1 = new JLabel();
        w1.setText("Word to search:");
        w1.setName("inputFieldName");
        // change font size
        w1.setFont(new Font("", Font.PLAIN, 18));
        searchp.add(w1);

        searchEntry = new JTextField();
        // change input field box size
        searchEntry.setPreferredSize(new Dimension(100, 25));
        searchp.add(searchEntry);

        search = new JButton();
        search.setText("Search");
        // click listener
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                searchButtonPressed = true;
            }
        });
        searchp.add(search);

        // use text area to wrap long text
        explanation = new JTextArea("Meaning: N/A", 0, 18);
        // change font size
        explanation.setFont(new Font("", Font.PLAIN, 18));
        explanation.setWrapStyleWord(true);
        explanation.setLineWrap(true);
        // disable editing
        explanation.setEditable(false);
        searchm.add(explanation);

        add(searchp);
        add(searchm);

        /* ------------------------------------------------------------------ */

        JSeparator s1 = new JSeparator(SwingConstants.HORIZONTAL);
        s1.setPreferredSize(new Dimension(Windows.WIDTH, 1));
        add(s1);

        /* ------------------------------ add ------------------------------ */

        JPanel addpw = new JPanel();
        JPanel addpm = new JPanel();
        JPanel addps = new JPanel();

        JLabel w2 = new JLabel();
        w2.setText("Word to add:");
        // change font size
        w2.setFont(new Font("", Font.PLAIN, 18));
        addpw.add(w2);

        addEntry = new JTextField();
        // change input field box size
        addEntry.setPreferredSize(new Dimension(100, 25));
        addpw.add(addEntry);

        JLabel m = new JLabel();
        m.setText("Meaning:");
        // change font size
        m.setFont(new Font("", Font.PLAIN, 18));
        addpm.add(m);

        meaning = new JTextField();
        // change input field box size
        meaning.setPreferredSize(new Dimension(100, 25));
        addpm.add(meaning);

        add = new JButton();
        add.setText("Add");
        // click listener
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // no meaning is provided for the new word
                if (meaning.getText().length() == 0) {
                    addStatus.setText("Please provide a meaning and try again.");
                }
                else {
                    addButtonPressed = true;
                }
            }
        });
        addpm.add(add);

        // use text area to wrap long text
        addStatus = new JTextArea("Status: N/A", 0, 18);
        // change font size
        addStatus.setFont(new Font("", Font.PLAIN, 18));
        addStatus.setWrapStyleWord(true);
        addStatus.setLineWrap(true);
        // disable editing
        addStatus.setEditable(false);
        addps.add(addStatus);

        add(addpw);
        add(addpm);
        add(addps);

        /* ------------------------------------------------------------------ */

        JSeparator s2 = new JSeparator(SwingConstants.HORIZONTAL);
        s2.setPreferredSize(new Dimension(Windows.WIDTH, 1));
        add(s2);

        /* ----------------------------- remove ----------------------------- */

        JPanel removep = new JPanel();
        JPanel removes = new JPanel();

        JLabel w3 = new JLabel();
        w3.setText("Word to remove:");
        // change font size
        w3.setFont(new Font("", Font.PLAIN, 18));
        removep.add(w3);

        removeEntry = new JTextField();
        // change input field box size
        removeEntry.setPreferredSize(new Dimension(100, 25));
        removep.add(removeEntry);

        remove = new JButton();
        remove.setText("Remove");
        // click listener
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeButtonPressed = true;
            }
        });
        removep.add(remove);

        // use text area to wrap long text
        removeStatus = new JTextArea("Status: N/A", 0, 18);
        // change font size
        removeStatus.setFont(new Font("", Font.PLAIN, 18));
        removeStatus.setWrapStyleWord(true);
        removeStatus.setLineWrap(true);
        // disable editing
        removeStatus.setEditable(false);
        removes.add(removeStatus);

        add(removep);
        add(removes);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setVisible(true);
    }

    public boolean isSearchButtonPressed() {
        return this.searchButtonPressed;
    }

    public boolean isAddButtonPressed() {
        return this.addButtonPressed;
    }

    public boolean isRemoveButtonPressed() {
        return this.removeButtonPressed;
    }

    public void resetSearchButton() {
        this.searchButtonPressed = false;
    }

    public void resetAddButton() {
        this.addButtonPressed = false;
    }

    public void resetRemoveButton() {
        this.removeButtonPressed = false;
    }

    public String getSearchQuery() {
        return this.searchEntry.getText();
    }

    public String getAddQuery() {
        return this.addEntry.getText();
    }

    public String getMeaning() {
        return this.meaning.getText();
    }

    public String getRemoveQuery() {
        return this.removeEntry.getText();
    }

    public void setSearchResult(String result) {
        this.explanation.setText(result);
    }

    public void setAddStatus(String msg) {
        this.addStatus.setText(msg);
    }

    public void setRemoveStatus(String msg) {
        this.removeStatus.setText(msg);
    }

    public void resetSearchStatus() {
        this.explanation.setText("Meaning: N/A");
    }

    public void resetAddStatus() {
        this.addStatus.setText(Windows.DEFAULT_STATUS);
    }

    public void resetRemoveStatus() {
        this.removeStatus.setText(Windows.DEFAULT_STATUS);
    }

}
