package Spiel.entities;

import Spiel.XY;
import Spiel.core.EntityContext;

import java.util.Objects;

public class Entity {
    private int iD;
    private int energy;
    private XY location;

    private static int counter = 0;

    protected Entity(XY location, int energy) {
        this.iD = counter;
        this.energy = energy;
        this.location = location;
        counter++;
    }

    public Entity() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return iD == entity.iD &&
                energy == entity.energy &&
                Objects.equals(location, entity.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iD, energy, location);
    }

    public void setLocation (XY location) {
        this.location = location;
    }

    public static int getCounter() {
        return counter;
    }

    public int getId() {
        return iD;
    }

    public int getEnergy() {
        return energy;
    }

    public void newLocation (XY location) {
        this.location = location;
    }

    public void updateEnergy(int deltaEnergy) {
        this.energy = this.energy + deltaEnergy;
    }

    public void nextStep(EntityContext entityContext)  {
    }

    public void nextStep() {
    }

    public void reset() {}

    public XY getLocation() {
        return location;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " id: " + getId() + " energy: " + getEnergy() + " x-Koordinate: " + location.getX() + " y-Koordinate " + location.getY();
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}