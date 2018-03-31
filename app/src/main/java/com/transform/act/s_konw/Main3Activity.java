package com.transform.act.s_konw;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void keyHide(View view) {
        TextView tv = (TextView) findViewById(R.id.textView);
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv.getWindowToken(),0);
    }

    public void cpass(View view) {
        EditText eop = (EditText) findViewById(R.id.editText);
        EditText enp = (EditText) findViewById(R.id.editText1);
        EditText enp1 = (EditText) findViewById(R.id.editText2);
        String op, np, np1, p1;
        op = eop.getText().toString();
        np = enp.getText().toString();
        np1 = enp1.getText().toString();
        SharedPreferences sp = Main3Activity.this.getSharedPreferences(getString(R.string.pass), MODE_PRIVATE);
        p1 = sp.getString(getString(R.string.pass), "cse2013*");
        if (op.equals(p1)) {
            if (np.equals(np1)) {

                SharedPreferences.Editor editor = sp.edit();
                editor.putString(getString(R.string.pass), np1);
                editor.commit();
                Toast.makeText(getBaseContext(), "Password changed!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getBaseContext(), "New passwords doesn't match!", Toast.LENGTH_LONG).show();
                enp.setText("");
                enp1.setText("");
            }
        } else {
            Toast.makeText(getBaseContext(), "Incorrect password!", Toast.LENGTH_LONG).show();
            super.onBackPressed();
        }
    }

    public void credits(View view) {
        ImageView image = new ImageView(this);
        image.setImageResource(R.mipmap.ic_launcher);
        String msg = "Idea & Guidance by\n"+
                "Hari Prasad G.\n"+
                "Asst.Professor\n"+
                "Dept.of CSE\n"+
                "Dev @ARJUN REDDY.D\n" +
                "SIETK-CSE\n" +
                "Co-Dev @P.CHANDRASEKHAR\n" +
                "SIETK-CSE\n" +
                "Co-Dev @R.TEENU KRISHNA MOHAN\n" +
                "SIETK-CSE\n" +
                "Co-Dev @B.UDAY KUMAR REDDY\n" +
                "SIETK-CSE\n\n"+
                "Support by:\n" +
                "DEPT.of CSE@SIETK\n" +
                "K.JAGAPATHI BABU, SIETK-CSE\n" +
                "K.JAYASRI, SIETK-CSE\n" +
                "S.MANIKANTA, SIETK-CSE\n" +
                "T.ANIL KUMAR, SIETK-CSE\n\n" +
                "Feedback & Suggestions\n@\n" +
                "dev.sgicse@gmail.com\n";
        onCreateDialog(msg);

    }

    public void onCreateDialog(String msg) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.alert, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();
        TextView tt = (TextView) promptView.findViewById(R.id.title);
        TextView t = (TextView) promptView.findViewById(R.id.message);
        t.setText(msg);
        Button btnAdd1 = (Button) promptView.findViewById(R.id.next_button);

        btnAdd1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                alertD.dismiss();

            }
        });
        alertD.setIcon(R.drawable.back);
        alertD.setView(promptView);

        alertD.show();

    }
}


