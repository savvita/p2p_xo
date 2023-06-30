package jui;

import controllers.GameController;
import game.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameForm extends JFrame {
    private final int WIDTH = 300;
    private final int HEIGHT = 300;
    private final int ROWS = 3;
    private final int COLUMNS = 3;
    private final int MARGIN = 10;
    private final int BUTTON_SIZE = 50;
    private JButton[][] cells;
    private JLabel msgLbl;
    private final GameController controller;
    public GameForm(GameController controller) {
        super(String.format("%s - %s", "XO", controller.getPlayer().getName()));
        super.setSize(WIDTH, HEIGHT);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = super.getContentPane();
        container.setLayout(new GridBagLayout());

        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.fill = GridBagConstraints.BOTH;
        panelConstraints.gridwidth = GridBagConstraints.REMAINDER;
        panelConstraints.weightx = 1.0f;

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        initializeCells(panel);
        container.add(panel, panelConstraints);
        msgLbl = new JLabel();
        container.add(msgLbl);
        this.controller = controller;
        this.controller.onGameUpdated.add(() -> {
            msgLbl.setText("You turn");
            updateField();
        });
        this.controller.onGameEnded.add(() -> {
            if(controller.getGame().isDraw()) {
                msgLbl.setText("Game over. It's draw!");
            } else {
                msgLbl.setText("Game over. You lost, looser!");
            }
            disableButtons();
        });
    }

    private void disableButtons() {
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                cells[i][j].setEnabled(false);
            }
        }
    }

    private void updateField() {
        Field field = controller.getGame().getField();
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                cells[i][j].setText(String.valueOf(field.getCellValue(i, j)));
                if(field.getCellValue(i, j) != field.EMPTY_SYMBOL) {
                    cells[i][j].setEnabled(false);
                } else {
                    cells[i][j].setEnabled(true);
                }
            }
        }
    }

    private void initializeCells(Container container) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
        panel.setLayout(new GridLayout(ROWS, COLUMNS));

        cells = new JButton[ROWS][COLUMNS];
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                cells[i][j] = new JButton();
                Dimension size = new Dimension(BUTTON_SIZE, BUTTON_SIZE);
                cells[i][j].setSize(size);
                cells[i][j].setMinimumSize(size);
                cells[i][j].setMaximumSize(size);
                cells[i][j].setPreferredSize(size);
                cells[i][j].setActionCommand(String.format("%d_%d", i, j));
                cells[i][j].addActionListener(new ButtonEventManager());
                panel.add(cells[i][j]);
            }
        }
        container.add(panel);
    }

    private class ButtonEventManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            disableButtons();
            msgLbl.setText("Not your turn");
            JButton btn = (JButton)e.getSource();
            btn.setText(String.valueOf(controller.getPlayer().getSymbol()));
            btn.setEnabled(false);
            var command = e.getActionCommand().split("_");
            int i = Integer.parseInt(command[0]);
            int j = Integer.parseInt(command[1]);
            controller.setValue(i, j);
            if(controller.getGame().isGameOver()) {
                if(controller.getGame().isDraw()) {
                    msgLbl.setText("Game over. It's draw!");
                } else {
                    msgLbl.setText("Game over. You win!");
                }
                disableButtons();
            }
        }
    }
}
