package Entities;

import Common.DamageDealt;

public class Direwolf extends Entity {

    /**
     * Конструктор "Лютоволка" зависимый на уровне сущности.
     * Чем выше уровень, тем выше параметры сущности, следовательно, выше уровень сложности при битве с такой сущностью.
     */
    public Direwolf(int level) {
        super(
                "Лютоволк",
                100 + 10 * level,
                100 + 10 * level,
                30 + 2 * level,
                40 + 3 * level,
                level
        );
    }

    /**
     * Рассекающий удар. Наносит очень сильный урон, уменьшает здоровье атакованной сущности на (level * 10)%, максимум 50%.
     * Возвращает сколько урона было нанесено.
     */
    public int slashingBlow(Entity entity) {
        int level = Math.min(getLevel(), 5); // Ограничиваем максимумом
        int percent = Math.min(level * 10, 50); // level * 10%, но не больше 50%
        int damage = (int) (entity.getHp() * (percent / 100.0));

        DamageDealt damageDealt = new DamageDealt(null, damage);
        return entity.takeDamage(damageDealt);
    }

    /**
     * Атака рывком. Наносит 30% от здоровья урона при уровне монстра < 5 и 50% при уровне >= 5.
     * Возвращает сколько урона было нанесено.
     * */
    public int dashAttack(Entity entity) {
        int damagePercent = (getLevel() < 5) ? 30 : 50;
        int damage = (int) (entity.getHp() * (damagePercent / 100.0));

        DamageDealt damageDealt = new DamageDealt(null, damage);
        return entity.takeDamage(damageDealt);
    }
}
