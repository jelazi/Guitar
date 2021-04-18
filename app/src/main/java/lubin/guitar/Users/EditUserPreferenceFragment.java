package lubin.guitar.Users;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lubin.guitar.Files.FileManager;
import lubin.guitar.R;
import lubin.guitar.Song.Songs;

public class EditUserPreferenceFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener{
    SharedPreferences settings;
    EditTextPreference preferenceNameUser;
    EditTextPreference preferencePassUser;
    EditTextPreference preferenceCoinUser;
    Preference preferenceCurrentLevel;
    Preference preferenceAllowLevel;
    Preference preferenceListSongs;
    Preference preferenceListInstruments;
    Preference preferenceListFrets;
    Preference preferenceListBackgrounds;
    Preference preferenceListStrings;

    Preference preferenceCurrentSong;
    Preference preferenceCurrentInstrument;
    Preference preferenceCurrentFret;
    Preference preferenceCurrentBackground;
    Preference preferenceCurrentString;

    SwitchPreference preferenceStopBeforeTone;
    User currentUser;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.edit_user);
        initPreference();
        fillPreferences();
    }


    private void initPreference () {
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preferenceNameUser = (EditTextPreference) findPreference("name_user");
        preferencePassUser = (EditTextPreference) findPreference("pass_user");
        preferenceCoinUser = (EditTextPreference) findPreference("coin_user");
        preferenceListSongs = findPreference("list_songs");
        preferenceListSongs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                controlWrongAllowData(preferenceListSongs);

                return true;
            }
        });
        preferenceAllowLevel = (Preference) findPreference("allow_level");
        preferenceAllowLevel.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showList(preferenceAllowLevel);
                return true;
            }
        });
        preferenceCurrentLevel = (Preference) findPreference("current_level");
        preferenceCurrentLevel.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showList(preferenceCurrentLevel);
                return true;
            }
        });

        preferenceListInstruments = (Preference) findPreference("list_instruments");
        preferenceListInstruments.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                controlWrongAllowData(preferenceListInstruments);

                return true;
            }
        });
        preferenceListFrets = (Preference) findPreference("list_frets");
        preferenceListFrets.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                controlWrongAllowData(preferenceListFrets);

                return true;
            }
        });
        preferenceListStrings = (Preference) findPreference("list_strings");
        preferenceListStrings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                controlWrongAllowData(preferenceListStrings);
                return true;
            }
        });
        preferenceListBackgrounds = (Preference) findPreference("list_backgrounds");
        preferenceListBackgrounds.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                controlWrongAllowData(preferenceListBackgrounds);
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
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        Preference pref = findPreference(key);
        if (pref == preferenceNameUser) {
           String newName = preferenceNameUser.getText();
           if (SingletonManagerUsers.isUniqueNameUser(newName, currentUser.getID(), this.getActivity())) {
               currentUser.setName(newName);
               preferenceNameUser.setSummary(newName);
           } else {
               Toast.makeText(getActivity(), getResources().getString(R.string.illegal_name), Toast.LENGTH_SHORT).show();
               return;
           }
        }
        if (pref == preferencePassUser) {
            String newPass = preferencePassUser.getText();
            currentUser.setPass(newPass);
            preferencePassUser.setSummary(newPass);
        }
        if (pref == preferenceCoinUser) {
            boolean isCorrect = android.text.TextUtils.isDigitsOnly(preferenceCoinUser.getText());
            if (isCorrect) {
                int newCoinValue = Integer.parseInt(preferenceCoinUser.getText());
                currentUser.setCoins(newCoinValue);
                preferenceCoinUser.setSummary(preferenceCoinUser.getText());
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.wrong_number), Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (pref == preferenceStopBeforeTone) {
            currentUser.setChoiceMultiTone(preferenceStopBeforeTone.isChecked());
        }
        SingletonManagerUsers.changeUser(currentUser);

    }




    protected void controlWrongAllowData (Preference preference) {
        if (preference == preferenceListBackgrounds) {
            List<String> wrongData = SingletonManagerUsers.getWrongDataCurrentUser(getActivity(), UserList.BACKGROUNDSLIST);
            if (wrongData.isEmpty()) {
                showList(preferenceListBackgrounds);
            } else {
                showAlertWrongData(preferenceListBackgrounds, wrongData);
            }
        }
        if (preference == preferenceListFrets) {
            List<String> wrongData = SingletonManagerUsers.getWrongDataCurrentUser(getActivity(), UserList.FRETSLIST);
            if (wrongData.isEmpty()) {
                showList(preferenceListFrets);
            } else {
                showAlertWrongData(preferenceListFrets, wrongData);
            }
        }
        if (preference == preferenceListSongs) {
            List<String> wrongData = SingletonManagerUsers.getWrongDataCurrentUser(getActivity(), UserList.SONGSLIST);
            if (wrongData.isEmpty()) {
                showList(preferenceListSongs);
            } else {
                showAlertWrongData(preferenceListSongs, wrongData);
            }
        }
        if (preference == preferenceListInstruments) {
            List<String> wrongData = SingletonManagerUsers.getWrongDataCurrentUser(getActivity(), UserList.INSTRUMENTSLIST);
            if (wrongData.isEmpty()) {
                showList(preferenceListInstruments);
            } else {
                showAlertWrongData(preferenceListInstruments, wrongData);
            }
        }
        if (preference == preferenceListStrings) {
            List<String> wrongData = SingletonManagerUsers.getWrongDataCurrentUser(getActivity(), UserList.STRINGSLIST);
            if (wrongData.isEmpty()) {
                showList(preferenceListStrings);
            } else {
                showAlertWrongData(preferenceListStrings, wrongData);
            }
        }
    }

    protected void showAlertWrongData (Preference preference, List<String> wrongData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (preference == preferenceListBackgrounds) {
            builder.setTitle(getResources().getString(R.string.wrong_name_background));
            String listString = "";
            for (String s : wrongData) {
                listString += s + "\t";
            }
            String message = getResources().getString(R.string.warning_wrong_backgrounds) + listString;
            builder.setMessage(message);
            builder.setPositiveButton(getResources().getString(R.string.erase), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showList(preferenceListBackgrounds);
                    SingletonManagerUsers.eraseWrongDataCurrentUser(getActivity(), UserList.BACKGROUNDSLIST);
                }
            });
        }
        if (preference == preferenceListFrets) {
            builder.setTitle(getResources().getString(R.string.wrong_name_frets));
            String listString = "";
            for (String s : wrongData) {
                listString += s + "\t";
            }
            String message = getResources().getString(R.string.warning_wrong_frets) + listString;
            builder.setMessage(message);
            builder.setPositiveButton(getResources().getString(R.string.erase), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showList(preferenceListFrets);
                    SingletonManagerUsers.eraseWrongDataCurrentUser(getActivity(), UserList.FRETSLIST);
                }
            });
        }
        if (preference == preferenceListSongs) {
            builder.setTitle(getResources().getString(R.string.wrong_name_songs));
            String listString = "";
            for (String s : wrongData) {
                listString += s + "\t";
            }
            String message = getResources().getString(R.string.warning_wrong_songs) + listString;
            builder.setMessage(message);
            builder.setPositiveButton(getResources().getString(R.string.erase), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showList(preferenceListSongs);
                    SingletonManagerUsers.eraseWrongDataCurrentUser(getActivity(), UserList.SONGSLIST);
                }
            });
        }
        if (preference == preferenceListInstruments) {
            builder.setTitle(getResources().getString(R.string.wrong_name_instruments));
            String listString = "";
            for (String s : wrongData) {
                listString += s + "\t";
            }
            String message = getResources().getString(R.string.warning_wrong_instruments) + listString;
            builder.setMessage(message);
            builder.setPositiveButton(getResources().getString(R.string.erase), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showList(preferenceListInstruments);
                    SingletonManagerUsers.eraseWrongDataCurrentUser(getActivity(), UserList.INSTRUMENTSLIST);
                }
            });
        }
        if (preference == preferenceListStrings) {
            builder.setTitle(getResources().getString(R.string.wrong_name_strings));
            String listString = "";
            for (String s : wrongData) {
                listString += s + "\t";
            }
            String message = getResources().getString(R.string.warning_wrong_strings) + listString;
            builder.setMessage(message);
            builder.setPositiveButton(getResources().getString(R.string.erase), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showList(preferenceListStrings);
                    SingletonManagerUsers.eraseWrongDataCurrentUser(getActivity(), UserList.STRINGSLIST);
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    protected void showList (Preference preference) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (preference == preferenceAllowLevel) {
            builder.setTitle(getResources().getString(R.string.max_level));
            final List<String> listAllowedLevel = SingletonManagerUsers.getListAllowLevel(UserLevel.CHAMPION, this.getActivity());
            final String[] arrayAllowedLevel = new String[listAllowedLevel.size()];
            listAllowedLevel.toArray(arrayAllowedLevel);
            final int[] checkItem = {currentUser.getAllowedLevel().getValue() - 1};
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
                    currentUser.setAllowedLevel(SingletonManagerUsers.getUserLevelByValue(checkItem[0]+1));
                    SingletonManagerUsers.changeUser(currentUser);
                    preferenceAllowLevel.setSummary(SingletonManagerUsers.getNameUserLevel(currentUser.getAllowedLevel(), EditUserPreferenceFragment.this.getActivity()));
                }
            });
        }
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
                    currentUser.setCurrentLevel(SingletonManagerUsers.getUserLevelByValue(checkItem[0]+1), EditUserPreferenceFragment.this.getActivity());
                    SingletonManagerUsers.changeUser(currentUser);
                    preferenceCurrentLevel.setSummary(SingletonManagerUsers.getNameUserLevel(currentUser.getCurrentLevel(), EditUserPreferenceFragment.this.getActivity()));
                }
            });
        }
        if (preference == preferenceListBackgrounds) {
            builder.setTitle(getResources().getString(R.string.allowed_backgrounds));
            FileManager.loadData(getActivity());
            final ArrayList<String> listBackgrounds = FileManager.getNameBackgrounds();
            String[] allBackgrounds = new String[listBackgrounds.size()];
            listBackgrounds.toArray(allBackgrounds);
            final boolean[] checkedBackground = SingletonManagerUsers.getCheckedDataCurrentUser(listBackgrounds, UserList.BACKGROUNDSLIST);
            builder.setMultiChoiceItems(allBackgrounds, checkedBackground, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                }
            });
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<String>namesSelectedBackgrounds = new ArrayList<>();
                    for (int i = 0; i < listBackgrounds.size();i ++) {
                        if (checkedBackground[i]) {
                            namesSelectedBackgrounds.add(listBackgrounds.get(i));
                        }
                    }
                    currentUser.setAllowedBackgrounds(namesSelectedBackgrounds);
                    SingletonManagerUsers.changeUser(currentUser);
                }
            });
        }
        if (preference == preferenceListFrets) {
            builder.setTitle(getResources().getString(R.string.allowed_frets));
            FileManager.loadData(getActivity());
            final ArrayList<String> listFrets = FileManager.getNameFrets();
            String[] allFrets = new String[listFrets.size()];
            listFrets.toArray(allFrets);
            final boolean[] checkedFrets = SingletonManagerUsers.getCheckedDataCurrentUser(listFrets, UserList.FRETSLIST);
            builder.setMultiChoiceItems(allFrets, checkedFrets, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                }
            });
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<String>namesSelectedFrets = new ArrayList<>();
                    for (int i = 0; i < listFrets.size();i ++) {
                        if (checkedFrets[i]) {
                            namesSelectedFrets.add(listFrets.get(i));
                        }
                    }
                    currentUser.setAllowedFrets(namesSelectedFrets);
                    SingletonManagerUsers.changeUser(currentUser);
                }
            });
        }
        if (preference == preferenceListSongs) {
            builder.setTitle(getResources().getString(R.string.allowed_songs));
            FileManager.loadData(getActivity());
            final ArrayList<String> listSongs = FileManager.getNameSongs();
            String[] allSongs = new String[listSongs.size()];
            listSongs.toArray(allSongs);
            final boolean[] checkedSongs = SingletonManagerUsers.getCheckedDataCurrentUser(listSongs, UserList.SONGSLIST);
            builder.setMultiChoiceItems(allSongs, checkedSongs, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                }
            });
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<String>namesSelectedSongs = new ArrayList<>();
                    for (int i = 0; i < listSongs.size();i ++) {
                        if (checkedSongs[i]) {
                            namesSelectedSongs.add(listSongs.get(i));
                        }
                    }
                    currentUser.setAllowedSongs(namesSelectedSongs);
                    SingletonManagerUsers.changeUser(currentUser);
                }
            });
        }
        if (preference == preferenceListInstruments) {
            builder.setTitle(getResources().getString(R.string.allowed_instruments));
            FileManager.loadData(getActivity());
            final ArrayList<String> listInstruments = FileManager.getNameInstruments();
            String[] allInstruments = new String[listInstruments.size()];
            listInstruments.toArray(allInstruments);
            final boolean[] checkedInstruments = SingletonManagerUsers.getCheckedDataCurrentUser(listInstruments, UserList.INSTRUMENTSLIST);
            builder.setMultiChoiceItems(allInstruments, checkedInstruments, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                }
            });
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<String>namesSelectedInstruments = new ArrayList<>();
                    for (int i = 0; i < listInstruments.size();i ++) {
                        if (checkedInstruments[i]) {
                            namesSelectedInstruments.add(listInstruments.get(i));
                        }
                    }
                    currentUser.setAllowedInstruments(namesSelectedInstruments);
                    SingletonManagerUsers.changeUser(currentUser);
                }
            });
        }
        if (preference == preferenceListStrings) {
            builder.setTitle(getResources().getString(R.string.allowed_strings));
            FileManager.loadData(getActivity());
            final ArrayList<String> listStrings = FileManager.getNameStrings();
            String[] allStrings = new String[listStrings.size()];
            listStrings.toArray(allStrings);
            final boolean[] checkedStrings = SingletonManagerUsers.getCheckedDataCurrentUser(listStrings, UserList.STRINGSLIST);
            builder.setMultiChoiceItems(allStrings, checkedStrings, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                }
            });
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<String>namesSelectedStrings = new ArrayList<>();
                    for (int i = 0; i < listStrings.size();i ++) {
                        if (checkedStrings[i]) {
                            namesSelectedStrings.add(listStrings.get(i));
                        }
                    }
                    currentUser.setAllowedStrings(namesSelectedStrings);
                    SingletonManagerUsers.changeUser(currentUser);
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
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
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


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        SingletonManagerUsers.setCurrentUser(currentUser);
    }

    private void fillPreferences() {
        if (currentUser == null || currentUser.getID() == 0) {
            Log.e("something problem", "problem with current User data");
            return;
        }
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .registerOnSharedPreferenceChangeListener(this);
        preferenceAllowLevel.setSummary(SingletonManagerUsers.getNameUserLevel(currentUser.getAllowedLevel(), this.getActivity()));
        preferenceNameUser.setSummary(currentUser.getName());
        preferenceNameUser.setText(currentUser.getName());
        preferencePassUser.setSummary(currentUser.getPass());
        preferencePassUser.setText(currentUser.getPass());
        preferenceCoinUser.setSummary(String.valueOf(currentUser.getCoins()));
        preferenceCoinUser.setText(String.valueOf(currentUser.getCoins()));
        preferenceCurrentLevel.setSummary(SingletonManagerUsers.getNameUserLevel(currentUser.getCurrentLevel(), this.getActivity()));
        preferenceCurrentSong.setSummary(currentUser.getCurrentNameSong());
        preferenceCurrentInstrument.setSummary(currentUser.getCurrentNameInstrument());
        preferenceCurrentFret.setSummary(currentUser.getCurrentNameFret());
        preferenceCurrentBackground.setSummary(currentUser.getCurrentNameBackground());
        preferenceCurrentString.setSummary(currentUser.getCurrentNameString());
        preferenceStopBeforeTone.setChecked(currentUser.isChoiceMultiTone());
    }
}
