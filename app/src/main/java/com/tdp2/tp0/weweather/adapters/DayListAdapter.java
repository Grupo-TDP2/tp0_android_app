package com.tdp2.tp0.weweather.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tdp2.tp0.weweather.viewModel.DayTemperatureViewModel;

import java.util.List;

public class DayListAdapter extends ArrayAdapter<DayTemperatureViewModel>
{
    private int resource;
    private boolean showTemperatures = true;

    public DayListAdapter(@NonNull Context context, int resource, @NonNull List<DayTemperatureViewModel> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    public void changeDisplayTemperature(boolean display)
    {
        if( showTemperatures != display )
        {
            showTemperatures = display;
            notifyDataSetChanged();
        }
    }

    public View getView (int position,
                         View convertView,
                         ViewGroup parent)
    {
        if( convertView == null )
        {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            DayTemperatureViewHolder viewHolder = new DayTemperatureViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        DayTemperatureViewModel model = getItem(position);
        DayTemperatureViewHolder viewHolder = (DayTemperatureViewHolder)convertView.getTag();
        viewHolder.setDayName(position, model.getDate());
        viewHolder.setDayIcon(getContext(), model.getDayIcon());
        viewHolder.setDayTemperatue(model.getDayTemperature());
        viewHolder.setNightIcon(getContext(),model.getNightIcon());
        viewHolder.setNightTemperature(model.getNightTemperature());
        viewHolder.displayTemperatureBlock(showTemperatures);
        return convertView;
    }
}
