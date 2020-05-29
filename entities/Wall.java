package Spiel.entities;

import Spiel.XY;

public class Wall extends Entity {
    private static final int initial_energy = -10;

    public Wall(XY location) {
        super(location, initial_energy);
    }

    public Wall() {
    }

    @Override
    public void nextStep() {
    }
}
