package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Fumio on 2017/10/16.
 */

public class Ghost extends GameObject {
    // 横幅、高さ
    public static final float GHOST_WIDTH = 0.7f;
    public static final float GHOST_HEIGHT = 0.93f;

    // 状態、存在する無くなった場合
    public static final int GHOST_EXIST = 0;
    public static final int GHOST_NONE = 1;

    //状態を保持するメンバ変数
    int mState;
    boolean hinder = true;

    // 速度
    //public static final float ENEMY_VELOCITY = - 3.5f;
    public static final float GHOST_VELOCITY = - 1.7f;



    public Ghost(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        setSize(GHOST_WIDTH, GHOST_HEIGHT);
        mState = GHOST_EXIST;
        velocity.x = GHOST_VELOCITY;
    }

    // 中央付近で上昇し画面下部に現れる
    public void update(float deltaTime) {
        setX(getX() + velocity.x * deltaTime);
        setY(getY() + velocity.y * deltaTime);
        if (getX() < 7.5f && getX() > 5) {
            //ここで上昇
            velocity.x = 0;
            velocity.y = 3;
        }
        if (getY() > 10) {
            velocity.y = 5f;
            setX(3.2f);
            setY(-9);
            setSize(7,9.3f);
        }
        if (getY() > -4.7f && getY() < 1){
            velocity.y = -2;
        }
        if (getY() < -10){
            mState = GHOST_NONE;
            setAlpha(0);

        }

    }
    //プレイヤーが触れた時に呼ばれるgetメソッド,状態をSTAR_NONEにし、setAlphaメソッドで透明に
    public void get() {
        mState = GHOST_NONE;
        setAlpha(0);
        hinder = true;
    }
}
