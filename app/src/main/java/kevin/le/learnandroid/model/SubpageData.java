package kevin.le.learnandroid.model;

public class SubpageData {
    private final String name;
    private final String type;
    private final String title;

    public SubpageData(String name, String type, String title) {
        this.name = name;
        this.type = type;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
}
