package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/06.
 */

public class Button3 extends GameObject {
    // 横幅、高さ
    public static final float BUTTON3_WIDTH = 2.0f;
    public static final float BUTTON3_HEIGHT = 2.25f;

    public Button3(TextureRegion texture) {
        super(texture);
        setSize(BUTTON3_WIDTH, BUTTON3_HEIGHT);
    }
}