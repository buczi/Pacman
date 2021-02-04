package general;

import exception.CriticalException;
import file.FileHandler;
import model.objects.field.Field;
import model.objects.field.FieldType;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class GeneralTest {

    @Test
    public void fileFailLoaderTest() {
        assertThrows(IOException.class, () -> FileHandler.fileLoader("wrongPath"));
    }

    @Test
    public void fileLoaderTest() throws CriticalException {
        assertNotNull(FileHandler.fileLoader(new File("")
                .getAbsolutePath() + "/src/test/resources/test.txt"));
    }


//    @Test
//    public void mapBuilderTest() {
//        assertAll(
//                () -> assertThrows(Exception.class, () -> new Map.Builder().build()),
//                () -> assertThrows(Exception.class, () -> new Map.Builder().),
//                () -> assertThrows(Exception.class, () -> new Map.Builder().build())
//        );
//
//    }

    @Test
    public void fieldTest() {
        assertAll("Check all Fields",
                () -> assertTrue(new Field(0, 0, FieldType.Empty).isImmovable()),
                () -> assertTrue(new Field(0, 0, FieldType.Fruit).isImmovable()),
                () -> assertTrue(new Field(0, 0, FieldType.BigFruit).isImmovable()),
                () -> assertTrue(new Field(0, 0, FieldType.Wall).isImmovable()),
                () -> assertFalse(new Field(0, 0, FieldType.PacMan).isImmovable()),
                () -> assertFalse(new Field(0, 0, FieldType.Blinky).isImmovable()),
                () -> assertFalse(new Field(0, 0, FieldType.Clyde).isImmovable()),
                () -> assertFalse(new Field(0, 0, FieldType.Pinky).isImmovable()),
                () -> assertFalse(new Field(0, 0, FieldType.Inky).isImmovable())
        );
    }

}
