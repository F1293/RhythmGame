package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class DeadLine extends GameObject {
    // 横幅、高さ
    public static final float BAR_WIDTH = 0.1f;
    public static final float BAR_HEIGHT = 4.5f;

    public DeadLine(TextureRegion texture) {
        super(texture);
        setSize(BAR_WIDTH, BAR_HEIGHT);
    }
}