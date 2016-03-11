package de.thodi.whammycontroller;

import java.util.logging.Logger;
import java.util.*;
import de.thodi.whammycontroller.effects.*;
import de.thodi.whammycontroller.whammies.*;
import de.thodi.whammycontroller.midi.*;

public class WhammyController implements Runnable {
    private long delay;
    private Whammy whammy;
    private WhammyMIDIDevice device;
    private Logger logger = Logger.getLogger("de.thodi.whammycontroller");
    private boolean stopThread = false;


    public WhammyController(Whammy pWhammy) {
        whammy = pWhammy;
        logger.info("Using whammy '" + whammy + "'");
    }


    public void setMIDIDevice(WhammyMIDIDevice pDevice) {
        device = pDevice;
    }


    public boolean setEffect(Effect pEffect) {
        boolean result = false;

        logger.info("Setting effect '" + pEffect + "'");
        //device.sendProgramChangeMessage(pEffect.getActiveProgramChangeNumber());

        return result;
    }


    public void setChannel(int pChannel) {
        device.setChannel(pChannel);
    }


    public void connect() {
        device.connect();
    }


    public void setDelay(long pDelay) {
        if (pDelay <= 0) {
            throw new IllegalArgumentException("Delay must be > 0");
        }
        delay = pDelay;
    }


    public boolean setEffectWithDelay(Effect pEffect) {
        boolean status;

        status = setEffect(pEffect);
        if (status == true) {
            try {
                Thread.sleep(delay);
            } catch (Exception ex) {
                status = false;
            }
        }

        return status;
    }


    public void setMode(String pMode) {
        whammy.setMode(pMode);
    }

    
    public void stopRunning() {
        stopThread = true;
    }

    @Override
    public void run() {
        try {
            Vector<Effect> enabledEffects = new Vector<Effect>();
            Effect[] allEffects = whammy.getEffects();
            
            for (int i = 0; i < allEffects.length; i++) {
                if (allEffects[i].isEnabled()) {
                    enabledEffects.addElement(allEffects[i]);
                }
            }
            
            Random r = new Random();
            while (stopThread == false) {
                setEffect(enabledEffects.elementAt(r.nextInt(enabledEffects.size())));
                Thread.sleep(delay);
            }
            stopThread = false;
        }
        catch (InterruptedException ex) {
            logger.info("Interrupted: " + ex);
        }
    }
}