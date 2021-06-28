package com.SimpleSQL;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText currentDate, name, subject,notes, dateDue, timeDue;
    TextView output;
    ImageButton save, datePicker, timePicker, del, edit, view;
    DatePickerDialog datepicker;
    TimePickerDialog timepicker;
    private SimpleDateFormat dateFormat;
    private String date;
    DataBaseHelper DataBaseHelper;

    public void inputs(){
        name = findViewById(R.id.name);
        subject = findViewById(R.id.subject);
        notes = findViewById(R.id.notes);
        dateDue = findViewById(R.id.dateDue);
        timeDue = findViewById(R.id.timeDue);
        output = findViewById(R.id.output);
    }

    public void buttons(){
        view = findViewById(R.id.view);
        edit = findViewById(R.id.edit);
        save = findViewById(R.id.save);
        del = findViewById(R.id.del);
        currentDate = findViewById(R.id.currentDate);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper = new DataBaseHelper(this);
        inputs();
        buttons();
        datePicker.setOnClickListener(view -> {
            final Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int mon = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            datepicker = new DatePickerDialog(MainActivity.this,
                    (datePicker, yyyy, mmm, dd) -> dateDue.setText(dd + "/" + mmm + "/" + yyyy), year, mon, day);
            datepicker.show();
        });
        timePicker.setOnClickListener(view -> {
            final Calendar calDate = Calendar.getInstance();
            int hour = calDate.get(Calendar.HOUR_OF_DAY);
            int min = calDate.get(Calendar.MINUTE);

            timepicker = new TimePickerDialog(MainActivity.this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            timeDue.setText(i + ":" + i1);
                        }
                    }, hour, min, true);
            timepicker.show();

        });
        output.setMovementMethod(new ScrollingMovementMethod());

        save.setOnClickListener(view -> {
            dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss aaa", Locale.US);
            date = dateFormat.format(Calendar.getInstance().getTime());
            currentDate.setText(date);

            DataBaseHelper.addData(
                    currentDate.getText().toString(),
                    name.getText().toString(),
                    subject.getText().toString(),
                    dateDue.getText().toString(),
                    timeDue.getText().toString(),
                    notes.getText().toString()
            );
            currentDate.setText("");
            name.setText("");
            subject.setText("");
            dateDue.setText("");
            timeDue.setText("");
            notes.setText("");
        });
        del.setOnClickListener(view -> {
            DataBaseHelper.delData(name.getText().toString());
            output = findViewById(R.id.output);
            Cursor R = DataBaseHelper.viewAll();
            StringBuffer buff = new StringBuffer();
            while(R.moveToNext()){
                //buff.append("ID :"+ R.getString(0)+"\n");
                buff.append("DATE :"+ R.getString(1)+"\n");
                buff.append("NAME :"+ R.getString(2)+"\n");
                buff.append("SUBJ :"+ R.getString(3)+"\n");
                buff.append("DATEDUE :"+ R.getString(4)+"\n");
                buff.append("TIME :"+ R.getString(5)+"\n");
                buff.append("NOTES :"+ R.getString(6)+"\n");
            }
            output.setText(buff);
            output.setText("");
            if (R.getCount()==0){ output.setText("No Values"); }
        });
        edit.setOnClickListener(view -> {
            DataBaseHelper.update(
                    currentDate.getText().toString(),
                    name.getText().toString(),
                    subject.getText().toString(),
                    dateDue.getText().toString(),
                    timeDue.getText().toString(),
                    notes.getText().toString()
            );
            currentDate.setText("");
            name.setText("");
            subject.setText("");
            dateDue.setText("");
            timeDue.setText("");
            notes.setText("");
        });
        view.setOnClickListener(view -> {
            Cursor R = DataBaseHelper.viewAll();
            if (R.getCount()==0){output.setText("No Values");}
            StringBuffer buff = new StringBuffer();
            // By editing the UNICODES between the string we can adjust the layout, thus the output
            // layout can be edited and arranged in the display.
            while(R.moveToNext()){
            /*buff.append("ID :"+ R.getString(0)+"\u0020");
            buff.append("NAME :"+ R.getString(1)+"\n");
            buff.append("ADDRESS :"+ R.getString(2)+"\n");
            buff.append("DATE :"+ R.getString(3)+"\n");*/
                buff.append("\n"+"ID :"+R.getString(0)+
                        "\n"+"NAME :"+ R.getString(2)+
                        "\n"+"DATE :"+ R.getString(1)+
                        "\n"+"DATE DUE :"+ R.getString(4)+R.getString(5)+
                        "\n"+"SUBJECT"+"\u0020"+R.getString(3)+
                        "\n"+"NOTES"+
                        "\n"+R.getString(6)+
                        "\n"+"END"
                );
            }
            output.setText(buff);
        });
    }


}