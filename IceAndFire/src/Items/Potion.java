package Items;

import Entities.GameCharacter;

public abstract class Potion extends Item {

    /*
    * Эффект зелья, например +10 к чему-либо или -10 к чему-либо
    * */
    private int effect;

    public Potion(String title, int effect, int price) {
        super(title, price);
    }

    public int getEffect() {
        return effect;
    }

}
