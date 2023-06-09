import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class Webpage {
    public static String getWebPageBody(String url) {
        try
        {
            Document doc = Jsoup.connect(url).get();
            Element body = doc.body();
            return body == null ? null : body.text().replaceAll("[^a-zA-Z ]", "").toLowerCase();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    // do it for the next url
    public static List<String> nextURL(String base) {
        List<String> linkList = new ArrayList<>();
        try
        {
            Document doc = Jsoup.connect(base).get();
            Elements links = doc.select("a[href]");

            for(Element link : links)
                linkList.add(link.attr("abs:href"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return linkList;
    }
}
