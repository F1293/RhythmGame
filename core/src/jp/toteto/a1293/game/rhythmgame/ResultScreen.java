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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ResultScreen extends ScreenAdapter {
    static final float GUI_WIDTH = 512;
    static final float GUI_HEIGHT = 288;

    private RhythmGame mGame;
    Sprite mBg;
    OrthographicCamera mGuiCamera;
    FitViewport mGuiViewPort;
    BitmapFont mFont;

    int mScore;

    Music gomusic = Gdx.audio.newMusic(Gdx.files.internal("gomusic.mp3"));//音楽準備
    Sound rebirth = Gdx.audio.newSound(Gdx.files.internal("rebirth.mp3"));//効果音準備

    public ResultScreen(RhythmGame game, int score) {

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
        mBg.setSize(GUI_WIDTH, GUI_HEIGHT);
        mBg.setPosition(0, 0);

        // GUI用のカメラを設定する
        mGuiCamera = new OrthographicCamera();
        mGuiCamera.setToOrtho(false, GUI_WIDTH, GUI_HEIGHT);
        mGuiViewPort = new FitViewport(GUI_WIDTH, GUI_HEIGHT, mGuiCamera);

        // フォント
        mFont = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
    }

    @Override
    public void render(float delta) {
        // 描画する
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // カメラの座標をアップデート（計算）し、スプライトの表示に反映させる
        mGuiCamera.update();
        mGame.batch.setProjectionMatrix(mGuiCamera.combined);

        mGame.batch.begin();
        mBg.draw(mGame.batch);
        mFont.draw(mGame.batch, "Score: " + mScore, 0, GUI_HEIGHT / 2 + 60, GUI_WIDTH, Align.center, false);
        mFont.draw(mGame.batch, "Retry?", 0, GUI_HEIGHT / 2 - 40, GUI_WIDTH, Align.center, false);
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
}