package com.zemeck.playerhud.capabilities.Player;

public interface IPlayerStats {

    void consumeMana(float points);
    void fillMana(float points);
    void setPower(int power);
    void setMana(float mana);
    void setDefence(int defence);
    void setStamina(int stamina);
    void setProfession(String profession);
    void setMaxMana(float points);

    String getProfession();
    int getStamina();
    int getPower();
    int getDefence();
    float getMana();
    float getMaxMana();
}
