package de.thodi.whammycontroller.effects;

public class HarmonyEffect extends Effect {

    public HarmonyEffect(String pDescription, int pActive, int pBypass) {
        super(pDescription, pActive, pBypass);
    }


    @Override
    public String toString() {
        return "HARMONY " + super.toString();
    }
}
