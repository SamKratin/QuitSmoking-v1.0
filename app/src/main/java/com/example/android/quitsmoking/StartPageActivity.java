package com.example.android.quitsmoking;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.android.quitsmoking.dao.SettingDAOImpl;
import com.example.android.quitsmoking.models.SettingEntity;
import com.example.android.quitsmoking.utils.CustomAlerts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class StartPageActivity extends FragmentActivity {
    private SettingEntity settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);


        SettingDAOImpl mydb = new SettingDAOImpl(this);
        int settingsRows = mydb.numberOfRows();

        if (settingsRows == 0) {
            Intent intentSettings = new Intent(StartPageActivity.this, SettingsActivity.class);
            StartPageActivity.this.startActivity(intentSettings);
        } else {

            try {
                settings = mydb.getData();
                CalendarView calendar = (CalendarView) findViewById(R.id.calendarView); // implementing calendar

                calendar.setFirstDayOfWeek(2); // adjusts first day of the week (Monday)

                calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        month++; // receive real number of month

                        String selectedDateString = String.valueOf(year) + String.valueOf(month) + String.valueOf(dayOfMonth);
                        SimpleDateFormat newFormat = new SimpleDateFormat("yyyyMMdd");
                        Button counterButton = findViewById(R.id.button);
                        //
                        CustomAlerts customAlerts = new CustomAlerts(getApplicationContext());

                        counterButton.setVisibility(View.INVISIBLE);

                        try {
                            Date selectedDate = newFormat.parse(selectedDateString);
                            boolean isCorrect = checkDate(selectedDate);

                            if (isCorrect) {
                                int dailyCigaretteAmount = getDailyCigaretteAmount(selectedDate);

                                if (dailyCigaretteAmount > 0) {
                                    counterButton.setText(String.valueOf(dailyCigaretteAmount));
                                    counterButton.setVisibility(View.VISIBLE);

                                    toggleCounterButton(selectedDate, counterButton);
                                } else {
                                    customAlerts.showMessage("Congratulations!");
                                }

                            } else {
                                customAlerts.showMessage("Date out of your challenge frame");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Button button = findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                   // settings.
                    }
                });

            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
    }

    private void toggleCounterButton(Date selectedDate, Button counterButton) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();

        counterButton.setClickable(Objects.equals(dateFormat.format(date), dateFormat.format(selectedDate)));
    }

    private boolean checkDate(Date selectedDate) {
        Date startDate = settings.getStart_date();
        Calendar c = Calendar.getInstance();

        c.setTime(startDate);
        c.add(Calendar.DATE, settings.getDays_frame());

        Date endDate = c.getTime();

        return (!(selectedDate.before(startDate) || selectedDate.after(endDate)));
    }

    private int getDailyCigaretteAmount(Date selectedDate) {
        int initialCigaretteAmount = settings.getInitial_cig_count();
        int timeFrame = settings.getDays_frame();
        Date initialDate = settings.getStart_date();
        float decrementPeriod = timeFrame / initialCigaretteAmount;
        long diff = selectedDate.getTime() - initialDate.getTime();
        float days = (diff / (1000 * 60 * 60 * 24)); // get days
        int enteredPeriod = (int) Math.ceil(days / decrementPeriod); //round to grace

        return initialCigaretteAmount - enteredPeriod;
    }
}