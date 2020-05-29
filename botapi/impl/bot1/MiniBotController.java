package Spiel.botapi.impl.bot1;

import Spiel.XY;
import Spiel.XYsupport;
import Spiel.botapi.BotController;
import Spiel.botapi.ControllerContext;
import Spiel.entities.EntityType;

public class MiniBotController implements BotController {

    @Override
    public void nextStep(ControllerContext view) {
        int bestMoveEnergy = 0;
        XY bestMove = new XY(0, 0);
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                int moveEnergy;
                XY move = new XY(x, y);
                EntityType target = view.getEntityAt(view.locate().plus(move));
                moveEnergy = evaluateMove(target);
                if (moveEnergy >= bestMoveEnergy) {
                    bestMoveEnergy = moveEnergy;
                    bestMove = move;
                }
            }
        }
        if (bestMoveEnergy == 1) {
            do {
                bestMove = XYsupport.randomVector();
            } while (evaluateMove(view.getEntityAt(view.locate().plus(bestMove))) < 0);
        }
        if (XYsupport.randomVector().equals(new XY(1, 1))) {
            view.implode(10);
            return;
        }
        view.move(bestMove);
    }

    private int evaluateMove(EntityType target) {
        if (target == null) {
            return 1;
        } else {
            switch (target) {
                case BadBeast:
                    return -150;
                case GoodPlant:
                    return 100;
                case GoodBeast:
                    return 150;
                case BadPlant:
                    return -100;
                case MasterSquirrel:
                    return -1000;
                case MiniSquirrel:
                    return -5;
                default:
                    return -10;
            }
        }
    }
}
