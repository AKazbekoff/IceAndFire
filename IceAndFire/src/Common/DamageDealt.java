package Common;

import Items.Item;

public class DamageDealt {

    /*
    * Предмет, которым нанесен урон.
    * */
    private Item item;

    /*
    * Размер урона.
    * */
    private int damage;

    public DamageDealt(Item item, int damage) {
        this.item = item;
        this.damage = damage;
    }

    public Item getItem() {
        return item;
    }

    public int getDamage() {
        return damage;
    }
}
