package Effects;

public class Effect {

    /*
    * Константа для указания эффекта на понижение ловкости
    * */
    public static final int EFFECT_LOW_DEXTERITY = 0;

    /*
    * Тип эффекта, например, EFFECT_LOW_DEXTERITY
    * */
    private int effectType;

    /*
    * Длительность эффекта: сколько ходов активно
    * */
    private int effectDuration;

    /*
    * Размер действующего эффекта, например, 30 - 30%
    * */
    private int effectAmount;

    public Effect(int effectType, int effectDuration, int effectAmount) {
        this.effectType = effectType;
        this.effectDuration = effectDuration;
        this.effectAmount = effectAmount;
    }

    /**
     * Геттер для поля effectDuration
     * */
    public int getDuration() {
        return effectDuration;
    }

    /**
     * Геттер для поля effectAmount
     * */
    public int getAmount() {
        return effectAmount;
    }

    /**
     * Геттер для поля effectType
     * */
    public int getType() {
        return effectType;
    }

    public void decrementDuration() {
        effectDuration--;
    }
}
