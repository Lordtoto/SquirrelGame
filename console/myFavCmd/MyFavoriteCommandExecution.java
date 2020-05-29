package Spiel.console.myFavCmd;

public class MyFavoriteCommandExecution {

    public void addi(Integer a, Integer b) {
        System.out.println(a + b);
    }

    public void addf(Float a, Float b) {
        System.out.println(a + b);
    }

    public void echo(String s, Integer i) {
        for (int j=0; j < i; j++) {
            System.out.println(s);
        }
    }

    public void help() {
        StringBuilder s = new StringBuilder();
        for (MyFavoriteCommandType m : MyFavoriteCommandType.values()) {
            s.append(m.getName()).append(" | ");
        }
        System.out.println(s);
    }

    public void exit() {
        System.exit(0);
    }
}
