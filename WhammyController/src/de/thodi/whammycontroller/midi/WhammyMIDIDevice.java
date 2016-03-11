package de.thodi.whammycontroller.midi;

import java.util.*;
import java.util.logging.Logger;
import javax.sound.midi.*;

public class WhammyMIDIDevice {

    private MidiDevice device;
    private Receiver receiver;
    private int channel;
    private boolean isInitialized = false;
    private MidiDevice.Info info;
    private ShortMessage message;
    private static Logger logger = Logger
            .getLogger("de.thodi.whammycontroller");
    
    private final int WHAMMY_CC_CHANNEL = 11;


    public WhammyMIDIDevice(MidiDevice.Info pInfo) {
        info = pInfo;
    }


    public void setChannel(int pChannel) {
        if (pChannel < 1 || pChannel > 16) {
            throw new IllegalArgumentException("MIDI channel not in range");
        }
        channel = pChannel;
    }


    public void connect() {
        if (!isInitialized) {
            try {
                device = MidiSystem.getMidiDevice(info);
                device.open();
                receiver = device.getReceiver();
                message = new ShortMessage();
                isInitialized = true;
            } catch (Exception ex) {

            }
        }
    }


    public void disconnect() {
        if (isInitialized) {
            receiver.close();
            device.close();
            isInitialized = false;
        }
    }


    public void sendProgramChangeMessage(int pValue) {
        try {
            message.setMessage(ShortMessage.PROGRAM_CHANGE, channel, pValue, 0);
            receiver.send(message, 0);
        } catch (Exception ex) {
            logger.warning("Could not send pc '" + pValue + "': " + ex);
        }
    }


    public void sendContinuousControlMessage(int pValue) {
        try {
            message.setMessage(ShortMessage.CONTROL_CHANGE, channel, WHAMMY_CC_CHANNEL, pValue);
            receiver.send(message, 0);
        } catch (Exception ex) {
            logger.warning("Could not send cc '" + pValue + "': " + ex);
        }        
    }
    
    
    public boolean isSupported() {
        if (!isInitialized) {
            connect();
        }

        return (!(device instanceof Sequencer)
                && !(device instanceof Synthesizer) && !(receiver == null));
    }


    @Override
    public String toString() {
        return info.getVendor() + " " + info.getName() + " ("
                + info.getDescription() + ")";
    }


    /**
     * Static method to filter all available MIDI devices, returning only those
     * which are capable of being used for a whammy connection.
     * 
     * @return a list of supported MIDI devices
     */
    public static WhammyMIDIDevice[] getSupportedDevices() {
        MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();
        Vector<WhammyMIDIDevice> tmp = new Vector<WhammyMIDIDevice>();

        for (int i = 0; i < devices.length; i++) {
            WhammyMIDIDevice wmd = new WhammyMIDIDevice(devices[i]);
            wmd.connect();
            if (wmd.isSupported()) {
                tmp.add(wmd);
                logger.info("Supported: '" + wmd + "'");
            }
            else {
                logger.info("Not supported: '" + wmd + "'");
            }
            wmd.disconnect();
        }

        WhammyMIDIDevice[] supportedDevices = new WhammyMIDIDevice[tmp.size()];
        return tmp.toArray(supportedDevices);
    }
}
