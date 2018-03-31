package com.transform.act.s_konw;

import android.content.Context;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
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



public class Main2Activity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView tv= (TextView) findViewById(R.id.textView);
        tv.setPaintFlags(tv.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Boolean isfirstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isfirstrun", true);
        if (isfirstrun) {
            alert a=new alert();
            a.execute();
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isfirstrun", false).commit();
        }
    }

    public void Enquery(View view) {
        EditText r, n, b;
        String r1 = "", n1 = "", b1 = "";
        TextView msg = (TextView) findViewById(R.id.textView4);
        msg.setText("");
        r = (EditText) findViewById(R.id.editText);
        n = (EditText) findViewById(R.id.editText2);
        b = (EditText) findViewById(R.id.editText3);
        r1 = r.getText().toString();
        n1 = n.getText().toString();
        b1 = b.getText().toString();
        SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
        Cursor cs;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //one
        if (r1.length() == 0 && n1.length() == 0 && b1.length() == 0) {
            Toast.makeText(getBaseContext(),"All the fields are empty",Toast.LENGTH_LONG).show();
        }
        //two
        else if (b1.length() > 0 && r1.length() == 0 && n1.length() == 0) {
            int bus;
            bus= Integer.parseInt(b1);
            cs = db.rawQuery("SELECT * FROM BUS WHERE bus_no LIKE '" + bus + "'", null);
            Cursor cs1=db.rawQuery("SELECT * FROM BUS_I WHERE bus_no LIKE '" + bus + "'", null);
            if (cs.getCount() > 0) {
                display(cs, cs1, true);
            } else {
                msg.setText("There is no bus of such number!\nBus number should be less than 24....");

            }
        }
        //three
        else if (r1.length() > 0 && n1.length() == 0 && b1.length() == 0) {
            if (r1.length() > 10) {
                Toast.makeText(getBaseContext(), "Roll NO should be 10 characters!", Toast.LENGTH_LONG).show();
            } else if ((r1.length() == 1 || r1.length() == 2)) {
                cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '________%" + r1 + "%'", null);
                if (cs.getCount() > 0) {
                    display(cs, null, false);
                } else {
                    msg.setText("Roll number doesn't match any student record!");

                }
            } else if (r1.length() == 3) {
                cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '_______" + r1 + "'", null);
                if (cs.getCount() > 0) {
                    display(cs, null, false);
                } else {
                    msg.setText("Roll number doesn't match any student record!");

                }
            } else if (r1.length() ==10) {
                int check= Integer.parseInt(r1.substring(0,2));
                if(check>11) {
                    cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '%" + r1 + "%'", null);
                    if (cs.getCount() > 0) {
                        display(cs, null, false);
                    } else {
                        msg.setText("Roll number doesn't match any student record!");

                    }
                }
                else {
                    msg.setText("Enter first two characters between 12-16");
                }
            }
            else if(r1.length() ==7) {
                cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '%" + r1 + "%'", null);
                if (cs.getCount() > 0) {
                    display(cs, null, false);
                } else {
                    msg.setText("Roll number doesn't match any student record!");

                }
            }
            else if(r1.length()>3){
                msg.setText("Enter valid Roll number!");
            }
        }
        //four
        else if (r1.length() == 0 && n1.length() > 0 && b1.length() == 0) {
            cs = db.rawQuery("SELECT * FROM BUS WHERE name LIKE '%" + n1 + "%'", null);
            if (cs.getCount() > 0) {
                display(cs, null, false);
            } else {
                msg.setText("No student found with this name!");

            }
        }
        //five
        else if (r1.length() > 0 && n1.length() > 0 && b1.length() == 0) {
            if (r1.length() > 10) {
                Toast.makeText(getBaseContext(), "Roll NO should be 10 characters!", Toast.LENGTH_LONG).show();
            } else if ((r1.length() == 1 || r1.length() == 2)) {
                cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '________%" + r1 + "%' AND name LIKE '%" + n1 + "%'", null);
                if (cs.getCount() > 0) {
                    display(cs, null, false);
                } else {
                    msg.setText("No student found with given Roll number & Name!");
                }
            }
            else if (r1.length() ==10) {
                int check= Integer.parseInt(r1.substring(0,2));
                if(check>11) {
                    cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '%" + r1 + "%' AND name LIKE '%" + n1 + "%'", null);
                    if (cs.getCount() > 0) {
                        display(cs, null, false);
                    } else {
                        msg.setText("Roll number doesn't match any student record!");

                    }
                }
                else {
                    msg.setText("Enter first two characters between 12-16");
                }
            } else if(r1.length() ==7){
                cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '%" + r1 + "%' AND name LIKE '%" + n1 + "%'", null);
                if (cs.getCount() > 0) {
                    display(cs, null, false);
                } else {
                    msg.setText("Roll number doesn't match any student record!");

                }
            }
            else if (r1.length() == 3) {
                cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '_______" + r1 + "' AND name LIKE '%" + n1 + "%'", null);
                if (cs.getCount() > 0) {
                    display(cs, null, false);
                } else {
                    msg.setText("No student found with given Roll number & Name!");

                }
            }
            else if (r1.length() > 3) {
                msg.setText("Enter valid Roll number!");
            }
        }
        //six
        else if (r1.length() == 0 && n1.length() > 0 && b1.length() > 0) {
            int bus;
            bus= Integer.parseInt(b1);
            cs = db.rawQuery("SELECT * FROM BUS WHERE name LIKE '%" + n1 + "%' AND bus_no LIKE '" + bus + "'", null);
            if (cs.getCount() > 0) {
                display(cs, null, false);
            } else {
                msg.setText("No student found with given Name & Bus number!");

            }
        }
        //seven
        else if (r1.length() > 0 && n1.length() == 0 && b1.length() > 0) {
            int bus;
            bus= Integer.parseInt(b1);
            if (r1.length() > 10) {
                msg.setText("Roll NO should be 10 characters!");
            } else if ((r1.length() == 1 || r1.length() == 2)) {
                cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '_______%" + r1 + "%' AND bus_no LIKE '" + bus + "'", null);
                if (cs.getCount() > 0) {
                    display(cs, null, false);
                } else {
                    msg.setText("No student found with given Roll number & Bus number!");

                }
            } else if (r1.length() == 3) {
                cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '_______" + r1 + "' AND bus_no LIKE '" + bus+ "'", null);
                if (cs.getCount() > 0) {
                    display(cs, null, false);
                } else {
                    msg.setText("No student found with given Roll number & Bus number!");

                }
            } else if (r1.length()== 10) {
                int check= Integer.parseInt(r1.substring(0,2));
                if(check>11) {
                    cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '%" + r1 + "%' AND bus_no LIKE '" + bus + "'", null);
                    if (cs.getCount() > 0) {
                        display(cs, null, false);
                    } else {
                        msg.setText("No student found with given Roll number & Bus number!");

                    }
                }
                else{
                    msg.setText("Enter first two characters between 12-16");
                }
            }
            else if(r1.length() ==7){
                cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '%" + r1 + "%' AND bus_no LIKE '" + bus + "'", null);
                if (cs.getCount() > 0) {
                    display(cs, null, false);
                } else {
                    msg.setText("Roll number doesn't match any student record!");

                }
            }
            else if(r1.length()>3){
                msg.setText("Enter valid Roll Number!");
            }
        }
        //eight
        else if (r1.length() > 0 && n1.length() > 0 && b1.length() > 0) {
            int bus;
            bus= Integer.parseInt(b1);
            if (r1.length() > 10) {
                Toast.makeText(getBaseContext(), "Roll NO should be 10 characters!", Toast.LENGTH_LONG).show();
            } else if ((r1.length() == 1 || r1.length() == 2)) {
                cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '________%" + r1 + "%' AND name LIKE '%" + n1 + "%' AND bus_no LIKE '" + bus + "'", null);
                if (cs.getCount() > 0) {
                    display(cs, null, false);
                } else {
                    msg.setText("Details not found!");

                }
            } else if (r1.length() == 3) {
                cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '_______" + r1 + "' AND name LIKE '%" + n1 + "%' AND bus_no LIKE '" + bus + "'", null);
                if (cs.getCount() > 0) {
                    display(cs, null, false);
                } else {
                    msg.setText("Details not found!");



                }
            } else if (r1.length() == 10) {
                int check= Integer.parseInt(r1.substring(0,2));
                if(check>11) {
                    cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '%" + r1 + "%' AND name LIKE '%" + n1 + "%' AND bus_no LIKE '" + bus + "'", null);
                    if (cs.getCount() > 0) {
                        display(cs, null, false);
                    } else {
                        msg.setText("Details not found!");

                    }
                }

                else {
                    msg.setText("Enter first two characters between 12-16");

                }
            }
            else if(r1.length() ==7){
                cs = db.rawQuery("SELECT * FROM BUS WHERE roll_no LIKE '%" + r1 + "%' AND name LIKE '%" + n1 + "%' AND bus_no LIKE '" + bus + "'", null);
                if (cs.getCount() > 0) {
                    display(cs, null, false);
                } else {
                    msg.setText("Roll number doesn't match any student record!");

                }
            }
            else if (r1.length()>3){
                msg.setText("Enter valid Roll Number!");
            }
        }
        db.close();

    }
    public void keyHide(View view){
        EditText r= (EditText) findViewById(R.id.editText);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(r.getWindowToken(), 0);
    }

    public void display(Cursor c, Cursor c1, boolean f) {
        SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
        String title = "COUNT: " + c.getCount();
        String msg1 = "";
        if (f) {
            c1.moveToFirst();
            if (c1.getCount() > 1) {
                title = "COUNT: " + c.getCount() + "/47";
                msg1 = "BUS NUMBER: " + c1.getString(0) + "\n" +
                        "COORDINATOR: " + "\n" + c1.getString(1) + "\n" + c1.getString(4) + "\n";
                c1.moveToNext();
                msg1 = msg1 + "COORDINATOR: " + "\n" + c1.getString(1) + "\n" + c1.getString(4) + "\n" + "ROUTE:" + c1.getString(2) + "\n" + c1.getString(3) + "\n\n";
            } else {
                do {
                    title = "COUNT: " + c.getCount() + "/47";
                    msg1 = "BUS No: " + c1.getString(0) + "\n" +"COORDINATOR: " + "\n" + c1.getString(1) + "\n" + c1.getString(4) + "\n" + "ROUTE:" + c1.getString(2) + " " + c1.getString(3) + "\n\n";
                } while (c1.moveToNext());
            }
        }
        c.moveToFirst();
        do {
            Cursor c2=db.rawQuery("SELECT * FROM BUS_I WHERE bus_no LIKE '%"+c.getString(4)+"%'",null);
            c2.moveToFirst();
            msg1 = msg1 + "" + c.getString(0) + "\n" +
                    "" + c.getString(1) + "\n" +
                    "" + c.getString(2) + "\n" +
                    "Yr: " + c.getString(3) + "   B.No: " + c.getString(4) + "   St.No: " + c.getString(5) + "\n"+"ROUTE:"+c2.getString(2)+"\n"+c2.getString(3)+"\n"+"------------------------------------\n";
        } while (c.moveToNext());
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.YourDialogStyle);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg1);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setIcon(R.drawable.back);
        alertDialog.show();
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }
    public class alert extends AsyncTask<Void,Void,Void> {
        ProgressDialog p=new ProgressDialog(Main2Activity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p.setIcon(R.drawable.back);
            p.setTitle("Building App data");
            p.setMessage("Building....");
            p.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            p.hide();
        }

        @Override
        protected Void doInBackground(Void... params) {
            SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
            db.execSQL("DROP TABLE IF EXISTS BUS");
            db.execSQL("CREATE TABLE BUS (roll_no VARCHAR,name VARCHAR,branch VARCHAR,year INT(1),bus_no INT(2),seat_no INT(2));");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0468', 'B.LAVANYA','SIETK-ECE','2','1','1');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0537', 'A.RUCHITHA','SISTK-CSE','2','1','2');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0550', 'V.JAYASREE','SIETK-ECE','2','1','3');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04I8', 'A.VAISHNAVI','SIETK-ECE','3','1','4');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0109','C.CHANDANA','SIETK-CIVIL','3','1','5');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0503','P.BABITHA','SIETK-CSE','3','1','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','B.SAROJA','SISTK-ECE','HOD','1','7');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0209','E.GEETHIKA','SIETK-EEE','4','1','8');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04A7','G.NISCHAY PRAJAPATHI','SIETK-ECE','2','1','9');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04G0','A.SOUMITH','SIETK-ECE','2','1','10');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0317','P.GNANA PRAKASH','SIETK-MECH','2','1','11');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.KALPALATHA','SIETK-BS&H','STAFF','1','12');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0534','K.JAYASRI','SIETK-CSE','4','1','13');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A05A5','V.ROHITH','SIETK-CSE','2','1','14');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0139','K.SAGAR NAIK','SISTK-CIVIL','2','1','15');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0523','P.DEVENDRA REDDY','SIETK-CSE','2','1','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','KVSK.PRAKASH','SIETK-CSE','STAFF','1','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','B.PAVAN KUMAR','SIETK-CSE','STAFF','1','18');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04D8','M.ROHITH','SIETK-ECE','2','1','19');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0466','M.MANOJ KUMAR','SIETK-ECE','2','1','20');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0493','A.PRAVEEN','SIETK-ECE','2','1','21');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A03C3','G.YASWANTH','SIETK-MECH','2','1','22');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04G4','K.SRIHARI','SIETK-ECE','2','1','23');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','S.IBRAHIM','DIP-SIETK','1','1','24');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-M-042','M.SAI KUMAR','DIP-SIETK','2','1','25');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-M-039','P.SAI KIRAN NAIDU','DIP-SIETK','2','1','26');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0146','C.THEJA ','SIETK-CIVIL','4','1','27');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04C2','N.PURUSHOTHAM','SIETK-ECE','3','1','28');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04I5','B.THARUN KUMAR','SIETK-ECE','2','1','29');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04E9','A.SAI LOKESH','SIETK-ECE','2','1','30');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A03B1','K.T.VAMSI KRISHNA','SIETK-MECH','2','1','31');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0530','M.JAITHEJA VINAY SAI','SIETK-CSE','2','1','32');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0415','K.BINNISH SAI','SIETK-ECE','3','1','33');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0329','S.JAGADEESH','SIETK-MECH','3','1','34');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0418','K.CHIRANJEEVI','SIETK-ECE','3','1','35');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04A3','M.NAGASAI PAVAN KUMAR','SIETK-ECE','3','1','36');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04C1','G.MOHAN KRISHNA','SIETK-ECE','4','1','37');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0157','I.RAMA KRISHNA','SIETK-CIVIL','2','1','38');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0301','R.AKHIL','SISTK-MECH','2','1','39');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0412','M.CHINNI KRISHNA','SISTK-ECE','4','1','40');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04I8','V.THAYAGACHARI','SIETK-ECE','3','1','41');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0236','T.SREEKANTH','SIETK-EEE','4','1','42');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0314','S.BHARGAV','SIETK-MECH','4','1','43');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0310','S.BALAJI','SIETK-MECH','4','1','44');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0248','K.KRISHANA VAMSI','SIETK-EEE','4','1','45');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0356','P.MANOJ KUMAR','SIETK-MECH','4','1','46');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0355','CH.MANOJ KUMAR','SIETK-MECH','4','1','47');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR', 'S.JYOTHSNA','SIETK-ECE','1','2','1');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR', 'S.SHALINI','SIETK-CSE','1','2','2');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR', 'D.RAGHAVI','SIETK-CSE','1','2','3');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR', 'P.M.NEELIMA','SISTK-ECE','1','2','4');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','K.BINDHU PRIYA','SIETK-ECE','1','2','5');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','T.HARIKA','SISTK-ECE','1','2','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.RATHNA KAMALA','SIETK-ECE','1','2','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.SASI KALA','SIETK-BSH','1','2','8');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','T.VAISHNAVI','SIETK-ECE','1','2','9');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','G.SRAVANI','SIETK-ECE','1','2','10');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','U.PRAVALLIKA','SIETK-ECE','1','2','11');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','Y.LAHARI','SIETK-ECE','1','2','12');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR ','V.D.POOJITHA','SIETK-ECE','1','2','13');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR',' C.SAI THANUSHA','SIETK-ECE','1','2','14');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','B.PREETHI','SIETK-CSE','1','2','15');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','N.DIVYA BHARATHI','SIETK-CSE','1','2','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','T.R.VENUGOPAL','SIETK-DIP','1','2','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','P.DILEEP','SIETK-DIP','1','2','18');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','N.RAJAGOPAL ','SIETK-BSH','1','2','19');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','A.MAHESH','SIETK-BSH','1','2','20');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','R.VASU','SIETK-DIP','1','2','21');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.SUBRAMANYA','SIETK-MBA','1','2','22');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','P.VENKATESH','SIETK-CIVIL','1','2','23');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','J.BALAJI','DIP-SIETK','1','2','24');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','S.SASI VARDHAN REDDY','CSE-SIETK','1','2','25');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','P.PURUSHOTHAM','CSE-SIETK','1','2','26');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','A.GOWTHAM KUMAR ','SIETK-ECE','1','2','27');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','K.SRIDHAR','SIETK-ECE','1','2','28');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','K.NAVEEN REDDY','SIETK-ECE','1','2','29');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','M.ANISH','SIETK-ECE','1','2','30');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','G.GANATH KUMAR','SIETK-MECH','1','2','31');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','S.MANSOOR ','SIETK-ECE','1','2','32');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','K.KARTHIK','SIETK-ECE','1','2','33');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','S.SAI MANOJ','SIETK-DIP','1','2','34');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','T.MONU','SIETK-EEE','1','2','35');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','K.RAMCHARAN','SIETK-MECH','1','2','36');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','P.RAJASEKHAR','SIETK-CIVIL','1','2','37');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','B.BASHA','SIETK-CSE','1','2','38');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','A.VINODH KUMAR','SIETK-ECE','1','2','39');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','V.MUNISAI','SIETK-ECE','1','2','40');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','K.CHARAN KUMAR','SISTK-ECE','1','2','41');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','K.VENKATESH','SIETK-ECE','1','2','42');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','G.VIJAY','SIETK-CSE','1','2','43');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','K.PURUSHOTHAM','SIETK-CIVIL','1','2','44');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','P.MANOJ','SIETK-ECE','1','2','45');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','A.SHAHID','SIETK-CSE','1','2','46');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','M.HARISH','SIETK-EEE','1','2','47');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04L4', 'D.SWATHI','SIETK-ECE','4','3','1');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR', 'N.SUKANYA','DIP-EEE','1','3','2');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR', 'ROJI','SIETK-ECE','1','3','3');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR', 'K.S.RAMYA','SIETK-ECE','1','3','4');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0235','D.MOUNIKA','SIETK-EEE','2','3','5');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0430','M.DIVYASRI','SIETK-ECE','2','3','6');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0220','B KOKILA','SIETK-EEE','3','3','7');");
            db.execSQL("INSERT INTO BUS VALUES ('1461A04H9','C.SWAPNA','SISTK-ECE','3','3','8');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','A.GOWTHAMI','SISTK-CIVIL','1','3','9');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0492', 'V.PRATHYUSHA','SISTK-ECE','2','3','10');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0540', 'P.VIJETHA','SIETK-CSE','3','3','11');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0547', 'T.THEJA','SISTK-CSE','2','3','12');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0401', 'R.ANITHA','SISTK-ECE','2','3','13');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A507', 'K.BHAVYA','SISTK-CSE','2','3','14');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0521','K.LAVANYA','SISTK-CSE','3','3','15');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0535','P.SUMAJA','SISTK-CSE','3','3','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','G.CHANDRAKANTH','SISTK-CIVIL','0','3','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','M.P.SRINIVASULU','SIETK-MECH','0','3','18');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR',' DIMPLE','SIETK-CSE','1','3','19');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'D.SUKANYA','SISTK-CIVIL','0','3','20');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'V.VAISHNAVI','SIETK-EEE','0','3','21');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','N.GNANENDRA','SIETK','1','3','22');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0111','A.GANGADHAR','SIETK-CIVIL','2','3','23');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','P.CHARAN','SIETK-EEE','1','3','24');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04F3','13F61A04F3','SIETK-ECE','4','3','25');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0459','B.HARSHA VARDHAN','SIETK-ECE','4','3','26');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0572','K.V.M.SAI JASWANTH','SIETK-CSE','4','3','27');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','P.SUNIL KUMAR','DIPLOMA','1','3','28');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0326','S.R.JAGAPATHI BABU','SIETK-MECH','2','3','29');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','B.BHARATH','SIETK','1','3','30');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-EF-003','N.BALASUBRAMANYAM','DIPLOMA','2','3','31');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','R.VENU','SISTK-ECE','1','3','32');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','E.LOKESH','SIETK-ECE','1','3','33');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','V.MADHU SUDHAN','SIETK-ECE','1','3','34');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0404','V.BENARJEE SAI','SIETK-ECE','2','3','35');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','P.DILIP','SIETK-DIP','1','3','36');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0207','N.DILEEP KUMAR','SISTK-EEE','2','3','37');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','MUNI RAJESH','SIETK-ECE','1','3','38');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0582','V.NAVEEN KUMAR','SIETK-CSE','2','3','39');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0152 ','C.PAVAN KUMAR','SIETK-CIVIL','3','3','40');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','C.YASHWANTH NAIDU','SIETK-MECH','1','3','41');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0236','S RAHUL EEE','SIETK-EEE','3','3','42');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0431','P.DWARAKANATH','SIETK-ECE','3','3','43');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0463','T.JAYA KRISHNA','SIETK-ECE','3','3','44');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04A5',' T.NAVEEN ','SIETK-ECE','2','3','45');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04G5','T.SV.S SAI SRIKAR','SIETK-ECE','3','3','46');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0231','C.MURALI KRISHNA','SIETK-EEE','3','3','47');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0104','PV.DEEPTHI','SISTK-CIVIL','4','3','48');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','D.ROHINI','SIETK-ECE','1','3','49');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','A.YASWANTH','SIETK-DIP','1','3','50');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04D4','B.SURI TEJA','SISTK-CIVIL','2','3','51');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0305','K.ANIL KUMAR','SIETK-MECH','2','3','52');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04F2', 'R.SAI ROJA','SIETK-ECE','3','4','1');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0497', 'V.MONIKA','SIETK-ECE','3','4','2');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0486', 'K.MADHURIMA','SIETK-ECE','1','4','3');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04F5', 'B.RADHIKA','SIETK-ECE','4','4','4');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0134','C.SAI TEJASWINI','SIETK-civil','4','4','5');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04J2','E.SNEHALATHA REDDY','SIETK-ECE','2','4','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','F.ANAND RAJU','SIETK-TRANSPORT-HEAD','0','4','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','E.KARTHEEK','SIETK-BS&H','1','4','8');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-EC-120','B.NAVYA SAII','SIETK-DIP','2','4','9');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0476', 'C.LAVANYA','SISTK-ECE','3','4','10');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0415', 'K.JYOTHSNA','SIETK-ECE','3','4','11');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0411', 'P.BAVITHA','SIETK-ECE','3','4','12');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0413', 'K.TEJA','SIETK-ECE','3','4','13');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0448', 'C.SRI MOUNICA','SIETK-ECE','2','4','14');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0509','B.ASHA KIRAN','SIETK-CSE','3','4','15');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0477','C.MANASA','SIETK-ECE','2','4','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','R.LAKSHMI DEVI','BS&H-SIETK','0','4','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','MAMATHA','BS&H-SIETK','0','4','18');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY',' VASUNDHARA','SIETK-T&P','0','4','19');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'RAMYA SREE','SISTK-CIVIL','0','4','20');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'V.DEEPA','SIETK-ECE','0','4','21');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','SANTOSH','SIETK-T&P','0','4','22');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','V.SREEDHAR','SIETK-BSH','0','4','23');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0573','M.SAI KIRAN','SIETK-CSE','4','4','24');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','THULASIRAM','SIETK-TECH','0','4','25');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','MOHAN BABU','SIETK-ECE','4','4','26');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','KODANADA RAMAIAH','SIETK-BSH','1','4','27');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','RAJESH KUMAR','SIETK-civil','0','4','28');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0348','K.MANOJ KUMAR','SIETK-MECH','3','4','29');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A03A6','D.THEJESH REDDY','SIETK-MECH','2','4','30');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0386','V.SAI SRINIVASULU','SIETK-MECH','3','4','31');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0379','P.SAI BHARGAV','SISTK-MECH','3','4','32');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04F4','M.SAI TEJA','SIETK-ECE','3','4','33');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST-YEAR','B.NIKHIL REDDY','SIETK','1','4','34');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0430','T.HEMANTH','SIETK-ECE','2','4','35');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0439','P.GIRISH','SIETK-ECE','2','4','36');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0136','P.SATHEESH','SIETK-CIVIL','4','4','37');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0407','M.ASHOK KUMAR','SIETK-CIVIL','4','4','38');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0438','S.ASIF','SIETK-ECE','2','4','39');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0557 ','A.KOUSHIK VARMA','SIETK-CSE','2','4','40');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04B3','B.SAI KIRAN','SIETK-ECE','2','4','41');");
            db.execSQL("INSERT INTO BUS VALUES ('LE','I.SAI GANESH','SIETK','3','4','42');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0438','V.JAYA SANKAR','SIETK-ECE','2','4','43');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0320','K.HARI KRISHNA','SIETK-MECH','2','4','44');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0486',' T.PAVAN KALYAN ','SIETK-ECE','2','4','45');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A03C2','M.VIVEK','SIETK-MECH','2','4','46');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04K4','A.VIT','SIETK-ECE','2','4','47');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0147','N.POORNA CHANDU','SIETK-CIVIL','2','4','48');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'E.MOUNIKA','SIETK-CSE','1','5','1');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'V.RAMYA','SIETK-ECE','1','5','2');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'Y.PALLAVI','SIETK-ECE','1','5','3');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'D. MOUNIKA','SIETK-ECE','1','5','4');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','S.KEERTHI YADAV','SIETK-ECE','1','5','5');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','M.HARITHA','SIETK-DECE','1','5','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','M.SANTHI','SIETK-MBA',' ','5','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','S.SAIDA  BHANU','SISTK-BS&H',' ','5','8');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','A.SUPRIYA','SIETK-CSE','1','5','9');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','R.VANI','SIETK-CSE','1','5','10');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','P.SUKANYA','SIETK-MECH','1','5','11');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','N.DIVYA','SIETK-CSE(LAB)',' ','5','12');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','D.NIKHITHA','SIETK-ECE',' 1','5','13');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','VVS.AKHILESH','SIETK-CSE','1','5','14');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','B.VENKATESH','SIETK-ECE',' ','5','15');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','G.SHANMUKHA SRINIVAS','SIETK-CIVIL',' ','5','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','C.SIVA KUMAR  PRASAD','SIETK-CIVIL',' ','5','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','A.SESHAPPA','SIETK-MECH',' ','5','18');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','N.MOHAN','SIETK-ECE','1','5','19');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','D.JANAKIRAMA REDDY','SISTK-BS&H','1','5','20');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','E.SATHEESH KUMAR','SISTK-ECE','1','5','21');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','M.NAGAPAVAN','SIETK-DME','1','5','22');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','D.PRAKASH','SIETK-ECE','1','5','23');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','R.MINILEELA KUMAR','SIETK-ECE','1','5','24');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','D.GOWTHAM REDDY','SIETK-MECH','1','5','25');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','T.MANO SAINATH','SIETK-ECE',' 1','5','26');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','SHAIK ABDUR RAZZAQ','SIETK-CES','1','5','27');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','R.PRANAY CHOWDARY','SIETK-CSE','1','5','28');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','G.CHAITHANYA SAI','SIETK-DME','1','5','29');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','C.PAVAN KUMAR','SIETK-DME','1','5','30');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','T.D MITHUN','SIETK-DME','1','5','31');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','V.BHANU DURGA PRASAD',' ','1','5','32');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','G.VENKAT SAI RAJU',' ','1','5','33');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','G.VS NIRANJAN',' ','1','5','34');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','A.ROOP SAI REDDY',' ','1','5','35');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','N.GURU PRAAD',' ','1','5','36');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'V.MUKESH','SIETK-CIVIL','1','5','37');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'A.HARSHA VARDHAN','SIETK-DME','1','5','38');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'A.VINAY','SIETK-ECE','1','5','39');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','G.MUNI PRASAD','SIETK-MECH','1','5','40');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','M.KANISHKA REDDY','SISTK-CSE','1','5','41');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','M.PURUSHOTHAM RAJU','SIETK-ECE','1','5','42');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','RAVI RAJU','SIETK-ECE','1','5','43');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','Y.UMA MAHESH',' ','1','5','44');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','G.VAMSI ','SIETK-MECH','1','5','45');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','DVMR SANDILYA ','SIETK-CSE','1','5','46');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','SIVA SAI KUMAR',' ','1','5','47');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','R.HARSHA VARDHAN','SISTK-ECE','1','5','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A05C4', 'T.P.SUMA','SIETK-CSE','2','6','1');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0219', 'K JASHNAVI','SIETK-EEE','2','6','2');");
            db.execSQL("INSERT INTO BUS VALUES ('15F65A0403', 'B.MADHAVI','SIETK-ECE','3','6','3');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0585', 'C.SIREESHA','SIETK-CSE','4','6','4');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0119','M.KOUMUDHI','SISTK-CIVIL','3','6','5');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0512','B.R.ESWARI','SISTK-CSE','2','6','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.NIRUPAMA','SIETK-CSE',' ','6','7');");
            db.execSQL("INSERT INTO BUS VALUES ('PA-PRINCIPAL','SUJATHA','SIETK',' ','6','8');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04K1','J.SRINIKITHA','SIETK-ECE','4','6','9');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04L6','K SWETHA','SIETK-ECE','4','6','10');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04L0','P.SUPRIYA','SIETK-ECE','4','6','11');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','K.BABY','SIETK-BS&H','1','6','12');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','M.VIJAYA LAKSHMI','SISTK-ECE',' ','6','13');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0145','O.SWATHI','SIETK-CIVIL','4','6','14');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04K2','K.VISHNU VANDHANA','SIETK-ECE','3','6','15');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0449','V.HARSHITHA','SIETK-ECE','3','6','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','J.YUGANDHAR','SIETK-EEE',' ','6','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','G.TIRUMALA VASU','SIETK-ECE',' ','6','18');");
            db.execSQL("INSERT INTO BUS VALUES ('14270-ECE-001','K.CHANDHINI','SIETK-ECE','3','6','19');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0465','N.SAI PRIYANKA','SISTK-ECE','4','6','20');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0218','P.VINEETHA REDDY','SISTK-EEE','4','6','21');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04C3','N.MUNI DILLI BABU','SIETK-ECE','4','6','22');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0316','D.DIROJ KUMAR','SIETK-MECH','2','6','23');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04E7','D.SAI PRATAP','SIETK-ECE','2','6','24');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','N.RAMESH RAJU','SIETK-EEE',' ','6','25');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','M.JANARDHAN RAJU','SIETK-ECE',' ','6','26');");
            db.execSQL("INSERT INTO BUS VALUES ('II-CIVIL','T.VENKATA PRASANTH','SIETK-CIVIL','2','6','27');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0327','B.GIRISH KUMAR','SIETK-MECH','4','6','28');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0352','K.S.MOUNISH BABU','SIETK-MECH','2','6','29');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0133','M.LAVA KUMAR','SIETK-CIVIL','3','6','30');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0405','J.DILEEP','SISTK-ECE','3','6','31');");
            db.execSQL("INSERT INTO BUS VALUES ('154E5A0208','B.SAI TEJ','SISTK-EEE','3','6','32');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04B1','P.SAI MANISH','SISTK-ECE','2','6','33');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0559','A.PREM KIRAN','SIETK-CSE','3','6','34');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0335','P.KOTEESWAR REDDY','SIETK-MECH','3','6','35');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-C-010', 'K.KESAVA RAO','SIETK-CSE','2','6','36');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0501', 'K.ABDUL WAHID','SIETK-CSE','2','6','37');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04A4', 'A.NAGENDRA KUMAR','SIETK-ECE','3','6','38');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0323', 'P.HUSSIAN KHAN','SIETK-MECH','2','6','39');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0396','T.SHANMUGA SRINIVAS REDDY','SIETK-MECH','2','6','40');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0303','S.ANIL KUMAR','SIETK-MECH','2','6','41');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04J1','D.UDAY KUMAR','SIETK-ECE','3','6','42');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0590','N.VASU','SIETK-CSE','3','6','43');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0181','A.NIKHIL REDDY','SIETK-CIVIL','2','6','44');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0416','P.CHAITANYA VARMA','SIETK-ECE','3','6','45');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0103','A.NIKHILESH','SIETK-CIVIL','2','6','46');");
            db.execSQL("INSERT INTO BUS VALUES ('124E1A0473','K.NAVEEN KUMAR','SISTK-ECE','4','6','47');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0130', 'P.JEEVANA','SIETK-CIVIL','3','7','1');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0485', 'P.MADHURI','SIETK-ECE','3','7','2');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0412', 'K.BHAVYA','SIETK-ECE','3','7','3');");
            db.execSQL("INSERT INTO BUS VALUES ('16F65A0403', 'P.M.KIRANMYE','SIETK-ECE','2','7','4');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04G5','P.SREE LAKSHMI','SIETK-ECE','2','7','5');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0499','D.MOUNIKA','SIETK-ECE','3','7','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','G.SESHADRI','SIETK-EEE',' ','7','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','G.PRASAD BABU','SIETK-CSE',' ','7','8');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','G.LATHA ','SIETK-MBA','1','7','9');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0527','R.MAMATHA','SISTK-CSE','2','7','10');");
            db.execSQL("INSERT INTO BUS VALUES ('16F65A0201','T.AMANI','SIETK-EEE','2','7','11');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0537','B.LATHA SREE ','SIETK-CSE','3 ','7','12');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0182','G.TEJESWINI','SIETK-CIVIL',' 3','7','13');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04E4','K.VASAVI','SISTK-ECE','2','7','14');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0106','B.MONICA','SIETK-CIVIL','2','7','15');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0107','B.POOJITHA','SIETK-CIVIL','2 ','7','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','. K.ESWARI','SIETK-CIVIL',' ','7','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','M.SHOBHA','SIETK-ECE',' ','7','18');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','K. SAI CHANDU','SIETK-MBA',' ','7','19');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','DASARADHI','SIETK-CSE',' ','7','20');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','K.MANI','SIETK-EEE',' ','7','21');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','B.HARI KRISHNA','SIETK-BS&H',' ','7','22');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.SURESH','SIETK-T&P',' ','7','23');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','S.MUNI SEKHAR','SIETK-EEE',' ','7','24');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','M.PURUSHOTHAM','SIETK-BS&H',' ','7','25');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','K.DINESH KUMAR','SITE-ENGR','  ','7','26');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0126','B.HITESH KUMAR','SIETK-CIVIL','3','7','27');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0131','P.RAKESH','SISTK-CIVIL','2','7','28');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0329 ','I PAVAN KUMAR','SISTK-MECH','2','7','29');");
            db.execSQL("INSERT INTO BUS VALUES ('16F65A0402','P.M.HARISH','SIETK-ECE','2','7','30');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','P.ROHITH','SIETK-ECE','1','7','31');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0537','D.GOVARDHAN','SIETK-CSE','2','7','32');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0504','B.P.BALA SREENIVAS','SIETK-CSE','3','7','33');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','C.SHARUK','SIETK-ECE','1','7','34');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','R.DINESH',' SIETK-MECH','1','7','35');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0517','D.KIRAN KUMAR','SISTK-CSE ','3','7','36');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0408', 'E.ESWAR','SISTK-ECE','3','7','37');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A444', 'E.SUNIL','SISTK-ECE','3','7','38');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0453', 'K.JASWANTH','SIETK-ECE','2','7','39');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0105','T.DHARANI DHAR','SISTK-CIVIL','3','7','40');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','M.MOHAMMAD ASAD',' ','1','7','41');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-M-035','S.PRADEEP','SIETK-MECH',' ','7','42');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-M-043',' A.SAI LOKESH','SIETK-MECH','1','7','43');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A03E3','N.YUGANDHAR','SIETK-MECH ','4','7','44');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0562','D.PRANAY RAM','SIETK-CSE','4','7','45');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04K0','CH.SRI MANITEJA ','SIETK-ECE','4','7','46');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04M6','K.VAMSI','SIETK-ECE ','4','7','47');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0537', 'K.SWATHI','SISTK-CSE','3','8','1');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0534', 'N.SUKANYA','SISTK-CSE','3','8','2');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04J2', 'K.USHASRI','SIETK-ECE','3','8','3');");
            db.execSQL("INSERT INTO BUS VALUES ('15F65A0212', 'M.NANDINI','SIETK-EEE','3','8','4');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0544','A.LIKITHA SRI','SIETK-CSE','4','8','5');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04B5','A.MANASA','SIETK-ECE','4','8','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.CHANDRASEKHAR','SIETK-EEE',' ','8','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','B. RAVINDRA NAICK','SIETK-CSE',' ','8','8');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A05',' G.MUNILAKSHMI','SIETK-CSE','2','8','9');");
            db.execSQL("INSERT INTO BUS VALUES ('M.TECH',' ','SIETK-M.TECH',' ','8','10');");
            db.execSQL("INSERT INTO BUS VALUES ('M.TECH',' ','SIETK-M.TECH',' ','8','11');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','V.VISWANADHA ','SIETK-ECE','  ','8','12');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','G.HARI PRASAD','SIETK-CSE','  ','8','13');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','R.JYOSHNA DEVI','SIETK-MBA',' ','8','14');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','K.S.DEEPA RANI','SIETK-MBA',' ','8','15');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','CH.PALLAVI','SIETK-ECE','  ','8','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','. CK.MEGHALATHA','SIETK-ECE',' ','8','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','J.JHANSI','SIETK-ECE',' ','8','18');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0480','M.MANIKANTA','SIETK-ECE','2 ','8','19');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A05B0','V.SAI SRAVAN','SIETK-CSE','2 ','8','20');");
            db.execSQL("INSERT INTO BUS VALUES ('LE',' ','SIETK',' ','8','21');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0321','K.DINESH','SIETK-MECH','3','8','22');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0455','L.HEMANTH','SIETK-ECE','3 ','8','23');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0590 ','M.PRASANNA','SIETK-CSE','2 ','8','24');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0522 ','D.C.POTHAIAH','SIETK-CSE','2 ','8','25');");
            db.execSQL("INSERT INTO BUS VALUES ('154E5A0403','T.JAGADEESH','SISTK-ECE',' 3 ','8','26');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','Y.GURU PRASAD','SIETK-CIVIL',' ','8','27');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04G2','P.RAM LAKSHMAN REDDY','SIETK-ECE','4','8','28');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04L0 ','Y.YOGA VINEETH','SIETK-ECE','2','8','29');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A05C7','G.UDAY GOUD','SIETK-CSE','2','8','30');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0485','S.MOHAMMED JABIVULLA','SIETK-ECE','2','8','31');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0502','M.BALAJI','SISTK-CSE','3','8','32');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0440','G.SHASANK','SISTK-ECE','3','8','33');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0339','A.JASWANTH','SIETK-MECH','4','8','34');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04B9','M.MANOJ KUMAR',' SIETK-ECE','4','8','35');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04G1','G.RAM KUMAR','SIETK-ECE ','4','8','36');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0132', 'N.REDDY SAI','SIETK-CIVIL','4','8','37');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04L3', 'K.SURYA','SIETK-ECE','4','8','38');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04H7', 'G.SAI KISHORE ROYAL','SIETK-ECE','4','8','39');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04G9','K.RANJITH KUMAR','SIETK-ECE','4','8','40');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0349','Y.LEELA VIGNESWAR',' SIETK-MECH','4','8','41');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A03D5','C.VENKATESH','SIETK-MECH','4 ','8','42');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0246',' K.U.VENKATESH','SIETK-EEE','4','8','43');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0310','S.BALAJI','SIETK-MECH ','4','8','44');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0307','N.BHANU PRASAD','SISTK-MECH','4','8','45');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0347','P.SIKINDAR ','SISTK-MECH','4','8','46');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A03C5','J.VEERA RAGHAVENDRA','SIETK-MECH ','4','8','47');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'M.RUCHITA','SIETK-ECE','1','9','1');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'S.DIVYA','SIETK-CSE','1','9','2');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'V.YAMINILOHITA','SISTK-CSE','1','9','3');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'G.REVATHI','SISTK-CSE','1','9','4');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'C.MOUNIKA','SISTK-ECE','1','9','5');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'G.HARIPRIYA','SIETK-CSE','1','9','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'N.SREEDHAR','SISTK-MECH','','9','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'BHASKAR','SIETK-OFF','','9','8');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'A.BHAVYA','SIETK-ECE','1','9','9');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'K.THEJASREE','SISTK-ECE','1','9','10');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'T.JHANSI','SISTK-ECE','1','9','11');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'P.M.KEERTHANA','SIETK-CSE','1','9','12');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'T.MOHANPRIYA',' ',' ','9','13');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'A.RAMYASREE','SIETK-CSE','1','9','14');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'K.PRIYANKA','SISTK-ECE','1','9','15');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'K.SAI VIJAYA LAKSHMI','SIETK-ECE','1','9','16');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'C.THANMAYEE','SIETK-CSE','1','9','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'R.VANDANA','SIETK-ECE','','9','18');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'S.DHANUSH','SIETK-DME','1','9','19');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'V.ROHITH','SIETK-CSE','1','9','20');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'S.KISHORE','SIETK-MBA','','9','21');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'T.THARAKESH','SIETK-DME','1','9','22');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'D.RAHUL','SIETK-CSE','1','9','23');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'B.SAI SRINIVAS','SIETK-MECH','1','9','24');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'P.LIKHITH SAI KUMAR','SIETK-CSE','1','9','25');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'K.YOGANANDA','SIETK-MECH','1','9','26');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'R.SRINIVASS REDDY','SIETK-DME','1','9','27');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'K.JAGADEESH','SIETK-MECH','1','9','28');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'G.MUNI KUMAR','SIETK-DCE','1','9','29');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'V.MADHAN','SIETK-DECE','1','9','30');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'E.NITHIN KUMAR','SIETK-DME','1','9','31');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'N.GNANESWAR','SIETK-ECE','1','9','32');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'P.S.VAMSI KRISHNA','SIETK-DME','1','9','33');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'P.PRIYATHEM','SIETK-DECE','1','9','34');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'P.KRISHNA VAMSI','SIETK-ECE','','9','35');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'T.PAVAN TEJA','SIETK-CIVIL','1','9','36');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'G.PRASAD','SIETK-DME','1','9','37');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'M.S.CHANIKYA TEJA','','','9','38');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'N.THULASIRAM','SIETK-DME','1','9','39');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'D.GANESH','SISTK-CSE','1','9','40');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'K.VAMSI','SISTK-ECE','1','9','41');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'P.B.YASHWANTH','SIETK-MECH','1','9','42');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'K.KARTHEEK','SISTK-CSE','1','9','43');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'M.CHANDRAHAS','SIETK-MECH','1','9','44');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'P.REDDY PAVAN','SIETK-ECE','1','9','45');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'A.HEMANTH KUMAR','SISTK-CSE','1','9','46');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'M.SIVA SANKAR REDDY','SIETK-MBA','1','9','47');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'N.VAMSI KRISHNA RAJU','SIETK-ECE','1','9','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'CH.SIREESHA','SIETK-CSE','1','9','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'C.MURALI','SIETK-EEE','1','9','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'M.SIVASANKAR REDDY','SIETK-MBA','1','9','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('1st year', 'M.PREM KUMAR','SIETK-MECH','1','9','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04B1','Y.PRASHITHA','SIETK-ECE','2','10','1');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04M3','T.VAISHNAVI','SIETK-ECE','4','10','2');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0476','D.TEJASWINI','SIETK-ECE','4','10','3');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0515','M.DEVIKA','SIETK-CSE','3','10','4');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0534','RS.KEERTHI','SIETK-CSE','3','10','5');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04E1','DR.REHANA','SIETK-ECE','3','10','6');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0303','C.DELLIP','SISTK-MECH','3','10','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.MANIMOHAN','SISTK-ECE','','10','8');");
            db.execSQL("INSERT INTO BUS VALUES ('LATERALENTRY','V.MANASA','','','10','9');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0465','P.MANJULA','SISTK-ECE','2','10','10');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0545','C.SRILEKHA','SISTK-ECE','2','10','11');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1AO548','M.YAMINI','SISTK-CSE','2','10','12');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04C2','N.POOJASREE','SIETK-ECE','3','10','13');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0428','T.DIVYA LAKSHMI','SIETK-ECE','3','10','14');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04B5','K.OM DEEPIKA','SIETK-ECE','3','10','15');");
            db.execSQL("INSERT INTO BUS VALUES ('', 'K.ROJA RAMANI','','','10','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','D.PRASAD','SISTK-EEE','','10','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','KAMALAKAR','SIETK-T&P','','10','18');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0420','D.DIVYA','SISTK-ECE','2','10','19');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04J8','M.VIDYA','SIETK-ECE','2','10','20');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04J4','S.VANI','SIETK-ECE','2','10','21');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A03C2','N.VENKATESH','SIETK-MECH','3','10','22');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0103','S.CHAITHANYA','SIETK-CIVIL','3','10','23');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0539','K.VENKATA SAIKUMAR','SIETK-CSE','3','10','24');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A03C6','T.VISHNUVARDHAN','SIETK-MECH','3','10','25');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0224','V.MAHESH REDDY','SIETK-EEE','3','10','26');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0140','P.SAI RAMAN','SISTK-CIVIL','2','10','27');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0482','G.MANJUNATH GOUD','SIETK-ECE','2','10','28');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0468','VC.MOHAN KUMAR','SISTK-ECE','2','10','29');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0492','C.MUNIGANESH','SIETK-ECE','2','10','30');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0373','T.RAJASEKHAR','SIETK-MECH','2','10','31');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0497','P.NAVVEN KUMAR','SIETK-ECE','2','10','32');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0490','N.MUKESH BABU','SIETK-ECE','2','10','33');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-M-024','A.KARTHICK','SIETK-MECH','2','10','34');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0551','M.JEEVAN','SIETK-CSE','2','10','35');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04C3','I.SIVAPRASAD','SISTK-ECE','2','10','36');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0349','K.MANOJ KUMAR','SIETK-MECH','3','10','37');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-C-001','T.ARVIND KUMAR','SIETK','2','10','38');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0132','V.MUNEENDRA','SIETK-CIVIL','2','10','39');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0491','K.MUNI KISHORE','SIETK-ECE','2','10','40');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0587','P.PAVAN','SIETK-CSE','2','10','41');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0401','J.AKHIL','SIETK-ECE','3','10','42');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0419','K.CHARAN','SIETK-ECE','2','10','43');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0430','KL DURGA CHOWDARY','SIETK-ECE','3','10','44');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04H6','A.SURYA TEJA','SIETK-ECE','3','10','45');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0431','O.DURGA SAIKUMAR','SIETK-ECE','2','10','46');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0454','K.HEMANTH','SIETK-ECE','2','10','47');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0412','M.TARUN TEJA','SIETK-ECE','3','10','48');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0443', 'C.KAVYA','SISTK-ECE','2','11','1');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0409', 'K.BHAVANA','SISTK-ECE','2','11','2');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04H4', 'N.SUSHMA','SIETK-ECE','2','11','3');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'P.BHARATHI','SIETK-CSE','1','11','4');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','T.PADMAJA','SISTK-ECE','1','11','5');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04J3','M.VANITHA','SIETK-ECE','3','11','6');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0447','G.VENKATESH','SISTK-ECE','3','11','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','T.NAGARAJU','SISTK-ECE',' ','11','8');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0432','T.R.JYOSHNA CHOWDARY','SISTK-ECE','4','11','9');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0443','P.HARIKA','SIETK-ECE','3','11','10');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04E0','P.REDDY MEGHANA','SIETK-ECE','3','11','11');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','T.SIVA','SISTK-ECE','1','11','12');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','MOHAMMED THEJA','SISTK-ECE',' 1','11','13');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','G.SAILAJA','SISTK-ECE',' ','11','14');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04F1','A.SAI MANISHA','SIETK-ECE','3','11','15');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','HARSHITA',' ','1','11','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.MADHU','SIETK-BS&H',' ','11','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','K.KIRAN','SIETK-BS&H',' ','11','18');");
            db.execSQL("INSERT INTO BUS VALUES ('15F6A0230','L.V.MANIKANTA','SIETK-EEE','2','11','19');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','VAMSI KRISHNA',' ','1','11','20');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','MUKTHADEER','SISTK-MECH','1','11','21');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0565','K.RAJU','SIETK-CSE ','4','11','22');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','RAHUL THONDAVADA',' ','1','11','23');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0313','P.HARISH','SISTK-MECH','4','11','24');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','O.CHAKRADHAR','SISTK-ECE','1','11','25');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0109','P.DURGA PRASAD','SISTK-CIVIL',' 3','11','26');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0425','A.DILEEP KUMAR','SIETK-ECE','3','11','27');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0424','K.MUNISEKHAR','SISTK-ECE','3','11','28');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','S.POORNA CHANDRA','SISTK-CIVIL','1','11','29');");
            db.execSQL("INSERT INTO BUS VALUES ('LE-SIETK-EEE','N.SRINIVAS','SIETK-EEE','2','11','30');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0539','G.GURU SAI','SIETK-CSE','2','11','31');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','DENNY PRIYATHAN',' ','1','11','32');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','E.LOKESH','SIETK-CSE ','1','11','33');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','A.KARTHIK','SIETK-MECH','1','11','34');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0494','M.MOHAN KUMAR','SIETK-ECE','3','11','35');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A03C4','M.YOGA PRASAD','SIETK-MECH ','2','11','36');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-C-015', 'C.MUNI KHAJURANADHA REDDY',' ','2','11','37');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'B.VIJAY KUMAR PERUR',' ','1','11','38');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0324', 'A.NAVEEN KUMAR','SISTK-MECH','2','11','39');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0456','D.PRUDHVI RAJ','SISTK-ECE','4','11','40');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0310','O.CHARAN KUMAR','SIETK-MECH','3','11','41');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04E6','G.SAI MOHITH','SIETK-ECE','2','11','42');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','A.ADHITHYA','CSE','1','11','43');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','Sunil','SISTK-ECE','1','11','44');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0176','E.VENKATESWARLU','SIETK-CIVIL','2','11','45');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','SAITHEJA ',' ','1','11','46');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','A.UDAY KUMAR','SIETK-ECE','1','11','47');");
            db.execSQL("INSERT INTO BUS VALUES ('2ND YEAR','SASI KUMAR','SIETK','2','11','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','SHIREESHA','SIETK','1','11','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','PRATAP','SIETK-ECE','1','11','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04G0','N.RAJITHA','SIETK-ECE','4','12','1');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','K.MADHUSHA','SIETK-ECE','1','12','3');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','K.ROOPA','SISTK-CSE','1','12','2');");
            db.execSQL("INSERT INTO BUS VALUES ('2NDYEAR','K.HEMASREE','SIETK-CSE','2','12','4');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYAER','V.KALYANI','SISTK-ECE','1','12','5');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','B.HARIKA','SISTK-ECE','1','12','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','G.NAGALAKSHMI','SISTK-CSE-HOD','','12','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','E.KATTHEEGA','SISTK-BSH','','12','8');");
            db.execSQL("INSERT INTO BUS VALUES ('','CHAMINI','SIETK','','12','9');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','M.SAILAJA','SIETK-EEE','','12','10');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','E.VIDYA PRIYA','SISTK-EEE','','12','11');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','G.YAMUNA','SIETK-MBA','','12','12');");
            db.execSQL("INSERT INTO BUS VALUES ('PA TO CHAIRMAN','D.PADMAJA','SISTK-SIETK','','12','13');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','A.RAJASEKHAR YADAV','SIETK-ECE','','12','14');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','M.CHIRANJEEVI','SIETK-T&P','','12','15');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.BALAJI','SIETK-CSE','','12','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','S.SIDDIRAJU','SIETK-CIVIL-HOD','','12','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','G.RAVI KUMAR','SISTK-CSE','','12','18');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','R.DINESH','SIETK-DME','1','12','19');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','K.MAHESH','SIETK-ECE','1','12','20');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','S.NAVVEN','SIETK-CIVIL','1','12','21');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','M.MUNISEKHAR','SIETK-ECE','1','12','22');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','E.MOUNISH','SIETK-DECE','1','12','23');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','M.DURGA PRASAD','SIETK-MECH','1','12','24');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','A.VAMSI','SIETK-DME','1','12','25');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','B.POORNA CHANDRA','SIETK-MECH','1','12','26');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','S.YASHWANTH SAI','SIETK-MECH','1','12','27');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','N.LAKSHMI PRANEETH','SIETK-MECH','1','12','28');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','I.VEDADHAR','SISTK-ECE','1','12','29');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','D.CHARAN KUMAR','SIETK-CSE','1','12','30');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','A.GURUPRASAD','SIETK-DEEE','1','12','31');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','G.GOWTHAM SAI','SIETK-MECH','1','12','32');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','V.MAHESH','SISTK-ECE','1','12','33');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','B.HARASHAVARD HAN','SIETK-DME','1','12','34');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','V.PAVAN KALYAN','SISTK-ECE','1','12','35');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','N.HARSHAVARDHAN','SIETK-ECE','1','12','36');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0451','P.IMARAN KHAN','SIETK-ECE','2','12','37');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-C-013','K.MADHU BABU','','','12','38');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-M-012','V.DHANUNJAYA','','','12','39');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-EE-013','P.T.RAHUL','','','12','40');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A05A7','V.SAIKRISHNA','SIETK-CSE','2','12','41');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0429','A.HEMANARAYAN YADAV','SIETK-ECE','2','12','42');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0562','G.LOHITHASYA','SIETK-CSE','2','12','43');");
            db.execSQL("INSERT INTO BUS VALUES ('1STYEAR','B.MAHESH BABU','SIETK-ME','1','12','44');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04K1','P.VINEETH','SIETK-ECE','2','12','45');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04G4','V.SIVA NARAYANA','SIETK-ECE','3','12','46');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0305','T.BALAJI SRI SESHA SAI','SIETK-MECH','3','12','47');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.LAVANYA','SIETK-CSE LAB','','12','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','DEVARAJULU','SIETK-CSE LAB','','12','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty', 'N.REVATHI','SIETK-ECE','0','13','1');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty', 'LYAKATH ','SIETK-CSE','0','13','2');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'G.RISHITHA','SIETK- ECE','1','13','3');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'D.GOWTHAMI','SIETK-ECE','1','13','4');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'K.PRATHYUSHA','SIETK-ECE','1','13','5');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'A.LOKESWARI ','SISTK-ECE','1','13','6');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0528', 'D.POOJITHA','SISTK- CSE','3','13','7');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'S.KRISHNAVENI','SISTK-ECE','1','13','8');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'A.MADHURI','SIETK-ECE','1','13','9');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'T.DEEPIKA','SIETK-CSE','1','13','10');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'A.ROJA','SISTK- ECE','1','13','11');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A05B8', 'Y.SONIYA','SIETK-CSE','2','13','12');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0535', 'B.GNANESWARI','SIETK-CSE`','2','13','13');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0522', 'D.DHANA LAKSHMI','SIETK-CSE','2','13','14');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0552', 'K.JHANSI','SIETK- CSE','2','13','15');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04K0', 'K.VISHALA','SIETK-ECE','3','13','16');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty', 'R.SURESH','SISTK-MECH','0','13','17');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty', 'D.KRISHNAIAH ','SIETK-MECH','0','13','18');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0502', 'M.ALEKYA','SIETK- CSE','2','13','19');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0146', 'A.NANDHINI','SIETK-CIVIL','3','13','20');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'K.SNIGDHA','SISTK-CSE','1','13','21');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty', 'B.VASANTH','SIETK-1-DIP.CIVIL','0','13','22');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty', 'M.BHANU KIRAN','SIETK-1-DIP.EEE','0','13','23');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0471', 'C.LIKHITHA','SIETK-ECE','2','13','24');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0484', 'D.MADHURI','SIETK-ECE','3','13','25');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04A0', 'C.LAVANYA','SIETK-ECE','4','13','26');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-C-024', 'M.TINKU RATNEESH',' ','2','13','27');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-EE-034', 'R.YASWANTH','EEE','2','13','28');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty', 'G.VEKATESWARLU','SIETK-MBA','0','13','29');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty', 'Dr.C.SURESH','SIETK-BS&H','0','13','30');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty', 'S.CHOWDAIAH','MCA-HOD','0','13','31');");
            db.execSQL("INSERT INTO BUS VALUES ('2NDst year', 'P.MOHAN','SIETK-IIMECH','2','13','32');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'KISHORE','SIETK-IMECH','1','13','33');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty', 'P.RAMESH BABU','SIETK-CSE','0','13','34');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'T.HEMANTH','SIETK-I-ECE','1','13','35');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'C.SAITEJA','SIETK-I-CIVIL','1','13','36');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0453', 'D.HEMANTH','SIETK-ECE','3','13','37');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0134', 'T.SAI RAMESH','SIETK-CIVIL','3','13','38');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0419', 'A.DILEEP','SISTK- ECE','2','13','39');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0409', 'Y.SRIKANTH','SIETK-ECE','2','13','40');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A05E0', 'K.YESWANTH','SIETK-CSE','2','13','41');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0362', 'Y.MUNI TEJA','SIETK-MECH','4','13','42');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0356', 'D.SYA PRASAD','SIETK- MECH','4','13','43');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61F0003', 'P.CHANDRA SEKHAR','SIETK-CIVIL','2','13','44');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0422', 'A.DHANA SEKHAR','SIETK-ECE','3','13','45');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0388', 'M.SAI ANIL','SIETK-MECH','2','13','46');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0377', 'S.RAJ KUMAR','SIETK- MECH','2','13','47');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty', 'J.HARITHA','SIETK-I-MCA','0','13','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0437', 'K.GURUHIMA BINDU','SIETK-ECE','3','13','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year', 'S.YASASWINI','SIETK-I-CSE','1','13','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty', 'K.SWARUPARANI','SIETK-MCA',' ','14','1');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0471', 'I.KEERTHANA','SIETK-ECE','3','14','2');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04B6)', 'P.POOJITHA','SIETK-ECE','2','14','3');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0414', 'V.CHAMANTHI','SISTK-ECE','2','14','4');");
            db.execSQL("INSERT INTO BUS VALUES ('Ist year','M.JYOTHIRMAYE','','1','14','5');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0565','M.MADHU SREE','SIETK-CSE','2','14','6');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty','P.CHANDRA MOULI','SIETK-CSE','0','14','7');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty','S.ARIF HUSSAIN','SISTK-CIVIL','0','14','8');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0450','M.HEMALATHA','SIETK-ECE','3','14','9');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0163','V.SAI NIHARIKA','SIETK-CIVIL','3','14','10');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0512','M.DIVYA','SISTK-CSE','3','14','11');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0495','V.PUNEETHA','SISTK-ECE','2','14','12');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04D1','KVL.SUPRIYA','SISTK-ECE','2','14','13');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0503','CH.BHARATHI','SISTK-CSE','2','14','14');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0494','J.PRIYANKA','SISTK-ECE','2','14','15');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0538','K.LAVANYA','SIETK-CSE','3','14','16');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04J6','D.VENKATA SAI KUMAR','SIETK-ECE','2','14','17');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0531','V.JEEVAN','SIETK-CSE','3','14','18');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0404',' V.ANUSHA','SIETK-ECE','4','14','19');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04J7','N.SRAVANI','SIETK-ECE','4','14','20');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0568','P.REVATHI','SIETK-CSE','4','14','21');");
            db.execSQL("INSERT INTO BUS VALUES ('14270-M-014 ','B.HAREESH YADAV','','3','14','22');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0361','C.NIKHIL KUMAR','SIETK-MECH','2','14','23');");
            db.execSQL("INSERT INTO BUS VALUES ('16F65A0302','G.DEVENDRA','SIETK-MECH','2','14','24');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty','O.KIRAN KISHORE','SIETK-CSE','','14','25');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty','C.BHASKAR','SISTK-EEE','','14','26');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A05D1','M.V.REVANTH','SIETK-CSE','2','14','27');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0501','S. AKHIL AHMAMED','SIETK-CSE','3','14','28');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04B0','B.SUDHEER','SISTK-ECE','2','14','29');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0245','P.SHIVA PRASAD','SIETK-EEE','3','14','30');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04E5','T.REVANTH','SIETK-ECE','3','14','31');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04K9','S.SUNIL','SIETK-ECE','4','14','32');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0322','K.FATHEY MOHAMMED','SIETK-MECH','3','14','33');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0448','T.HEMANTH','SIETK-ECE','2','14','34');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0593','T.VENKATESH','SIETK-CSE','3','14','35');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0373','D.RAJESH','SIETK-MECH','3','14','36');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0359','P.NAVEEN','SIETK-MECH','2','14','37');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A03E1','K.VINAY','SIETK-MECH','4','14','38');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0167','D.SOMASEKHAR','SIETK-CIVIL','2','14','39');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04F2','B.PRUDHIV REDDY','SIETK-ECE','4','14','40');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0382','C.RAJU','SIETK-MECH','4','14','41');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0439','M.DINESH KUMAR','SIETK-ECE','4','14','42');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0368','K.PAVAN KUMAR','SIETK-MECH','4','14','43');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0468','V.JAGADEESH','SIETK-ECE','4','14','44');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04B7','K.PARDHU','SIETK-ECE','3','14','45');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR', 'K.KASTURI','SIETK-MCA','1','15','1');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR', 'P.HARITHA','SIETK-ECE','1','15','2');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR', 'M.PRASANNA KUMARI','SIETK-ECE','1','15','3');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR', 'J.PRATHIJA','SISTK-ECE','1','15','4');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','S.GAYATHRI','','1','15','5');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','A.MUNI LAKSHMI','SIETK-CSE','1','15','6');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty','Dr.K.DHANALAKSHMI','SIETK-I-BS&H','','15','7');");
            db.execSQL("INSERT INTO BUS VALUES ('faculty','C.DHANALAKSHMI','SIETK-I-BSH','','15','8');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','V.SOWJA','SISTK-I-ECE','1','15','9');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','D.USHA','SIETK-I-CSE','1','15','10');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','T.PRIYANKA','SIETK-I-ECE','1','15','11');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K.RAMYA SREE','SIETK-I-CSE','1','15','12');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0409','P.BENNY SWETHA','SIETK-ECE','2','15','13');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0434','S.RESHMI LEHARIN','SISTK-CSE','3','15','14');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0579','T.SUNAINI','SIETK-CSE','3','15','15');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0591','K.SUSHMITHA','SIETK-CSE','4','15','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K.MONIKA','SIETK-I-CSE','1','15','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','V.HEMITHA','SIETK-I-ECE ','1','15','18');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','  T.ARUNA','SIETK-I-CSE','1','15','19');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0584','B.SINDHU PRIYA','SIETK-CSE','4','15','20');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0596','B.RASI PRIYA','SIETK-CSE','2','15','21');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','Dr.SUNEEL KUMAR REDDY','MECH-HOD','','15','22');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','VINODH KUMAR BALAJI','SIETK-CIVIL','','15','23');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','P.KRANTHI KUMAR','SIETK-I-DCE','1','15','24');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','CHANDU','SIETK-I-DEEE','1','15','25');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','T.M.VINAY','SIETK-I-DME','1','15','26');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','SOURABH SRIVASTAVA','SIETK-I-MECH','1','15','27');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','M.LOHITH VARDHAN SAI','SIETK-I-ECE','1','15','28');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','B.DINESH KUMAR','SIETK-I-ECE','1','15','29');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K.CHETHAN REDDY','SIETK-I-CSE','1','15','30');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','V.RAJESH','SIETK-I-EEE','1','15','31');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','M.SREEKANTH','SIETK-I-MCA','1','15','32');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','SAI KIRITY','SIETK-I-MBA','1','15','33');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','T.PRASANTH','SIETK-I-CSE','1','15','34');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','T.NAGENDRA BABU','SIETK-I-CSE','1','15','35');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','N.AADHIKESHAVA REDDY','SIETK-I-ECE','1','15','36');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04F5','S.NADEEM AHMED','SIETK-ECE','2','15','37');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-EE-011','K.GOWTHAM','SIETK-MECH','2','15','38');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','B.MUNI VENKATA SAI YASHWANTH','SIETK-I-ECE','1','15','39');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K.MUNI VARAPRASAD','SIETK-I-ECE','1','15','40');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0382','C.RAJU','SIETK-MECH','4','15','41');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K.SARAVANA','SIETK-I-CSE','1','15','42');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0329','GOWTHAM','SIETK-MECH','4','15','43');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0369','M.PRAKASH','SIETK-MECH','2','15','44');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0593','K.PRUDHVI NAIDU','SIETK-CSE','2','15','45');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0354','B.MUKTHESWAR','SIETK-MECH','2','15','46');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0499','J.NAVEEN','SIETK-ECE','2','15','47');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0381','M.SAGAR','SIETK-MECH','2','15','48');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR', 'A. SANDHYA','CSE, SIETK','1','16','1');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR', 'B. PALLAVI','ECE, SIETK','1','16','2');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR', 'G. ROSHINI','ECE, SIETK','1','16','3');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR', '. M. SWAPNA','CSE, SIETK','1','16','4');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','T. MALLIKA','    CSE, SIETK','1','16','5');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','A.K. FATHIMA RUMAISHA','CSE, SISTK','1','16','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','B.R. RAMYA','CSE','1','16','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K.J. SAI YASWANTH','ECE, SISTK','','16','8');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K. RADHA',' CSE, SIETK','1','16','9');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K. PREMA','ECE','1','16','10');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','A. MONISHA','CSE','1','16','11');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','N.PRAVALIKA','ECE','1','16','12');");
            db.execSQL("INSERT INTO BUS VALUES ('','','','','','');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K.JAYA KUMAR','CE','3','16','14');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K. ROHITH',' EEE','3','16','15');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','T. NAVEEN','ME','4','16','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','G BHARGAVI','SISTK-I-ECE','1','16','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','PRASANTHI','BS&H, SISTK ','','16','18');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','  E.AJITH KUMAR','ME, SIETK ','1','16','19');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','H. PRUDVI RAJ','CSE','4','16','20');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','M. KUMAR','EEE','2','16','21');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','M. MOULI KRISHNA','SIETK-I- CSE','','16','22');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','G. BANU ','SIETK-I-EEE','','16','23');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','B. PAVAN KUMAR','ME','1','16','24');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K. REDDI PRASAD','ECE','1','16','25');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','G. CHARAN','ECE','1','16','26');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','E.S. KALYAN KUMAR','E.CELL,SIETK','','16','27');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','M. SUBRAMANYAM','EEE, SIETK','','16','28');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','N. JITHESH KUMAR','ECE, SIETK','1','16','29');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K. PRANAYA KRISHNA',' ME','1','16','30');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','S.H. MANOJ','ME, SIETK','1','16','31');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','SUDHAKAR','ME SISTK HOD','','16','32');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','J. ROHITH KUMAR','ECE','1','16','33');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K SATHYA PRKACH','SIETK-I-CSE','1','16','34');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','A. VAMSI','CSE','1','16','35');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','I. ISHAS KHAN','ECE','1','16','36');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','P. VAMSI KRISHNA','CSE','2','16','37');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','A. GURU PRASAD','ECE','','16','38');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','P. ROHITH KUMAR','ECE','1','16','39');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','N. HARSHAVARADHAN','CSE','1','16','40');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRST YEAR','K.KANTHEESH','CSE-SIETK','4','16','41');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','K.DIVAKAR','SIETK','1','16','42');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','G.CHANDU','EEE','','16','43');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','.P PRAVEEN KUMAR ','SIETK-I-ECE','','16','44');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0439', 'K.JYOTHSNA','SISTK-ECE','2','17','1');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04H8', 'K.SWATHI','SIETK-ECE','2','17','2');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0250', 'N.SASIKALA','SIETK-EEE','2','17','3');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0529', 'S.DIVYA','SIETK-CSE','2','17','4');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0525','G.HARITHA','SIETK-CSE','3','17','5');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0408','N.BHARGAVI','SIETK-ECE','3','17','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','N.PRAKASH','SIETK-CSE',' ','17','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','A.J REUBEN THOMAS RAJ','SISTK-ECE',' ','17','8');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0464','T. HEMASRI','SIETK-ECE','4','17','9');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04E9','B.PRIYANKA','SIETK-ECE','4','17','10');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0470','N. SNEHA','SISTK-ECE','4','17','11');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','K.SUDHAKAR ','SIETK-MECH','  ','17','12');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY',' ',' ','  ','17','13');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','14');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','15');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ','  ','17','16');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','. K.GOWTHAM','SIETK-I-DECE','1','17','17');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0457','N.HARI','SIETK-ECE','4','17','18');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','19');");
            db.execSQL("INSERT INTO BUS VALUES ('',' ',' ',' ','17','20');");
            db.execSQL("INSERT INTO BUS VALUES ('',' ',' ',' ','17','21');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0536','C.GOPI','SIETK-CSE','2','17','22');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04F2','S.SARAVANA','SIETK-ECE','2','17','23');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0355','B.MURALI','SIETK-MECH','3','17','24');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0262','P.VINITH','SIETK-EEE','2 ','17','25');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0412','K.CHAITANYA','SISTK-ECE',' 2','17','26');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0338 ','J.LATHEESH','SIETK-MECH','3','17','27');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0141','V.MOHAN KUMAR','SIETK-CIVIL','3','17','28');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0398','S.SATHEESH','SIETK-MECH','4','17','29');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04A6','P.LOKESH','SIETK-ECE','4','17','30');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0316','A.HEMANTH KUMAR','SISTK-MECH','4','17','31');");
            db.execSQL("INSERT INTO BUS VALUES ('14F65A0209','P.SASI KIRAN','SIETK-EEE','3','17','32');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0466','V.JEEVAN KUMAR','SIETK-ECE','3','17','33');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','34');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','35');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','36');");
            db.execSQL("INSERT INTO BUS VALUES (' ', ' ',' ',' ','17','37');");
            db.execSQL("INSERT INTO BUS VALUES (' ', ' ',' ',' ','17','38');");
            db.execSQL("INSERT INTO BUS VALUES (' ', ' ',' ',' ','17','39');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','40');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','41');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','42');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','43');");
            db.execSQL("INSERT INTO BUS VALUES ('',' ',' ',' ','17','44');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','45');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','46');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','17','47');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0442', 'B.DIVYASREE','SIETK-ECE','4','18','1');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0490', 'V.KAVYASREE','SIETK-ECE','4','18','2');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0235', 'B.SOWMYA','SIETK-EEE','4','18','3');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0511', 'K.CHANDANA SREE','SIETK-CSE','3','18','4');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04G8','B. Srilatha','SIETK-ECE','3','18','5');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0414','P.BINDU','SIETK-ECE','3','18','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','D.SUDHAKAR','SISTK-MECH(HOD)',' ','18','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','R.KARTHIK','SIETK-T&P',' ','18','8');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','Dr.K.PRASANTHI','SISTK-BS&H',' ','18','9');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04E9','B.Priyanka','SIETK-ECE','4','18','10');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0408','N.Bhargavi','SIETK-ECE','3','18','11');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04A7','A.Ruchitha','SISTK-ECE','2','18','12');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY',' ',' ','  ','18','13');");
            db.execSQL("INSERT INTO BUS VALUES (' 134E1A0470','N. Sneha','SISTK-ECE','4','18','14');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0464','T. Hemasri','SIETK-ECE','4','18','15');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0407','M.R. Bhargavi','SIETK-ECE','3','18','16');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0448','K. Swathi','SIETK-ECE','2','18','17');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04E0','M.S Roshini','SIETK-ECE','2','18','18');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0589','G.Poojitha','SIETK-CSE','2','18','19');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04A3','K. Jyothsna','SISTK-ECE','2','18','20');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0561','B. Lalithya','SIETK-CSE','2','18','21');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','S.Reshma','SISTK-CIVIL',' ','18','22');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0250','K.Sasikala','SIETK-EEE','2','18','23');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04C1','M. Priyanka','SIETK-ECE','2','18','24');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0530','G.Nandini','SISTK-CSE','2 ','18','25');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04A3','A. Reddy Sree','SISTK-ECE',' 2','18','26');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0467 ','I.IRFAN KHAN','SIETK-ECE','4','18','27');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A04A6','P.LOKESH','SIETK-ECE','4','18','28');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0226','B.MANOJ KUMAR','SIETK-EEE','3','18','29');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0417','E.CHATHAN CHOWDARY','SIETK-ECE','3','18','30');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0557','D.PRANEETH','SIETK-CSE','3','18','31');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','18','32');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','18','33');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0316','C. Bhavani Sankar','SIETK-MECH','4','18','34');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0343','C. Mahesh Chowdary','SIETK-MECH','3','18','35');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0316','A.Hemanth Kumar','SISTK-MECH','4','18','36');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0325', 'G. Jagannadham','SIETK-MECH','2','18','37');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61E0005', 'G. Anil','SIETK-CIVIL','2','18','38');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0218', 'B.V. Kamal kumar','SIETK-EEE','3','18','39');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0536','C. Gopi','SIETK-CSE','2','18','40');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04k3','V.Vishnuprasad','SIETK-ECE','2','18','41');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04F2','S.Saravana','SIETK-ECE','2','18','42');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0528','P.Dinesh kumar','SIETK-CSE','2','18','43');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04B2','K.Pavan','SIETK-ECE','2','18','44');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0356','A.Nagendra','SIETK-MECH','3','18','45');");
            db.execSQL("INSERT INTO BUS VALUES ('134E1A0320','G.K.KIRAN KUMAR','SISTK-MECH','4','18','46');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','K.Goutham',' ',' ','18','47');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0536', 'A.LAKSHMI PRASANNA','SIETK-CSE','3','19','1');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0547', 'G.MUNEESWARI','SIETK-CSE','3','19','2');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0428','P.PRANATHI','SISTK-ECE','3','19','3');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'D.BHAGYASREE','SIETK-ECE','1','19','4');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','V.VEDHASHREE','SISTK-CSE','1','19','5');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','A.CHANDANA','SIETK-CSE','1','19','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','B.MOHINDER SINGH','SIETK-MCA',' ','19','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.BALAJI','SISTK-CSE',' ','19','8');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A05A9','S.SAI SNEHA','SIETK-CSE','2','19','9');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','S.GOWTHAMI','SISTK-ECE','1','19','10');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','V.JAHNAVI','SIETK-ECE','1','19','11');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','S.SURESH','SIETK-BS&H',' ','19','12');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR',' K.RAJESH','SISTK-CIVIL',' 1','19','13');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.SAI KUSUMA','SIETK-ECE',' ','19','14');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','V.R.SAI DEVAYANI','SISTK-CIVIL',' ','19','15');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','SUNITHA','SIETK-CSE',' ','19','16');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','N.PURUSHOTHAM','SIETK-ECE','1','19','17');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','G.MAHESH','SIETK-ECE','1','19','18');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-M-038','K.RUSHI SAI CHARAN REDY',' ',' ','19','19');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','N.VISHNU SAI REDDY','SIETK-CIVIL','1','19','20');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','V.CHAITANYA','SIETK-MECH',' ','19','21');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0304','Y.ASIF BASHA','SIETK-MECH','3','19','22');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','K.CHANDRASEKHAR VENKAT','SIETK-ECE','1','19','23');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','T.YASWANTH','SIETK-EEE','1','19','24');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','S.VENKATA SAI NAVEEN','SIETK-MECH','1 ','19','25');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','N.SANDEEP','SIETK-CSE',' 1','19','26');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','K.GNANESWAR','SIETK-DCE','1','19','27');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','M.DHANASEKHAR','SIETK-ECE','1','19','28');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','V.PAVAN KALYAN','SIETK-CSE','1','19','29');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','K.BALANANDH','SIETK-ECE','1','19','30');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','G.VENKATA SAI ROHITH','SIETK-CSE','1','19','31');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A03A9','K.UDAY SANKAR','SIETK-MECH','2','19','32');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04G1','M.SRAVAN KUMAR ','SIETK-ECE','2','19','33');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','M.SARATH BABU','SIETK-ECE','1','19','34');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','R.PAVAN ','SIETK-CSE','1','19','35');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','P.VINAY PADMAKAR','SISTK-ECE','1','19','36');");
            db.execSQL("INSERT INTO BUS VALUES ('3RD YEAR', 'A.SAI RAJ','SISTK-III-MECH','3','19','37');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0467', 'B.LAVAN REDDY','SIETK-ECE','2','19','38');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04E1', 'S.UPENDRA REDDY','SISTK-ECE','2','19','39');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0352','T.VENKATA SAI','SISTK-MECH','2','19','40');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR',' B.VIJAY REDDY','SIETK-ECE','1','19','41');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04I6','R.THEJESH','SIETK-ECE','3','19','42');");
            db.execSQL("INSERT INTO BUS VALUES ('3RD YEAR','B.VENKATA MOHIT KALYAN','SIETK-ECE','3','19','43');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0246','PS.PRADEEP','SIETK-EEE','2','19','44');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0526','G.DINAKAR','SIETK-CSE','2','19','45');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0324','A.EMMANUEL','SIETK-MECH','4','19','46');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0302','G.AKHIL','SIETK-MECH','4','19','47');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','V.CHAKRI REDDY','SIETK-DME','1','19','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','P.BHUVAN SANKAR','SISTK-ECE','1','19','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','N.THULASI RAM','SIETK-ECE','1','19','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','T.INDRA KIRAN','SIETK-CIVIL','1','19','EXCESS');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'V.JAYASREE',' ',' 1','20','1');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'D.SWETHA',' ','1','20','2');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','G.NAVEENA',' ','1','20','3');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR', 'SUDHA',' ','1','20','4');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','P.RAVALI',' ','1','20','5');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','M.POORNIMA',' ','1','20','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','SUDHAKAR','SIETK-BS&H',' ','20','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','N.DEEPAK KUMAR','SIETK-CSE',' ','20','8');");
            db.execSQL("INSERT INTO BUS VALUES ('',' ',' ',' ',' 20','9');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','20','10');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','20','11');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','ROJA','SISTK-ECE',' ','20','12');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY',' ASHA','SISTK-BS&H',' ','20','13');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','A.SIREESHA','PA-TO-PRINCE',' ','20','14');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','S.KAVITHA','SIETK-CIVIL',' ','20','15');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'R.NAGADEVI','SISTK-CSE',' ','20','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','K.SONIA','SIETK-EEE',' ','20','17');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','V.NITHYA','SIETK-EEE-LAB',' ','20','18');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','M. MANIKANTA','SIETK-CIVIL',' ','20','19');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-M-040','SAI KRISHNA',' ','2','20','20');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','Y.VISWANADH',' ','1','20','21');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0304','Y.ASIF BASHA','SIETK-MECH','3','20','22');");
            db.execSQL("INSERT INTO BUS VALUES ('2ND YEAR','G.K.VIVEK','SISTK-MECH','2','20','23');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','K.SAI KUMAR',' ','1','20','24');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','M.ANIL KUMAR',' ','1 ','20','25');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','SAI CHAITANYA',' ',' 1','20','26');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','YUGANDHAR',' ','1','20','27');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','B.SANTHOSH KUMAR',' ','1','20','28');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','S.SANDEEP',' ','1','20','29');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','SK.RUHULLA',' ','1','20','30');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','D.SAI KALYAN',' ','1','20','31');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0385','S.SAI KIRAN','SIETK-MECH','2','20','32');");
            db.execSQL("INSERT INTO BUS VALUES ('12F61A0301','V.AJAY KUMAR ','SIETK-MECH','4','20','33');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','K.CHAITANYA REDDY','SIETK-MECH','1','20','34');");
            db.execSQL("INSERT INTO BUS VALUES ('1ST YEAR','S.K.ASMUDULLA ',' ','1','20','35');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','20','36');");
            db.execSQL("INSERT INTO BUS VALUES (' ', ' ',' ',' ','20','37');");
            db.execSQL("INSERT INTO BUS VALUES (' ', ' ',' ',' ',' 20','38');");
            db.execSQL("INSERT INTO BUS VALUES (' ', ' ',' ',' ','20','39');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','20','40');");
            db.execSQL("INSERT INTO BUS VALUES ('',' ',' ','','20','41');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','20','42');");
            db.execSQL("INSERT INTO BUS VALUES (' ',' ',' ',' ','20','43');");
            db.execSQL("INSERT INTO BUS VALUES ('',' ',' ',' ','20','44');");
            db.execSQL("INSERT INTO BUS VALUES ('',' ',' ',' ','20','45');");
            db.execSQL("INSERT INTO BUS VALUES ('',' ',' ',' ','20','46');");
            db.execSQL("INSERT INTO BUS VALUES ('',' ',' ',' ','20','47');");
            db.execSQL("INSERT INTO BUS VALUES ('I year', 'G.Usha','SISTK-ECE','1','21','1');");
            db.execSQL("INSERT INTO BUS VALUES ('I year', 'G.LEEMA','SIETK-ECE','1','21','2');");
            db.execSQL("INSERT INTO BUS VALUES ('14270-C-027', 'PV.SREEDEVI','DCE','3','21','3');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0452','T.LEELA KUMARI','SISTK-ECE','2','21','4');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0241', 'K.RAMYA','SIETK-EEE','2','21','5');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0478', 'J.JAYASUDHA','SIETK-ECE','4','21','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'B.RAJANI','SIETK-EEE',' ','21','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY', 'B.VINODHINI','SIETK-BS&H',' ','21','8');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0467', 'A.MEGHANA','SISTK-ECE','2','21','9');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0425', 'N.HARITHA','SISTK-ECE','2','21','10');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0217', 'K.HIMAJA','SIETK-EEE','2','21','11');");
            db.execSQL("INSERT INTO BUS VALUES ('I-YEAR', 'P.THULASI','SIETK-ECE','1','21','12');");
            db.execSQL("INSERT INTO BUS VALUES ('I-YEAR', 'A.YAMINI','SIETK-ECE','1','21','13');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04F2', 'B.REKHA','SIETK-ECE','3','21','14');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04K7','A.YASWITHA','SIETK-ECE','3','21','15');");
            db.execSQL("INSERT INTO BUS VALUES ('14F65A0109','M.SIDHUJA','SIETK-MECH','4','21','16');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','K.BHARGAVI','SISTK-EEE','','21','17');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0546','K.SUSHMITHA','SISTK-CSE','2','21','18');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRSTYEAR','C.LAVANYA','SIETK-MCA','1','21','19');");
            db.execSQL("INSERT INTO BUS VALUES ('FIRSTYEAR','M.HARITHA','SIETK-MCA','1','21','20');");
            db.execSQL("INSERT INTO BUS VALUES ('','','','','','21');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','D.MADHU','SIETK-ECE','','21','22');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','M.MARIMUTTHU','SIETK-ECE','','21','23');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0470','K.MOHAN','SISTK-ECE','2','21','24');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0515','P.HARI','SISTK-CSE','2','21','25');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0592','B.PRAVEENKUMARREDDY','SIETK-CSE','2','21','26');");
            db.execSQL("INSERT INTO BUS VALUES ('I-YEAR','A KIRAN REDDY','SISTK-ECE','1','21','27');");
            db.execSQL("INSERT INTO BUS VALUES ('I-YEAR','G.JASWANTH','SIETK-ECE','1','21','28');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04B9','P.SHANMUGHAM','SISTK-ECE','2','21','29');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0342','T.LIKITH KUMAR','SIETK-MECH','2','21','30');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04D9','P.ROHITH','SIETK-ECE','2','21','31');");
            db.execSQL("INSERT INTO BUS VALUES ('I YEAR','K.CHAITANYA','SIETK-DEEE','1','21','32');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-ECE-003','P.CHARAN','SIETK-DECE','2','21','33');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0391','M.SARATH KUMAR','SIETK-MECH','2','21','34');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0125','K.HEMANTH KUMAR','SIETK-CE','3','21','35');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04B1','K.NAVEEN','SIETK-ECE','3','21','36');");
            db.execSQL("INSERT INTO BUS VALUES ('I YEAR','J.VAMSI','SIETK-DIP','1','21','37');");
            db.execSQL("INSERT INTO BUS VALUES ('I YEAR','J.VAMSI','SIETK-DIP','1','21','38');");
            db.execSQL("INSERT INTO BUS VALUES ('144E5A0103','N.S.SANKAR','SISTK-CE','4','21','39');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A04E7','A.RUPESH','SIETK-ECE','3','21','40');");
            db.execSQL("INSERT INTO BUS VALUES ('13F61A0484','P.JYOTHINADH','SIETK-ECE','4','21','41');");
            db.execSQL("INSERT INTO BUS VALUES ('I','D.SAI GANESH','SIETK-ECE','1','21','42');");
            db.execSQL("INSERT INTO BUS VALUES ('I','C.UDAY','SISTK-ECE','1','21','43');");
            db.execSQL("INSERT INTO BUS VALUES ('I','M.PRAVEEN KUMAR','SIETK-ECE','1','21','44');");
            db.execSQL("INSERT INTO BUS VALUES ('I','N.MUKESH','SIETK-MECH','1','21','45');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0140','P.NIHAR KUMAR','SIETK-CE','2','21','45');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0363','M.NITHIN KUMAR','SIETK-MECH','2','21','45');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0588','J.V.PAVITHRA','SIETK-CSE','2','22','1');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0257','P.THEJASWI','SIETK-EEE','2','22','2');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0521','T.CHARUMATHI','SIETK-CSE','2','22','3');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0403','R.RAMYA','SIETK-ECE','2','22','4');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04H6','T.SUSHMITHA','SIETK-ECE','2','22','5');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0569','K.B.MAMATHA','SIETK-CSE','2','22','6');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.G.LOGAN','SISTK-BS&H','','22','7');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','V.S.RAVI','SISTK-MECH','','22','8');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04B5','C.POOJITHA','SIETK-ECE','2','22','9');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0514','K.N.BHAVYA','SIETK-CSE','2','22','10');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0533','R.S.GEETHA','SIETK-CSE','2','22','11');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','Dr.V.M.DAYALAN','SISTK-BS&H','','22','12');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.M.VIJAYAN','SISTK-ECE','','22','13');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A05B7', 'D.SISINDRI','SIETK-CSE','2','22','14');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0405', 'M.BHANU PRIYA','SISTK-ECE','2','22','15');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0145', 'A.PAVITHRA','SIETK-CE','2','22','16');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0102', 'R.HARIKA','SIETK-CE','2','22','17');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0429', 'V.S.DIVYA','SIETK-ECE','2','22','18');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0102', 'R.ARIFFA','SIETK-CE','2','22','19');");
            db.execSQL("INSERT INTO BUS VALUES ('','','','','','20');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61F0008', 'V.VIJAYALAKSHMI','SIETK-CE','2','22','21');");
            db.execSQL("INSERT INTO BUS VALUES ('15270-ECE-004', 'S.W.DHANALAKSHMI','SIETK-DECE','2','22','22');");
            db.execSQL("INSERT INTO BUS VALUES ('I', 'B.BHAVANA','SIETK-I-DECE','1','22','23');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','E.HARI HARAN','SIETK-MCA','','22','24');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','C.P.GOPI','SIETK-MBA','','22','25');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','P.SESHADRI','SISTK-CIVIL','','22','26');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','S.SRUTHI','SISTK-ECE','','22','27');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','M.AMUDHA','SISTK-CSE','','22','28');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','MURALI','SIETK-LIBRARY','','22','29');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','ASHOK','SIETK-T&P','','22','30');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','R.G.KUMAR','SIETK-CSE','','22','31');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0516','P.HEMANADHA REDDY','SISTK-CSE','3','22','32');");
            db.execSQL("INSERT INTO BUS VALUES ('I','P.A.KISHOR KUMAR','SIETK-I-MECH','1','22','33');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','R.P.PANDU','SIETK-EEE','','22','34');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','S.K.GURU MURTHY','SIETK-MB','','22','35');");
            db.execSQL("INSERT INTO BUS VALUES ('FACULTY','Dr.VPN.PADANADHAN','SISTK-BS&H','','22','36');");
            db.execSQL("INSERT INTO BUS VALUES ('I-YEAR','B.E.PACHIAPPAN','SIETK-ECE','1','22','37');");
            db.execSQL("INSERT INTO BUS VALUES ('I-YEAR','J.SRINIVASAN','SISTK-EEE','1','22','38');");
            db.execSQL("INSERT INTO BUS VALUES ('I-YEAR','M.SAMPATH','SIETK-MBA','1','22','39');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0123','A.HEMANTH','SIETK-CE','2','22','40');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A04C3','M.N.PURUSHOTHAM','SIETK-ECE','2','22','41');");
            db.execSQL("INSERT INTO BUS VALUES ('15F61A0599','K.K.RAJ KUMAR','SIETK-CSE','2','22','42');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A0447','N.K.KUMAR','SISTK-ECE','2','22','43');");
            db.execSQL("INSERT INTO BUS VALUES ('','','','','','44');");
            db.execSQL("INSERT INTO BUS VALUES ('154E1A04F0','K.R.VIGNESH','SISTK-ECE','2','22','45');");
            db.execSQL("INSERT INTO BUS VALUES ('14F61A0128','M.JANARDHAN','SISTK-CE','2','22','46');");
            db.execSQL("INSERT INTO BUS VALUES ('144E1A0542','J.VINODH KUMAR','SISTK-CSE','2','22','47');");
            db.execSQL("INSERT INTO BUS VALUES('144E1A0430','J.PRIYANKA','SISTK-ECE','3','23','1');");
            db.execSQL("INSERT INTO BUS VALUES('I','B.M.MONISHA','SIETK-ECE','1','23','2');");
            db.execSQL("INSERT INTO BUS VALUES('I','V.PRIYANKA','SIETK-ECE','1','23','3');");
            db.execSQL("INSERT INTO BUS VALUES('14F61A0562','R.RAMYA','SIETK-ECE','3','23','4');");
            db.execSQL("INSERT INTO BUS VALUES('I YEAR','M.SOWMYA','SISTK-CSE','1','23','5');");
            db.execSQL("INSERT INTO BUS VALUES('13F61A0441','P.RAVALI','SIETK-ECE','4','23','6');");
            db.execSQL("INSERT INTO BUS VALUES('154E1A04C1','G.SHARMILA','SISTK-ECE','2','23','9');");
            db.execSQL("INSERT INTO BUS VALUES('LE','K.SIVA KUMARI','SIETK-LE-CIVIL','','23','10');");
            db.execSQL("INSERT INTO BUS VALUES('FACULTY','S.RAMESH','SISTK-EEE','','23','7');");
            db.execSQL("INSERT INTO BUS VALUES('FACULTY','A.VENU','SISTK-ECE','','23','8');");
            db.execSQL("INSERT INTO BUS VALUES('144E1A0533','P.SRUTHI','SISTK-CSE','3','23','11');");
            db.execSQL("INSERT INTO BUS VALUES('14F61A04E3','A.RESHMA','SIETK-ECE','3','23','12');");
            db.execSQL("INSERT INTO BUS VALUES('','','','','','13');");
            db.execSQL("INSERT INTO BUS VALUES('15F61A0436','P.GEETHANJALI','SIETK-ECE','2','23','14');");
            db.execSQL("INSERT INTO BUS VALUES('14F61A0599','M.YAMINI','SIETK-CSE','3','23','15');");
            db.execSQL("INSERT INTO BUS VALUES('14F61A0148','K.NAVYASREE','SIETK-CE','3','23','16');");
            db.execSQL("INSERT INTO BUS VALUES('FACULTY','D RAJESWARA RAJU','SIETK-BS&H','','23','17');");
            db.execSQL("INSERT INTO BUS VALUES('FACULTY','D PAYANI','SIETK-ECE-ATTEN','','23','18');");
            db.execSQL("INSERT INTO BUS VALUES('13F61A0443','K.DIVYASREE','SIETK-ECE','4','23','19');");
            db.execSQL("INSERT INTO BUS VALUES('15F61A05C0','R.SRINITHI','SIETK-CSE','2','23','20');");
            db.execSQL("INSERT INTO BUS VALUES('13F61A0461','M.HEMA','SIETK-ECE','4','23','21');");
            db.execSQL("INSERT INTO BUS VALUES('I YEAR','B.NITHIN','SISTK-DME','1','23','22');");
            db.execSQL("INSERT INTO BUS VALUES('I YEAR','K.YUVA KISHORE','SISTK-I-ECE','1','23','23');");
            db.execSQL("INSERT INTO BUS VALUES('14F61A0521','P.GAYATHRI','SIETK-CSE','3','23','24');");
            db.execSQL("INSERT INTO BUS VALUES('14F61A0230','K.MOSHI SAI','SIETK-EEE','3','23','25');");
            db.execSQL("INSERT INTO BUS VALUES('I YEAR','K.SAI TEJA','SISTK-I-CSE','1','23','26');");
            db.execSQL("INSERT INTO BUS VALUES('I YEAR','P.V.VENKATESH','SIETK-I-MECH','1','23','27');");
            db.execSQL("INSERT INTO BUS VALUES('I YEAR','N.BILVANADHAM','SIETK-I-EEE','1','23','28');");
            db.execSQL("INSERT INTO BUS VALUES('FACULTY','N.SRAVANTHI','SISTK-EEE','','23','29');");
            db.execSQL("INSERT INTO BUS VALUES('FACULTY','K.S.DEVASWARI','SIETK-ECE','','23','30');");
            db.execSQL("INSERT INTO BUS VALUES('FACULTY','E.SUJATHA','SISTK-EEE','','23','31');");
            db.execSQL("INSERT INTO BUS VALUES('I YEAR','P.SAI ABHISHAKE','SIETK-I-DME','1','23','32');");
            db.execSQL("INSERT INTO BUS VALUES('','','','','','33');");
            db.execSQL("INSERT INTO BUS VALUES('I YEAR','B.KUMARESHAN','SIETK-I-EEE','1','23','34');");
            db.execSQL("INSERT INTO BUS VALUES('I YEAR','V.PAVAN','SISTK-I-CSE','1','23','35');");
            db.execSQL("INSERT INTO BUS VALUES('154E1A0201','N.ARUNKUMAR','SISTK-EEE','2','23','36');");
            db.execSQL("INSERT INTO BUS VALUES('154E5A0401','E.CHIRANJEEVI','SISTK-ECE','2','23','37');");
            db.execSQL("INSERT INTO BUS VALUES('15F61F0010','S.MANIGANDAN','SIETK-MCA','2','23','38');");
            db.execSQL("INSERT INTO BUS VALUES('14F61A0339','K.V.LOKESH','SIETK-MECH','3','23','39');");
            db.execSQL("INSERT INTO BUS VALUES('12F61A05A2','D.H.THULASI GIRI','SIETK-CSE','4','23','40');");
            db.execSQL("INSERT INTO BUS VALUES('14F61A0317','P.DILIP KUMAR','SIETK-MEC','3','23','41');");
            db.execSQL("INSERT INTO BUS VALUES('','','','','','42');");
            db.execSQL("INSERT INTO BUS VALUES('','','','','','43');");
            db.execSQL("INSERT INTO BUS VALUES('','','','','','44');");
            db.execSQL("INSERT INTO BUS VALUES('','','','','','45');");
            db.execSQL("INSERT INTO BUS VALUES('','','','','','46');");
            db.execSQL("INSERT INTO BUS VALUES('','','','','','47');");


            db.execSQL("CREATE TABLE BUS_I (bus_no INT(2),name VARCHAR,stop VARCHAR,Route VARCHAR,contact INT);");
            db.execSQL("INSERT INTO BUS_I VALUES(1,'B.PAVAN (SIETK-CSE)','TPT-J','(MED.CLG->M.R.PALLI)','9704668399');");
            db.execSQL("INSERT INTO BUS_I VALUES(2,'N.RAJAGOPAL REDDY (SIETK-BS&H)','TPT','(MED.CLG->M.R.PALLI)','9701448196');");
            db.execSQL("INSERT INTO BUS_I VALUES(3,'M.P.SRINIVASULU (SIETK-MECH)','PUDI','(VP ARCH->PUDI)','9052309788');");
            db.execSQL("INSERT INTO BUS_I VALUES(4,'E.KARTHEEK (SIETK-BS&H)','TPT','(AVILAL->PDMTI.PRM)','9885771826');");
            db.execSQL("INSERT INTO BUS_I VALUES(5,'A.SESHAPPA (SIETK-MECH)','TPT-J','(ISKCON->SINDHU)','8099327096');");
            db.execSQL("INSERT INTO BUS_I VALUES(6,'K.BABY (SIETK-BS&H)','TPT','(ISKCON->M.OFFICE)','9966353759');");
            db.execSQL("INSERT INTO BUS_I VALUES(7,'Mr.G.SESHADRI (SIETK-EEE)','TPT','(M.OFFICE->K.L.M HOSPITAL)','9491311521');");
            db.execSQL("INSERT INTO BUS_I VALUES(8,'B.RAVINDRA NAICK (SIETK-CSE)','TPT','(KPLTRTM->LKSPRM)','9052739992');");
            db.execSQL("INSERT INTO BUS_I VALUES(9,'N.SREEDHAR (SISTK-MECH)','TPT-J','(KPLTRTM->LKSPRM)','9502504497');");
            db.execSQL("INSERT INTO BUS_I VALUES(10,'P.MANI MOHAN (SISTK-ECE)','TPT','(L.MAHAL->SINDHU)','9000220230');");
            db.execSQL("INSERT INTO BUS_I VALUES(11,'T.NAGARAJU (SISTK-ECE)','CHDGR','(CHDGR->V.NAGAR)',9441394392);");
            db.execSQL("INSERT INTO BUS_I VALUES(12,'G.RAVI KUMAR (SISTK-CSE)','RNGT','(RNGT->TADUKU)','9581997453');");
            db.execSQL("INSERT INTO BUS_I VALUES(12,'E.KEERTHIGA (SISTK-BS&H)','RNGT','(RNGT->TADUKU)','963140986');");
            db.execSQL("INSERT INTO BUS_I VALUES(13,'G.VENKATESWARLU(SIETK-MBA)','VGR','(VGR->VDMP)','9908704770');");
            db.execSQL("INSERT INTO BUS_I VALUES(14,'S.ARIF HUSSIAN (SISTK-CIVIL)','SKHT','(SKHT->SPONGE)','9441999236,8686768555');");
            db.execSQL("INSERT INTO BUS_I VALUES(15,'K.DHANA LAKSHMI (SIETK-BS&H)','SKHT-J','(SKHT->COK)','9032939778');");
            db.execSQL("INSERT INTO BUS_I VALUES(16,'E.S. KALYAN KUMAR (EXAM CELL SIETK)','CTR','(CTR->K.NAGARAM)','9440694395');");
            db.execSQL("INSERT INTO BUS_I VALUES(17,'N.PRAKASH (SIETK-CSE)','CTR','(CTR.DARGA->K.NAGARAM)','9652448081');");
            db.execSQL("INSERT INTO BUS_I VALUES(18,'S.RESHMA (SISTK-CIVIL)','CTR','(CTR.DARGA->K.NAGARAM)',' 9100450767');");
            db.execSQL("INSERT INTO BUS_I VALUES(19,'P.BALAJI (SISTK-CSE)','TPT','(S.QRTS->M.MANDI)','9885482633');");
            db.execSQL("INSERT INTO BUS_I VALUES(20,'N.DEEPAK KUMAR (SIETK-CSE)','STVD','(STVD->G.KNDRG)','9705840995');");
            db.execSQL("INSERT INTO BUS_I VALUES(21,'B.VINODHINI (SIETK-BS&H)','PNMR-c','(PMNR->K.NAGARAM)','9949065237');");
            db.execSQL("INSERT INTO BUS_I VALUES(22,'V.S.RAVI (SISTK- MECH)','KRIMBD','(KRIMBD->P.MANGALAM)','7207926248');");
            db.execSQL("INSERT INTO BUS_I VALUES(23,'A.VENU (SISTK-ECE)','PNNR','(SUB STN-NNDR->NAGARI)','9866631220');");
            db.close();
            return null;
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

