package com.example.android.quitsmoking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.quitsmoking.dao.SettingDAOImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by sam on 2017-10-29.
 */

public class SettingsActivity extends Activity {
    private SettingDAOImpl mydb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_settings);

        Button b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final EditText initial_cig_count_obj = (EditText) findViewById(R.id.editText2);
                final EditText days_frame_obj = (EditText) findViewById(R.id.editText3);
                final EditText cig_price_obj = (EditText) findViewById(R.id.editText);


                if (checkFields(
                        new ArrayList<EditText>() {{
                            add(initial_cig_count_obj);
                            add(days_frame_obj);
                            add(cig_price_obj);
                        }}
                )) {
                    mydb = new SettingDAOImpl(v.getContext());
                    int initial_cig_count = Integer.parseInt(initial_cig_count_obj.getText().toString());
                    int days_frame = Integer.parseInt(days_frame_obj.getText().toString());

                    mydb.insertSetting(
                            initial_cig_count,
                            days_frame,
                            new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                            initial_cig_count,
                            Double.parseDouble(cig_price_obj.getText().toString()),
                            subtract_cigarette,                 //ADD SUBTRACT CIGARETTE!!
                            false

                    );

                    Intent intentStartPage = new Intent(v.getContext(), StartPageActivity.class);
                    v.getContext().startActivity(intentStartPage);
                }

            }
        });
    }

    private boolean checkFields(ArrayList<EditText> fields) {
        boolean result = true;

        for (EditText field : fields) {
            String s = field.getText().toString();

            if (Objects.equals(s, "")) {
                result = false;
                field.setError("Field should be filled");

            } else if (Double.parseDouble(s) == 0) {
                result = false;
                field.setError("Field should be equal to 0");
            }
        }

        return result;
    }

}