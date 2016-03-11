package de.thodi.whammycontroller.whammies;

import de.thodi.whammycontroller.effects.*;
import de.thodi.whammycontroller.*;

public class WhammyBass2014 extends Whammy {
    private static final int BYPASS_OFFSET = 21;

    private static final Effect[] CLASSIC_MODE_EFFECTS = {
            new WhammyEffect("2 OCT UP", 1, 1 + BYPASS_OFFSET),
            new WhammyEffect("OCT UP", 2, 2 + BYPASS_OFFSET),
            new WhammyEffect("5TH UP", 3, 3 + BYPASS_OFFSET),
            new WhammyEffect("4TH UP", 4, 4 + BYPASS_OFFSET),
            new WhammyEffect("2ND UP", 5, 5 + BYPASS_OFFSET),
            new WhammyEffect("2DN DOWN", 6, 6 + BYPASS_OFFSET),
            new WhammyEffect("4TH DOWN", 7, 7 + BYPASS_OFFSET),
            new WhammyEffect("5TH DOWN", 8, 8 + BYPASS_OFFSET),
            new WhammyEffect("OCT DOWN", 9, 9 + BYPASS_OFFSET),
            new WhammyEffect("DIVE BOMB", 10, 10 + BYPASS_OFFSET),
            new DetuneEffect("DEEP", 11, 11 + BYPASS_OFFSET),
            new DetuneEffect("SHALLOW", 12, 12 + BYPASS_OFFSET),
            new HarmonyEffect("4TH DOWN 3RD UP", 13, 13 + BYPASS_OFFSET),
            new HarmonyEffect("4TH DOWN 5TH UP", 14, 14 + BYPASS_OFFSET),
            new HarmonyEffect("5TH DOWN 5TH UP", 15, 15 + BYPASS_OFFSET),
            new HarmonyEffect("4TH DOWN 3RD UP", 16, 16 + BYPASS_OFFSET),
            new HarmonyEffect("5TH UP OCT UP", 17, 17 + BYPASS_OFFSET),
            new HarmonyEffect("OCT DOWN 4TH DOWN", 18, 18 + BYPASS_OFFSET),
            new HarmonyEffect("OCT DOWN OCT UP", 19, 19 + BYPASS_OFFSET),
            new HarmonyEffect("OCT UP 10TH UP", 20, 20 + BYPASS_OFFSET),
            new HarmonyEffect("OCT UP 2OCT UP", 21, 21 + BYPASS_OFFSET),
            new BypassEffect("Bypass")
    };
    private static final Effect[] CHORDS_MODE_EFFECTS = {
            new WhammyEffect("2 OCT UP", 43, 43 + BYPASS_OFFSET),
            new WhammyEffect("OCT UP", 44, 44 + BYPASS_OFFSET),
            new WhammyEffect("5TH UP", 45, 45 + BYPASS_OFFSET),
            new WhammyEffect("4TH UP", 46, 46 + BYPASS_OFFSET),
            new WhammyEffect("2ND UP", 47, 47 + BYPASS_OFFSET),
            new WhammyEffect("2DN DOWN", 48, 48 + BYPASS_OFFSET),
            new WhammyEffect("4TH DOWN", 49, 49 + BYPASS_OFFSET),
            new WhammyEffect("5TH DOWN", 50, 50 + BYPASS_OFFSET),
            new WhammyEffect("OCT DOWN", 51, 51 + BYPASS_OFFSET),
            new WhammyEffect("DIVE BOMB", 52, 52 + BYPASS_OFFSET),
            new DetuneEffect("DEEP", 53, 53 + BYPASS_OFFSET),
            new DetuneEffect("SHALLOW", 54, 54 + BYPASS_OFFSET),
            new HarmonyEffect("4TH DOWN 3RD UP", 55, 55 + BYPASS_OFFSET),
            new HarmonyEffect("4TH DOWN 5TH UP", 56, 56 + BYPASS_OFFSET),
            new HarmonyEffect("5TH DOWN 5TH UP", 57, 57 + BYPASS_OFFSET),
            new HarmonyEffect("4TH DOWN 3RD UP", 58, 58 + BYPASS_OFFSET),
            new HarmonyEffect("5TH UP OCT UP", 59, 59 + BYPASS_OFFSET),
            new HarmonyEffect("OCT DOWN 4TH DOWN", 60, 60 + BYPASS_OFFSET),
            new HarmonyEffect("OCT DOWN OCT UP", 61, 61 + BYPASS_OFFSET),
            new HarmonyEffect("OCT UP 10TH UP", 62, 62 + BYPASS_OFFSET),
            new HarmonyEffect("OCT UP 2OCT UP", 63, 63 + BYPASS_OFFSET),
            new BypassEffect("Bypass")
    };
    private static final Effect[] CLASSIC_MODE_SEMITONE_EFFECTS = {
            new SemitoneEffect("1 UP", 5, 5 + BYPASS_OFFSET,
                               Constants.PEDAL_POSITION_TOE_DOWN / 2),
            new SemitoneEffect("2 UP", 5, 5 + BYPASS_OFFSET,
                               Constants.PEDAL_POSITION_TOE_DOWN),
            // TODO 3,
            // TODO 4,
            new SemitoneEffect("5 UP", 4, 4 + BYPASS_OFFSET,
                               Constants.PEDAL_POSITION_TOE_DOWN),
            // TODO 6,
            new SemitoneEffect("7 UP", 3, 3 + BYPASS_OFFSET,
                               Constants.PEDAL_POSITION_TOE_DOWN),
            // TODO 8,
            // TODO 9,
            // TODO 10,
            // TODO 11,
            new WhammyEffect("12 UP", 2, 2 + BYPASS_OFFSET,
                             Constants.PEDAL_POSITION_TOE_DOWN),  
    };
    private static final Effect[] CHORDS_MODE_SEMITONE_EFFECTS = {
            new SemitoneEffect("1 UP", 47, 47 + BYPASS_OFFSET,
                               Constants.PEDAL_POSITION_TOE_DOWN / 2),
            new SemitoneEffect("2 UP", 47, 47 + BYPASS_OFFSET,
                               Constants.PEDAL_POSITION_TOE_DOWN),
            // TODO 3,
            // TODO 4,
            new SemitoneEffect("5 UP", 46, 46 + BYPASS_OFFSET,
                               Constants.PEDAL_POSITION_TOE_DOWN),
            // TODO 6,
            new SemitoneEffect("7 UP", 45, 45 + BYPASS_OFFSET,
                               Constants.PEDAL_POSITION_TOE_DOWN),
            // TODO 8,
            // TODO 9,
            // TODO 10,
            // TODO 11,
            new WhammyEffect("12 UP", 44, 44 + BYPASS_OFFSET,
                             Constants.PEDAL_POSITION_TOE_DOWN),  
    };    
    

    public WhammyBass2014() {
        super();
        description = "Bass Whammy (2014)";
    }


    @Override
    public boolean setMode(String pMode) {
        if (pMode == null) {
            logger.warning("null mode");
            return false;
        }
        else if (pMode.equals(Constants.WHAMMY_MODE_CHORDS)) {
            mode = pMode;
            builtinEffects = CHORDS_MODE_EFFECTS;
            semitoneEffects = CHORDS_MODE_SEMITONE_EFFECTS;
        }
        else if (pMode.equals(Constants.WHAMMY_MODE_CLASSIC)) {
            mode = pMode;
            builtinEffects = CLASSIC_MODE_EFFECTS;
            semitoneEffects = CLASSIC_MODE_SEMITONE_EFFECTS;
        }
        else {
            throw new IllegalArgumentException(
                    "Unsupported mode '" + pMode + "'");
        }
        logger.info("Mode now '" + mode + "'");
        return true;
    }


    @Override
    public String[] getSupportedModes() {
        return new String[] { Constants.WHAMMY_MODE_CHORDS, 
                              Constants.WHAMMY_MODE_CLASSIC };
    }


    @Override
    public Effect[] getBuiltinEffects() {
        return builtinEffects;
    }
    
    
    public Effect[] getSemitoneEffects() {
        return semitoneEffects;
    }
}
