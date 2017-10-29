package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/14.
 */

public class Player  extends GameObject implements ApplicationListener {
    // 横幅、高さ
    public static final float PLAYER_WIDTH = 1.0f;
    public static final float PLAYER_HEIGHT = 1.4f;

    // 状態（ジャンプ中、落ちている最中）
    public static final int PLAYER_STATE_JUMP = 0;
    public static final int PLAYER_STATE_FALL = 1;

    // 速度
    public static final float PLAYER_JUMP_VELOCITY = 10.0f;

    //状態を保持する
    int mState;
    int jumpstate;
    float stateTime = 0.01f;
    public float playerY;
    float posx;
    float posy;

    GameScreen mGameScreen;

    public Player(TextureRegion texture1) {
        super(texture1);
        //draw((Batch) texture1),posx, posy,PLAYER_WIDTH, PLAYER_HEIGHT);
        //super(texture1);
        setSize(PLAYER_WIDTH, PLAYER_HEIGHT);
        setPosition(1.4f, 5.2f);
        //setPosition(1.4f,playerY );
        //mState = PLAYER_STATE_FALL;

    }

    public void update (float deltaTime) {



        if (jumpstate == 1) {
            if (velocity.y == 0) {
                velocity.y = PLAYER_JUMP_VELOCITY;
                mState = PLAYER_STATE_JUMP;
            }
            // while (mState != PLAYER_STATE_FALLED) {
            // 重力をプレイヤーの速度に加算し、速度から位置を計算する
            velocity.add(0, -20 * deltaTime);
            setPosition(getX(), getY() + velocity.y * deltaTime);


            // y方向の速度が正（＝上方向）のときにSTATEがPLAYER_STATE_JUMPでなければPLAYER_STATE_JUMPにする
            if (velocity.y > 0) {
                if (mState != PLAYER_STATE_JUMP) {
                    mState = PLAYER_STATE_JUMP;
                }
            }

            // y方向の速度が負（＝下方向）のときにSTATEがPLAYER_STATE_FALLでなければPLAYER_STATE_FALLにする
            if (velocity.y < 0 && -20 < velocity.y) {
                if (mState != PLAYER_STATE_FALL) {
                    mState = PLAYER_STATE_FALL;
                }
                if (velocity.y <= -9.9f) {
                    velocity.y = PLAYER_JUMP_VELOCITY;
                    setPosition(1.4f, 5.2f);
                    jumpstate = 0;
                    //break;
                }
            }
        }
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}