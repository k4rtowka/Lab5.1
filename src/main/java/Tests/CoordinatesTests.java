package Tests;

import static org.junit.jupiter.api.Assertions.*;


import Models.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoordinatesTests {
    private Coordinates coordinates;

    @BeforeEach
    public void setUp(){
        coordinates = new Coordinates();
    }

    @Test
    public void setX_validX(){
        coordinates.setX(2.4);
        assertEquals(2.4, coordinates.getX());
    }

    @Test
    public void setY_validY(){
        coordinates.setY(2);
        assertEquals(2, coordinates.getY());
    }
}
