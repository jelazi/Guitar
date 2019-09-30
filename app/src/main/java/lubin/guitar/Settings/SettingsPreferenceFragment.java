package lubin.guitar.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.List;

import lubin.guitar.R;
import lubin.guitar.Songs;

public class SettingsPreferenceFragment extends PreferenceFragment {

    public static ListPreference listSongs;
    public static ListPreference listInstruments;
    public static Preference valueUser;
    public static Preference nameUser;

    SharedPreferences settings;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        addPreferencesFromResource(R.xml.settings_screen);

        listSongs = (ListPreference) findPreference("list_songs");
        listSongs.setSummary(settings.getString("list_songs", "Pro Elisku"));
        listInstruments = (ListPreference) findPreference("list_instruments");
        listInstruments.setSummary(settings.getString("list_instruments", "a1.wav"));

        setListPreferenceData(listSongs, listInstruments, getActivity()); //naplneni preference listem pisni a nastroju

        valueUser = (Preference) findPreference("value_user");
        valueUser.setSummary(settings.getString("value_user", "0"));

        nameUser = (Preference) findPreference("name_user");
        nameUser.setSummary(settings.getString("name_user", null));


        listInstruments.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                listInstruments.setSummary(newValue.toString());
                return true;

            }
        });

        listSongs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                listSongs.setSummary(newValue.toString());
                return true;
            }
        });
    }

    protected void setListPreferenceData(ListPreference listSongs, ListPreference listInstruments, Context context) {  //naplnení preference listem
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
