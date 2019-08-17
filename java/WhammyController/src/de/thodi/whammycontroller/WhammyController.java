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
    //private Effect currentEffect;


    public WhammyController(Whammy pWhammy) {
        whammy = pWhammy;
        logger.info("Using whammy '" + whammy + "'");
    }


    public void setMIDIDevice(WhammyMIDIDevice pDevice) {
        logger.info("Device: '" + pDevice + "'");
        if (pDevice.isSupported()) {
            device = pDevice;
        }
        else {
            device = new FakeWhammyMIDIDevice();
        }
    }
    
    
    public void setEffect(Effect pEffect) {
        int changeNumber;
        int pedalPosition = pEffect.getPedalPosition();
        
        logger.info("Setting effect '" + pEffect + "'");
        if (pEffect instanceof BypassEffect) {
            changeNumber = Constants.MIDI_PC_MIN - 1;
            pedalPosition = Constants.PEDAL_POSITION_TOE_UP;
        }
        else {
            changeNumber = pEffect.getActiveProgramChangeNumber();
            //currentEffect = pEffect;
            pedalPosition = Constants.PEDAL_POSITION_TOE_DOWN;
        }

        if (changeNumber >= Constants.MIDI_PC_MIN && 
            changeNumber <= Constants.MIDI_PC_MAX) {
            device.sendProgramChangeMessage(changeNumber);
        }

        if (pedalPosition >= Constants.PEDAL_POSITION_TOE_UP && 
            pedalPosition <= Constants.PEDAL_POSITION_TOE_DOWN) {
            setPedalPosition(pedalPosition);
        }
        
//        if (pEffect instanceof BypassEffect)  {
//            if (currentEffect == null) {
//                // TODO
//                // First ever effect is bypass -> send no change command
//                // this is wrong, of course, since an effect could currently
//                // be selected
//                changeNumber = Constants.MIDI_PC_MIN - 1;
//            }
//            else {
//                changeNumber = currentEffect.getBypassProgramChangeNumber();                
//            }
//        }
//        else {
//            changeNumber = pEffect.getActiveProgramChangeNumber();
//            currentEffect = pEffect;
//        }
//        
//        if (changeNumber >= Constants.MIDI_PC_MIN && 
//            changeNumber <= Constants.MIDI_PC_MAX) {
//            device.sendProgramChangeMessage(changeNumber);
//        }
//        
//        if (pedalPosition >= Constants.PEDAL_POSITION_TOE_UP && 
//            pedalPosition <= Constants.PEDAL_POSITION_TOE_DOWN) {
//            setPedalPosition(pedalPosition);
//        }
    }


    public void setPedalPosition(int pPedalPosition) {
        if (pPedalPosition >= Constants.PEDAL_POSITION_TOE_UP && 
                pPedalPosition <= Constants.PEDAL_POSITION_TOE_DOWN) {
            logger.info("Setting pedal at " + pPedalPosition);
            device.sendContinuousControlMessage(pPedalPosition);
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
        logger.info("Delay: " + pDelay);
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
            Effect[] allEffects = whammy.getBuiltinEffects();
            
            for (int i = 0; i < allEffects.length; i++) {
                if (allEffects[i].isEnabled()) {
                    enabledEffects.addElement(allEffects[i]);
                }
            }
    
            
            Random r = new Random();
            
            // A little calibration
            //long millisBefore = System.currentTimeMillis();
            //setEffect(enabledEffects.elementAt(r.nextInt(enabledEffects.size())));
            //Thread.sleep(10);
            //long millisAfter = System.currentTimeMillis();
            //long epsilon = millisAfter - 10 - millisBefore;
            //logger.warning("Epsilon: " + epsilon);
            
            long epsilon = 0;
            while (stopThread == false) {
                long sleepStart = System.currentTimeMillis();
                setEffect(enabledEffects.elementAt(r.nextInt(enabledEffects.size())));
                Thread.sleep(delay - epsilon);
                long sleepDuration = System.currentTimeMillis() - sleepStart;
                if (sleepDuration != delay) {
                    //logger.warning("Took " + sleepDuration + 
                    //               " instead of " + delay);
                    if (sleepDuration > delay) {
                        epsilon = sleepDuration - delay;
                        logger.config("e -> " + epsilon);
                    }
                }
            }
            stopThread = false;
        }
        catch (InterruptedException ex) {
            logger.info("Interrupted: " + ex);
        }
    }
}