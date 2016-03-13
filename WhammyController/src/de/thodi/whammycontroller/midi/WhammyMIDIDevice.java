package de.thodi.whammycontroller.midi;

public interface WhammyMIDIDevice {
    void setChannel(int pChannel);
    void connect();
    void disconnect();
    void sendProgramChangeMessage(int pValue);
    void sendContinuousControlMessage(int pValue);
    boolean isSupported();
    String toString();
}