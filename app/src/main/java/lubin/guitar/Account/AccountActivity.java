package lubin.guitar.Account;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lubin.guitar.Files.DialogType;
import lubin.guitar.GuitarActivity.PlayChordActivity;
import lubin.guitar.GuitarActivity.PreviewSongActivity;
import lubin.guitar.R;
import lubin.guitar.GuitarActivity.TrySongActivity;
import lubin.guitar.Shop.ShopActivity;
import lubin.guitar.SingletonSizeScreen;
import lubin.guitar.Users.SingletonManagerUsers;
import lubin.guitar.Users.User;

public class AccountActivity extends AppCompatActivity {
    Button btnAcc;
    ImageView img;
    EditText accountPass;
    Spinner nameSpinner;
    List<String>nameUsers;
    SharedPreferences settings;
    User currentUser;
    ListAdapter listActivity;
    List <String> listNameActivity;
    TextView lblName;
    TextView lblPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btnAcc = findViewById(R.id.btnAccount);
        btnAcc.setOnClickListener(openAccountListener);
        img = findViewById(R.id.AccGuitarist);
        android.view.animation.Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        img.startAnimation(animation1);
        accountPass = findViewById(R.id.AccountPass);
        nameSpinner = findViewById(R.id.Spinner01);
        lblName = findViewById(R.id.NameLabel);
        lblPass = findViewById(R.id.PassLabel);

        accountPass.setOnClickListener(onClickSetPass);

        nameUsers = SingletonManagerUsers.getListNamesUsers(false, this); //nacteni uzivatelu
        String[] arrayUsers = new String[nameUsers.size()];
        nameUsers.toArray(arrayUsers);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayUsers){
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                TextView tv = ((TextView) v);
                tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
                tv.setSingleLine();
                tv.setEllipsize(TextUtils.TruncateAt.END);
                tv.setTextSize(SingletonSizeScreen.getTextSizeSpinner());
                return v;
                }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                return getView(position, convertView, parent);
            }
            };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(adapter);

        settings = PreferenceManager.getDefaultSharedPreferences(this);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume () {
        super.onResume();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    protected void showDialog (DialogType dialogType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (dialogType == DialogType.CHOICE_ACTIVITY) {
            final DialogItem[] items = {
                    new DialogItem(getResources().getString(R.string.action_play_chords), R.mipmap.ic_table_large),
                    new DialogItem(getResources().getString(R.string.action_preview_song), R.mipmap.guitar_acoustic),
                    new DialogItem(getResources().getString(R.string.action_try_song), R.mipmap.ic_guitar_pick),
                    new DialogItem(getResources().getString(R.string.open_shop), R.mipmap.guitar_acoustic),
            };
            listNameActivity = new ArrayList<>();
            listNameActivity.add(getResources().getString(R.string.action_play_chords));
            listNameActivity.add(getResources().getString(R.string.action_preview_song));
            listNameActivity.add(getResources().getString(R.string.action_try_song));
            listNameActivity.add(getResources().getString(R.string.open_shop));


            listActivity = new ArrayAdapter<DialogItem>(
                    this,
                    android.R.layout.select_dialog_item,
                    android.R.id.text1,
                    items){
                public View getView(int position, View convertView, ViewGroup parent) {
                    //Use super class to create the View
                    View v = super.getView(position, convertView, parent);
                    TextView tv = (TextView)v.findViewById(android.R.id.text1);
                    tv.setText(items[position].getText());

                    //Put the image on the TextView
                    tv.setCompoundDrawablesWithIntrinsicBounds(items[position].getImage(), 0, 0, 0);

                    //Add margin between image and text (support various screen densities)
                    int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
                    tv.setCompoundDrawablePadding(dp5);

                    return v;
                }
            };

            builder.setTitle(getResources().getString(R.string.choice_activity));

            builder.setAdapter(listActivity, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openActivity(listNameActivity.get(which));
                }
            });

        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void openActivity (String activityName) {
        Intent i = new Intent(AccountActivity.this, PlayChordActivity.class);
            if (activityName.equals(getResources().getString(R.string.action_play_chords))) {
                i = new Intent(AccountActivity.this, PlayChordActivity.class);
            } else if (activityName.equals(getResources().getString(R.string.action_preview_song))) {
                i = new Intent(AccountActivity.this, PreviewSongActivity.class);
            } else if (activityName.equals(getResources().getString(R.string.action_try_song))) {
                i = new Intent(AccountActivity.this, TrySongActivity.class);
            } else if (activityName.equals(getResources().getString(R.string.open_shop))) {
                i = new Intent(AccountActivity.this, ShopActivity.class);
            }
        startActivity(i);
    }


    View.OnClickListener openAccountListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            currentUser = SingletonManagerUsers.getUserByName(nameSpinner.getSelectedItem().toString());
                if (currentUser.getPass().equals(accountPass.getText().toString())){
                    SingletonManagerUsers.setCurrentUser(currentUser);
                    showDialog(DialogType.CHOICE_ACTIVITY);
                    return;
                }
                Toast.makeText(AccountActivity.this, getResources().getString(R.string.warning_wrong_name_pass), Toast.LENGTH_SHORT).show();
                nameSpinner.setSelection(0);
                accountPass.setText("");
        }
    };




    View.OnClickListener onClickSetPass = new View.OnClickListener(){
        @Override
        public void onClick(View view){

        }
    };
}
