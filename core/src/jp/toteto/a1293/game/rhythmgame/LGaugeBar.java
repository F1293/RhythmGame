package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/17.
 */

public class LGaugeBar extends GameObject {
    // 横幅、高さ
    public static final float LGB_WIDTH = 0.2f;
    public static final float LGB_HEIGHT = 3.5f;

    public LGaugeBar(TextureRegion texture) {
        super(texture);
        setSize(LGB_WIDTH, LGB_HEIGHT);
        setColor(1,1,1,1);
    }
    public void  GetDamage (){
        if (GameScreen.LifeGauge > 0) {
            setSize(LGB_WIDTH, GameScreen.LifeGauge * 0.035f);
        }else{
            setSize(LGB_WIDTH, 0);
        }
    }
}