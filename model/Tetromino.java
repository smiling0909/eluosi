package model;

import java.awt.*;

public class Tetromino {
    // 七种形状（保持原样，但改为 4x4）
    public static final int[][][] SHAPES = {
            // I
            {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}},
            // O
            {{0,0,0,0},{0,1,1,0},{0,1,1,0},{0,0,0,0}},
            // T
            {{0,0,0,0},{0,1,0,0},{1,1,1,0},{0,0,0,0}},
            // S
            {{0,0,0,0},{0,1,1,0},{1,1,0,0},{0,0,0,0}},
            // Z
            {{0,0,0,0},{1,1,0,0},{0,1,1,0},{0,0,0,0}},
            // J
            {{0,0,0,0},{1,0,0,0},{1,1,1,0},{0,0,0,0}},
            // L
            {{0,0,0,0},{0,0,1,0},{1,1,1,0},{0,0,0,0}}
    };

    // 对应七种颜色
    public static final Color[] COLORS = {
            Color.CYAN,      // I
            Color.YELLOW,    // O
            Color.MAGENTA,   // T
            Color.GREEN,     // S
            Color.RED,       // Z
            Color.BLUE,      // J
            Color.ORANGE     // L
    };

    private int[][] shape;
    private int colorIndex;
    private int x, y;

    public Tetromino(int[][] shape, int colorIndex) {
        this.shape = new int[shape.length][shape[0].length];
        for (int i = 0; i < shape.length; i++) {
            System.arraycopy(shape[i], 0, this.shape[i], 0, shape[i].length);
        }
        this.colorIndex = colorIndex;
        this.x = 10 / 2 - shape[0].length / 2;
        this.y = 0;
    }

    public int[][] getShape() { return shape; }
    public int getColorIndex() { return colorIndex; }
    public Color getColor() { return COLORS[colorIndex]; }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public Tetromino copy() {
        return new Tetromino(shape, colorIndex);
    }

    public int[][] getRotatedShape() {
        int rows = shape.length, cols = shape[0].length;
        int[][] rotated = new int[cols][rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                rotated[j][rows - 1 - i] = shape[i][j];
        return rotated;
    }
}