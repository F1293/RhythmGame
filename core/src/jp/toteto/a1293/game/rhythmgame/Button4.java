package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Fumio on 2017/10/06.
 */

public class Button4  extends GameObject {
    // 横幅、高さ
    public static final float BUTTON4_WIDTH = 2.0f;
    public static final float BUTTON4_HEIGHT = 2.25f;

    public Button4(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(BUTTON4_WIDTH, BUTTON4_HEIGHT);
    }
}