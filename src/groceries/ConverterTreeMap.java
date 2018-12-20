package groceries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Sergiy on 12.12.2017.
 */
public class ConverterTreeMap extends Converter {
    private static TreeMap<Integer, String> units;
    private static ArrayList<String> unitsNames;

    static {
        units = new TreeMap<>(Collections.reverseOrder());
        unitsNames = new ArrayList<>();

        units.put(1, "pinch");
        units.put(2, "dash");
        units.put(2 * 8, "teaspoon");
        units.put(2 * 8 * 3, "tablespoon");
        units.put(2 * 8 * 3 * 2, "ounce");
        units.put(2 * 8 * 8, "piece");
        units.put(2 * 8 * 3 * 2 * 8, "cup");
        units.put(2 * 8 * 3 * 2 * 8 * 2, "pint");
        units.put(2 * 8 * 3 * 2 * 8 * 2 * 2, "quart");
        units.put(2 * 8 * 3 * 2 * 8 * 2 * 2 * 4, "gallon");

    }

    /*public static void main(String[] args) {
        ConverterTreeMap converterTreeMap = new ConverterTreeMap();
        getUnits().forEach((k, v) -> System.out.println(k + " " + v));
    }*/

    /**
     * This method fits units of two identical items
     * It reduces a unit if one item to lower unit of another and correspondingly changes amount of units.
     *
     * @param
     * @param
     */
    public static void fitUnits(GroceryItem one, GroceryItem two) {
        int u1 = 0;
        int u2 = 0;
        String oneUnit = "";
        String twoUnit = "";
        for (Map.Entry entry : units.entrySet()) {
            oneUnit = one.getUnit();
            twoUnit = two.getUnit();
            if (u1 > 0 && u2 > 0) break;
            if (oneUnit.equals("pound")) oneUnit = "pint";
            if (twoUnit.equals("pound")) twoUnit = "pint";
            if (entry.getValue().equals(oneUnit)) u1 = (int) entry.getKey();
            if (entry.getValue().equals(twoUnit)) u2 = (int) entry.getKey();

        }
        check(u1, u2, one, two);
    }

    public static void convertUnits(GroceryItem item) {
        if (!units.containsValue(item.getUnit()) || item.getUnit().equals("piece"))
            return;        // check if it actually has this unit name in the list
        double amount = item.getAmount();
        String unit = item.getUnit();
        int valueUnit = 0;

        for (Map.Entry entry : units.entrySet()) {
            if (entry.getValue().equals(unit)) {
                valueUnit = (int) entry.getKey();
                break;
            }
        }

        for (Map.Entry entry : units.entrySet()) {
            // System.out.println(entry.getKey() + " " + entry.getValue());
            int valueIndex = (int) entry.getKey();
            double newAmount;
            boolean go = true;
            String unitName = (String) entry.getValue();
            if (!item.isLiquid() && (unitName.equals("gallon") || unitName.equals("quart")) || unitName.equals("piece"))
                go = false;
            if ((newAmount = amount / (valueIndex / valueUnit)) >= 1 && go) {
                if (item.isLiquid() && entry.getValue().equals("ounce")) unitName = "fluid ounce";
                if (!item.isLiquid() && entry.getValue().equals("pint")) unitName = "pound";
                item.setUnit(unitName);
                item.setAmount(newAmount);
                return;
            }
        }
    }

    public static TreeMap<Integer, String> getUnitsTreeMap() {
        return units;
    }
}
