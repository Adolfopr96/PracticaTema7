package com.example.adolfo.practicatema7;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.adolfo.practicatema7.DatabaseManager.Sites;

import java.util.ArrayList;
import java.util.List;

import static com.example.adolfo.practicatema7.R.id.rb_main;

public class CardAdapter extends ArrayAdapter {
    private List<Sites> listado = new ArrayList<>();

    static class CardViewHolder {
        TextView line1;
        TextView line2;
        TextView line3;
    }

    public CardAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public void add(Sites object) {
        listado.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.listado.size();
    }

    @Override
    public Sites getItem(int index) {
        return this.listado.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            CardViewHolder viewHolder = new CardViewHolder();
            viewHolder.line1 = row.findViewById(R.id.line1);
            Sites s = getItem(position);
            viewHolder.line1.setText(s.getNameSite());
            RatingBar rb;
            rb = row.findViewById(R.id.rb_main);
            rb.setRating(s.getRatingSite());
            row.setTag(viewHolder);
        }
        return row;
    }

}
