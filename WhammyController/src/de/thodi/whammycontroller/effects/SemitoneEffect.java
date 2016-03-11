package de.thodi.whammycontroller.effects;

import de.thodi.whammycontroller.Constants;

public class SemitoneEffect extends Effect {

    public SemitoneEffect(String pDescription, int pActive, int pBypass) {
        super(pDescription, pActive, pBypass, Constants.IGNORE_PEDAL_POSITION);
    }


    public SemitoneEffect(String pDescription, int pActive, int pBypass,
                          int pPedalPosition) {
        super(pDescription, pActive, pBypass, pPedalPosition);
    }

    
    @Override
    public String toString() {
        return "SEMITONE " + super.toString();
    }
}
