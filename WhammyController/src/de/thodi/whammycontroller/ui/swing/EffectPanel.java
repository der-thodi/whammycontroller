package de.thodi.whammycontroller.ui.swing;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import de.thodi.whammycontroller.effects.*;

public class EffectPanel extends JPanel implements ChangeListener {

    private JCheckBox checkBox;
    private Effect effect;
    
    public EffectPanel(Effect pEffect) {
        super();
        effect = pEffect;
        
        setLayout(new BorderLayout());
        checkBox = new JCheckBox();
        checkBox.setSelected(effect instanceof WhammyEffect);
        effect.setEnabled(effect instanceof WhammyEffect);
        checkBox.setEnabled(true);
        checkBox.addChangeListener(this);
        add(checkBox, BorderLayout.WEST);
        add(new JLabel(effect.toString()), BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JCheckBox checkBox = (JCheckBox)e.getSource();
        effect.setEnabled(checkBox.isSelected());
    }
}
