package main;

import model.GameConfig;
import view.GameBoard;
import view.InfoPanel;

import javax.swing.*;
import java.awt.*;

public class Tetris extends JFrame {
    public Tetris() {
        setTitle("俄罗斯方块");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // 难度选择
        GameConfig.Difficulty difficulty = showDifficultyDialog();
        if (difficulty == null) System.exit(0);

        InfoPanel infoPanel = new InfoPanel();
        GameBoard gameBoard = new GameBoard(difficulty, infoPanel);

        setLayout(new BorderLayout());
        add(gameBoard, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private GameConfig.Difficulty showDifficultyDialog() {
        Object[] options = {"简单", "普通", "困难"};
        int choice = JOptionPane.showOptionDialog(this,
                "请选择难度", "俄罗斯方块",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[1]);
        switch (choice) {
            case 0: return GameConfig.Difficulty.EASY;
            case 1: return GameConfig.Difficulty.NORMAL;
            case 2: return GameConfig.Difficulty.HARD;
            default: return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Tetris());
    }
}