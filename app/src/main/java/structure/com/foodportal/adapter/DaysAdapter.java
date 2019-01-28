package structure.com.foodportal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.models.WeekDay;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.Holder> {
    private MainActivity mainActivity;
    private ArrayList<WeekDay> days;

    public DaysAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.days = new ArrayList<>();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dayview, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.checkBox.setText(days.get(position).getName());
        if (days.get(position).isChecked()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (position == 0) {
                if (isChecked) {
                    for (int pos = 0; pos < days.size(); pos++) {
                        days.get(pos).setChecked(true);
                    }
                } else {
                    for (int pos = 0; pos < days.size(); pos++) {
                        days.get(pos).setChecked(false);
                    }
                }
                notifyDataSetChanged();
            } else {
                if (isChecked) {
                    days.get(position).setChecked(true);
                } else {
                    days.get(position).setChecked(false);
                    if (days.get(0).isChecked()) {
                        days.get(0).setChecked(false);
                        notifyItemChanged(0);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.days.size();
    }

    public void addAll(ArrayList<WeekDay> days) {
        this.days.clear();
        this.days.addAll(days);
    }

    public ArrayList<WeekDay> getDays() {
        return days;
    }

    static class Holder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        Holder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkbox);
        }
    }
}
