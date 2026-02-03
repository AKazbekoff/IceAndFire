package Entities;

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

    /*
     * Метод нанесения урона: в качестве аргумента принимает сущность, которую атакуют.
     * Возвращает значение, сколько на самом деле было нанесено урона.
     * */
    public abstract int dealDamage(Entity anotherEntity);

    /*
     * Метод получения урона. Возвращает значение, сколько на самом деле было получено урона.
     * */
    public abstract int takeDamage();
}
