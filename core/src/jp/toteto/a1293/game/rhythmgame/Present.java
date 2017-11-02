package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/11/02.
 */

public class Present extends GameObject {
    // 横幅、高さ
    public static final float PRESENT_WIDTH = 0.95f;
    public static final float PRESENT_HEIGHT = 0.85f;



    public Present(TextureRegion texture) {
        super(texture);
        setSize(PRESENT_WIDTH, PRESENT_HEIGHT);
    }

    //プレイヤーが触れた時に呼ばれるgetメソッド,状態をSTAR_NONEにし、setAlphaメソッドで透明に
    public void get() {
        setAlpha(0);
    }
}