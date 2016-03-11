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
    private Effect currentEffect;
    
    private final int PEDAL_TOE_UP = 0;
    private final int PEDAL_TOE_DOWN = 127;

    public WhammyController(Whammy pWhammy) {
        whammy = pWhammy;
        logger.info("Using whammy '" + whammy + "'");
    }


    public void setMIDIDevice(WhammyMIDIDevice pDevice) {
        device = pDevice;
    }


    public void setEffect(Effect pEffect) {
        setEffect(pEffect, PEDAL_TOE_UP - 1);
    }
    
    
    public void setEffect(Effect pEffect, int pPedalPosition) {
        int changeNumber;
        
        logger.info("Setting effect '" + pEffect + "', pedal at '" + 
                    pPedalPosition + "'");
        if (pEffect instanceof BypassEffect)  {
            if (currentEffect == null) {
                // First ever effect is bypass -> do nothing
                return;
            }
            else {
                changeNumber = currentEffect.getBypassProgramChangeNumber();                
            }
        }
        else {
            changeNumber = pEffect.getActiveProgramChangeNumber();
            currentEffect = pEffect;
        }
        //device.sendProgramChangeMessage(changeNumber);
        if (pPedalPosition >= PEDAL_TOE_UP && pPedalPosition <= PEDAL_TOE_DOWN) {
            //device.sendContinuousControlMessage(pPedalPosition);
        }
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