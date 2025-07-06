package entity;

public enum EntityType {
    ENEMY("INIMIGO"),
    BOSS("CHEFE"),
    POWERUP("POWERUP");

    public final String label;

    EntityType(String label) {
        this.label = label;
    }

    public static EntityType fromLabel(String label) {
        for (EntityType type : EntityType.values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de entidade desconhecido: " + label);
    }
}
