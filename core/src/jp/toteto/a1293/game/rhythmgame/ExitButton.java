package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/31.
 */

public class ExitButton extends GameObject {
    // 横幅、高さ
    public static final float BUTTON1_WIDTH = 83;
    public static final float BUTTON1_HEIGHT = 51;
    float Darker;//明るさ切り替え
    float color;

    public ExitButton(TextureRegion texture) {
        super(texture);
        setSize(BUTTON1_WIDTH, BUTTON1_HEIGHT);
    }

    public void Darker(float screenTime,boolean tb) {
        setColor(color, color, color, 1);
        Darker = screenTime * 0.5f;
        if (tb){
            setColor(0.5f, 0.5f, 0.5f, 1);
        }
    }
}