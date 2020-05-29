package Spiel.entities;

public enum EntityType {
    GoodBeast { @Override public boolean isCharacter() { return true; }},
    BadBeast { @Override public boolean isCharacter() { return true; }},
    GoodPlant {@Override public boolean isCharacter() { return false; }},
    BadPlant {@Override public boolean isCharacter() { return false; }},
    Wall {@Override public boolean isCharacter() { return false; }},
    MasterSquirrel {@Override public boolean isCharacter() { return true; }},
    MiniSquirrel {@Override public boolean isCharacter() { return true; }},
    NONE {@Override public boolean isCharacter() { return false; }};

    public abstract boolean isCharacter();
}

