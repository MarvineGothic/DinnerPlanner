import utils.Listener;
import utils.MenuHelper;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Sergiy on 11.12.2017.
 */
public class View extends JFrame implements interfaces.View {

    private Listener listener;

    private JPanel mainPanel = new JPanel(new BorderLayout(2, 2));
    private JPanel recipePanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel centerPanel = new JPanel();

    private JTextArea textArea = new JTextArea(30, 50);
    private JTextArea groceriesList = new JTextArea(10, 20);
    private JTextField servings = new JTextField("0", 3);

    private JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    private JScrollPane scrollLeft = new JScrollPane(groceriesList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    private JScrollPane scrollRecipe = new JScrollPane(recipePanel);

    private JButton leftButton;
    private JButton clear;

    private JPopupMenu recipePopUp;
    private JPopupMenu panelPopUp;

    private GridBagConstraints gbc = new GridBagConstraints();

    public View() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        init();
        initInstances();
        initItems();
        initMenuBar();

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        View view = new View();
    }

    public void init() {
        setTitle("Dinner Planner");
        setPreferredSize(new Dimension(1100, 700));
        setMinimumSize(new Dimension(1100, 700));
        setResizable(false);
        setLayout(new FlowLayout());
        mainPanel.setBorder(new TitledBorder("BorderLayout"));
        add(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void initInstances() {
        listener = new Listener(this);
        //pop up menu
        recipePopUp = new JPopupMenu();
        panelPopUp = new JPopupMenu();
        MenuHelper.initPopUpButton(listener, recipePopUp);
        MenuHelper.initPopUpPanel(listener, panelPopUp);
        leftButton = new JButton("Create List");
        clear = new JButton("Clear List");
    }

    public void initMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        MenuHelper.initFileMenu(listener, jMenuBar);
        MenuHelper.initHelpMenu(listener, jMenuBar);
    }

    public void initItems() {

        leftPanel.setLayout(new GridLayout(0, 1, 5, 5));
        leftPanel.add(scrollLeft);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;

        recipePanel.setLayout(new GridBagLayout());
        recipePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        recipePanel.setBackground(new Color(255, 255, 255));
        recipePanel.addMouseListener(listener);
        refreshRecipePanel();

        scrollRecipe.setPreferredSize(new Dimension(200, 400));
        scrollRecipe.setWheelScrollingEnabled(true);

        topPanel.setLayout(new FlowLayout(0, 15, 10));
        topPanel.add(new JLabel("Servings"));
        topPanel.add(servings);
        topPanel.add(leftButton);
        topPanel.add(clear);

        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        groceriesList.setEditable(false);
        groceriesList.setWrapStyleWord(true);

        centerPanel.add(scroll);
        centerPanel.repaint();

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.EAST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(scrollRecipe, BorderLayout.WEST);


        leftButton.addActionListener(listener);
        clear.addActionListener(listener);
    }

    public void clearGroceriesList() {
        groceriesList.setText("");
    }

    public void clearTextArea() {
        textArea.setText("");
    }

    public void clearRecipePanel() {
        recipePanel.removeAll();
        refreshRecipePanel();
    }
    public void refreshRecipePanel(){
        recipePanel.add(new JPanel(), gbc);
    }

    // getters setters


    public JPanel getRecipePanel() {
        return recipePanel;
    }

    public Listener getListener() {
        return listener;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextArea getGroceriesList() {
        return groceriesList;
    }

    public JTextField getServings() {
        return servings;
    }

    public JPopupMenu getRecipePopUp() {
        return recipePopUp;
    }

    public JPopupMenu getPanelPopUp() {
        return panelPopUp;
    }

    public JScrollPane getScrollRecipe() {
        return scrollRecipe;
    }
}
