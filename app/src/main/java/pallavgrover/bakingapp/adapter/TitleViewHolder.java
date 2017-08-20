package pallavgrover.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pallavgrover.bakingapp.R;


public class TitleViewHolder extends RecyclerView.ViewHolder  {

    TextView titleView;

    public TitleViewHolder(View v) {
        super(v);
        titleView = (TextView) v.findViewById(R.id.title);
    }
    public void bindView(String title) {
        titleView.setText(title);
    }
}
