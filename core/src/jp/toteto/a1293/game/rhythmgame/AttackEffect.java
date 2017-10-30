package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/30.
 */

public class AttackEffect extends GameObject{
    // 横幅、高さ
    public static final float ATTACKLINE_WIDTH = 2.0f;
    public static final float ATTACKLINE_HEIGHT = 2.0f;
    float rotationTime;

    //状態を保持する
    //int mState;
    int threw = 0;

    GameScreen mGameScreen;

    public AttackEffect(TextureRegion texture, int i, int i1, int i2, int i3) {
        super(texture);
        setSize(ATTACKLINE_WIDTH, ATTACKLINE_HEIGHT);
        setPosition(0.8f, 4.7f);
        setOrigin(1,1);
        setAlpha(0);
    }

    public void update (float delta) {

        if (threw == 1) {
            rotationTime += delta;
            setAlpha(1);//見えるように
            //setSize(2 - 4 * rotationTime,2 - 4 * rotationTime);
            setScale(1.5f - 3 * rotationTime);
            //setRotation(rotationTime * 720);
            if (rotationTime>0.5f){
                rotationTime = 0;
                threw = 0;
                setAlpha(0);
                setSize(ATTACKLINE_WIDTH, ATTACKLINE_HEIGHT);
            }
        }

    }
    //プレイヤーが触れた時に呼ばれるgetメソッド
    public void get() {
        threw = 0;
        setAlpha(0);
        setX(8.0f);
    }
}