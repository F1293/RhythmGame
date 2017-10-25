package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Fumio on 2017/10/24.
 */

public class ButtonBack extends GameObject {
    // 横幅、高さ
    public static final float BB_WIDTH = 2.0f;
    public static final float BB_HEIGHT = 4.5f;

    public ButtonBack(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(BB_WIDTH, BB_HEIGHT);
    }
}