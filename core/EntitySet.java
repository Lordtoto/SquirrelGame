package Spiel.core;

import Spiel.XY;
import Spiel.entities.Character;
import Spiel.entities.Entity;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntitySet {
    private Comparator<Entity> comparator = new ComparatorImpl();
    //private SortedSet<Entity> container = new TreeSet<Entity>(comparator);
    private List<Entity> container = new CopyOnWriteArrayList<>();

    public Collection<Entity> getContainer() {
        return container;
    }

    /* private int findEmptyPosition() {
        for (int i = 0; i < MAXLENGTH; i++) {
            if (container[i] == null)
                return i;
        }
        return MAXLENGTH -1;
    }

     */

    public boolean add(Entity entity) {
        return container.add(entity);
    }


    /* public void removeEntity(int position) {
         if(position == MAXLENGTH - 1) {
             container[position] = null;
         }
         try{
             while (container[position] != null) {
                 container[position] = container[position+1];
                 position++;
                 if(position == MAXLENGTH -1) {
                     container[position] = null;
                     return;
                 }
             }
         }
         catch (ArrayIndexOutOfBoundsException e) {
             System.out.println(e.toString());
             System.out.println();
         }
     }


     */
    public Entity searchByLocation(XY location) {
        for (Entity entity : container) {
            if (location.equals(entity.getLocation()))
                return entity;
        }
        return null;
    }


    public boolean removeEntityById(int id) {
        for (Entity entity : container) {
            if (entity.getId() == id) {
                return container.remove(entity);
            }
        }
        return false;
    }

    public boolean removeEntity(Entity entity) {
        return container.remove(entity);
    }

    public boolean equalsAny(XY location) {
        for (Entity entity : container) {
            if (location.equals(entity.getLocation())) {
                return true;
            }
        }
        return false;
    }

    public void nextStepAll(EntityContext entityContext) {
        for (Entity entity : container) {
            if (entity instanceof Character) {
                entity.nextStep(entityContext);
            }
        }
    }

    public boolean isLocUsed(XY loc) {
        return equalsAny(loc);
    }


    public String toString() {
        if (container.isEmpty()) {
            return null;
        }

        String all = "";
        for (Entity entity : container) {
            all += entity + "\n";
        }
        return all;
    }

    public static class ComparatorImpl implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof Entity && o2 instanceof Entity))
                return 0;
            if (((Entity) o1).getId() == ((Entity) o1).getId()) {
                return 0;
            }
            if (((Entity) o1).getId() < ((Entity) o1).getId()) {
                return -1;
            } else return 1;
        }
    }
}