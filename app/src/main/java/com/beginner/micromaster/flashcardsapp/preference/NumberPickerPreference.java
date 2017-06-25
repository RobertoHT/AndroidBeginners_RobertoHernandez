package com.beginner.micromaster.flashcardsapp.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import com.beginner.micromaster.flashcardsapp.R;

/**
 * Created by praxis on 04/05/17.
 */

public class NumberPickerPreference extends DialogPreference {
    private NumberPicker picker;
    private Integer value;
    private static final Integer DEFAULT_VALUE = 10;

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setDialogLayoutResource(R.layout.numberpicker_dialog);
        setPositiveButtonText(context.getString(R.string.button_ok));
        setNegativeButtonText(context.getString(R.string.button_cancel));
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        picker = (NumberPicker) view.findViewById(R.id.numberPicker);
        picker.setMinValue(1);
        picker.setMaxValue(24);

        if (value != null){
            picker.setValue(value);
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            value = picker.getValue();
            persistInt(value);
        }
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            value = getPersistedInt(DEFAULT_VALUE);
        }
        else {
            value = (int) defaultValue;
            persistInt(value);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInteger(index, DEFAULT_VALUE);
    }
}
