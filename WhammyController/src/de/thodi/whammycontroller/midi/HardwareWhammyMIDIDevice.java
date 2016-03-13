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
    public void sendProgramChangeMessage(int pValue) {
        try {
            message.setMessage(ShortMessage.PROGRAM_CHANGE, channel, pValue, 0);
            receiver.send(message, 0);
        } catch (Exception ex) {
            logger.warning("Could not send pc '" + pValue + "': " + ex);
        }
    }


    @Override
    public void sendContinuousControlMessage(int pValue) {
        try {
            message.setMessage(ShortMessage.CONTROL_CHANGE, channel,
                               Constants.MIDI_CC_COMMAND, pValue);
            receiver.send(message, 0);
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
