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
                pActive > Constants.MIDI_CHANNEL_MAX) {
                throw new IllegalArgumentException("pActive not in range");
            }
    
            if (pActive < Constants.MIDI_PC_MIN ||
                pActive > Constants.MIDI_CHANNEL_MAX) {
                throw new IllegalArgumentException("pBypass not in range");
            }
            if (pPedalPosition < Constants.PEDAL_POSITION_TOE_UP ||
                pPedalPosition > Constants.PEDAL_POSITION_TOE_DOWN) {
                
            }
        }

        activeProgramChangeNumber = pActive;
        bypassProgramChangeNumber = pBypass;
    }


    public int getActiveProgramChangeNumber() {
        return activeProgramChangeNumber;
    }


    public int getBypassProgramChangeNumber() {
        return bypassProgramChangeNumber;
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