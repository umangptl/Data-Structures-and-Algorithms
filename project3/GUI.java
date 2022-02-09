import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;

public class GUI {

    static JTextField t;
    static JTextField t2;
    static JFrame f;
    static JButton b;
    static JLabel l;
    static JLabel l2;
    static JLabel mes;
    static JLabel mes2;
    static JLabel mes3;
    static JTextArea list;
    static JTextArea list2;
    static JTextArea list3;
    static JScrollPane lista;
    static JScrollPane listb;
    static JScrollPane listc;

    private static final String PATH = "/C:/Users/HP/IdeaProjects/Assignment3-365/src";
    private static final String URL_FILE = "/Users/HP/IdeaProjects/Assignment3-365/src/url.txt";
    int urlCount = 0;
    public int count = 0;

    List<Vertex> vertexList = new ArrayList<Vertex>();
    List<Edge> edgeList = new ArrayList<Edge>();
    List<String> urlsList = new CopyOnWriteArrayList<String>();
    List<String> medoids = new ArrayList<String>();
    final HashTable frequencyHashie = new HashTable();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.f.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GUI() throws IOException{
        runcheck ();
    }

    private void runcheck ()throws IOException {

        // create a new frame to store text field and button
        f = new JFrame("CSC-365 Assignment 3 ");

        // f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create a label to display text
        l = new JLabel("Enter URL 1 ");
        l2 = new JLabel("Enter URL 2 ");

        // wiki notification
        mes = new JLabel("Please Enter A WIKIPEDIA LINK");
        mes2 = new JLabel("The list of Disjoint Set");
        mes3 = new JLabel("Representing the shortest path");

        // create a new button
        b = new JButton("Search");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                String urlOne = t.getText();
                String urlTwo = t2.getText();
                if (!urlsList.contains(urlOne) || !urlsList.contains(urlTwo)) {
                    JOptionPane.showMessageDialog(f, "Please enter a valid URL.", "Oops",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    Dijkstras dijkstras = new Dijkstras();
                    Vertex one = new Vertex("");
                    Vertex two = new Vertex("");
                    for (Vertex v : vertexList) {
                        if(v.getName().equals(urlOne)) {
                            one = v;
                            System.out.println("Found one");
                        }
                        if(v.getName().equals(urlTwo)) {
                            two = v;
                            System.out.println("Found two");
                        }
                    }

                    dijkstras.calculateShortestPath(one);
                    List<Vertex> path = new ArrayList<Vertex>();
                    path = dijkstras.getShortPath(two);
                    String pathText = "";
                    for (Vertex v : path) {
                        pathText = pathText + v.getName() + "\n";
                    }

                    for (String medoid : medoids) {
                        if (pathText.contains(medoid)) {
                            pathText = pathText + "You went through the medoid " + medoid + "\n";
                        }
                    }
                    list3.setText(pathText);
                }
            }
        });

        // create a object of JTextField with 16 columns and a given initial text
        t = new JTextField("", 35);
        t2 = new JTextField("", 35);

        // create text area for the similar links
        list = new JTextArea();
        list.setEditable(false);
        list.setColumns(49);
        list.setRows(10);

        list2 = new JTextArea();
        list2.setEditable(false);
        list2.setColumns(49);
        list2.setRows(10);

        list3 = new JTextArea();
        list3.setEditable(false);
        list3.setColumns(49);
        list3.setRows(7);

        lista = new JScrollPane(list);
        listb = new JScrollPane(list2);
        listc = new JScrollPane(list3);

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        // add buttons and textfield to panel
        p.add(l);
        p.add(t);
        p.add(b);
        p.add(l2);
        p.add(t2);
        p.add(mes);
        p.add(lista);
        p.add(mes2);
        p.add(listb);
        p.add(mes3);
        p.add(list3);

        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setSize(590, 670);
        f.setVisible(true);

        final File urlFile = new File(URL_FILE);
        Scanner sc = new Scanner(urlFile);
        String[] urls = sc.next().split(",");
        sc.close();

        System.out.println("Setting url list ...");
        setUrlList(urls);

        System.out.println("Setting hash tables ...");

        for (String url : urlsList) {
            setHasher(url);
            count++;
        }

        System.out.println("Setting urls to list ...");
        list.setText(getUrlText());

        System.out.println("Creating vertex and edges ... ");
        setVertexAndEdges(urls);

        System.out.println("Creating the graph ...");
        Graph graph = new Graph(vertexList, edgeList);

        List<String> disjointList = graph.disjoint();
        System.out.println("Generated disjoint list ...");
        System.out.println(getDisjoint(disjointList));
        list2.setText("The disjoint list: \n" +
                "The number of disjoints are: " + graph.getDisjointCount()
                + "\n" + getDisjoint(disjointList));

        addSerializedFile();
        System.out.println("Created serialized file ...");
    }

    public String getDisjoint(List<String> list) {
        String text = "";
        for(String item : list) {
            text = text + item + "\n";
        }
        return text;
    }

    public void setUrlList(String[] urls) {

        WebScraper scraper = new WebScraper();
        for (String url : urls) {
            medoids.add(url);
            urlsList.add(url);
            urlCount++;
            String[] links = scraper.getLinks(url);
            for (String link : links) {
                if (link.contains("en.wikipedia.org") && !Character.isDigit(link.charAt(link.length() - 1))
                        && !urlsList.contains(link) && !link.contains("Main_Page")) {
                    urlsList.add(link);
                    urlCount++;
                    if(urlCount == 500)
                        break;
                }
            }
        }
    }

    public void setVertexAndEdges(String[] urls) {

        WebScraper scraper = new WebScraper();
        for (String url : urls) {
            Vertex v = new Vertex(url);
            double fc = getUrlFC(url);
            String[] links = scraper.getLinks(url);
            for (String link : links) {
                if (link.contains("en.wikipedia.org") && !Character.isDigit(link.charAt(link.length() - 1)) && !link.contains("Main_Page")) {
                    double fcLink = getUrlFC(link);
                    Vertex vLink = new Vertex(link);
                    Edge linkToNull = new Edge(0, vLink, null);
                    Edge linkToSource = new Edge(fc, vLink, v);
                    Edge sourceToLink = new Edge(fcLink, v, vLink);
                    vLink.addEdge(linkToNull);
                    vLink.addEdge(linkToSource);
                    v.addEdge(sourceToLink);
                    edgeList.add(linkToNull);
                    edgeList.add(linkToSource);
                    edgeList.add(sourceToLink);
                    vertexList.add(vLink);
                }
            }
            vertexList.add(v);
        }
    }

    public String getUrlText() {
        String text = "";
        for (String url : urlsList) {
            if (url != null) {
                text = text + url + "\n";
            }
        }
        return text;
    }

    public double getUrlFC(String url) {
        Document doc;
        HashTable hashie = new HashTable();
        double fc = 0;
        String text = "";
        count ++;
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception EH) {
            return -1;
        }
        text = doc.body().text();
        String[] wordCount = text.split(" ");
        for (int i = 0; i < wordCount.length; i++) {
            hashie.add(wordCount[i].toLowerCase());
        }
        for (int i = 0; i < hashie.table.length; i++) {
            for (HashTable.Node p = hashie.table[i]; p != null; p = p.next) {

                fc = tfidfCount(urlCount, frequencyHashie.get(p.key.toLowerCase()), hashie.table.length,
                        hashie.get(p.key.toLowerCase())) + fc;
            }
        }
        return fc;
    }

    public void setHasher(String link) throws IOException {
        Document urlDoc = new Document("");
        HashTable urlHashie = new HashTable();
        try {
            urlDoc = Jsoup.connect(link).get();
        } catch (Exception e) {
            urlsList.remove(link);
            urlCount--;
            return;
        }
        String[] urlWordCount = urlDoc.body().text().split(" ");

        // Adds all elements to a hash table
        for (int j = 0; j < urlWordCount.length; j++) {
            urlHashie.add(urlWordCount[j].toLowerCase());
        }
        // Adds all keys to the frequency table
        for (int j = 0; j < urlHashie.table.length; j++) {
            for (HashTable.Node p = urlHashie.table[j]; p != null; p = p.next) {
                frequencyHashie.add(p.key.toLowerCase());
            }
        }
    }

    public double tfidfCount(int wordFrequency, int totalDocuments, int totalWords, int documentFrequency) {
        double tf = (double) documentFrequency / (double) totalWords;
        double idf = Math.log((double) totalDocuments / (double) wordFrequency);
        double tfidf = tf * idf;
        return Math.abs(tfidf);
    }

    public void addSerializedFile() throws IOException {
        FileOutputStream fout = new FileOutputStream(PATH + "Urls.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(edgeList);
        fout.close();
        oos.close();
    }
}