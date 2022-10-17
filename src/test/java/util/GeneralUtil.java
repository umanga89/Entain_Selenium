package util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;

public class GeneralUtil {

    public static String removeCurrencySymbol(String value){
        String newValue = value.substring(1);
        return newValue;
    }
    public static String getJsonObjectFromFile(String fileName, String jsonObject, String itemName) {
        String value = "";
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir")+"\\src\\test\\java\\testData\\"+fileName));
            JSONObject jsonObj = (JSONObject) obj;
            JSONObject currentObj = (JSONObject) jsonObj.get(jsonObject);
            value = (String) currentObj.get(itemName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String extractOrderReferenceFromText(String text){
        String[] lines = text.split("\n");
        String[] words = lines[6].split(" ");
        return words[9];
    }

    public static double formatPriceToTwoDecimalPlaces(double price){
        final DecimalFormat dfZero = new DecimalFormat("0.00");
        return Double.parseDouble(dfZero.format(price));
    }
}
