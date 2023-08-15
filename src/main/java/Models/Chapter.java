package Models;

public class Chapter {
    private String name;
    private long marinesCount;

    public Chapter(String name, long marinesCount){
        this.name = name;
        this.marinesCount = marinesCount;
    }

    public Chapter(){}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMarinesCount(long marinesCount) {
        this.marinesCount = marinesCount;
    }

    public long getMarinesCount() {
        return marinesCount;
    }

    @Override
    public String toString() {
        return "Chapter: " +
                "name = " + name +
                ", marinesCount = " + marinesCount;
    }
}
