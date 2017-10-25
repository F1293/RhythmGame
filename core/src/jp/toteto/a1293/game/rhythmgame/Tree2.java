package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Fumio on 2017/10/24.
 */

public class Tree2 extends GameObject {
    // 横幅、高さ
    public static final float TREE_WIDTH = 6.0f;
    public static final float TREE_HEIGHT = 6.0f;

    public static final int TREE_EXIST = 0;
    public static final int TREE_NONE = 1;

    //状態を保持するメンバ変数
    int mState;

    // 速度
    public static final float TREE_VELOCITY = - 0.6f;



    public Tree2(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(TREE_WIDTH, TREE_HEIGHT);
        mState = TREE_EXIST;
        velocity.x = TREE_VELOCITY;
    }

    // 座標を更新する
    public void update(float deltaTime) {
        setX(getX() + velocity.x * deltaTime);
        if (getX()<= -8.0){
            setPosition(16, 5.3f);
        }
    }
}