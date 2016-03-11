package de.thodi.whammycontroller.effects;

public class BypassEffect extends Effect {

    public BypassEffect(String pDescription, int pActive, int pBypass) {
        super(pDescription, pActive, pBypass);
    }

    @Override
    public String toString() {
        return "BYPASS";
    }
}
