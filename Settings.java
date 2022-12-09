package com.ogreenwood.discord_music;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Fragment implements View.OnClickListener {

    View view;
    String PREFS_NAME = "SETTINGS";

    public Settings() {
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
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        view.findViewById(R.id.save_button).setOnClickListener(this);

        SharedPreferences settings = getActivity().getApplicationContext().getSharedPreferences(PREFS_NAME, 0);

        CharSequence username = settings.getString("username", "");
        EditText user_input = view.findViewById(R.id.user_input);
        user_input.setText(username);

        CharSequence webhook = settings.getString("webhook", "");
        EditText webhook_input = view.findViewById(R.id.webhook_input);
        webhook_input.setText(webhook);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:

                EditText user_text = (EditText)view.findViewById(R.id.user_input);
                String user = user_text.getText().toString();

                EditText webhook_text = (EditText)view.findViewById(R.id.webhook_input);
                String webhook = webhook_text.getText().toString();

                SharedPreferences settings = getActivity().getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("username", user);
                editor.putString("webhook", webhook);

                editor.apply();

                Toast.makeText(getActivity().getApplicationContext(),"Info Saved", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}