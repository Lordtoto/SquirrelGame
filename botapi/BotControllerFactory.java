package Spiel.botapi;

public interface BotControllerFactory {
    BotController createMasterBotController();
    BotController createMiniBotController();
}
