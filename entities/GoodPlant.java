package Spiel.entities;

import Spiel.XY;

public class GoodPlant extends Entity {
    private static final int initial_energy = 100;

    public GoodPlant(XY location) {
        super(location, initial_energy);
    }

    public GoodPlant() {
    }

    public void reset() {
        setEnergy(initial_energy);
    }


    @Override
    public void nextStep() {
    }
}
