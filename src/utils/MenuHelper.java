package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Utility class(static) for making menu and context menu items
 * Created by Sergiy on 11.12.2017.
 */
public class MenuHelper {

    public static JMenuItem addMenuItem(JMenu parent, String text, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(actionListener);
        parent.add(menuItem);
        return menuItem;
    }

    public static JMenuItem addMenu(JPopupMenu jPopupMenu, String title, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(actionListener);
        jPopupMenu.add(menuItem);
        return menuItem;
    }

    public static void initFileMenu(ActionListener listener, JMenuBar menuBar) {
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        addMenuItem(fileMenu, "Open File", listener);
        addMenuItem(fileMenu, "Open Directory", listener);
        JMenuItem save = addMenuItem(fileMenu, "Save File", listener);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        addMenuItem(fileMenu, "Save As...", listener);
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Exit", listener);
    }

    public static void initHelpMenu(ActionListener listener, JMenuBar menuBar) {
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        addMenuItem(helpMenu, "About", listener);
    }

    public static void initPopUpPanel(ActionListener listener, JPopupMenu popupMenu) {
        addMenu(popupMenu, "Add Recipes", listener);
        addMenu(popupMenu, "Add new recipe", listener);
        addMenu(popupMenu, "Clear List", listener);
    }
    public static void initPopUpButton(ActionListener listener, JPopupMenu popupMenu){
        addMenu(popupMenu, "Options", listener);
        addMenu(popupMenu, "Remove recipe from list", listener);
    }

    public static JMenu initPopUpSubmenu(String title, int event, JPopupMenu popupMenu) {
        JMenu submenu = new JMenu(title);
        submenu.setMnemonic(event);
        popupMenu.add(submenu);
        return submenu;
    }
    // probably delete or use in popup
    public static void showDialog(Component view, String message, String title) {
        JOptionPane.showMessageDialog(view, message,
                title, JOptionPane.INFORMATION_MESSAGE);
    }
}
