package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/17.
 */

public class RFrame extends GameObject {
    // 横幅、高さ
    public static final float RF_WIDTH = 16;
    public static final float RF_HEIGHT = 4.5f;
    float Alpha = 0;
    int n = 1;

    public RFrame(TextureRegion texture) {
        super(texture);
        setSize(RF_WIDTH, RF_HEIGHT);
    }

    public void update(float deltaTime) {

        setAlpha(Alpha);
        Alpha += n * deltaTime;
        if (Alpha > 0.9f) {
            n = -1;
        }
        if (Alpha < 0.1f) {
            n = 1;
        }

    }
}