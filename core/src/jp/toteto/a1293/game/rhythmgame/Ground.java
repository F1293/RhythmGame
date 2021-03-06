package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/23.
 */

public class Ground   extends GameObject {
    // 横幅、高さ
    public static final float GROUND_WIDTH = 16.0f;
    public static final float GROUND_HEIGHT = 1.0f;

    public static final int GROUND_EXIST = 0;
    public static final int GROUND_NONE = 1;

    //状態を保持するメンバ変数
    int mState;

    // 速度
    public static final float TREE_VELOCITY = - 2.0f;



    public Ground(TextureRegion texture) {
        super(texture);
        setSize(GROUND_WIDTH, GROUND_HEIGHT);
        mState = GROUND_EXIST;
        velocity.x = TREE_VELOCITY;
    }

    // 座標を更新する
    public void update(float deltaTime) {
        setX(getX() + velocity.x * deltaTime);
    }
}