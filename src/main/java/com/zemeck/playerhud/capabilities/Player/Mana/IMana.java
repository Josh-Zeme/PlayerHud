package com.zemeck.playerhud.capabilities.Player.Mana;

public interface IMana {

    public void consume(float points);
    public void fill(float points);
    public void setMana(float points);
    public void setMaxMana(float points);

    public float getMana();
    public float getMaxMana();
}
