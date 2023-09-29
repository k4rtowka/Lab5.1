package Tests;

import static org.junit.jupiter.api.Assertions.*;


import Models.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoordinatesTests {
    private Coordinate coordinates;

    @BeforeEach
    public void setUp(){
        coordinates = new Coordinate();
    }

    @Test
    public void setX_validX(){
        coordinates.setX(2.4);
        assertEquals(2.4, coordinates.getX());
    }

    @Test
    public void setX_InvalidX(){
        assertThrows(IllegalArgumentException.class, () -> coordinates.setX(-1000.0));
    }

    @Test
    public void setY_validY(){
        coordinates.setY(2);
        assertEquals(2, coordinates.getY());
    }
}
