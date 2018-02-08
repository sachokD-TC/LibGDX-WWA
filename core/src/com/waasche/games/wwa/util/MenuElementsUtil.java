package com.waasche.games.wwa.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 * Created by sadm on 1/23/2018.
 */
public class MenuElementsUtil {


    public static Label getFinalTextLabel(float xPos, float yPos, String text){
        Label label = getAboutTextLabel(xPos,yPos,text);
        label.setColor(Color.DARK_GRAY);
        return label;
    }

    public static Label getAboutTextLabel(float xPos, float yPos, String text) {
        Label aboutLabel = getTextActor(xPos, yPos, text);
        aboutLabel.setFontScale(2f);
        return aboutLabel;
    }

    public static Label getTextActor(float xPos, float yPos, String text) {
        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = getFont(1f);
        Label label = new Label(text, textStyle);
        label.setFontScale(5f, 5f);
        label.setAlignment(Align.center);
        label.setPosition(xPos, yPos);
        label.setBounds(xPos, yPos, text.length() * 100f, 200f);
        label.setColor(Color.BLACK);
        label.setBounds(xPos, yPos, 100, 100);
        return label;
    }

    public static BitmapFont getFont(float scale){
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/JOKERMAN.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.characters += "¡…Õ”÷?⁄‹?·ÈÌÛˆ?˙¸?";
        fontParameter.size = Math.round(18.0f * scale);
        return fontGenerator.generateFont(fontParameter);
    }

    public static void clearScreen(){
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
    }
}

