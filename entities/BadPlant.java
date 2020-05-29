package Spiel.entities;

import Spiel.XY;

public class BadPlant extends Entity {
    private static final int initial_energy = -100;

    public BadPlant(XY location) {
        super(location, initial_energy);
    }

    public BadPlant() {
    }

    @Override
    public void reset() {
        setEnergy(initial_energy);
    }

    @Override
    public void nextStep() {
    }
}
