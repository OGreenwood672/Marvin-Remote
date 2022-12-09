package com.ogreenwood.discord_music;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class about extends Fragment {

    View view;
    String marvin = "https://discord.com/oauth2/authorize?client_id=858001176549523456&scope=bot";

    public about() {
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
        view = inflater.inflate(R.layout.fragment_about, container, false);

        TextView marvin_text = view.findViewById(R.id.marvin_text);
        marvin_text.setText(
                "Musical Marvin link: \n" + marvin
        );

        TextView about_text = view.findViewById(R.id.about);
        about_text.setText(
                "Set Up\n" +
                "To begin, invite the Musical Marvin bot by entering the above link in any browser and inviting him to your server. " +
                "Then in the set up tab enter your username and a text channel webhook. " +
                "Musical Marvin's Remote will send messages to that text channel which Marvin will recieve. " +
                "\n\nCUSTOM SOUNDS\nTo add custom sound, find the sound on Youtube, copy the URL and paste it into " +
                "the Add URLs tab. Give your sound a title and click add (The sound will load into custom " +
                "sounds shortly)." +
                "\n\nWEBHOOKS\nTo get your webhook link, go onto the web or desktop version of Discord, " +
                "go to text channel settings -> Integrations -> Create Webhook -> Copy Webhook Url"

        );

        view.findViewById(R.id.copy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(getActivity().getApplicationContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Marvin URL", marvin);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getActivity().getApplicationContext(), "URL Copied", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}