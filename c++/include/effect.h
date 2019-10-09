#ifndef EFFECT_H
#define EFFECT_H

#include <string>
#include <ostream>
#include "constants.h"

class Effect {
protected:
    std::string description;
    int activeProgramChangeNumber;
    int bypassProgramChangeNumber;
    int pedalPosition;
    bool enabled = true;

public:
    Effect(std::string pDescription, int pActive, int pBypass, int pPedalPosition = Constants::IGNORE_PEDAL_POSITION);

    int getActiveProgramChangeNumber() const { return activeProgramChangeNumber; }
    int getBypassProgramChangeNumber() const { return bypassProgramChangeNumber; }
    int getPedalPosition() const { return pedalPosition; }
    virtual std::string getDescription() const;

    void setPedalPosition(int pPedalPosition) { pedalPosition = pPedalPosition; }
    void setEnabled(bool pEnabled) { enabled = pEnabled; }
};

#endif
