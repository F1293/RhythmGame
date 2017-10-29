package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/06.
 */

public class Note2 extends GameObject {
    // 横幅、高さ
    public static final float NOTE_WIDTH = 0.7f;
    public static final float NOTE_HEIGHT = 0.7f;

    // 状態、存在する場合と獲得されて亡くなった場合
    public static final int NOTE_EXIST = 0;
    public static final int NOTE_NONE = 1;

    //状態を保持するメンバ変数
    int mState;


    // 速度
    public static final float NOTE_VELOCITY = - 7.0f;



    public Note2(TextureRegion texture) {
        super(texture);
        setSize(NOTE_WIDTH, NOTE_HEIGHT);
        velocity.x = NOTE_VELOCITY;
    }

    // 座標を更新する
    public void update(float deltaTime) {
        setX(getX() + velocity.x * deltaTime);

    }
    //プレイヤーが触れた時に呼ばれるgetメソッド,状態をSTAR_NONEにし、setAlphaメソッドで透明に
    public void get() {
        mState = NOTE_NONE;
        setAlpha(0);
    }
}
