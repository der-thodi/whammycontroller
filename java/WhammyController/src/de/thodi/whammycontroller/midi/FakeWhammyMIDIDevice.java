package de.thodi.whammycontroller.midi;

import java.util.logging.Logger;
import de.thodi.whammycontroller.Constants;

public class FakeWhammyMIDIDevice implements WhammyMIDIDevice {

    private static Logger logger = Logger.getLogger("de.thodi.whammycontroller");
    
    
    public FakeWhammyMIDIDevice() {
        logger.info("constructor");
    }


    @Override
    public void setChannel(int pChannel) {
        logger.info("setChannel(" + pChannel + ")");
    }


    @Override
    public void connect() {
        logger.info("connect()");
    }


    @Override
    public void disconnect() {
        logger.info("disconnect()");
    }


    @Override
    public void sendProgramChangeMessage(int pData1) {
        sendProgramChangeMessage(pData1, 0);
    }

    
    @Override
    public void sendProgramChangeMessage(int pData1, int pData2) {
        logger.info("sendProgramChangeMessage(" + pData1 + ", " + pData2 + ")");
    }
    

    @Override
    public void sendContinuousControlMessage(int pValue) {
        sendContinuousControlMessage(Constants.MIDI_CC_COMMAND, pValue);
    }

    
    @Override
    public void sendContinuousControlMessage(int pCC, int pValue) {
        logger.info("sendContinuousControlMessage(" + pCC + ", " +
                    pValue + ")");        
    }

    @Override
    public boolean isSupported() {
        return true;
    }
    
    
    public String toString() {
        return "Internal fake MIDI testing device";
    }
}
