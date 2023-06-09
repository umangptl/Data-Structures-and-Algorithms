import java.util.ArrayList;

public class Scrap extends ArrayList<CustomUrl> {

    public int getTotalDocsContainingTerm(String term) {
        int total = 0;
        for(CustomUrl url : this)
            if(url.getFreqTable().containsKey(term))
                total++;
        return total;
    }
}

