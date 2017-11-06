package com.alexdoub.bitcoinmanager.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import com.alexdoub.bitcoinmanager.interfaces.Listable;

/**
 * Created by Alex on 11/3/2017.
 */

public class GenericListAdapter<T extends Listable> implements ListAdapter {

    List<DataSetObserver> observers = new ArrayList<>();
    List<T> listContent = new ArrayList<>();

    public int getCount() {
        return listContent.size();
    }

    public void clearListContent() {
        setListContent(new ArrayList<T>());
    }

    public void addListContent(T item) {
        listContent.add(item);
    }

    public void setListContent(List<T> content) {
        listContent = content;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return listContent.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        observers.add(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        observers.remove(observer);
    }

    public void notifyDataSetChanged(){
        for (DataSetObserver observer: observers) {
            observer.onChanged();
        }
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return listContent.get(position).getView(parent.getContext(), convertView, parent);
    }

    public int getItemViewType(int pos) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isEnabled(int position) {
        return true;
    }

    public boolean areAllItemsEnabled() {
        return true;
    }
}


