package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Fumio on 2017/10/16.
 */

public class Skeleton extends GameObject {
    // 横幅、高さ
    public static final float SKELETON_WIDTH = 0.7f;
    public static final float SKELETON_HEIGHT = 0.93f;

    // 状態、存在する場合と獲得されて亡くなった場合
    public static final int SKELETON_EXIST = 0;
    public static final int SKELETON_NONE = 1;

    //状態を保持するメンバ変数
    int mState;
Star mStar;
    // 速度
    //public static final float ENEMY_VELOCITY = - 3.5f;
    public static final float SKELETON_VELOCITY = - 2;



    public Skeleton(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(SKELETON_WIDTH, SKELETON_HEIGHT);
        mState = SKELETON_EXIST;
        velocity.x = SKELETON_VELOCITY;
    }

    // 中央付近で折り返し、行ったり来たり
    public void update(float deltaTime) {
        if (mState == 0) {
            setX(getX() + velocity.x * deltaTime);
            if (getX() < 7.5f) {
                //ここで攻撃出す
                velocity.x = 1;
            }
            if (getX() > 12 && velocity.x > 0) {
                velocity.x = -1;
            }
        }
    }
    //プレイヤーが触れた時に呼ばれるgetメソッド,状態をSTAR_NONEにし、setAlphaメソッドで透明に
    public void get() {
        mState = SKELETON_NONE;
        setAlpha(0);
        setX(16);
    }
}
