package Spiel.core;

import Spiel.XY;
import Spiel.entities.EntityType;

public interface BoardView {
    EntityType getEntityType(int x, int y);

    XY getSize();
}
