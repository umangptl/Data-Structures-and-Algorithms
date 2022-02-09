import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Data_Structures.btree.CustomBTree;
import Data_Structures.btree.Value;
import Data_Structures.CustomHashTables;

public class Main_util
{
    //to get top 5 links from the category
    private static final int TOP_CLUSTERS = 5;
    private String url;
    private Set<String> words;
    private String[] clusters;
    private Category category;
    private CustomHashTables<Category, List<Suggestion>> linkOccurences;
    // top ten most used words to be avoid
    private List<String> aviod = Arrays.asList("the", "on", "to", "of", "and", "a", "in", "for", "was", "is");

    public Main_util(String url)
    {
        this.url = url;
        this.words = new HashSet<>();
        this.linkOccurences = new CustomHashTables<>();
        this.clusters = new String[TOP_CLUSTERS];
    }
    private void addWords()
    {
        try
        {
            System.out.println("Adding words from url: " + url);

            //parse body of web page with JSoup
            String body = Webpage.getWebPageBody(url);

            //split by spaces
            String[] bodyParts = body != null ? body.split(" ") : null;
            if(bodyParts == null)
            {
                System.out.println("ERROR: cant Find  URL");
                return;
            }
            for(String s : bodyParts)
            {
                if(s.length() == 0)
                    continue;
                if(aviod.contains(s))
                    continue;

                words.add(s);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void execute() throws ExceptionInInitializerError
    {
        try
        {
            //parse words from primary url
            addWords();
            System.out.println("Adding " + words.size() + " words");

            Category mostSimilar = null;
            double highestTFIDF = -1;
            for(Category c : Category.values())
            {
                double toCompare = sumTfIdf(c, c.getBTree());
                if(toCompare > highestTFIDF)
                {
                    mostSimilar = c;
                    highestTFIDF = toCompare;
                }
                System.out.println("Category: " + c);
                System.out.println("TFIDF Ratio for " + c + ": " + toCompare);
            }
            category = mostSimilar;
            List<Suggestion> suggestionsList = linkOccurences.get(category);
            Collections.sort(suggestionsList);
            for(int i = 0; i < clusters.length; i++)
                clusters[i] = suggestionsList.get(i).link + " - " + suggestionsList.get(i).amount;
            System.out.println("Matched category: " + mostSimilar);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private double sumTfIdf(Category c, CustomBTree customBTree)
    {
        double sum = 0;
        for(String word : words)
        {
            Value[] vals = customBTree.get(word);
            if(vals == null)
                continue;
            for(Value v : vals)
            {
                if(v == null)
                    continue;
                sum += v.getTfIdf();
                List<Suggestion> currentSuggestions = linkOccurences.get(c);
                if(currentSuggestions == null)
                    currentSuggestions = new ArrayList<>();

                Suggestion s = null;
                for(Suggestion s1 : currentSuggestions)
                {
                    if(s1.link.equals(v.getUrl()))
                    {
                        s = s1;
                        break;
                    }
                }
                if(s == null)
                    currentSuggestions.add(new Suggestion(v.getUrl(), 1));
                else
                    s.amount++;
                linkOccurences.put(c, currentSuggestions);
            }
        }
        return sum;
    }

    public Category getCategory()
    {
        return category;
    }

    public String[] getclusters()
    {
        return clusters;
    }

    private class Suggestion implements Comparable<Suggestion>
    {
        String link;
        int amount;
        public Suggestion(String link, int amount)
        {
            this.link = link;
            this.amount = amount;
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((link == null) ? 0 : link.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Suggestion other = (Suggestion) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (link == null)
            {
                if (other.link != null)
                    return false;
            } else if (!link.equals(other.link))
                return false;
            return true;
        }

        private Main_util getOuterType()
        {
            return Main_util.this;
        }

        @Override
        public int compareTo(Suggestion o)
        {
            return o.amount - amount;
        }
    }
}
