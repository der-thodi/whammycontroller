#ifndef CONSTANTS_H
#define CONSTANTS_H

#include <string>

class Constants {

public:
    static const std::string WHAMMY_DT;
    static const std::string WHAMMY_MODE_CLASSIC;
    static const std::string WHAMMY_MODE_CHORDS;

    static const unsigned int MIDI_CC_COMMAND = 11;
    static const int PEDAL_POSITION_TOE_UP = 0;
    static const int PEDAL_POSITION_TOE_DOWN = 127;
    static const int IGNORE_PEDAL_POSITION = PEDAL_POSITION_TOE_UP - 1;
    static const unsigned int MIDI_CHANNEL_MIN = 1;
    static const unsigned int MIDI_CHANNEL_MAX = 16;
    static const unsigned int MIDI_PC_MIN = 0;
    static const unsigned int MIDI_PC_MAX = 127;
    static const unsigned int MIN_BPM = 1;
    static const unsigned int MAX_BPM = 60000;
    static const int MIDI_TIMESTAMP_NOW = -1;
};

#endif
