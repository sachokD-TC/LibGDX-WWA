package com.waasche.games.wwa.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 * Created by sadm on 1/23/2018.
 */
public class MenuElementsUtil {

    public static Label getAboutTextLabel(float xPos, float yPos, String text) {
        Label aboutLabel = getTextActor(xPos, yPos, text);
        aboutLabel.setFontScale(2f);
        return aboutLabel;
    }

    public static Label getTextActor(float xPos, float yPos, String text) {
        Label.LabelStyle textStyle = new Label.LabelStyle();
        ;
        textStyle.font = new BitmapFont();
        Label label = new Label(text, textStyle);
        label.setFontScale(5f, 5f);
        label.setAlignment(Align.center);
        label.setPosition(xPos, yPos);
        label.setBounds(xPos, yPos, text.length() * 100f, 200f);
        label.setColor(Color.BLACK);
        label.setBounds(xPos, yPos, 100, 100);
        return label;
    }
}

