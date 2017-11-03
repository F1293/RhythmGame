package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.badlogic.gdx.graphics.g2d.Animation;
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
    //ボタンがタッチされているかaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    boolean tb1;
    boolean tb2;
    boolean tb3;
    boolean tb4;
    boolean tb5;
    boolean etb;

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
    Sound hitsound = Gdx.audio.newSound(Gdx.files.internal("hitsound.mp3"));
    Sound fall = Gdx.audio.newSound(Gdx.files.internal("fall.mp3"));
    Sound jingle = Gdx.audio.newSound(Gdx.files.internal("jingle.mp3"));
    Sound getstarsound = Gdx.audio.newSound(Gdx.files.internal("getstarsound.mp3"));



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
        switch (stage){
            case 1:
                playingmusic = Gdx.audio.newMusic(Gdx.files.internal("Satie-Gymnopedies.mp3"));
                break;
            case 2:
                playingmusic = Gdx.audio.newMusic(Gdx.files.internal("Satie-Jeteveux.mp3"));
                break;
            case 3:
                playingmusic = Gdx.audio.newMusic(Gdx.files.internal("Satie-Vexations.mp3"));
                playingmusic.setLooping(true);//音楽はループ
                break;
        }




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
        mHighScore = mPrefs.getInteger("HIGHSCORE", 0);//第2引数はキーに対応する値がなかった場合に返ってくる値（初期値）

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
        //mFont.draw(mGame.batch, "HighScore: " + mHighScore, 16, GUI_HEIGHT - 15);
        //mFont.draw(mGame.batch, "Music: " + musictime, 16, GUI_HEIGHT - 55);
        //mFont.draw(mGame.batch, "Score: " + mScore + "FG: " + GameOverCounter + FearGauge + "Life: " + LifeGauge , 16, GUI_HEIGHT - 35);
        //mFont.draw(mGame.batch, "tb4.2.3.1" + tb4 + "." + tb2 + "." + tb3 + "." + tb1 + "." + mPlayer.jumpstate +mPlayer.stateTime, 16, GUI_HEIGHT - 75);
        mFont.draw(mGame.batch, screen2sTimer+ "fps", 16, GUI_HEIGHT - 15);

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
        TextureRegion grassTexture = new TextureRegion(grasst,0,845,1024,171);

        Texture jiment = new Texture("jimen.png");
        TextureRegion groundTexture = new TextureRegion(jiment,0,896,1024,128);

        Texture bone = new Texture("hone.png");
        TextureRegion boneTexture = new TextureRegion(bone,0, 0, 128, 128);

        // StepとStar、DarkStar、Enemyをゴールの高さまで配置していく
        // float y = 0;
        // 棒を配置
        mBar = new Bar(barTexture);
        mBar.setPosition(3.0f, 0);

        mIBar = new iBar(ibarTexture);
        mIBar.setPosition(3, 0);
        mIBar1 = new iBar1(ibarTexture);
        //元の位置mIBar1.setPosition(2.79f, 0);
        mIBar1.setPosition(2.89f, 0);
        mIBar2 = new iBar2(ibarTexture);
        //元の位置mIBar2.setPosition(3.3f, 0);
        mIBar2.setPosition(3.1f, 0);
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
            if (MathUtils.random(0, 1) == 1) {
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

        while ( grasses < 24) {

            Grass grass = new Grass(grassTexture);
            grass.setPosition(grasses, 4.5f);
            mGrass.add(grass);
            grasses += 6;
        }
        while ( Gs <= 24) {

            Ground ground = new Ground(groundTexture);
            ground.setPosition(Gs, 4.5f);
            mGround.add(ground);
            Gs += 8;
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

        screen1sTimer += delta;
        if (screen1sTimer >1){
            screen1sTimer =0;
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
                        FearGauge -= 1;//プレーヤーダメージ
                        note.get();//消す
                    }
                }

                //タイミング判定
            }
            if (note.mState == 0 && mDeadLine.getBoundingRectangle().overlaps(note.getBoundingRectangle())) {
                //押されなかった場合（ダメージを追加予定
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
                        FearGauge -= 2;//プレーヤーダメージ
                        note2.get();//消す
                    }
                }
                //タイミング判定
            }
            if (note2.mState == 0 && mDeadLine.getBoundingRectangle().overlaps(note2.getBoundingRectangle())) {
                //押されなかった場合（ダメージを追加予定
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
                        mPlayer.jumpstate = 1;

                    }
                    if (tb2 && !tb1) {
                        //ボタン2押しながら得点得た時の動作
                        //animation = new Animation<TextureRegion>(1,attackFrame);
                        //mPlayer = new Player(animation.getKeyFrame(elapsedTime, true));//テクスチャをplayerクラスに渡す
                        //アニメ―ション
                        mStar.jumpstate = 1;
                        mAttackEffect.threw = 1;
                    }
                    if (mIBar1.getBoundingRectangle().overlaps(enote.getBoundingRectangle())
                            && mIBar2.getBoundingRectangle().overlaps(enote.getBoundingRectangle())) {
                        getstarsound.play(1.0f);//獲得音
                        mScore = mScore + 5; //スコアに加算
                        if (mScore > mHighScore) { //ハイスコアを超えた場合
                            mHighScore = mScore; //今の点数をハイスコアに
                            //ハイスコアをPreferenceに保存する
                            mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                            mPrefs.flush(); // 値を永続化するのに必要
                        }
                        enote.get();//消す
                    } else {
                        mScore = mScore + 3; //スコアに加算
                        if (mScore > mHighScore) { //ハイスコアを超えた場合
                            mHighScore = mScore; //今の点数をハイスコアに
                            //ハイスコアをPreferenceに保存する
                            mPrefs.putInteger("HIGHSCORE", mHighScore); // 第1引数にキー、第2引数に値を指定
                            mPrefs.flush(); // 値を永続化するのに必要
                        }
                        enote.get();//消す

                        break;
                    }
                } else {
                    if (mIBar1.getBoundingRectangle().overlaps(enote.getBoundingRectangle())
                            || mIBar2.getBoundingRectangle().overlaps(enote.getBoundingRectangle())) {
                        hitsound.play(1.0f);//衝突音
                        FearGauge -= 2;//プレーヤーダメージ
                        enote.get();//消す
                    }
                }
                //タイミング判定
            }
            if (enote.mState == 0 && mDeadLine.getBoundingRectangle().overlaps(enote.getBoundingRectangle())) {
                //押されなかった場合（ダメージを追加予定
                FearGauge -= 2;//プレーヤーダメージ
                enote.get();//消す
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
        //敵の攻撃
        if (mPlayer.getBoundingRectangle().overlaps(mBone.getBoundingRectangle()) ){
            //攻撃当たった場合
            LifeGauge -= 5;
            mBone.get();//消す
        }
        //あたり判定ここまで
    }

    //ゲームオーバー時ResultScreenに遷移
    private void updateGameOver(float delta) {
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
        fall.dispose();//メモリ解放
        getstarsound.dispose();//メモリ解放
        jingle.dispose();//メモリ解放
    }

    //ゲームクリア時CrearScreenに遷移
    private void updateGameCrear(float delta) {
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
            mGame.setScreen(new CrearScreen(mGame, mScore,STAGENo));
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
    }
    private void TimingList(int stage) {
        if (stage == 1) {
            LengthOfSong =6;//66
            GhostT.add(1.514f);
            ToS.add(1.802f);
            ToS.add(2.102f);
            ToS.add(2.418f);
            ToS.add(2.784f);
            ToS.add(3.084f);
            ToS.add(3.4840002f);
            BatT.add(3.784f);
            ToS.add(4.0860004f);
            ToS.add(4.401f);
            ToS.add(4.701f);
            ToS.add(5.0010004f);
            ToS.add(5.318f);
            PumpkinT.add(5.634f);
            ToS.add(5.951f);
            ToS.add(6.234f);
            SkeletonT.add(6.5509996f);
            ToS.add(6.851f);
            ToS.add(7.151f);
            ToS.add(7.467f);
            ToS.add(7.785f);
            ToS.add(8.104f);
            ToS.add(8.401f);
            ToS.add(8.668f);
            ToS.add(9.001f);
            ToS.add(9.301f);
            ToS.add(9.601f);
            BatT.add(9.901f);
            ToS.add(10.218f);
            ToS.add(10.818f);
            ToS.add(11.452f);
            ToS.add(12.034f);
            ToS.add(12.618f);
            ToS.add(13.268f);
            BatT.add(13.584f);
            ToS.add(13.900999f);
            ToS.add(14.518f);
            PumpkinT.add(15.152f);
            ToS.add(15.750999f);
            ToS.add(16.052f);
            ToS.add(16.318f);
            SkeletonT.add(16.951f);
            ToS.add(17.518f);
            ToS.add(18.184f);
            BatT.add(18.501f);
            ToS.add(18.851f);
            ToS.add(19.435f);
            ToS.add(20.018f);
            ToS.add(20.684f);
            ToS.add(21.318f);
            ToS.add(21.636f);
            ToS.add(21.802f);
            PumpkinT.add(22.085f);
            ToS.add(22.539f);
            ToS.add(23.701f);
            ToS.add(24.067f);
            ToS.add(24.234f);
            ToS.add(24.534f);
            SkeletonT.add(24.951f);
            ToS.add(26.184f);
            ToS.add(26.518f);
            BatT.add(26.701f);
            ToS.add(26.985f);
            ToS.add(27.451f);
            ToS.add(28.153f);
            ToS.add(28.467f);
            ToS.add(28.766998f);
            ToS.add(29.368f);
            ToS.add(29.987999f);
            ToS.add(30.601002f);
            ToS.add(30.883999f);
            ToS.add(31.222f);
            PumpkinT.add(31.550999f);
            ToS.add(31.719002f);
            ToS.add(31.987999f);
            ToS.add(32.455f);
            ToS.add(33.669f);
            ToS.add(34.018f);
            ToS.add(34.184f);
            SkeletonT.add(34.501f);
            ToS.add(34.917f);
            ToS.add(36.175f);
            ToS.add(36.468f);
            ToS.add(36.634f);
            ToS.add(37.001f);
            ToS.add(37.418f);
            GhostT.add(38.068f);
            ToS.add(38.384f);
            ToS.add(38.667f);
            ToS.add(39.301f);
            ToS.add(39.902f);
            ToS.add(40.468f);
            ToS.add(40.803f);
            ToS.add(41.14f);
            ToS.add(42.034f);
            ToS.add(42.351f);
            ToS.add(43.317f);
            PumpkinT.add(43.637f);
            ToS.add(43.918f);
            ToS.add(44.234f);
            ToS.add(44.57f);
            ToS.add(44.734f);
            GhostT.add(44.951f);
            ToS.add(46.085f);
            ToS.add(46.725f);
            ToS.add(47.274f);
            ToS.add(47.856f);
            ToS.add(48.523f);
            ToS.add(49.089f);
            GhostT.add(49.39f);
            ToS.add(49.773f);
            ToS.add(50.126f);
            ToS.add(50.49f);
            ToS.add(51.026f);
            ToS.add(51.355f);
            ToS.add(51.556f);
            ToS.add(51.707f);
            PumpkinT.add(52.173f);
            ToS.add(52.356f);
            ToS.add(53.525f);
            ToS.add(53.84f);
            ToS.add(54.192f);
            ToS.add(54.49f);
            ToS.add(54.776f);
            ToS.add(55.073f);
            ToS.add(55.389f);
            ToS.add(55.727f);
            ToS.add(55.991f);
            ToS.add(56.273f);
            ToS.add(56.44f);
            ToS.add(56.605f);
            ToS.add(57.089f);
            ToS.add(57.238f);
            ToS.add(58.423f);
            ToS.add(58.738f);
            ToS.add(59.074f);
            ToS.add(59.373f);
            ToS.add(59.673f);
            ToS.add(59.976f);
            //ToS.add(60.172997f);
            ToS.add(60.189003f);
            ToS.add(60.372f);
            //ToS.add(60.739998f);
            ToS.add(60.889f);
            ToS.add(61.072f);


            ToS.add(61.002f);


            ToS.add(98.1f);
            ToS2.add(98.1f);
            PumpkinT.add(98.0f);
            SkeletonT.add(98.7f);
            BatT.add(99.7f);
            GhostT.add(98.7f);
        }
        if (stage == 2) {
            LengthOfSong =66;//66
            GhostT.add(1.514f);
            ToS.add(1.802f);
            ToS.add(2.102f);
            ToS.add(2.418f);
            ToS.add(2.784f);
            ToS.add(3.084f);
            ToS.add(3.4840002f);
            BatT.add(3.784f);
            ToS.add(4.0860004f);
            ToS.add(4.401f);
            ToS.add(4.701f);
            ToS.add(5.0010004f);
            ToS.add(5.318f);
            PumpkinT.add(5.634f);
            ToS.add(5.951f);
            ToS.add(6.234f);
            SkeletonT.add(6.5509996f);
            ToS.add(6.851f);
            ToS.add(7.151f);
            ToS.add(7.467f);
            ToS.add(7.785f);
            ToS.add(8.104f);
            ToS.add(8.401f);
            ToS.add(8.668f);
            ToS.add(9.001f);
            ToS.add(9.301f);
            ToS.add(9.601f);
            BatT.add(9.901f);
            ToS.add(10.218f);
            ToS.add(10.818f);
            ToS.add(11.452f);
            ToS.add(12.034f);
            ToS.add(12.618f);
            ToS.add(13.268f);
            BatT.add(13.584f);
            ToS.add(13.900999f);
            ToS.add(14.518f);
            PumpkinT.add(15.152f);
            ToS.add(15.750999f);
            ToS.add(16.052f);
            ToS.add(16.318f);
            SkeletonT.add(16.951f);
            ToS.add(17.518f);
            ToS.add(18.184f);
            BatT.add(18.501f);
            ToS.add(18.851f);
            ToS.add(19.435f);
            ToS.add(20.018f);
            ToS.add(20.684f);
            ToS.add(21.318f);
            ToS.add(21.636f);
            ToS.add(21.802f);
            PumpkinT.add(22.085f);
            ToS.add(22.539f);
            ToS.add(23.701f);
            ToS.add(24.067f);
            ToS.add(24.234f);
            ToS.add(24.534f);
            SkeletonT.add(24.951f);
            ToS.add(26.184f);
            ToS.add(26.518f);
            BatT.add(26.701f);
            ToS.add(26.985f);
            ToS.add(27.451f);
            ToS.add(28.153f);
            ToS.add(28.467f);
            ToS.add(28.766998f);
            ToS.add(29.368f);
            ToS.add(29.987999f);
            ToS.add(30.601002f);
            ToS.add(30.883999f);
            ToS.add(31.222f);
            PumpkinT.add(31.550999f);
            ToS.add(31.719002f);
            ToS.add(31.987999f);
            ToS.add(32.455f);
            ToS.add(33.669f);
            ToS.add(34.018f);
            ToS.add(34.184f);
            SkeletonT.add(34.501f);
            ToS.add(34.917f);
            ToS.add(36.175f);
            ToS.add(36.468f);
            ToS.add(36.634f);
            ToS.add(37.001f);
            ToS.add(37.418f);
            GhostT.add(38.068f);
            ToS.add(38.384f);
            ToS.add(38.667f);
            ToS.add(39.301f);
            ToS.add(39.902f);
            ToS.add(40.468f);
            ToS.add(40.803f);
            ToS.add(41.14f);
            ToS.add(42.034f);
            ToS.add(42.351f);
            ToS.add(43.317f);
            PumpkinT.add(43.637f);
            ToS.add(43.918f);
            ToS.add(44.234f);
            ToS.add(44.57f);
            ToS.add(44.734f);
            GhostT.add(44.951f);
            ToS.add(46.085f);
            ToS.add(46.725f);
            ToS.add(47.274f);
            ToS.add(47.856f);
            ToS.add(48.523f);
            ToS.add(49.089f);
            GhostT.add(49.39f);
            ToS.add(49.773f);
            ToS.add(50.126f);
            ToS.add(50.49f);
            ToS.add(51.026f);
            ToS.add(51.355f);
            ToS.add(51.556f);
            ToS.add(51.707f);
            PumpkinT.add(52.173f);
            ToS.add(52.356f);
            ToS.add(53.525f);
            ToS.add(53.84f);
            ToS.add(54.192f);
            ToS.add(54.49f);
            ToS.add(54.776f);
            ToS.add(55.073f);
            ToS.add(55.389f);
            ToS.add(55.727f);
            ToS.add(55.991f);
            ToS.add(56.273f);
            ToS.add(56.44f);
            ToS.add(56.605f);
            ToS.add(57.089f);
            ToS.add(57.238f);
            ToS.add(58.423f);
            ToS.add(58.738f);
            ToS.add(59.074f);
            ToS.add(59.373f);
            ToS.add(59.673f);
            ToS.add(59.976f);
            //ToS.add(60.172997f);
            ToS.add(60.189003f);
            ToS.add(60.372f);
            //ToS.add(60.739998f);
            ToS.add(60.889f);
            ToS.add(61.072f);


            ToS.add(61.002f);


            ToS.add(98.1f);
            ToS2.add(98.1f);
            PumpkinT.add(98.0f);
            SkeletonT.add(98.7f);
            BatT.add(99.7f);
            GhostT.add(98.7f);
        }
        if (stage == 3) {
            LengthOfSong =666;//66
            GhostT.add(1.514f);
            ToS.add(1.802f);
            ToS.add(2.102f);
            ToS.add(2.418f);
            ToS.add(2.784f);
            ToS.add(3.084f);
            ToS.add(3.4840002f);
            BatT.add(3.784f);
            ToS.add(4.0860004f);
            ToS.add(4.401f);
            ToS.add(4.701f);
            ToS.add(5.0010004f);
            ToS.add(5.318f);
            PumpkinT.add(5.634f);
            ToS.add(5.951f);
            ToS.add(6.234f);
            SkeletonT.add(6.5509996f);
            ToS.add(6.851f);
            ToS.add(7.151f);
            ToS.add(7.467f);
            ToS.add(7.785f);
            ToS.add(8.104f);
            ToS.add(8.401f);
            ToS.add(8.668f);
            ToS.add(9.001f);
            ToS.add(9.301f);
            ToS.add(9.601f);
            BatT.add(9.901f);
            ToS.add(10.218f);
            ToS.add(10.818f);
            ToS.add(11.452f);
            ToS.add(12.034f);
            ToS.add(12.618f);
            ToS.add(13.268f);
            BatT.add(13.584f);
            ToS.add(13.900999f);
            ToS.add(14.518f);
            PumpkinT.add(15.152f);
            ToS.add(15.750999f);
            ToS.add(16.052f);
            ToS.add(16.318f);
            SkeletonT.add(16.951f);
            ToS.add(17.518f);
            ToS.add(18.184f);
            BatT.add(18.501f);
            ToS.add(18.851f);
            ToS.add(19.435f);
            ToS.add(20.018f);
            ToS.add(20.684f);
            ToS.add(21.318f);
            ToS.add(21.636f);
            ToS.add(21.802f);
            PumpkinT.add(22.085f);
            ToS.add(22.539f);
            ToS.add(23.701f);
            ToS.add(24.067f);
            ToS.add(24.234f);
            ToS.add(24.534f);
            SkeletonT.add(24.951f);
            ToS.add(26.184f);
            ToS.add(26.518f);
            BatT.add(26.701f);
            ToS.add(26.985f);
            ToS.add(27.451f);
            ToS.add(28.153f);
            ToS.add(28.467f);
            ToS.add(28.766998f);
            ToS.add(29.368f);
            ToS.add(29.987999f);
            ToS.add(30.601002f);
            ToS.add(30.883999f);
            ToS.add(31.222f);
            PumpkinT.add(31.550999f);
            ToS.add(31.719002f);
            ToS.add(31.987999f);
            ToS.add(32.455f);
            ToS.add(33.669f);
            ToS.add(34.018f);
            ToS.add(34.184f);
            SkeletonT.add(34.501f);
            ToS.add(34.917f);
            ToS.add(36.175f);
            ToS.add(36.468f);
            ToS.add(36.634f);
            ToS.add(37.001f);
            ToS.add(37.418f);
            GhostT.add(38.068f);
            ToS.add(38.384f);
            ToS.add(38.667f);
            ToS.add(39.301f);
            ToS.add(39.902f);
            ToS.add(40.468f);
            ToS.add(40.803f);
            ToS.add(41.14f);
            ToS.add(42.034f);
            ToS.add(42.351f);
            ToS.add(43.317f);
            PumpkinT.add(43.637f);
            ToS.add(43.918f);
            ToS.add(44.234f);
            ToS.add(44.57f);
            ToS.add(44.734f);
            GhostT.add(44.951f);
            ToS.add(46.085f);
            ToS.add(46.725f);
            ToS.add(47.274f);
            ToS.add(47.856f);
            ToS.add(48.523f);
            ToS.add(49.089f);
            GhostT.add(49.39f);
            ToS.add(49.773f);
            ToS.add(50.126f);
            ToS.add(50.49f);
            ToS.add(51.026f);
            ToS.add(51.355f);
            ToS.add(51.556f);
            ToS.add(51.707f);
            PumpkinT.add(52.173f);
            ToS.add(52.356f);
            ToS.add(53.525f);
            ToS.add(53.84f);
            ToS.add(54.192f);
            ToS.add(54.49f);
            ToS.add(54.776f);
            ToS.add(55.073f);
            ToS.add(55.389f);
            ToS.add(55.727f);
            ToS.add(55.991f);
            ToS.add(56.273f);
            ToS.add(56.44f);
            ToS.add(56.605f);
            ToS.add(57.089f);
            ToS.add(57.238f);
            ToS.add(58.423f);
            ToS.add(58.738f);
            ToS.add(59.074f);
            ToS.add(59.373f);
            ToS.add(59.673f);
            ToS.add(59.976f);
            //ToS.add(60.172997f);
            ToS.add(60.189003f);
            ToS.add(60.372f);
            //ToS.add(60.739998f);
            ToS.add(60.889f);
            ToS.add(61.072f);


            ToS.add(61.002f);


            ToS.add(98.1f);
            ToS2.add(98.1f);
            PumpkinT.add(98.0f);
            SkeletonT.add(98.7f);
            BatT.add(99.7f);
            GhostT.add(98.7f);
        }
    }

}
