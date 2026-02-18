package Entities;

import Common.DamageDealt;

public abstract class Entity {

    private final String name; // Имя сущности
    private int hp; // Уровень здоровья
    private int maxHp; // Максимальный уровень здоровья
    private int power; // Сила
    private int dexterity; // Ловкость
    private int level; // Уровень сущности

    /*
     * Конструктор сущности по умолчанию.
     * */
    public Entity(String name) {
        this.name = name;
        this.hp = 100;
        this.maxHp = hp;
        this.power = 5;
        this.dexterity = 50;
        this.level = 1;
    }

    /*
     * Конструктор для пользовательской конфигурации сущности (используется для создания монстров)
     * */
    public Entity(String name, int hp, int maxHp, int power, int dexterity, int level) {
        this.name = name;
        this.hp = hp;
        this.maxHp = maxHp;
        this.power = power;
        this.dexterity = dexterity;
        this.level = level;
    }

    /**
     * Классический расчет урона: сила + зависимость от уровня здоровья.
     * Ловкость определяет верхнюю границу, при котором будет удар считаться успешным.
     * Так, если ловкость = 50, а случайное сгенерированное число 67, то удар считается не удачным, что означает,
     * что герой промахнулся и было нанесено 0 урона. В обратном случае герой наносит полный рассчитанный размер урона.
     */
    public int dealDamage(Entity anotherEntity) {
        int damage = (int) (getPower() * (hp / 100.0) + 1);
        DamageDealt damageDealt = new DamageDealt(null, damage);

        return anotherEntity.takeDamage(damageDealt);
    }

    /**
     * Метод получения урона. Возвращает значение, сколько на самом деле было получено урона.
     */
    protected int takeDamage(DamageDealt damageDealt) {
        setHp(Math.max(0, hp - damageDealt.getDamage()));
        return damageDealt.getDamage();
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    protected void setMaxHp(int hp) {
        maxHp = hp;
    }

    protected void setHp(int hp) {
        this.hp = hp;
    }

    public int getPower() {
        return power;
    }

    protected void setPower(int power) {
        this.power = power;
    }

    public int getLevel() {
        return level;
    }

    protected void increaseLevel() {
        level += 1;
    }

    public int getDexterity() {
        return dexterity;
    }

    protected void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }
}
