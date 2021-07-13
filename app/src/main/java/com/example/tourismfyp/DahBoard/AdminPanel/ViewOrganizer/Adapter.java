package com.example.tourismfyp.DahBoard.AdminPanel.ViewOrganizer;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tourismfyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> implements Filterable {
    private List<ModelCLass> modelCLassList;
    List<ModelCLass> modelCLassListAll;
    Context context;
    public String Roll;

    public Adapter(List<ModelCLass> modelCLassList, Context context) {
        this.modelCLassList = modelCLassList;
        this.modelCLassListAll = new ArrayList<>(modelCLassList);
        this.context = context;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {

        final String name = modelCLassList.get(position).getName();
        final String email = modelCLassList.get(position).getEmail();
        final String pass = modelCLassList.get(position).getPass();
        final String image = modelCLassList.get(position).getImage();


        holder.setData(name, email,image);
        SharedPreferences getShared2 = context.getApplicationContext().getSharedPreferences("RollName", MODE_PRIVATE);
        Roll = getShared2.getString("Roll", null);

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence[] options = {"Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Are you want to Delete it?");
                dialog.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {

                            //Delete Code is Here Admin Can't Delete the Account of Organizer but Admin can banned the organizer account

                            Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                            deleteAccount(modelCLassList.get(position).getEmail(),modelCLassList.get(position).getPass());

                        }
                    }
                });dialog.show();



            }

        });*/

    }

    @Override
    public int getItemCount() {
        return modelCLassList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //Run on BackgroundThread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelCLass> filteredlist = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filteredlist.addAll(modelCLassListAll);
            } else {
                for (ModelCLass data : modelCLassListAll) {
                    if (data.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredlist.add(data);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredlist;
            return filterResults;
        }

        //Run on UIThread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelCLassList.clear();
            modelCLassList.addAll((Collection<? extends ModelCLass>) results.values);
            notifyDataSetChanged();
        }
    };

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView name,email;
        private ImageView imageView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.OrganizerName);
            email = itemView.findViewById(R.id.OrganizerEmail);


        }


        private void setData(String OrganizerName, String OrganizerEmail,String image) {
            name.setText(OrganizerName);
            email.setText(OrganizerEmail);

            if (image!=null)
            {
            Picasso.get().load(image).into(imageView);
            }


        }
    }

    private void deleteAccount(String email,String pass) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    }
}
