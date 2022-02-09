package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;


public class FilesAdapter extends
        RecyclerView.Adapter<FilesAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView fileNameTextView;
        public Button trackButton;
        public Button viewButton;

        public ViewHolder(View fileView){
            super(fileView);

            fileNameTextView = (TextView) fileView.findViewById(R.id.fileName);
            trackButton = (Button) fileView.findViewById(R.id.track_button);
            trackButton.setOnClickListener( new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(v.getContext(), MapsActivity.class);
                    intent.putExtra("FileName", fileNameTextView.getText());
                    v.getContext().startActivity(intent);
                }
            });
            viewButton = (Button) fileView.findViewById(R.id.view_button);
            viewButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(v.getContext(), FlightVisualizationActivity.class);
                    intent.putExtra("FileName", fileNameTextView.getText());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    private String[] fileNames;

    public FilesAdapter(String[] newNames){
        fileNames = newNames;
    }

    @Override
    public FilesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View fileView = inflater.inflate(R.layout.rv_file_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(fileView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FilesAdapter.ViewHolder viewHolder, int position){

        TextView textView = viewHolder.fileNameTextView;
        textView.setText(fileNames[position]);

        Button vButton = viewHolder.viewButton;
        vButton.setText("View");

        Button tButton = viewHolder.trackButton;
        tButton.setText("Track");

    }

    @Override
    public int getItemCount(){
        return fileNames.length;
    }
}
