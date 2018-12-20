package Tests;

import Tests.TestData.RecordingOutputStream;
import groceries.*;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GroceriesTest {
    static PrintStream orgout;
    static RecordingOutputStream record;
    static String[] path;

    @org.junit.BeforeClass
    public static void setUpStdOut() {
        orgout = System.out;
        record = new RecordingOutputStream(orgout);
        System.setOut(new java.io.PrintStream(record));
        path = new String[]{"C:/Users/Admin/IdeaProjects/DinnerPlanner/src/Tests/TestData/data/to-die-for-fettuccine-alfredo.txt",
                "C:/Users/Admin/IdeaProjects/DinnerPlanner/src/Tests/TestData/data/data1/worlds-best-lasagna.txt",
                "C:/Users/Admin/IdeaProjects/DinnerPlanner/src/Tests/TestData/data/data1/broiled-tilapia-parmesan.txt",
                "C:/Users/Admin/IdeaProjects/DinnerPlanner/src/Tests/TestData/data/data1/baked-teriyaki-chicken.txt"
        };
    }

    @org.junit.AfterClass
    public static void tearDownStdOut() {
        System.setOut(orgout);
        record = null;
    }

    public static void main(String[] args) {
        Result result = org.junit.runner.JUnitCore.runClasses(GroceriesTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            break;
        }
        System.out.printf("Tests run: %d,  Failures: %d/n", result.getRunCount(), result.getFailureCount());
    }

    @Test
    public void convertUnits() {
        record.start();
        List<Recipe> allRecipes = new ArrayList<>();
        RecipesList.createRecipesList(path[2], allRecipes);
        Groceries.getChosenRecipes().addAll(allRecipes);
        Groceries.makeGroceriesList();
        for (GroceryItem item : Groceries.getGroceryItemList()) {
            Converter.convertUnits(item);
            System.out.printf("%s %s %s\n", item.getAmount(), item.getUnit(), item.getName());
        }
        Groceries.clearChosenRecipes();
        assertEquals("0.5 cup Parmesan\n" +
                "0.25 cup butter\n" +
                "1.5 ounce mayonnaise\n" +
                "1.0 fluid ounce lemon juice\n" +
                "2.0 dash basil\n" +
                "2.0 dash black pepper\n" +
                "1.0 pinch onion powder\n" +
                "1.0 pinch celery salt\n" +
                "2.0 pound tilapia fillets\n", record.stop().replaceAll("\r", ""));
    }

    @Test
    public void makeGroceriesList() throws Exception {
        record.start();
        for (int i = 0; i < path.length - 1; i++) {
            List<Recipe> allRecipes = new ArrayList<>();
            RecipesList.createRecipesList(path[i], allRecipes);
            Groceries.getChosenRecipes().addAll(allRecipes);
            Groceries.makeGroceriesList();

            Groceries.getGroceryItemList().forEach(System.out::println);
            System.out.println("-------------------------------------");
            Groceries.clearChosenRecipes();
        }
        assertEquals("1.5     pounds       dry fettuccini\n" +
                "1       cup          butter\n" +
                "0.75    pint         cream\n" +
                "1       dash         garlic salt\n" +
                "0.75    cup          Romano\n" +
                "0.5     cup          Parmesan\n" +
                "-------------------------------------\n" +
                "1       pound        sweet Italian sausage\n" +
                "0.75    pound        ground beef\n" +
                "0.5     cup          onion\n" +
                "2       cloves       garlic\n" +
                "3.5     cups         canned tomatoes\n" +
                "12      ounces       tomato paste\n" +
                "13      ounces       tomato sauce\n" +
                "0.5     cup          water\n" +
                "2       tablespoons  sugar\n" +
                "1.5     teaspoons    basil\n" +
                "0.5     teaspoon     fennel seeds\n" +
                "1       teaspoon     Italian seasoning\n" +
                "1       tablespoon   salt\n" +
                "2       dashs        black pepper\n" +
                "4       tablespoons  parsley\n" +
                "12      pieces       lasagna noodles\n" +
                "16      ounces       ricotta\n" +
                "1       piece        egg\n" +
                "0.5     teaspoon     salt\n" +
                "0.75    pound        mozzarella\n" +
                "0.75    cup          Parmesan\n" +
                "-------------------------------------\n" +
                "0.5     cup          Parmesan\n" +
                "0.25    cup          butter\n" +
                "3       tablespoons  mayonnaise\n" +
                "2       tablespoons  lemon juice\n" +
                "2       dashs        basil\n" +
                "2       dashs        black pepper\n" +
                "1       pinch        onion powder\n" +
                "1       pinch        celery salt\n" +
                "2       pounds       tilapia fillets\n" +
                "-------------------------------------\n", record.stop().replaceAll("\r", ""));
    }

    @Test
    public void mkGroceriesServings() throws Exception {
        record.start();
        List<Recipe> allRecipes = new ArrayList<>();
        RecipesList.createRecipesList(path[1], allRecipes);
        Groceries.getChosenRecipes().addAll(allRecipes);
        Groceries.mkGroceriesServings(6);
        Groceries.getGroceryItemList().forEach(System.out::println);
        Groceries.clearChosenRecipes();
        assertEquals("0.5     teaspoon     Italian seasoning\n" +
                "0.37    cup          Parmesan\n" +
                "0.75    teaspoon     basil\n" +
                "1       dash         black pepper\n" +
                "1.75    cups         canned tomatoes\n" +
                "0.5     piece        egg\n" +
                "0.25    teaspoon     fennel seeds\n" +
                "1       clove        garlic\n" +
                "0.37    pound        ground beef\n" +
                "6       pieces       lasagna noodles\n" +
                "0.37    pound        mozzarella\n" +
                "0.25    cup          onion\n" +
                "1       ounce        parsley\n" +
                "1       cup          ricotta\n" +
                "1.75    teaspoons    salt\n" +
                "1       tablespoon   sugar\n" +
                "0.5     pound        sweet Italian sausage\n" +
                "6       ounces       tomato paste\n" +
                "6.5     fluid ounces tomato sauce\n" +
                "0.25    cup          water\n", record.stop().replaceAll("\r", ""));
    }

    @Test
    public void convert() throws Exception {
        record.start();

        for (int i = 2; i < path.length; i++) {
            List<Recipe> allRecipes = new ArrayList<>();
            RecipesList.createRecipesList(path[i], allRecipes);
            Groceries.getChosenRecipes().addAll(allRecipes);
            Groceries.makeGroceriesList();
            Groceries.convert();
            Groceries.getGroceryItemList().forEach(System.out::println);
            Groceries.clearChosenRecipes();
            System.out.println("-----------------------------");
        }
        assertEquals("0.5     cup          Parmesan\n" +
                "0.25    cup          butter\n" +
                "1.5     ounces       mayonnaise\n" +
                "1       fluid ounce  lemon juice\n" +
                "2       dashs        basil\n" +
                "2       dashs        black pepper\n" +
                "1       pinch        onion powder\n" +
                "1       pinch        celery salt\n" +
                "2       pounds       tilapia fillets\n" +
                "-----------------------------\n" +
                "6       ounces       sweet Italian sausage\n" +
                "5       fluid ounces water\n" +
                "0.5     cup          sugar\n" +
                "0.5     ounce        canned tomatoes\n" +
                "0.25    cup          tomato paste\n" +
                "2.5     fluid ounces tomato sauce\n" +
                "1.83    tablespoons  basil\n" +
                "2.5     teaspoons    fennel seeds\n" +
                "12      pieces       egg\n" +
                "-----------------------------\n", record.stop().replaceAll("\r", ""));
    }
}