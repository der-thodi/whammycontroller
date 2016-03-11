package de.thodi.whammycontroller.effects;

public class DetuneEffect extends Effect {

    public DetuneEffect(String pDescription, int pActive, int pBypass) {
        super(pDescription, pActive, pBypass);
    }


    @Override
    public String toString() {
        return "DETUNE " + super.toString();
    }
}
