package net.rimoto.intlphoneinput;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.TelephonyManager;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Locale;

public class IntlPhoneInput extends RelativeLayout {

    private final String DEFAULT_COUNTRY = Locale.getDefault().getCountry();
    private final static String DEFAULT_2ISO = "GB";
    private final static String DEFAULT_3ISO = "GBR";
    // UI Views
    private ImageView mCountryIv;
    private ImageView mSeparatorIv;
    private EditText mPhoneEdit;
    private View mHighlightBig;
    private View mHighlightSmall;

    private int mTextColor = -1;
    private boolean mIsHighlightEnabled = false;
    private String mOldIso = "";

    private String mPhonePattern = "";

    //Adapters
    private PhoneNumberWatcher mPhoneNumberWatcher = new PhoneNumberWatcher(DEFAULT_COUNTRY);

    //Util
    private PhoneNumberUtil mPhoneUtil = PhoneNumberUtil.getInstance();

    // Fields
    private Country mSelectedCountry;
    private CountriesFetcher.CountryList mCountries;
    private IntlPhoneInputListener mIntlPhoneInputListener;
    private OnClickListener mClickCallback;

    /**
     * Spinner listener
     */

    private OnCountrySelectListener mListener = new OnCountrySelectListener() {
        @Override
        public void onCountryChanged(Country country) {
            contryChanged(country);
        }
    };

    public void contryChanged(Country country) {
        mSelectedCountry = country;
        if (mPhoneNumberWatcher != null) {
            mPhoneEdit.removeTextChangedListener(mPhoneNumberWatcher);
        }
        mPhoneNumberWatcher = new PhoneNumberWatcher(mSelectedCountry.getIso());
        mPhoneEdit.addTextChangedListener(mPhoneNumberWatcher);
        mPhoneEdit.setText(mPhoneEdit.getText());
        mPhoneEdit.setSelection(mPhoneEdit.getText().length());
        setupView();
    }

    /**
     * Constructor
     *
     * @param context Context
     */
    public IntlPhoneInput(Context context) {
        super(context);
        init(null);
    }

    /**
     * Constructor
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    public IntlPhoneInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * Init after constructor
     */
    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.intl_phone_input, this);

        /**+
         * Country spinner
         */

        Communicator.getInstance().setChangeListener(mListener);

        mCountryIv = (ImageView) findViewById(R.id.intl_phone_edit__country);

        mCountryIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickCallback != null) {
                    mClickCallback.onClick(view);
                }

                PhonePrefixListPickerActivity.showListPicker(getContext());
            }
        });

        mCountries = CountriesFetcher.getCountries(getContext());
        setupCountryIvListener();

        mSeparatorIv = (ImageView) findViewById(R.id.intl_phone_edit__separator);

        mHighlightBig = findViewById(R.id.intl__highlight_big);
        mHighlightSmall = findViewById(R.id.intl__highlight_small);

        /**
         * Phone text field
         */
        mPhoneEdit = (EditText) findViewById(R.id.intl_phone_edit__phone);
        mPhoneEdit.addTextChangedListener(mPhoneNumberWatcher);

        setDefault();
        setEditTextDefaults(attrs);

        mSeparatorIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickCallback != null) {
                    mClickCallback.onClick(v);
                }

                PhonePrefixListPickerActivity.showListPicker(getContext());
            }
        });
    }

    private void setupCountryIvListener() {
        mCountryIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickCallback != null) {
                    mClickCallback.onClick(view);
                }

                PhonePrefixListPickerActivity.showListPicker(getContext());
            }
        });
    }

    private void setupView() {
        mCountryIv.setImageResource(mSelectedCountry.getFlagResource(getContext()));
        setHint();
    }

    private void setEditTextDefaults(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.IntlPhoneInput);

        int textSize = a.getDimensionPixelSize(R.styleable.IntlPhoneInput_android_textSize, (int) getResources().getDimension(R.dimen.text_size_default));
        textSize = a.getDimensionPixelSize(R.styleable.IntlPhoneInput_textSize, textSize);
        mPhoneEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);


        mTextColor = a.getColor(R.styleable.IntlPhoneInput_android_textColor, mPhoneEdit.getCurrentTextColor());
        mTextColor = a.getColor(R.styleable.IntlPhoneInput_textColor, mTextColor);
        mPhoneEdit.setTextColor(mTextColor);

        boolean isEnabled = a.getBoolean(R.styleable.IntlPhoneInput_android_enabled, true);
        setBlocked(!isEnabled);

        int colorHint = a.getColor(R.styleable.IntlPhoneInput_android_textColorHint, ContextCompat.getColor(getContext(), R.color.default_color));
        colorHint = a.getColor(R.styleable.IntlPhoneInput_textColorHint, colorHint);
        mPhoneEdit.setHintTextColor(colorHint);

        mPhoneEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (mIsHighlightEnabled) {
                    if (view.hasFocus()) {
                        mHighlightBig.setVisibility(VISIBLE);
                    } else {
                        mHighlightBig.setVisibility(INVISIBLE);
                    }
                }
            }
        });

        mIsHighlightEnabled = a.getBoolean(R.styleable.IntlPhoneInput_enableHighlight, false);
        enableHighlight(mIsHighlightEnabled);

        a.recycle();
    }

    /**
     * Hide keyboard from phoneEdit field
     */
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mPhoneEdit.getWindowToken(), 0);
    }

    /**
     * Set default value
     * Will try to retrieve phone number from device
     */
    public void setDefault() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
            String phone = telephonyManager.getLine1Number();
            if (phone != null && !phone.isEmpty()) {
                this.setNumber(phone);
            } else {
                String iso = telephonyManager.getNetworkCountryIso();
                setEmptyDefault(iso);
            }
        } catch (SecurityException e) {
            setEmptyDefault();
        }
    }

    public String getDefault2Iso() {
        return DEFAULT_2ISO;
    }

    public String getDefault3Iso() {
        return DEFAULT_3ISO;
    }

    /**
     * Set default value with
     *
     * @param iso ISO2 of country
     */
    public void setEmptyDefault(String iso) {
        if (iso == null || iso.isEmpty() || mCountries.indexOfIso(iso) == -1) {
            iso = DEFAULT_COUNTRY;
        }
        int defaultIdx = mCountries.indexOfIso(iso);
        if (defaultIdx == -1) {
            defaultIdx = mCountries.indexOfIso(DEFAULT_2ISO);
        }
        mSelectedCountry = mCountries.get(defaultIdx);
        setupView();
    }

    public Country getContryByPrefix(int prefix) {
        for (Country c : mCountries) {
            if (c.getDialCode() == prefix) {
                return c;
            }
        }
        return new Country("", "", 0);
    }

    /**
     * Alias for setting empty string of default settings from the device (using locale)
     */
    private void setEmptyDefault() {
        setEmptyDefault(null);
    }

    /**
     * Set hint number for country
     */
    private void setHint() {
        if (mSelectedCountry == null) {
            setEmptyDefault();
        }

        String iso = "GB";
        if (mSelectedCountry != null && mSelectedCountry.getIso() != null) {
            iso = mSelectedCountry.getIso();
        }

        if (mPhoneEdit != null) {
            Phonenumber.PhoneNumber phoneNumber = mPhoneUtil.getExampleNumberForType(iso, PhoneNumberUtil.PhoneNumberType.MOBILE);
            if (phoneNumber != null) {
                mPhonePattern = mPhoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            } else {
                mPhonePattern = "";
            }
        } else {
            mPhonePattern = "";
        }
        mPhoneEdit.setHint("Mobile Number");
    }

    /**
     * Get number
     *
     * @return Phone number in E.164 format | null on error
     */
    @SuppressWarnings("unused")
    public String getNumber() {
        Phonenumber.PhoneNumber phoneNumber = getPhoneNumber();

        if (phoneNumber == null) {
            return "";
        }

        return mPhoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
    }

    /**
     * Set Number
     *
     * @param number E.164 format or national format(for selected country)
     */
    public void setNumber(String number) {
        try {
            String iso = null;
            if (mSelectedCountry == null) {
                setEmptyDefault();
            }
            if (number == null) {
                number = "";
            }
            Phonenumber.PhoneNumber phoneNumber;

            if (mSelectedCountry != null && mSelectedCountry.getIso() != null) {
                iso = mSelectedCountry.getIso();
                phoneNumber = mPhoneUtil.parse(number, iso);
                mCountryIv.setImageResource(mSelectedCountry.getFlagResource(getContext()));
                setHint();
            } else {
                phoneNumber = mPhoneUtil.parse(number, "GB");
                mCountryIv.setImageResource(R.drawable.country_gb);
            }

            mPhoneEdit.setText(mPhoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
        } catch (NumberParseException ignored) {
        }
    }

    public String getHintNumber() {
        if (mPhonePattern == null) {
            setHint();
        }

        try {
            return mPhoneUtil.format(mPhoneUtil.parse(mPhonePattern, mSelectedCountry.getIso()), PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getText() {
        return getNumber();
    }

    /**
     * Get PhoneNumber object
     *
     * @return PhonenUmber | null on error
     */
    @SuppressWarnings("unused")
    public Phonenumber.PhoneNumber getPhoneNumber() {
        try {
            String iso = null;
            if (mSelectedCountry != null) {
                iso = mSelectedCountry.getIso();
            }
            return mPhoneUtil.parse(mPhoneEdit.getText().toString(), iso);
        } catch (NumberParseException ignored) {
            return null;
        }
    }

    /**
     * Get selected country
     *
     * @return Country
     */
    @SuppressWarnings("unused")
    public Country getSelectedCountry() {
        return mSelectedCountry;
    }

    /**
     * Check if number is valid
     *
     * @return boolean
     */
    @SuppressWarnings("unused")
    public boolean isValid() {
        Phonenumber.PhoneNumber phoneNumber = getPhoneNumber();
        return phoneNumber != null && mPhoneUtil.isValidNumber(phoneNumber);
    }

    /**
     * Add validation listener
     *
     * @param listener IntlPhoneInputListener
     */
    public void setOnValidityChange(IntlPhoneInputListener listener) {
        mIntlPhoneInputListener = listener;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mPhoneEdit.setEnabled(enabled);
        mCountryIv.setEnabled(enabled);
    }

    /**
     * Set keyboard done listener to detect when the user click "DONE" on his keyboard
     *
     * @param listener IntlPhoneInputListener
     */
    public void setOnKeyboardDone(final IntlPhoneInputListener listener) {
        mPhoneEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    listener.done(IntlPhoneInput.this, isValid());
                }
                return false;
            }
        });
    }

    public Typeface getTypeface() {
        return mPhoneEdit.getTypeface();
    }

    public void setTypeface(Typeface typeface) {
        mPhoneEdit.setTypeface(typeface);
    }

    public void setTextColor(int color) {
        mPhoneEdit.setTextColor(color);
    }

    public int getTextColor(int color) {
        return mPhoneEdit.getCurrentTextColor();
    }

    public float getTextSize() {
        return mPhoneEdit.getTextSize();
    }

    public void setTextSize(float textSize) {
        mPhoneEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        mPhoneEdit.addTextChangedListener(textWatcher);
    }

    public void removeTextChangedListener(TextWatcher textWatcher) {
        mPhoneEdit.removeTextChangedListener(textWatcher);
    }

    public void enableHighlight(boolean enableHighlight) {
        mIsHighlightEnabled = enableHighlight;
        if (enableHighlight) {
            mHighlightSmall.setVisibility(View.VISIBLE);
            mHighlightBig.setVisibility(View.INVISIBLE);
        } else {
            mHighlightSmall.setVisibility(View.GONE);
            mHighlightBig.setVisibility(View.GONE);
        }
    }

    public int getTextGravity() {
        return mPhoneEdit.getGravity();
    }

    public void setTextGravity(int textGravity) {
        mPhoneEdit.setGravity(textGravity);
    }

    public void setBlocked(boolean blocked) {
        if (blocked) {
            mCountryIv.setOnClickListener(null);
            mSeparatorIv.setVisibility(GONE);
            mPhoneEdit.setEnabled(false);
            mPhoneEdit.setTextColor(ContextCompat.getColor(getContext(), R.color.default_color));
        } else {
            setupCountryIvListener();
            mSeparatorIv.setVisibility(INVISIBLE);
            mPhoneEdit.setEnabled(true);
            if (mTextColor != -1) {
                mPhoneEdit.setTextColor(mTextColor);
            }
        }
    }

    public void setClickCallback(OnClickListener listener) {
        mClickCallback = listener;
    }

    /**
     * Simple validation listener
     */
    public interface IntlPhoneInputListener {
        void done(View view, boolean isValid);
    }

    /**
     * Phone number watcher
     */
    private class PhoneNumberWatcher extends PhoneNumberFormattingTextWatcher {
        private boolean lastValidity;

        @SuppressWarnings("unused")
        public PhoneNumberWatcher() {
            super();
        }

        //TODO solve it! support for android kitkat
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public PhoneNumberWatcher(String countryCode) {
            super(countryCode);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            super.onTextChanged(s, start, before, count);
            try {
                String iso = null;
                if (mSelectedCountry != null) {
                    iso = mSelectedCountry.getIso();
                }
                Phonenumber.PhoneNumber phoneNumber = mPhoneUtil.parse(s.toString(), iso);
                iso = mPhoneUtil.getRegionCodeForNumber(phoneNumber);
                if (iso != null) {
                    int countryIdx = mCountries.indexOfIso(iso);
                    if (mCountries.size() > countryIdx) {
                        mSelectedCountry = mCountries.get(countryIdx);
                    }
                    setupView();
                    if (!iso.equals(mOldIso)) {
                        mOldIso = iso;
                        mPhoneEdit.removeTextChangedListener(this);
                        contryChanged(mSelectedCountry);
                        mPhoneUtil.parse(s.toString(), iso);
                    }
                    mOldIso = iso;
                }
            } catch (NumberParseException ignored) {
                ignored.printStackTrace();
            }

            if (mIntlPhoneInputListener != null) {
                boolean validity = isValid();
                if (validity != lastValidity) {
                    mIntlPhoneInputListener.done(IntlPhoneInput.this, validity);
                }
                lastValidity = validity;
            }
        }
    }
}
