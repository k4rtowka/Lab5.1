import Models.MeleeWeapon;
import Models.SpaceMarine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class SpaceMarineTest {
    private SpaceMarine marine;

    @BeforeEach
    public void setUp() {
        marine = new SpaceMarine();
    }

    @Test
    public void setId_validId_setsId() {
        marine.setId(10);
        assertEquals(10, marine.getId());
    }

    @Test
    public void setId_noValidId(){
        assertThrows(IllegalArgumentException.class, () -> marine.setId(-20));
    }

    @Test
    public void getMeleeWeapon(){
        assertEquals("цепной топор", MeleeWeapon.CHAIN_AXE.getText());
    }

}
