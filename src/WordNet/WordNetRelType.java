package WordNet;

public class WordNetRelType {

    private int reltypeno;
    private char typeno;
    private String description;

    public WordNetRelType(int reltypeno, char typeno, String description) {
        this.reltypeno = reltypeno;
        this.typeno = typeno;
        this.description = description;
    }

    public int getRelTypeno() {
        return reltypeno;
    }

    public void setReltypeno(int reltypeno) {
        this.reltypeno = reltypeno;
    }

    public char getTypeno() {
        return typeno;
    }

    public void setTypeno(char typeno) {
        this.typeno = typeno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RelType{" + "reltypeno=" + reltypeno + ", typeno=" + typeno + ", description=" + description + '}';
    }

}
