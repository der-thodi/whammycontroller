package de.thodi.whammycontroller;

public class Constants {

    public static final String WHAMMY_DT = "WHAMMY DT";
    public static final String WHAMMY_MODE_CLASSIC = "CLASSIC";
    public static final String WHAMMY_MODE_CHORDS = "CHORDS";
    public static final int MIDI_CC_COMMAND = 11;
    public static final int PEDAL_POSITION_TOE_UP = 0;
    public static final int PEDAL_POSITION_TOE_DOWN = 127;
    public static final int IGNORE_PEDAL_POSITION = PEDAL_POSITION_TOE_UP - 1;
    public static final int MIDI_CHANNEL_MIN = 1;
    public static final int MIDI_CHANNEL_MAX = 16;
    public final static int MIDI_PC_MIN = 0;
    public final static int MIDI_PC_MAX = 127;
    public final static long MIN_BPM = 1;
    public final static long MAX_BPM = 60_000;
    public final static int MIDI_TIMESTAMP_NOW = -1;
   
}
