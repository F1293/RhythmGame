package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Fumio on 2017/10/12.
 */

public class ActionBack extends GameObject {
    // 横幅、高さ
    public static final float AB_WIDTH = 16;
    public static final float AB_HEIGHT = 4.5f;

    public ActionBack(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(AB_WIDTH, AB_HEIGHT);
    }

    public void Darker() {
        if (GameScreen.FearGauge > 15) {
            float color;
            color = GameScreen.FearGauge * 0.01f;
            setColor(color, color, color, 1);
        }
    }
}