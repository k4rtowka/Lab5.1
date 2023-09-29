package Models;

public enum WeaponType
{
    CHAIN_SWORD("цепной меч"),
    POWER_SWORD("силовой меч"),
    CHAIN_AXE("цепной топор"),
    POWER_BLADE("силовой клинок"),
    POWER_FIST("силовой удар");

    private String text;

    WeaponType(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
