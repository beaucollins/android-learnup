package com.automattic.app;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class MembersActivity extends ListActivity {

    protected JSONArray getMembersJSON() throws JSONException {
        String jsonString = loadJSONFromAsset();
        JSONArray array = new JSONArray(jsonString);
        return array;

    }

    String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("members.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        try {
            JSONArray members = getMembersJSON();
            setListAdapter(new JSONArrayAdapter(members));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.members, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class JSONArrayAdapter extends BaseAdapter {


        private final JSONArray mJSONArray;

        JSONArrayAdapter(JSONArray array) {
            mJSONArray = array;
        }

        @Override
        public int getCount() {
            return mJSONArray.length();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.member_item, parent, false);
            } else {
                view = convertView;
            }

            JSONObject member = mJSONArray.optJSONObject(position);

            String name = member.optString("name");
            String email = member.optString("email");

            TextView nameField = (TextView) view.findViewById(R.id.name);
            TextView emailField = (TextView) view.findViewById(R.id.email);

            nameField.setText(name);
            emailField.setText(email);

            return view;
        }
    }

}
