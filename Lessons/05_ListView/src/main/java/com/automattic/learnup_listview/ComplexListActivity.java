package com.automattic.learnup_listview;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ComplexListActivity extends ListActivity {

    private static final String DB_NAME = "elements";
    private static final String DB_ASSET = "elements.sqlite3";
    private static final String TAG = "Learnup.ComplexList";

    private CopyDatabaseTask mCopyTask;
    SQLiteDatabase mDatabase;

    TextView mStatusText;

    private boolean mReady = false;
    private ElementsAdapter mAdapter;
    private int mCreatedViewCount = 0;
    private int mUsedViewCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        copyDatabase();
        setContentView(R.layout.activity_complex_list);

        mAdapter = new ElementsAdapter();
        setListAdapter(mAdapter);

        mStatusText = (TextView) findViewById(R.id.status);

        ListView listView = getListView();


        updateStatusText();
    }

    public void logTapped() {
        Integer integer = 5;
        Log.d("App", "tapped " + integer);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.complex_list, menu);
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

    protected void onDatabaseReady() {
        if (BuildConfig.DEBUG) Log.d(TAG, "Database ready");

        loadList();

    }

    private void loadList() {

        if (mDatabase == null) return;

        Cursor cursor = mDatabase.query("elements", null, null, null, null, null, "weight ASC");

        mAdapter.changeCursor(cursor);

    }

    protected void onDatabaseFailure() {
        if (BuildConfig.DEBUG) Log.d(TAG, "Database copy failed");
    }

    private void copyDatabase() {

        if (mReady) return;

        if (mCopyTask != null) {
            if (BuildConfig.DEBUG) Log.d(TAG, "Already started copy database task.");
            return;
        }

        mCopyTask = new CopyDatabaseTask();
        mCopyTask.execute();
    }

    private class CopyDatabaseTask extends AsyncTask<Void,Void,SQLiteDatabase> {

        @Override
        protected SQLiteDatabase doInBackground(Void... params) {

            if (mDatabase != null) return mDatabase;

            File db = getDatabasePath(DB_NAME);

            if (db.exists()) {
                mReady = true;
                return openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
            }

            InputStream in = null;
            OutputStream out = null;
            try {

                File dir = db.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new IOException("Could not find or create database directory: " + dir);

                in = new BufferedInputStream(getAssets().open(DB_ASSET));
                out = new BufferedOutputStream(new FileOutputStream(db));

                byte[] data = new byte[1024];
                int length;
                while ((length = in.read(data)) > 0) {
                    out.write(data, 0, length);
                }

                // copied the database, we're ready
                mReady = true;
            } catch (FileNotFoundException e) {
                Log.e(TAG, "File not found", e);

                mReady = false;
            } catch (IOException e) {
                Log.e(TAG, "IO Error", e);

                mReady = false;
            } finally {
                if (in != null) try {
                    in.close();
                } catch (IOException e) {
                    // fine then
                }
                if (out != null) try {
                    out.close();
                } catch (IOException e) {
                    // fine then
                }
            }

            return openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        }

        @Override
        protected void onPostExecute(SQLiteDatabase db) {

            mDatabase = db;

            if (BuildConfig.DEBUG) Log.d(TAG, "Completed db task");

            if (mReady) {
                onDatabaseReady();
            } else {
                onDatabaseFailure();
            }

            mCopyTask = null;

        }
    }

    private class ElementsAdapter extends CursorAdapter {

        ElementsAdapter() {
            this(null);
        }

        public ElementsAdapter(Cursor c) {
            super(ComplexListActivity.this, c, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            mCreatedViewCount ++;
            updateStatusText();

            LayoutInflater inflater = getLayoutInflater();

            View view = inflater.inflate(R.layout.element_list_item, parent, false);

            TextView tv = (TextView) view.findViewById(R.id.name);
            ViewHolder holder = new ViewHolder(tv);
            view.setTag(holder);

            return view;

        }

        private class ViewHolder {
            private final TextView textView;

            public ViewHolder(TextView tv) {
                textView = tv;
            }

        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            mUsedViewCount ++;
            updateStatusText();

            ViewHolder holder = (ViewHolder) view.getTag();
            TextView groupText = (TextView) view.findViewById(R.id.group);

            holder.textView.setText(cursor.getString(cursor.getColumnIndex("name")));
            groupText.setText(cursor.getString(cursor.getColumnIndex("section")));

        }

    }

    private void updateStatusText() {
        mStatusText.setText("Rows Created: " + mCreatedViewCount + " Rows Displayed: " + mUsedViewCount);
    }
}
