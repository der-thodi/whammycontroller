package de.thodi.whammycontroller.whammies;

import de.thodi.whammycontroller.effects.*;
import java.util.logging.*;

public class Whammy {

    protected Logger logger = Logger.getLogger("de.thodi.whammycontroller");
    protected Effect[] effects;
    protected String description;
    public static String WHAMMY_DT = "WHAMMY DT";
    public static String WHAMMY_MODE_CLASSIC = "CLASSIC";
    public static String WHAMMY_MODE_CHORDS = "CHORDS";
    protected String mode;


    public Whammy() {
    }


    public boolean setMode(String pMode) {
        logger.severe("Not implemented in superclass");
        return false;
    }


    public Effect[] getEffects() {
        logger.warning("No effects in superclass");
        return effects;
    }


    @Override
    public String toString() {
        return description;
    }


    public String[] getSupportedModes() {

        return new String[] {};
    }
}
