#ifndef HARMONYEFFECT_H
#define HARMONYEFFECT_H
#include "effect.h"

class HarmonyEffect : public Effect {
public:
    using Effect::Effect;

    std::string getDescription() const override;
};

#endif
