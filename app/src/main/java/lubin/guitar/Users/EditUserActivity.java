package lubin.guitar.Users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EditUserActivity extends AppCompatActivity {

    EditUserPreferenceFragment editUserPreferenceFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("user_name");
        initFragment(userName);
    }


    protected void initFragment (String userName) {
        editUserPreferenceFragment = new EditUserPreferenceFragment();
        editUserPreferenceFragment.setCurrentUser(SingletonManagerUsers.getUserByName(userName));
        getFragmentManager().beginTransaction().replace(android.R.id.content, editUserPreferenceFragment).commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}





