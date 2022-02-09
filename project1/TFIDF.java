import java.util.ArrayList;

public class TFIDF {

    // doc list of Strings
    //term string represents a term
    // return term frequency of term in doc
    public double tf(Hashtables doc, String term) {
        double result = 0;
        if (doc.contains(term))
            result = doc.get(term);

        return result / doc.count;
    }

    //docs list of list strings
    // term string represents a term
    // return the inverse term frequency of term in documents
    public double idf(ArrayList<Hashtables> docs, String term) {
        double n = 0;
        for (Hashtables doc : docs) {
            for (int i = 0; i < doc.count; i++) {
                if (doc.contains(term)) {
                    n++;
                }
            }
        }
        return Math.log(docs.size() / n);
    }

    // doc a text document
    // docs all document
    // term term
    // return the TF-IDF of term
    public double tfIdf(Hashtables doc, ArrayList<Hashtables> docs, String term) {
        return Math.abs(tf(doc, term) * idf(docs, term));
    }
}
