package de.thodi.whammycontroller.ui.swing;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.*;
import javax.swing.border.*;
import javax.swing.event.*;
import de.thodi.whammycontroller.*;
import de.thodi.whammycontroller.midi.*;
import de.thodi.whammycontroller.whammies.*;
import de.thodi.whammycontroller.effects.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class SwingFrame extends JFrame {

    private static WhammyController wc;
    private static WhammyMIDIDevice midiDevice;
    private static int channel;
    private static boolean running = false;
    private static Whammy whammy; 
    private JPanel contentPane;
    private static Logger logger = Logger
            .getLogger("de.thodi.whammycontroller");
    private JTextField bpmTextField;
    private JTextField pcNumberTextField;
    private JTextField pcValueTextField;
    private JTextField ccTextField;
    private JTextField ccValueField;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        //logger.setLevel(Level.WARNING);
        
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
                effectOptionPanel.setBorder(new TitledBorder(null, "Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
                effectTab.add(effectOptionPanel, BorderLayout.SOUTH);
                effectOptionPanel.setLayout(new GridLayout(0, 1, 0, 0));
                
                JCheckBox pedalPositionOptionCheckBox = new JCheckBox("Pedal: Explicitly set toe down position at beginning");
                effectOptionPanel.add(pedalPositionOptionCheckBox);


        JPanel semitoneTab = new JPanel();
        tabbedPane.addTab("Semitones", null, semitoneTab, null);
        tabbedPane.setEnabledAt(1, false);

        JPanel playerTab = new JPanel();
        tabbedPane.addTab("Pattern player", null, playerTab, null);
        tabbedPane.setEnabledAt(2, true);
        playerTab.setLayout(new BorderLayout(0, 0));
        
        JPanel playerButtonPanel = new JPanel();
        playerTab.add(playerButtonPanel, BorderLayout.NORTH);
        playerButtonPanel.setLayout(new GridLayout(0, 4, 0, 0));
        
        JButton btnNewButton = new JButton("Load...");
        playerButtonPanel.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Save...");
        playerButtonPanel.add(btnNewButton_1);
        
        JTextArea patternTextArea = new JTextArea();
        patternTextArea.setLineWrap(true);
        //playerTab.add(patternTextArea, BorderLayout.SOUTH);
        
        JScrollPane scrollPane = new JScrollPane(patternTextArea);
        playerTab.add(scrollPane, BorderLayout.CENTER);

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
        tabbedPane.setEnabledAt(3, true);
        midiTestTab.setLayout(new GridLayout(3, 0, 0, 0));
        
        JPanel midiTestConnectPanel = new JPanel();
        midiTestTab.add(midiTestConnectPanel);
        
        JButton midiTestConnectButton = new JButton("Connect to MIDI device");
        midiTestConnectPanel.add(midiTestConnectButton);
        initializeMidiTestConnectButton(midiTestConnectButton, receiverComboBox,
                                        channelComboBox);
        
        JPanel midiTestPCPanel = new JPanel();
        midiTestPCPanel.setBorder(new TitledBorder(null, "MIDI program change", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        midiTestTab.add(midiTestPCPanel);
        midiTestPCPanel.setLayout(new GridLayout(2, 3, 0, 0));
        
        JLabel lblNewLabel_4 = new JLabel("Program Change #");
        midiTestPCPanel.add(lblNewLabel_4);
        
        pcNumberTextField = new JTextField();
        pcNumberTextField.setHorizontalAlignment(SwingConstants.TRAILING);
        pcNumberTextField.setText("1");
        midiTestPCPanel.add(pcNumberTextField);
        pcNumberTextField.setColumns(10);
        
        JButton pcSendButton = new JButton("Send");
        midiTestPCPanel.add(pcSendButton);

        JLabel pcValueLabel = new JLabel("Value");
        midiTestPCPanel.add(pcValueLabel);
        
        pcValueTextField = new JTextField();
        pcValueTextField.setText("0");
        pcValueTextField.setHorizontalAlignment(SwingConstants.TRAILING);
        midiTestPCPanel.add(pcValueTextField);
        pcValueTextField.setColumns(10);
  
        initializePCSendButton(pcSendButton, pcNumberTextField, pcValueTextField);
        
        JPanel midiTestCCPanel = new JPanel();
        midiTestCCPanel.setBorder(new TitledBorder(null, "MIDI continuous control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        midiTestTab.add(midiTestCCPanel);
        midiTestCCPanel.setLayout(new GridLayout(2, 3, 0, 0));
        
        JLabel lblNewLabel_5 = new JLabel("CC #");
        midiTestCCPanel.add(lblNewLabel_5);
        
        ccTextField = new JTextField();
        ccTextField.setHorizontalAlignment(SwingConstants.TRAILING);
        ccTextField.setText("11");
        midiTestCCPanel.add(ccTextField);
        ccTextField.setColumns(10);
        
        JButton ccSendButton = new JButton("Send");
        midiTestCCPanel.add(ccSendButton);
        
        JLabel lblNewLabel_6 = new JLabel("Value");
        midiTestCCPanel.add(lblNewLabel_6);
        
        ccValueField = new JTextField();
        ccValueField.setText("0");
        ccValueField.setHorizontalAlignment(SwingConstants.TRAILING);
        midiTestCCPanel.add(ccValueField);
        ccValueField.setColumns(10);
        
        initializePCSendButton(ccSendButton, ccTextField, ccValueField);
        
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        
        JMenu mnLogging = new JMenu("Log Level");
        menuBar.add(mnLogging);
        
        JRadioButtonMenuItem finestRadioButton = new JRadioButtonMenuItem("FINEST");
        mnLogging.add(finestRadioButton);
        
        JRadioButtonMenuItem infoRadioButton = new JRadioButtonMenuItem("INFO");
        infoRadioButton.setSelected(true);
        mnLogging.add(infoRadioButton);
        
        JRadioButtonMenuItem warningRadioButton = new JRadioButtonMenuItem("WARNING");
        mnLogging.add(warningRadioButton);
        
        JRadioButtonMenuItem severeRadioButton = new JRadioButtonMenuItem("SEVERE");
        mnLogging.add(severeRadioButton);
        
        ButtonGroup loglevelGroup = new ButtonGroup();
        loglevelGroup.add(finestRadioButton);
        loglevelGroup.add(infoRadioButton);
        loglevelGroup.add(warningRadioButton);
        loglevelGroup.add(severeRadioButton);
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

                    channel = (Integer)channelComboBox.getSelectedItem();
                    midiDevice =
                            (WhammyMIDIDevice)receiverComboBox.getSelectedItem();
 // TODO Catch NumberFormatException
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
                                  try {
                                  long bpm = Long.parseLong(bpmString);
                                      if (bpm >= Constants.MIN_BPM &&
                                          bpm <= Constants.MAX_BPM) {
                                          wc.setDelay((long)(60_000 / bpm));
                                      }
                                  } catch (NumberFormatException ex) {
                                      // TODO
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
    
    
    private static void initializeMidiTestConnectButton(final JButton midiTestConnectButton,
            final JComboBox<WhammyMIDIDevice> receiverComboBox,
            final JComboBox<Integer> channelComboBox) {
        channel = (Integer)channelComboBox.getSelectedItem();
        midiDevice =
                (WhammyMIDIDevice)receiverComboBox.getSelectedItem();                  
        
        if (midiDevice == null) {
            logger.severe("No device");
            return;
        }
        
        midiDevice.setChannel(channel);
    }
    
    
    private static void initializePCSendButton(final JButton pcSendButton,
            final JTextField pcNumberTextField,
            final JTextField pcValueTextField) {
        pcSendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int pPC = Integer.parseInt(pcNumberTextField.getText());
                int pValue = Integer.parseInt(pcValueTextField.getText());
                
                midiDevice.sendProgramChangeMessage(pPC, pValue);
            }
        });
    }
    
    
    private static void initializeCCSendButton(final JButton ccSendButton,
            final JTextField ccNumberTextField,
            final JTextField ccValueTextField) {
        ccSendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int pPC = Integer.parseInt(ccNumberTextField.getText());
                int pValue = Integer.parseInt(ccValueTextField.getText());
                
                midiDevice.sendContinuousControlMessage(pPC, pValue);
            }
        });
    }
}
