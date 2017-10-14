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
}