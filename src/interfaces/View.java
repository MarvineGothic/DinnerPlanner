package interfaces;

import utils.Listener;

import javax.swing.*;

public interface View {

    JTextArea getTextArea();

    JPopupMenu getPanelPopUp();

    JPopupMenu getRecipePopUp();

    JPanel getRecipePanel();

    JTextField getServings();

    Listener getListener();

    JTextArea getGroceriesList();

    JScrollPane getScrollRecipe();

    void clearGroceriesList();

    void clearTextArea();

    void clearRecipePanel();

    void repaint();

    void setVisible(boolean isVisible);
}
