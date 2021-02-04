package model.objects.ghosts.movement;

import model.objects.field.Field;
import model.objects.field.FieldType;
import model.objects.ghosts.Ghost;
import util.Direction;
import util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Road {
    // Based on
    // https://rosettacode.org/wiki/A*_search_algorithm#Java

    private final int xS;
    private final int yS;
    private final Field[][] map;
    private final List<Node> open;
    private final List<Node> closed;
    private final List<Node> path;
    private Node now;

    private final Pair<Integer,Integer> lTunnel = new Pair<>(28,12);
    private final Pair<Integer,Integer> rTunnel = new Pair<>(-1,12);

    class Node implements Comparable<Node> {
        public final Node parent;
        public final int x;
        public final int y;
        public float g;
        public final float h;

        Node(Node parent, int yPos, int xPos, float g, float h) {
            this.parent = parent;
            this.x = xPos;
            this.y = yPos;
            this.g = g;
            this.h = h;
        }

        @Override
        public int compareTo(Node o) {
            return (int) ((this.g + this.h) - (o.g + o.h));
        }
    }

    public Road(Field[][] map, Ghost ghost, int x, int y) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        this.xS = x;
        this.yS = y;
        this.map = map;
        this.now = new Node(null, ghost.getY(), ghost.getX(), 0, 0);
    }

    public LinkedList<Field> findPathTo() {
        this.closed.add(this.now);
        addNeighborsToOpenList();
        while (this.now.y != this.yS || this.now.x != this.xS) {
            if (this.open.isEmpty()) {
                return null;
            }
            this.now = this.open.get(0);
            this.open.remove(0);

            this.closed.add(this.now);
            addNeighborsToOpenList();
        }
        this.path.add(0, this.now);
        while (this.now.parent != null) {
            this.now = this.now.parent;
            this.path.add(0, this.now);
        }
        LinkedList<Field> list = new LinkedList<>();
        for (Node node : this.path) {
            list.add(new Field(node.x, node.y, FieldType.Empty));
        }

        return list;
    }

    private float distance(int dx, int dy) {
        return Math.abs(this.now.x + dx - this.xS) + Math.abs(this.now.y + dy - this.yS);
    }

    private void addNeighborsToOpenList() {
        Node node;
        List<Pair<Field, Direction>> nodes = getNodes();
        int x = 0, y = 0;
        float cost;
        for (Pair<Field, Direction> item : nodes) {
            cost = this.distance(x, y);
            switch (item.getY()) {
                case Up:
                    y = -1;
                    break;
                case Right:
                    x = 1;
                    break;
                case Down:
                    y = 1;
                    break;
                case Left:
                    x = -1;
                    break;
                case None:
                    if(item.getX().getX() == 0)
                        x = -this.now.x;
                    else
                        x = item.getX().getX();
                    cost = 1;
                    break;

            }
            node = new Node(this.now, this.now.y + y, this.now.x + x, this.now.g, cost);
            node.g += 1;
            if(!find(closed,node) && !find(open,node))
                open.add(node);
            x = 0;
            y = 0;
        }
        Collections.sort(this.open);
    }

    /**
     * Gets all adjacent nodes. Makes sure to take into consideration tunnel nodes
     * @return all neighbouring nodes
     */
    private List<Pair<Field, Direction>> getNodes() {
        List<Pair<Field, Direction>> nodes = new ArrayList<>();
        if (this.now.y - 1 >= 0)
            if (!map[this.now.y - 1][this.now.x].getType().equals(FieldType.Wall))
                nodes.add(new Pair<>(new Field(this.now.x, this.now.y - 1, FieldType.Empty), Direction.Up));
        if (this.now.y + 1 < map.length)
            if (!map[this.now.y + 1][this.now.x].getType().equals(FieldType.Wall))
                nodes.add(new Pair<>(new Field(this.now.x, this.now.y + 1, FieldType.Empty), Direction.Down));
        if (this.now.x - 1 >= 0)
            if (!map[this.now.y][this.now.x - 1].getType().equals(FieldType.Wall))
                //noinspection SuspiciousNameCombination
                nodes.add(new Pair<>(new Field(this.now.x - 1, this.now.y, FieldType.Empty), Direction.Left));
        if (this.now.x + 1 < map[0].length)
            if (!map[this.now.y][this.now.x + 1].getType().equals(FieldType.Wall))
                //noinspection SuspiciousNameCombination
                nodes.add(new Pair<>(new Field(this.now.x + 1, this.now.y, FieldType.Empty), Direction.Right));
        if(this.now.x - 1 == rTunnel.getX() && this.now.y == rTunnel.getY()){
            nodes.add(new Pair<>(new Field(lTunnel.getX() - 1, lTunnel.getY(),FieldType.Empty),Direction.None));
        }
        if(this.now.x + 1 == lTunnel.getX() && this.now.y == lTunnel.getY())
            nodes.add(new Pair<>(new Field(rTunnel.getX() + 1, rTunnel.getY(),FieldType.Empty),Direction.None));
        return nodes;

    }

    private boolean find(List<Node> list, Node node) {
        if(list.size() == 0)
            return false;
        return list.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
    }

}


