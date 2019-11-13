package lubin.guitar.Settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AlertDialog;

import java.util.List;

import lubin.guitar.R;
import lubin.guitar.Song.Songs;
import lubin.guitar.Users.SingletonManagerUsers;
import lubin.guitar.Users.User;

public class SettingsPreferenceFragment extends PreferenceFragment {

    public static Preference preferenceCoinUser;
    public static Preference preferenceNameUser;
    Preference preferenceCurrentLevel;
    Preference preferenceCurrentSong;
    Preference preferenceCurrentInstrument;
    Preference preferenceCurrentFret;
    Preference preferenceCurrentBackground;
    Preference preferenceCurrentString;

    SwitchPreference preferenceStopBeforeTone;
    User currentUser;
    SharedPreferences settings;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        currentUser = SingletonManagerUsers.getCurrentUser();

        addPreferencesFromResource(R.xml.settings_screen);

        preferenceCoinUser = findPreference("coin_user");
        preferenceNameUser = findPreference("name_user");

        preferenceCurrentLevel = findPreference("current_level");
        preferenceCurrentLevel.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showList(preferenceCurrentLevel);
                return true;
            }
        });

        preferenceCurrentSong = findPreference("current_song");
        preferenceCurrentSong.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showList(preferenceCurrentSong);
                return true;
            }
        });
        preferenceCurrentInstrument = findPreference("current_instrument");
        preferenceCurrentInstrument.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showList(preferenceCurrentInstrument);
                return true;
            }
        });
        preferenceCurrentFret = findPreference("current_fret");
        preferenceCurrentFret.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showList(preferenceCurrentFret);
                return true;
            }
        });
        preferenceCurrentBackground = findPreference("current_background");
        preferenceCurrentBackground.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showList(preferenceCurrentBackground);
                return true;
            }
        });
        preferenceCurrentString = findPreference("current_string");
        preferenceCurrentString.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showList(preferenceCurrentString);
                return true;
            }
        });

        preferenceStopBeforeTone = (SwitchPreference) findPreference("stop_before_tone");
        fillPreferences();
    }


    protected void fillPreferences () {
        preferenceCoinUser.setSummary(String.valueOf(currentUser.getCoins()));
        preferenceNameUser.setSummary(currentUser.getName());
        preferenceCurrentLevel.setSummary(SingletonManagerUsers.getNameUserLevel(currentUser.getCurrentLevel(), this.getActivity()));
        preferenceCurrentSong.setSummary(currentUser.getCurrentNameSong());
        preferenceCurrentInstrument.setSummary(currentUser.getCurrentNameInstrument());
        preferenceCurrentFret.setSummary(currentUser.getCurrentNameFret());
        preferenceCurrentBackground.setSummary(currentUser.getCurrentNameBackground());
        preferenceCurrentString.setSummary(currentUser.getCurrentNameString());
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

    protected void showList (Preference preference) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (preference == preferenceCurrentLevel) {
            builder.setTitle(getResources().getString(R.string.current_level));
            final List<String> listAllowedLevel = SingletonManagerUsers.getListAllowLevel(currentUser.getAllowedLevel(), this.getActivity());
            final String[] arrayAllowedLevel = new String[listAllowedLevel.size()];
            listAllowedLevel.toArray(arrayAllowedLevel);
            final int[] checkItem = {currentUser.getCurrentLevel().getValue() - 1};
            builder.setSingleChoiceItems(
                    arrayAllowedLevel,
                    checkItem[0],
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            checkItem[0] = item;
                        }
                    }
            );
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    currentUser.setCurrentLevel(SingletonManagerUsers.getUserLevelByValue(checkItem[0]+1), SettingsPreferenceFragment.this.getActivity());
                    SingletonManagerUsers.changeUser(currentUser);
                    preferenceCurrentLevel.setSummary(SingletonManagerUsers.getNameUserLevel(currentUser.getCurrentLevel(), SettingsPreferenceFragment.this.getActivity()));
                }
            });
        }
        if (preference == preferenceCurrentSong) {
            builder.setTitle(getResources().getString(R.string.current_song));
            final List<String> listAllowedSongs = currentUser.getAllowedSongs();
            final String[] allAllowedSongs = new String[listAllowedSongs.size()];
            listAllowedSongs.toArray(allAllowedSongs);
            final int[] checkItem = {0};
            if (listAllowedSongs.contains(currentUser.getCurrentNameSong())) {
                for (int i = 0; i < listAllowedSongs.size(); i++) {
                    if (listAllowedSongs.get(i).equals(currentUser.getCurrentNameSong())) {
                        checkItem[0] = i;
                        break;
                    }
                }
            } else {
                checkItem[0] = 0;
            }
            builder.setSingleChoiceItems(
                    allAllowedSongs,
                    checkItem[0],
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            checkItem[0] = item;
                        }
                    }
            );
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String nameCurrentSong = "";
                    nameCurrentSong = listAllowedSongs.get(checkItem[0]);
                    if (!nameCurrentSong.isEmpty()) {
                        currentUser.setCurrentNameSong(nameCurrentSong);
                        SingletonManagerUsers.changeUser(currentUser);
                        preferenceCurrentSong.setSummary(currentUser.getCurrentNameSong());
                    }
                }
            });
        }
        if (preference == preferenceCurrentInstrument) {
            builder.setTitle(getResources().getString(R.string.current_instrument));
            final List<String> listAllowedInstruments = currentUser.getAllowedInstruments();
            final String[] allAllowedInstruments = new String[listAllowedInstruments.size()];
            listAllowedInstruments.toArray(allAllowedInstruments);
            final int[] checkItem = {0};
            if (listAllowedInstruments.contains(currentUser.getCurrentNameInstrument())) {
                for (int i = 0; i < listAllowedInstruments.size(); i++) {
                    if (listAllowedInstruments.get(i).equals(currentUser.getCurrentNameInstrument())) {
                        checkItem[0] = i;
                        break;
                    }
                }
            } else {
                checkItem[0] = 0;
            }
            builder.setSingleChoiceItems(
                    allAllowedInstruments,
                    checkItem[0],
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            checkItem[0] = item;
                        }
                    }
            );
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String nameCurrentInstrument = "";
                    nameCurrentInstrument = listAllowedInstruments.get(checkItem[0]);
                    if (!nameCurrentInstrument.isEmpty()) {
                        currentUser.setCurrentNameInstrument(nameCurrentInstrument);
                        SingletonManagerUsers.changeUser(currentUser);
                        preferenceCurrentInstrument.setSummary(currentUser.getCurrentNameInstrument());
                    }
                }
            });
        }
        if (preference == preferenceCurrentFret) {
            builder.setTitle(getResources().getString(R.string.current_fret));
            final List<String> listAllowedFrets = currentUser.getAllowedFrets();
            final String[] allAllowedFrets = new String[listAllowedFrets.size()];
            listAllowedFrets.toArray(allAllowedFrets);
            final int[] checkItem = {0};
            if (listAllowedFrets.contains(currentUser.getCurrentNameFret())) {
                for (int i = 0; i < listAllowedFrets.size(); i++) {
                    if (listAllowedFrets.get(i).equals(currentUser.getCurrentNameFret())) {
                        checkItem[0] = i;
                        break;
                    }
                }
            } else {
                checkItem[0] = 0;
            }
            builder.setSingleChoiceItems(
                    allAllowedFrets,
                    checkItem[0],
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            checkItem[0] = item;
                        }
                    }
            );
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String nameCurrentFret = "";
                    nameCurrentFret = listAllowedFrets.get(checkItem[0]);
                    if (!nameCurrentFret.isEmpty()) {
                        currentUser.setCurrentNameFret(nameCurrentFret);
                        SingletonManagerUsers.changeUser(currentUser);
                        preferenceCurrentFret.setSummary(currentUser.getCurrentNameFret());
                    }
                }
            });
        }
        if (preference == preferenceCurrentBackground) {
            builder.setTitle(getResources().getString(R.string.current_background));
            final List<String> listAllowedBackground = currentUser.getAllowedBackgrounds();
            final String[] allAllowedBackgrounds = new String[listAllowedBackground.size()];
            listAllowedBackground.toArray(allAllowedBackgrounds);
            final int[] checkItem = {0};
            if (listAllowedBackground.contains(currentUser.getCurrentNameBackground())) {
                for (int i = 0; i < listAllowedBackground.size(); i++) {
                    if (listAllowedBackground.get(i).equals(currentUser.getCurrentNameBackground())) {
                        checkItem[0] = i;
                        break;
                    }
                }
            } else {
                checkItem[0] = 0;
            }
            builder.setSingleChoiceItems(
                    allAllowedBackgrounds,
                    checkItem[0],
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            checkItem[0] = item;
                        }
                    }
            );
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String nameCurrentBackground = "";
                    nameCurrentBackground = listAllowedBackground.get(checkItem[0]);
                    if (!nameCurrentBackground.isEmpty()) {
                        currentUser.setCurrentNameBackground(nameCurrentBackground);
                        SingletonManagerUsers.changeUser(currentUser);
                        preferenceCurrentBackground.setSummary(currentUser.getCurrentNameBackground());
                    }
                }
            });
        }
        if (preference == preferenceCurrentString) {
            builder.setTitle(getResources().getString(R.string.current_strings));
            final List<String> listAllowedStrings = currentUser.getAllowedStrings();
            final String[] allAllowedStrings = new String[listAllowedStrings.size()];
            listAllowedStrings.toArray(allAllowedStrings);
            final int[] checkItem = {0};
            if (listAllowedStrings.contains(currentUser.getCurrentNameString())) {
                for (int i = 0; i < listAllowedStrings.size(); i++) {
                    if (listAllowedStrings.get(i).equals(currentUser.getCurrentNameString())) {
                        checkItem[0] = i;
                        break;
                    }
                }
            } else {
                checkItem[0] = 0;
            }
            builder.setSingleChoiceItems(
                    allAllowedStrings,
                    checkItem[0],
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            checkItem[0] = item;
                        }
                    }
            );
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String nameCurrentString = "";
                    nameCurrentString = listAllowedStrings.get(checkItem[0]);
                    if (!nameCurrentString.isEmpty()) {
                        currentUser.setCurrentNameString(nameCurrentString);
                        SingletonManagerUsers.changeUser(currentUser);
                        preferenceCurrentString.setSummary(currentUser.getCurrentNameString());
                    }
                }
            });
        }
        builder.setNegativeButton(getResources().getString(R.string.cancel), null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
