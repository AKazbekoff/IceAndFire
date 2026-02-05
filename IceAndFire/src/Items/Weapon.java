package Items;

public abstract class Weapon extends Item {

    private int power;
    private String description;
    private int replicaCode;

    public Weapon(String title, int power, String description, int replicaCode) {
        super(title);
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
