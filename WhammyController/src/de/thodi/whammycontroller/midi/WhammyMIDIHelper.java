package de.thodi.whammycontroller.midi;

import java.util.Vector;
import java.util.logging.Logger;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;

public class WhammyMIDIHelper {
    
    private static Logger logger = Logger
            .getLogger("de.thodi.whammycontroller");

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
            WhammyMIDIDevice wmd = new HardwareWhammyMIDIDevice(devices[i]);
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
        tmp.add(new FakeWhammyMIDIDevice());

        WhammyMIDIDevice[] supportedDevices = new WhammyMIDIDevice[tmp.size()];
        return tmp.toArray(supportedDevices);
    }
    
}
