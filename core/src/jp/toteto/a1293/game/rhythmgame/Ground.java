package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Fumio on 2017/10/23.
 */

public class Ground   extends GameObject {
    // 横幅、高さ
    public static final float GROUND_WIDTH = 8.1f;
    public static final float GROUND_HEIGHT = 1.0f;

    public static final int GROUND_EXIST = 0;
    public static final int GROUND_NONE = 1;

    //状態を保持するメンバ変数
    int mState;

    // 速度
    public static final float TREE_VELOCITY = - 1.8f;



    public Ground(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(GROUND_WIDTH, GROUND_HEIGHT);
        mState = GROUND_EXIST;
        velocity.x = TREE_VELOCITY;
    }

    // 座標を更新する
    public void update(float deltaTime) {
        setX(getX() + velocity.x * deltaTime);
            if (getX()<= -8.0){
                setPosition(16, 4.5f);
            }

    }
}