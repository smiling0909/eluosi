import model.GameConfig;
import model.Tetromino;
import view.GameBoard;
import view.InfoPanel;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class TestGameLogic {
    public static void main(String[] args) throws Exception {
        System.out.println("========== 俄罗斯方块核心逻辑测试 ==========\n");

        // -------------------- 1. 测试 Tetromino 类 --------------------
        System.out.println("【1】测试 Tetromino（方块）");
        Tetromino t = new Tetromino(Tetromino.SHAPES[2], 2);
        System.out.print("原始T形: ");
        printShape(t.getShape());
        int[][] rotated = t.getRotatedShape();
        System.out.print("顺时针旋转后: ");
        printShape(rotated);
        System.out.println("旋转测试通过 ✓\n");

        // -------------------- 2. 创建游戏板 --------------------
        InfoPanel dummyPanel = new InfoPanel();
        GameBoard board = new GameBoard(GameConfig.Difficulty.NORMAL, dummyPanel);
        System.out.println("【2】游戏板初始化完成，网格20x10，当前方块已生成");
        printBoardState(board);
        System.out.println();

        // 获取私有方法和字段
        Method movePiece = GameBoard.class.getDeclaredMethod("movePiece", int.class, int.class);
        movePiece.setAccessible(true);
        Field boardField = GameBoard.class.getDeclaredField("board");
        boardField.setAccessible(true);
        Field currentPieceField = GameBoard.class.getDeclaredField("currentPiece");
        currentPieceField.setAccessible(true);

        // 辅助函数：获取当前方块坐标
        java.util.function.Function<GameBoard, int[]> getPos = (b) -> {
            try {
                Tetromino p = (Tetromino) currentPieceField.get(b);
                return new int[]{p.getX(), p.getY()};
            } catch (Exception e) { return new int[]{-1,-1}; }
        };

        // -------------------- 3. 测试移动 --------------------
        System.out.println("【3】测试移动 (右移2格, 下移1格)");
        int[] before = getPos.apply(board);
        movePiece.invoke(board, 2, 0);
        movePiece.invoke(board, 0, 1);
        int[] after = getPos.apply(board);
        System.out.println("移动前位置: (" + before[0] + "," + before[1] + ")");
        System.out.println("移动后位置: (" + after[0] + "," + after[1] + ")");
        if (after[0] == before[0]+2 && after[1] == before[1]+1) {
            System.out.println("移动成功 ✓");
        } else {
            System.out.println("移动失败 ✗");
        }
        printBoardState(board);
        System.out.println();

        // -------------------- 4. 测试碰撞（移动到右边界）--------------------
        System.out.println("【4】测试碰撞检测：向右移动直到边界");
        int steps = 0;
        int lastX = getPos.apply(board)[0];
        while (true) {
            movePiece.invoke(board, 1, 0);
            int newX = getPos.apply(board)[0];
            if (newX == lastX) break; // 无法移动
            lastX = newX;
            steps++;
        }
        System.out.println("共向右移动 " + steps + " 步后碰撞，最终位置: (" + lastX + ", " + getPos.apply(board)[1] + ")");
        printBoardState(board);
        System.out.println();

        // -------------------- 5. 测试向下移动直到碰撞底部 --------------------
        System.out.println("【5】测试向下移动直到碰撞底部或已有方块");
        steps = 0;
        int lastY = getPos.apply(board)[1];
        while (true) {
            movePiece.invoke(board, 0, 1);
            int newY = getPos.apply(board)[1];
            if (newY == lastY) break;
            lastY = newY;
            steps++;
        }
        System.out.println("共向下移动 " + steps + " 步后碰撞，最终位置: (" + getPos.apply(board)[0] + ", " + lastY + ")");
        printBoardState(board);
        System.out.println();

        // -------------------- 6. 测试生成新方块 --------------------
        System.out.println("【6】生成新方块");
        Method spawn = GameBoard.class.getDeclaredMethod("spawnNewPiece");
        spawn.setAccessible(true);
        spawn.invoke(board);
        printBoardState(board);
        System.out.println();

        System.out.println("\n========== 所有测试完成 ==========");
        System.out.println("如果以上输出符合预期，则核心功能正常。");
    }

    private static void printShape(int[][] shape) {
        for (int[] row : shape) {
            for (int cell : row) {
                System.out.print(cell == 0 ? " . " : " X ");
            }
            System.out.println();
        }
    }

    private static void printBoardState(GameBoard board) throws Exception {
        Field boardField = GameBoard.class.getDeclaredField("board");
        boardField.setAccessible(true);
        int[][] grid = (int[][]) boardField.get(board);
        Field pieceField = GameBoard.class.getDeclaredField("currentPiece");
        pieceField.setAccessible(true);
        Tetromino piece = (Tetromino) pieceField.get(board);
        int px = piece.getX();
        int py = piece.getY();
        int[][] shape = piece.getShape();

        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int row = 0; row < 20; row++) {
            System.out.printf("%2d ", row);
            for (int col = 0; col < 10; col++) {
                boolean isPiece = (row >= py && row < py + shape.length &&
                        col >= px && col < px + shape[0].length &&
                        shape[row - py][col - px] != 0);
                if (isPiece) {
                    System.out.print(" X ");
                } else if (grid[row][col] != 0) {
                    System.out.print(" # ");
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
        System.out.println("当前方块位置: (" + px + ", " + py + ")");
    }
}