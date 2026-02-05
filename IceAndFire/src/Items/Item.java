package Items;

public abstract class Item {

    /*
    * Название предмета
    * */
    private final String title;

    /*
    * Стоимость предмета
    * */
    private final int price;

    public Item(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }
}
