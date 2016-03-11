package de.thodi.whammycontroller.effects;

public class WhammyEffect extends Effect {
    public WhammyEffect(String pDescription, int pActive, int pBypass) {
        super(pDescription, pActive, pBypass);
    }


    @Override
    public String toString() {
        return "WHAMMY " + super.toString();
    }
}
