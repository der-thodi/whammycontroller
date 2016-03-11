package de.thodi.whammycontroller.effects;

import de.thodi.whammycontroller.Constants;

public class BypassEffect extends Effect {

    public BypassEffect(String pDescription, int pActive, int pBypass,
                        int pPedalPosition) {
        super(pDescription, pActive, pBypass, pPedalPosition);
    }
    
    
    public BypassEffect(String pDescription, int pActive, int pBypass) {
        this(pDescription, pActive, pBypass, Constants.IGNORE_PEDAL_POSITION);
    }

    
    public BypassEffect(String pDescription) {
        this(pDescription, 0, 0, Constants.IGNORE_PEDAL_POSITION);
    }
    
    
    @Override
    public String toString() {
        return "BYPASS";
    }
}
