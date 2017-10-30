package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/06.
 */

public class Button1 extends GameObject {
    // 横幅、高さ
    public static final float BUTTON1_WIDTH = 2.0f;
    public static final float BUTTON1_HEIGHT = 2.25f;
    float Darker;//明るさ切り替え
    float color;
    public Button1(TextureRegion texture) {
        super(texture);
        setSize(BUTTON1_WIDTH, BUTTON1_HEIGHT);
    }

    public void Darker(float screenTime,boolean tb1) {
        setColor(color, color, color, 1);
        Darker = screenTime * 0.5f;
        if (!tb1) {
            if (screenTime < 0.5f) {
                color = 1 - Darker;
            }
            if (screenTime > 0.5f) {
                color = 0.5f + Darker;
            }
        }else{
            setColor(0.5f, 0.5f, 0.5f, 1);
        }
    }
}