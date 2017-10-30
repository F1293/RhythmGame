package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

import static jp.toteto.a1293.game.rhythmgame.Ghost.GHOST_VELOCITY;

public class ResultScreen extends ScreenAdapter {
    //カメラのサイズを表す定数を定義する
    static final float CAMERA_WIDTH = 16;
    static final float CAMERA_HEIGHT = 9;
    static final float GUI_WIDTH = 512;
    static final float GUI_HEIGHT = 288;
    //カメラクラスとビューポートクラスをメンバ変数として定義
    OrthographicCamera mCamera;
    FitViewport mViewPort;
    //FitViewport mGuiViewPort;

    List<Ghost> mGhost;

    int GhostTs;
    int end = 0;
    int createghost = 0;
    float screen1sTimer =0;
    //ArrayList<Float> GhostT = new ArrayList<Float>();

    private RhythmGame mGame;
    Sprite mBg;
    OrthographicCamera mGuiCamera;
    FitViewport mGuiViewPort;
    BitmapFont mFont;

    int mScore;

    Music gomusic = Gdx.audio.newMusic(Gdx.files.internal("gomusic.mp3"));//音楽準備
    Sound rebirth = Gdx.audio.newSound(Gdx.files.internal("rebirth.mp3"));//効果音準備

    public ResultScreen(RhythmGame game, int score) {
        // カメラ、ViewPortを生成、設定するメンバ変数に初期化して代入
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        // GUI用のカメラを設定する
        mGuiCamera = new OrthographicCamera();
        mGuiCamera.setToOrtho(false, GUI_WIDTH, GUI_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);
        mGuiViewPort = new FitViewport(GUI_WIDTH, GUI_HEIGHT, mGuiCamera);

        mGhost = new ArrayList<Ghost>();

        gomusic.play(); //ゲームオーバー画面の音楽再生
        rebirth.dispose();

        mGame = game;

        if (mGame.mRequestHandler != null) {//表示するかどうか
            mGame.mRequestHandler.showAds(true); // 広告表示
        }


        mScore = score;

        // 背景の準備
        Texture bgTexture = new Texture("resultscreen.png");
        mBg = new Sprite(new TextureRegion(bgTexture, 0, 0, 1022, 608));
        mBg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        mBg.setPosition(0, 0);



        // フォント
        mFont = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        createStage();
    }

    @Override
    public void render(float delta) {
        screen1sTimer += delta;
        //幽霊出すタイミングで動作

        // 描画する
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

// カメラの座標をアップデート（計算）し、スプライトの表示に反映させる
        mCamera.update();
        mGame.batch.setProjectionMatrix(mCamera.combined);
        //↑はカメラの座標を計算しスプライト表示に反映させるのに必要

        mGame.batch.begin();
        mBg.draw(mGame.batch);
        mGhost.get(0).updateRS(delta);
        mGhost.get(1).updateRS(delta);
        mGhost.get(2).updateRS(delta);
        mGhost.get(3).updateRS(delta);
        mGhost.get(4).updateRS(delta);
        mGhost.get(5).updateRS(delta);
        mGhost.get(6).updateRS(delta);
        mGhost.get(7).updateRS(delta);
        mGhost.get(8).updateRS(delta);
        mGhost.get(9).updateRS(delta);
        for (int i = 0; i < mGhost.size(); i++) {
            mGhost.get(i).draw(mGame.batch);
        }

        mGame.batch.end();
        // カメラの座標をアップデート（計算）し、スプライトの表示に反映させる
        mGuiCamera.update();
        mGame.batch.setProjectionMatrix(mGuiCamera.combined);
        mGame.batch.begin();
        mFont.draw(mGame.batch, "Score: " + mScore, 0, GUI_HEIGHT / 2 + 60, GUI_WIDTH, Align.center, false);
        mFont.draw(mGame.batch,screen1sTimer + "Retry?", 0, GUI_HEIGHT / 2 - 40, GUI_WIDTH, Align.center, false);

        mGame.batch.end();

        if (Gdx.input.justTouched()) {
            gomusic.dispose();//メモリ解放
            rebirth.play(1.0f);//復活音
            if (mGame.mRequestHandler != null) {
                mGame.mRequestHandler.showAds(false); //広告消す
            }
            mGame.setScreen(new GameScreen(mGame));
            //タッチされたらgameScreenに戻る
        }
    }
    @Override
    public void resize(int width, int height) {
        mViewPort.update(width, height);
        mGuiViewPort.update(width, height);
    }

    private void createStage() {

        GhostTs = 10;


        Texture ghosttt = new Texture("ghost.png");
        TextureRegion ghostTexture = new TextureRegion(ghosttt,0, 2, 24, 32);

        while (end < 10) {
            Ghost ghost = new Ghost(ghostTexture);
            //場所を決める
            ghost.setPosition(MathUtils.random(0, 8), MathUtils.random(0, 5));
            GHOST_VELOCITY =MathUtils.random(-3, 3);
            mGhost.add(ghost);
            end++;
        }
    }
    //あたり判定の処理
    /*private void checkCollision() {
        for (int i = 0; i < mGhost.size(); i++) {
            Ghost ghost = mGhost.get(i);
            if (mStar.getBoundingRectangle().overlaps(ghost.getBoundingRectangle()) && ghost.getY() < 8) {
                //攻撃当たった場合
                ghost.get();//消す
            }
        }
    }*/
}