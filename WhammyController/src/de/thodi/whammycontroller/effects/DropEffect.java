package de.thodi.whammycontroller.effects;

public class DropEffect extends Effect {

    public DropEffect(String pDescription, int pActive, int pBypass) {
        super(pDescription, pActive, pBypass);
    }


    @Override
    public String toString() {
        return "DROP " + super.toString();
    }
}
