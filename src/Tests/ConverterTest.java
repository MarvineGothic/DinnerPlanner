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
import static org.junit.runner.JUnitCore.runClasses;

public class ConverterTest {
    static PrintStream orgout;
    static RecordingOutputStream record;
    static Groceries groceries;
    static List<String> unitsNames = new ArrayList<>();
    static List<Integer> unitsValues = new ArrayList<>();
    static GroceryItem one;
    static GroceryItem two;

    @org.junit.BeforeClass
    public static void setUpStdOut() {
        orgout = System.out;
        record = new RecordingOutputStream(orgout);
        System.setOut(new java.io.PrintStream(record));
        unitsNames.add("pinch");
        unitsNames.add("dash");
        unitsNames.add("teaspoon");
        unitsNames.add("tablespoon");
        unitsNames.add("fluid ounce");
        unitsNames.add("ounce");            // fluid ounce  for liquid same as ounce
        unitsNames.add("piece");
        unitsNames.add("cup");
        unitsNames.add("pound");
        unitsNames.add("pint");           //  for liquid same as pound
        unitsNames.add("quart");           //  for liquid
        unitsNames.add("gallon");

        unitsValues.add(1);
        unitsValues.add(2);
        unitsValues.add(2 * 8);
        unitsValues.add(2 * 8 * 3);
        unitsValues.add(2 * 8 * 3 * 2);
        // unitsValues.add(2 * 8 * 8);
        unitsValues.add(2 * 8 * 3 * 2 * 8);
        unitsValues.add(2 * 8 * 3 * 2 * 8 * 2);
        unitsValues.add(2 * 8 * 3 * 2 * 8 * 2 * 2);
        unitsValues.add(2 * 8 * 3 * 2 * 8 * 2 * 2 * 4);

    }

    @org.junit.AfterClass
    public static void tearDownStdOut() {
        System.setOut(orgout);
        record = null;
    }

    public static void main(String[] args) {
        Result result = runClasses(ConverterTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            break;
        }
        System.out.printf("Tests run: %d,  Failures: %d\n", result.getRunCount(), result.getFailureCount());
    }
    @Test
    public void fitUnits1() throws Exception{

    }

    @Test
    public void fitUnits() throws Exception {
        record.start();
        for (String gi1 : unitsNames) {
            for (String gi2 : unitsNames) {
                one = new GroceryItem("Parmesan", gi1, 1);
                two = new GroceryItem("Parmesan", gi2, 1);
                Converter.fitUnits(one, two);
                System.out.printf("Item one: %s -> %s %s  %" + (20 - (one.getUnit().length() + String.valueOf(one.getAmount()).length()))
                        + "s  Item two: %s -> %s %s\n", gi1, one.getUnit(), one.getAmount(), "---", gi2, two.getUnit(), two.getAmount());
            }
            System.out.println("--------------------------------------------------");
        }
        assertEquals("Item one: pinch -> pinch 1.0           ---  Item two: pinch -> pinch 1.0\n" +
                "Item one: pinch -> pinch 1.0           ---  Item two: dash -> pinch 2.0\n" +
                "Item one: pinch -> pinch 1.0           ---  Item two: teaspoon -> pinch 16.0\n" +
                "Item one: pinch -> pinch 1.0           ---  Item two: tablespoon -> pinch 48.0\n" +
                "Item one: pinch -> pinch 1.0           ---  Item two: fluid ounce -> pinch 96.0\n" +
                "Item one: pinch -> pinch 1.0           ---  Item two: ounce -> pinch 96.0\n" +
                "Item one: pinch -> pinch 1.0           ---  Item two: piece -> piece 1.0\n" +
                "Item one: pinch -> pinch 1.0           ---  Item two: cup -> pinch 768.0\n" +
                "Item one: pinch -> pinch 1.0           ---  Item two: pound -> pinch 1536.0\n" +
                "Item one: pinch -> pinch 1.0           ---  Item two: pint -> pinch 1536.0\n" +
                "Item one: pinch -> pinch 1.0           ---  Item two: quart -> pinch 3072.0\n" +
                "Item one: pinch -> pinch 1.0           ---  Item two: gallon -> pinch 12288.0\n" +
                "--------------------------------------------------\n" +
                "Item one: dash -> pinch 2.0           ---  Item two: pinch -> pinch 1.0\n" +
                "Item one: dash -> dash 1.0            ---  Item two: dash -> dash 1.0\n" +
                "Item one: dash -> dash 1.0            ---  Item two: teaspoon -> dash 8.0\n" +
                "Item one: dash -> dash 1.0            ---  Item two: tablespoon -> dash 24.0\n" +
                "Item one: dash -> dash 1.0            ---  Item two: fluid ounce -> dash 48.0\n" +
                "Item one: dash -> dash 1.0            ---  Item two: ounce -> dash 48.0\n" +
                "Item one: dash -> dash 1.0            ---  Item two: piece -> piece 1.0\n" +
                "Item one: dash -> dash 1.0            ---  Item two: cup -> dash 384.0\n" +
                "Item one: dash -> dash 1.0            ---  Item two: pound -> dash 768.0\n" +
                "Item one: dash -> dash 1.0            ---  Item two: pint -> dash 768.0\n" +
                "Item one: dash -> dash 1.0            ---  Item two: quart -> dash 1536.0\n" +
                "Item one: dash -> dash 1.0            ---  Item two: gallon -> dash 6144.0\n" +
                "--------------------------------------------------\n" +
                "Item one: teaspoon -> pinch 16.0          ---  Item two: pinch -> pinch 1.0\n" +
                "Item one: teaspoon -> dash 8.0            ---  Item two: dash -> dash 1.0\n" +
                "Item one: teaspoon -> teaspoon 1.0        ---  Item two: teaspoon -> teaspoon 1.0\n" +
                "Item one: teaspoon -> teaspoon 1.0        ---  Item two: tablespoon -> teaspoon 3.0\n" +
                "Item one: teaspoon -> teaspoon 1.0        ---  Item two: fluid ounce -> teaspoon 6.0\n" +
                "Item one: teaspoon -> teaspoon 1.0        ---  Item two: ounce -> teaspoon 6.0\n" +
                "Item one: teaspoon -> teaspoon 1.0        ---  Item two: piece -> piece 1.0\n" +
                "Item one: teaspoon -> teaspoon 1.0        ---  Item two: cup -> teaspoon 48.0\n" +
                "Item one: teaspoon -> teaspoon 1.0        ---  Item two: pound -> teaspoon 96.0\n" +
                "Item one: teaspoon -> teaspoon 1.0        ---  Item two: pint -> teaspoon 96.0\n" +
                "Item one: teaspoon -> teaspoon 1.0        ---  Item two: quart -> teaspoon 192.0\n" +
                "Item one: teaspoon -> teaspoon 1.0        ---  Item two: gallon -> teaspoon 768.0\n" +
                "--------------------------------------------------\n" +
                "Item one: tablespoon -> pinch 48.0          ---  Item two: pinch -> pinch 1.0\n" +
                "Item one: tablespoon -> dash 24.0           ---  Item two: dash -> dash 1.0\n" +
                "Item one: tablespoon -> teaspoon 3.0        ---  Item two: teaspoon -> teaspoon 1.0\n" +
                "Item one: tablespoon -> tablespoon 1.0      ---  Item two: tablespoon -> tablespoon 1.0\n" +
                "Item one: tablespoon -> tablespoon 1.0      ---  Item two: fluid ounce -> tablespoon 2.0\n" +
                "Item one: tablespoon -> tablespoon 1.0      ---  Item two: ounce -> tablespoon 2.0\n" +
                "Item one: tablespoon -> tablespoon 1.0      ---  Item two: piece -> piece 1.0\n" +
                "Item one: tablespoon -> tablespoon 1.0      ---  Item two: cup -> tablespoon 16.0\n" +
                "Item one: tablespoon -> tablespoon 1.0      ---  Item two: pound -> tablespoon 32.0\n" +
                "Item one: tablespoon -> tablespoon 1.0      ---  Item two: pint -> tablespoon 32.0\n" +
                "Item one: tablespoon -> tablespoon 1.0      ---  Item two: quart -> tablespoon 64.0\n" +
                "Item one: tablespoon -> tablespoon 1.0      ---  Item two: gallon -> tablespoon 256.0\n" +
                "--------------------------------------------------\n" +
                "Item one: fluid ounce -> pinch 96.0          ---  Item two: pinch -> pinch 1.0\n" +
                "Item one: fluid ounce -> dash 48.0           ---  Item two: dash -> dash 1.0\n" +
                "Item one: fluid ounce -> teaspoon 6.0        ---  Item two: teaspoon -> teaspoon 1.0\n" +
                "Item one: fluid ounce -> tablespoon 2.0      ---  Item two: tablespoon -> tablespoon 1.0\n" +
                "Item one: fluid ounce -> fluid ounce 1.0     ---  Item two: fluid ounce -> fluid ounce 1.0\n" +
                "Item one: fluid ounce -> fluid ounce 1.0     ---  Item two: ounce -> ounce 1.0\n" +
                "Item one: fluid ounce -> fluid ounce 1.0     ---  Item two: piece -> piece 1.0\n" +
                "Item one: fluid ounce -> fluid ounce 1.0     ---  Item two: cup -> fluid ounce 8.0\n" +
                "Item one: fluid ounce -> fluid ounce 1.0     ---  Item two: pound -> fluid ounce 16.0\n" +
                "Item one: fluid ounce -> fluid ounce 1.0     ---  Item two: pint -> fluid ounce 16.0\n" +
                "Item one: fluid ounce -> fluid ounce 1.0     ---  Item two: quart -> fluid ounce 32.0\n" +
                "Item one: fluid ounce -> fluid ounce 1.0     ---  Item two: gallon -> fluid ounce 128.0\n" +
                "--------------------------------------------------\n" +
                "Item one: ounce -> pinch 96.0          ---  Item two: pinch -> pinch 1.0\n" +
                "Item one: ounce -> dash 48.0           ---  Item two: dash -> dash 1.0\n" +
                "Item one: ounce -> teaspoon 6.0        ---  Item two: teaspoon -> teaspoon 1.0\n" +
                "Item one: ounce -> tablespoon 2.0      ---  Item two: tablespoon -> tablespoon 1.0\n" +
                "Item one: ounce -> ounce 1.0           ---  Item two: fluid ounce -> fluid ounce 1.0\n" +
                "Item one: ounce -> ounce 1.0           ---  Item two: ounce -> ounce 1.0\n" +
                "Item one: ounce -> ounce 1.0           ---  Item two: piece -> piece 1.0\n" +
                "Item one: ounce -> ounce 1.0           ---  Item two: cup -> ounce 8.0\n" +
                "Item one: ounce -> ounce 1.0           ---  Item two: pound -> ounce 16.0\n" +
                "Item one: ounce -> ounce 1.0           ---  Item two: pint -> ounce 16.0\n" +
                "Item one: ounce -> ounce 1.0           ---  Item two: quart -> ounce 32.0\n" +
                "Item one: ounce -> ounce 1.0           ---  Item two: gallon -> ounce 128.0\n" +
                "--------------------------------------------------\n" +
                "Item one: piece -> piece 1.0           ---  Item two: pinch -> pinch 1.0\n" +
                "Item one: piece -> piece 1.0           ---  Item two: dash -> dash 1.0\n" +
                "Item one: piece -> piece 1.0           ---  Item two: teaspoon -> teaspoon 1.0\n" +
                "Item one: piece -> piece 1.0           ---  Item two: tablespoon -> tablespoon 1.0\n" +
                "Item one: piece -> piece 1.0           ---  Item two: fluid ounce -> fluid ounce 1.0\n" +
                "Item one: piece -> piece 1.0           ---  Item two: ounce -> ounce 1.0\n" +
                "Item one: piece -> piece 1.0           ---  Item two: piece -> piece 1.0\n" +
                "Item one: piece -> piece 1.0           ---  Item two: cup -> cup 1.0\n" +
                "Item one: piece -> piece 1.0           ---  Item two: pound -> pound 1.0\n" +
                "Item one: piece -> piece 1.0           ---  Item two: pint -> pint 1.0\n" +
                "Item one: piece -> piece 1.0           ---  Item two: quart -> quart 1.0\n" +
                "Item one: piece -> piece 1.0           ---  Item two: gallon -> gallon 1.0\n" +
                "--------------------------------------------------\n" +
                "Item one: cup -> pinch 768.0         ---  Item two: pinch -> pinch 1.0\n" +
                "Item one: cup -> dash 384.0          ---  Item two: dash -> dash 1.0\n" +
                "Item one: cup -> teaspoon 48.0       ---  Item two: teaspoon -> teaspoon 1.0\n" +
                "Item one: cup -> tablespoon 16.0     ---  Item two: tablespoon -> tablespoon 1.0\n" +
                "Item one: cup -> fluid ounce 8.0     ---  Item two: fluid ounce -> fluid ounce 1.0\n" +
                "Item one: cup -> ounce 8.0           ---  Item two: ounce -> ounce 1.0\n" +
                "Item one: cup -> cup 1.0             ---  Item two: piece -> piece 1.0\n" +
                "Item one: cup -> cup 1.0             ---  Item two: cup -> cup 1.0\n" +
                "Item one: cup -> cup 1.0             ---  Item two: pound -> cup 2.0\n" +
                "Item one: cup -> cup 1.0             ---  Item two: pint -> cup 2.0\n" +
                "Item one: cup -> cup 1.0             ---  Item two: quart -> cup 4.0\n" +
                "Item one: cup -> cup 1.0             ---  Item two: gallon -> cup 16.0\n" +
                "--------------------------------------------------\n" +
                "Item one: pound -> pinch 1536.0        ---  Item two: pinch -> pinch 1.0\n" +
                "Item one: pound -> dash 768.0          ---  Item two: dash -> dash 1.0\n" +
                "Item one: pound -> teaspoon 96.0       ---  Item two: teaspoon -> teaspoon 1.0\n" +
                "Item one: pound -> tablespoon 32.0     ---  Item two: tablespoon -> tablespoon 1.0\n" +
                "Item one: pound -> fluid ounce 16.0    ---  Item two: fluid ounce -> fluid ounce 1.0\n" +
                "Item one: pound -> ounce 16.0          ---  Item two: ounce -> ounce 1.0\n" +
                "Item one: pound -> pound 1.0           ---  Item two: piece -> piece 1.0\n" +
                "Item one: pound -> cup 2.0             ---  Item two: cup -> cup 1.0\n" +
                "Item one: pound -> pound 1.0           ---  Item two: pound -> pound 1.0\n" +
                "Item one: pound -> pound 1.0           ---  Item two: pint -> pint 1.0\n" +
                "Item one: pound -> pound 1.0           ---  Item two: quart -> pound 2.0\n" +
                "Item one: pound -> pound 1.0           ---  Item two: gallon -> pound 8.0\n" +
                "--------------------------------------------------\n" +
                "Item one: pint -> pinch 1536.0        ---  Item two: pinch -> pinch 1.0\n" +
                "Item one: pint -> dash 768.0          ---  Item two: dash -> dash 1.0\n" +
                "Item one: pint -> teaspoon 96.0       ---  Item two: teaspoon -> teaspoon 1.0\n" +
                "Item one: pint -> tablespoon 32.0     ---  Item two: tablespoon -> tablespoon 1.0\n" +
                "Item one: pint -> fluid ounce 16.0    ---  Item two: fluid ounce -> fluid ounce 1.0\n" +
                "Item one: pint -> ounce 16.0          ---  Item two: ounce -> ounce 1.0\n" +
                "Item one: pint -> pint 1.0            ---  Item two: piece -> piece 1.0\n" +
                "Item one: pint -> cup 2.0             ---  Item two: cup -> cup 1.0\n" +
                "Item one: pint -> pint 1.0            ---  Item two: pound -> pound 1.0\n" +
                "Item one: pint -> pint 1.0            ---  Item two: pint -> pint 1.0\n" +
                "Item one: pint -> pint 1.0            ---  Item two: quart -> pint 2.0\n" +
                "Item one: pint -> pint 1.0            ---  Item two: gallon -> pint 8.0\n" +
                "--------------------------------------------------\n" +
                "Item one: quart -> pinch 3072.0        ---  Item two: pinch -> pinch 1.0\n" +
                "Item one: quart -> dash 1536.0         ---  Item two: dash -> dash 1.0\n" +
                "Item one: quart -> teaspoon 192.0      ---  Item two: teaspoon -> teaspoon 1.0\n" +
                "Item one: quart -> tablespoon 64.0     ---  Item two: tablespoon -> tablespoon 1.0\n" +
                "Item one: quart -> fluid ounce 32.0    ---  Item two: fluid ounce -> fluid ounce 1.0\n" +
                "Item one: quart -> ounce 32.0          ---  Item two: ounce -> ounce 1.0\n" +
                "Item one: quart -> quart 1.0           ---  Item two: piece -> piece 1.0\n" +
                "Item one: quart -> cup 4.0             ---  Item two: cup -> cup 1.0\n" +
                "Item one: quart -> pound 2.0           ---  Item two: pound -> pound 1.0\n" +
                "Item one: quart -> pint 2.0            ---  Item two: pint -> pint 1.0\n" +
                "Item one: quart -> quart 1.0           ---  Item two: quart -> quart 1.0\n" +
                "Item one: quart -> quart 1.0           ---  Item two: gallon -> quart 4.0\n" +
                "--------------------------------------------------\n" +
                "Item one: gallon -> pinch 12288.0       ---  Item two: pinch -> pinch 1.0\n" +
                "Item one: gallon -> dash 6144.0         ---  Item two: dash -> dash 1.0\n" +
                "Item one: gallon -> teaspoon 768.0      ---  Item two: teaspoon -> teaspoon 1.0\n" +
                "Item one: gallon -> tablespoon 256.0    ---  Item two: tablespoon -> tablespoon 1.0\n" +
                "Item one: gallon -> fluid ounce 128.0   ---  Item two: fluid ounce -> fluid ounce 1.0\n" +
                "Item one: gallon -> ounce 128.0         ---  Item two: ounce -> ounce 1.0\n" +
                "Item one: gallon -> gallon 1.0          ---  Item two: piece -> piece 1.0\n" +
                "Item one: gallon -> cup 16.0            ---  Item two: cup -> cup 1.0\n" +
                "Item one: gallon -> pound 8.0           ---  Item two: pound -> pound 1.0\n" +
                "Item one: gallon -> pint 8.0            ---  Item two: pint -> pint 1.0\n" +
                "Item one: gallon -> quart 4.0           ---  Item two: quart -> quart 1.0\n" +
                "Item one: gallon -> gallon 1.0          ---  Item two: gallon -> gallon 1.0\n" +
                "--------------------------------------------------\n", record.stop().replaceAll("\r", ""));
    }

    @Test
    public void convertUnits() throws Exception {
        record.start();

        System.out.println("vine:");
        for (String u : unitsNames) {
            for (Integer i : unitsValues) {
                GroceryItem dry = new GroceryItem("vine", u, i);
                dry.setLiquid(true);
                Converter.convertUnits(dry);
                System.out.println(u + " " + i + " -> " + dry.getUnit() + " " + dry.getAmount());
            }
            System.out.println("----------------------");
        }
        System.out.println("-------------------------------------------------------\n");
        System.out.println("rice:");
        unitsNames.remove("pint");
        unitsNames.remove("quart");
        unitsNames.remove("gallon");
        unitsNames.remove("fluid ounce");

        for (String u : unitsNames) {
            for (Integer i : unitsValues) {
                GroceryItem dry = new GroceryItem("rice", u, i);
                dry.setLiquid(false);
                Converter.convertUnits(dry);
                System.out.println(u + " " + i + " -> " + dry.getUnit() + " " + dry.getAmount());
            }
            System.out.println("----------------------");
        }

        assertEquals("vine:\n" +
                "pinch 1 -> pinch 1.0\n" +
                "pinch 2 -> dash 1.0\n" +
                "pinch 16 -> teaspoon 1.0\n" +
                "pinch 48 -> tablespoon 1.0\n" +
                "pinch 96 -> fluid ounce 1.0\n" +
                "pinch 768 -> cup 1.0\n" +
                "pinch 1536 -> pint 1.0\n" +
                "pinch 3072 -> quart 1.0\n" +
                "pinch 12288 -> gallon 1.0\n" +
                "----------------------\n" +
                "dash 1 -> dash 1.0\n" +
                "dash 2 -> dash 2.0\n" +
                "dash 16 -> teaspoon 2.0\n" +
                "dash 48 -> fluid ounce 1.0\n" +
                "dash 96 -> fluid ounce 2.0\n" +
                "dash 768 -> pint 1.0\n" +
                "dash 1536 -> quart 1.0\n" +
                "dash 3072 -> quart 2.0\n" +
                "dash 12288 -> gallon 2.0\n" +
                "----------------------\n" +
                "teaspoon 1 -> teaspoon 1.0\n" +
                "teaspoon 2 -> teaspoon 2.0\n" +
                "teaspoon 16 -> fluid ounce 2.6666666666666665\n" +
                "teaspoon 48 -> cup 1.0\n" +
                "teaspoon 96 -> pint 1.0\n" +
                "teaspoon 768 -> gallon 1.0\n" +
                "teaspoon 1536 -> gallon 2.0\n" +
                "teaspoon 3072 -> gallon 4.0\n" +
                "teaspoon 12288 -> gallon 16.0\n" +
                "----------------------\n" +
                "tablespoon 1 -> tablespoon 1.0\n" +
                "tablespoon 2 -> fluid ounce 1.0\n" +
                "tablespoon 16 -> cup 1.0\n" +
                "tablespoon 48 -> pint 1.5\n" +
                "tablespoon 96 -> quart 1.5\n" +
                "tablespoon 768 -> gallon 3.0\n" +
                "tablespoon 1536 -> gallon 6.0\n" +
                "tablespoon 3072 -> gallon 12.0\n" +
                "tablespoon 12288 -> gallon 48.0\n" +
                "----------------------\n" +
                "fluid ounce 1 -> fluid ounce 1.0\n" +
                "fluid ounce 2 -> fluid ounce 2.0\n" +
                "fluid ounce 16 -> pint 1.0\n" +
                "fluid ounce 48 -> quart 1.5\n" +
                "fluid ounce 96 -> quart 3.0\n" +
                "fluid ounce 768 -> gallon 6.0\n" +
                "fluid ounce 1536 -> gallon 12.0\n" +
                "fluid ounce 3072 -> gallon 24.0\n" +
                "fluid ounce 12288 -> gallon 96.0\n" +
                "----------------------\n" +
                "ounce 1 -> fluid ounce 1.0\n" +
                "ounce 2 -> fluid ounce 2.0\n" +
                "ounce 16 -> pint 1.0\n" +
                "ounce 48 -> quart 1.5\n" +
                "ounce 96 -> quart 3.0\n" +
                "ounce 768 -> gallon 6.0\n" +
                "ounce 1536 -> gallon 12.0\n" +
                "ounce 3072 -> gallon 24.0\n" +
                "ounce 12288 -> gallon 96.0\n" +
                "----------------------\n" +
                "piece 1 -> piece 1.0\n" +
                "piece 2 -> piece 2.0\n" +
                "piece 16 -> piece 16.0\n" +
                "piece 48 -> piece 48.0\n" +
                "piece 96 -> piece 96.0\n" +
                "piece 768 -> piece 768.0\n" +
                "piece 1536 -> piece 1536.0\n" +
                "piece 3072 -> piece 3072.0\n" +
                "piece 12288 -> piece 12288.0\n" +
                "----------------------\n" +
                "cup 1 -> cup 1.0\n" +
                "cup 2 -> pint 1.0\n" +
                "cup 16 -> gallon 1.0\n" +
                "cup 48 -> gallon 3.0\n" +
                "cup 96 -> gallon 6.0\n" +
                "cup 768 -> gallon 48.0\n" +
                "cup 1536 -> gallon 96.0\n" +
                "cup 3072 -> gallon 192.0\n" +
                "cup 12288 -> gallon 768.0\n" +
                "----------------------\n" +
                "pound 1 -> pint 1.0\n" +
                "pound 2 -> quart 1.0\n" +
                "pound 16 -> gallon 2.0\n" +
                "pound 48 -> gallon 6.0\n" +
                "pound 96 -> gallon 12.0\n" +
                "pound 768 -> gallon 96.0\n" +
                "pound 1536 -> gallon 192.0\n" +
                "pound 3072 -> gallon 384.0\n" +
                "pound 12288 -> gallon 1536.0\n" +
                "----------------------\n" +
                "pint 1 -> pint 1.0\n" +
                "pint 2 -> quart 1.0\n" +
                "pint 16 -> gallon 2.0\n" +
                "pint 48 -> gallon 6.0\n" +
                "pint 96 -> gallon 12.0\n" +
                "pint 768 -> gallon 96.0\n" +
                "pint 1536 -> gallon 192.0\n" +
                "pint 3072 -> gallon 384.0\n" +
                "pint 12288 -> gallon 1536.0\n" +
                "----------------------\n" +
                "quart 1 -> quart 1.0\n" +
                "quart 2 -> quart 2.0\n" +
                "quart 16 -> gallon 4.0\n" +
                "quart 48 -> gallon 12.0\n" +
                "quart 96 -> gallon 24.0\n" +
                "quart 768 -> gallon 192.0\n" +
                "quart 1536 -> gallon 384.0\n" +
                "quart 3072 -> gallon 768.0\n" +
                "quart 12288 -> gallon 3072.0\n" +
                "----------------------\n" +
                "gallon 1 -> gallon 1.0\n" +
                "gallon 2 -> gallon 2.0\n" +
                "gallon 16 -> gallon 16.0\n" +
                "gallon 48 -> gallon 48.0\n" +
                "gallon 96 -> gallon 96.0\n" +
                "gallon 768 -> gallon 768.0\n" +
                "gallon 1536 -> gallon 1536.0\n" +
                "gallon 3072 -> gallon 3072.0\n" +
                "gallon 12288 -> gallon 12288.0\n" +
                "----------------------\n" +
                "-------------------------------------------------------\n" +
                "\n" +
                "rice:\n" +
                "pinch 1 -> pinch 1.0\n" +
                "pinch 2 -> dash 1.0\n" +
                "pinch 16 -> teaspoon 1.0\n" +
                "pinch 48 -> tablespoon 1.0\n" +
                "pinch 96 -> ounce 1.0\n" +
                "pinch 768 -> cup 1.0\n" +
                "pinch 1536 -> pound 1.0\n" +
                "pinch 3072 -> pound 2.0\n" +
                "pinch 12288 -> pound 8.0\n" +
                "----------------------\n" +
                "dash 1 -> dash 1.0\n" +
                "dash 2 -> dash 2.0\n" +
                "dash 16 -> teaspoon 2.0\n" +
                "dash 48 -> ounce 1.0\n" +
                "dash 96 -> ounce 2.0\n" +
                "dash 768 -> pound 1.0\n" +
                "dash 1536 -> pound 2.0\n" +
                "dash 3072 -> pound 4.0\n" +
                "dash 12288 -> pound 16.0\n" +
                "----------------------\n" +
                "teaspoon 1 -> teaspoon 1.0\n" +
                "teaspoon 2 -> teaspoon 2.0\n" +
                "teaspoon 16 -> ounce 2.6666666666666665\n" +
                "teaspoon 48 -> cup 1.0\n" +
                "teaspoon 96 -> pound 1.0\n" +
                "teaspoon 768 -> pound 8.0\n" +
                "teaspoon 1536 -> pound 16.0\n" +
                "teaspoon 3072 -> pound 32.0\n" +
                "teaspoon 12288 -> pound 128.0\n" +
                "----------------------\n" +
                "tablespoon 1 -> tablespoon 1.0\n" +
                "tablespoon 2 -> ounce 1.0\n" +
                "tablespoon 16 -> cup 1.0\n" +
                "tablespoon 48 -> pound 1.5\n" +
                "tablespoon 96 -> pound 3.0\n" +
                "tablespoon 768 -> pound 24.0\n" +
                "tablespoon 1536 -> pound 48.0\n" +
                "tablespoon 3072 -> pound 96.0\n" +
                "tablespoon 12288 -> pound 384.0\n" +
                "----------------------\n" +
                "ounce 1 -> ounce 1.0\n" +
                "ounce 2 -> ounce 2.0\n" +
                "ounce 16 -> pound 1.0\n" +
                "ounce 48 -> pound 3.0\n" +
                "ounce 96 -> pound 6.0\n" +
                "ounce 768 -> pound 48.0\n" +
                "ounce 1536 -> pound 96.0\n" +
                "ounce 3072 -> pound 192.0\n" +
                "ounce 12288 -> pound 768.0\n" +
                "----------------------\n" +
                "piece 1 -> piece 1.0\n" +
                "piece 2 -> piece 2.0\n" +
                "piece 16 -> piece 16.0\n" +
                "piece 48 -> piece 48.0\n" +
                "piece 96 -> piece 96.0\n" +
                "piece 768 -> piece 768.0\n" +
                "piece 1536 -> piece 1536.0\n" +
                "piece 3072 -> piece 3072.0\n" +
                "piece 12288 -> piece 12288.0\n" +
                "----------------------\n" +
                "cup 1 -> cup 1.0\n" +
                "cup 2 -> pound 1.0\n" +
                "cup 16 -> pound 8.0\n" +
                "cup 48 -> pound 24.0\n" +
                "cup 96 -> pound 48.0\n" +
                "cup 768 -> pound 384.0\n" +
                "cup 1536 -> pound 768.0\n" +
                "cup 3072 -> pound 1536.0\n" +
                "cup 12288 -> pound 6144.0\n" +
                "----------------------\n" +
                "pound 1 -> pound 1.0\n" +
                "pound 2 -> pound 2.0\n" +
                "pound 16 -> pound 16.0\n" +
                "pound 48 -> pound 48.0\n" +
                "pound 96 -> pound 96.0\n" +
                "pound 768 -> pound 768.0\n" +
                "pound 1536 -> pound 1536.0\n" +
                "pound 3072 -> pound 3072.0\n" +
                "pound 12288 -> pound 12288.0\n" +
                "----------------------\n", record.stop().replaceAll("\r", ""));

    }

    @Test
    public void test() {
        record.start();
        List<Recipe> allRecipes = new ArrayList<>();
        RecipesList.createRecipesList("data", allRecipes);
        Groceries.getChosenRecipes().addAll(allRecipes);
        Groceries.mkGroceriesServings(100);         // comment it to check minimal solution
        Groceries.getGroceryItemList().forEach(System.out::println);
        assertEquals("12.5    pounds       Arborio rice\n" +
                "1.04    pounds       Cajun seasoning\n" +
                "25      pounds       Cheddar\n" +
                "1.38    ounces       Italian seasoning\n" +
                "3.9     gallons      Marsala wine\n" +
                "8.33    pounds       Monterey Jack\n" +
                "34.1    pounds       Parmesan\n" +
                "6.25    pounds       Romano\n" +
                "100     slices       Swiss\n" +
                "2.77    fluid ounces Worcestershire sauce\n" +
                "25      pounds       angel hair pasta\n" +
                "35.71   slices       bacon\n" +
                "1.56    pints        balsamic vinegar\n" +
                "16.66   bunchs       basil\n" +
                "4.19    pounds       basil\n" +
                "2.39    pounds       beef broth\n" +
                "50      pounds       beef rib roast\n" +
                "25      pounds       beef round steak\n" +
                "12.5    bottles      beer\n" +
                "4.46    ounces       black pepper\n" +
                "35.41   pounds       bread crumbs\n" +
                "6.77    pounds       brown sugar\n" +
                "34.89   pounds       butter\n" +
                "8.72    gallons      canned tomatoes\n" +
                "6.25    dashs        celery salt\n" +
                "2.77    ounces       chicken bouillon granules\n" +
                "800     pieces       chicken breast\n" +
                "62.5    pounds       chicken broth\n" +
                "200     pieces       chicken thighs\n" +
                "1.56    pounds       chives\n" +
                "1.04    quarts       cider vinegar\n" +
                "2.7     gallons      condensed cream of mushroom soup\n" +
                "2.6     gallons      condensed tomato soup\n" +
                "1.17    pounds       cornstarch\n" +
                "3.38    gallons      cream\n" +
                "1.56    quarts       cream cheese\n" +
                "25      pounds       dry fettuccini\n" +
                "1.04    cups         dry onion soup mix\n" +
                "10      pounds       dry ziti pasta\n" +
                "95.83   pieces       egg\n" +
                "1.38    tablespoons  fennel seeds\n" +
                "12.5    pounds       fettuccine\n" +
                "15.39   pounds       flour\n" +
                "1.56    cups         fresh ginger root\n" +
                "13.02   pounds       frozen cheese ravioli\n" +
                "308.33  cloves       garlic\n" +
                "1.75    pounds       garlic powder\n" +
                "4.16    ounces       garlic salt\n" +
                "1.9     ounces       ginger\n" +
                "150     pieces       green bell pepper\n" +
                "103.75  pounds       ground beef\n" +
                "100     slices       ham\n" +
                "4.68    gallons      heavy cream\n" +
                "5.18    pounds       ketchup\n" +
                "100     pieces       lasagna noodles\n" +
                "50      pieces       lemon\n" +
                "1.56    cups         lemon juice\n" +
                "2.08    ounces       lemon pepper\n" +
                "12.5    pounds       linguine\n" +
                "10.54   pounds       mayonnaise\n" +
                "2.14    gallons      milk\n" +
                "1.04    pounds       minced garlic\n" +
                "41.25   pounds       mozzarella\n" +
                "166.66  pounds       mushrooms\n" +
                "13.69   pounds       noodles\n" +
                "100     slices       of bacon\n" +
                "2.86    gallons      olive oil\n" +
                "52.39   pounds       onion\n" +
                "6.25    dashs        onion powder\n" +
                "1.95    cups         onion soup mix\n" +
                "3.38    pounds       oregano\n" +
                "1.06    pounds       paprika\n" +
                "1.04    pounds       parsley\n" +
                "2.08    pounds       pecans\n" +
                "16.66   pounds       penne pasta\n" +
                "160.71  pounds       pork chops\n" +
                "54.16   pounds       portobello mushrooms\n" +
                "45.83   pounds       pot roast\n" +
                "1.56    cups         prepared mustard\n" +
                "3.75    pounds       provolone\n" +
                "25      pieces       red bell pepper\n" +
                "4.16    pounds       rice\n" +
                "1.56    pints        rice wine\n" +
                "8.33    pounds       ricotta\n" +
                "300     sprigs       rosemary\n" +
                "100     pieces       salmon fillets\n" +
                "12.5    pounds       salsa\n" +
                "1.04    pounds       salt\n" +
                "4.68    pounds       saltine cracker crumbs\n" +
                "50      pieces       scallion\n" +
                "2.77    ounces       seasoning salt\n" +
                "33.33   pieces       shallot\n" +
                "3.12    pounds       sherry\n" +
                "25      pounds       shrimp\n" +
                "12.5    pounds       snow peas\n" +
                "3.06    gallons      sour cream\n" +
                "3.25    quarts       soy sauce\n" +
                "5.88    gallons      spaghetti sauce\n" +
                "18.75   pounds       spinach\n" +
                "6.95    pounds       sugar\n" +
                "9.37    pounds       sun-dried tomatoes\n" +
                "8.33    pounds       sweet Italian sausage\n" +
                "12.5    pieces       sweet onion\n" +
                "16.66   pounds       sweet potato\n" +
                "1.04    pounds       taco seasoning mix\n" +
                "25      pounds       tilapia fillets\n" +
                "7.29    pounds       tomato paste\n" +
                "7.09    gallons      tomato sauce\n" +
                "83.33   pounds       tomatoes\n" +
                "1.38    tablespoons  vanilla extract\n" +
                "1.56    cups         vegetable oil\n" +
                "1.49    gallons      water\n" +
                "1.38    gallons      white wine\n" +
                "7.14    pieces       yellow bell pepper\n", record.stop().replaceAll("\r", ""));
    }
}