package de.thodi.whammycontroller.midi;

public interface WhammyMIDIDevice {
    void setChannel(int pChannel);
    void connect();
    void disconnect();
    void sendProgramChangeMessage(int pData1);
    void sendProgramChangeMessage(int pData1, int pData2);
    void sendContinuousControlMessage(int pValue);
    void sendContinuousControlMessage(int pCC, int pValue);
    boolean isSupported();
    String toString();
}