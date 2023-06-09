import java.awt.event.*;
import javax.swing.*;

class GUI extends JFrame implements ActionListener {

    static JTextField t;
    static JFrame f;
    static JButton b;
    static JLabel l;
    static JLabel category;
    static JLabel err;
    static JLabel categoryName;
    static JTextArea list;
    static JTextArea textbox;

    // default constructor
    GUI() {}

    public static void main(String[] args) {

        // create a new frame to store text field and button
        f = new JFrame("CSC-365 Assignment 2 ");

        // f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create a label to display text
        l = new JLabel("Enter URL ");

        // create a label to display category
        category = new JLabel("Category :");
        categoryName = new JLabel(" ");

        // wiki notification
        err = new JLabel("Please Enter A WIKIPEDIA LINK");

        // create a new button
        b = new JButton("submit");

        // create a object of the GUI class
        GUI te = new GUI();

        // addActionListener to button
        b.addActionListener(te);

        // create a object of JTextField with 16 columns and a given initial text
        t = new JTextField("", 35);

        // create text area for the similar links
        list = new JTextArea();
        list.setEditable(false);
        list.setColumns(49);
        list.setRows(6);

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        // add buttons and textfield to panel
        p.add(l);
        p.add(t);
        p.add(b);
        p.add(category);
        p.add(categoryName);
        p.add(list);
        p.add(err);

        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setSize(570, 230);

        f.show();
        textbox = new JTextArea();
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e) {
        Main_util mainUtil = new Main_util(t.getText());
        mainUtil.execute();
        categoryName.setText(mainUtil.getCategory().name());
        String clusters = "";
        for (String cluster : mainUtil.getclusters())
            clusters += (cluster + "\n");
        list.setText(clusters);
        b.setEnabled(false);
    }
}
