package Entities;

import Common.DamageDealt;
import Effects.Effect;

public class Giant extends Entity {

    /**
     * Конструктор "Великана" зависимый на уровне сущности.
     * Обладает крайне высокой базовой силой и уровнем здоровья. При этом при повышении уровня эти параметры вырастают.
     * Однако обладает крайне низкой ловкостью, при этом с повышением уровня этот параметр уменьшается
     */
    public Giant(int level) {
        super(
                "Великан",
                200 + 50 * level,
                200 + 50 * level,
                85 + 20 * level,
                20 - level,
                level
        );
    }

    /**
     * Атака "Капкан". Фатальна для любой сущности угодившей в эту атаку. Великан откусывает голову, убивая тем самым
     * моментально.
     * Является специализированной атакой, не зависит от уровня здоровья.
     * Возвращает сколько урона было нанесено.
     */
    public int trapAttack(Entity entity) {
        return dealDamage(entity, entity.getMaxHp());
    }

    /**
     * Атака "Молот". В зависимости от уровня сущности наносит 20%, 30%, 40%, 50%, максимально 60% урона от уровня
     * здоровья атакованной сущности.
     * Является специализированной атакой, не зависит от уровня здоровья.
     * Возвращает сколько урона было нанесено.
     */
    public int hummerAttack(Entity entity) {
        int level = Math.min(getLevel(), 5); // Ограничиваем максимумом
        int percent = Math.min(level * 10 + 10, 60); // (level * 10 + 10)%, но не больше 50%
        int damage = (int) (entity.getMaxHp() * (percent / 100.0));

        return dealDamage(entity, damage);
    }

    /**
     * Атака "Хлопок". Наносит небольшое количество урона.
     * Является специализированной атакой, не зависит от уровня здоровья.
     * Если атакует пользователя, наносит на игрока эффект снижения ловкости на 5 ходов в размере 30%.
     * Возвращает сколько урона было нанесено.
     */
    public int swatAttack(Entity entity) {
        if (entity instanceof GameCharacter) {
            ((GameCharacter) entity).applyEffect(new Effect(Effect.EFFECT_LOW_DEXTERITY, 5, 30));
        }
        return dealDamage(entity, 20);
    }
}
