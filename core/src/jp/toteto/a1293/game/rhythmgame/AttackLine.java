package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Fumio on 2017/10/13.
 */

public class AttackLine extends GameObject {
    // 横幅、高さ
    public static final float ATTACKLINE_WIDTH = 2.0f;
    public static final float ATTACKLINE_HEIGHT = 2.0f;


    public AttackLine(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(ATTACKLINE_WIDTH, ATTACKLINE_HEIGHT);
        setAlpha(0);
    }

    public void unpush() {
        setAlpha(0);
    }
    public void push() {
        setAlpha(1);
    }

}