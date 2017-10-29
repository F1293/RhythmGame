package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/17.
 */

public class FGaugeBar extends GameObject {
    // 横幅、高さ
    public static final float LGB_WIDTH = 0.2f;
    public static final float LGB_HEIGHT = 0;

    public FGaugeBar(TextureRegion texture) {
        super(texture);
        setSize(LGB_WIDTH, LGB_HEIGHT);
    }

    public void  GetDamage (){
        if (GameScreen.FearGauge > 0) {
            setY(5 + GameScreen.FearGauge * 0.035f);
            setSize(LGB_WIDTH, (100 - GameScreen.FearGauge) * 0.035f);
        }else{
            setY(5);
            setSize(LGB_WIDTH, 3.5f);
        }
    }
}