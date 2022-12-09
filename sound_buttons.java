package com.ogreenwood.discord_music;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class sound_buttons extends Fragment implements View.OnClickListener {

    View view;
    String PREFS_NAME = "SETTINGS";

    boolean shouldRefreshOnResume = false;

    public SQLiteDB db;
    public SQLiteDatabase readable_db;
    public SQLiteDatabase writable_db;
    Context context;

    String prefix = "=>";
    String play = prefix + "play";
    String skip = prefix + "skip";
    String stop = prefix + "stop";

    String WEBHOOK, USER;
    Bot bot;

    public sound_buttons() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.sound_buttons_fragment, container, false);


        SharedPreferences settings = getActivity().getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        USER = settings.getString("username", "<default-user>");

        WEBHOOK = settings.getString("webhook", "None");

        bot = new Bot("Musical Marvin's Remote", WEBHOOK);

        context = getActivity().getApplicationContext();
        db = new SQLiteDB(context);

        readable_db = db.getReadableDatabase();
        writable_db = db.getWritableDatabase();

        add_buttons();

        view.findViewById(R.id.skip_button).setOnClickListener(this);
        view.findViewById(R.id.stop_button).setOnClickListener(this);

        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        // Check should we need to refresh the fragment
        if(shouldRefreshOnResume){
            refresh();
        }
    }
    public void refresh() {
        LinearLayout layout = view.findViewById(R.id.button_layout);
        layout.removeAllViews();
        add_buttons();
    }

    @Override
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }

    public void add_buttons() {
        String[] projections = {
                SQLiteDB.BUTTON_COLUMN_TITLE,
                SQLiteDB.BUTTON_COLUMN_URL,
                SQLiteDB.BUTTON_COLUMN_AUTOSHUFFLE,
                SQLiteDB.BUTTON_COLUMN_ID
        };


        Cursor cursor = readable_db.query(
                SQLiteDB.BUTTON_TABLE_NAME,   // The table to query
                projections,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );


//        db.close();
//        this.deleteDatabase(SQLiteDB.DATABASE_NAME);

        LinearLayout layout = view.findViewById(R.id.button_layout);

        if (cursor.moveToFirst()) {

            int id = 100;
            readable_db.beginTransaction();
            writable_db.beginTransaction();

            while (!cursor.isAfterLast()) {
                //int id = cursor.getColumnIndex(SQLiteDB.BUTTON_COLUMN_ID);
                String title = cursor.getString(cursor.getColumnIndex(projections[0]));
                String url = cursor.getString(cursor.getColumnIndex(projections[1]));
                String autoshuffle = "";
                if (cursor.getString(cursor.getColumnIndex(projections[2])).equals("1")) {
                    autoshuffle = "autoshuffle";
                }

                Button btnTag = new Button(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        Math.max(200, LinearLayout.LayoutParams.WRAP_CONTENT)
                );
                params.setMargins(0, 16, 0, 16);
                btnTag.setLayoutParams(params);

                btnTag.setBackgroundColor(getResources().getColor(R.color.purple_700));
                btnTag.setText(title);
                btnTag.setTextColor(getResources().getColor(R.color.white));
                btnTag.setId(id);

                layout.addView(btnTag);

                String finalAutoshuffle = autoshuffle;
                view.findViewById(id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bot.WEBHOOK.equals("") || bot.NAME.equals("")) {
                            Toast.makeText(getActivity().getApplicationContext(), "Set Up not", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        bot.post(play + " " + url + " " + USER + " " + finalAutoshuffle);
                        Toast.makeText(getActivity().getApplicationContext(), "<" + title + "> Sent", Toast.LENGTH_SHORT).show();
                    }
                });

                view.findViewById(id).setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                         String[] whereArgs = new String[] { title, url };
                         writable_db.delete(
                                SQLiteDB.BUTTON_TABLE_NAME,
                              SQLiteDB.BUTTON_COLUMN_TITLE + "=? and " +
                              SQLiteDB.BUTTON_COLUMN_URL + "=?"
                              , whereArgs
                         );
                         refresh();
                         return true;

                    }

                });

                id++;

                cursor.moveToNext();

            }
            readable_db.setTransactionSuccessful();
            readable_db.endTransaction();
            writable_db.setTransactionSuccessful();
            writable_db.endTransaction();
        }

        cursor.close();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip_button:
                bot.post(skip + " " + USER);
                Toast.makeText(getActivity().getApplicationContext(), "Skip Message Sent", Toast.LENGTH_SHORT).show();
                break;
            case R.id.stop_button:
                bot.post(stop + " " + USER);
                Toast.makeText(getActivity().getApplicationContext(), "Stop Message Sent", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}