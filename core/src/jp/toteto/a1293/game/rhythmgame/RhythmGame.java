package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RhythmGame extends Game {
	// publicにして外からアクセスできるようにする
	public SpriteBatch batch;
    public ActivityRequestHandler mRequestHandler;
	public RhythmGame(ActivityRequestHandler requestHandler) {
		super();
		mRequestHandler = requestHandler;
	}
	public interface AdShowable {
		public void showInterstitialAd();
	}
    @Override
	public void create () {
		batch = new SpriteBatch();

		// GameScreenを表示する
		setScreen(new StartScreen(this));
	}
}