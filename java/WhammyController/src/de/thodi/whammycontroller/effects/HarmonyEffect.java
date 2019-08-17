package de.thodi.whammycontroller.effects;

import de.thodi.whammycontroller.Constants;

public class HarmonyEffect extends Effect {
    
    public HarmonyEffect(String pDescription, int pActive, int pBypass,
                         int pPedalPosition) {
        super(pDescription, pActive, pBypass, pPedalPosition);
    }
    
    
    public HarmonyEffect(String pDescription, int pActive, int pBypass) {
        this(pDescription, pActive, pBypass, Constants.IGNORE_PEDAL_POSITION);
    }


    @Override
    public String toString() {
        return "HARMONY " + super.toString();
    }
}
