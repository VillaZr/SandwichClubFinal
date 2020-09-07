package com.udacity.sandwichclub.utils;

import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v4.app.FragmentTransaction;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {
    private static final String NAME = "name";
    private static final String MAINNAME = "mainName";
    private static final String ALSOKNOWNAS = "alsoKnownAs";
    private static final String PLACEOFORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    private static String name = "";
    private static String mainName = "";
    private static List<String> alsoKnownAs = new ArrayList<String>();
    private static String placeOfOrigin = "";
    private static String description = "";
    private static String image = "";
    private static List<String> ingredients = new ArrayList<String>();


    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichObject = new JSONObject(json);

            JSONObject nameObject = sandwichObject.getJSONObject(NAME);

            name = sandwichObject.getString(NAME);

            mainName = nameObject.getString(MAINNAME);

            alsoKnownAs.clear();
            JSONArray aAlsoKnownAs = nameObject.getJSONArray(ALSOKNOWNAS);
            for (int i = 0; i < aAlsoKnownAs.length(); i++){
                alsoKnownAs.add(aAlsoKnownAs.getString(i));
            }

            placeOfOrigin = sandwichObject.getString(PLACEOFORIGIN);
            description = sandwichObject.getString(DESCRIPTION);
            image = sandwichObject.getString(IMAGE);

            ingredients.clear();
            JSONArray aIngredients = sandwichObject.getJSONArray(INGREDIENTS);
            for (int i = 0; i < aIngredients.length(); i++){
                ingredients.add(aIngredients.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return null;
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
