package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 左側判定バー
 */

public class iBar1 extends GameObject {
    // 横幅、高さ
    public static final float BAR_WIDTH = 0.01f;
    public static final float BAR_HEIGHT = 4.5f;

    public iBar1(TextureRegion texture) {
        super(texture);
        setSize(BAR_WIDTH, BAR_HEIGHT);
    }
}