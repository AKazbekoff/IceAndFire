package Entities;

import Common.DamageDealt;
import Items.Item;

import java.util.Random;

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
        this.power = 20;
        this.dexterity = 50;
        this.level = 1;
    }

    /*
     * Конструктор для пользовательской конфигурации сущности
     * */
    public Entity(String name, int hp, int maxHp, int power, int dexterity, int level) {
        if (level < 1) {
            throw new IllegalArgumentException("Level can not be less then 1");
        }

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
     * что сущность промахнулась и было нанесено 0 урона. В обратном случае сущность наносит полный рассчитанный размер урона.
     */
    public int dealDamage(Entity anotherEntity) {
        DamageDealt damageDealt;
        int damage = (int) (getPower() * (hp / 100.0) + 1);
        if (dexterity >= 100 || dexterity >= (new Random().nextInt(0, 100))) {
            damageDealt = new DamageDealt(null, damage);
        } else {
            damageDealt = new DamageDealt(null, 0);
        }

        return anotherEntity.takeDamage(damageDealt);
    }

    /**
     * Метод нанесения урона сущности специализированными атаками сущностей.
     * Наносит уже рассчитанный урон.
     * Возвращает сколько урона на самом деле было нанесено.
     * */
    public int dealDamage(Entity anotherEntity, int damage) {
        return dealDamage(anotherEntity, damage, null);
    }

    /**
     * Метод нанесения урона сущности специализированными атаками сущностей.
     * Наносит уже рассчитанный урон. Принимает так же в качестве аргумента предмет, которым был нанесен удар.
     * Возвращает сколько урона на самом деле было нанесено.
     * */
    public int dealDamage(Entity anotherEntity, int damage, Item item) {
        DamageDealt damageDealt = new DamageDealt(item, damage);
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
