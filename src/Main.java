import groceries.Groceries;
import groceries.Recipe;
import groceries.RecipesList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy on 11.12.2017.
 */
public class Main {
    public static void main(String[] args) {
        String path = "./data/recipes/";
        List<Recipe> recipes = new ArrayList<Recipe>();
        RecipesList.createRecipesList(path, recipes);
        recipes.forEach(Groceries::chooseRecipes);
        //groceries.makeGroceriesList();                      // for minimal solution
        Groceries.mkGroceriesServings(100);         // comment it to check minimal solution
        Groceries.getGroceryItemList().forEach(System.out::println);

    }
}
