package test;


import com.waasche.games.wwa.entities.Levels;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static junit.framework.Assert.assertFalse;


public class JsonLevelReaderTest  {

    private File file;

    @Before
    public void createJsonFile() throws IOException {
        file =  File.createTempFile("temp", "json");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getName()));
        writer.write("{level:desert, " +
                     "enemies[" +
                     "{pic:sun.png," +
                     "rectangles[" +
                     "{100,100,200,200}]" +
                     "]}" +
                     "}");
    }

    @Test
    public void testRead(){
        Levels levels = new Levels(file.getName());
        assertFalse(levels.getLevels().isEmpty());
    }




}
