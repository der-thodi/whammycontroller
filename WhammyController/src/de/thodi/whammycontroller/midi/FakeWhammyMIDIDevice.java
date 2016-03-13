package de.thodi.whammycontroller.midi;

import java.util.logging.Logger;

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
    public void sendProgramChangeMessage(int pValue) {
        logger.info("sendProgramChangeMessage(" + pValue + ")");
    }


    @Override
    public void sendContinuousControlMessage(int pValue) {
        logger.info("sendContinuousControlMessage(" + pValue + ")");
    }


    @Override
    public boolean isSupported() {
        return true;
    }
    
    
    public String toString() {
        return "Internal fake MIDI testing device";
    }
}
