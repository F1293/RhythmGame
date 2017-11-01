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
    float screenTime;

    public ExitButton(TextureRegion texture) {
        super(texture);
        setSize(BUTTON1_WIDTH, BUTTON1_HEIGHT);
    }

    public void Darker(float delta) {
        setAlpha(color);
        screenTime += delta;
        Darker = color;
        if (screenTime<8){
            color = 1;
        }
        if (8<=screenTime && screenTime<=9){
            color = -0.5f * screenTime + 4;
        }
        if (9<=screenTime && screenTime<10){
            color = 0.5f * screenTime -4;
        }
        if (screenTime>10){
            screenTime = 8;
        }
    }

    public void Push(){
        setAlpha(1);
        setColor(0.5f,0.5f,0.5f,1);
    }
}