package Entities;

import Common.DamageDealt;
import Items.Weapon;
import Items.Weapons.IronSword;

public class WildingHuman extends Entity {

    // По умолчанию держит в руке Железный Меч
    Weapon leadingHand = new IronSword();

    /**
     * Конструктор "Одичалого" зависимый на уровне сущности.
     * Чем выше уровень, тем выше параметры сущности, следовательно, выше уровень сложности при битве с такой сущностью.
     */
    public WildingHuman(int level) {
        super(
                "Одичалый",
                50,
                50,
                30 + 5 * level,
                50 + 2 * level,
                level
        );
    }

    /**
     * Конструктор "Одичалого" зависимый на уровне сущности.
     * Чем выше уровень, тем выше параметры сущности, следовательно, выше уровень сложности при битве с такой сущностью.
     * Принимает вторым параметром оружие сущности. Этим предметом сущность может наносить урон.
     */
    public WildingHuman(int level, Weapon weapon) {
        this(level);
        leadingHand = weapon;
    }

    /**
     * Переопределение базового метода getPower(). Учитывает урон от оружия.
     * Формула: 0.5 * entity.power + weapon.power
     */
    @Override
    public int getPower() {
        return (int) Math.round(0.5 * super.getPower() + leadingHand.getPower());
    }

    /**
     * Двойной удар. Наносит удар с двух кулаков. Сила удара равна 175% от базовой силы.
     * Возвращает сколько урона на самом деле было нанесено.
     */
    public int doubleStrikeAttack(Entity entity) {
        int damage = (int) Math.round(getPower() * 1.75);

        return dealDamage(entity, damage);
    }

    public int uppercutAttack(Entity entity) {
        return -1; //TODO: дописать этот метод
    }
}
