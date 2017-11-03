package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Manual extends GameObject {
    // 横幅、高さ
    public static final float MANUAL_WIDTH = 500.0f;
    public static final float MANUAL_HEIGHT = 275.0f;

    public Manual(TextureRegion texture) {
        super(texture);
        setSize(MANUAL_WIDTH, MANUAL_HEIGHT);
    }
}