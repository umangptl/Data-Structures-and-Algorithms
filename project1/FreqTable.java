import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FreqTable {

    public Document input;

    public String scrap(Document inputUrl) throws IOException {
        input = inputUrl;
        ArrayList<Hashtables> urls = new ArrayList<>();
        ArrayList<Document> urlList = new Webpage().scrap();
        Hashtables urlON = new Hashtables();
        double[] tfidfRate = new double[20];
        String[] intiURL = new String[20];
        String[] UrlNames = new String[20];

        String thisDirectory = System.getProperty("user.dir");
        Document file;
        String[] userURL;
        String matchLink;

        for (int i = 0; i < urlList.size(); i++) {
            UrlNames[i] = urlList.get(i).baseUri();
            intiURL[i] = thisDirectory + "/" + urlList.get(i).title().replaceAll("[^a-zA-Z0-9]", " ") + ".txt";
        }
        for (int i = 0; i < intiURL.length; i++) {
            try {
                Hashtables hashURL = new Hashtables();
                if (intiURL[i] != null) {
                    file = Jsoup.parse(new File(intiURL[i]), "ISO-8859-1");
                    file = new Cleaner(Whitelist.basic()).clean(file);
                    String[] urlwords = file.body().text().split(" ");

                    for (String word : urlwords) {
                        hashURL.add(word.toLowerCase());
                    }
                    urls.add(hashURL);

                }
            }
            // this current working dir
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        userURL = input.body().text().split(" ");

        for (int i = 0; i < userURL.length; i++) {
            urlON.add(userURL[i].toLowerCase());
        }

        for (int i = 0; i< urls.size();i++){
            Hashtables.Node temp = urlON.first;
            while(temp!= null){
                tfidfRate[i] += new TFIDF().tfIdf(urls.get(i),urls,temp.key);
                System.out.println(i + ": The value is: " + tfidfRate[i]);
                temp = temp.next;
            }
        }
        matchLink = matchURL(UrlNames,tfidfRate);
        System.out.println("This is the Matched link " + matchLink);

        return matchLink;
    }

    public String matchURL(String[] links,double[] rate){
        double max = rate[0];
        int p = 0;

        for (int i = 0; i< rate.length; i++){
            if(rate[i]> max) max = rate[i];
        }
        while(rate[p]!= max){
            p++;
        }
        return links[p];
    }
}
