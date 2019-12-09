package com.company;

import java.util.*;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    private static Object o = null;

    public static void main(String[] args) {

        List<Point> points;
        if (args != null && args.length > 0 && "mock".equals(args[0])) {
            points = mockData();
        } else {
            points = readData();
        }


        Map<Point, Set<Edge>> map = generate(points);

        double result;

        if (points.size() == 2) {
            Edge edge = new Edge(points.get(0), points.get(1));
            result = edge.getWeight();
        } else {
            result = calculation(points, map);
        }
        System.out.printf("Minimal length of the network is: %.2f", result);
    }

    private static double calculation(List<Point> points, Map<Point, Set<Edge>> map) {
        List<Point> spanningTree = new ArrayList<>();
        spanningTree.add(points.get(0));
        double minLength = 0.0;
        System.out.println(spanningTree);

        while (spanningTree.size() < map.size()) {

            Edge min = null;
            for (Point point : spanningTree) {
                Set<Edge> edges = map.get(point);
                for (Edge edge : edges) {
                    if (min == null || min.getWeight() > edge.getWeight()) {
                        min = edge;
                    }
                }
            }
            if (min == null) {
                throw new IllegalStateException("min is null");
            }
            minLength += min.getWeight();

            for (Point point : spanningTree) {
                Set<Edge> edges = map.get(point);
                edges.remove(new Edge(point, min.getD()));
            }
            System.out.println(min);
            spanningTree.add(min.getD());
            System.out.println(spanningTree);
        }

        return minLength;
    }

//    private void removePoint(Map<Point, Set<Edge>> map, Point p) {
//        map.remove(p);
//        for (Map.Entry<Point, Set<Edge>> pointSetEntry : map.entrySet()) { //Edge(s, d)
//
//        }
//    }

    private static List<Point> mockData() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(5, 19));
        points.add(new Point(55, 28));
        points.add(new Point(38, 101));
        points.add(new Point(28, 62));
        points.add(new Point(111, 84));
        points.add(new Point(43, 116));

        return points;
    }

    private static List<Point> readData() {
        int computers = scanner.nextInt();
        List<Point> points = new ArrayList<>();

        for (int i = 1; i <= computers; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points.add(new Point(x, y));
        }

        return points;
    }

    private static Map<Point, Set<Edge>> generate(List<Point> points) {
        Map<Point, Set<Edge>> result = new HashMap<>();

        for (Point s : points) {
            result.put(s, new TreeSet<>(Comparator.comparingDouble(Edge::getWeight)));
            for (Point d : points) {
                if (s.equals(d)) {
                    continue;
                }
                result.compute(s, (point, edges) -> {
                    edges.add(new Edge(s, d));
                    return edges;
                });
            }
        }

        return result;
//        for (int i = 0; i < points.size(); i++) {
//            for (int j = i + 1; j < points.size(); j++) {
//
//            }
//        }
    }
}

class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" +
                x +
                "; " + y +
                ')';
    }
}

class Edge {
    private Point s;
    private Point d;
    private double weight;

    public Edge(Point s, Point d) {
        this.s = s;
        this.d = d;
        this.weight = Math.sqrt(
                Math.pow(s.getX() - d.getX(), 2)
                        + Math.pow(s.getY() - d.getY(), 2));
    }

    public Point getS() {
        return s;
    }

    public Point getD() {
        return d;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.weight, weight) == 0 &&
                Objects.equals(s, edge.s) &&
                Objects.equals(d, edge.d);
    }

    @Override
    public int hashCode() {
        return Objects.hash(s, d, weight);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "s=" + s +
                ", d=" + d +
                ", weight=" + weight +
                '}';
    }
}
