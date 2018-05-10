package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    //buildSandwich represents the current sandwich we are building
    private static Sandwich buildSandwich = new Sandwich();


    public static Sandwich parseSandwichJson(String json) {

        //We are using a try/catch block because the data may not exist sometimes or be of the wrong type
        try {
            //First, indexing the whole dataset
            JSONObject data = new JSONObject(json);

            //We also need to index the name object to get the data inside of it
            JSONObject name = data.getJSONObject("name");

            //Setting the values of the sandwich data based on the data in the indexed JSONObjects
            //For arrays, we need to convert them to the List<String> type using getArray()
            buildSandwich.setMainName(name.getString("mainName"));
            buildSandwich.setAlsoKnownAs(getArray(name.getJSONArray("alsoKnownAs")));
            buildSandwich.setDescription(data.getString("description"));
            buildSandwich.setPlaceOfOrigin(data.getString("placeOfOrigin"));
            buildSandwich.setImage(data.getString("image"));
            buildSandwich.setIngredients(getArray(data.getJSONArray("ingredients")));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Afterward, we return the sandwich that data has been added to
        return buildSandwich;

    }

    //The getArray() function takes a JSONArray and returns a List<String>
    private static List<String> getArray(JSONArray data){

        ArrayList<String> newData = new ArrayList<>();

        //We run a loop that adds each value of the JSONArray to the list
        for(int i = 0; i < data.length(); i++){
            //Try/Catch block because the data could be of the wrong type
            try {
                newData.add((String) data.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Returning the converted array
        return newData;
    }
}
/*
{\"name\":{\"mainName\":\"Pljeskavica\",\"alsoKnownAs\":[]},\"placeOfOrigin\":\"Serbia\",\"description\":\"Pljeskavica,
            a grilled dish of spiced meat patty mixture of pork, beef and lamb, is a national dish
            of Serbia, also popular in Bosnia and Herzegovina and Croatia. It is a main course
            served with onions, kajmak (milk cream), ajvar (relish), and urnebes (spicy cheese
            salad), either on plate with side dishes, or with lepinja (flatbread, as a type of
            hamburger). Recently, Pljeskavica has gained popularity elsewhere in Europe and is
            served in a few speciality fast food restaurants in Germany, Sweden, and
            Austria.\",\"image\":\"https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Pljeskavica_%286883073017%29.jpg/800px-Pljeskavica_%286883073017%29.jpg\",\"ingredients\":[\"Two
            or more of beef, lamb, pork, veal\",\"Onions\",\"Bread crumbs\",\"Lard\"]}
*/




