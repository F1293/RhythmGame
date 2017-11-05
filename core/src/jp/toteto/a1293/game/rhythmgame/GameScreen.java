package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
public class GameScreen extends ScreenAdapter {

    //アニメーション
    public TextureRegion jumpingFrame;
    public TextureRegion attackFrame;

    Texture img;
    Texture button3;
    TextureRegion[] playeranimationFrames;
    Animation<TextureRegion> playeranimation;
    TextureRegion[] button3animationFrames;
    Animation<TextureRegion> button3animation;
    TextureRegion[] button4animationFrames;
    Animation<TextureRegion> button4animation;
    float elapsedTime;
    float anitimer;
    //アニメーション
    float b3pushcounter;
    float b4pushcounter;
    float screen1sTimer;
    float screen2sTimer;
    float screen12sTimer;
    float GameOverCounter;

    //カメラのサイズを表す定数を定義する
    static final float CAMERA_WIDTH = 16;
    static final float CAMERA_HEIGHT = 9;
    static final float GUI_WIDTH = 512;//GUI用カメラのサイズ
    static final float GUI_HEIGHT = 288;//GUI用カメラのサイズ

    //ゲーム開始前、中、ゲーム終了を表す定数の定義
    static final int GAME_STATE_READY = 0;
    static final int GAME_STATE_PLAYING = 1;
    static final int GAME_STATE_GAMEOVER = 2;
    static final int GAME_STATE_GAMECREAR = 3;
    static int FearGauge = 100;//体力上限を減らすゲージ
    static int LifeGauge = 100;//体力

    // 重力
    static final float GRAVITY = -12;

    private RhythmGame mGame;

    int LengthOfSong;
    int RemainingTime;

    int ToSs;
    int ToSs2;
    int PumpkinTs;
    int SkeletonTs;
    int GhostTs;
    int BatTs;


    Sprite mBg;
    //カメラクラスとビューポートクラスをメンバ変数として定義
    OrthographicCamera mCamera;
    OrthographicCamera mGuiCamera;

    FitViewport mViewPort;
    FitViewport mGuiViewPort;

    Random mRandom;//乱数を取得するためのクラス
    List<ENote> mENote;
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
    BackButton mBackButton;
    Player mPlayer;
    Message mMessage;
    Message2 mMessage2;

    AttackLine mAttackLine;
    JumpLine mJumpLine;
    Star mStar;
    Bone mBone;
    AttackEffect mAttackEffect;

    ActionBack mActionBack;
    ButtonBack mButtonBack;
    RFrame mRFrame;


    //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    List<Bat> mBat;
    List<Skeleton> mSkeleton;
    List<Ghost> mGhost;
    List<Pumpkin> mPumpkin;
    List<Tree> mTree;
    List<Tree2> mTree2;
    List<Grass> mGrass;
    List<Ground> mGround;

    EnemyCreate mEnemyCreate;

    int mGameState;//ゲームの状態を保持
    Vector3 mTouchPoint; // タッチされた座標を保持するメンバ変数
    BitmapFont mFont; // Bitmapフォントの使用
    int mScore; // スコアを保持するメンバ変数
    int mHighScore; // ハイスコアを保持するメンバ変数
    float musictime = 0.00f;//再生時間を入れる変数
    Preferences mPrefs; // データを永続化させるためのPreferenceをメンバ変数に定義
    boolean b = false;

    int VCounter;
    //ボタンがタッチされているかaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    boolean tb1;
    boolean tb2;
    boolean tb3;
    boolean tb4;
    boolean tb5;
    boolean etb;
    boolean VCswitch = true;

    //ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp
    //boolean b = true;


    //リストの数を入れる
    int en=0;//エネルギーノーツを取り出すときに使用

    //int FearGauge = 100;
    //int LifeGauge = 100;//体力

    int n = 0;
    int nn = 0;

    int STAGENo;

    int end = 0;
    int ENEMY_NUMBER = 0;//敵を選ぶための数字
    int createpumpkin = 0;//かぼちゃ出すときに加算する
    int createskeleton = 0;
    int createghost = 0;
    int createbat = 0;

    //float[] ToS = {5.20f,6.90f};//再生時間に応じて音を鳴らす、どのタイミングかを入れる
    ArrayList<Float> ToS;
    ArrayList<Float> ToS2;
    ArrayList<Float> PumpkinT;
    ArrayList<Float> SkeletonT;
    ArrayList<Float> GhostT;
    ArrayList<Float> BatT;
    //ToS.add(10.2f);
    //String stringFormat;

    //float f = 2.7f;

    Music playingmusic;


    //効果音の準備
    Sound hitsound2 = Gdx.audio.newSound(Gdx.files.internal("hitsound.mp3"));
    Sound hitsound = Gdx.audio.newSound(Gdx.files.internal("piano chord2.mp3"));
    Sound getstarsound = Gdx.audio.newSound(Gdx.files.internal("getstarsound.mp3"));
    Sound attacksound = Gdx.audio.newSound(Gdx.files.internal("attack1.mp3"));
    Sound jumpsound = Gdx.audio.newSound(Gdx.files.internal("jump01.mp3"));



    public GameScreen(RhythmGame game,int stage) {

        mGame = game;
        ToS = new ArrayList<Float>();
        ToS2 = new ArrayList<Float>();
        PumpkinT = new ArrayList<Float>();
        SkeletonT = new ArrayList<Float>();
        GhostT = new ArrayList<Float>();
        BatT = new ArrayList<Float>();
        //ToS.add(1.168f);
//音楽の準備





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
        mENote = new ArrayList<ENote>();
        mNote2 = new ArrayList<Note2>();
        mGameState = GAME_STATE_READY;
        mTouchPoint = new Vector3();
        mFont = new BitmapFont(Gdx.files.internal("roundfont.fnt"), Gdx.files.internal("roundfont.png"), false); // フォントファイルの読み込み
        mFont.getData().setScale(0.2f);// フォントサイズも指定
        mScore = 0;
        mHighScore = 0;
        mSkeleton = new ArrayList<Skeleton>();
        mGhost = new ArrayList<Ghost>();
        mPumpkin = new ArrayList<Pumpkin>();
        mBat = new ArrayList<Bat>();
        mTree = new ArrayList<Tree>();
        mTree2 = new ArrayList<Tree2>();
        mGrass = new ArrayList<Grass>();
        mGround = new ArrayList<Ground>();
        // ハイスコアをPreferencesから取得する
        mPrefs = Gdx.app.getPreferences("jp.toteto.a1293.game.rhythmgame");//Preferencesの取得

        //mHighScore = mPrefs.getInteger("HIGHSCORE", 0);//第2引数はキーに対応する値がなかった場合に返ってくる値（初期値）

        switch (stage){
            case 1:
                playingmusic = Gdx.audio.newMusic(Gdx.files.internal("Satie-Gymnopedies.mp3"));
                mHighScore = mPrefs.getInteger("HIGHSCORE1", 0);
                break;
            case 2:
                playingmusic = Gdx.audio.newMusic(Gdx.files.internal("Satie-Jeteveux.mp3"));
                mHighScore = mPrefs.getInteger("HIGHSCORE2", 0);
                break;
            case 3:
                playingmusic = Gdx.audio.newMusic(Gdx.files.internal("Satie-Vexations.mp3"));
                mHighScore = mPrefs.getInteger("HIGHSCORE3", 0);
                playingmusic.setLooping(true);//音楽はループ
                break;
        }

        createStage(stage);
        //オブジェクト配置するcreateStageメソッドを呼び出す

    }



    //描画を行うレンダーメソッド
    @Override
    public void render(float deltaTime) {
        // それぞれの状態をアップデートする
        update(deltaTime);


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

        //aniani

        //aniani

        // 原点は左下
        mBg.setPosition(mCamera.position.x - CAMERA_WIDTH / 2, mCamera.position.y - CAMERA_HEIGHT / 2);
        mBg.draw(mGame.batch);

        // Note,リストで保持しているので順番に取り出し
        for (int i = 0; i < mNote.size(); i++) {
            mNote.get(i).draw(mGame.batch);
        }
        // Note2,リストで保持しているので順番に取り出し
        for (int i = 0; i < mNote2.size(); i++) {
            mNote2.get(i).draw(mGame.batch);
        }
        // ENote,リストで保持しているので順番に取り出し
        for (int i = 0; i < mENote.size(); i++) {
            mENote.get(i).draw(mGame.batch);
        }

        // 棒の表示
        mBar.draw(mGame.batch);
        // mIBar.draw(mGame.batch);
        //mIBar1.draw(mGame.batch);
        //mIBar2.draw(mGame.batch);
        //デッドラインの表示
        // mDeadLine.draw(mGame.batch);

        mButtonBack.draw(mGame.batch);
        // ボタンの表示
        mButton1.draw(mGame.batch);
        mButton2.draw(mGame.batch);
        mButton3.draw(mGame.batch);
        mButton4.draw(mGame.batch);
        mActionBack.draw(mGame.batch);

        for (int i = 0; i < mTree2.size(); i++) {
            mTree2.get(i).draw(mGame.batch);
        }

        // Treeリストで保持しているので順番に取り出し
        for (int i = 0; i < mTree.size(); i++) {
            mTree.get(i).draw(mGame.batch);
        }

        for (int i = 0; i < mGround.size(); i++) {
            mGround.get(i).draw(mGame.batch);
        }
        mJumpLine.draw(mGame.batch);
        mBone.draw(mGame.batch);
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

        mAttackLine.draw(mGame.batch);
        mStar.draw(mGame.batch);


        // Bat,リストで保持しているので順番に取り出し
        for (int i = 0; i < mBat.size(); i++) {
            mBat.get(i).draw(mGame.batch);
        }
//アニメーション

        mPlayer.draw(mGame.batch);

        mAttackEffect.draw(mGame.batch);

        // mPlayer.draw(animation.getKeyFrame(elapsedTime,true), pos.x, pos.y, width, height);
//アニメーション
        // Grassリストで保持しているので順番に取り出し
        for (int i = 0; i < mGrass.size(); i++) {
            mGrass.get(i).draw(mGame.batch);
        }

        mRFrame.draw(mGame.batch);

        mBackButton.draw(mGame.batch);


        //ライフゲージ周り
        mGaugeBarBack.draw(mGame.batch);
        mLGaugeBar.draw(mGame.batch);
        mFGaugeBar.draw(mGame.batch);

        mMessage.draw(mGame.batch);
        mMessage2.draw(mGame.batch);

        mGame.batch.end();

        // スコア表示
        mGuiCamera.update();
        mGame.batch.setProjectionMatrix(mGuiCamera.combined);
        //↑はカメラの座標を計算しスプライト表示に反映させるのに必要
        mGame.batch.begin();

        //drawメソッドで描画第1引数にSprteBatch、第2引数に表示されたい文字列、第3引数にx座標、第4引数にy座標
        if (STAGENo == 3){
            mFont.draw(mGame.batch, "Vexations mode: " + VCounter + "/840", 60, GUI_HEIGHT - 60);
        }
        //mFont.draw(mGame.batch, "Music: " + musictime, 16, GUI_HEIGHT - 55);
        //mFont.draw(mGame.batch, "Score: " + mScore + "FG: " + GameOverCounter + FearGauge + "Life: " + LifeGauge , 16, GUI_HEIGHT - 35);
        if (STAGENo == 1 || STAGENo ==2){mFont.draw(mGame.batch, ""+ RemainingTime, GUI_WIDTH/2 - 20, GUI_HEIGHT - 10);
        }
        mFont.draw(mGame.batch, "Score" + mScore, 16, GUI_HEIGHT - 15);

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
    private void createStage(int stage) {
        STAGENo = stage;
        TimingList(stage);
        RemainingTime =  LengthOfSong -1;

        float x = 0;
        float trees = 0;
        float grasses = 0;
        float Gs = 0;
        LifeGauge = 100;
        FearGauge = 100;

        ToSs = ToS.size();
        ToSs2 = ToS2.size();
        PumpkinTs = PumpkinT.size();
        SkeletonTs = SkeletonT.size();
        GhostTs = GhostT.size();
        BatTs = BatT.size();

        //ボタン
        button3 = new Texture("buttonsp.png");
        TextureRegion[] [] button3Frames = TextureRegion.split(button3,128,128);
        button3animationFrames = new TextureRegion[12];
        int b3index = 0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 3; j++){
                button3animationFrames[b3index++] = button3Frames[j][i];
            }
        }
        //playeranimation = new Animation<TextureRegion>(1f/8f,playeranimationFrames);
        button3animation = new Animation<TextureRegion>(1f/30f,button3animationFrames);


        Texture darkstar = new Texture("darkstar.png");
        TextureRegion darkstarTexture = new TextureRegion(darkstar,0, 0, 128, 128);

        Texture notet = new Texture("note.png");
        TextureRegion noteTexture = new TextureRegion(notet,0, 0, 128, 128);

        Texture pnote = new Texture("pnote.png");
        TextureRegion pnoteTexture = new TextureRegion(pnote,0, 0, 128, 128);

        Texture player = new Texture("majo.png");
        TextureRegion playerTexture = new TextureRegion(player,0, 64, 22, 32);

        //Texture ufoTexture = new Texture("ufo.png");

        Texture exitbutton = new Texture("ExitButton.png");
        TextureRegion messageTexture = new TextureRegion(exitbutton,0, 430, 512, 30);
        TextureRegion message2Texture = new TextureRegion(exitbutton,175, 370, 143, 24);
        //TextureRegion exitbuttonTexture = new TextureRegion(exitbutton,0, 0, 512, 310);

        Texture backbutton = new Texture("present.png");
        TextureRegion backbuttonTexture = new TextureRegion(backbutton, 812, 0, 212, 160);

        Texture bar = new Texture("bar.png");
        TextureRegion barTexture = new TextureRegion(bar,0,845,1024,171);

        Texture ibar = new Texture("ibar.png");
        TextureRegion ibarTexture = new TextureRegion(ibar,0,845,1024,171);

        Texture gaugebarback = new Texture("gaugeback.png");
        TextureRegion gaugebarbackTexture = new TextureRegion(gaugebarback,0, 0, 256, 256);

        Texture lgaugebar = new Texture("lgauge.png");
        TextureRegion lgaugebarTexture = new TextureRegion(lgaugebar,0, 0, 256, 256);

        Texture fgaugebar = new Texture("fgauge.png");
        TextureRegion fgaugebarTexture = new TextureRegion(fgaugebar,0, 0, 256, 256);

        Texture button1 = new Texture("button1a.png");
        TextureRegion button1Texture = new TextureRegion(button1,0, 0, 128, 128);

        Texture button2 = new Texture("button2a.png");
        TextureRegion button2Texture = new TextureRegion(button2,0, 0, 128, 128);

        //Texture button3bTexture = new Texture("button3b.png");


        Texture buttonback = new Texture("BB.png");
        TextureRegion buttonbackTexture = new TextureRegion(buttonback,0, 0, 90, 256);

        Texture actionback = new Texture("actionback.png");
        TextureRegion actionbackTexture = new TextureRegion(actionback,0, 0, 1024, 304);


        Texture attackline = new Texture("mahojin.png");
        TextureRegion attacklineTexture = new TextureRegion(attackline,0, 0, 128, 128);

        Texture attackeffect = new Texture("mahojin2.png");
        TextureRegion attackeffectTexture = new TextureRegion(attackeffect,0, 0, 128, 128);

        Texture jumpline = new Texture("up.png");
        TextureRegion jumplineTexture = new TextureRegion(jumpline,0, 0, 128, 128);

        Texture batt = new Texture("Bat.png");
        TextureRegion batTexture = new TextureRegion(batt,0, 0, 21, 16);

        Texture skeletont = new Texture("skeleton.png");
        TextureRegion skeletonTexture = new TextureRegion(skeletont,0, 34, 24, 32);

        Texture ghostt = new Texture("ghost.png");
        TextureRegion ghostTexture = new TextureRegion(ghostt,0, 2, 24, 32);

        Texture pumpkint = new Texture("pumpkin.png");
        TextureRegion pumpkinTexture = new TextureRegion(pumpkint,0, 34, 32, 27);

        Texture frame = new Texture("frame.png");
        TextureRegion frameTexture = new TextureRegion(frame,0, 0, 1024, 304);

        Texture treet = new Texture("f_tree_shadow70.png");
        TextureRegion treeTexture = new TextureRegion(treet,0,0,512,512);

        Texture tree2t = new Texture("tree.png");
        TextureRegion tree2Texture = new TextureRegion(tree2t,0,0,512,512);

        Texture grasst = new Texture("kusa.png");
        //TextureRegion grassTexture = new TextureRegion(grasst,0,845,1024,171);
        TextureRegion grassTexture = new TextureRegion(grasst,0,0,1024,47);

        Texture jiment = new Texture("jimen.png");
        TextureRegion groundTexture = new TextureRegion(jiment,0,896,1024,128);

        Texture bone = new Texture("hone.png");
        TextureRegion boneTexture = new TextureRegion(bone,0, 0, 128, 128);

        // StepとStar、DarkStar、Enemyをゴールの高さまで配置していく
        // float y = 0;
        // 棒を配置
        mBar = new Bar(barTexture);
        mBar.setPosition(3.1f, 0);

        mIBar = new iBar(ibarTexture);
        mIBar.setPosition(3, 0);
        mIBar1 = new iBar1(ibarTexture);
        //元の位置mIBar1.setPosition(2.79f, 0);
        mIBar1.setPosition(2.89f, 0);
        mIBar2 = new iBar2(ibarTexture);
        //元の位置mIBar2.setPosition(3.3f, 0);
        mIBar2.setPosition(3.11f, 0);
//左右0.1までのずれ許容
        // デッドラインを配置
        mDeadLine = new DeadLine(ibarTexture);
        mDeadLine.setPosition(1.3f, 0);


        mGaugeBarBack = new GaugeBarBack(gaugebarbackTexture);
        mGaugeBarBack.setPosition(0, 4.5f);

        mFGaugeBar = new FGaugeBar(fgaugebarTexture);
        mFGaugeBar.setPosition(0, 5.0f);

        mLGaugeBar = new LGaugeBar(lgaugebarTexture);
        mLGaugeBar.setPosition(0, 5.0f);

        // アクション部背景を配置
        mActionBack = new ActionBack(actionbackTexture, 0, 0, 1024, 304);
        mActionBack.setPosition(0, 4.5f);

        // 警告フレームを配置
        mRFrame = new RFrame(frameTexture);
        mRFrame.setPosition(0, 4.5f);
        mRFrame.setAlpha(0);

        // 攻撃エリア表示を配置
        mAttackLine = new AttackLine(attacklineTexture, 0, 0, 128, 128);
        mAttackLine.setPosition(8.2f, 5.0f);

        mJumpLine = new JumpLine(jumplineTexture);
        mJumpLine.setPosition(0.6f, 5.3f);

        mStar = new Star(darkstarTexture);
        mStar.setPosition(6.8f, 10);

        mBone = new Bone(boneTexture);
        mBone.setPosition(8.0f, 5.5f);

// Playerを配置
        mPlayer = new Player(playerTexture);

        mAttackEffect = new AttackEffect(attackeffectTexture, 0, 0, 128, 128);

        // ボタン１を配置
        mButton1 = new Button1(button1Texture);
        mButton1.setPosition(14, 2.25f);

        // ボタン2を配置
        mButton2 = new Button2(button2Texture);
        mButton2.setPosition(14, 0);

        // ボタン3を配置
        mButton3 = new Button3(button3animation.getKeyFrame(elapsedTime,true));
        mButton3.setPosition(0, 2);

        // ボタン4を配置
        mButton4 = new Button4(button3animation.getKeyFrame(elapsedTime,true));
        mButton4.setPosition(0, 0.1f);

        mButtonBack = new ButtonBack(buttonbackTexture);
        mButtonBack.setPosition(0, 0);

        while (end < ToSs || end < ToSs2) {

            Note note = new Note(noteTexture);
            //場所を決める
            note.setPosition(30.0f, 0.85f);
            mNote.add(note);

            Note2 note2 = new Note2(noteTexture);
            //場所を決める
            note2.setPosition(30.0f, 2.85f);
            mNote2.add(note2);

            ENote enote = new ENote(pnoteTexture);
            //ランダムで場所を決める
            if (MathUtils.random(0, 1) == 1) {
                enote.setPosition(30.0f, 2.85f);
            }else{
                enote.setPosition(30.0f, 0.85f);
            }
            mENote.add(enote);

            Skeleton skeleton = new Skeleton(skeletonTexture);
            //場所を決める
            skeleton.setPosition(18, 5.2f);
            mSkeleton.add(skeleton);

            Ghost ghost = new Ghost(ghostTexture);
            //場所を決める
            ghost.setPosition(16.8f, 5.2f);
            mGhost.add(ghost);

            Pumpkin pumpkin = new Pumpkin(pumpkinTexture);
            //場所を決める
            pumpkin.setPosition(16.8f, 5.2f);
            mPumpkin.add(pumpkin);

            Bat bat = new Bat(batTexture);
            //ランダムで場所を決める
            if (MathUtils.random(1, 3) == 2) {
                bat.setPosition(37.76f, 7.0f);
            }else {
                bat.setPosition(37.76f, 5.5f);
            }
            mBat.add(bat);

            end++;

        }
        while ( x < 24) {

            Tree tree = new Tree(treeTexture);
            tree.setPosition(x, 5.3f);
            mTree.add(tree);
            x += mRandom.nextFloat() * 10;
        }

        while ( trees < 24) {
            Tree2 tree2 = new Tree2(tree2Texture);
            tree2.setPosition(trees, 5.3f);
            mTree2.add(tree2);
            trees += mRandom.nextFloat() * 10;
        }
/*
        while ( grasses < 24) {
            Grass grass = new Grass(grassTexture);
            grass.setPosition(grasses, 4.5f);
            mGrass.add(grass);
            grasses += 6;
        }
*/
        while ( grasses < 48) {
            Grass grass = new Grass(grassTexture);
            grass.setPosition(grasses, 4.5f);
            mGrass.add(grass);
            grasses += 24;
        }
/*
        while ( Gs <= 24) {

            Ground ground = new Ground(groundTexture);
            ground.setPosition(Gs, 4.5f);
            mGround.add(ground);
            Gs += 8;
        }
        */
        while ( Gs <= 24) {
            Ground ground = new Ground(groundTexture);
            ground.setPosition(Gs, 4.5f);
            mGround.add(ground);
            Gs += 12;
        }

        mBackButton = new BackButton(backbuttonTexture);
        mBackButton.setPosition(14.6f, 8.0f);

        mMessage = new Message(messageTexture);
        mMessage.setPosition(16, 4.0f);
        mMessage2 = new Message2(message2Texture);
        mMessage2.setPosition(6.5f, 3.4f);//変える

    }

    // それぞれのオブジェクトの状態をアップデートする
    private void update(float delta) {


        switch (mGameState) {
            case GAME_STATE_READY:
                updateReady(delta);
                break;
            case GAME_STATE_PLAYING:
                updatePlaying(delta);
                break;
            case GAME_STATE_GAMEOVER:
                updateGameOver(delta);
                break;
            case GAME_STATE_GAMECREAR:
                updateGameCrear(delta);
                break;
        }
    }

    //タッチされたら状態をゲーム中であるGAME_STATE_PLAYINGに変更
    private void updateReady(float delta) {
        screen2sTimer += delta;
        if (screen2sTimer>2){
            screen2sTimer = 0;
        }
        mMessage2.update(screen2sTimer);
        if (Gdx.input.justTouched()) {
            mBackButton.setAlpha(0.4f);
            screen2sTimer = 0;
            mMessage2.hide();
            mGameState = GAME_STATE_PLAYING;
        }
    }

    //aniani

    //aniani
    private void updatePlaying(float delta) {
//アニメーション
        screen12sTimer += delta;


        if (screen12sTimer>=6 && mGrass.get(0).getX()<-1){
            mGrass.get(0).setX(24);
            mGround.get(0).setX(12);
        }
        if (screen12sTimer>=6 && mGrass.get(1).getX()<-1){
            mGrass.get(1).setX(24);
            mGround.get(1).setX(12);
        }
        if (screen12sTimer > 6){
            screen12sTimer = 0;
        }

        screen1sTimer += delta;
        if (screen1sTimer >1){
            screen1sTimer =0;
            RemainingTime -= 1;
        }
        //float deltaTime = Gdx.graphics.getDeltaTime();
        mPlayer.update(delta,screen1sTimer);
        elapsedTime += Gdx.graphics.getDeltaTime();//時間積み上げ
        //anitimer +=delta;
        //if (mPlayer.jumpstate == 0) {
        //mPlayer = new Player(playeranimation.getKeyFrame(elapsedTime, true));//テクスチャをplayerクラスに渡す
        //  playeranimation = new Animation<TextureRegion>(1f / 8f, playeranimationFrames);
        //}
        button3animation = new Animation<TextureRegion>(1f / 12f, button3animationFrames);

        mButton4 = new Button4(button3animation.getKeyFrame(elapsedTime, true));
        mButton3 = new Button3(button3animation.getKeyFrame(elapsedTime, true));
        //アニメーション
        if (!playingmusic.isPlaying()) {
            playingmusic.play();//音楽を再生
        }

        mAttackLine.update(delta);
        mStar.update(delta);
        mBone.update(delta);
        mAttackEffect.update(delta);
        for (int i = 0; i < mTree2.size(); i++) { //ここで一回のみの動作に
            mTree2.get(i).update(delta);
        }

        for (int i = 0; i < mTree.size(); i++) { //ここで一回のみの動作に
            mTree.get(i).update(delta);
        }

        for (int i = 0; i < mGround.size(); i++) { //ここで一回のみの動作に
            mGround.get(i).update(delta);
        }

        for (int i = 0; i < mGrass.size(); i++) { //ここで一回のみの動作に
            mGrass.get(i).update(delta);
        }

        musictime = playingmusic.getPosition();//再生時間取得
        //ボタンがタッチされているか
        tb1 = false;
        tb2 = false;
        tb3 = false;
        tb4 = false;
        mAttackLine.unpush();
        mJumpLine.unpush();
        
        for (int i = 0; i < 5; i++) { // 20 is max number of touch points
            if (Gdx.input.isTouched(i)) {
                //test
                Rectangle rightu = new Rectangle(GUI_WIDTH - 70, 72, GUI_WIDTH, 72);//ボタン1
                Rectangle rightd = new Rectangle(GUI_WIDTH - 70, 0, GUI_WIDTH, 72);//ボタン2
                Rectangle leftu = new Rectangle(0, 72, 70, 72);//ボタン３
                Rectangle leftd = new Rectangle(0, 0, 70, 72);//ボタン４
                Rectangle exit = new Rectangle(467.2f, 246.4f, 83, 51);
                //test
                final int iX = Gdx.input.getX(i);
                final int iY = Gdx.input.getY(i);

                mGuiViewPort.unproject(mTouchPoint.set(iX, iY, 0));
                tb1 = tb1 || (rightu.contains(mTouchPoint.x, mTouchPoint.y)); // Touch coordinates are in screen space
                tb2 = tb2 || (rightd.contains(mTouchPoint.x, mTouchPoint.y));
                tb3 = tb3 || (leftu.contains(mTouchPoint.x, mTouchPoint.y)); // Touch coordinates are in screen space
                tb4 = tb4 || (leftd.contains(mTouchPoint.x, mTouchPoint.y));
                tb5 = tb5 || (exit.contains(mTouchPoint.x, mTouchPoint.y));
            }
        }
        if (tb1) {
            mJumpLine.update(delta);
            mJumpLine.push();
            //animation = new Animation<TextureRegion>(1f/8f,jumpingFrame);
            //animation = new Animation<TextureRegion>(1f/8f,playeranimationFrames);
        }
        if (tb2) {
            mAttackLine.push();
            //animation = new Animation<TextureRegion>(1f/8f,playeranimationFrames);
        }
        if (tb5) {
            mBackButton.Push();
            Dispose();
            if (mGame.mRequestHandler != null && MathUtils.random(1, 3) == 2) {//表示するかどうか
                mGame.mRequestHandler.showAdsi(true); // 広告表示
            }

            mGame.setScreen(new StartScreen(mGame));
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
                mPumpkin.get(i).update(delta,screen1sTimer);
            }
            if (playingmusic.getPosition() > PumpkinT.get(createpumpkin)) {//指定時間を超えた瞬間にカウンター加算しノーツを出す
                createpumpkin++;
                en++;
            }
        }
        //骸骨出すタイミングで動作
        if (createskeleton < SkeletonTs) {
            for (int i = 0; i < createskeleton; i++) {
                mSkeleton.get(i).update(delta,screen1sTimer);
                if (mSkeleton.get(i).getX() < 7.5f) {
                    mBone.threw = 1;
                }
            }
            if (playingmusic.getPosition() > SkeletonT.get(createskeleton)) {//指定時間を超えた瞬間にカウンター加算しノーツを出す
                createskeleton++;
                en++;
            }
        }
        //幽霊出すタイミングで動作
        if (createghost < GhostTs) {
            for (int i = 0; i < createghost; i++) {
                mGhost.get(i).update(delta,screen1sTimer);
            }
            if (playingmusic.getPosition() > GhostT.get(createghost)) {//指定時間を超えた瞬間にカウンター加算しノーツを出す
                createghost++;
                en++;
            }
        }
        //コウモリ出すタイミングで動作
        if (createbat < BatTs) {
            for (int i = 0; i < createbat; i++) {
                mBat.get(i).update(delta,screen1sTimer);
                //mENote.get(en).update(delta);
                //en++;
            }
            if (playingmusic.getPosition() > BatT.get(createbat)) {//指定時間を超えた瞬間にカウンター加算しノーツを出す
                createbat++;
                en++;
            }
        }

        //すべての敵タイミングで動作
        if (createpumpkin < PumpkinTs||createghost < GhostTs||createskeleton < SkeletonTs||createbat < BatTs) {
            for (int i = 0; i < en; i++) {
                mENote.get(i).update(delta);
            }
        }


        mLGaugeBar.GetDamage();
        mFGaugeBar.GetDamage();
        mActionBack.Darker();
        mButton1.Darker(screen1sTimer,tb1);
        mButton2.Darker(screen1sTimer,tb2);


        if (LifeGauge > FearGauge){
            LifeGauge = FearGauge;
        }

        if (LifeGauge <= 15){
            mRFrame.update(delta);
        }

        if (STAGENo==3){
            if (musictime <1 && VCswitch) {
                VCounter++;
                VCswitch = false;
                if (LifeGauge<50){
                    FearGauge += 10;
                    LifeGauge += 10;
                }
            }
            if (musictime > 91){
                for (int i = 0; i < ToSs; i++) {
                    mNote.get(i).loop();
                    n = 0;
                }
                for (int i = 0; i < ToSs2; i++) {
                    mNote2.get(i).loop();
                    nn = 0;
                }
                VCswitch = true;
            }
        }
        checkCollision();

        // ゲームオーバーか判断する
        checkGameOver();

    }

    //あたり判定の処理
    private void checkCollision() {


        // ノーツとの当たり判定
        for (int i = 0; i < mNote.size(); i++) {
            Note note = mNote.get(i);
            if (tb4) {mButton4.Push();
                //ボタン３が押されたときに上ラインのあたり判定
                if (note.mState == 1) {
                    continue;
                    //消えてるのには反応しない
                }
                if (mIBar.getBoundingRectangle().overlaps(note.getBoundingRectangle())) {
                    //testa
                    /*if (tb1 && !tb2){
                        mPlayer.jumpstate = 1;
                    }
                    if (tb2 && !tb1){
                        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        mStar.jumpstate = 1;
                        //ボタン2押しながら得点得た時の動作
                    }*/
                    if (mIBar1.getBoundingRectangle().overlaps(note.getBoundingRectangle())
                            && mIBar2.getBoundingRectangle().overlaps(note.getBoundingRectangle())) {
                        mScore = mScore + 5; //スコアに加算
                        if (mScore > mHighScore) { //ハイスコアを超えた場合
                            mHighScore = mScore; //今の点数をハイスコアに
                            //ハイスコアをPreferenceに保存する
                            //mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                            //mPrefs.flush(); // 値を永続化するのに必要
                        }
                        note.get();//消す
                    } else {
                        mScore = mScore + 3; //スコアに加算
                        if (mScore > mHighScore) { //ハイスコアを超えた場合
                            mHighScore = mScore; //今の点数をハイスコアに
                            //ハイスコアをPreferenceに保存する
                            //mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                            //mPrefs.flush(); // 値を永続化するのに必要
                        }
                        note.get();//消す
                        break;
                    }
                } else {
                    if (mIBar1.getBoundingRectangle().overlaps(note.getBoundingRectangle())
                            || mIBar2.getBoundingRectangle().overlaps(note.getBoundingRectangle())) {
                        hitsound.play(0.3f);//衝突音
                        FearGauge -= 1;//プレーヤーダメージ
                        note.get();//消す
                    }
                }

                //タイミング判定
            }
            if (note.mState == 0 && mDeadLine.getBoundingRectangle().overlaps(note.getBoundingRectangle())) {
                //押されなかった場合（ダメージを追加予定
                hitsound.play(0.3f);//衝突音
                FearGauge -= 1;//プレーヤーダメージ
                note.get();//消す
            }
        }
        for (int i = 0; i < mNote2.size(); i++) {
            Note2 note2 = mNote2.get(i);
            if (tb3) {
                mButton3.Push();
                //ボタン３が押されたときに上ラインのあたり判定
                if (note2.mState == 1) {
                    continue;
                    //消えてるのには反応しない
                }

                if (mIBar.getBoundingRectangle().overlaps(note2.getBoundingRectangle())) {
                    //testa
                    /*if (tb1 && !tb2){
                        mPlayer.jumpstate = 1;
                    }
                    if (tb2 && !tb1){
                        //ボタン2押しながら得点得た時の動作
                        mStar.jumpstate = 1;
                    }*/
                    if (mIBar1.getBoundingRectangle().overlaps(note2.getBoundingRectangle())
                            && mIBar2.getBoundingRectangle().overlaps(note2.getBoundingRectangle())) {
                        mScore = mScore + 5; //スコアに加算
                        if (mScore > mHighScore) { //ハイスコアを超えた場合
                            mHighScore = mScore; //今の点数をハイスコアに
                            //ハイスコアをPreferenceに保存する
                            //mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                            //mPrefs.flush(); // 値を永続化するのに必要
                        }
                        note2.get();//消す
                    } else {
                        mScore = mScore + 3; //スコアに加算
                        if (mScore > mHighScore) { //ハイスコアを超えた場合
                            mHighScore = mScore; //今の点数をハイスコアに
                        }
                        note2.get();//消す

                        break;
                    }
                } else {
                    if (mIBar1.getBoundingRectangle().overlaps(note2.getBoundingRectangle())
                            || mIBar2.getBoundingRectangle().overlaps(note2.getBoundingRectangle())) {
                        hitsound.play(0.3f);//衝突音
                        FearGauge -= 2;//プレーヤーダメージ
                        note2.get();//消す
                    }
                }
                //タイミング判定
            }
            if (note2.mState == 0 && mDeadLine.getBoundingRectangle().overlaps(note2.getBoundingRectangle())) {
                //押されなかった場合（ダメージを追加予定
                hitsound.play(0.3f);//衝突音
                FearGauge -= 2;//プレーヤーダメージ
                note2.get();//消す
            }
        }

        for (int i = 0; i < mENote.size(); i++) {
            ENote enote = mENote.get(i);
            if (enote.getY()>2) {
                etb = tb3;
            }else{
                etb = tb4;
            }
            if (etb) {
                //ボタン３が押されたときに上ラインのあたり判定
                if (enote.mState == 1) {
                    continue;
                    //消えてるのには反応しない
                }

                if (mIBar.getBoundingRectangle().overlaps(enote.getBoundingRectangle())) {
                    //testa
                    if (tb1 && !tb2) {

                        //アニメ―ション
                        //animation = new Animation<TextureRegion>(1,jumpingFrame);
                        //mPlayer = new Player(animation.getKeyFrame(elapsedTime, true));//テクスチャをplayerクラスに渡す
                        jumpsound.play(0.3f);
                        mPlayer.jumpstate = 1;

                    }
                    if (tb2 && !tb1) {
                        //ボタン2押しながら得点得た時の動作
                        //animation = new Animation<TextureRegion>(1,attackFrame);
                        //mPlayer = new Player(animation.getKeyFrame(elapsedTime, true));//テクスチャをplayerクラスに渡す
                        //アニメ―ション
                        mStar.jumpstate = 1;
                        attacksound.play(0.2f);
                        mAttackEffect.threw = 1;
                    }
                    if (mIBar1.getBoundingRectangle().overlaps(enote.getBoundingRectangle())
                            && mIBar2.getBoundingRectangle().overlaps(enote.getBoundingRectangle())) {
                        mScore = mScore + 5; //スコアに加算
                        if (mScore > mHighScore) { //ハイスコアを超えた場合
                            mHighScore = mScore; //今の点数をハイスコアに
                            //ハイスコアをPreferenceに保存する
                            //mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                            //mPrefs.flush(); // 値を永続化するのに必要
                        }
                        enote.get();//消す
                    } else {
                        mScore = mScore + 3; //スコアに加算
                        if (mScore > mHighScore) { //ハイスコアを超えた場合
                            mHighScore = mScore; //今の点数をハイスコアに
                            //ハイスコアをPreferenceに保存する
                            //mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                            //mPrefs.flush(); // 値を永続化するのに必要
                        }
                        enote.get();//消す

                        break;
                    }
                } else {
                    if (mIBar1.getBoundingRectangle().overlaps(enote.getBoundingRectangle())
                            || mIBar2.getBoundingRectangle().overlaps(enote.getBoundingRectangle())) {
                        hitsound.play(0.3f);//衝突音
                        FearGauge -= 2;//プレーヤーダメージ
                        enote.get();//消す
                    }
                }
                //タイミング判定
            }
            if (enote.mState == 0 && mDeadLine.getBoundingRectangle().overlaps(enote.getBoundingRectangle())) {
                //押されなかった場合（ダメージを追加予定
                hitsound.play(0.3f);//衝突音
                FearGauge -= 2;//プレーヤーダメージ
                enote.get();//消す
            }
        }
        //かぼちゃ
        for (int i = 0; i < mPumpkin.size(); i++) {
            Pumpkin pumpkin = mPumpkin.get(i);
            if (mStar.getBoundingRectangle().overlaps(pumpkin.getBoundingRectangle())) {
                //攻撃当たった場合
                mScore = mScore + 5; //スコアに加算
                if (mScore > mHighScore) { //ハイスコアを超えた場合
                    mHighScore = mScore; //今の点数をハイスコアに
                }
                pumpkin.get();//消す
            }
            if (pumpkin.mState == 0 && mPlayer.getBoundingRectangle().overlaps(pumpkin.getBoundingRectangle())) {
                //キャラに当たった場合
                LifeGauge -= 5;
                hitsound2.play(0.2f);//衝突音
                pumpkin.get();//消す
            }
        }
        //骸骨
        for (int i = 0; i < mSkeleton.size(); i++) {
            Skeleton skeleton = mSkeleton.get(i);
            if (mStar.getBoundingRectangle().overlaps(skeleton.getBoundingRectangle())) {
                //攻撃当たった場合

                mScore = mScore + 5; //スコアに加算
                if (mScore > mHighScore) { //ハイスコアを超えた場合
                    mHighScore = mScore; //今の点数をハイスコアに
                }
                skeleton.get();//消す
            }
        }
        //幽霊
        for (int i = 0; i < mGhost.size(); i++) {
            Ghost ghost = mGhost.get(i);
            if (mStar.getBoundingRectangle().overlaps(ghost.getBoundingRectangle()) && ghost.getY() < 8) {
                //攻撃当たった場合
                mScore = mScore + 5; //スコアに加算
                if (mScore > mHighScore) { //ハイスコアを超えた場合
                    mHighScore = mScore; //今の点数をハイスコアに
                }
                ghost.get();//消す
            }
        }
        //コウモリ
        for (int i = 0; i < mBat.size(); i++) {
            Bat bat = mBat.get(i);
            if (bat.mState == 0 && mPlayer.getBoundingRectangle().overlaps(bat.getBoundingRectangle())) {
                //キャラに当たった場合
                LifeGauge -= 5;
                hitsound2.play(0.2f);//衝突音
                bat.get();//消す
            }
        }
        //敵の攻撃
        if (mPlayer.getBoundingRectangle().overlaps(mBone.getBoundingRectangle()) ){
            //攻撃当たった場合
            LifeGauge -= 5;
            hitsound2.play(0.2f);//衝突音
            mBone.get();//消す
        }
        //あたり判定ここまで
    }

    //ゲームオーバー時ResultScreenに遷移
    private void updateGameOver(float delta) {
        switch (STAGENo){
            case 1:
                mPrefs.putInteger("HIGHSCORE1", mHighScore);
                mPrefs.flush(); // 値を永続化するのに必要
                break;
            case 2:
                mPrefs.putInteger("HIGHSCORE2", mHighScore);
                mPrefs.flush(); // 値を永続化するのに必要
                break;
            case 3:
                mPrefs.putInteger("HIGHSCORE3", mHighScore);
                mPrefs.flush(); // 値を永続化するのに必要
                break;
        }
        screen2sTimer += delta;
        if (screen2sTimer>2){
            screen2sTimer = 0;
        }
        Dispose();

        mMessage.update(delta);
        mMessage2.update(screen2sTimer);

        if (Gdx.input.justTouched()) {
            mMessage2.hide();
        mGame.setScreen(new ResultScreen(mGame, mScore,STAGENo));
        }
    }

    private void Dispose() {
        playingmusic.dispose();//メモリ解放
        hitsound.dispose();//メモリ解放
        getstarsound.dispose();//メモリ解放
        attacksound.dispose();
        hitsound2.dispose();
        jumpsound.dispose();
    }

    //ゲームクリア時ClearrScreenに遷移
    private void updateGameCrear(float delta) {
        switch (STAGENo){
            case 1:
                mPrefs.putInteger("HIGHSCORE1", mHighScore);
                mPrefs.flush(); // 値を永続化するのに必要
                break;
            case 2:
                mPrefs.putInteger("HIGHSCORE2", mHighScore);
                mPrefs.flush(); // 値を永続化するのに必要
                break;
            case 3:
                mPrefs.putInteger("HIGHSCORE3", mHighScore);
                mPrefs.flush(); // 値を永続化するのに必要
                break;
        }
        screen2sTimer += delta;
        if (screen2sTimer>2){
            screen2sTimer = 0;
        }
        Dispose();
        mMessage.setRegion(0, 471, 512, 30);
        mMessage.update(delta);
        mMessage2.update(screen2sTimer);

        if (Gdx.input.justTouched()) {
            mMessage2.hide();
            mGame.setScreen(new ClearScreen(mGame, mScore,STAGENo,LifeGauge));
        }
    }

    private void checkGameOver() {
        //ここの時間で終了
        if (playingmusic.getPosition()>LengthOfSong) {
            playingmusic.dispose();//メモリ解放
            mGameState = GAME_STATE_GAMECREAR;
            //mGameState = GAME_STATE_GAMEOVER;
        }
        if (LifeGauge < 0) {
            Gdx.app.log("RhythmGame", "GAMEOVER");
            //gomusic.play();//ゲームオーバー画面の音楽再生
            playingmusic.dispose();//メモリ解放
            mGameState = GAME_STATE_GAMEOVER;
        }
        if (VCounter == 841){
            mGameState = GAME_STATE_GAMECREAR;
        }
    }
    private void TimingList(int stage) {
        if (stage == 1) {
            LengthOfSong =115;//66

            ToS.add(0.08f);
            ToS2.add(1.02f);
            ToS.add(2.97f);
            ToS2.add(3.9f);
            ToS2.add(5.845f);
            ToS.add(6.77f);
            ToS2.add(8.64f);
            ToS.add(9.56f);
            ToS2.add(11.46f);
            ToS.add(12.35f);
            ToS.add(13.27f);
            PumpkinT.add(14.18f);
            ToS2.add(15.09f);
            ToS.add(16.01f);
            ToS.add(16.93f);
            ToS2.add(17.85f);
            ToS.add(18.76f);
            SkeletonT.add(19.68f);
            ToS.add(20.58f);
            PumpkinT.add(22.41f);
            ToS.add(23.34f);
            ToS2.add(25.17f);
            BatT.add(26.06f);
            ToS.add(27.90f);
            BatT.add(28.81f);
            ToS.add(30.65f);
            ToS.add(31.55f);
            ToS2.add(33.39f);
            SkeletonT.add(34.29f);
            ToS.add(35.19f);
            ToS.add(36.11f);
            ToS.add(37.02f);
            ToS2.add(37.93f);
            SkeletonT.add(38.85f);
            ToS.add(39.77f);
            ToS2.add(40.68f);
            BatT.add(41.58f);
            GhostT.add(42.52f);
            ToS.add(44.33f);
            ToS2.add(45.25f);
            ToS.add(47.07f);
            PumpkinT.add(47.99f);
            ToS.add(49.80f);
            ToS2.add(50.71f);
            SkeletonT.add(52.54f);
            ToS.add(53.44f);
            BatT.add(55.25f);
            ToS2.add(56.14f);
            BatT.add(57.95f);
            ToS.add(58.86f);
            PumpkinT.add(59.77f);
            ToS.add(60.67f);
            ToS2.add(61.57f);
            BatT.add(62.48f);
            ToS2.add(63.39f);
            ToS.add(64.29f);
            ToS2.add(65.19f);
            BatT.add(66.09f);
            ToS.add(67.00f);
            PumpkinT.add(68.81f);
            ToS.add(69.73f);
            ToS2.add(70.63f);
            ToS.add(71.52f);
            ToS2.add(72.42f);
            BatT.add(73.33f);
            ToS.add(74.23f);
            ToS2.add(75.15f);
            SkeletonT.add(76.05f);
            ToS.add(76.95f);
            ToS.add(77.86f);
            ToS2.add(78.76f);
            ToS.add(79.66f);
            SkeletonT.add(80.57f);
            ToS.add(82.34f);
            BatT.add(83.30f);
            GhostT.add(84.19f);
            ToS.add(85.21f);
            PumpkinT.add(86.12f);
            ToS.add(87.95f);
            ToS2.add(88.86f);
            BatT.add(90.69f);
            ToS.add(91.60f);
            SkeletonT.add(92.51f);
            ToS2.add(93.44f);
            SkeletonT.add(94.34f);
            BatT.add(95.26f);
            GhostT.add(96.18f);
            ToS2.add(97.09f);
            BatT.add(97.99f);
            GhostT.add(98.92f);
            PumpkinT.add(99.90f);
            ToS.add(100.93f);
            BatT.add(102.12f);
            ToS2.add(105.27f);

            ToS.add(120f);
            ToS2.add(120f);
            PumpkinT.add(120f);
            SkeletonT.add(120f);
            BatT.add(120f);
            GhostT.add(120f);

        }
        if (stage == 2) {
            LengthOfSong =157;//66
            ToS.add(0.163f);
            ToS2.add(0.605f);
            BatT.add(1.024f);
            ToS.add(2.408f);
            ToS.add(3.401f);
            GhostT.add(3.932f);
            ToS.add(5.532f);
            ToS.add(6.392f);
            ToS2.add(7.535f);
            BatT.add(9.37f);
            ToS.add(10.579f);
            ToS2.add(11.31f);
            SkeletonT.add(11.67f);
            ToS.add(12.757f);
            ToS.add(13.848f);
            ToS2.add(14.902f);
            ToS.add(15.617f);
            BatT.add(15.983f);
            ToS2.add(17.389f);
            ToS.add(17.780f);
            GhostT.add(18.171f);
            ToS2.add(19.572f);
            ToS.add(19.944f);
            SkeletonT.add(20.30f);
            ToS2.add(21.689f);
            ToS2.add(22.07f);
            ToS.add(22.413f);
            ToS.add(23.10f);
            ToS.add(23.778f);
            ToS.add(24.488f);
            ToS2.add(25.537f);
            ToS.add(25.913f);
            ToS.add(26.294f);
            PumpkinT.add(26.708f);
            ToS.add(27.762f);
            ToS.add(28.450f);
            ToS.add(28.801f);
            ToS2.add(29.860f);
            ToS.add(30.913f);
            ToS2.add(31.957f);
            ToS2.add(32.654f);
            SkeletonT.add(32.988f);
            ToS2.add(34.055f);
            ToS.add(34.427f);
            ToS.add(34.813f);
            BatT.add(35.165f);
            ToS2.add(35.528f);
            ToS.add(35.871f);
            ToS.add(36.205f);
            ToS.add(36.544f);
            PumpkinT.add(36.878f);
            ToS.add(37.207f);
            ToS.add(37.550f);
            SkeletonT.add(37.875f);
            ToS.add(38.204f);
            ToS2.add(38.524f);
            GhostT.add(38.853f);
            ToS.add(39.234f);
            ToS.add(39.940f);
            ToS2.add(40.321f);
            ToS.add(40.683f);
            ToS.add(41.440f);
            ToS2.add(41.845f);
            ToS2.add(42.202f);
            ToS.add(42.593f);
            ToS.add(42.997f);
            ToS.add(43.425f);
            BatT.add(43.886f);
            ToS2.add(45.326f);
            ToS2.add(45.711f);
            SkeletonT.add(46.036f);
            ToS.add(47.438f);
            ToS2.add(47.800f);
            ToS2.add(48.195f);
            ToS.add(48.882f);
            ToS2.add(49.564f);
            PumpkinT.add(50.213f);
            ToS.add(51.210f);
            ToS.add(51.582f);
            ToS.add(51.925f);
            ToS2.add(52.278f);
            ToS.add(53.741f);
            ToS.add(54.103f);
            SkeletonT.add(54.451f);
            ToS.add(55.815f);
            ToS.add(56.182f);
            ToS2.add(56.568f);
            ToS.add(57.292f);
            ToS.add(57.974f);
            SkeletonT.add(58.623f);
            ToS2.add(59.644f);
            ToS.add(59.992f);
            ToS.add(60.378f);
            PumpkinT.add(60.754f);
            ToS.add(62.236f);
            ToS2.add(62.598f);
            SkeletonT.add(62.932f);
            ToS2.add(64.352f);
            ToS.add(64.729f);
            ToS.add(65.086f);
            ToS.add(65.745f);
            ToS.add(66.403f);
            GhostT.add(67.062f);
            ToS2.add(68.050f);
            ToS.add(68.416f);
            ToS.add(68.835f);
            BatT.add(69.183f);
            ToS.add(70.641f);
            ToS2.add(70.999f);
            SkeletonT.add(71.356f);
            ToS.add(72.814f);
            ToS.add(73.177f);
            ToS.add(73.544f);
            ToS2.add(73.920f);
            ToS.add(74.296f);
            PumpkinT.add(74.663f);
            SkeletonT.add(75.863f);
            ToS.add(76.366f);
            ToS2.add(76.940f);
            ToS2.add(77.551f);
            ToS.add(78.398f);
            ToS.add(79.466f);
            BatT.add(80.858f);
            ToS2.add(82.001f);
            ToS2.add(82.735f);
            SkeletonT.add(83.106f);
            ToS.add(84.179f);
            ToS2.add(85.265f);
            ToS.add(86.366f);
            ToS.add(87.081f);
            BatT.add(87.434f);
            ToS.add(88.915f);
            ToS.add(89.278f);
            SkeletonT.add(89.626f);
            ToS.add(91.032f);
            ToS.add(91.371f);
            PumpkinT.add(91.728f);
            ToS.add(93.139f);
            ToS.add(93.520f);
            ToS2.add(93.883f);
            ToS2.add(94.593f);
            ToS.add(95.317f);
            PumpkinT.add(96.023f);
            ToS.add(97.091f);
            ToS.add(97.486f);
            ToS2.add(97.862f);
            SkeletonT.add(98.229f);
            ToS2.add(99.254f);
            ToS.add(99.951f);
            SkeletonT.add(100.308f);
            ToS2.add(101.371f);
            ToS.add(102.425f);
            ToS.add(103.460f);
            ToS2.add(104.184f);
            PumpkinT.add(104.518f);
            ToS.add(105.586f);
            ToS.add(105.976f);
            ToS2.add(106.348f);
            ToS.add(106.691f);
            ToS.add(107.081f);
            BatT.add(107.354f);
            ToS.add(107.693f);
            ToS2.add(108.027f);
            SkeletonT.add(108.342f);
            ToS.add(108.657f);
            ToS.add(108.982f);
            ToS.add(109.325f);
            ToS2.add(109.659f);
            PumpkinT.add(109.984f);
            ToS2.add(110.308f);
            ToS.add(110.685f);
            ToS2.add(111.385f);
            ToS.add(111.715f);
            ToS.add(112.119f);
            BatT.add(112.919f);
            ToS.add(115.125f);
            SkeletonT.add(115.694f);
            ToS2.add(116.447f);
            ToS.add(117.086f);
            PumpkinT.add(117.646f);
            ToS2.add(118.408f);
            ToS.add(119.001f);
            ToS.add(119.744f);
            SkeletonT.add(120.431f);
            ToS2.add(121.023f);
            ToS.add(121.710f);
            ToS.add(122.383f);
            ToS.add(122.769f);
            BatT.add(123.088f);
            ToS.add(123.634f);
            ToS2.add(124.396f);
            ToS.add(124.961f);
            ToS2.add(125.600f);
            ToS2.add(126.362f);
            ToS.add(126.974f);
            ToS.add(127.435f);
            BatT.add(127.783f);
            ToS2.add(128.126f);
            ToS.add(128.470f);
            SkeletonT.add(128.813f);
            ToS2.add(129.142f);
            ToS.add(129.4f);
            ToS.add(129.829f);
            PumpkinT.add(130.215f);
            SkeletonT.add(131.198f);
            ToS.add(131.729f);
            ToS2.add(132.505f);
            BatT.add(133.150f);
            ToS.add(133.714f);
            ToS2.add(135.116f);
            PumpkinT.add(135.760f);
            ToS.add(136.442f);
            ToS.add(136.988f);
            BatT.add(137.661f);
            ToS2.add(138.056f);
            ToS.add(138.432f);
            ToS2.add(138.738f);
            BatT.add(139.100f);
            SkeletonT.add(139.655f);
            ToS.add(140.380f);
            ToS.add(140.963f);
            BatT.add(141.546f);
            ToS.add(142.936f);
            ToS.add(143.357f);
            PumpkinT.add(143.714f);
            ToS.add(144.096f);
            ToS.add(144.453f);
            SkeletonT.add(144.825f);
            ToS.add(145.191f);
            ToS.add(145.563f);
            ToS.add(145.944f);
            ToS2.add(146.410f);
            ToS2.add(147.129f);

            ToS.add(230f);
            ToS2.add(230f);
            PumpkinT.add(230f);
            SkeletonT.add(230f);
            BatT.add(230f);
            GhostT.add(230f);

        }
        if (stage == 3) {
            LengthOfSong =100;//66

            ToS.add(0.128f);
            ToS.add(1.965f);
            ToS2.add(2.822f);
            ToS.add(3.595f);
            ToS2.add(5.203f);
            ToS.add(6.953f);
            ToS2.add(7.738f);
            ToS.add(8.536f);
            ToS2.add(9.315f);
            ToS.add(10.132f);
            ToS.add(11.826f);
            ToS.add(13.502f);
            ToS2.add(14.288f);
            ToS.add(15.036f);
            ToS2.add(15.834f);
            ToS2.add(16.626f);
            ToS.add(18.314f);
            ToS.add(19.328f);
            ToS2.add(22.377f);
            ToS.add(24.097f);
            ToS2.add(24.925f);
            ToS.add(25.705f);
            ToS2.add(27.232f);
            ToS2.add(28.871f);
            ToS.add(29.663f);
            ToS2.add(30.442f);
            ToS2.add(31.246f);
            ToS.add(31.988f);
            ToS2.add(33.652f);
            ToS2.add(35.340f);
            ToS.add(36.194f);
            ToS2.add(36.998f);
            ToS.add(37.727f);
            ToS2.add(38.538f);
            ToS2.add(40.232f);
            ToS2.add(41.197f);
            ToS.add(45.223f);
            ToS.add(47.004f);
            ToS2.add(47.808f);
            ToS.add(48.594f);
            ToS2.add(50.220f);
            ToS.add(51.933f);
            ToS.add(52.713f);
            ToS.add(53.511f);
            ToS2.add(54.284f);
            ToS.add(55.081f);
            ToS2.add(56.714f);
            ToS.add(58.415f);
            ToS.add(59.219f);
            ToS2.add(60.023f);
            ToS.add(60.808f);
            ToS2.add(61.600f);
            ToS2.add(63.307f);
            ToS2.add(64.383f);
            ToS.add(67.537f);
            ToS.add(69.362f);
            ToS2.add(70.116f);
            ToS.add(70.895f);
            ToS.add(72.491f);
            ToS2.add(74.198f);
            ToS.add(75.027f);
            ToS.add(75.837f);
            ToS.add(76.604f);
            ToS.add(77.371f);
            ToS2.add(78.991f);
            ToS2.add(80.649f);
            ToS.add(81.471f);
            ToS2.add(82.250f);
            ToS2.add(83.023f);
            ToS2.add(83.827f);
            ToS.add(85.658f);
            ToS.add(86.697f);
            ToS2.add(100f);
            ToS.add(100f);

        }
    }

}
