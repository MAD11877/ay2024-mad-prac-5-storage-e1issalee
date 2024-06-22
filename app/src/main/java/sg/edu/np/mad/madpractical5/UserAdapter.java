package sg.edu.np.mad.madpractical5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    ArrayList<User> list_objects;
    private ListActivity activity;


    public UserAdapter(ArrayList<User> list_objects, ListActivity activity) {
        this.list_objects = list_objects;
        this.activity = activity;
    }

    // Method to create a view holder for a username.
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_activity_list, parent, false);

        return new UserViewHolder(view);
    }

    //Method to bind username to a view holder
    public void onBindViewHolder(UserViewHolder holder, int position){
        //Get position of a username
        User list_items = list_objects.get(position);
        //Set username to the view holder based on custom_activity_list.xml
        holder.name.setText(list_items.getName());
        //Set description to the view holder based on custom_activity_list.xml
        holder.description.setText(list_items.getDescription());
        //Configure setOnClickListener( for the small image on the view holder based on custom_activity_list.cml

        holder.smallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Profile");
                builder.setMessage(list_items.getName());
                builder.setPositiveButton("VIEW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when VIEW button is clicked
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.putExtra("name", list_items.getName());
                        intent.putExtra("description", list_items.getDescription());
                        intent.putExtra("followed", list_items.getFollowed());
                        intent.putExtra("id", list_items.getId());
                        v.getContext().startActivity(intent);
                    }
                });
                builder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when Cancel button is clicked
                        dialog.dismiss(); // Dismiss the dialog
                    }
                });
                builder.create().show(); // Show the AlertDialog
            }

        });
        String userId =String.valueOf(list_items.getName());
        if (userId.contains("7")) {
            holder.bigImage.setVisibility(View.VISIBLE);
        }
        else {
            holder.bigImage.setVisibility(View.GONE);
        }
    }
    public int getItemCount() {
        return list_objects.size();
    }
}
