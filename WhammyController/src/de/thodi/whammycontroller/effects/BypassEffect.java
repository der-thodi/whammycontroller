package de.thodi.whammycontroller.effects;

public class BypassEffect extends Effect {

    public BypassEffect(String pDescription, int pActive, int pBypass) {
        super(pDescription, pActive, pBypass);
    }

    public BypassEffect(String pDescription) {
        super(pDescription, 0, 0);
    }
    
    @Override
    public String toString() {
        return "BYPASS";
    }
}
