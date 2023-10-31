package com.example.recycleviewcrud;

import static com.example.recycleviewcrud.DBMain.TABLENAME;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ModelViewHolder> {
    Context context;
    ArrayList<Model> list = new ArrayList<>();
    SQLiteDatabase sqLiteDatabase;
    //generate constructor

    public MyAdapter(Context context, int singledata, ArrayList<Model> list, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.list = list;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public MyAdapter.ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.singledata, null);
        return new ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ModelViewHolder holder, int position) {
        final Model model = list.get(position);
        holder.txtfname.setText(model.getFirstname());
        holder.txtlname.setText(model.getLastname());

        //click on button go to main activity
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id",model.getId());
                bundle.putString("fname",model.getFirstname());
                bundle.putString("lname",model.getLastname());
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("userdata",bundle);
                context.startActivity(intent);
            }
        });
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            DBMain dbMain = new DBMain(context);
            @Override
            public void onClick(View v) {
                sqLiteDatabase = dbMain.getReadableDatabase();
                long delete = sqLiteDatabase.delete(TABLENAME,"id="+model.getId(),null);
                if(delete!=-1){
                    Toast.makeText(context, "Delete success!", Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ModelViewHolder extends RecyclerView.ViewHolder {
        TextView txtfname,txtlname;
        Button btnedit,btndelete;
        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);
            txtfname = (TextView)itemView.findViewById(R.id.txtfname);
            txtlname = (TextView)itemView.findViewById(R.id.txtlname);
            btnedit = (Button)itemView.findViewById(R.id.btnedit);
            btndelete = (Button)itemView.findViewById(R.id.btndelete);
        }
    }
}
