package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/13.
 */

public class AttackLine extends GameObject {
    // 横幅、高さ
    public static final float ATTACKLINE_WIDTH = 2.0f;
    public static final float ATTACKLINE_HEIGHT = 2.0f;
    float rotationTime;


    public AttackLine(TextureRegion texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture);
        setSize(ATTACKLINE_WIDTH, ATTACKLINE_HEIGHT);
        setAlpha(0);
    }

    public void update(float deltaTime) {
        rotationTime += deltaTime;
        setPosition(8.2f, 5.0f);
        setOrigin(1,1);
        setRotation(rotationTime * -30);
        if (rotationTime>12){
            rotationTime = 0;
        }
    }

    public void unpush() {
        setAlpha(0);
    }
    public void push() {
        setAlpha(1);
    }

}