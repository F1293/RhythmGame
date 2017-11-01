package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/31.
 */

public class Message extends GameObject {
    // 横幅、高さ
    public static final float M_WIDTH = 16;
    public static final float M_HEIGHT = 0.9f;

    public static final int M_EXIST = 0;
    public static final int GRASS_NONE = 1;

    //状態を保持するメンバ変数
    int mState;

    // 速度
    public static final float GRASS_VELOCITY = - 50.0f;



    public Message(TextureRegion texture) {
        super(texture);
        setSize(M_WIDTH, M_HEIGHT);
        mState = M_EXIST;
        velocity.x = GRASS_VELOCITY;
    }

    // 座標を更新する
    public void update(float deltaTime) {
        setX(getX() + velocity.x * deltaTime);
        if (getX()<= 0){
            setPosition(0, 4.0f);
        }
    }
}