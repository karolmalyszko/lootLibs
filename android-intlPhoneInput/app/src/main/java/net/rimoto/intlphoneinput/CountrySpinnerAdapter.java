package net.rimoto.intlphoneinput;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CountrySpinnerAdapter extends ArrayAdapter<Country> {
    private LayoutInflater mLayoutInflater;

    /**
     * Constructor
     *
     * @param context Context
     */
    public CountrySpinnerAdapter(Context context) {
        super(context, 0);
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * Drop down selected view
     *
     * @param position    position of selected item
     * @param convertView View of selected item
     * @param parent      parent of selected view
     * @return convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_country, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.intl_phone_edit__country__item_image);
            viewHolder.mNameView = (TextView) convertView.findViewById(R.id.intl_phone_edit__country__item_name);
            viewHolder.mDialCode = (TextView) convertView.findViewById(R.id.intl_phone_edit__country__item_dialcode);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Country country = getItem(position);
        viewHolder.mImageView.setImageResource(country.getFlagResource(getContext()));
        viewHolder.mNameView.setText(country.getName());
        viewHolder.mDialCode.setText(String.format("+%s", country.getDialCode()));
        return convertView;
    }



    /**
     * View holder for caching
     */
    private static class ViewHolder {
        public ImageView mImageView;
        public TextView mNameView;
        public TextView mDialCode;
    }
}
