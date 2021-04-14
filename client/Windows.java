/**
 * Author: Liguo Chen
 * Student ID: 851090
 * Description: This file contains the logic of the client GUI
 */

package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Windows extends JFrame {

    private static final int WIDTH  = 500;
    private static final int HEIGHT = 600;
    private static final int FONT_SIZE = 20;
    private static final int BUTTON_FONT_SIZE = 17;
    private static final int FIELD_WIDTH = 150;
    private static final int FIELD_HEIGHT = 30;
    private static final int STATUS_WIDTH = 25;
    private static final String DEFAULT_STATUS = "Status: N/A";

    private JTextField searchEntry;
    private JTextField addEntry;
    private JTextField meaning;
    private JTextField removeEntry;
    private JTextField updateEntry;
    private JTextField meaningUpdate;

    private JButton search;
    private JButton add;
    private JButton remove;
    private JButton update;

    private JTextArea explanation;
    private JTextArea addStatus;
    private JTextArea removeStatus;
    private JTextArea updateStatus;

    private boolean searchButtonPressed = false;
    private boolean addButtonPressed = false;
    private boolean removeButtonPressed = false;
    private boolean updateButtonPressed = false;

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
        w1.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        searchp.add(w1);

        searchEntry = new JTextField();
        // change input text font size
        searchEntry.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        // change input field box size
        searchEntry.setPreferredSize(new Dimension(Windows.FIELD_WIDTH, Windows.FIELD_HEIGHT));
        searchp.add(searchEntry);

        search = new JButton();
        search.setText("Search");
        // change button text font size
        search.setFont(new Font("", Font.PLAIN, Windows.BUTTON_FONT_SIZE));
        // click listener
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                searchButtonPressed = true;
            }
        });
        searchp.add(search);

        // use text area to wrap long text
        explanation = new JTextArea("Meaning: N/A", 0, Windows.STATUS_WIDTH);
        // change font size
        explanation.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
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
        w2.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        addpw.add(w2);

        addEntry = new JTextField();
        // change input text font size
        addEntry.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        // change input field box size
        addEntry.setPreferredSize(new Dimension(Windows.FIELD_WIDTH, Windows.FIELD_HEIGHT));
        addpw.add(addEntry);

        JLabel m = new JLabel();
        m.setText("Meaning:");
        // change font size
        m.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        addpm.add(m);

        meaning = new JTextField();
        // change input text font size
        meaning.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        // change input field box size
        meaning.setPreferredSize(new Dimension(Windows.FIELD_WIDTH, Windows.FIELD_HEIGHT));
        addpm.add(meaning);

        add = new JButton();
        add.setText("Add");
        // change button text font size
        add.setFont(new Font("", Font.PLAIN, Windows.BUTTON_FONT_SIZE));
        // click listener
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // no word is provided
                if (addEntry.getText().length() == 0) {
                    addStatus.setText("Please provide a word and try again.");
                    addStatus.setForeground(Color.RED);
                }
                // no meaning is provided for the new word
                else if (meaning.getText().length() == 0) {
                    addStatus.setText("Please provide a meaning and try again.");
                    addStatus.setForeground(Color.RED);
                }
                else {
                    addButtonPressed = true;
                }
            }
        });
        addpm.add(add);

        // use text area to wrap long text
        addStatus = new JTextArea("Status: N/A", 0, Windows.STATUS_WIDTH);
        // change font size
        addStatus.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
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
        w3.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        removep.add(w3);

        removeEntry = new JTextField();
        // change input text font size
        removeEntry.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        // change input field box size
        removeEntry.setPreferredSize(new Dimension(Windows.FIELD_WIDTH, Windows.FIELD_HEIGHT));
        removep.add(removeEntry);

        remove = new JButton();
        remove.setText("Remove");
        // change button text font size
        remove.setFont(new Font("", Font.PLAIN, Windows.BUTTON_FONT_SIZE));
        // click listener
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeButtonPressed = true;
            }
        });
        removep.add(remove);

        // use text area to wrap long text
        removeStatus = new JTextArea("Status: N/A", 0, Windows.STATUS_WIDTH);
        // change font size
        removeStatus.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        removeStatus.setWrapStyleWord(true);
        removeStatus.setLineWrap(true);
        // disable editing
        removeStatus.setEditable(false);
        removes.add(removeStatus);

        add(removep);
        add(removes);

        /* ------------------------------------------------------------------ */

        JSeparator s3 = new JSeparator(SwingConstants.HORIZONTAL);
        s3.setPreferredSize(new Dimension(Windows.WIDTH, 1));
        add(s3);

        /* ----------------------------- update ----------------------------- */

        JPanel updatepw = new JPanel();
        JPanel updatepm = new JPanel();
        JPanel updateps = new JPanel();

        JLabel w4 = new JLabel();
        w4.setText("Word to update:");
        // change font size
        w4.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        updatepw.add(w4);

        updateEntry = new JTextField();
        // change input text font size
        updateEntry.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        // change input field box size
        updateEntry.setPreferredSize(new Dimension(Windows.FIELD_WIDTH, Windows.FIELD_HEIGHT));
        updatepw.add(updateEntry);

        JLabel m2 = new JLabel();
        m2.setText("New Meaning:");
        // change font size
        m2.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        updatepm.add(m2);

        meaningUpdate = new JTextField();
        // change input text font size
        meaningUpdate.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        // change input field box size
        meaningUpdate.setPreferredSize(new Dimension(Windows.FIELD_WIDTH, Windows.FIELD_HEIGHT));
        updatepm.add(meaningUpdate);

        update = new JButton();
        update.setText("Update");
        // change button text font size
        update.setFont(new Font("", Font.PLAIN, Windows.BUTTON_FONT_SIZE));
        // click listener
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // no word is provided
                if (updateEntry.getText().length() == 0) {
                    updateStatus.setText("Please provide a word and try again.");
                    updateStatus.setForeground(Color.RED);
                }
                // no meaning is provided for the update
                else if (meaningUpdate.getText().length() == 0) {
                    updateStatus.setText("Please provide a meaning and try again.");
                    updateStatus.setForeground(Color.RED);
                }
                else {
                    updateButtonPressed = true;
                }
            }
        });
        updatepm.add(update);

        // use text area to wrap long text
        updateStatus = new JTextArea("Status: N/A", 0, Windows.STATUS_WIDTH);
        // change font size
        updateStatus.setFont(new Font("", Font.PLAIN, Windows.FONT_SIZE));
        updateStatus.setWrapStyleWord(true);
        updateStatus.setLineWrap(true);
        // disable editing
        updateStatus.setEditable(false);
        updateps.add(updateStatus);

        add(updatepw);
        add(updatepm);
        add(updateps);

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

    public boolean isUpdateButtonPressed() {
        return this.updateButtonPressed;
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

    public void resetUpdateButton() {
        this.updateButtonPressed = false;
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

    public String getUpdateQuery() {
        return this.updateEntry.getText();
    }

    public String getMeaningUpdate() {
        return this.meaningUpdate.getText();
    }

    public void setSearchResult(String result) {
        int len = result.length();
        if (len == 0) {
            // no match found in the server
            this.explanation.setText("No match result");
            this.explanation.setForeground(Color.RED);
        }
        else {
            this.explanation.setText(result);
            this.explanation.setForeground(Color.GREEN);
        }
    }

    public void setAddStatus(String msg, boolean isSuccessful) {
        this.addStatus.setText(msg);
        if (isSuccessful) {
            this.addStatus.setForeground(Color.GREEN);
        }
        else {
            this.addStatus.setForeground(Color.RED);
        }
    }

    public void setRemoveStatus(String msg, boolean isSuccessful) {
        this.removeStatus.setText(msg);
        if (isSuccessful) {
            this.removeStatus.setForeground(Color.GREEN);
        }
        else {
            this.removeStatus.setForeground(Color.RED);
        }
    }

    public void setUpdateStatus(String msg, boolean isSuccessful) {
        this.updateStatus.setText(msg);
        if (isSuccessful) {
            this.updateStatus.setForeground(Color.GREEN);
        }
        else {
            this.updateStatus.setForeground(Color.RED);
        }
    }

    public void resetSearchStatus() {
        this.explanation.setText("Meaning: N/A");
        this.explanation.setForeground(Color.BLACK);
    }

    public void resetAddStatus() {
        this.addStatus.setText(Windows.DEFAULT_STATUS);
        this.addStatus.setForeground(Color.BLACK);
    }

    public void resetRemoveStatus() {
        this.removeStatus.setText(Windows.DEFAULT_STATUS);
        this.removeStatus.setForeground(Color.BLACK);
    }

    public void resetUpdateStatus() {
        this.updateStatus.setText(Windows.DEFAULT_STATUS);
        this.updateStatus.setForeground(Color.BLACK);
    }
}
