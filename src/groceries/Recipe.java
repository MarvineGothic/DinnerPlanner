package groceries;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sergiy on 11.12.2017.
 * Class groceries.Recipe
 * Creates an object of recipes.
 */
public class Recipe {
    private static final Pattern ITEM = Pattern.compile("(\\d+[.]?\\d*)\\s+([-]|\\w+|fluid\\s+ounce\\w+)\\s+((?:.+\\s*)+)");
    private String name;
    private String author;
    private String description;
    private String time;
    private int servings;
    private int calories;
    private List<String> ingredients;
    private String directions;
    private Path path;
    private List<GroceryItem> groceryItems;
    private Matcher matcher;


    public Recipe(String name, String author, String description, String time, int servings, int calories, List<String> ingredients, String directions, Path path) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.time = time;
        this.servings = servings;
        this.calories = calories;
        this.ingredients = ingredients;
        this.directions = directions;
        this.path = path;
        groceryItems = new ArrayList<>();
        setGroceryItems(groceryItems);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public List<GroceryItem> getGroceryItems() {
        return groceryItems;
    }

    public void setGroceryItems(List<GroceryItem> groceryItems) {
        ingredients.forEach(i -> {
            if ((matcher = ITEM.matcher(i)).matches())
                groceryItems.add(new GroceryItem(matcher.group(3), matcher.group(2), Double.parseDouble(matcher.group(1))));
        });
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getDirections() {
        return directions;
    }

    public Path getPath() {
        return path;
    }

    public String toString() {
        return String.format("Name: %s\nAuthor: %s\ndescription: %s\nTime: %s\nServings: %d\nCalories: %d\nIngtedients: %s\nDirections: %s\n\n",
                this.getName(), this.getAuthor(), this.getDescription(), this.getTime(), this.getServings(),
                this.getCalories(), this.getIngredients().toString(), this.getDirections());
    }

}
