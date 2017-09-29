package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by sadm on 9/29/2017.
 */
public class WwaInputProcessor implements InputProcessor {

    float cowboyX = 0;
    float cowboyY = 0;


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
            float distance = (float) Math.sqrt(Math.pow(x - cowboyX,2) + Math.pow(y - cowboyY, 2));
            Vector2 start = new Vector2(cowboyX, cowboyY);
            Vector2 end = new Vector2(x,y);
            Vector2 delta = end.sub(start);
            delta.nor(); // Normalize the vector or you get odd diagonal movement
            Vector2 position = new Vector2(x, y);
            delta.scl(distance);
            position.add(delta);
            this.cowboyX = position.x;
            this.cowboyY = position.y;
            return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public float getCowboyX() {
        return cowboyX;
    }

    public float getCowboyY() {
        return cowboyY;
    }

}
