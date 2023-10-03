package Tests.Data;

import static org.junit.jupiter.api.Assertions.*;

import Models.Chapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChapterTests {
    private Chapter chapter;

    @BeforeEach
    public void setUp(){
        chapter = new Chapter();
    }

    @Test
    public void setName_validName(){
        chapter.setName("ABC 3");
        assertEquals("ABC 3", chapter.getName());
    }

    @Test
    public void setMarinesCount_validCount(){
        chapter.setMarinesCount(512);
        assertEquals(512, chapter.getMarinesCount());
    }
}
