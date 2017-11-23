package com.example.android.quitsmoking.models;

import java.util.Date;

/**
 * Class contains all getter and setter methods.
 * Created by sam on 2017-10-30.
 */

public class SettingEntity extends CommonEntity {
    private int initial_cig_count;
    private int days_frame;
    private Date start_date;
    private int daily_cig_count;
    private double cig_price;
    private boolean fine;
    private int subtract_cigarette;

    public SettingEntity(int id, int initial_cig_count, int days_frame, Date start_date,
                         int daily_cig_count, double cig_price, boolean fine, int subtract_cigarette) {

        super.id = id;
        this.initial_cig_count = initial_cig_count;
        this.days_frame = days_frame;
        this.start_date = start_date;
        this.daily_cig_count = daily_cig_count;
        this.cig_price = cig_price;
        this.fine = fine;
        this.subtract_cigarette = subtract_cigarette;
    }

    public int getInitial_cig_count() {
        return initial_cig_count;
    }

    public void setInitial_cig_count(int initial_cig_count) {
        this.initial_cig_count = initial_cig_count;
    }

    public int getDays_frame() {
        return days_frame;
    }

    public void setDays_frame(int days_frame) {
        this.days_frame = days_frame;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public int getDaily_cig_count() {
        return daily_cig_count;
    }

    public void setDaily_cig_count(int daily_cig_count) {
        this.daily_cig_count = daily_cig_count;
    }

    public double getCig_price() {
        return cig_price;
    }

    public void setCig_price(double cig_price) {
        this.cig_price = cig_price;
    }

    public boolean isFine() {
        return fine;
    }

    public void setFine(boolean fine) {
        this.fine = fine;
    }

    public int getSubtract_cigarette() {
        return subtract_cigarette;
    }

    public void setSubtract_cigarette(int subtract_cigarette) {
        this.subtract_cigarette = subtract_cigarette;
    }
}
