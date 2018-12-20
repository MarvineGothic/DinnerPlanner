package groceries;

import java.util.*;

/**
 * Created by Sergiy on 12.12.2017.
 */
public class Converter {
    private static List<String> unitsNames;
    private static Map<String, Integer> units;

    static {
        units = new LinkedHashMap<>();
        units.put("pinch", 1);                                //1
        units.put("dash", 2);                                 //2
        units.put("teaspoon", 2 * 8);                         //16
        units.put("tablespoon", 2 * 8 * 3);                   //48
        units.put("ounce", 2 * 8 * 3 * 2);                    // 96
        units.put("fluid ounce", 2 * 8 * 3 * 2);              // 96
        units.put("cup", 2 * 8 * 3 * 2 * 8);                 // 768
        units.put("piece", 2 * 8 * 8);                       // 128
        units.put("pound", 2 * 8 * 3 * 2 * 8 * 2);           // 1536
        units.put("pint", 2 * 8 * 3 * 2 * 8 * 2);            // 1536
        units.put("quart", 2 * 8 * 3 * 2 * 8 * 2 * 2);       // 3072
        units.put("gallon", 2 * 8 * 3 * 2 * 8 * 2 * 2 * 4);  // 12288

        unitsNames = new ArrayList<>(units.keySet());
        unitsNames.remove("piece");
    }

    /**
     * This method fits units of two identical items
     * It reduces a unit if one item to lower unit of another and correspondingly changes amount of units.
     *
     * @param one
     * @param two
     */
    public static void fitUnits(GroceryItem one, GroceryItem two) {
        if (one.getUnit().equals("piece") || two.getUnit().equals("piece")) return;
        int unitValue1 = 0;
        int unitValue2 = 0;

        if (units.containsKey(one.getUnit())) unitValue1 = units.get(one.getUnit());
        if (units.containsKey(two.getUnit())) unitValue2 = units.get(two.getUnit());
        if (unitValue1 != 0 && unitValue2 != 0)
            check(unitValue1, unitValue2, one, two);
    }

    public static void check(int u1, int u2, GroceryItem one, GroceryItem two) {
        try {
            if (u1 > u2) {
                one.setUnit(two.getUnit());         // set unit name to lesser one. fx. if one has cup and two has dash, we set one to dash
                one.setAmount(one.getAmount() * (u1 / u2));
            } else if (u2 > u1) {
                two.setUnit(one.getUnit());
                two.setAmount(two.getAmount() * (u2 / u1));
            }
        } catch (ArithmeticException e) {
            System.out.println("Oops! Something went wrong! We divide by zero.");
        }
    }

    public static void convertUnits(GroceryItem item) {
        String actualUnit = item.getUnit();
        double actualAmount = item.getAmount();
        int index = unitsNames.indexOf(actualUnit);                      // index of unit in unitsNames, so we don't go lower it

        if (actualUnit.equals("piece") || !units.containsKey(actualUnit))        // check if it actually has this unit name in the list
            return;

        int size = item.isLiquid() ? unitsNames.size() - 1 : unitsNames.size() - 4;

        for (int i = size; i >= index; i--) {
            String unitNameFromList = unitsNames.get(i);
            int valueFromList = units.get(unitNameFromList);       // value of unit in HashMap
            int actualValue = units.get(actualUnit);                      // value of item unit
            double newAmount;
            if ((newAmount = actualAmount / (valueFromList / actualValue)) >= 1) {
                if (item.isLiquid() && unitNameFromList.equals("ounce")) unitNameFromList = "fluid ounce";
                if (!item.isLiquid() && unitNameFromList.equals("fluid ounce")) unitNameFromList = "ounce";
                item.setUnit(unitNameFromList);
                item.setAmount(newAmount);
                return;
            }
        }
    }

    public static Map<String, Integer> getUnits() {
        return units;
    }
}
