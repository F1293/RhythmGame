package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.Texture;


public class DeadLine extends GameObject {
    // 横幅、高さ
    public static final float BAR_WIDTH = 0.1f;
    public static final float BAR_HEIGHT = 4.5f;

    public DeadLine(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(BAR_WIDTH, BAR_HEIGHT);
    }
}