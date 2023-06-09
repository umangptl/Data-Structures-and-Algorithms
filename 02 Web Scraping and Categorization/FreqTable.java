import Data_Structures.CustomHashTables;

public class FreqTable extends CustomHashTables<String, Word> {
    private int maxRawFrequency;
    private Scrap scrap;

    public FreqTable(Scrap scrap) {
        this.scrap = scrap;
    }

    public void addWord(String word) {
        Word wordEntry = get(word);

        if(wordEntry == null)
            put(word, new Word());
        else
            wordEntry.incFreq();
    }

    public void calculate() {
        for(String key : keySet()) {
            Word word = get(key);
            if(word != null)
                word.set_TfIdf(cal_Tf(key));
        }
    }

    // return term frequency of term in url
    private double cal_Tf(String word) {
        return TFIDF(word) * cal_IDF(word);
    }

    // return the inverse term frequency of term in url
    private double cal_IDF(String word) {
        return Math.log((double) scrap.size() / (1 + scrap.getTotalDocsContainingTerm(word)));
    }

    // return the TF-IDF of term
    private double TFIDF (String word) {
        Word wordEntry = get(word);
        int rawFreq = wordEntry == null ? 0 : wordEntry.getFreq();

        if(rawFreq > maxRawFrequency)
            maxRawFrequency = rawFreq;

        return 0.5 + (0.5 * (rawFreq / maxRawFrequency));
    }
}
