package de.thodi.whammycontroller.ui.swing;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;
import javax.swing.border.TitledBorder;
import de.thodi.whammycontroller.*;
import de.thodi.whammycontroller.midi.*;
import de.thodi.whammycontroller.whammies.*;
import de.thodi.whammycontroller.effects.*;
import javax.swing.*;


public class SwingFrame extends JFrame {

    private static WhammyController wc;
    private static boolean connected = false, running = false;
    private static Whammy whammy; 
    private JPanel contentPane;
    private static Logger logger = Logger
            .getLogger("de.thodi.whammycontroller");
    private JTextField delayTextField;


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
        midiPanel.setLayout(new GridLayout(3, 1, 0, 0));

        JPanel receiverPanel = new JPanel();
        midiPanel.add(receiverPanel);
        receiverPanel.setLayout(new BorderLayout(0, 0));

        JLabel lblMidiReceiver = new JLabel("Receiver");
        receiverPanel.add(lblMidiReceiver, BorderLayout.WEST);

        JComboBox<WhammyMIDIDevice> receiverComboBox = new JComboBox<WhammyMIDIDevice>();
        receiverComboBox.setMaximumRowCount(16);
        receiverPanel.add(receiverComboBox, BorderLayout.CENTER);
        initializeReceiverComboBox(receiverComboBox);

        JPanel channelPanel = new JPanel();
        midiPanel.add(channelPanel);
        channelPanel.setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel = new JLabel("Channel");
        channelPanel.add(lblNewLabel, BorderLayout.WEST);

        JComboBox<Integer> channelComboBox = new JComboBox<Integer>();
        channelPanel.add(channelComboBox, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        midiPanel.add(buttonPanel);
        buttonPanel.setLayout(new BorderLayout(0, 0));

        JButton connectButton = new JButton("Connect");
        buttonPanel.add(connectButton, BorderLayout.CENTER);

        JLabel connectedLabel = new JLabel("Not connected");
        buttonPanel.add(connectedLabel, BorderLayout.EAST);

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

        JPanel effectPanel = new JPanel();
        contentPane.add(effectPanel, BorderLayout.CENTER);
        
        JPanel runPanel = new JPanel();
        contentPane.add(runPanel, BorderLayout.SOUTH);
        runPanel.setLayout(new GridLayout(0, 2, 0, 0));
        
        JPanel delayPanel = new JPanel();
        runPanel.add(delayPanel);
        delayPanel.setLayout(new BorderLayout(0, 0));
        
        delayTextField = new JTextField();
        delayTextField.setHorizontalAlignment(SwingConstants.TRAILING);
        delayPanel.add(delayTextField, BorderLayout.CENTER);
        delayTextField.setText("1000");
        delayTextField.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("Delay (ms)");
        delayPanel.add(lblNewLabel_3, BorderLayout.WEST);
        
        JButton runButton = new JButton("Run");
        runPanel.add(runButton);
        
        initializeConnectButton(connectButton, connectedLabel);
        initializeChannelComboBox(channelComboBox);
        initializeWhammyTypeComboBox(whammyTypeComboBox,
                                     whammyModeComboBox,
                                     effectPanel);
        initializeRunButton(runButton, delayTextField);
    }


    private static void initializeReceiverComboBox(
            JComboBox<WhammyMIDIDevice> receiverComboBox) {
        WhammyMIDIDevice[] devices = WhammyMIDIDevice.getSupportedDevices();

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
                    Effect[] effects = whammy.getEffects();
                    logger.info("Available effects: " + effects.length);
                    effectPanel.removeAll();
                    effectPanel.setLayout(new GridLayout((effects.length / 2) + 1, 2));
                    for (int i = 0; i < effects.length; i++) {
                        effectPanel.add(new EffectPanel(effects[i]));
                    }
                }
            }
        });

        typeComboBox.addItem(new WhammyBass2014());
        //typeComboBox.addItem(new WhammyDT());
        typeComboBox.setSelectedIndex(0); // to fill mode combo box
    }


    private static void initializeConnectButton(final JButton button,
            final JLabel label) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!connected) {
                    label.setText("Connected");
                    button.setText("Disconnect");
                    connected = true;
                }
                else {
                    label.setText("Not connected");
                    button.setText("Connect");
                    connected = false;
                }
            }
        });
    }
    
    
    private static void initializeRunButton(final JButton runButton,
            final JTextField delayTextField) {
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!running) {
                    runButton.setText("Stop");
                    wc = new WhammyController(whammy);
                    wc.setDelay(Integer.parseInt(delayTextField.getText()));
                    Effect[] e = whammy.getEffects();
                    for (int i = 0; i< e.length; i++) {
                        logger.info(e[i] + " " + e[i].isEnabled());
                    }
                    delayTextField.setEditable(false);
                    
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
                    delayTextField.setEditable(true);
                    running = false;
                }
            }
        });
    }
    
}
