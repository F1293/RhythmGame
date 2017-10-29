package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/23.
 */

public class Grass  extends GameObject {
    // 横幅、高さ
    public static final float GRASS_WIDTH = 6.1f;
    public static final float GRASS_HEIGHT = 0.7f;

    public static final int GRASS_EXIST = 0;
    public static final int GRASS_NONE = 1;

    //状態を保持するメンバ変数
    int mState;

    // 速度
    public static final float GRASS_VELOCITY = - 4.0f;



    public Grass(TextureRegion texture) {
        super(texture);
        setSize(GRASS_WIDTH, GRASS_HEIGHT);
        mState = GRASS_EXIST;
        velocity.x = GRASS_VELOCITY;
    }

    // 座標を更新する
    public void update(float deltaTime) {
        setX(getX() + velocity.x * deltaTime);
        if (getX()<= -8.0){
            setPosition(16, 4.5f);
        }
    }
}