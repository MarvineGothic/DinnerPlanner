package utils;

import groceries.Groceries;
import groceries.Recipe;
import interfaces.View;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dynamic {

    public static void main(String[] args) {
        new Dynamic();
    }

    public Dynamic() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }

                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {
        private JPanel mainPanel = new JPanel(new BorderLayout(2, 2));
        private JPanel recipePanel = new JPanel();
        private JScrollPane scrollRecipe ;

        public TestPane() {
            setLayout(new FlowLayout());
            mainPanel.setBorder(new TitledBorder("BorderLayout"));
            add(mainPanel);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.weighty = 1;
            recipePanel = new JPanel(new GridBagLayout());
            recipePanel.add(new JPanel(), gbc);
            scrollRecipe = new JScrollPane(recipePanel);
            scrollRecipe.setPreferredSize(new Dimension(200, 400));
            scrollRecipe.setWheelScrollingEnabled(true);
            mainPanel.add(scrollRecipe, BorderLayout.WEST);

            JButton add = new JButton("Add");
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    /*JPanel panel = new JPanel();
                    panel.add(new JButton("Hello"));
                    //panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridwidth = GridBagConstraints.REMAINDER;
                    gbc.weightx = 1;
                    gbc.fill = GridBagConstraints.HORIZONTAL;

                    JButton button = new JButton("Hello22");
                    button.setPreferredSize(new Dimension(170, 20));

                    JPanel flowPanel = new JPanel();
                    flowPanel.setLayout(new FlowLayout(1, 2, 1));
                    flowPanel.add(button);
                    mainList.add(flowPanel, gbc, 0);

                    validate();
                    repaint();*/
                    addNewRecipeToPanel("name", recipePanel);
                }
            });

            mainPanel.add(add, BorderLayout.EAST);

        }
        public void addNewRecipeToPanel(String name, JPanel parent) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JButton button = new JButton(name);
            button.setPreferredSize(new Dimension(170, 20));
            Color backGround = button.getBackground();
            // button.setContentAreaFilled(false);
            button.setOpaque(true);                 //        changing button's  color

            button.setToolTipText(name);
            JPanel flowPanel = new JPanel();
            flowPanel.setLayout(new FlowLayout(1, 2, 1));
            flowPanel.add(button);
            parent.add(flowPanel, gbc, 0);
            parent.validate();
            parent.repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }
    }
}