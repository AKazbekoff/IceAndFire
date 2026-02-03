package Items;

public abstract class Weapon extends Item {

    private int power;

    public Weapon(String title, int power) {
        super(title);
        this.power = power;
    }
}
