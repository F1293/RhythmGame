package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/31.
 */

public class Message2  extends GameObject {
    // 横幅、高さ
    public static final float M_WIDTH = 2.6f;
    public static final float M_HEIGHT = 0.5f;
    float color;
    public Message2(TextureRegion texture) {
        super(texture);
        setSize(M_WIDTH, M_HEIGHT);
        setAlpha(0);
    }

    public void update(float screenTime) {
        if (screenTime < 1) {
            color =  0;
        }
            if (1 <= screenTime && screenTime <= 1.5f) {
                color = 2 * screenTime - 2;
            }
            if (screenTime > 1.5f) {
                color = -2 * screenTime + 4;
            }
        setAlpha(color);
    }
    public void hide() {
        setAlpha(0);
    }
}