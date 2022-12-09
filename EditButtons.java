package com.ogreenwood.discord_music;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class EditButtons extends Fragment implements View.OnClickListener {

    View view;


    public EditButtons() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_buttons, container, false);

        view.findViewById(R.id.add_button).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                EditText title_text = view.findViewById(R.id.title_input);
                String title = title_text.getText().toString();

                EditText url_text = view.findViewById(R.id.url_input);
                String url = url_text.getText().toString();

                if (url.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(),"URL needed", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (title.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(),"Title needed", Toast.LENGTH_SHORT).show();
                    return;
                }

                CheckBox autoshuffle_button = view.findViewById(R.id.autoshuffle);
                boolean autoshuffle = autoshuffle_button.isChecked();

                SQLiteDatabase database = new SQLiteDB(getActivity().getApplicationContext()).getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(SQLiteDB.BUTTON_COLUMN_TITLE, title);
                values.put(SQLiteDB.BUTTON_COLUMN_URL, url);
                values.put(SQLiteDB.BUTTON_COLUMN_AUTOSHUFFLE, autoshuffle);

                long newRowId = database.insert(SQLiteDB.BUTTON_TABLE_NAME, null, values);

//                Log.e("DATABASE OPERATION", title + " added with " + newRowId);

                title_text.setText("");
                url_text.setText("");
                autoshuffle_button.setChecked(false);

                Toast.makeText(getActivity().getApplicationContext(),title + " added", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}