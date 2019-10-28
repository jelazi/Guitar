package lubin.guitar.Users;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;

import com.elvishew.xlog.XLog;

import lubin.guitar.R;

public class EditUserPreferenceFragment extends PreferenceFragment {
    SharedPreferences settings;
    EditTextPreference preferenceNameUser;
    EditTextPreference preferencePassUser;
    EditTextPreference preferenceCoinUser;
    CheckBoxPreference preferenceListSongs;
    CheckBoxPreference preferenceListInstruments;
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
        preferenceListSongs = (CheckBoxPreference) findPreference("list_songs");
        preferenceListInstruments = (CheckBoxPreference) findPreference("list_instruments");
        preferenceStopBeforeTone = (SwitchPreference) findPreference("stop_before_tone");
    }


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
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
