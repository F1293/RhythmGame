package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/06.
 */

public class Button4  extends GameObject {
    // 横幅、高さ
    public static final float BUTTON4_WIDTH = 2.0f;
    public static final float BUTTON4_HEIGHT = 2.25f;

    public Button4(TextureRegion texture) {
        super(texture);
        setSize(BUTTON4_WIDTH, BUTTON4_HEIGHT);
        setPosition(0,0.1f);
        setAlpha(0.25f);
    }
    public void Push(){
        setPosition(0,0);
        setAlpha(0.6f);
        setRegion(384, 512, 64, 64);
    }
}