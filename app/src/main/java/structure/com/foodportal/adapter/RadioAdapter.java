package structure.com.foodportal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import structure.com.foodportal.R;

public abstract class RadioAdapter<T> extends RecyclerView.Adapter<RadioAdapter.ViewHolder> {
    private int mSelectedItem = -1;
    private List<T> mItems;
    private Context mContext;

    public RadioAdapter(Context context, List<T> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public void onBindViewHolder(RadioAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.mRadio.setChecked(i == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.layout_radio_option, viewGroup, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton mRadio;
        TextView mText;

        public ViewHolder(final View inflate) {
            super(inflate);
            mText = (TextView) inflate.findViewById(R.id.text);
            mRadio = (RadioButton) inflate.findViewById(R.id.radio);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            };
            itemView.setOnClickListener(clickListener);
            mRadio.setOnClickListener(clickListener);
        }
    }
}
