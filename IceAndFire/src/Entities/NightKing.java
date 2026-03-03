package Entities;

public class NightKing extends Entity {

    /**
     * Конструктор "Короля ночи".
     * Существует всегда в одном представлении.
     */
    public NightKing() {
        super(
                "Белый Ходок",
                5000,
                5000,
                99,
                90,
                1
        );
    }

    //TODO: продумать специализированные атаки для этой сущности.
}
