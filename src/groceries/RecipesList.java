package groceries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * A class that creates a list of objects Recipes
 * Created by Sergiy on 11.12.2017.
 */
public class RecipesList {

    /**
     * Creates list of all recipes from folder or adds from a single file
     */
    @SuppressWarnings("unchecked")
    public static void createRecipesList(Object dir, List<Recipe> allRecipes) {
        if (dir instanceof String) {
            walk((String) dir, "No such directory: %s\n", allRecipes);
        } else if (dir instanceof List) {
            List<File> files = List.class.cast(dir);
            for (File file : files)
                walk(file.toString(), "No such file: %s\n", allRecipes);
        }
    }

    private static void walk(String input, String msg, List<Recipe> allRecipes) {
        try (Stream<Path> paths = Files.walk(Paths.get(input))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(path -> makeAllRecipeList(path, allRecipes));
        } catch (IOException e) {
            System.out.printf(msg, input);
        }
    }

    /**
     * Method makeAllRecipeList
     * it parses file, given path as parameter and extracts all raw data from text to create a new object Recipe.
     * All objects Recipe are added to a list "createRecipeList"
     *
     * @param path
     */
    public static void makeAllRecipeList(Path path, List<Recipe> recipeList) {
        // check if same file name already in list
        boolean run = true;
        Path path2 = path.getFileName();
        for (Recipe recipe : recipeList) {
            Path path1 = recipe.getPath().getFileName();
            if (path1.equals(path2)) {
                run = false;
                break;
            }
        }
        if (run)
            try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)))) {
                String s;
                String name = "";
                String author = "";
                String description = "";
                String time = "";
                int servings = 0;
                int calories = 0;
                List<String> ingredients = new ArrayList<>();
                String directions = "";

                while ((s = reader.readLine()) != null) {
                    if (s.startsWith("Name:")) name = s.substring(5).trim();
                    else if (s.startsWith("Author:")) author = s.substring(7).trim();
                    else if (s.startsWith("Description:")) description = s.substring(12);
                    else if (s.startsWith("Time:")) time = s.substring(5).trim();
                    else if (s.startsWith("Servings:")) servings = Integer.parseInt(s.substring(9).trim());
                    else if (s.startsWith("Calories:")) calories = Integer.parseInt(s.substring(9).trim());
                    else if (s.startsWith("Ingredients:")) {
                        while ((s = reader.readLine()) != null) {
                            ingredients.add(s.trim());
                            if (s.isEmpty()) break;
                        }
                    } else if (s.startsWith("Directions:")) {
                        while ((s = reader.readLine()) != null) {
                            directions = directions.concat(s);
                        }
                    }
                }
                Recipe recipe = new Recipe(name, author, description, time, servings, calories, ingredients, directions, path);
                recipeList.add(recipe);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
