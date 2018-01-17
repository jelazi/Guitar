package lubin.guitar;


import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingsScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        public static ListPreference listSongs;
        public static ListPreference listInstruments;
        public static Preference valueUser;

        SharedPreferences settings;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

            addPreferencesFromResource(R.xml.settings_screen);

            listSongs = (ListPreference) findPreference("list_songs");
            listInstruments = (ListPreference) findPreference("list_instruments");

            setListPreferenceData(listSongs, listInstruments, getActivity()); //naplneni preference listem pisni a nastroju

            valueUser = (Preference) findPreference("value_user");
            valueUser.setSummary(settings.getString("value_user", "0"));
        }

    }

    protected static void setListPreferenceData(ListPreference listSongs, ListPreference listInstruments, Context context) {  //naplnení preference listem
        List<String> songsList = Songs.getSongsName(context);


        CharSequence[] entriesSong = songsList.toArray(new CharSequence[songsList.size()]);
        CharSequence[] entryValuesSong = songsList.toArray(new CharSequence[songsList.size()]);
        listSongs.setEntries(entriesSong);
        listSongs.setDefaultValue("1");
        listSongs.setEntryValues(entryValuesSong);


        List<String> instrumentList = Songs.getNameInstruments();
        CharSequence[] entriesInstrument = instrumentList.toArray(new CharSequence[songsList.size()]);
        CharSequence[] entryValuesInstrument = instrumentList.toArray(new CharSequence[songsList.size()]);
        listInstruments.setEntries(entriesInstrument);
        listInstruments.setDefaultValue("1");
        listInstruments.setEntryValues(entryValuesInstrument);

    }




}





