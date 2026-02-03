package Entities;

import Items.Item;

import java.util.ArrayList;
import java.util.List;

public class GameCharacter extends Entity {

    /*
    * Текущий накопленный опыт. Обнуляется при достижении нового уровня
    * */
    private int currentExperience = 0;

    /*
    * Потолок накопления опыта. После достижения этой границы увеличивается уровень
    * */
    private int totalExperience = 100;

    /*
    * Ведущая рука. Здесь хранится предмет, который персонаж держит прямо сейчас. Это может быть меч, либо иной предмет
    * */
    private Item leadingHand = null;

    /*
    * Рюкзак персонажа. В нем он может складывать вещи
    * */
    private List<Item> backpack = new ArrayList<>();

    /*
    * Лимит предметов в рюкзаке. По умолчанию 8
    * */
    private int backpackLimit = 8;

    /*
    * Количество предметов в рюкзаке на текущий момент
    * */
    private int currentBackpackSize = 0;

    /*
    * Монеты персонажа. На них он может покупать у торговца амуницию
    * */
    private int coins = 120;

    public GameCharacter(String name) {
        super(name);
    }

    @Override
    public int dealDamage(Entity anotherEntity) {
        return 0;
    }

    @Override
    public int takeDamage() {
        return 0;
    }
}
