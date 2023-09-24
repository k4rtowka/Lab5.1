package Models;

public enum MeleeWeapon
{
    CHAIN_SWORD("цепной меч"),
    POWER_SWORD("силовой меч"),
    CHAIN_AXE("цепной топор"),
    POWER_BLADE("силовой клинок"),
    POWER_FIST("силовой удар");

    private String text;

    MeleeWeapon(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
