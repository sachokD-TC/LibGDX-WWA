package com.waasche.games.wwa.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by sadm on 10/9/2017.
 */
public class TouchPadListener extends InputListener {

    private boolean isTouchDown;

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        isTouchDown = true;
        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        isTouchDown = false;
    }

    public boolean isTouchDown() {
        return isTouchDown;
    }



}
