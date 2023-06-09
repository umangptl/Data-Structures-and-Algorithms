import Data_Structures.btree.CustomBTree;
import Data_Structures.btree.Value;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public enum Category {
    Intel("https://en.wikipedia.org/wiki/Intel"),
    Amazon("https://en.wikipedia.org/wiki/Amazon_(company)"),
    Google("https://en.wikipedia.org/wiki/Google"),
    Facebook("https://en.wikipedia.org/wiki/Facebook"),
    Microsoft("https://en.wikipedia.org/wiki/Microsoft"),
    IBM("https://en.wikipedia.org/wiki/IBM"),
    Oracle("https://en.wikipedia.org/wiki/Oracle_Corporation"),
    Tencent("https://en.wikipedia.org/wiki/Tencent"),
    Nvidia("https://en.wikipedia.org/wiki/Nvidia"),
    Apple("https://en.wikipedia.org/wiki/Apple_Inc");

    // top five category to check then go in deep
    private static final int MAX_SUB_LINKS = 5 ;
    private CustomBTree customBTree;
    private Scrap scrap;
    private String parentUrl;


    Category(String parentUrl)
    {
        this.parentUrl = parentUrl;
        this.scrap = new Scrap();
        customBTree = new CustomBTree(parentUrl.replaceAll("/", ""));
        if(Update(customBTree.getLastModifiedRaf()))
        {
            customBTree.removeRaf();
            loadUrlsToCorpus();
            addWordsToCorpus();
            cal_TfIdf();
            loadBTree();
        }
    }

    private boolean Update(Date d) {
        if(d == null)
            return true;

        LocalDateTime ldt = LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();

        return ldt.plusWeeks(1).isBefore(now);
    }

    private void loadUrlsToCorpus() {
        //add words from parent link first
        scrap.add(new CustomUrl(parentUrl, scrap));
        List<String> links = Webpage.nextURL(parentUrl);
        Collections.shuffle(links);

        for(String link : links) {
            try
            {
                //check word length first
                if(link.getBytes("UTF-32BE").length >= 256)
                {
                    System.out.println(link + " Unacceptable link (too long)");
                    continue;
                }

                if(scrap.size() >= MAX_SUB_LINKS)
                    break;

                CustomUrl newUrl = new CustomUrl(link, scrap);
                if(scrap.contains(newUrl))
                {
                    System.out.println("Duplicate url " + link);
                    continue;
                }
                System.out.println(link);
                scrap.add(newUrl);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addWordsToCorpus() {
        for(CustomUrl url : scrap) {
            try
            {
                System.out.println("Adding words: " + url.getUrl());

                //parse body of web page with JSoup
                String body = Webpage.getWebPageBody(url.getUrl());

                //split by spaces
                String[] bodyParts = body != null ? body.split(" ") : null;

                if(bodyParts == null)
                    continue;

                for(String s : bodyParts)
                {
                    if(s.length() == 0)
                        continue;
                    //check word length first
                    if(s.getBytes("UTF-32BE").length >= 256)
                    {
                        System.out.println(s + " Unacceptable link (too long)");
                        continue;
                    }
                    url.getFreqTable().addWord(s);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void cal_TfIdf() {
        for(CustomUrl url : scrap)
            url.getFreqTable().calculate();
    }

    private void loadBTree() {
        for(CustomUrl url : scrap)
        {
            System.out.println("Insert words from " + url.getUrl() + " to btree...");
            for(String word : url.getFreqTable().keySet())
            {
                Value toInsert = new Value(url.getUrl(), url.getFreqTable().get(word).get_TfIdf());
                customBTree.put(word, toInsert);
            }
        }
    }
    public CustomBTree getBTree()
    {
        return customBTree;
    }
}
