package com.automattic.app;

import android.app.Activity;
import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class MembersActivity extends Activity {
    class Member {
        public String name;
        public String email;

        @Override
        public String toString() {
            return name + "\n  " + email;
        }
    }

    private List<Member> mMembers;
    private JSONArray mMembersArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        readMembers();
        showMembers();
    }

    private void showMembers() {
        //ListAdapter adapter = new ArrayAdapter<Member>(this, R.layout.member_entry, mMembers);
        ListAdapter adapter = new JSONArrayListAdapter(mMembersArray);

        ListView list = (ListView)findViewById(R.id.member_list);
        list.setAdapter(adapter);
    }

    private void readMembers() {
        mMembers = new ArrayList<Member>();
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
            return;
        }

        try {
            JSONArray memberArray = new JSONArray(json);
            mMembersArray = memberArray;
            for ( int i = 0; i < memberArray.length(); i++ ) {
                JSONObject jsonMember = memberArray.getJSONObject(i);
                Member member = new Member();
                member.name = jsonMember.getString("name");
                member.email = jsonMember.getString("email");
                mMembers.add(member);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return;
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

    private class JSONArrayListAdapter extends BaseAdapter {
        private final JSONArray mArray;

        public JSONArrayListAdapter(JSONArray array) {
            mArray = array;
        }

        @Override
        public int getCount() {
            return mArray.length();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if ( view == null ) {
                view = getLayoutInflater().inflate(R.layout.member_item, viewGroup, false );
            }

            String email = null;
            String name = null;
            try {
                JSONObject member = mArray.getJSONObject(i);
                name = member.getString("name");
                email = member.getString("email");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            TextView nameField = (TextView)view.findViewById(R.id.name);
            TextView emailField = (TextView)view.findViewById(R.id.email);
            final ImageView gravatarField = (ImageView)view.findViewById(R.id.gravatar);

            nameField.setText(name);
            emailField.setText(email);

            String hashedEmail = null;
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(email.trim().toLowerCase().getBytes());
                byte messageDigest[] = digest.digest();

                StringBuffer hexString = new StringBuffer();
                for (int c = 0; c < messageDigest.length; c++) {
                    String octet = Integer.toHexString( (0xFF & messageDigest[c]) | 0x100 );
                    hexString.append(octet.substring(1, 3));
                }
                hashedEmail = hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return view;
            }

            if ( email.equals("alex.concha@automattic.com") ){
                Log.d("ALEX", hashedEmail);
            }

            final String url = "http://www.gravatar.com/avatar/" + hashedEmail;

            ImageLoader loader = new ImageLoader(gravatarField);
            loader.execute(url);

            return view;
        }

        private class ImageLoader extends AsyncTask<String, Object, Bitmap> {
            ImageView mView;

            ImageLoader(ImageView view) {
                mView = view;
            }

            @Override
            protected Bitmap doInBackground(String... strings) {
                String url = strings[0];

                Log.d("--->", url);

                try {
                    final OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    Response response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    byte[] bodyBytes = body.bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bodyBytes, 0, bodyBytes.length);
                    return bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute (Bitmap result) {
                if ( result != null ) {
                    mView.setImageBitmap(result);
                }
            }
        }
    }
}
