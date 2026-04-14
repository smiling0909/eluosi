package view;

import model.Tetromino;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private static final int PREVIEW_SIZE = 20;
    private Tetromino nextPiece;
    private int score;
    private int level;
    private int highScore;
    private boolean isRunning;

    public InfoPanel() {
        setPreferredSize(new Dimension(180, 600));
        // 设置纯色背景（例如深蓝色）
        setBackground(new Color(220, 255, 220));
        setOpaque(true);          // 确保背景不透明
        setFocusable(false);
    }

    public void updateInfo(Tetromino next, int score, int level, int highScore, boolean running) {
        this.nextPiece = next;
        this.score = score;
        this.level = level;
        this.highScore = highScore;
        this.isRunning = running;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);   // 会先填充背景色（因为 setOpaque(true)）

        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑", Font.BOLD, 14));
        g.drawString("下一个:", 20, 30);

        if (nextPiece != null) {
            int[][] shape = nextPiece.getShape();
            int offsetX = 20;
            int offsetY = 50;
            g.setColor(nextPiece.getColor());
            for (int r = 0; r < shape.length; r++) {
                for (int c = 0; c < shape[r].length; c++) {
                    if (shape[r][c] != 0) {
                        g.fillRect(offsetX + c * PREVIEW_SIZE, offsetY + r * PREVIEW_SIZE,
                                PREVIEW_SIZE - 1, PREVIEW_SIZE - 1);
                    }
                }
            }
        }

        g.drawString("分数: " + score, 20, 150);
        g.drawString("等级: " + level, 20, 180);
        g.drawString("最高分: " + highScore, 20, 210);

        if (!isRunning) {
            g.setColor(Color.RED);
            g.drawString("游戏结束", 20, 260);
            g.drawString("按空格开始", 20, 290);
        } else {
            g.setColor(Color.WHITE);
            g.drawString("游戏中...", 20, 260);
            g.drawString("P键暂停", 20, 290);
        }
    }
}