package sg.edu.np.mad.madpractical5;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;


public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        DatabaseHandler dbHandler = new DatabaseHandler(this, null, null, 1);

        // Create a list of 20 Random Users
     //   ArrayList<User> user_data = new ArrayList<>();
     //   for (int i = 1; i < 21; i++) {
     //       Random rand = new Random();
     //       int upperbound = 99999;
     //       int random_int = rand.nextInt(upperbound);
     //       int random_int2 = rand.nextInt(upperbound);
     //       int random_int3 = rand.nextInt(2);
     //       String name = "Name" + random_int;
     //       String description = "Description " + random_int2;
     //       int id = i;

     //       boolean followed = random_int3 == 1;

     //       User user = new User(name, description, id, followed);
     //       user_data.add(user);
     //   }

        // Add users to the database
     //   for (User user : user_data) {
     //       dbHandler.addUser(user);
     //   }


        // Add This (RecycleView)
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        UserAdapter userAdapter =  new UserAdapter(dbHandler.getUsers(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);

    }
}