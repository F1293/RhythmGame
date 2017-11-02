package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/11/02.
 */

public class Home  extends GameObject {
    // 横幅、高さ
    public static final float HOME_WIDTH = 5;
    public static final float HOME_HEIGHT = 5;
    float Darker;//明るさ切り替え
    float color;
    float screenTime;

    public Home(TextureRegion texture) {
        super(texture);
        setSize(HOME_WIDTH, HOME_HEIGHT);
    }
}