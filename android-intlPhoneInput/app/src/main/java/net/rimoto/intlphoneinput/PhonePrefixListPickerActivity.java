package net.rimoto.intlphoneinput;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.ButterKnife;

import android.support.v7.app.AppCompatActivity;



public class PhonePrefixListPickerActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private ListView mPrefixListView;
    private SearchView mSearchView;
    private ArrayList<Country> mAllCountriesPhone;
    private CountrySpinnerAdapter mPhonePrefixAdapter;

    private ImageView mCloseIv;

    public static void showListPicker(Context context) {
        Intent intent = new Intent(context, net.rimoto.intlphoneinput.PhonePrefixListPickerActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_prefix_list_picker);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.blank_activity_animation,R.anim.blank_activity_animation);
        initDialogElements();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.blank_activity_animation,R.anim.blank_activity_animation);
    }

    private void initDialogElements() {
        mPrefixListView = ButterKnife.findById(this, R.id.phone_prefix_list_picker_rv);
        mSearchView = ButterKnife.findById(this, R.id.phone_prefix_list_picker_search);
        mCloseIv = ButterKnife.findById(this, R.id.phone_prefix_list_picker_close_iv);
        mCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setIconified(false);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnCloseListener(this);
        mSearchView.setScrollContainer(false);
        View searchplate = (View) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchplate.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        //noinspection RestrictedApi
        mSearchView.clearFocus();
        RelativeLayout rootLayout = ButterKnife.findById(this, R.id.phone_prefix_list_picker_root_rl);
        rootLayout.requestFocus();
        try {
            Field mDrawable = SearchView.class.getDeclaredField("mSearchHintIcon");
            mDrawable.setAccessible(true);
            Drawable drawable = (Drawable) mDrawable.get(mSearchView);
            drawable.setAlpha(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            EditText editText = ((EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
            editText.setHintTextColor(Color.LTGRAY);
            editText.setHint("Search");
            editText.setTypeface(null, Typeface.BOLD);
            editText.setTextSize(30);
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(editText, R.drawable.cursor);
        } catch (Exception e) {
        }
        ;


        mAllCountriesPhone = CountriesFetcher.getCountries(this);
        mPhonePrefixAdapter = new CountrySpinnerAdapter(this);
        mPhonePrefixAdapter.addAll(mAllCountriesPhone);
        mPrefixListView.setAdapter(mPhonePrefixAdapter);
        mPrefixListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Communicator.getInstance().setCountry(mPhonePrefixAdapter.getItem(position));
                    finish();
                } catch (Exception e) {}
            }
        });

        SearchView.SearchAutoComplete textArea = (SearchView.SearchAutoComplete) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textArea.setTextSize(24f);
        Typeface font = Typeface.createFromAsset(getAssets(), "Lato-Bold.ttf");
        textArea.setTypeface(font);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Country> result = new ArrayList<>();
        for (Country cp : mAllCountriesPhone) {
            if (cp.getName().toLowerCase().contains(newText.toLowerCase()) || ("+" + cp.getDialCode()).toLowerCase().contains(newText.toLowerCase())) {
                result.add(cp);
            }
        }
        mPhonePrefixAdapter.clear();
        mPhonePrefixAdapter.addAll(result);
        mPrefixListView.setAdapter(mPhonePrefixAdapter);
        mPhonePrefixAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onClose() {
        finish();
        return true;
    }

}
