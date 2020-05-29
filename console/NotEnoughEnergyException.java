package Spiel.console;

public class NotEnoughEnergyException extends Exception {

    public NotEnoughEnergyException() {
        super();
    }

    public NotEnoughEnergyException(String msg) {
        super(msg);
    }
}
