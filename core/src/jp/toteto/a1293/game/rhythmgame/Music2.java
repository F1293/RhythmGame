package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/11/02.
 */

public class Music2 extends GameObject {
    // 横幅、高さ
    public static final float BUTTON1_WIDTH = 200;
    public static final float BUTTON1_HEIGHT = 50;
    float Darker;//明るさ切り替え
    float color;
    float screenTime;

    public Music2(TextureRegion texture) {
        super(texture);
        setSize(BUTTON1_WIDTH, BUTTON1_HEIGHT);
    }

    public void Darker(float delta) {
        setColor(color,color,color,1);
        screenTime += delta;
        Darker = color;
        if (0<=screenTime && screenTime<=1){
            color = -0.5f * screenTime + 1;
        }
        if (1<=screenTime && screenTime<2){
            color = 0.5f * screenTime;
        }
        if (screenTime>2){
            screenTime = 0;
        }
    }

    public void Push(){
        setAlpha(1);
        setColor(0.3f,0.3f,0.3f,1);
    }
}