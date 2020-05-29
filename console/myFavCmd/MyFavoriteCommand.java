package Spiel.console.myFavCmd;

import java.util.Arrays;

public class MyFavoriteCommand {
    private final MyFavoriteCommandTypeInfo myFavoriteCommandTypeInfo;
    private final Object[] params;

    public MyFavoriteCommand(MyFavoriteCommandTypeInfo myFavoriteCommandTypeInfo, Object... params) {
        this.myFavoriteCommandTypeInfo = myFavoriteCommandTypeInfo;
        this.params = params;
    }

    public MyFavoriteCommandTypeInfo getCommandType() {
        return myFavoriteCommandTypeInfo;
    }

    public Object[] getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandType=" + myFavoriteCommandTypeInfo +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
