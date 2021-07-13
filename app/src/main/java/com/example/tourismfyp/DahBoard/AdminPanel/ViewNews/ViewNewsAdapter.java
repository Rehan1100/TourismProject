package com.example.tourismfyp.DahBoard.AdminPanel.ViewNews;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.tourismfyp.DahBoard.AdminPanel.ViewNews.EditNews.EditNews;
import com.example.tourismfyp.DahBoard.AdminPanel.ViewOrganizer.ModelCLass;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Edit.EditPostActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.ViewAllOnCategory;
import com.example.tourismfyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ViewNewsAdapter extends RecyclerView.Adapter<ViewNewsAdapter.Viewholder>  {
    private List<NewsModelCLass> modelCLassList;
    List<NewsModelCLass> modelCLassListAll;
    Context context;
    public String Roll;

    public ViewNewsAdapter(List<NewsModelCLass> modelCLassList, Context context) {
        this.modelCLassList = modelCLassList;
        this.modelCLassListAll = new ArrayList<>(modelCLassList);
        this.context = context;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsrows, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {

        final String id = modelCLassList.get(position).getId();
        final String email = modelCLassList.get(position).getLoginuserEmail();
        final String name = modelCLassList.get(position).getName();
        final String news = modelCLassList.get(position).getNews();
        final String postDate = modelCLassList.get(position).getPostDate();
        final String postTime = modelCLassList.get(position).getPostTime();


        holder.setData(id, email,name, news,postDate,postTime);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence option[] = new CharSequence[]
                        {
                                "Edit", "Delete"
                        };

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Modification");
                dialog.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {

                            Intent intent = new Intent(context, EditNews.class);
                            intent.putExtra("id",id);
                            intent.putExtra("news",news);
                            intent.putExtra("postDate",postDate);
                            intent.putExtra("postTime",postTime);

                            context.startActivity(intent);
                            ((viewNews)context).finish();

                        }
                        if (which == 1) {
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference node = db.getReference("News");
                            Query query = node.orderByChild("id").equalTo(modelCLassList.get(position).getId());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.exists()) {
                                        for (DataSnapshot datas : snapshot.getChildren()) {
                                            String key = datas.getKey();
                                            node.child(key).removeValue();
                                            removeItem(position);

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                               /* Intent intent = new Intent(context, dashboard.class);
                                context.startActivity(intent);
                                ((dashboard)context).finish();
*/
                        }
                    }
                });
                dialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return modelCLassList.size();
    }

    public void removeItem(int position) {

        modelCLassList.remove(position);
        notifyItemRemoved(position);

    }
    class Viewholder extends RecyclerView.ViewHolder {

        private TextView Name,Email,News,PostDate,PostTime;
        private ImageView imageView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.AdminName);
            Email = itemView.findViewById(R.id.adminMail);
            News = itemView.findViewById(R.id.news);
            PostDate = itemView.findViewById(R.id.date);
            PostTime = itemView.findViewById(R.id.time);


        }


        private void setData(String id, String email, String name, String news, String postDate, String postTime) {
            Name.setText(name);
            Email.setText(email);
            News.setText(news);
            PostDate.setText(postDate);
            PostTime.setText(postTime);




        }
    }

}
