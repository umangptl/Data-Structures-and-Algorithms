import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

class GUI extends JFrame implements ActionListener {

    static JTextField t;
    static JFrame f;
    static JButton b;
    static JLabel l;
    String url;

    public static JTextArea textbox;

    // default constructor
    GUI() {
    }

    public static void main(String[] args)
    {
        // create a new frame to store text field and button
        f = new JFrame("URL ");

        // f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create a label to display text
        l = new JLabel("Enter URL ");

        // create a new button
        b = new JButton("submit");

        // create a object of the GUI class
        GUI te = new GUI();

        // addActionListener to button
        b.addActionListener(te);

        // create a object of JTextField with 16 columns and a given initial text
        t = new JTextField("", 40);

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        // add buttons and textfield to panel
        p.add(t);
        p.add(b);
        p.add(l);

        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setSize(550, 150);

        f.show();
        textbox = new JTextArea();

    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e) {
        Document doc;
        try {
            doc = Jsoup.connect(t.getText()).get();
        } catch (Exception EH) {
            JOptionPane.showMessageDialog(f, "Please enter a valid wikipedia URL.", "ERROR",
                    JOptionPane.WARNING_MESSAGE);
            t.requestFocusInWindow();
            t.selectAll();
            return;
        }
        try {
            url=  new FreqTable().scrap(doc);
            //sout the matched link
            l.setText("The closest match is: " + url);
            // submit button enabled to submit again coz only one time use program
            b.setEnabled(false);
        } catch (IOException ea) {
            ea.printStackTrace();
        }
    }
}
