package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/17.
 */

public class GaugeBarBack extends GameObject {
    // 横幅、高さ
    public static final float GBB_WIDTH = 4.0f;
    public static final float GBB_HEIGHT = 4.52f;

    public GaugeBarBack(TextureRegion texture) {
        super(texture);
        setSize(GBB_WIDTH, GBB_HEIGHT);
    }
}