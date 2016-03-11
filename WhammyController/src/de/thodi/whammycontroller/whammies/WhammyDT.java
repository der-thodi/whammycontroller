package de.thodi.whammycontroller.whammies;

import de.thodi.whammycontroller.*;
import de.thodi.whammycontroller.effects.*;

public class WhammyDT extends Whammy {
    private static final int BYPASS_OFFSET = 21;

    private static final Effect[] effects = {
            new WhammyEffect("2 OCT UP", 1, 1 + BYPASS_OFFSET),
            new WhammyEffect("OCT UP", 2, 2 + BYPASS_OFFSET),
            new WhammyEffect("5TH UP", 3, 3 + BYPASS_OFFSET),
            new WhammyEffect("4TH UP", 4, 4 + BYPASS_OFFSET),
            new WhammyEffect("2ND DOWN", 5, 5 + BYPASS_OFFSET),
            new WhammyEffect("4TH DOWN", 6, 6 + BYPASS_OFFSET),
            new WhammyEffect("5TH DOWN", 7, 7 + BYPASS_OFFSET),
            new WhammyEffect("OCT DOWN", 8, 8 + BYPASS_OFFSET),
            new WhammyEffect("2 OCT DOWN", 9, 9 + BYPASS_OFFSET),
            new WhammyEffect("DIVE BOMB", 10, 10 + BYPASS_OFFSET),

            new DetuneEffect("DEEP", 11, 11 + BYPASS_OFFSET),
            new DetuneEffect("SHALLOW", 12, 12 + BYPASS_OFFSET),

            new HarmonyEffect("2ND UP 3RD UP", 13, 13 + BYPASS_OFFSET), };


    public WhammyDT() {
        super();
        description = "Whammy DT";
    }


    @Override
    public boolean setMode(String pMode) {
        if (pMode == null) {
            logger.warning("null mode");
            return false;
        }
        else if (pMode.equals(Constants.WHAMMY_DT)) {
            mode = pMode;
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
        return new String[] { Constants.WHAMMY_DT };
    }


    @Override
    public Effect[] getEffects() {
        return effects;
    }
}
