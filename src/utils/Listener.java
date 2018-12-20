package utils;

import interfaces.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Listener implements ActionListener, MouseListener {
    private View view;
    private String buttonName="";

    public Listener(View view) {
        this.view = view;
    }

    // ActionListener
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        switch (action) {
            // menu tab
            case "Open File":
                MenuController.openFile(view);
                break;
            case "Open Directory":
            case "Add Recipes":
                MenuController.openDocument(view);
                break;
            case "Save File":
                MenuController.saveDocument(view);
                break;
            case "Save As...":
                MenuController.saveDocumentAs(view.getTextArea().getText(), view);
                break;
            case "Exit":
                System.exit(0);
                break;
            case "About":
                MenuHelper.showDialog(null, "Dinner Planner\nby Sergiy Isakov", "About");
                break;
            // buttons on window
            case "Create List":
                GuiItems.createGroceryList(view);
                break;
            case "Clear List":
                GuiItems.clearAllWindows(view);
                GuiItems.clearAllRecipes();
                break;
            // recipe context menu
            case "Options":
                PopUp.options();
                break;
            case "Add new recipe":
                PopUp.createRecipe(this, view);
                break;
            case "Remove recipe from list":
                if (!buttonName.isEmpty())
                PopUp.deleteRecipe(buttonName, view);
                break;
        }

    }

    // MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {
        int mod = e.getModifiers();
        String eName = e.getSource().getClass().getSimpleName();
        switch (mod) {
            case 16:

                break;
            case 4:
                switch (eName) {
                    case "JPanel":
                        view.getPanelPopUp().show(e.getComponent(), e.getX(), e.getY());
                        break;
                    case "JButton":
                        view.getRecipePopUp().show(e.getComponent(), e.getX(), e.getY());
                        buttonName = ((JButton) e.getSource()).getText();
                        break;
                }
                break;

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
