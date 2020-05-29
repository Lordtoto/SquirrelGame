package Spiel.botapi.impl.bot1;

import Spiel.XY;
import Spiel.XYsupport;
import Spiel.botapi.BotController;
import Spiel.botapi.ControllerContext;
import Spiel.core.LoggingHandler;
import Spiel.entities.EntityType;

import java.lang.reflect.Proxy;

public class MasterBotController implements BotController {
    @Override
    public void nextStep(ControllerContext view) {
        ControllerContext proxy = (ControllerContext) Proxy.newProxyInstance(
                ControllerContext.class.getClassLoader(),
                new Class[]{ControllerContext.class},
                new LoggingHandler(view)
        );
        proxy.move(XYsupport.randomVector());
    }
}