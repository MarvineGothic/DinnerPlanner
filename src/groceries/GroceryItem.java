package groceries;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sergiy on 11.12.2017.
 * <p>
 * Class groceries.GroceryItem
 * Creates an object of groceries to make a shopping list
 */
public class GroceryItem implements Comparable<GroceryItem> {
    private String name;
    private String unit;
    private double amount;
    private boolean isLiquid;

    public GroceryItem(String name, String unit, double amount) {
        this.name = name;
        Pattern pattern = Pattern.compile("\\bcanned|water|milk|soup|sauce|wine|oil|extract|cream|mustard|mayonaise|juice|vinegar\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        if (matcher.find() && !name.contains("dry")) {
            isLiquid = true;
        }
        if (unit.trim().equals("-")) unit = "piece";
        this.unit = unit;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isLiquid() {
        return isLiquid;
    }

    public void setLiquid(boolean liquid) {
        isLiquid = liquid;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        String one = df.format(amount).replace(",", ".");
        String two = amount > 1 ? unit + "s" : unit;
        return String.format("%s %" + (7 - one.length() + two.length()) + "s %"+(12-two.length()+name.length())+"s", one, two, name);
    }

    @Override
    public int compareTo(GroceryItem o) {
        return name.compareTo(o.getName());
    }
}
/*Double.toString(amount).replaceAll("\\.?0*$", "")*/