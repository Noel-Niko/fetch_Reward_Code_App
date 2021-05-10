package com.android.fetchdataparsing.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.fetchdataparsing.R;
import com.android.fetchdataparsing.model.fetchItem;
import com.android.fetchdataparsing.util.ItemListAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<fetchItem> fetchItems;
    private static String JSON_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
    ItemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.itemListRV);
        fetchItems = new ArrayList<>();
        extractItems();
    }

    private void extractItems() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject itemObject = response.getJSONObject(i);

                        fetchItem item = new fetchItem();
                        item.setId(itemObject.getInt("id"));
                        item.setListId(itemObject.getInt("listId"));
                        item.setName(itemObject.getString("name").toString());

                        fetchItems.add(item);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //implement sorting by ListID then Name (filtering out null and "") before passing to recycler view
                fetchItems.removeIf(item -> item.getName() == "null" || item.getName().isEmpty());

                //To correctly sort names by the true integer value in the name String...
                //NOTE: ID IS the int value in the "name" field, and it would be simplest to just sort by id #
                //however the requirements were to sort by the "name" field which potentially could be given a non-matching value.
                for (int i = 0; i < fetchItems.size(); i++){
                    String alphanum = fetchItems.get(i).getName();
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(alphanum);
                    while(m.find()) {
                        String n = m.group();
                        int N = Integer.parseInt(n);
                        fetchItems.get(i).setNameNum(N);
                    }
                }

               //Sort the List as requested...
                Collections.sort(fetchItems, Comparator.comparing(fetchItem::getListId)
                        .thenComparing(fetchItem::getNameNum));

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new ItemListAdapter(getApplicationContext(), fetchItems);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);

    }
}