package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/16.
 */

public class Pumpkin extends GameObject {
    // 横幅、高さ
    public static final float PUMPKIN_WIDTH = 0.8f;
    public static final float PUMPKIN_HEIGHT = 0.63f;

    // 状態、存在する場合と獲得されて亡くなった場合
    public static final int PUMPKIN_EXIST = 0;
    public static final int PUMPKIN_NONE = 1;

    //状態を保持するメンバ変数
    int mState;

    // 速度
    //public static final float ENEMY_VELOCITY = - 3.5f;
    public static final float PUMPKIN_VELOCITY = - 3.5f;

    public Pumpkin(TextureRegion texture) {
        super(texture);
        setSize(PUMPKIN_WIDTH, PUMPKIN_HEIGHT);
        mState = PUMPKIN_EXIST;
        velocity.x = PUMPKIN_VELOCITY;
    }

    // 座標を更新する
    public void update(float deltaTime,float screenTime) {
        if (0.25f > screenTime && screenTime >0){
            setRegion (0, 34, 32, 27);
        }
        if (0.5f > screenTime && screenTime >0.25f){
            setRegion (32, 34, 32, 27);
        }
        if (0.75f > screenTime && screenTime >0.5f){
            setRegion (0, 34, 32, 27);
        }
        if (screenTime >0.75f){
            setRegion (32, 34, 32, 27);
        }
        setX(getX() + velocity.x * deltaTime);
    }
    //プレイヤーが触れた時に呼ばれるgetメソッド,状態をSTAR_NONEにし、setAlphaメソッドで透明に
    public void get() {
        mState = PUMPKIN_NONE;
        setAlpha(0);
    }
}