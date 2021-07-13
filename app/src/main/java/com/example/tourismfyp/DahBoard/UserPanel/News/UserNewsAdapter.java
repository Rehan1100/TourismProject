package com.example.tourismfyp.DahBoard.UserPanel.News;

import android.content.Context;
import android.content.DialogInterface;
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

import com.example.tourismfyp.DahBoard.UserPanel.BookingPost.AddToCartClass;
import com.example.tourismfyp.DahBoard.UserPanel.Cart.CartModelClass;
import com.example.tourismfyp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class UserNewsAdapter extends RecyclerView.Adapter<UserNewsAdapter.Viewholder> {
    private List<UserNewsModelClass> modelCLassList;
    List<UserNewsModelClass> modelCLassListAll;
    Context context;
    public String Roll;
    public String LoginUserEmail,LoginUserFirstName,LoginUserLastName;

    public UserNewsAdapter(List<UserNewsModelClass> modelCLassList, Context context, String Loginuseremail, String Loginfirstname, String LoginLastname) {
        this.modelCLassList = modelCLassList;
        this.modelCLassListAll = new ArrayList<>(modelCLassList);
        this.context = context;
        this.LoginUserEmail=Loginuseremail;
        this.LoginUserFirstName=Loginfirstname;
        this.LoginUserLastName=LoginLastname;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsrow, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {

        final String id = modelCLassList.get(position).getId();
        final String loginuseremail = modelCLassList.get(position).getLoginuseremail();
        final String name = modelCLassList.get(position).getName();
        final String news = modelCLassList.get(position).getNews();
        final String postdate = modelCLassList.get(position).getPostdate();
        final String posttime = modelCLassList.get(position).getPosttime();




        holder.setData(name, news, posttime);
        SharedPreferences getShared2 = context.getApplicationContext().getSharedPreferences("RollName", MODE_PRIVATE);
        Roll = getShared2.getString("Roll", null);

    }

    public void removeItem(int position) {

        modelCLassList.remove(position);
        notifyItemRemoved(position);

    }
    @Override
    public int getItemCount() {
        return modelCLassList.size();
    }


        //Run on UIThread

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView name,post,posttime;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            post = itemView.findViewById(R.id.desc);
            posttime = itemView.findViewById(R.id.posttime);



        }


        public void setData(String Name, String News, String PostTime) {
            name.setText("Today News");
            post.setText(News);
            posttime.setText(PostTime);
        }
    }

}
