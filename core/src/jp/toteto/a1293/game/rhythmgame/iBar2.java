package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 右側判定バー
 */

public class iBar2 extends GameObject {
    // 横幅、高さ
    public static final float BAR_WIDTH = 0.07f;
    public static final float BAR_HEIGHT = 4.5f;

    public iBar2(TextureRegion texture) {
        super(texture);
        setSize(BAR_WIDTH, BAR_HEIGHT);
    }
}