import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Webpage {
    private static ArrayList<Document> urlList = new ArrayList<>();

    public ArrayList<Document> scrap() throws IOException {
        Document tURL;
        String fName;
        BufferedWriter bw;

        // get the file of the initial 10 urls
        File URLs = new File("C:\\Users\\HP\\IdeaProjects\\Asingment1-365\\src\\URLS.txt");
        Scanner scan = new Scanner(URLs);
        String[] urlslist = scan.next().split(",");

        // use jsoup to get the webpage content
        for (String url : urlslist) {
            tURL = Jsoup.connect(url).get();
            fName = tURL.title().replaceAll("[^a-zA-z0-9]", " ");
            FileWriter fw = new FileWriter(fName + ".txt");
            bw = new BufferedWriter(fw);
            bw.write(tURL.toString());
            urlList.add(tURL);
        }
        tURL = Jsoup.connect("https://en.wikipedia.org/wiki/Main_Page").get();

        // 10 coz of the number of URLs
        for (int i = 0; i < 10; i++) {

            fName = tURL.title().replaceAll("[^a-zA-z0-9]", " ");
            FileWriter fw = new FileWriter(fName + ".txt");
            bw = new BufferedWriter(fw);
            bw.write(tURL.toString());
            urlList.add(tURL);

            tURL = nextURL(tURL);
        }
        scan.close();
        return urlList;

    }

    // do it for the next url
    private Document nextURL (Document tURL) throws IOException {
        Document newUrl;
        ArrayList<String> list = new ArrayList<>();
        Random r = new Random();
        Elements links = tURL.select("a[href]");
        for (Element link : links) {
            String url = link.attr("abs:href");

            if (!url.startsWith("https://en.wikipedia.org") || !url.contains("/wiki/") || url.substring(6).contains(":")
                    || url.contains("#") || url.contains("%"))
                continue;

            list.add(url);
        }
        newUrl = Jsoup.connect(list.get(r.nextInt(list.size()))).get();
        return newUrl;
    }
}