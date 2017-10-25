package jp.toteto.a1293.game.rhythmgame;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



/**
 * Created by Fumio on 2017/10/01.
 */
// implements ApplicationListener, InputProcessor
public class TGameScreen extends ScreenAdapter {

    //qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq
    //カメラのサイズを表す定数を定義する
    static final float CAMERA_WIDTH = 16;
    static final float CAMERA_HEIGHT = 9;
    static final float GUI_WIDTH = 512;//GUI用カメラのサイズ
    static final float GUI_HEIGHT = 288;//GUI用カメラのサイズ

    //ゲーム開始前、中、ゲーム終了を表す定数の定義
    static final int GAME_STATE_READY = 0;
    static final int GAME_STATE_PLAYING = 1;
    static final int GAME_STATE_GAMEOVER = 2;
    static int FearGauge = 100;//体力上限を減らすゲージ
    static int LifeGauge = 100;//体力

    // 重力
    static final float GRAVITY = -12;

    private RhythmGame mGame;
//qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq
   /* class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }*/
    //private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();
    //タッチ情報を保持するクラスを作り、それのHashMapを作成して、複数のタッチ情報を保存
    //qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq

    Sprite mBg;
    //カメラクラスとビューポートクラスをメンバ変数として定義
    OrthographicCamera mCamera;
    OrthographicCamera mGuiCamera;

    FitViewport mViewPort;
    FitViewport mGuiViewPort;

    Random mRandom;//乱数を取得するためのクラス
    List<Note> mNote;
    List<Note2> mNote2;
    Bar mBar;

    GaugeBarBack mGaugeBarBack;
    LGaugeBar mLGaugeBar;
    FGaugeBar mFGaugeBar;


    iBar mIBar;
    iBar1 mIBar1;
    iBar2 mIBar2;
    DeadLine mDeadLine;

    Button1 mButton1;
    Button2 mButton2;
    Button3 mButton3;
    Button4 mButton4;

    Player mPlayer;

    AttackLine mAttackLine;
    JumpLine mJumpLine;
    Star mStar;

    ActionBack mActionBack;
    RFrame mRFrame;
    //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    List<Bat> mBat;
    List<Skeleton> mSkeleton;
    List<Ghost> mGhost;
    List<Pumpkin> mPumpkin;

    EnemyCreate mEnemyCreate;

    int mGameState;//ゲームの状態を保持
    Vector3 mTouchPoint; // タッチされた座標を保持するメンバ変数
    BitmapFont mFont; // Bitmapフォントの使用
    int mScore; // スコアを保持するメンバ変数
    int mHighScore; // ハイスコアを保持するメンバ変数
    float musictime = 0.00f;//再生時間を入れる変数
    Preferences mPrefs; // データを永続化させるためのPreferenceをメンバ変数に定義
    boolean b = false;
    //ボタンがタッチされているかaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    boolean tb1;
    boolean tb2;
    boolean tb3;
    boolean tb4;

    //ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp
    //boolean b = true;
    int ToSs;
    int ToSs2;
    int PumpkinTs;
    int SkeletonTs;
    int GhostTs;
    int BatTs;
    //リストの数を入れる
    String str = String.valueOf(musictime);
    //int FearGauge = 100;
    //int LifeGauge = 100;//体力

    int n = 0;
    int nn = 0;

    int end = 0;
    int hoge;//ランダムな数字
    int ENEMY_NUMBER = 0;//敵を選ぶための数字
    int createpumpkin = 0;//かぼちゃ出すときに加算する
    int createskeleton = 0;
    int createghost = 0;
    int createbat = 0;

    //float[] ToS = {5.20f,6.90f};//再生時間に応じて音を鳴らす、どのタイミングかを入れる
    ArrayList<Float> ToS = new ArrayList<Float>();
    ArrayList<Float> ToS2 = new ArrayList<Float>();
    ArrayList<Float> PumpkinT = new ArrayList<Float>();
    ArrayList<Float> SkeletonT = new ArrayList<Float>();
    ArrayList<Float> GhostT = new ArrayList<Float>();
    ArrayList<Float> BatT = new ArrayList<Float>();
    //ToS.add(10.2f);
    //String stringFormat;

    //float f = 2.7f;


    //音楽の準備
    Music playingmusic = Gdx.audio.newMusic(Gdx.files.internal("s_WitchAndCat.mp3"));

    //効果音の準備
    Sound hitsound = Gdx.audio.newSound(Gdx.files.internal("hitsound.mp3"));
    Sound fall = Gdx.audio.newSound(Gdx.files.internal("fall.mp3"));
    Sound jingle = Gdx.audio.newSound(Gdx.files.internal("jingle.mp3"));
    Sound getstarsound = Gdx.audio.newSound(Gdx.files.internal("getstarsound.mp3"));

    public TGameScreen(RhythmGame game) {
        mGame = game;

        ToS.add(1.43f);
        ToS.add(1.73f);
        ToS.add(1.95f);
        ToS.add(2.35f);
        ToS.add(2.67f);
        ToS.add(2.98f);
        ToS.add(3.29f);
        ToS.add(3.59f);
        ToS.add(3.93f);
        ToS.add(4.21f);
        ToS.add(4.52f);
        ToS.add(4.83f);
        ToS.add(5.45f);
        ToS.add(5.75f);
        ToS.add(6.08f);
        ToS.add(6.40f);
        ToS.add(6.65f);
        ToS.add(6.99f);
        ToS.add(7.30f);
        ToS.add(7.65f);
        ToS.add(7.95f);
        ToS.add(8.26f);
        ToS.add(8.58f);
        ToS.add(8.91f);
        ToS.add(9.21f);
        ToS.add(9.52f);
        ToS.add(9.81f);
        ToS.add(10.13f);
        ToS.add(10.74f);
        ToS.add(11.28f);
        ToS.add(11.83f);
        ToS.add(12.53f);
        ToS.add(13.17f);
        ToS.add(13.39f);
        ToS.add(13.83f);
        ToS.add(14.38f);
        ToS.add(15.03f);
        ToS.add(15.65f);
        ToS.add(15.78f);
        ToS.add(16.13f);
        ToS.add(16.76f);
        ToS.add(17.44f);

        ToS.add(18.22f);
        ToS.add(18.53f);

        ToS.add(18.82f);
        ToS.add(19.47f);
        ToS.add(20.07f);
        ToS.add(20.70f);

        ToS.add(21.28f);
        ToS.add(21.41f);
        ToS.add(21.56f);
        ToS.add(21.73f);
        ToS.add(22.03f);
        ToS.add(22.48f);

        ToS.add(23.74f);
        ToS.add(23.89f);
        ToS.add(24.04f);
        ToS.add(24.20f);
        ToS.add(24.50f);
        ToS.add(24.97f);

        ToS.add(26.20f);
        ToS.add(26.36f);
        ToS.add(26.51f);
        ToS.add(26.67f);
        ToS.add(26.98f);
        ToS.add(27.43f);

        ToS.add(28.06f);
        ToS.add(28.37f);
        ToS.add(28.67f);
        ToS.add(29.30f);
        ToS.add(29.92f);
        ToS.add(30.53f);
        ToS.add(30.85f);

        ToS.add(31.16f);
        ToS.add(31.32f);
        ToS.add(31.48f);
        ToS.add(31.66f);
        ToS.add(31.93f);
        ToS.add(32.4f);


        ToS.add(33.65f);
        ToS.add(33.82f);
        ToS.add(33.95f);
        ToS.add(34.15f);
        ToS.add(34.40f);
        ToS.add(34.89f);

        ToS.add(36.09f);
        ToS.add(36.28f);
        ToS.add(36.44f);
        ToS.add(36.61f);
        ToS.add(36.88f);
        ToS.add(37.35f);

        ToS.add(37.94f);
        ToS.add(38.29f);
        ToS.add(38.60f);
        ToS.add(39.20f);
        ToS.add(39.89f);
        ToS.add(40.43f);
        ToS.add(40.75f);
        ToS.add(41.08f);
        ToS.add(42.02f);
        ToS.add(42.36f);
        ToS.add(43.24f);
        ToS.add(43.57f);
        ToS.add(43.79f);
        ToS.add(44.28f);
        ToS.add(44.51f);

        ToS.add(46.04f);
        ToS.add(46.66f);
        ToS.add(47.37f);
        ToS.add(47.87f);

        ToS.add(48.56f);
        ToS.add(49.12f);
        ToS.add(49.45f);

        ToS.add(49.79f);
        ToS.add(50.11f);
        ToS.add(50.40f);


        ToS.add(50.97f);

        ToS.add(51.27f);
        ToS.add(51.54f);

        ToS.add(51.70f);

        ToS.add(51.86f);
        ToS.add(52.00f);
        ToS.add(52.18f);


        ToS.add(52.47f);
        ToS.add(52.63f);
        ToS.add(52.79f);
        ToS.add(52.95f);

        ToS.add(53.39f);
        ToS.add(53.57f);
        ToS.add(53.73f);
        ToS.add(53.89f);

        ToS.add(54.09f);

        ToS.add(54.22f);

        ToS.add(54.43f);

        ToS.add(54.65f);


        ToS.add(54.95f);

        ToS.add(55.23f);
        ToS.add(55.55f);

        ToS.add(55.85f);

        ToS.add(56.13f);
        ToS.add(56.31f);


        ToS.add(56.50f);
        ToS.add(56.91f);
        ToS.add(57.13f);
        ToS.add(58.27f);

        ToS.add(58.45f);
        ToS.add(58.63f);
        ToS.add(58.81f);
        ToS.add(58.97f);

        ToS.add(59.15f);


        ToS.add(59.64f);
        ToS.add(59.78f);
        ToS.add(59.92f);
        ToS.add(60.05f);

        ToS.add(59.94f);
        ToS.add(60.28f);
        //  ToS.add(60.22f);
        ToS.add(60.35f);

        ToS.add(60.81f);

        ToS.add(98.1f);


        ToS2.add(98.1f);

        PumpkinT.add(0.0f);
        PumpkinT.add(2.7f);
        PumpkinT.add(5.1f);
        PumpkinT.add(93.0f);

        BatT.add(0.0f);
        BatT.add(12.4f);
        BatT.add(99.7f);

        SkeletonT.add(1.3f);
        SkeletonT.add(9.1f);
        SkeletonT.add(98.7f);

        GhostT.add(11.1f);
        GhostT.add(15.1f);
        GhostT.add(98.7f);

        //playingmusic.setLooping(true);//音楽はループ

        //背景の処理
        Texture bgTexture = new Texture("back.png");
        //TextureReionで切り出すときの原点は左上
        mBg = new Sprite(new TextureRegion(bgTexture, 0, 0, 1024, 608));
        //画像の切り出し
        mBg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        //カメラサイズに設定
        mBg.setPosition(0, 0);
        //左下基準０．０に描画

        // カメラ、ViewPortを生成、設定するメンバ変数に初期化して代入
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);

        // GUI用のカメラを設定する
        mGuiCamera = new OrthographicCamera();
        mGuiCamera.setToOrtho(false, GUI_WIDTH, GUI_HEIGHT);
        mGuiViewPort = new FitViewport(GUI_WIDTH, GUI_HEIGHT, mGuiCamera);


        // メンバ変数の初期化
        mRandom = new Random();
        mNote = new ArrayList<Note>();
        mNote2 = new ArrayList<Note2>();
        mGameState = GAME_STATE_READY;
        mTouchPoint = new Vector3();
        mFont = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false); // フォントファイルの読み込み
        mFont.getData().setScale(0.8f);// フォントサイズも指定
        mScore = 0;
        mHighScore = 0;
        mSkeleton = new ArrayList<Skeleton>();
        mGhost = new ArrayList<Ghost>();
        mPumpkin = new ArrayList<Pumpkin>();
        mBat = new ArrayList<Bat>();
        // ハイスコアをPreferencesから取得する
        mPrefs = Gdx.app.getPreferences("jp.toteto.a1293.game.rhythmgame");//Preferencesの取得
        mHighScore = mPrefs.getInteger("HIGHSCORE", 0);//第2引数はキーに対応する値がなかった場合に返ってくる値（初期値）

        createStage();
        //オブジェクト配置するcreateStageメソッドを呼び出す

    }

    //描画を行うレンダーメソッド
    @Override
    public void render(float delta) {

        // それぞれの状態をアップデートする
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        //赤、緑、青、透過の指定
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //指定した色で塗りつぶし

        //スプライトなどの描画はbeginとendの間で行う
        // カメラの座標をアップデート（計算）し、スプライトの表示に反映させる
        mCamera.update();
        mGame.batch.setProjectionMatrix(mCamera.combined);
        //↑はカメラの座標を計算しスプライト表示に反映させるのに必要

        mGame.batch.begin();


        // 原点は左下
        mBg.setPosition(mCamera.position.x - CAMERA_WIDTH / 2, mCamera.position.y - CAMERA_HEIGHT / 2);
        mBg.draw(mGame.batch);

        // Note,リストで保持しているので順番に取り出し
        for (int i = 0; i < mNote.size(); i++) {
            //for (int i = 0; i < 6; i++) {
            mNote.get(i).draw(mGame.batch);
        }
        // Note2,リストで保持しているので順番に取り出し
        for (int i = 0; i < mNote2.size(); i++) {
            //for (int i = 0; i < 6; i++) {
            mNote2.get(i).draw(mGame.batch);
        }

        // 棒の表示
        mBar.draw(mGame.batch);

        mIBar.draw(mGame.batch);
        mIBar1.draw(mGame.batch);
        mIBar2.draw(mGame.batch);
        //デッドラインの表示
        // mDeadLine.draw(mGame.batch);


        // ボタンの表示
        mButton1.draw(mGame.batch);
        mButton2.draw(mGame.batch);
        mButton3.draw(mGame.batch);
        mButton4.draw(mGame.batch);
        mActionBack.draw(mGame.batch);
        mAttackLine.draw(mGame.batch);
        mJumpLine.draw(mGame.batch);
        mStar.draw(mGame.batch);
        // 骸骨リストで保持しているので順番に取り出し
        for (int i = 0; i < mSkeleton.size(); i++) {
            mSkeleton.get(i).draw(mGame.batch);
        }
        // 幽霊リストで保持しているので順番に取り出し
        for (int i = 0; i < mGhost.size(); i++) {
            mGhost.get(i).draw(mGame.batch);
        }
        // かぼちゃリストで保持しているので順番に取り出し
        for (int i = 0; i < mPumpkin.size(); i++) {
            mPumpkin.get(i).draw(mGame.batch);
        }
        // Bat,リストで保持しているので順番に取り出し
        for (int i = 0; i < mBat.size(); i++) {
            mBat.get(i).draw(mGame.batch);
        }
        mPlayer.draw(mGame.batch);
        mRFrame.draw(mGame.batch);

        //ライフゲージ周り
        mGaugeBarBack.draw(mGame.batch);
        mLGaugeBar.draw(mGame.batch);
        mFGaugeBar.draw(mGame.batch);

        mGame.batch.end();

        // スコア表示
        mGuiCamera.update();
        mGame.batch.setProjectionMatrix(mGuiCamera.combined);
        //↑はカメラの座標を計算しスプライト表示に反映させるのに必要
        mGame.batch.begin();

        //drawメソッドで描画第1引数にSprteBatch、第2引数に表示されたい文字列、第3引数にx座標、第4引数にy座標
        //mFont.draw(mGame.batch, "HighScore: " + mHighScore, 16, GUI_HEIGHT - 15);
        mFont.draw(mGame.batch, "Music: " + musictime, 16, GUI_HEIGHT - 55);
        mFont.draw(mGame.batch, "Score: " + mScore + "FG: " + FearGauge + "Life: " + LifeGauge, 16, GUI_HEIGHT - 35);
        mFont.draw(mGame.batch, "tb4.2.3.1" + tb4 + "." + tb2 + "." + tb3 + "." + tb1 + "." + mPlayer.jumpstate, 16, GUI_HEIGHT - 75);


        mGame.batch.end();

    }

    //resizeメソッドをオーバーライドしてFitViewportクラスのupdateメソッドを呼び出す
    //このメソッドは物理的な画面のサイズが変更されたときに呼ばれる
    @Override
    public void resize(int width, int height) {
        mViewPort.update(width, height);
        mGuiViewPort.update(width, height);
    }

    // ステージを作成する、オブジェクトを配置するメソッド
    private void createStage() {
        LifeGauge = 100;
        FearGauge = 100;

        ToSs = ToS.size();
        ToSs2 = ToS2.size();
        PumpkinTs = PumpkinT.size();
        SkeletonTs = SkeletonT.size();
        GhostTs = GhostT.size();
        BatTs = BatT.size();

        // テクスチャの準備
        Texture stepTexture = new Texture("step.png");
        Texture stepaTexture = new Texture("stepa.png");
        Texture starTexture = new Texture("star.png");
        Texture darkstarTexture = new Texture("darkstar.png");
        Texture noteTexture = new Texture("note.png");
        Texture pnoteTexture = new Texture("pnote.png");
        Texture playerTexture = new Texture("majo.png");
        Texture ufoTexture = new Texture("ufo.png");
        Texture barTexture = new Texture("bar.png");
        Texture ibarTexture = new Texture("ibar.png");
        Texture gaugebarbackTexture = new Texture("gaugeback.png");
        Texture lgaugebarTexture = new Texture("lgauge.png");
        Texture fgaugebarTexture = new Texture("fgauge.png");
        Texture button1Texture = new Texture("button1a.png");
        Texture button2Texture = new Texture("button2a.png");
        Texture button3Texture = new Texture("button3a.png");
        Texture button3bTexture = new Texture("button3b.png");
        Texture button4Texture = new Texture("button4a.png");
        Texture actionbackTexture = new Texture("actionback.png");
        Texture attacklineTexture = new Texture("attackline.png");
        Texture jumplineTexture = new Texture("up.png");
        Texture batTexture = new Texture("Bat.png");
        Texture skeletonTexture = new Texture("skeleton.png");
        Texture ghostTexture = new Texture("ghost.png");
        Texture pumpkinTexture = new Texture("pumpkin.png");
        Texture frameTexture = new Texture("frame.png");

        // StepとStar、DarkStar、Enemyをゴールの高さまで配置していく
        // float y = 0;
        // 棒を配置
        mBar = new Bar(barTexture, 0, 0, 128, 128);
        mBar.setPosition(3.1f, 0);

        mIBar = new iBar(ibarTexture, 0, 0, 128, 128);
        mIBar.setPosition(3, 0);
        mIBar1 = new iBar1(ibarTexture, 0, 0, 128, 128);
        mIBar1.setPosition(2.79f, 0);
        mIBar2 = new iBar2(ibarTexture, 0, 0, 128, 128);
        mIBar2.setPosition(3.3f, 0);
//左右0.1までのずれ許容
        // デッドラインを配置
        mDeadLine = new DeadLine(ibarTexture, 0, 0, 128, 128);
        mDeadLine.setPosition(1.3f, 0);


        mGaugeBarBack = new GaugeBarBack(gaugebarbackTexture, 0, 0, 256, 256);
        mGaugeBarBack.setPosition(0, 4.5f);

        mFGaugeBar = new FGaugeBar(fgaugebarTexture, 0, 0, 256, 256);
        mFGaugeBar.setPosition(0, 5.0f);

        mLGaugeBar = new LGaugeBar(lgaugebarTexture, 0, 0, 256, 256);
        mLGaugeBar.setPosition(0, 5.0f);

        // アクション部背景を配置
        mActionBack = new ActionBack(actionbackTexture, 0, 0, 1024, 304);
        mActionBack.setPosition(0, 4.5f);

        // 警告フレームを配置
        mRFrame = new RFrame(frameTexture, 0, 0, 1024, 304);
        mRFrame.setPosition(0, 4.5f);
        mRFrame.setAlpha(0);

        // 攻撃エリア表示を配置
        mAttackLine = new AttackLine(attacklineTexture, 0, 0, 512, 170);
        mAttackLine.setPosition(1.6f, 4.7f);

        mJumpLine = new JumpLine(jumplineTexture, 0, 0, 128, 128);
        mJumpLine.setPosition(0.6f, 5.3f);

        mStar = new Star(darkstarTexture, 0, 0, 128, 128);
        mStar.setPosition(6.8f, 10);

        // ボタン１を配置
        mButton1 = new Button1(button1Texture, 0, 0, 128, 128);
        mButton1.setPosition(14, 2.25f);

        // ボタン2を配置
        mButton2 = new Button2(button2Texture, 0, 0, 128, 128);
        mButton2.setPosition(14, 0);

        // ボタン3を配置
        mButton3 = new Button3(button3Texture, 0, 0, 128, 128);
        mButton3.setPosition(0, 2.25f);

        // ボタン4を配置
        mButton4 = new Button4(button4Texture, 0, 0, 128, 128);
        mButton4.setPosition(0, 0);

        // Playerを配置
        mPlayer = new Player(playerTexture, 0, 64, 23, 32);
        mPlayer.setPosition(1.4f, 5.2f);


        while (end < ToSs || end < ToSs2) {

            Note note = new Note(noteTexture, 0, 0, 128, 128);
            //場所を決める
            note.setPosition(31, 0.85f);
            mNote.add(note);

            Note2 note2 = new Note2(pnoteTexture, 0, 0, 128, 128);
            //場所を決める
            note2.setPosition(17, 2.85f);
            mNote2.add(note2);

            Skeleton skeleton = new Skeleton(skeletonTexture, 0, 34, 24, 32);
            //場所を決める
            skeleton.setPosition(18, 5.2f);
            mSkeleton.add(skeleton);

            Ghost ghost = new Ghost(ghostTexture, 0, 2, 24, 32);
            //場所を決める
            ghost.setPosition(16.8f, 5.2f);
            mGhost.add(ghost);

            Pumpkin pumpkin = new Pumpkin(pumpkinTexture, 0, 0, 31, 26);
            //場所を決める
            pumpkin.setPosition(17.8f, 5.2f);
            mPumpkin.add(pumpkin);

            Bat bat = new Bat(batTexture, 0, 100, 21, 16);
            //場所を決める
            bat.setPosition(37.76f, 5.5f);
            mBat.add(bat);


            end++;

        }
    }

    // それぞれのオブジェクトの状態をアップデートする
    private void update(float delta) {


        switch (mGameState) {
            case GAME_STATE_READY:
                updateReady();
                break;
            case GAME_STATE_PLAYING:
                updatePlaying(delta);
                break;
            case GAME_STATE_GAMEOVER:
                updateGameOver();
                break;
        }
    }

    //タッチされたら状態をゲーム中であるGAME_STATE_PLAYINGに変更
    private void updateReady() {
        if (Gdx.input.justTouched()) {
            mGameState = GAME_STATE_PLAYING;
        }
    }

    private void updatePlaying(float delta) {

        if (!playingmusic.isPlaying()) {
            playingmusic.play();//音楽を再生
        }

        mPlayer.update(delta);
        mStar.update(delta);

        musictime = playingmusic.getPosition();//再生時間取得
        //ボタンがタッチされているか
        tb1 = false;
        tb2 = false;
        tb3 = false;
        tb4 = false;
        mAttackLine.unpush();
        mJumpLine.unpush();


        //for (int i = 0; i < 5; i++) { // 20 is max number of touch points
        if (Gdx.input.justTouched()) {Gdx.app.log("MyTag","ToS.add("+String.valueOf(musictime - 4)+"f);");
            //test
            Rectangle rightu = new Rectangle(GUI_WIDTH - 70, 72, GUI_WIDTH, 72);//ボタン1
            Rectangle rightd = new Rectangle(GUI_WIDTH - 70, 0, GUI_WIDTH, 72);//ボタン2
            Rectangle leftu = new Rectangle(0, 72, 70, 72);//ボタン３
            Rectangle leftd = new Rectangle(0, 0, 70, 72);//ボタン４
            //test
            //final int iX = Gdx.input.getX(i);
            //final int iY = Gdx.input.getY(i);

            //mGuiViewPort.unproject(mTouchPoint.set(iX, iY, 0));
            tb1 = tb1 || (rightu.contains(mTouchPoint.x, mTouchPoint.y)); // Touch coordinates are in screen space
            tb2 = tb2 || (rightd.contains(mTouchPoint.x, mTouchPoint.y));
            tb3 = tb3 || (leftu.contains(mTouchPoint.x, mTouchPoint.y)); // Touch coordinates are in screen space
            tb4 = tb4 || (leftd.contains(mTouchPoint.x, mTouchPoint.y));
        }
        //}
        if (tb1) {
            //mJumpLine.update(delta);
            //mJumpLine.push();


        }
        if (tb2) {
            mAttackLine.push();
        }

        if (n < ToSs) {

            for (int i = 0; i < n; i++) { //ここで一回のみの動作に
                mNote.get(i).update(delta);
            }
            if (playingmusic.getPosition() > ToS.get(n)) {
                n++;
            }
        }
        //mNote2.get(2).update(delta);
        // Stepa
        if (nn < ToSs2) {

            for (int i = 0; i < nn; i++) { //ここで一回のみの動作に
                mNote2.get(i).update(delta);

            }
            if (playingmusic.getPosition() > ToS2.get(nn)) {//指定時間を超えた瞬間にカウンター加算しノーツを出す
                nn++;
            }
        }

        //かぼちゃ出すタイミングで動作
        if (createpumpkin < PumpkinTs) {
            for (int i = 0; i < createpumpkin; i++) {
                mPumpkin.get(i).update(delta);
                //mGhost.get(i).update(delta)
            }
            if (playingmusic.getPosition() > PumpkinT.get(createpumpkin)) {//指定時間を超えた瞬間にカウンター加算しノーツを出す
                createpumpkin++;
            }
        }
        //骸骨出すタイミングで動作
        if (createskeleton < SkeletonTs) {
            for (int i = 0; i < createskeleton; i++) {
                mSkeleton.get(i).update(delta);
                //mGhost.get(i).update(delta);
            }
            if (playingmusic.getPosition() > SkeletonT.get(createskeleton)) {//指定時間を超えた瞬間にカウンター加算しノーツを出す
                createskeleton++;
            }
        }
        //幽霊出すタイミングで動作
        if (createghost < GhostTs) {
            for (int i = 0; i < createghost; i++) {
                mGhost.get(i).update(delta);
            }
            if (playingmusic.getPosition() > GhostT.get(createghost)) {//指定時間を超えた瞬間にカウンター加算しノーツを出す
                createghost++;
            }
        }
        //コウモリ出すタイミングで動作
        if (createbat < BatTs) {
            for (int i = 0; i < createbat; i++) {
                mBat.get(i).update(delta);
            }
            if (playingmusic.getPosition() > BatT.get(createbat)) {//指定時間を超えた瞬間にカウンター加算しノーツを出す
                createbat++;
            }
        }

        mLGaugeBar.GetDamage();
        mFGaugeBar.GetDamage();
        mActionBack.Darker();


        if (LifeGauge > FearGauge) {
            LifeGauge = FearGauge;
        }

        if (LifeGauge <= 15) {
            mRFrame.update(delta);
        }
//ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp
        checkCollision();

        // ゲームオーバーか判断する
        checkGameOver();

    }

    //あたり判定の処理
    private void checkCollision() {

        // ノーツとの当たり判定
        for (int i = 0; i < mNote.size(); i++) {
            Note note = mNote.get(i);
            //testaa if (tb4) {
            //ボタン３が押されたときに上ラインのあたり判定
            if (note.mState == 1) {
                continue;
                //消えてるのには反応しない
            }//testaa次のtestaaまで消す
            if (mIBar1.getBoundingRectangle().overlaps(note.getBoundingRectangle())
                    && mIBar2.getBoundingRectangle().overlaps(note.getBoundingRectangle())) {
                getstarsound.play(1.0f);//獲得音
                mScore = mScore + 1; //スコアに加算
                if (mScore > mHighScore) { //ハイスコアを超えた場合
                    mHighScore = mScore; //今の点数をハイスコアに
                    //ハイスコアをPreferenceに保存する
                    mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                    mPrefs.flush(); // 値を永続化するのに必要
                }
                note.get();//消す
            }/*testaaここまで消す
                if (mIBar.getBoundingRectangle().overlaps(note.getBoundingRectangle())) {
                    //testa
                    if (tb1 && !tb2){
                        mPlayer.jumpstate = 1;
                    }
                    if (tb2 && !tb1){
                        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        mStar.jumpstate = 1;
                        //ボタン2押しながら得点得た時の動作
                    }
                    if (mIBar1.getBoundingRectangle().overlaps(note.getBoundingRectangle())
                            && mIBar2.getBoundingRectangle().overlaps(note.getBoundingRectangle())) {
                        getstarsound.play(1.0f);//獲得音
                        mScore = mScore + 5; //スコアに加算
                        if (mScore > mHighScore) { //ハイスコアを超えた場合
                            mHighScore = mScore; //今の点数をハイスコアに
                            //ハイスコアをPreferenceに保存する
                            mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                            mPrefs.flush(); // 値を永続化するのに必要
                        }
                        note.get();//消す
                    } else {
                        mScore = mScore + 3; //スコアに加算
                        if (mScore > mHighScore) { //ハイスコアを超えた場合
                            mHighScore = mScore; //今の点数をハイスコアに
                            //ハイスコアをPreferenceに保存する
                            mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                            mPrefs.flush(); // 値を永続化するのに必要
                        }
                        note.get();//消す
                        break;
                    }
                } else {
                    if (mIBar1.getBoundingRectangle().overlaps(note.getBoundingRectangle())
                            || mIBar2.getBoundingRectangle().overlaps(note.getBoundingRectangle())) {
                        hitsound.play(1.0f);//衝突音
                        FearGauge -= 5;//プレーヤーダメージ
                        note.get();//消す
                    }
                }testaa*/
            //タイミング判定
            //testaa}
            if (note.mState == 0 && mDeadLine.getBoundingRectangle().overlaps(note.getBoundingRectangle())) {
                //押されなかった場合（ダメージを追加予定
                FearGauge -= 5;//プレーヤーダメージ
                note.get();//消す
            }
        }
        for (int i = 0; i < mNote2.size(); i++) {
            Note2 note2 = mNote2.get(i);
            //testaa if (tb3) {
            //ボタン３が押されたときに上ラインのあたり判定
            if (note2.mState == 1) {
                continue;
                //消えてるのには反応しない
            }
            //testaa次のtestaaまで消す
            if (mIBar1.getBoundingRectangle().overlaps(note2.getBoundingRectangle())
                    && mIBar2.getBoundingRectangle().overlaps(note2.getBoundingRectangle())) {
                getstarsound.play(1.0f);//獲得音
                mScore = mScore + 5; //スコアに加算
                if (mScore > mHighScore) { //ハイスコアを超えた場合
                    mHighScore = mScore; //今の点数をハイスコアに
                    //ハイスコアをPreferenceに保存する
                    mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                    mPrefs.flush(); // 値を永続化するのに必要
                }
                note2.get();//消す
            }/*testaaここまで消す
                if (mIBar.getBoundingRectangle().overlaps(note2.getBoundingRectangle())) {
                    //testa
                    if (tb1 && !tb2){
                        mPlayer.jumpstate = 1;
                    }
                    if (tb2 && !tb1){
                        //ボタン2押しながら得点得た時の動作
                        mStar.jumpstate = 1;
                    }
                    if (mIBar1.getBoundingRectangle().overlaps(note2.getBoundingRectangle())
                            && mIBar2.getBoundingRectangle().overlaps(note2.getBoundingRectangle())) {
                        getstarsound.play(1.0f);//獲得音
                        mScore = mScore + 5; //スコアに加算
                        if (mScore > mHighScore) { //ハイスコアを超えた場合
                            mHighScore = mScore; //今の点数をハイスコアに
                            //ハイスコアをPreferenceに保存する
                            mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                            mPrefs.flush(); // 値を永続化するのに必要
                        }
                        note2.get();//消す
                    } else {
                        mScore = mScore + 3; //スコアに加算
                        if (mScore > mHighScore) { //ハイスコアを超えた場合
                            mHighScore = mScore; //今の点数をハイスコアに
                            //ハイスコアをPreferenceに保存する
                            mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                            mPrefs.flush(); // 値を永続化するのに必要
                        }
                        note2.get();//消す

                        break;
                    }
                } else {
                    if (mIBar1.getBoundingRectangle().overlaps(note2.getBoundingRectangle())
                            || mIBar2.getBoundingRectangle().overlaps(note2.getBoundingRectangle())) {
                        hitsound.play(1.0f);//衝突音
                        FearGauge -= 5;//プレーヤーダメージ
                        note2.get();//消す
                    }
                }testaa*/
            //タイミング判定
            //testaa }
            if (note2.mState == 0 && mDeadLine.getBoundingRectangle().overlaps(note2.getBoundingRectangle())) {
                //押されなかった場合（ダメージを追加予定
                FearGauge -= 5;//プレーヤーダメージ
                note2.get();//消す
            }
        }
        //かぼちゃ
        for (int i = 0; i < mPumpkin.size(); i++) {
            Pumpkin pumpkin = mPumpkin.get(i);
            if (mStar.getBoundingRectangle().overlaps(pumpkin.getBoundingRectangle())) {
                //攻撃当たった場合
                pumpkin.get();//消す
            }
            if (pumpkin.mState == 0 && mPlayer.getBoundingRectangle().overlaps(pumpkin.getBoundingRectangle())) {
                //キャラに当たった場合
                LifeGauge -= 5;
                pumpkin.get();//消す
            }
        }
        //骸骨
        for (int i = 0; i < mSkeleton.size(); i++) {
            Skeleton skeleton = mSkeleton.get(i);
            if (mStar.getBoundingRectangle().overlaps(skeleton.getBoundingRectangle())) {
                //攻撃当たった場合
                skeleton.get();//消す
            }
        }
        //幽霊
        for (int i = 0; i < mGhost.size(); i++) {
            Ghost ghost = mGhost.get(i);
            if (mStar.getBoundingRectangle().overlaps(ghost.getBoundingRectangle()) && ghost.getY() < 8) {
                //攻撃当たった場合
                ghost.get();//消す
            }
        }
        //コウモリ
        for (int i = 0; i < mBat.size(); i++) {
            Bat bat = mBat.get(i);
            if (bat.mState == 0 && mPlayer.getBoundingRectangle().overlaps(bat.getBoundingRectangle())) {
                //キャラに当たった場合
                LifeGauge -= 5;
                bat.get();//消す
            }
        }
        //あたり判定ここまで
    }

    //ゲームオーバー時ResultScreenに遷移
    private void updateGameOver() {
        playingmusic.dispose();//メモリ解放
        hitsound.dispose();//メモリ解放
        fall.dispose();//メモリ解放
        getstarsound.dispose();//メモリ解放
        jingle.dispose();//メモリ解放
        mGame.setScreen(new ResultScreen(mGame, mScore));
    }

    private void checkGameOver() {
        //if (LifeGauge <= 0){
        //    updateGameOver();
        //}
        //ここの時間で終了
        if (playingmusic.getPosition() > 67) {
            Gdx.app.log("RhythmGame", "GAMEOVER");
            //gomusic.play();//ゲームオーバー画面の音楽再生
            playingmusic.dispose();//メモリ解放
            mGameState = GAME_STATE_GAMEOVER;
        }
    }

    private void check() {

    }
}
