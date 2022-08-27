package com.venpillar.testprogrammer.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentActivity;

public class Helper {

    public static void initDateRange(Context context, EditText start, EditText end, Date startDate){
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        final Calendar c = Calendar.getInstance();

        if(startDate != null){
            start.setText( sdf.format(c.getTime()) );
            c.add(Calendar.DATE, 1);
            end.setText( sdf.format(c.getTime()) );
        }

        final MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

        start.setOnClickListener(v -> {
            picker.show( ( (FragmentActivity)context).getSupportFragmentManager(), "Tanggal");
        });

        picker.addOnPositiveButtonClickListener(selection -> {
            Long sDateL = selection.first;
            Long eDateL = selection.second;

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(sDateL);

            start.setText( sdf.format( calendar.getTime() ) );

            Calendar calendar1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar1.setTimeInMillis(eDateL);

            end.setText( sdf.format( calendar1.getTime() ) );

        });

    }

    public static void initDate(Context context, EditText dateEditText, Date defaultDate){
        final Calendar c = Calendar.getInstance();

        if(defaultDate != null){
            c.setTime(defaultDate);
        }

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        String date = sdf.format(c.getTime());
        dateEditText.setText(date);

        DatePickerDialog.OnDateSetListener onDateSetListener = (view12, year, monthOfYear, dayOfMonth) -> {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateEditText.setText(sdf.format(c.getTime()));
        };

        dateEditText.setOnClickListener(view1 -> {

            DatePickerDialog dialog = new DatePickerDialog(
                    context,
                    onDateSetListener,
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
            );
//            dialog.getDatePicker().setSpinnersShown(true);
//            dialog.getDatePicker().setCalendarViewShown(false);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
    }
}
