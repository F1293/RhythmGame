package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/11/03.
 */

public class BackButton  extends GameObject {
    // 横幅、高さ
    public static final float BACKBUTTON_WIDTH = 1.325f;
    public static final float BACKBUTTON_HEIGHT = 1.0f;

    public BackButton(TextureRegion texture) {
        super(texture);
        setSize(BACKBUTTON_WIDTH, BACKBUTTON_HEIGHT);
        setPosition(14,7);
    }
    public void Push(){
        setColor(0.5f,0.5f,0.5f,1);
    }
}