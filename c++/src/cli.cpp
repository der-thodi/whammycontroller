#include <string>
#include <iostream>

#include "constants.h"
#include "effect.h"
#include "harmonyeffect.h"

int main() {
    HarmonyEffect e1 = HarmonyEffect("bla", 1, 2);
    e1.setPedalPosition(Constants::PEDAL_POSITION_TOE_DOWN);
    e1.setEnabled(false);

    Effect e2 = Effect("blubb", 1, 2);
    e2.setPedalPosition(Constants::PEDAL_POSITION_TOE_DOWN);
    e2.setEnabled(false);

    std::cout << "e1: " << e1.getDescription() << std::endl;
    std::cout << "e2: " << e2.getDescription() << std::endl;
    return 0;
}
