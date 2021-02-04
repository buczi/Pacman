package model.objects.ghosts;

import model.objects.field.Field;
import model.objects.field.FieldType;
import model.objects.ghosts.movement.Road;
import model.objects.pacman.Pacman;
import util.Direction;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static model.objects.ghosts.movement.MovementBehaviour.endPoint;

/**
 * Ghost which tries to position itself in front of pacman
 */
public class Pinky extends Ghost {
    private final int side = 5;

    /**
     * Class representing part of map
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private class Chunk {
        protected final int xMin;
        protected final int xMax;
        protected final int yMin;
        protected final int yMax;

        protected final List<Pair<Integer, Direction>> near;

        protected Chunk(Field[][] map, int x, int y, int parts) {
            int mapX = map[0].length;
            int mapY = map.length;
            int fx = (int) Math.floor((float) mapX / parts);
            int fy = (int) Math.floor((float) mapY / parts);
            this.xMin = fx * x;
            this.yMin = fy * y;
            this.xMax = x == parts - 1 ? mapX : fx * (x + 1);
            this.yMax = y == parts - 1 ? mapY : fy * (y + 1);
            this.near = new ArrayList<>();
            this.addNearChunks(x,y,parts);
        }

        /**
         * Adds adjacent chunks to current
         * @param x current X
         * @param y current Y
         * @param parts amount of chunks in a row
         */
        private void addNearChunks(int x, int y, int parts) {
            if (x + 1 < parts)
                this.near.add(new Pair<>(x+side*y + 1, Direction.Right));
            if (y + 1 < parts)
                this.near.add(new Pair<>(x+side*(y + 1), Direction.Down));
            if (x - 1 >= 0)
                this.near.add(new Pair<>(x+side*y - 1, Direction.Left));
            if (y - 1 >= 0)
                this.near.add(new Pair<>(x+side*(y - 1), Direction.Up));
            if(x - 1 < 0 && y == (int)Math.floor((float)parts/2))
                this.near.add(new Pair<>(parts - 1+side*y, Direction.Left));
            if(x + 1 > parts && y == (int)Math.floor((float)parts/2))
                this.near.add(new Pair<>(side*y, Direction.Right));
        }

        /**
         * Checks if given point is in the chunk
         * @return true - in  false - not in
         */
        public boolean isInChunk(int x, int y) {
            return xMin <= x && x < xMax
                    && yMin <= y && y < yMax;
        }

        /**
         * Generates random point in chunk
         * @param map game map
         * @return point to which ghost will move
         */
        public Pair<Integer, Integer> getPointInChunk(Field[][] map){
            Random random = new Random();
            int randomX, randomY;
            for(int i = 0; i < (xMax - xMin)*(yMax - yMin); i++){
                randomX = random.nextInt(xMax - xMin) + xMin;
                randomY = random.nextInt(yMax - yMin) + yMin;
                if(!List.of(FieldType.Wall, FieldType.EmptyWall)
                        .contains(map[randomY][randomX]
                                .getType()))
                    return new Pair<>(randomX,randomY);
            }
            return new Pair<>(getX(),getY());
        }

    }

    public Pinky(Field field) {
        super(field);
        this.baseCords = new Pair<>(15, 12);
    }

    public List<Field> getRoute(Field[][] map, Pacman pacman, Direction direction) {
        super.getRoute(map,pacman,direction);
        Pair<Integer,Integer>pair = getEndPoint(map, pacman, direction);
        if (Math.abs(this.getX() - pacman.getX()) + Math.abs(pacman.getY() - this.getY()) <7)
            pair = new Pair<>(pacman.getX(),pacman.getY());
        if (retreatMode)
            return new Road(map, this, baseCords.getX(), baseCords.getY()).findPathTo();
        else if(running){
            Pair<Integer, Integer> nPair = endPoint(map, pacman,this);
            return new Road(map, this, nPair.getX(), nPair.getY()).findPathTo();
        }
        return new Road(map, this, pair.getX(), pair.getY()).findPathTo();
    }

    /**
     * Gets ghost destination base on chunks
     * @param map game map
     * @param pacman pacman object
     * @param direction current pacman direction
     * @return ghost destination point
     */
    private Pair<Integer, Integer> getEndPoint(Field[][] map, Pacman pacman, Direction direction) {
        Chunk[] chunks = new Chunk[side*side];
        int pacmanChunk = 0;
        for(int i = 0; i < side; i++)
            for(int j = 0; j < side; j++){
                chunks[i*side + j] = new Chunk(map,j,i,side);
                if(chunks[i*side + j].isInChunk(pacman.getX(),pacman.getY()))
                    pacmanChunk = i*side + j;
            }
        for(int i = 0; i < side*side; i++){
            for(Pair<Integer,Direction> x : chunks[i].near){
                if(x.getY().equals(direction)
                        && pacmanChunk == i){
                    return chunks[x.getX()].getPointInChunk(map);
                }
            }
        }
        return chunks[pacmanChunk].getPointInChunk(map);
    }



}
