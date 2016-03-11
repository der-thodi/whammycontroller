package de.thodi.whammycontroller.effects;

import de.thodi.whammycontroller.Constants;

public class DropEffect extends Effect {
    
    public DropEffect(String pDescription, int pActive, int pBypass,
                      int pPedalPosition) {
        super(pDescription, pActive, pBypass, pPedalPosition);
    }
    
    
    public DropEffect(String pDescription, int pActive, int pBypass) {
        this(pDescription, pActive, pBypass, Constants.IGNORE_PEDAL_POSITION);
    }


    @Override
    public String toString() {
        return "DROP " + super.toString();
    }
}
