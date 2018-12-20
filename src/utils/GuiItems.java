package utils;

import groceries.Groceries;
import groceries.Recipe;
import groceries.RecipesList;
import interfaces.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class GuiItems {
    private static List<Recipe> allRecipes;                  // list of all recipes from the folder

    static {
        allRecipes = new ArrayList<>();
    }

    /**
     * List of buttons on the left side with the names of recipes
     *
     * @param dir
     * @param view
     */
    public static void createRecipeList(Object dir, View view) {
        try {
            RecipesList.createRecipesList(dir, allRecipes);   // going through list of paths
            allRecipes.sort(comparing(Recipe::getName).reversed());
            refreshRecipeList(view);
        } catch (Exception e) {
            e.printStackTrace();
            MenuHelper.showDialog(null, "Couldn't create recipes list.\nPlease, try again", "Error!");
        }
    }

    /**
     * List of groceries on the right side
     *
     * @param view
     */
    static void createGroceryList(View view) {
        try {
            int serving = Integer.parseInt(view.getServings().getText());           // typed amount of servings
            Groceries.mkGroceriesServings(serving);
            if (serving <= 0) {
                MenuHelper.showDialog(null, "Please, provide amount of servings", "Error!");
                return;
            }
            view.clearGroceriesList();
            Groceries.getGroceryItemList().forEach(t -> view.getGroceriesList().append(t.toString() + "\n"));
        } catch (NullPointerException np) {
            MenuHelper.showDialog(null, "Please, choose a directory first!", "Error!");
        } catch (NumberFormatException nf) {
            MenuHelper.showDialog(null, "Please, provide amount of servings", "Error!");
        }
    }

    /**
     * Helping method. Creates a new button item for every recipe in the list
     * click on button will show the recipe
     *
     * @param name
     * @param parent
     */
    public static void addNewRecipeToPanel(String name, JPanel parent, View view) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton button = new JButton(name);
        button.setPreferredSize(new Dimension(170, 20));
        Color backGround = button.getBackground();
        // button.setContentAreaFilled(false);
        button.setOpaque(true);                 //        changing button's  color

        button.addMouseListener(view.getListener());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getTextArea().setText("");                    // clean text area first
                Recipe recipe = getRecipe(e.getActionCommand());    // get recipe connected to button
                // now output text to text area
                view.getTextArea().append(String.format("Name: %s\nAuthor: %s\nDescription: %s\nTime: %s\nServings: %s\nCalories: %s\n",
                        recipe.getName(), recipe.getAuthor(), recipe.getDescription(), recipe.getTime(), recipe.getServings(), recipe.getCalories()));
                view.getTextArea().append("Ingredients: \n");
                recipe.getIngredients().forEach(i -> view.getTextArea().append(i + "\n"));
                view.getTextArea().append(String.format("Directions: %s\n", recipe.getDirections()));

                // choosing buttons by CTRL + L_click
                if ((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
                    if (!button.getBackground().equals(Color.RED)) {
                        button.setBackground(Color.RED);             // set red color "chosen" to button
                        button.setForeground(Color.RED);
                        Groceries.chooseRecipes(recipe);            // chose recipe to list

                    } else {
                        button.setBackground(backGround);
                        button.setForeground(Color.BLACK);
                        Groceries.unChoseRecipe(recipe);
                    }
                }
            }
        });
        button.setToolTipText(name);
        JPanel flowPanel = new JPanel();
        flowPanel.setLayout(new FlowLayout(1, 2, 1));
        flowPanel.add(button);
        parent.add(flowPanel, gbc, 0);
        /*parent.validate();
        parent.repaint();*/
    }

    /**
     * Clears all windows
     *
     * @param view
     */
    static void clearAllWindows(View view) {
        Groceries.clearChosenRecipes();
        view.clearGroceriesList();
        view.clearTextArea();
        view.clearRecipePanel();
        view.setVisible(true);
        view.repaint();
    }

    /**
     * Clears list of all recipes
     */
    static void clearAllRecipes() {
        allRecipes = new ArrayList<>();
    }

    /**
     * Refreshes a left list of recipes buttons
     *
     * @param view
     */
    static void refreshRecipeList(View view) {
        view.clearRecipePanel();
        allRecipes.forEach(t -> addNewRecipeToPanel(t.getName(), view.getRecipePanel(), view));
        view.setVisible(true);
        view.repaint();
    }

    /**
     * Removes a button from left list of recipes
     *
     * @param buttonName
     */
    static void removeRecipe(String buttonName) {
        allRecipes.remove(getRecipe(buttonName));
        if (!Groceries.getChosenRecipes().isEmpty())
            allRecipes.removeAll(Groceries.getChosenRecipes());
    }

    static void deleteFromDirectory(String buttonName) {
        Path recipe = GuiItems.getRecipe(buttonName).getPath();
        try {
            Files.deleteIfExists(recipe);
            if (!Groceries.getChosenRecipes().isEmpty())
                for (Recipe recipe1 : Groceries.getChosenRecipes())
                    Files.deleteIfExists(recipe1.getPath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            MenuHelper.showDialog(null, "Couldn't delete file", "Error!");
        }
    }

    /**
     * Returns a recipe by its name
     *
     * @param recipeName
     * @return
     */
    static Recipe getRecipe(String recipeName) {
        return allRecipes.stream().filter(r -> r.getName().equals(recipeName)).findAny().get();
    }
}
