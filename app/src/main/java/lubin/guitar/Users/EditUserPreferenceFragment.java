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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lubin.guitar.Files.FileManager;
import lubin.guitar.R;
import lubin.guitar.Song.Songs;

public class EditUserPreferenceFragment extends PreferenceFragment {
    SharedPreferences settings;
    EditTextPreference preferenceNameUser;
    EditTextPreference preferencePassUser;
    EditTextPreference preferenceCoinUser;
    Preference preferenceListSongs;
    Preference preferenceListInstruments;
    Preference preferenceListFrets;
    Preference preferenceListBackgrounds;
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
        preferenceListSongs = (Preference) findPreference("list_songs");
        preferenceListSongs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                controlWrongAllowData(preferenceListSongs);

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
        preferenceListBackgrounds = (Preference) findPreference("list_backgrounds");
        preferenceListBackgrounds.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                controlWrongAllowData(preferenceListBackgrounds);
                return true;
            }
        });

        preferenceStopBeforeTone = (SwitchPreference) findPreference("stop_before_tone");
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
    }

    protected void showAlertWrongData (Preference preference, List<String> wrongData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (preference == preferenceListBackgrounds) {
            builder.setTitle("Špatné jména pozadí");
            String listString = "";
            for (String s : wrongData)
            {
                listString += s + "\t";
            }
            String message = "Našli jsme tyto špatně zapsané jména pozadí: " + listString;
            builder.setMessage(message);
            builder.setPositiveButton("Vymazat", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showList(preferenceListBackgrounds);
                    SingletonManagerUsers.eraseWrongDataCurrentUser(getActivity(), UserList.BACKGROUNDSLIST);
                }
            });
        }
        if (preference == preferenceListFrets) {
            builder.setTitle("Špatné jména pražců");
            String listString = "";
            for (String s : wrongData)
            {
                listString += s + "\t";
            }
            String message = "Našli jsme tyto špatně zapsané jména pražců: " + listString;
            builder.setMessage(message);
            builder.setPositiveButton("Vymazat", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showList(preferenceListFrets);
                    SingletonManagerUsers.eraseWrongDataCurrentUser(getActivity(), UserList.FRETSLIST);
                }
            });
        }
        if (preference == preferenceListSongs) {
            builder.setTitle("Špatné jména písní");
            String listString = "";
            for (String s : wrongData)
            {
                listString += s + "\t";
            }
            String message = "Našli jsme tyto špatně zapsané jména písní: " + listString;
            builder.setMessage(message);
            builder.setPositiveButton("Vymazat", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showList(preferenceListSongs);
                    SingletonManagerUsers.eraseWrongDataCurrentUser(getActivity(), UserList.SONGSLIST);
                }
            });
        }
        if (preference == preferenceListInstruments) {
            builder.setTitle("Špatné názvy nástrojů");
            String listString = "";
            for (String s : wrongData)
            {
                listString += s + "\t";
            }
            String message = "Našli jsme tyto špatně zapsané jména nástrojů: " + listString;
            builder.setMessage(message);
            builder.setPositiveButton("Vymazat", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showList(preferenceListInstruments);
                    SingletonManagerUsers.eraseWrongDataCurrentUser(getActivity(), UserList.INSTRUMENTSLIST);
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    protected void showList (Preference preference) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (preference == preferenceListBackgrounds) {
            builder.setTitle("Možné pozadí");
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
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
            builder.setTitle("Možné pražce");
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
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
            builder.setTitle("Možné písně");
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
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
            builder.setTitle("Možné Nástroje");
            Songs.fillSongs(getActivity());
            final ArrayList<String> listInstruments = Songs.getNameInstruments();
            String[] allInstruments = new String[listInstruments.size()];
            listInstruments.toArray(allInstruments);
            final boolean[] checkedInstruments = SingletonManagerUsers.getCheckedDataCurrentUser(listInstruments, UserList.INSTRUMENTSLIST);
            builder.setMultiChoiceItems(allInstruments, checkedInstruments, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

        builder.setNegativeButton("Cancel", null);
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
        preferenceNameUser.setSummary(currentUser.getName());
        preferencePassUser.setSummary(currentUser.getPass());
        preferenceCoinUser.setSummary(String.valueOf(currentUser.getCoins()));
    }
}
