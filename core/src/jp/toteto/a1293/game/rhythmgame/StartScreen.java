package jp.toteto.a1293.game.rhythmgame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class StartScreen extends ScreenAdapter {
    boolean tbE;
    Music1 mMusic1;
    Music2 mMusic2;
    Home mHome;
    BackButton mBackButton;
    Manual mManual;
    //カメラのサイズを表す定数を定義する
    static final float CAMERA_WIDTH = 16;
    static final float CAMERA_HEIGHT = 9;
    static final float GUI_WIDTH = 512;
    static final float GUI_HEIGHT = 288;
    //カメラクラスとビューポートクラスをメンバ変数として定義
    OrthographicCamera mCamera;
    FitViewport mViewPort;
    //FitViewport mGuiViewPort;
    int stage;//ステージセレクト

    Preferences mPrefs;
    int mHighScore1;
    int mHighScore2;
    int mHighScore3;


    Player mPlayer;
    boolean tb1;
    boolean tb2;
    boolean tb3;
    boolean tb4;
    boolean ReleaseButton1;
    boolean ReleaseButton2;
    boolean ReleaseButton3;
    int b3switch;
    int end = 0;
    int createghost = 0;
    float screen1sTimer = 0;
    float screenbuttonTimer = 0;
    //ArrayList<Float> GhostT = new ArrayList<Float>();

    private RhythmGame mGame;
    Sprite mBg;
    OrthographicCamera mGuiCamera;
    FitViewport mGuiViewPort;
    BitmapFont mFont;
    Vector3 mTouchPoint; // タッチされた座標を保持するメンバ変数

    int mScore;

    Music gomusic = Gdx.audio.newMusic(Gdx.files.internal("175-Mozart-Symphony-No41-2nd.mp3"));//音楽準備
    Sound rebirth = Gdx.audio.newSound(Gdx.files.internal("rebirth.mp3"));//効果音準備

    public StartScreen(RhythmGame game) {
        // カメラ、ViewPortを生成、設定するメンバ変数に初期化して代入
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        // GUI用のカメラを設定する
        mGuiCamera = new OrthographicCamera();
        mGuiCamera.setToOrtho(false, GUI_WIDTH, GUI_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);
        mGuiViewPort = new FitViewport(GUI_WIDTH, GUI_HEIGHT, mGuiCamera);
        mTouchPoint = new Vector3();

        mPrefs = Gdx.app.getPreferences("jp.toteto.a1293.game.rhythmgame");
        mHighScore1 = mPrefs.getInteger("HIGHSCORE1", 0);
        mHighScore2 = mPrefs.getInteger("HIGHSCORE2", 0);
        mHighScore3 = mPrefs.getInteger("HIGHSCORE3", 0);

        gomusic.play(); //ゲームオーバー画面の音楽再生
        rebirth.dispose();

        mGame = game;

        if (mGame.mRequestHandler != null) {//表示するかどうか
            mGame.mRequestHandler.showAds(true); // 広告表示
        }


        // 背景の準備
        Texture bgTexture = new Texture("startscreen.png");
        mBg = new Sprite(new TextureRegion(bgTexture, 0, 0, 1022, 608));
        mBg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        mBg.setPosition(0, 0);


        // フォント
        mFont = new BitmapFont(Gdx.files.internal("roundfont.fnt"), Gdx.files.internal("roundfont.png"), false);
        mFont.getData().setScale(0.3f);// フォントサイズも指定
        createStage();
    }

    @Override
    public void render(float delta) {
        screen1sTimer += delta;
        if (screen1sTimer > 1) {
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
        mPlayer.draw(mGame.batch);
        mPlayer.updatestart(delta, screen1sTimer);
        mHome.draw(mGame.batch);
        mGame.batch.end();

        // カメラの座標をアップデート（計算）し、スプライトの表示に反映させる
        mGuiCamera.update();
        mGame.batch.setProjectionMatrix(mGuiCamera.combined);

        mGame.batch.begin();
        //mFont.draw(mGame.batch, "Score: " + mScore, 0, GUI_HEIGHT / 2 + 120, GUI_WIDTH, Align.center, false);
        if (mHighScore1>1 || mHighScore2>1) {
            mFont.draw(mGame.batch, "High Score" + "", -70, GUI_HEIGHT / 2 + 49, GUI_WIDTH, Align.center, false);
            mFont.draw(mGame.batch, mHighScore1 + "", 10, GUI_HEIGHT / 2 + 77, GUI_WIDTH, Align.center, false);
            mFont.draw(mGame.batch, mHighScore2 + "", 10, GUI_HEIGHT / 2 + 20, GUI_WIDTH, Align.center, false);
        }
        if (mHighScore3>1) {
            mFont.draw(mGame.batch, "Vexations mode:" + mHighScore3, 90, 110, GUI_WIDTH, Align.center, false);
        }

        mMusic1.draw(mGame.batch);
        mMusic2.draw(mGame.batch);

        mMusic1.Darker(delta);
        mMusic2.Darker(delta);
        mManual.draw(mGame.batch);
        mGame.batch.end();

        mCamera.update();
        mGame.batch.setProjectionMatrix(mCamera.combined);
        //↑はカメラの座標を計算しスプライト表示に反映させるのに必要
        mGame.batch.begin();
        mBackButton.draw(mGame.batch);
        mGame.batch.end();
/*
        if (Gdx.input.justTouched()) {
            gomusic.dispose();//メモリ解放
            if (mGame.mRequestHandler != null) {
                mGame.mRequestHandler.showAds(false); //広告消す
            }
            stage = 1;
            mGame.setScreen(new GameScreen(mGame,stage));
            //タッチされたらgameScreenに戻る選んだステージで始まる
        }*/
        tb1 = false;
        tb2 = false;
        tb3 = false;
        tb4 = false;


        for (int i = 0; i < 5; i++) { // 20 is max number of touch points
            if (Gdx.input.isTouched(i)) {
                Rectangle music1 = new Rectangle(300, 190, 200, 50);//
                //Rectangle rightd = new Rectangle(GUI_WIDTH - 70, 0, GUI_WIDTH, 72);//
                Rectangle music2 = new Rectangle(300, 130, 200, 50);//
                Rectangle exit = new Rectangle(467.2f, 246.4f, 83, 51);
                //test
                final int iX = Gdx.input.getX(i);
                final int iY = Gdx.input.getY(i);

                mGuiViewPort.unproject(mTouchPoint.set(iX, iY, 0));
                tb1 = tb1 || (music1.contains(mTouchPoint.x, mTouchPoint.y)); // Touch coordinates are in screen space
                tb2 = tb2 || (music2.contains(mTouchPoint.x, mTouchPoint.y));
                tb3 = tb3 || (exit.contains(mTouchPoint.x, mTouchPoint.y));
                //tb4 = tb4 || (leftd.contains(mTouchPoint.x, mTouchPoint.y));
            }
        }
        if (tb1 && !ReleaseButton2 && b3switch == 0) {
            mMusic1.Push();
            ReleaseButton1 = true;
        }
        if (tb2 && !ReleaseButton1 && b3switch == 0) {
            mMusic2.Push();
            ReleaseButton2 = true;
        }
        if (tb3 && !ReleaseButton1 && !ReleaseButton2) {
            ReleaseButton3 = true;
        }
        if (ReleaseButton1 && !tb1) {
            mPlayer.start=1;
            gomusic.dispose();//メモリ解放
            if (mGame.mRequestHandler != null) {
                mGame.mRequestHandler.showAds(false); //広告消す
            }
            stage = 1;
            screenbuttonTimer += delta;
            //タッチされたらgameScreenに戻る選んだステージで始まる
        }
        if (ReleaseButton2 && !tb2) {
            mPlayer.start=1;
            gomusic.dispose();//メモリ解放
            if (mGame.mRequestHandler != null) {
                mGame.mRequestHandler.showAds(false); //広告消す
            }
            stage = 2;
            screenbuttonTimer += delta;
            //タッチされたらgameScreenに戻る選んだステージで始まる
        }
        if (ReleaseButton3 && !tb3) {
            ReleaseButton3 = false;
            if (b3switch == 0) {
                mBackButton.setRegion(600, 192, 212, 160);
                if (mGame.mRequestHandler != null) {//表示するかどうか
                    mGame.mRequestHandler.showAds(false); // 広告表示
                }
                mManual.setAlpha(1);
                b3switch = 1;
                //ここに説明書入れる
            }else if(b3switch == 1){

                mBackButton.setRegion(600, 0, 212, 160);
                if (mGame.mRequestHandler != null) {//表示するかどうか
                    mGame.mRequestHandler.showAds(true); // 広告表示
                }
                mManual.setAlpha(0);
                b3switch = 0;
            }
        }
        if (screenbuttonTimer>2.3f){
            if (mGame.mRequestHandler != null) {//表示するかどうか
                mGame.mRequestHandler.showAds(false); // 広告表示
            }

            mGame.setScreen(new GameScreen(mGame, stage));
        }
    }

    @Override
    public void resize(int width, int height) {
        mViewPort.update(width, height);
        mGuiViewPort.update(width, height);
    }

    private void createStage() {

        Texture music1 = new Texture("music.png");
        TextureRegion music1Texture = new TextureRegion(music1, 0, 0, 512, 128);
        Texture music2 = new Texture("music.png");
        TextureRegion music2Texture = new TextureRegion(music2, 0, 150, 512, 128);
        Texture backbutton = new Texture("present.png");
        TextureRegion backbuttonTexture = new TextureRegion(backbutton, 600, 0, 212, 160);
        Texture player = new Texture("majo.png");
        TextureRegion playerTexture = new TextureRegion(player, 0, 64, 22, 32);
        Texture pumpkint = new Texture("pumpkin.png");
        TextureRegion pumpkinTexture = new TextureRegion(pumpkint, 0, 64, 32, 27);
        Texture home = new Texture("startscreen.png");
        TextureRegion homeTexture = new TextureRegion(home, 0, 625, 400, 400);
        Texture manual = new Texture("music.png");
        TextureRegion manualTexture = new TextureRegion(manual, 0, 280, 421, 232);



        // Playerを配置
        mPlayer = new Player(playerTexture);
        mPlayer.setPosition(1, 2.5f);

        mHome = new Home(homeTexture);
        mHome.setPosition(0, 1.3f);

        mMusic1 = new Music1(music1Texture);
        mMusic1.setPosition(300, 190);
        mMusic2 = new Music2(music2Texture);
        mMusic2.setPosition(300, 130);

        mBackButton = new BackButton(backbuttonTexture);
        mBackButton.setPosition(14.6f, 7.7f);

        mManual = new Manual(manualTexture);
        mManual.setPosition(6.0f, 5.0f);
        mManual.setAlpha(0);
    }
}
