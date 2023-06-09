public class Word {
    private int rawFreqt;
    private double tfIdf;

    public Word() {
        rawFreqt = 1;
    }
    public void incFreq() {
        rawFreqt++;
    }
    public void set_TfIdf(double d) {
        tfIdf = d;
    }
    public int getFreq() {
        return rawFreqt;
    }
    public double get_TfIdf() {
        return tfIdf;
    }
}
