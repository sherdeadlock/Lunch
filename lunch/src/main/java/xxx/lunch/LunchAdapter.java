package xxx.lunch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.List;

public class LunchAdapter extends ArrayAdapter<Lunch> {
    private final LayoutInflater inflater;

    public LunchAdapter(Context context, List<Lunch> lunches) {
        super(context, R.layout.lunch, lunches);
        inflater = LayoutInflater.from(context) ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Lunch lunch = getItem(position);
        final CheckedTextView lunchName;

        if (convertView == null) {
            // Create a new row view
            convertView = inflater.inflate(R.layout.lunch, parent, false);

            lunchName = (CheckedTextView) convertView.findViewById( R.id.lunch_name);
            // Optimization: Tag the row with it's child views, so we don't have to
            // call findViewById() later when we reuse the row.
            convertView.setTag(new LunchView(lunchName));
        } else {
            // Reuse existing row view
            // Because we use a ViewHolder, we avoid having to call findViewById().
            LunchView v = (LunchView) convertView.getTag();
            lunchName = v.lunchName;
        }

        lunchName.setText(lunch.name);

        return convertView;
    }

    public List<Lunch> getCheckedLunches() {
        List<Lunch> ret = new ArrayList<Lunch>();
        int count = getCount();
        for (int i = 0; i < count; i++) {
            Lunch lunch = getItem(i);
            if (lunch.isChecked())
                ret.add(lunch);
        }
        return ret;
    }

    public String[] getCheckedString() {
        List<String> ret = new ArrayList<String>();
        int count = getCount();
        for (int i = 0; i < count; i++) {
            Lunch lunch = getItem(i);
            if (lunch.isChecked())
                ret.add(lunch.name);
        }
        return ret.toArray(new String[ret.size()]);
    }

    public static class LunchView {
        public final CheckedTextView lunchName;

        LunchView(CheckedTextView lunchName) {
            this.lunchName = lunchName;
        }
    }
}
