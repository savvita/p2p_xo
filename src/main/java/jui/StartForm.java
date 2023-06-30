package jui;

import controllers.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import static javax.swing.SwingConstants.*;

public class StartForm extends JFrame {
    private final int WIDTH = 300;
    private final int HEIGHT = 150;
    private final int PADDING = 10;
    private final int MARGIN = 10;
    private JTextField nameTxt;
    private JButton startBtn;

    private JLabel msgLbl;

    private final GameController controller;

    public StartForm(String host, int port, int rows, int columns) {
        super("XO");
        super.setSize(WIDTH, HEIGHT);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = super.getContentPane();
        container.setLayout(new GridLayout(3, 1, 5, 5));

        addPanel(container);
        addStartBtn(container);
        addLabel(container);

        controller = new GameController(host, port, rows, columns);
        controller.onAccepted.add(() -> {
            GameForm form = new GameForm(controller);
            form.setVisible(true);
            StartForm.this.setVisible(false);
        });
    }

    private void addPanel(Container container) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(MARGIN * 2, MARGIN, MARGIN, MARGIN));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.fill = GridBagConstraints.BOTH;
        panelConstraints.gridwidth = GridBagConstraints.REMAINDER;
        panelConstraints.weightx = 1.0f;
        panelConstraints.insets = new Insets(MARGIN, MARGIN,0, MARGIN);
        panelConstraints.ipadx = PADDING;
        panelConstraints.ipady = PADDING;

        panel.add(new JLabel("Name"));
        addTextBox(panel);
        container.add(panel, panelConstraints);
    }

    private void addTextBox(Container container) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1.0f;
        constraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
        constraints.ipadx = PADDING;
        constraints.ipady = PADDING;

        nameTxt = new JTextField();

        container.add(nameTxt, constraints);
    }

    private void addStartBtn(Container container) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, MARGIN,0, MARGIN);
        constraints.anchor = GridBagConstraints.CENTER;

        startBtn = new JButton("Start");
        startBtn.addActionListener(new ButtonEventManager());

        panel.add(startBtn, constraints);
        container.add(panel);
    }

    private void addLabel(Container container) {
        msgLbl = new JLabel();
        msgLbl.setHorizontalAlignment(CENTER);
        container.add(msgLbl);
    }

    private class ButtonEventManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(nameTxt.getText().length() > 0) {
                msgLbl.setText("Waiting for other players...");
                startBtn.setEnabled(false);
                new Thread(() -> controller.addPlayer(nameTxt.getText())).start();

            } else {
                JOptionPane.showMessageDialog(null, "Enter your name", "Error", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
