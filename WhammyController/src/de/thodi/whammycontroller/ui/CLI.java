package de.thodi.whammycontroller.ui;

import de.thodi.whammycontroller.whammies.*;
import de.thodi.whammycontroller.effects.*;
import de.thodi.whammycontroller.midi.*;
import de.thodi.whammycontroller.*;
import java.util.*;
import java.util.logging.Logger;

public class CLI {

    private static Logger logger = Logger
            .getLogger("de.thodi.whammycontroller");


    public static void main(String[] args) {
        Whammy w = new WhammyBass2014();
        w.setMode(Whammy.WHAMMY_MODE_CLASSIC);
        WhammyController c = new WhammyController(w);
        Effect[] e = w.getEffects();

        WhammyMIDIDevice[] devices = WhammyMIDIDevice.getSupportedDevices();
        if (devices.length > 0) {
            c.setMIDIDevice(devices[0]);
            c.connect();
            c.setChannel(1);
            c.setDelay(1000);
            c.run();
//            Random r = new Random();
//            for (int i = 0; i < 10; i++) {
//                c.setEffectWithDelay(e[r.nextInt(e.length)]);
//            }
        }
        else {
            logger.severe("no supported devices");
        }
    }
}
