package com.zemeck.playerhud.capabilities.Player;

public class PlayerStats implements IPlayerStats {

    private float mana = 100;
    private float maxMana = 250;
    private int power = 10;
    private int defence = 10;
    private int stamina = 10;
    private String profession = "Squire";

    // Mana methods

    public void consumeMana(float points)
    {
        this.mana -= points;

        if (this.mana < 0.0F) this.mana = 0.0F;
    }

    public void fillMana(float points)
    {
        this.mana += points;

        if (this.mana > this.maxMana) this.mana = this.maxMana;
    }

    // Player Stat setters

    public void setPower(int power)
    {
        this.power = power;
    }
    public void setDefence(int defence)
    {
        this.defence = defence;
    }
    public void setStamina(int stamina)
    {
        this.stamina = stamina;
    }
    public void setProfession(String profession)
    {
        this.profession = profession;
    }
    public void setMana(float points)
    {
        this.mana = points;
    }
    public void setMaxMana(float points)
    {
        this.maxMana = points;
    }

    // Player Stat getters

    public int getPower()
    {
        return this.power;
    }
    public int getDefence()
    {
        return this.defence;
    }
    public int getStamina()
    {
        return this.stamina;
    }
    public String getProfession()
    {
        return this.profession;
    }
    public float getMana()
    {
        return this.mana;
    }
    public float getMaxMana() { return this.maxMana; }
}
