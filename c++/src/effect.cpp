#include "effect.h"
#include "constants.h"

Effect::Effect(std::string pDescription, int pActive, int pBypass, int pPedalPosition)
{
    description = pDescription;
    activeProgramChangeNumber = pActive;
    bypassProgramChangeNumber = pBypass;
    pedalPosition = pPedalPosition;
}

std::string Effect::getDescription() const
{
    return "Effect(" + description + ")";
}
