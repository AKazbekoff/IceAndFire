package Entities;

import Common.DamageDealt;
import Common.Strategy;
import Effects.Effect;
import Items.Item;
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
    * Массив активных эффектов
    * */
    private ArrayList<Effect> activeEffects = new ArrayList<>();

    /*
     * Монеты персонажа. На них он может покупать у торговца амуницию
     * */
    private int coins = 120;

    public GameCharacter(String name) {
        super(name);
    }

    /**
     * Возвращает копию рюкзака.
     */
    public List<Item> getBackpack() {
        return new ArrayList<>(backpack);
    }

    /**
     * Возвращает максимальный размер содержимого рюкзака.
     */
    public int getBackpackLimit() {
        return backpackLimit;
    }

    /**
     * Возвращает количество монет
     * */
    public int getCoinsAmount() {
        return coins;
    }

    /**
     * Изменяет текущее количество монет на переданный аргумент
     */
    public void addCoins(int amount) {
        coins += amount;
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

        // Применяем активные эффекты, которые могут повлиять на расчет урона.
        int currentDexterity = getDexterity();
        for (Effect effect : activeEffects) {
            if (effect.getType() == Effect.EFFECT_LOW_DEXTERITY) {
                // Применяем эффект на ловкость в размере effectAmount выраженного в процентах, округляя до целого числа
                currentDexterity = (int) (currentDexterity * (1 - (effect.getAmount() / 100.0)));
            }
        }

        if (currentDexterity >= 100 || currentDexterity >= (new Random().nextInt(0, 100))) {
            damageDealt = new DamageDealt(leadingHand, damage);
        } else {
            damageDealt = new DamageDealt(leadingHand, 0);
        }

        return anotherEntity.takeDamage(damageDealt);
    }

    /**
     * Применяет эффект зелья на пользователя.
     * Используется, когда пользователь держит зелье в руке командой: "Выпить".
     * Возвращает true, если зелье было применено, и false в обратном случае.
     */
    public boolean usePotion() {
        if (leadingHand instanceof HealingPotion) {
            int currentHp = getHp();
            int maxHp = getMaxHp();

            setHp(Math.min(currentHp + ((HealingPotion) leadingHand).getEffect(), maxHp));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Применяет эффект на пользователя.
     * Имеет выраженный длительный характер.
     * Может быть нанесен другими сущностями.
     * Результат эффектов применяется на каждом ходу перед ключевым действием, пока срок действия эффекта не окончен.
     * */
    public void applyEffect(Effect effect) {
        activeEffects.add(effect);
    }

    /**
     * Метод смены предмета в руке героя. Меняет выбранный предмет и текущий предмет в руке местами в памяти.
     * Используется командой "Взять + индекс".
     * Возвращает Item, который отображается в ведущей руке после применения команды. Возвращает null, если рокировка
     * не удалась.
     */
    public Item swap(int selectedItemIndex) {
        if (selectedItemIndex >= backpack.size() || selectedItemIndex < 0) {
            return null;
        }

        Item selectedItem = backpack.get(selectedItemIndex);
        backpack.set(selectedItemIndex, leadingHand);
        leadingHand = selectedItem;

        return leadingHand;
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

                    case Strategy.INCREASE_TYPE_DEXTERITY ->
                            setDexterity((int) (getDexterity() * 1.05)); // +5% к ловкости

                    case Strategy.INCREASE_TYPE_POWER -> setPower((int) (getPower() * 1.1)); // +10% к базовой силе

                    default -> throw new RuntimeException("Unprocessed increase type: " + increaseType);

                }
                increaseLevel();
                totalExperience = (int) (totalExperience * 1.25); // +25% к потолку опыта
                currentExperience = remain;
            }
        } else {
            currentExperience += experience;
        }
    }

    /**
     * Пересчитывает активные эффекты. Если время вышло - удаляет эффект из коллекции.
     * */
    public void recalculateEffects() {

        var iterator = activeEffects.iterator();
        while (iterator.hasNext()) {
            var effect = iterator.next();
            effect.decrementDuration();
            if (effect.getDuration() <= 0) {
                iterator.remove();
            }
        }
    }
}
