package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/15.
 */

public class Star extends GameObject{
    // 横幅、高さ
    public static final float PLAYER_WIDTH = 2.0f;
    public static final float PLAYER_HEIGHT = 2.0f;

    // 状態（ジャンプ中、落ちている最中）
    public static final int PLAYER_STATE_JUMP = 0;
    public static final int PLAYER_STATE_FALL = 1;

    //状態を保持する
    //int mState;
    int jumpstate;

    GameScreen mGameScreen;

    public Star(TextureRegion texture) {
        super(texture);
        setSize(PLAYER_WIDTH, PLAYER_HEIGHT);
        //mState = PLAYER_STATE_FALL;
    }

    public void update (float delta) {
        if (jumpstate == 1) {
            setPosition(getX() + 3 * delta, getY() + -9 * delta);
            if (getY()< 4.5f){
                setPosition(6.8f, 10);
                jumpstate = 0;
            }
        }
    }
}