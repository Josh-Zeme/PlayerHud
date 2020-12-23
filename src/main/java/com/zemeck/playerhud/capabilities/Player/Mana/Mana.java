package com.zemeck.playerhud.capabilities.Player.Mana;

public class Mana implements IMana {

    private float mana;
    private float maxMana = 250;

    public void consume(float points)
    {
        this.mana -= points;

        if (this.mana < 0.0F) this.mana = 0.0F;
    }

    public void fill(float points)
    {
        this.mana += points;

        if (this.mana > this.maxMana) this.mana = this.maxMana;
    }

    public void setMana(float points)
    {
        this.mana = points;
    }

    public void setMaxMana(float points)
    {
        this.maxMana = points;
    }

    public float getMana()
    {
        return this.mana;
    }

    public float getMaxMana() { return this.maxMana; }
}
