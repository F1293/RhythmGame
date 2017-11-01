package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Fumio on 2017/10/12.
 */

public class StartScreen extends ScreenAdapter {
    static final float CAMERA_WIDTH = 512;
    static final float CAMERA_HEIGHT = 288;

    private RhythmGame mGame;
    int stage;
    Sprite mBg;
    OrthographicCamera mCamera;
    FitViewport mViewPort;

    public StartScreen(RhythmGame game) {
        mGame = game;

        // 背景の準備
        Texture bgTexture = new Texture("startscreen.png");
        // TextureRegionで切り出す時の原点は左上
        mBg = new Sprite(new TextureRegion(bgTexture, 0, 0, 1022, 608));
        mBg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        mBg.setPosition(0, 0);

        // カメラ、ViewPortを生成、設定する
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // カメラの座標をアップデート（計算）し、スプライトの表示に反映させる
        mCamera.update();
        mGame.batch.setProjectionMatrix(mCamera.combined);

        mGame.batch.begin();

        // 原点は左下
        mBg.setPosition(mCamera.position.x - CAMERA_WIDTH / 2, mCamera.position.y - CAMERA_HEIGHT / 2);
        mBg.draw(mGame.batch);

        mGame.batch.end();
        if (Gdx.input.justTouched()) {
            stage = 1;
            //mGame.setScreen((Screen) new LibGdxSample());
            mGame.setScreen(new GameScreen(mGame,stage));
            //タッチされたらgameScreenに戻る
        }
    }

}