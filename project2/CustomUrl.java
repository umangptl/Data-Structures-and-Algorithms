
public class CustomUrl {
    private FreqTable freqTable;
    private String url;

    public CustomUrl(String url, Scrap scrap) {
        this.url = url;
        this.freqTable = new FreqTable(scrap);
    }

    public FreqTable getFreqTable() {
        return freqTable;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomUrl other = (CustomUrl) obj;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;

        return true;
    }
}
