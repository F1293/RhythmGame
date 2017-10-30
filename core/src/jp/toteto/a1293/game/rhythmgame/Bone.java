package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/21.
 */

public class Bone extends GameObject{
    // 横幅、高さ
    public static final float PLAYER_WIDTH = 0.7f;
    public static final float PLAYER_HEIGHT = 0.7f;
    float rotationTime;

    //状態を保持する
    //int mState;
    int threw = 0;

    GameScreen mGameScreen;

    public Bone(TextureRegion texture) {
        super(texture);
        setSize(PLAYER_WIDTH, PLAYER_HEIGHT);
        setAlpha(0);
        //mState = PLAYER_STATE_FALL;
    }

    public void update (float delta) {
        rotationTime += delta;
        if (threw == 1) {
            setAlpha(1);//見えるように
            setPosition(getX() + -6 * delta, getY());
            if (getX()< -1){
                threw = 0;
                setAlpha(0);
                setX(8.0f);
            }
        }
        setOrigin(0.27f,0.33f);
        setRotation(rotationTime * 720);
        if (rotationTime>0.5f){
            rotationTime = 0;
        }
    }
    //プレイヤーが触れた時に呼ばれるgetメソッド
    public void get() {
        threw = 0;
        setAlpha(0);
        setX(8.0f);
    }
}