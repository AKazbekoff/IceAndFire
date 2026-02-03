package Items;

public abstract class Item {

    private final String title;

    public Item(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
