package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.Texture;

/**
 * 右側判定バー
 */

public class iBar2 extends GameObject {
    // 横幅、高さ
    public static final float BAR_WIDTH = 0.3f;
    public static final float BAR_HEIGHT = 4.5f;

    public iBar2(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(BAR_WIDTH, BAR_HEIGHT);
    }
}