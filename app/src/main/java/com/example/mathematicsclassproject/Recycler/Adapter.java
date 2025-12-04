package com.example.mathematicsclassproject.Recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mathematicsclassproject.DetailsActivity;
import com.example.mathematicsclassproject.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<Model> chapterlist;

    public Adapter(Context context, ArrayList<Model> chapterlist) {
        this.context = context;
        this.chapterlist = chapterlist;
    }
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Model model = chapterlist.get(position);
        holder.chapter.setText(model.getChapter());

        holder.itemView.setOnClickListener(v -> {
            if (model.getPdfLink() == null || model.getPdfLink().isEmpty()) {
                Toast.makeText(context, "Chapter not available", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("img", model.getImg());
                intent.putExtra("pdfLink", model.getPdfLink());
                intent.putExtra("videoLink", model.getVideoLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
       return chapterlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView chapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chapter = itemView.findViewById(R.id.chapter);
        }
    }
}
