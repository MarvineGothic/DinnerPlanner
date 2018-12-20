package groceries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Comparator.comparing;

/**
 * Created by Sergiy on 11.12.2017.
 */
public class Groceries {
    private static List<GroceryItem> groceryItemList;        // list of groceries for shopping list
    private static List<Recipe> chosenRecipes;

    static {
        clearChosenRecipes();
    }

    /**
     * Minimal solution
     * creates a list of all groceryItemList
     */
    public static void makeGroceriesList() {
        groceryItemList = new ArrayList<>();
        chosenRecipes.forEach(r -> groceryItemList.addAll(r.getGroceryItems()));
    }

    /**
     * Extensions 1, 2, 3
     * This method:
     * Allows user to specify, how many servings are required (Extension 1)
     * Merges identical ingredients from the allRecipes (Extension 2)
     * Uses the plural when items are more than 1 (Extension 3)
     *
     * @param newServings
     */
    public static void mkGroceriesServings(int newServings) {
        groceryItemList = new ArrayList<>();                                      // list of groceries for shopping
        for (Recipe recipe : chosenRecipes) {                                           // allRecipes from allRecipes list
            double servings = recipe.getServings();                               // initial servings amount
            for (GroceryItem baseItem : recipe.getGroceryItems()) {               // list of groceryItems from each recipe
                double amount = baseItem.getAmount() * (newServings / servings);  // new amount of item based on servings
                GroceryItem tempItem;
                if (!groceryItemList.isEmpty()) {                                 // check if the list is still empty
                    Iterator<GroceryItem> it = groceryItemList.iterator();

                    while (it.hasNext()) {
                        tempItem = it.next();
                        if (tempItem.getName().equals(baseItem.getName()) && !tempItem.getUnit().equals("bunch") && !baseItem.getUnit().equals("bunch")) {
                            if (!tempItem.getUnit().equals(baseItem.getUnit()))
                                Converter.fitUnits(tempItem, baseItem);
                            amount = baseItem.getAmount() * (newServings / servings) + tempItem.getAmount();
                            it.remove();
                        }
                    }
                }

                tempItem = new GroceryItem(baseItem.getName(), baseItem.getUnit(), amount);
                groceryItemList.add(tempItem);
            }
        }
       groceryItemList.sort(comparing(GroceryItem::getName));
        convert();
    }

    /**
     * Chose recipes to create a list of groceries
     *
     * @param chosenRecipe
     */
    public static void chooseRecipes(Recipe chosenRecipe) {
        chosenRecipes.add(chosenRecipe);
    }

    /**
     * Remove recipe from chosen ones
     *
     * @param unChosenRecipe
     */
    public static void unChoseRecipe(Recipe unChosenRecipe) {
        chosenRecipes.remove(unChosenRecipe);
    }

    /**
     * Clears chosenRecipes list
     */
    public static void clearChosenRecipes() {
        chosenRecipes = new ArrayList<>();
    }

    public static void convert() {
        groceryItemList.forEach(Converter::convertUnits);
    }

    public static List<GroceryItem> getGroceryItemList() {
        return groceryItemList;
    }

    public static List<Recipe> getChosenRecipes() {
        return chosenRecipes;
    }
}
