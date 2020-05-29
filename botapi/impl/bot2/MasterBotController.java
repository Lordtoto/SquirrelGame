package Spiel.botapi.impl.bot2;

import Spiel.XY;
import Spiel.XYsupport;
import Spiel.botapi.BotController;
import Spiel.botapi.ControllerContext;
import Spiel.botapi.SpawnException;
import Spiel.core.LoggingHandler;
import Spiel.entities.EntityType;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;


public class MasterBotController implements BotController {

    private ControllerContext view;
    int move = 0;

    @Override
    public void nextStep(ControllerContext view) {
        this.view = view;
        ControllerContext proxy = (ControllerContext) Proxy.newProxyInstance(
                ControllerContext.class.getClassLoader(),
                new Class[] {ControllerContext.class},
                new LoggingHandler(view)
        );
        move++;
        int bestMoveEnergy = 0;
        XY bestMove = new XY(0,0);
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                int moveEnergy;
                XY move = new XY(x, y);
                EntityType target = proxy.getEntityAt(proxy.locate().plus(move));
                moveEnergy = evaluateMove(target);
                if (moveEnergy >= bestMoveEnergy) {
                    bestMoveEnergy = moveEnergy;
                    bestMove = move;
                }
            }
        }
        if (bestMoveEnergy == 0) {
            if (nearestGood() != null) {
                bestMove = findPath(nearestGood());
            } else {
                if (proxy.getEnergy() >= 500) {
                    try {
                        proxy.spawnMiniBot(XYsupport.randomVector(), 250);
                    } catch (SpawnException e) {
                        e.printStackTrace();

                    }
                }
                int tries = 0;
                while ((evaluateMove(view.getEntityAt(view.locate().plus(bestMove))) == 0) && tries < 20) {
                    bestMove = XYsupport.randomVector();
                    tries++;
                }
            }
            int tries = 0;
            while ((evaluateMove(view.getEntityAt(view.locate().plus(bestMove))) < 0) && tries < 20) {
                bestMove = XYsupport.randomVector();
                tries++;
            }

        }
        if (move % 20 == 0 && view.getEnergy() >= 500) {
            try {
                view.spawnMiniBot(XYsupport.randomVector(), 200);
            } catch (SpawnException e) {
                e.printStackTrace();
            }
        }
        proxy.move(bestMove);
        System.out.println(view.getRemainingSteps());
    }

    private Map<XY, EntityType> mapSurrounding() {
        Map<XY, EntityType> out = new LinkedHashMap<XY, EntityType>();
        for (int x = view.getViewLowerLeft().getX(); x < view.getViewUpperRight().getX(); x++) {
            for (int y = view.getViewLowerLeft().getY(); y < view.getViewUpperRight().getY(); y++) {
                XY targetLoc = new XY(x, y);
                out.put(targetLoc, view.getEntityAt(targetLoc));
            }
        }
        return out;
    }

    private XY nearestGood() {
        XY nearestGoodLoc = null;
        int distance = 15;
        for (Map.Entry<XY, EntityType> entry: mapSurrounding().entrySet()) {
            EntityType entity = entry.getValue();
            XY entityLoc = entry.getKey();
            if (evaluateMove(entity) > 0) {
                if (entityLoc.distanceFrom(view.locate()) <= distance) {
                    nearestGoodLoc = entityLoc;
                    distance = (int) entityLoc.distanceFrom(view.locate());
                }
            }
        }
        return nearestGoodLoc;
    }


    private XY closeDistance(XY destination) {
        int moveX = (int)Math.signum(destination.getX() - view.locate().getX());
        int moveY = (int)Math.signum(destination.getY() - view.locate().getY());
        return (new XY(moveX, moveY));
    }

    private XY findPath (XY destination) {
        XY pathMove = closeDistance(destination);
        if (view.getEntityAt(view.locate().plus(pathMove)) == EntityType.NONE) {
            return pathMove;
        }
        if(pathMove.getX() == -1) {
            pathMove = pathMove.plus(new XY(1, 0));
        } else  if(pathMove.getX() == 1) {
            pathMove = pathMove.minus(new XY(1, 0));
        }  if(pathMove.getX() == 0) {
            pathMove = pathMove.plus(new XY(1, 0));
            if (view.getEntityAt(view.locate().plus(pathMove)) == EntityType.NONE) {
                pathMove = pathMove.plus(XY.LEFT);
            }
        } if (pathMove.getY() == 0) {
            pathMove = pathMove.minus(new XY(0, 1));
            if (view.getEntityAt(view.locate().plus(pathMove)) == EntityType.NONE) {
                pathMove = pathMove.plus(XY.UP);
            }
        }
        return pathMove;
    }

    /* private XY altPath(XY destination) {
        XY pathMove = closeDistance(destination);
        if (view.getEntityAt(view.locate().plus(pathMove)) == EntityType.NONE) {
            return pathMove;
        }
        if(pathMove.getY() == -1) {
            pathMove = pathMove.plus(XY.UP);
        } else  if(pathMove.getY() == 1) {
            pathMove = pathMove.plus(XY.DOWN);
        }  if(pathMove.getX() == 0) {
            pathMove = pathMove.plus(XY.LEFT);
        } if (pathMove.getY() == 0) {
            pathMove = pathMove.plus(XY.UP);
        }
        return pathMove;
    }

     */

    private int evaluateMove(EntityType target) {
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
                return -1;
            case MiniSquirrel:
                return -5;
            case Wall:
                return -10;
            case NONE:
                return 0;
            default:
                throw new IllegalStateException("Unexpected value: " + target);
        }
    }

}