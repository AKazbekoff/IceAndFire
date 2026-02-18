package Entities;

import Common.DamageDealt;
import Common.Strategy;
import Items.Item;
import Items.Potion;
import Items.Potions.HealingPotion;
import Items.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * Монеты персонажа. На них он может покупать у торговца амуницию
     * */
    private int coins = 120;

    public GameCharacter(String name) {
        super(name);
    }

    /**
     * Классический расчет урона: (сила + урон) и зависимость от уровня здоровья.
     * Ловкость определяет верхнюю границу, при котором будет удар считаться успешным.
     * Так, если ловкость = 50, а случайное сгенерированное число 67, то удар считается не удачным, что означает,
     * что герой промахнулся и было нанесено 0 урона. В обратном случае герой наносит полный рассчитанный размер урона.
     */
    @Override
    public int dealDamage(Entity anotherEntity) {
        // Получаем сразу же размер урона оружия, если оно есть (выбрано)
        int weaponPower = 0;
        if (leadingHand instanceof Weapon) {
            weaponPower = ((Weapon) leadingHand).getPower();
        }

        DamageDealt damageDealt;
        int damage = (int) ((getPower() + weaponPower) * (getHp() / 100.0) + 1);
        if (getDexterity() >= 100 || getDexterity() >= (new Random().nextInt(0, 100))) {
            damageDealt = new DamageDealt(leadingHand, damage);
        } else {
            damageDealt = new DamageDealt(leadingHand, 0);
        }

        return anotherEntity.takeDamage(damageDealt);
    }

    /**
     * Применяет эффект зелья на пользователя.
     */
    public void usePotion(Potion potion) {
        if (potion instanceof HealingPotion) {
            int currentHp = getHp();
            int maxHp = getMaxHp();

            setHp(Math.min(currentHp + potion.getEffect(), maxHp));
        }
    }

    /**
     * Печатает содержимое рюкзака с привязкой по индексу
     */
    public void printBackpack() {
        StringBuilder sb = new StringBuilder("Содержимое рюкзака:\n");
        for (int i = 0; i < backpack.size(); i++) {
            Item item = backpack.get(i);
            sb.append("[").append(i).append("]\t").append(item.getTitle()).append("\t").append(item.getPrice()).append("$\n");
        }

        System.out.println(sb);
    }

    /**
     * Метод смены предмета в руке героя. Меняет выбранный предмет и текущий предмет в руке местами в памяти.
     * Используется командой "Взять + индекс".
     */
    public void swap(int selectedItemIndex) {
        if (selectedItemIndex >= backpack.size() || selectedItemIndex < 0) {
            throw new IllegalArgumentException("Не существует предмета в рюкзаке с таким индексом!");
        }

        Item selectedItem = backpack.get(selectedItemIndex);
        backpack.set(selectedItemIndex, leadingHand);
        leadingHand = selectedItem;
    }

    /**
     * Метод накопления опыта. Накапливает до достижения границы уровня.
     * При достижении границы уровня предлагает игроку выбрать повышение.
     */
    public void gainExperience(int experience) {
        if (experience < 0) {
            throw new IllegalArgumentException("Experience can not be less then 0");
        }
        if (experience + currentExperience >= 100) {
            int levelsToUp = experience + currentExperience / 100;
            int remain = (experience + currentExperience) % 100;

            for (int i = 0; i < levelsToUp; i++) {
                int increaseType = Strategy.activityIncreaseLevel(getLevel());
                switch (increaseType) {
                    case Strategy.INCREASE_TYPE_HP -> setMaxHp((int) (getMaxHp() * 1.1)); // +10% к уровню здоровья

                    case Strategy.INCREASE_TYPE_DEXTERITY -> setDexterity((int) (getDexterity() * 1.05)); // +5% к ловкости

                    case Strategy.INCREASE_TYPE_POWER -> setPower((int) (getPower() * 1.1)); // +10% к базовой силе

                    default -> throw new RuntimeException("Unprocessed increase type: " + increaseType);

                }
                increaseLevel();
                totalExperience = (int) (totalExperience * 1.25); // +25% к потолку опыта
            }
        } else {
            currentExperience += experience;
        }
    }
}
