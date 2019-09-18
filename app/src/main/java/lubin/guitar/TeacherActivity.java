package lubin.guitar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TeacherActivity extends AppCompatActivity {

    Button editUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editUser = (Button) findViewById(R.id.edit_user);
        editUser.setOnClickListener(editUserListener);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch(id)
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    View.OnClickListener editUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i = new Intent(v.getContext(), EditUserActivity.class);
            startActivity(i);

        }
    };
}
