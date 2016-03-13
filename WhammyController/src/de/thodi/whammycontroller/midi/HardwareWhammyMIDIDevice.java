package de.thodi.whammycontroller.midi;

import java.util.logging.*;
import javax.sound.midi.*;
import de.thodi.whammycontroller.*;

public class HardwareWhammyMIDIDevice implements WhammyMIDIDevice {

    private MidiDevice device;
    private Receiver receiver;
    private int channel;
    private boolean isInitialized = false;
    private MidiDevice.Info info;
    private ShortMessage message;
    private static Logger logger = Logger
            .getLogger("de.thodi.whammycontroller");

    
    public HardwareWhammyMIDIDevice(MidiDevice.Info pInfo) {
        info = pInfo;
    }


    @Override
    public void setChannel(int pChannel) {
        if (pChannel < Constants.MIDI_CHANNEL_MIN ||
            pChannel > Constants.MIDI_CHANNEL_MAX) {
            throw new IllegalArgumentException("MIDI channel not in range");
        }
        channel = pChannel;
    }


    @Override
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


    @Override
    public void disconnect() {
        if (isInitialized) {
            receiver.close();
            device.close();
            isInitialized = false;
        }
    }


    @Override
    public void sendProgramChangeMessage(int pData1) {
        sendProgramChangeMessage(pData1, 0);
    }


    @Override
    public void sendProgramChangeMessage(int pData1, int pData2) {
        try {
            message.setMessage(ShortMessage.PROGRAM_CHANGE, channel, pData1,
                               pData2);
            receiver.send(message, Constants.MIDI_TIMESTAMP_NOW);
        } catch (Exception ex) {
            logger.warning("Could not send pc '" + pData1 + "': " + ex);
        }
    }
    
    
    @Override
    public void sendContinuousControlMessage(int pValue) {
        sendContinuousControlMessage(Constants.MIDI_CC_COMMAND, pValue);
    }
    
    
    @Override
    public void sendContinuousControlMessage(int pCC, int pValue) {
        try {
            message.setMessage(ShortMessage.CONTROL_CHANGE, channel, pCC,
                               pValue);
            receiver.send(message, Constants.MIDI_TIMESTAMP_NOW);
        } catch (Exception ex) {
            logger.warning("Could not send cc '" + pValue + "': " + ex);
        }  
    }  
    
    
    @Override
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


 



}
