package util;

import java.util.prefs.Preferences;

public class ScoreRecorder {
    private static final String KEY_HIGH_SCORE = "tetris_high_score";
    private static Preferences prefs = Preferences.userNodeForPackage(ScoreRecorder.class);

    public static int getHighScore() {
        return prefs.getInt(KEY_HIGH_SCORE, 0);
    }

    public static void setHighScore(int score) {
        int current = getHighScore();
        if (score > current) {
            prefs.putInt(KEY_HIGH_SCORE, score);
        }
    }
}
