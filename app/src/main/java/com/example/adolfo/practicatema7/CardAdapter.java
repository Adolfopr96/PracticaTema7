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

public class CardAdapter extends ArrayAdapter {
    private List <Sites> listado = new ArrayList<>();

    static class CardViewHolder {
        TextView line;
        RatingBar line2;
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
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);

            viewHolder = new CardViewHolder();
            viewHolder.line = row.findViewById(R.id.line1);
            viewHolder.line2 = row.findViewById(R.id.rb_main);

            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) row.getTag();
        }
        Sites l = getItem(position);

        viewHolder.line.setText(l.getNameSite());
        viewHolder.line2.setRating(l.getRatingSite());
        return row;
    }
}
