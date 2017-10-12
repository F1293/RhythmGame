package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RhythmGame extends Game {
	// publicにして外からアクセスできるようにする
	public SpriteBatch batch;
    public ActivityRequestHandler mRequestHandler;

    @Override
	public void create () {
		batch = new SpriteBatch();

		// GameScreenを表示する
		setScreen(new StartScreen(this));
	}
}