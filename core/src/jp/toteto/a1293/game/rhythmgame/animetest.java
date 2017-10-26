package jp.toteto.a1293.game.rhythmgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fumio on 2017/10/26.
 */

public class animetest implements ApplicationListener {

    //アニメーション

    Texture img;
    TextureRegion[] animationFrames;
    Animation<TextureRegion> animation;
    float elapsedTime;
    SpriteBatch batch;
    //アニメーション

    @Override
    public void create() {
        //アニメーション
        img = new Texture("majomajo.png");
        TextureRegion[] [] tmpFrames = TextureRegion.split(img,256,256);

        animationFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 2; j++){
                animationFrames[index++] = tmpFrames[j][i];
            }
        }
        animation = new Animation<TextureRegion>(1f/4f,animationFrames);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
//アニメーション
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(animation.getKeyFrame(elapsedTime,true),0,0,1,1);
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
