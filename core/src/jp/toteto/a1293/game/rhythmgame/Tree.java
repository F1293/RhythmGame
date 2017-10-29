package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/23.
 */

public class Tree  extends GameObject {
    // 横幅、高さ
    public static final float TREE_WIDTH = 4.0f;
    public static final float TREE_HEIGHT = 4.0f;

    public static final int TREE_EXIST = 0;
    public static final int TREE_NONE = 1;

    //状態を保持するメンバ変数
    int mState;

    // 速度
    public static final float TREE_VELOCITY = - 1.2f;



    public Tree(TextureRegion texture) {
        super(texture);
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