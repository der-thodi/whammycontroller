package de.thodi.whammycontroller.whammies;

import de.thodi.whammycontroller.effects.*;
import java.util.logging.*;

public class Whammy {

    protected Logger logger = Logger.getLogger("de.thodi.whammycontroller");
    protected Effect[] builtinEffects;
    protected Effect[] semitoneEffects;
    protected String description;
    protected String mode;
    

    public Whammy() {
    }


    public boolean setMode(String pMode) {
        logger.severe("Not implemented in superclass");
        return false;
    }


    public Effect[] getBuiltinEffects() {
        logger.warning("No builtinEffects in superclass");
        return builtinEffects;
    }


    @Override
    public String toString() {
        return description;
    }


    public String[] getSupportedModes() {
        return new String[] {};
    }
}
