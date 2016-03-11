package de.thodi.whammycontroller.effects;

public class Effect {

    protected String description;
    protected int activeProgramChangeNumber;
    protected int bypassProgramChangeNumber;
    protected boolean isEnabled = true;


    public Effect(String pDescription, int pActive, int pBypass) {
        description = pDescription;

        if (!(this instanceof BypassEffect)) {
            if (pActive < 1 || pActive > 128) {
                throw new IllegalArgumentException("pActive not in range");
            }
    
            if (pBypass < 1 || pBypass > 128) {
                throw new IllegalArgumentException("pBypass not in range");
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