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
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;



/**
 * Created by Fumio on 2017/10/01.
 */

public class GameScreen extends ScreenAdapter {
    //カメラのサイズを表す定数を定義する
    static final float CAMERA_WIDTH = 16;
    static final float CAMERA_HEIGHT = 9;
    //ゲーム世界の広さを定義
    static final float WORLD_WIDTH = 16;
    static final float WORLD_HEIGHT = 9 * 20; // 20画面分登れば終了
    static final float GUI_WIDTH = 512;//GUI用カメラのサイズ
    static final float GUI_HEIGHT = 288;//GUI用カメラのサイズ

    //ゲーム開始前、中、ゲーム終了を表す定数の定義
    static final int GAME_STATE_READY = 0;
    static final int GAME_STATE_PLAYING = 1;
    static final int GAME_STATE_GAMEOVER = 2;

    // 重力
    static final float GRAVITY = -12;

    private RhythmGame mGame;

    Sprite mBg;
    Sprite mBg2;

    //カメラクラスとビューポートクラスをメンバ変数として定義
    OrthographicCamera mCamera;
    OrthographicCamera mGuiCamera;

    FitViewport mViewPort;
    FitViewport mGuiViewPort;

    Random mRandom;//乱数を取得するためのクラス
    List<Note> mNote;
    List<Note2> mNote2;
    Bar mBar;
    //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    iBar mIBar;
    iBar1 mIBar1;
    iBar2 mIBar2;
    DeadLine mDeadLine;

    Button1 mButton1;
    Button2 mButton2;
    Button3 mButton3;
    Button4 mButton4;

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

    //boolean b = true;
    int ToSs;
    int ToSs2;

    int n = 0;
    int a = 0;
    int nn = 0;
    int aa = 0;

    int end = 0;


    //float[] ToS = {5.20f,6.90f};//再生時間に応じて音を鳴らす、どのタイミングかを入れる
    ArrayList<Float> ToS = new ArrayList<Float>();
    ArrayList<Float> ToS2 = new ArrayList<Float>();
    //ToS.add(10.2f);
    //String stringFormat;

    //float f = 2.7f;


    //音楽の準備
    Music playingmusic = Gdx.audio.newMusic(Gdx.files.internal("playingmusic.mp3"));

    //効果音の準備
    Sound hitsound = Gdx.audio.newSound(Gdx.files.internal("hitsound.mp3"));
    Sound fall = Gdx.audio.newSound(Gdx.files.internal("fall.mp3"));
    Sound jingle = Gdx.audio.newSound(Gdx.files.internal("jingle.mp3"));
    Sound getstarsound = Gdx.audio.newSound(Gdx.files.internal("getstarsound.mp3"));

    public GameScreen(RhythmGame game) {
        mGame = game;

        ToS.add(2.1f);
        ToS.add(3.7f);
        ToS.add(4.7f);
        ToS.add(5.7f);
        ToS.add(6.7f);
        ToS.add(7.7f);
        ToS.add(98.7f);

        ToS2.add(1.1f);
        ToS2.add(2.7f);
        ToS2.add(3.1f);
        ToS2.add(4.1f);
        ToS2.add(5.1f);
        ToS2.add(6.1f);
        ToS2.add(7.1f);
        ToS2.add(8.7f);
        ToS2.add(9.1f);
        ToS2.add(10.1f);
        ToS2.add(11.1f);
        ToS2.add(12.1f);
        ToS2.add(98.1f);

        //playingmusic.setLooping(true);//音楽はループ

        //背景の処理
        Texture bgTexture = new Texture("back.png");
        //TextureReionで切り出すときの原点は左上
        mBg = new Sprite(new TextureRegion(bgTexture, 0, 0, 1022, 608));
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

        // ハイスコアをPreferencesから取得する
        mPrefs = Gdx.app.getPreferences("jp.techacademy.fumio.ueda.rhythmgame");//Preferencesの取得
        mHighScore = mPrefs.getInteger("HIGHSCORE", 0);//第2引数はキーに対応する値がなかった場合に返ってくる値（初期値）

        createStage();
        //オブジェクト配置するcreateStageメソッドを呼び出す

        //背景の処理
        Texture bg2Texture = new Texture("back2.png");
        //TextureReionで切り出すときの原点は左上
        mBg2 = new Sprite(new TextureRegion(bg2Texture, 0, 0, 1022, 608));
        //画像の切り出し
        mBg2.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        //カメラサイズに設定
        mBg2.setPosition(0, 0);
        //左下基準０．０に描画
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
/*
        // カメラの中心を超えたらカメラを上に移動させる つまりキャラが画面の上半分には絶対に行かない
        if (mPlayer.getY() > mCamera.position.y) {
            mCamera.position.y = mPlayer.getY();
        }
*/
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
        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        //mIBar.draw(mGame.batch);
        //mIBar1.draw(mGame.batch);
        //mIBar2.draw(mGame.batch);
        // デッドラインの表示
        //mDeadLine.draw(mGame.batch);

        // ボタンの表示
        mButton1.draw(mGame.batch);
        mButton2.draw(mGame.batch);
        mButton3.draw(mGame.batch);
        mButton4.draw(mGame.batch);

        mGame.batch.end();

        // スコア表示
        mGuiCamera.update();
        mGame.batch.setProjectionMatrix(mGuiCamera.combined);
        //↑はカメラの座標を計算しスプライト表示に反映させるのに必要
        mGame.batch.begin();

        //drawメソッドで描画第1引数にSprteBatch、第2引数に表示されたい文字列、第3引数にx座標、第4引数にy座標
        mFont.draw(mGame.batch, "HighScore: " + mHighScore, 16, GUI_HEIGHT - 15);
        mFont.draw(mGame.batch, "Music: " + musictime, 16, GUI_HEIGHT - 55);
        mFont.draw(mGame.batch, "Score: " + mScore, 16, GUI_HEIGHT - 35);
        mFont.draw(mGame.batch, "tb1.2.3.4"+ tb1 + "."+ tb2 + "."+ tb3 + "."+ tb4 + ".", 16, GUI_HEIGHT - 75);
        // mFont.draw(mGame.batch, "ToSs" + ToSs, 16, GUI_HEIGHT - 95);
        // mFont.draw(mGame.batch, "n ="+ n + "" , 16, GUI_HEIGHT - 115);
        //mFont.draw(mGame.batch, "hasseibu i="+ nn + "" , 16, GUI_HEIGHT - 135);

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
        ToSs = ToS.size();
        ToSs2 = ToS2.size();

        // テクスチャの準備
        Texture stepTexture = new Texture("step.png");
        Texture stepaTexture = new Texture("stepa.png");
        Texture starTexture = new Texture("star.png");
        Texture darkstarTexture = new Texture("darkstar.png");
        Texture noteTexture = new Texture("note.png");
        Texture note2Texture = new Texture("note.png");
        Texture playerTexture = new Texture("uma.png");
        Texture ufoTexture = new Texture("ufo.png");
        Texture barTexture = new Texture("bar.png");
        Texture button1Texture = new Texture("button1a.png");
        Texture button2Texture = new Texture("button2a.png");
        Texture button3Texture = new Texture("button3a.png");
        Texture button3bTexture = new Texture("button3b.png");
        Texture button4Texture = new Texture("button4a.png");

        // StepとStar、DarkStar、Enemyをゴールの高さまで配置していく
       // float y = 0;
        // 棒を配置
        mBar = new Bar(barTexture, 0, 0, 128, 128);
        mBar.setPosition(3.1f, 0);
        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        mIBar = new iBar(barTexture, 0, 0, 128, 128);
        mIBar.setPosition(3, 0);
       mIBar1 = new iBar1(barTexture, 0, 0, 128, 128);
       mIBar1.setPosition(2.5f, 0);
        mIBar2 = new iBar2(barTexture, 0, 0, 128, 128);
        mIBar2.setPosition(3.3f, 0);
//左右0.1までのずれ許容
        // デッドラインを配置
        mDeadLine = new DeadLine(barTexture, 0, 0, 128, 128);
        mDeadLine.setPosition(1, 0);

        // ボタン１を配置
        mButton1 = new Button1(button1Texture, 0, 0, 128, 128);
        mButton1.setPosition(14, 2.2f);

        // ボタン2を配置
        mButton2 = new Button2(button2Texture, 0, 0, 128, 128);
        mButton2.setPosition(14, 0);

        // ボタン１を配置
        mButton3 = new Button3(button3Texture, 0, 0, 128, 128);
        mButton3.setPosition(0, 2.2f);

        // ボタン１を配置
        mButton4 = new Button4(button4Texture, 0, 0, 128, 128);
        mButton4.setPosition(0, 0);

        //lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll
        //float maxJumpHeight = 5;
        //ゴール直前まで繰り返して生成
        while (end < ToSs || end < ToSs2 ) {
        //while (y < WORLD_HEIGHT - 5) {
            //x方向はランダムな場所
            //float x = mRandom.nextFloat() * (WORLD_WIDTH - Step.STEP_WIDTH);

            // Step step = new Step(stepTexture, 0, 0, 144, 36);
            // step.setPosition(x, y);
            // mSteps.add(step);//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

            // Stepa stepa = new Stepa(stepaTexture, 0, 0, 144, 36);
            // stepa.setPosition(1, 1);
            // mStepas.add(stepa);

            //星はランダムで生成(2/5の確率
            // if (mRandom.nextFloat() > 0.6f) {
            //     Star star = new Star(starTexture, 0, 0, 72, 72);
            //     //床を基準に乱数で場所を決める
            //     star.setPosition(step.getX() + mRandom.nextFloat(), step.getY() + Star.STAR_HEIGHT + mRandom.nextFloat() * 3);
            //     mStars.add(star);
            // }

            //偽星はランダムで生成(1/5の確率、中央に配置させないため二つに分ける
            //if (mRandom.nextFloat() > 0.9f) {
            //    DarkStar darkstar = new DarkStar(darkstarTexture, 0, 0, 72, 72);
            //    //床を基準に乱数で場所を決める
            //    darkstar.setPosition(mRandom.nextFloat() * 3, step.getY() + DarkStar.DARKSTAR_HEIGHT + 1);
            //    mDarkStars.add(darkstar);
            //}
            //if (mRandom.nextFloat() > 0.9f) {
            //    DarkStar darkstar = new DarkStar(darkstarTexture, 0, 0, 72, 72);
            //     //床を基準に乱数で場所を決める
            //    darkstar.setPosition(mRandom.nextFloat() * 4 + 6, step.getY() + DarkStar.DARKSTAR_HEIGHT + 1);
            //    mDarkStars.add(darkstar);
            // }

            //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            Note note = new Note(noteTexture, 0, 0, 128, 128);
            //場所を決める
            note.setPosition(15, 0.85f);
            mNote.add(note);

            Note2 note2 = new Note2(note2Texture, 0, 0, 128, 128);
            //場所を決める
            note2.setPosition(15, 2.85f);
            mNote2.add(note2);
            //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            /*
            * //敵はランダムで生成(2/5の確率
            if (mRandom.nextFloat() > 0.6f) {
                Enemy enemy = new Enemy(enemyTexture, 0, 0, 120, 74);
                //床を基準に乱数で場所を決める
                enemy.setPosition(step.getX() + mRandom.nextFloat(), step.getY() + 8 + mRandom.nextFloat() * 3);
                mEnemy.add(enemy);
            }*/

            //床はジャンプで届く位置に生成するように調整
            //y += (maxJumpHeight - 0.5f);
            //y -= mRandom.nextFloat() * (maxJumpHeight / 3);

            end ++;

        }

        // Playerを配置
        // mPlayer = new Player(playerTexture, 0, 0, 72, 72);
        //mPlayer.setPosition(WORLD_WIDTH / 2 - mPlayer.getWidth() / 2, Step.STEP_HEIGHT);

        // ゴールのUFOを配置
        //mUfo = new Ufo(ufoTexture, 0, 0, 120, 74);
        //mUfo.setPosition(WORLD_WIDTH / 2 - Ufo.UFO_WIDTH / 2, y);
        /*lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll
        //ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp
        //while ( n < 5) {
            //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            Note note = new Note(noteTexture, 0, 0, 120, 74);
            //場所を決める
            note.setPosition(5, 5);
            mNote.add(note);
            //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        //}
        //ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp
        */
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

        //int diff = Float.compare(f, playingmusic.getPosition());
        //if(diff == 0) {
        //   hitsound.play(1.0f);//衝突音
        //}

        musictime = playingmusic.getPosition();//再生時間取得
        //ボタンがタッチされているかaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        tb1 = false;
        tb2 = false;
        tb3 = false;
        tb4 = false;
//sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
        //タッチされている間動作
        if (Gdx.input.justTouched()) {
            //Gdx.input.getX()とGdx.input.getY()でタッチされた座標を取得
            // Vector3クラスはx,yだけでなくZ軸を保持するメンバ変数zも持っているためsetメソッドの第3引数には0を指定
            //mTouchPointをOrthographicCameraクラスのunprojectメソッドに与えて呼び出すことでカメラを使った座標に変換
            mGuiViewPort.unproject(mTouchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            Rectangle leftu = new Rectangle(0, 77, 70, 144);//ボタン３
            Rectangle leftd = new Rectangle(0, 0, 70, 77);//ボタン４
            if (leftu.contains(mTouchPoint.x, mTouchPoint.y)) {
                //左タッチ時の動作
                tb3 = true;
               // mButton3.setTexture();
            }
            if (leftd.contains(mTouchPoint.x, mTouchPoint.y)) {
                //右タッチ時の動作
                tb4 = true;
            }
        }

        //mNote.get(2).update(delta);
        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        // Stepa
        if (n < ToSs) {
            for (int i = 0; i < a; i++) { //ここで一回のみの動作に
                mNote.get(i).update(delta);
                //mNote.get(i).draw(mGame.batch);

            }
            if (playingmusic.getPosition() > ToS.get(n)) {


                n++;
                a++;
            }
        }
        //mNote2.get(2).update(delta);
        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        // Stepa
        if (nn < ToSs2) {
            for (int i = 0; i < aa; i++) { //ここで一回のみの動作に
                mNote2.get(i).update(delta);
                //mNote2.get(i).draw(mGame.batch);

            }
            if (playingmusic.getPosition() > ToS2.get(nn)) {


                nn++;
                aa++;
            }
        }

//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        checkCollision();

        // ゲームオーバーか判断する
        checkGameOver();

    }

    //あたり判定の処理
    private void checkCollision() {

            // ノーツとの当たり判定
            for (int i = 0; i < mNote.size(); i++) {
                Note note = mNote.get(i);
                if (tb4 == true) {
                    //ボタン３が押されたときに上ラインのあたり判定
                if (note.mState == note.NOTE_NONE) {
                    continue;
                    //消えてるのには反応しない
                }
                if (mIBar.getBoundingRectangle().overlaps(note.getBoundingRectangle())) {
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
                        note.get();//消す
                    }
                }
                //タイミング判定
            }
            if (note.mState == note.NOTE_EXIST && mDeadLine.getBoundingRectangle().overlaps(note.getBoundingRectangle())) {
                hitsound.play(1.0f);//衝突音
                note.get();//消す
            }
            //押されなかった場合（ダメージを追加予定
        }
        for (int i = 0; i < mNote2.size(); i++) {
            Note2 note2 = mNote2.get(i);
            if (tb3 == true) {
                //ボタン３が押されたときに上ラインのあたり判定
                if (note2.mState == note2.NOTE_NONE) {
                    continue;
                    //消えてるのには反応しない
                }
                if (mIBar.getBoundingRectangle().overlaps(note2.getBoundingRectangle())) {
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
                        note2.get();//消す
                    }
                }
                //タイミング判定
            }
            if (note2.mState == note2.NOTE_EXIST && mDeadLine.getBoundingRectangle().overlaps(note2.getBoundingRectangle())) {
                hitsound.play(1.0f);//衝突音
                note2.get();//消す
            }
            //押されなかった場合（ダメージを追加予定
        }

    }

    //ゲームオーバー時タッチするとResultScreenに遷移
    private void updateGameOver() {
        //playingmusic.stop();//音楽停止
        //gomusic.play();//ゲームオーバー画面の音楽再生
        //if (Gdx.input.justTouched()) {
            //gomusic.stop(); //ゲームオーバー画面の音楽停止
            hitsound.dispose();//メモリ解放
            fall.dispose();//メモリ解放
            getstarsound.dispose();//メモリ解放
            jingle.dispose();//メモリ解放
            mGame.setScreen(new ResultScreen(mGame, mScore));
        //}
    }
    private void checkGameOver() {
        //地面との距離からカメラの高さの半分を引き、その値より低くなったらゲームオーバー
        //(画面の下まで落ちた場合）
        if (playingmusic.getPosition()>18) {
            Gdx.app.log("RhythmGame", "GAMEOVER");
            //gomusic.play();//ゲームオーバー画面の音楽再生
            playingmusic.dispose();//メモリ解放
            mGameState = GAME_STATE_GAMEOVER;
        }
    }
}