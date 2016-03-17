package de.thodi.whammycontroller.effects;

import de.thodi.whammycontroller.Constants;

public class Effect {
    protected String description;
    protected int activeProgramChangeNumber;
    protected int bypassProgramChangeNumber;
    protected int pedalPosition;
    protected boolean isEnabled = true;


    public Effect(String pDescription, int pActive, int pBypass) {
        this(pDescription, pActive, pBypass, Constants.IGNORE_PEDAL_POSITION);
    }
    
    
    public Effect(String pDescription, int pActive, int pBypass,
                  int pPedalPosition) {
        description = pDescription;

        if (!(this instanceof BypassEffect)) {
            if (pActive < Constants.MIDI_PC_MIN ||
                pActive > Constants.MIDI_PC_MAX) {
                throw new IllegalArgumentException("pActive not in range: " +
                                                   pActive);
            }
    
            if (pBypass < Constants.MIDI_PC_MIN ||
                pBypass > Constants.MIDI_PC_MAX) {
                throw new IllegalArgumentException("pBypass not in range: " + 
                                                   pBypass);
            }
            if (pPedalPosition != Constants.IGNORE_PEDAL_POSITION && 
                (pPedalPosition < Constants.PEDAL_POSITION_TOE_UP ||
                 pPedalPosition > Constants.PEDAL_POSITION_TOE_DOWN)) {
                throw new IllegalArgumentException("pPedalPosition not in range");               
            }
        }

        activeProgramChangeNumber = pActive;
        bypassProgramChangeNumber = pBypass;
        pedalPosition = pPedalPosition;
    }


    public int getActiveProgramChangeNumber() {
        return activeProgramChangeNumber;
    }


    public int getBypassProgramChangeNumber() {
        return bypassProgramChangeNumber;
    }


    public int getPedalPosition() {
        return pedalPosition;
    }
    
    
    public boolean isEnabled() {
        return isEnabled;
    }


    public void setEnabled(boolean pEnabled) {
        isEnabled = pEnabled;
    }


    @Override
    public String toString() {
        return description;
    }

}