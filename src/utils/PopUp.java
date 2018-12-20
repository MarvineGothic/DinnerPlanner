package utils;

import interfaces.View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static java.awt.BorderLayout.CENTER;

/**
 * Utility class(static) for making popup windows
 */
class PopUp {
    private static JPanel gui;
    private static JPanel labelFields;
    private static JPanel fields;
    private static JPanel guiCenter;
    private static JPanel buttonConstrain;

    private static String btnString1 = "Enter";
    private static String btnString2 = "Cancel";

    /**
     * A popup window from context menu "Options"
     * will be able to specify which weekdays and servings for a recipe
     */
    static void options() {
        gui = new JPanel(new BorderLayout(2, 2));

        fields = new JPanel(new GridLayout(0, 1, 5, 5));
        TitledBorder servings = new TitledBorder("Servings");
        fields.setBorder(servings);

        // text fields for weekdays
        JTextField sMonday = new JTextField(10);
        JTextField sTuesday = new JTextField(10);
        JTextField sWednesday = new JTextField(10);
        JTextField sThursday = new JTextField(10);
        JTextField sFriday = new JTextField(10);
        JTextField sSaturday = new JTextField(10);
        JTextField sSunday = new JTextField(10);

        fields.add(sMonday);
        fields.add(sTuesday);
        fields.add(sWednesday);
        fields.add(sWednesday);
        fields.add(sThursday);
        fields.add(sFriday);
        fields.add(sSaturday);
        fields.add(sSunday);


        guiCenter = new JPanel(new BorderLayout(2, 2));
        guiCenter.setBorder(new TitledBorder("Options"));

        // check boxes for weekdays
        JPanel check = new JPanel(new GridLayout(0, 1, 5, 5));
        check.setBorder(new TitledBorder("Weekdays"));
        check.add(new JCheckBox("Monday"));
        check.add(new JCheckBox("Tuesday"));
        check.add(new JCheckBox("Wednesday"));
        check.add(new JCheckBox("Thursday"));
        check.add(new JCheckBox("Friday"));
        check.add(new JCheckBox("Saturday"));
        check.add(new JCheckBox("Sunday"));

        buttonConstrain = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonConstrain.setBorder(new TitledBorder(""));
        buttonConstrain.add(new JButton("Apply"));

        guiCenter.add(check, CENTER);
        guiCenter.add(fields, BorderLayout.EAST);
        guiCenter.add(buttonConstrain, BorderLayout.SOUTH);

        // guiCenter.add(new JScrollPane(new JTextArea(5, 30)));

        gui.add(guiCenter, CENTER);
        popUp("Options");
    }

    /**
     * A popup window from context menu "Add new recipe"
     *
     * @param listener
     * @param view
     */
    static void createRecipe(ActionListener listener, View view) {
        //1. Create GUI
        gui = new JPanel(new BorderLayout(2, 2));

        // panel for fields name, author....
        labelFields = new JPanel(new BorderLayout(2, 2));
        labelFields.setBorder(new TitledBorder("Create new groceries"));
        fields = new JPanel(new GridLayout(0, 1, 1, 1));
        fields.setBorder(new TitledBorder(""));
        JTextField name = (JTextField) addLabelField("Name", fields, 200, 20, true);
        JTextField author = (JTextField) addLabelField("Author", fields, 200, 20, true);
        JTextField description = (JTextField) addLabelField("Description", fields, 200, 20, true);
        JTextField time = (JTextField) addLabelField("Time", fields, 200, 20, true);
        JTextField servings = (JTextField) addLabelField("Servings", fields, 200, 20, true);
        servings.setToolTipText("digits only");
        JTextField calories = (JTextField) addLabelField("Calories", fields, 200, 20, true);
        calories.setToolTipText("digits only");
        labelFields.add(fields, CENTER);

        // another panel for Ingredients and Directions
        JPanel labelFields2 = new JPanel(new BorderLayout(2, 2));
        labelFields2.setBorder(new TitledBorder(""));
        JPanel fields2 = new JPanel(new GridLayout(0, 1, 1, 1));
        fields2.setBorder(new TitledBorder(""));
        // labels and text fields
        JTextPane ingredients = (JTextPane) addLabelField("Ingredients", fields2, 200, 100, false);
        ingredients.setToolTipText("Input format:  amount measure name");
        JTextPane directions = (JTextPane) addLabelField("Directions", fields2, 200, 100, false);
        labelFields2.add(fields2, CENTER);

        gui.add(labelFields, BorderLayout.NORTH);
        gui.add(labelFields2, CENTER);
        // END of GUI creation

        // 2. cancel auto-closing using PropertyChangeListener() https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#stayup
        Object[] options = {btnString1, btnString2};
        JOptionPane optionPane = new JOptionPane(gui, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION,
                null, options, options[0]);
        JDialog dialog = new JDialog((Frame) null, "Create groceries", true);
        dialog.setContentPane(optionPane);
        dialog.setLocationRelativeTo(null);
        /*dialog.setDefaultCloseOperation(
                JDialog.DO_NOTHING_ON_CLOSE);*/
        optionPane.addPropertyChangeListener(
                new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        String prop = e.getPropertyName();

                        if (dialog.isVisible() && (e.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(prop)
                                || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
                            Object value = optionPane.getValue();
                            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                                return;
                            }
                            optionPane.setValue(
                                    JOptionPane.UNINITIALIZED_VALUE);

                            if (btnString1.equals(value)) {     // btn "Enter"
                                if (name.getText().isEmpty() || author.getText().isEmpty() || description.getText().isEmpty()
                                        || time.getText().isEmpty() || calories.getText().isEmpty() ||
                                        ingredients.getText().isEmpty() || directions.getText().isEmpty()) {
                                    MenuHelper.showDialog(null, "some fields are empty or wrong format", "Error");
                                          /* dialog = JOptionPane.showConfirmDialog(null, gui, "Create groceries", JOptionPane.OK_CANCEL_OPTION);*/
                                } else {
                                    String text = String.format("Name: %s\nAuthor: %s\nDescription: %s\nTime: %s" +
                                                    "\nServings: %s\nCalories: %s\n\nIngredients:\n%s\n\nDirections: \n%s\n",
                                            name.getText(), author.getText(), description.getText(), time.getText(),
                                            servings.getText(), calories.getText(), ingredients.getText(), directions.getText());
                                    MenuController.saveDocumentAs(text, view);
                                    dialog.setVisible(false);
                                    if (MenuController.getFileChooser() != null) {
                                        GuiItems.clearAllWindows(view);
                                        MenuController.createGroceries(view);
                                    }
                                }
                            } else dialog.setVisible(false);   // btn "Cancel"
                        }
                    }
                });

        dialog.pack();
        dialog.setVisible(true);
    }

    /**
     * Method deletes recipe from list or also from a hard disk
     *
     * @param buttonName - name of button and a recipe to delete
     * @param view       - main window GUI
     */
    static void deleteRecipe(String buttonName, View view) {
        //"Do you want to remove this item?"
        gui = new JPanel(new BorderLayout(2, 2));
        JLabel howToDelete = new JLabel("How do you want to remove recipe?");
        JRadioButton hardDisk = new JRadioButton("Permanently delete also from hard disk", false);
        JRadioButton recipeList = new JRadioButton("Only from this list", true);
        ButtonGroup group = new ButtonGroup();
        group.add(hardDisk);
        group.add(recipeList);
        gui.add(howToDelete, BorderLayout.NORTH);
        gui.add(hardDisk, BorderLayout.CENTER);
        gui.add(recipeList, BorderLayout.SOUTH);

        int dialog = JOptionPane.showConfirmDialog(null, gui, "Remove recipe from list", JOptionPane.YES_NO_OPTION);
        if (dialog == JOptionPane.YES_OPTION) {
            if (hardDisk.isSelected()) {
                int confirm = JOptionPane.showConfirmDialog(null, "You will permanently delete chosen files from disk.\nAre you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION)
                    GuiItems.deleteFromDirectory(buttonName);
                else
                    return;
            }
            GuiItems.removeRecipe(buttonName);
            GuiItems.clearAllWindows(view);
            GuiItems.refreshRecipeList(view);
            JOptionPane.showConfirmDialog(null, "Recipe successfully removed", "Confirmation!", JOptionPane.DEFAULT_OPTION);
        }
    }

    // TODO: 07.01.2018 remove
    // helping methods
    @Deprecated
    private static void popUp(String title) {
        JOptionPane.showConfirmDialog(null, gui, title, JOptionPane.OK_CANCEL_OPTION);
    }

    /**
     * Helping method, adds a label and a JTextField/JTextPane (depends on choice) to a JPanel(FlowLayout)
     *
     * @param label     a label of a text field
     * @param parent    - JPanel, which gets all content
     * @param width     - width of a text field
     * @param height    - height of a text field
     * @param textField - choice of a JTextField/JTextPane
     * @return component - is a JTextField or a JTextPane depending on choice implementation
     */
    private static Component addLabelField(String label, JPanel parent, int width, int height, boolean textField) {
        Component component;
        JPanel flow = new JPanel(new FlowLayout(0, 2, 2));
        JPanel border = new JPanel(new BorderLayout(2, 2));
        flow.add(border);

        JLabel lb = new JLabel(label);
        lb.setPreferredSize(new Dimension(100, 20));
        border.add(lb, BorderLayout.WEST);
        if (textField) {
            JTextField field = new JTextField();
            field.setPreferredSize(new Dimension(width, height));
            border.add(field, CENTER);
            component = field;
        } else {
            JTextPane textPane = new JTextPane();
            JScrollPane scrollPane = new JScrollPane(textPane);
            scrollPane.setPreferredSize(new Dimension(width, height));
            border.add(scrollPane, CENTER);
            component = textPane;
        }
        parent.add(flow);
        return component;
    }
    /*
    template
    gui = new JPanel(new BorderLayout(2, 2));

        labelFields = new JPanel(new BorderLayout(2, 2));
        labelFields.setBorder(new TitledBorder("BorderLayout"));

        labels = new JPanel(new GridLayout(0, 1, 1, 1));
        labels.setBorder(new TitledBorder("GridLayout"));
        fields = new JPanel(new GridLayout(0, 1, 1, 1));
        fields.setBorder(new TitledBorder("GridLayout"));

        for (int ii = 1; ii < 4; ii++) {
            labels.add(new JLabel("Label " + ii));
            // if these were of different size, it would be necessary to
            // constrain them using another panel
            fields.add(new JTextField(10));
        }

        labelFields.add(labels, BorderLayout.CENTER);
        labelFields.add(fields, BorderLayout.EAST);

        guiCenter = new JPanel(new BorderLayout(2, 2));
        guiCenter.setBorder(new TitledBorder("BorderLayout"));
        buttonConstrain = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonConstrain.setBorder(new TitledBorder("FlowLayout"));
        buttonConstrain.add(new JButton("Click Me"));
        guiCenter.add(buttonConstrain, BorderLayout.NORTH);

        guiCenter.add(new JScrollPane(new JTextArea(5, 30)));

        gui.add(labelFields, BorderLayout.NORTH);
        gui.add(guiCenter, BorderLayout.CENTER);
        popUp("Create groceries");
    * */
}
