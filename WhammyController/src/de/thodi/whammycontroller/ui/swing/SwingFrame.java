package de.thodi.whammycontroller.ui.swing;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import de.thodi.whammycontroller.*;
import de.thodi.whammycontroller.midi.*;
import de.thodi.whammycontroller.whammies.*;
import de.thodi.whammycontroller.effects.*;
import javax.swing.*;


public class SwingFrame extends JFrame {

    private static WhammyController wc;
    private static boolean running = false;
    private static Whammy whammy; 
    private JPanel contentPane;
    private static Logger logger = Logger
            .getLogger("de.thodi.whammycontroller");
    private JTextField bpmTextField;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    SwingFrame frame = new SwingFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create the frame.
     */
    public SwingFrame() {
        setTitle("WhammyController");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel parameterPanel = new JPanel();
        contentPane.add(parameterPanel, BorderLayout.NORTH);
        parameterPanel.setLayout(new GridLayout(0, 2, 0, 0));

        JPanel midiPanel = new JPanel();
        parameterPanel.add(midiPanel);
        midiPanel.setBorder(new TitledBorder(null, "MIDI", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        midiPanel.setLayout(new GridLayout(2, 1, 0, 0));

        JPanel receiverPanel = new JPanel();
        midiPanel.add(receiverPanel);
        receiverPanel.setLayout(new BorderLayout(0, 0));

        JLabel lblMidiReceiver = new JLabel("Receiver");
        receiverPanel.add(lblMidiReceiver, BorderLayout.WEST);

        JComboBox<WhammyMIDIDevice> receiverComboBox = new JComboBox<WhammyMIDIDevice>();
        receiverComboBox.setMaximumRowCount(16);
        receiverPanel.add(receiverComboBox, BorderLayout.CENTER);


        JPanel channelPanel = new JPanel();
        midiPanel.add(channelPanel);
        channelPanel.setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel = new JLabel("Channel");
        channelPanel.add(lblNewLabel, BorderLayout.WEST);

        JComboBox<Integer> channelComboBox = new JComboBox<Integer>();
        channelPanel.add(channelComboBox, BorderLayout.CENTER);

        JPanel whammyPanel = new JPanel();
        parameterPanel.add(whammyPanel);
        whammyPanel.setBorder(new TitledBorder(null, "Whammy",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        whammyPanel.setLayout(new GridLayout(2, 0, 0, 0));

        JPanel whammyTypePanel = new JPanel();
        whammyPanel.add(whammyTypePanel);
        whammyTypePanel.setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel_1 = new JLabel("Type");
        whammyTypePanel.add(lblNewLabel_1, BorderLayout.WEST);

        JComboBox<Whammy> whammyTypeComboBox = new JComboBox<Whammy>();
        whammyTypePanel.add(whammyTypeComboBox, BorderLayout.CENTER);

        JPanel whammyModePanel = new JPanel();
        whammyPanel.add(whammyModePanel);
        whammyModePanel.setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel_2 = new JLabel("Mode");
        whammyModePanel.add(lblNewLabel_2, BorderLayout.WEST);

        JComboBox<String> whammyModeComboBox = new JComboBox<String>();
        whammyModePanel.add(whammyModeComboBox, BorderLayout.CENTER);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        
        JPanel effectTab = new JPanel();
        tabbedPane.addTab("Effects", null, effectTab, null);
                effectTab.setLayout(new BorderLayout(0, 0));
        
                JPanel effectPanel = new JPanel();
                effectTab.add(effectPanel);
                initializeWhammyTypeComboBox(whammyTypeComboBox,
                        whammyModeComboBox,
                        effectPanel);
                
                JPanel effectOptionPanel = new JPanel();
                effectTab.add(effectOptionPanel, BorderLayout.SOUTH);
                effectOptionPanel.setLayout(new GridLayout(0, 1, 0, 0));
                
                JCheckBox pedalPositionOptionCheckBox = new JCheckBox("Pedal: Explicitly set toe down position");
                effectOptionPanel.add(pedalPositionOptionCheckBox);


        JPanel semitoneTab = new JPanel();
        tabbedPane.addTab("Semitones", null, semitoneTab, null);
        tabbedPane.setEnabledAt(1, false);

        JPanel playerTab = new JPanel();
        tabbedPane.addTab("Pattern player", null, playerTab, null);
        tabbedPane.setEnabledAt(2, false);

        JPanel runPanel = new JPanel();
        contentPane.add(runPanel, BorderLayout.SOUTH);
        runPanel.setLayout(new GridLayout(0, 2, 0, 0));

        JPanel delayPanel = new JPanel();
        runPanel.add(delayPanel);
        delayPanel.setLayout(new BorderLayout(0, 0));

        bpmTextField = new JTextField();
        bpmTextField.setHorizontalAlignment(SwingConstants.TRAILING);
        delayPanel.add(bpmTextField, BorderLayout.CENTER);
        bpmTextField.setText("60");
        bpmTextField.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel(" BPM ");
        delayPanel.add(lblNewLabel_3, BorderLayout.WEST);

        JButton runButton = new JButton("Run");
        runPanel.add(runButton);

        initializeChannelComboBox(channelComboBox);
        initializeRunButton(runButton, bpmTextField, receiverComboBox,
                            channelComboBox, pedalPositionOptionCheckBox);
        initializeReceiverComboBox(receiverComboBox);
        
        JPanel midiTestTab = new JPanel();
        tabbedPane.addTab("MDI Test", null, midiTestTab, null);
        tabbedPane.setEnabledAt(3, false);
    }


    private static void initializeReceiverComboBox(
            JComboBox<WhammyMIDIDevice> receiverComboBox) {
        WhammyMIDIDevice[] devices = WhammyMIDIHelper.getSupportedDevices();

        for (int i = 0; i < devices.length; i++) {
            receiverComboBox.addItem(devices[i]);
        }
    }


    private static void initializeChannelComboBox(
            JComboBox<Integer> channelComboBox) {
        ((JLabel) channelComboBox.getRenderer())
                .setHorizontalAlignment(SwingConstants.RIGHT);
        for (int i = 1; i <= 16; i++) {
            channelComboBox.addItem(i);
        }
    }


    private static void initializeWhammyTypeComboBox(
            final JComboBox<Whammy> typeComboBox,
            final JComboBox<String> modeComboBox,
            final JPanel effectPanel) {
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                whammy = (Whammy) typeComboBox.getSelectedItem();

                if (whammy != null) {
                    modeComboBox.removeAllItems();
                    String[] modes = whammy.getSupportedModes();
                    for (int i = 0; i < modes.length; i++) {
                        modeComboBox.addItem(modes[i]);
                    }
                }
            }
        });

        modeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String m = (String) modeComboBox.getSelectedItem();

                if (whammy != null && m != null) {
                    whammy.setMode(m);
                    Effect[] effects = whammy.getBuiltinEffects();
                    logger.info("Available builtinEffects: " + effects.length);
                    effectPanel.removeAll();
                    effectPanel.setLayout(new GridLayout((effects.length / 2) + 1, 2));
                    for (int i = 0; i < effects.length; i++) {
                        effectPanel.add(new EffectPanel(effects[i]));
                    }
                }
            }
        });

        typeComboBox.addItem(new WhammyBass2014());
        typeComboBox.setSelectedIndex(0); // to fill mode combo box
    }
    
    
    private static void initializeRunButton(final JButton runButton,
            final JTextField bpmTextField,
            final JComboBox<WhammyMIDIDevice> receiverComboBox,
            final JComboBox<Integer> channelComboBox,
            final JCheckBox pedalPositionOptionCheckBox) {
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!running) {

                    int channel = (Integer)channelComboBox.getSelectedItem();
                    WhammyMIDIDevice midiDevice =
                            (WhammyMIDIDevice)receiverComboBox.getSelectedItem();
                    long delay = (long)(60_000 / 
                            Long.parseLong(bpmTextField.getText()));                    
                    
                    if (midiDevice == null) {
                        logger.severe("No device");
                        return;
                    }
                    
                    wc = new WhammyController(whammy);
                    wc.setMIDIDevice(midiDevice);
                    wc.setChannel(channel);
                    wc.setDelay(delay);
                    
                    Effect[] effect = whammy.getBuiltinEffects();
                    for (int i = 0; i< effect.length; i++) {
                        logger.info(effect[i] + " " + effect[i].isEnabled());
                    }
                    
                    bpmTextField.getDocument().addDocumentListener(new DocumentListener() {
                        public void changedUpdate(DocumentEvent e) {
                            updateBPM();
                          }
                          public void removeUpdate(DocumentEvent e) {
                            updateBPM();
                          }
                          public void insertUpdate(DocumentEvent e) {
                            updateBPM();
                          }

                          public void updateBPM() {
                              String bpmString = bpmTextField.getText();
                              
                              if (!bpmString.trim().isEmpty()) {
                                  long bpm = Long.parseLong(bpmString);
                                  if (bpm >= Constants.MIN_BPM &&
                                      bpm <= Constants.MAX_BPM) {
                                      wc.setDelay((long)(60_000 / bpm));
                                  }
                              }
                          }
                        });
                    
                    runButton.setText("Stop");
                    
                    if (pedalPositionOptionCheckBox.isSelected()) {
                        wc.setPedalPosition(Constants.PEDAL_POSITION_TOE_DOWN);
                    }
                    new SwingWorker<WhammyController, Void>() {
                        @Override
                        public WhammyController doInBackground() {
                            WhammyController wc2 = wc;
                            wc.run();
                            return wc2;
                        }
                    }.execute();
                    running = true;
                }
                else {
                    wc.stopRunning();
                    runButton.setText("Run");
                    running = false;
                }
            }
        });
    }
}
