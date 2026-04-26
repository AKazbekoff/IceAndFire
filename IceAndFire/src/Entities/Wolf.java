package Entities;

import Common.DamageDealt;

public class Wolf extends Entity {

    /**
     * Конструктор "Волка" зависимый на уровне сущности.
     * Чем выше уровень, тем выше параметры сущности, следовательно, выше уровень сложности при битве с такой сущностью.
     */
    public Wolf(int level) {
        super(
                "Волк",
                100 + 5 * level,
                100 + 5 * level,
                15 + 3 * level,
                50 + 10 * level,
                level
        );
    }

    /**
     * Сильный укус. Наносит удвоенный размер урона (от базовой силы сущности).
     * Возвращает сколько урона на самом деле было нанесено.
     * */
    public int strongBiteAttack(Entity entity) {
        return dealDamage(entity, getPower() * 2);
    }
}
