package Items;

public abstract class Weapon extends Item {

    /*
    * Базовый урон наносимый этим оружием
    * */
    private int power;

    /*
    * Описание этого оружия
    * */
    private String description;

    /*
    * Код для проигрывания определенных реплик связанных с использованием игрока этого оружия
    * */
    private int replicaCode;

    public Weapon(String title, int power, String description, int replicaCode, int price) {
        super(title, price);
        this.power = power;
        this.description = description;
        this.replicaCode = replicaCode;
    }

    public int getPower() {
        return power;
    }

    public String getDescription() {
        return description;
    }

    public int getReplicaCode() {
        return replicaCode;
    }
}
