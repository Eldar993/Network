package com.company;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int computers = scanner.nextInt();
    double distance = 0;
    boolean[][] points = new boolean[][];
    boolean point = true;
    for(int  i = 1;i <= computers;i++){
        for (int  j = 1;j <=computers) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points[x][y] = point;
        }
    }

    // BFS...


        System.out.println(distance);
    }
}
