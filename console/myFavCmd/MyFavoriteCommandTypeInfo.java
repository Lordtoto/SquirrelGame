package Spiel.console.myFavCmd;

public interface MyFavoriteCommandTypeInfo {

    String getName();

    String getHelpText();

    Class<?>[] getParamTypes();

    String getMethodName();

}
