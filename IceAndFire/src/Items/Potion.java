package Items;

public abstract class Potion extends Item {

    private final String effect;

    public Potion(String title, String effect) {
        super(title);
        this.effect = effect;
    }
}
