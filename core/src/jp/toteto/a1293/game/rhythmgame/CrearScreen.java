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

public class CrearScreen extends ScreenAdapter {
    boolean tbE;
    ExitButton mExitButton;
    RetryButton mRetryButton;
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
    Player mPlayer;
    Pumpkin mPumpkin;

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

    public CrearScreen(RhythmGame game, int score) {
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
        Texture bgTexture = new Texture("creascreen.png");
        mBg = new Sprite(new TextureRegion(bgTexture, 0, 0, 1022, 608));
        mBg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        mBg.setPosition(0, 0);



        // フォント
        mFont = new BitmapFont(Gdx.files.internal("roundfont.fnt"), Gdx.files.internal("roundfont.png"), false);
        mFont.getData().setScale(0.5f);// フォントサイズも指定
        createStage();
    }

    @Override
    public void render(float delta) {
        screen1sTimer += delta;
        if (screen1sTimer>1){
            screen1sTimer = 0;
        }
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
        mGhost.get(0).updateRS(delta,-1.7f);
        mGhost.get(1).updateRS(delta,-1.4f);
        mGhost.get(2).updateRS(delta,-1.1f);
        mGhost.get(3).updateRS(delta,-0.7f);
        mGhost.get(4).updateRS(delta,0);
        mGhost.get(5).updateRS(delta,2.2f);
        mGhost.get(6).updateRS(delta,1.2f);
        mGhost.get(7).updateRS(delta,0.8f);
        mGhost.get(8).updateRS(delta,0.4f);
        mGhost.get(9).updateRS(delta,3.0f);
        for (int i = 0; i < mGhost.size(); i++) {
            //mGhost.get(i).draw(mGame.batch);
        }
        mPlayer.draw(mGame.batch);
        mPlayer.updateSS(delta,screen1sTimer);
        mPumpkin.draw(mGame.batch);
        mPumpkin.updateSS(delta);

        mGame.batch.end();

        // カメラの座標をアップデート（計算）し、スプライトの表示に反映させる
        mGuiCamera.update();
        mGame.batch.setProjectionMatrix(mGuiCamera.combined);
        mGame.batch.begin();
        mFont.draw(mGame.batch, "Score: " + mScore, 0, GUI_HEIGHT / 2 + 120, GUI_WIDTH, Align.center, false);

        mExitButton.draw(mGame.batch);
        mRetryButton.draw(mGame.batch);
        mExitButton.Darker(delta);
        mRetryButton.Darker(delta);
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
        Texture exitbutton = new Texture("ExitButton.png");
        TextureRegion exitbuttonTexture = new TextureRegion(exitbutton,0, 0, 512, 310);
        Texture retrybutton = new Texture("RetryButton.png");
        TextureRegion retrybuttonTexture = new TextureRegion(retrybutton,0, 0, 512, 310);
        Texture ghosttt = new Texture("ghost.png");
        TextureRegion ghostTexture = new TextureRegion(ghosttt,0, 2, 24, 32);
        Texture player = new Texture("majo.png");
        TextureRegion playerTexture = new TextureRegion(player,0, 64, 22, 32);
        Texture pumpkint = new Texture("pumpkin.png");
        TextureRegion pumpkinTexture = new TextureRegion(pumpkint,0, 64, 32, 27);


        while (end < 10) {
            Ghost ghost = new Ghost(ghostTexture);
            //場所を決める
            ghost.setPosition(MathUtils.random(0, 16), MathUtils.random(1, 7.7f));
            GHOST_VELOCITY =MathUtils.random(-3, 3);
            mGhost.add(ghost);
            end++;
        }
        // Playerを配置
        mPlayer = new Player(playerTexture);
        mPlayer.setPosition(-1,2.5f);

        mPumpkin = new Pumpkin(pumpkinTexture);
        mPumpkin.setPosition(-24,2.5f);


        mExitButton = new ExitButton(exitbuttonTexture);
        mExitButton.setPosition(128, 15);
        mRetryButton = new RetryButton(retrybuttonTexture);
        mRetryButton.setPosition(300, 15);

    }
}