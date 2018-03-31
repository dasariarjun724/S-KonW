package com.transform.act.s_konw;


import android.content.Context;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        EditText u = (EditText) findViewById(R.id.editText);
        u.setText("SIETK");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void signIn(View view) {
        EditText u = (EditText) findViewById(R.id.editText);
        EditText p = (EditText) findViewById(R.id.editText1);
        TextView m = (TextView) findViewById(R.id.textView2);
        String user = u.getText().toString();
        String pass = p.getText().toString();
        m.setText("");
        SharedPreferences sp = MainActivity.this.getSharedPreferences(getString(R.string.pass), MODE_PRIVATE);
        String rpass = sp.getString(getString(R.string.pass), "cse2013*");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(p.getWindowToken(), 0);
        if (user.equals("") && pass.equals("")) {
            Toast.makeText(getBaseContext(), "Enter username and password!", Toast.LENGTH_LONG).show();
        } else if (user.length() > 0 && pass.equals("")) {
            Toast.makeText(getBaseContext(), "Enter password!", Toast.LENGTH_LONG).show();
        } else if ((user.equals("SIETK") || user.equals("sietk")) && pass.equals(rpass)) {
            u.setText("");
            p.setText("");
            m.setText("");
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        } else {
            m.setText("Wrong password try again!");
        }
    }

    public void change(View view) {
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }

    public void keyHide(View view) {
        TextView tv = (TextView) findViewById(R.id.textView);
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv.getWindowToken(),0);
    }

    public void credits(View view) {
        ImageView image = new ImageView(this);
        image.setImageResource(R.mipmap.ic_launcher);
        String msg = "Idea & Guidance by\n"+
                "Hari Prasad G.\n"+
                "Dept.of CSE\n"+
                "Asst.Professor\n"+
                "Dev @ARJUN REDDY.D\n" +
                "SIETK-CSE\n"+
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
