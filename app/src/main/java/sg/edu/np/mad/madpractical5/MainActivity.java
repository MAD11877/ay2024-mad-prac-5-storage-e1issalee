package sg.edu.np.mad.madpractical5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private DatabaseHandler dbHandler;
    private User user;

    // Define the TAG variable at the class level
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHandler dbHandler = new DatabaseHandler(this, null, null, 1);

        // Retrieve user data from the intent
        Intent receivingEnd = getIntent();
        String name = receivingEnd.getStringExtra("name");
        String description = receivingEnd.getStringExtra("description");
        boolean followed = receivingEnd.getBooleanExtra("followed", false); // Use getBooleanExtra for boolean
        int id = receivingEnd.getIntExtra("id", 1); // Use getIntExtra for int

        // Retrieve the user from the database based on username
        user = dbHandler.getUsers(name);

        // If user is null, create a new user with intent data
        if (user == null) {
            user = new User(name, description, id, followed);
            // Add the new user to the database
            dbHandler.addUser(user);
        } else {
            // If user is found, update its properties
            user.setName(name);
            user.setDescription(description);
            user.setFollowed(followed);
            user.setId(id);
            // Update the existing user in the database
            dbHandler.updateUser(user);
        }

        // Get the TextViews and buttons from the layout
        TextView tvName = findViewById(R.id.textView); // Update to match the ID in your layout
        TextView tvDescription = findViewById(R.id.textView1); // Update to match the ID in your layout
        Button btnFollow = findViewById(R.id.button1);
        Button btnMessage = findViewById(R.id.button2);

        // Set the TextViews with the user's name, description and default button message
        tvName.setText(user.getName());
        tvDescription.setText(user.getDescription());
        btnFollow.setText(user.getFollowed() ? "Unfollow" : "Follow");
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check the current text of the button
                String buttonText = btnFollow.getText().toString();

                // Toggle between "Follow" and "Unfollow"
                if (buttonText.equals("Follow") || buttonText.equals("FOLLOW")) {
                    btnFollow.setText("Unfollow");
                    Toast.makeText(MainActivity.this, "Followed", Toast.LENGTH_SHORT).show();
                    user.setFollowed(true); // Update user object
                } else {
                    btnFollow.setText("Follow");
                    Toast.makeText(MainActivity.this, "Unfollowed", Toast.LENGTH_SHORT).show();
                    user.setFollowed(false); // Update user object
                }
                // Update the user in the database
                dbHandler.updateUser(user);
            }
        });
    }
}