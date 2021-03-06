package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/13.
 */

public class JumpLine extends GameObject {
    // 横幅、高さ
    public static final float JUMPLINE_WIDTH = 0.7f;
    public static final float JUMPLINE_HEIGHT = 0.7f;

    public JumpLine(TextureRegion texture) {
        super(texture);
        setSize(JUMPLINE_WIDTH, JUMPLINE_HEIGHT);
        setAlpha(0);
    }

    public void unpush() {
        setAlpha(0);
    }
    public void push() {
        setAlpha(1);
    }

    // 座標を更新する
    public void update(float deltaTime) {
            setY(getY() + 3.6f * deltaTime);

            if (getY() > GameScreen.CAMERA_HEIGHT - JUMPLINE_HEIGHT - 0.5f ) {
                setY(5.3f);
            }
    }

}