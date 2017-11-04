package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
    public static final int GHOST_ROUND = 2;

    //状態を保持するメンバ変数
    int mState;
    boolean hinder = true;

    // 速度
    //public static final float ENEMY_VELOCITY = - 3.5f;
    public static float GHOST_VELOCITY = - 1.7f;



    public Ghost(TextureRegion texture) {
        super(texture);
        setSize(GHOST_WIDTH, GHOST_HEIGHT);
        mState = GHOST_EXIST;
        velocity.x = GHOST_VELOCITY;
    }

    // 中央付近で上昇し画面下部に現れる
    public void update(float deltaTime,float screenTime) {
        setX(getX() + velocity.x * deltaTime);
        setY(getY() + velocity.y * deltaTime);
        if (getX() > 7.5f) {
            velocity.x = -1.7f;
            if (0.5f > screenTime && screenTime > 0) {
                setRegion(0, 33, 24, 32);
            }
            if (screenTime > 0.5f) {
                setRegion(32, 33, 24, 32);
            }
        }

        if (getX() < 7.5f && getX() > 5) {
            //ここで上昇
            setRegion(0, 2, 24, 32);
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

    public void updateRS(float deltaTime,float v) {
        setSize(0.88f,1.2f);
        setX(getX() + velocity.x * deltaTime);
        float v2 = Math.abs( v );
        if (velocity.x<0){
            setRegion(0, 33, 24, 32);
        }
        if (velocity.x>0){
            setRegion(0, 64, 24, 32);
        }
        if (mState == 0) {
            velocity.x = v;
            if (getX() > 17||getX() < -2) {
                mState = 2;
            }
        }
        if (mState == 2) {
            if (getX() < -2) {
                velocity.x = v2;
            }
            if (getX() > 17) {
                velocity.x = -v2;
            }
        }
    }

    //プレイヤーが触れた時に呼ばれるgetメソッド,状態をSTAR_NONEにし、setAlphaメソッドで透明に
    public void get() {
        setRegion(0, 2, 24, 32);
        mState = GHOST_NONE;
        setAlpha(0);
        hinder = true;
    }
}
