package Spiel.botapi.impl.bot1;

import Spiel.botapi.BotController;
import Spiel.botapi.BotControllerFactory;

public class BotControllerFactoryImpl implements BotControllerFactory {
    @Override
    public BotController createMasterBotController() {
        return new MasterBotController();
    }

    @Override
    public BotController createMiniBotController() {
        return new MiniBotController();
    }
}
