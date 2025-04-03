package com.example.crud_demo;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText name, contact,dob;
    Button insert, update, delete,view;

    DBHelper DB; //8
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name); //1
        contact = findViewById((R.id.contact));//2
        dob = findViewById((R.id.dob));//3

        insert = findViewById(R.id.btnInsert); //4
        update = findViewById(R.id.btnupdate);//5
        delete = findViewById(R.id.btnDelete);//6
        view = findViewById(R.id.btnView);//7
        DB = new DBHelper(this); //8
        //9

        // Setting an OnClickListener for the 'insert' button
        insert.setOnClickListener(new View.OnClickListener() {
            // This method is called when the 'insert' button is clicked
            @Override
            public void onClick(View view) {
                // Get the text entered by the user in the 'name' EditText field and convert it to a String
                String nameTXT = name.getText().toString();

                // Get the text entered by the user in the 'contact' EditText field and convert it to a String
                String contactTXT = contact.getText().toString();

                // Get the text entered by the user in the 'dob' EditText field and convert it to a String
                String dobTXT = dob.getText().toString();

                // Call the insertuserdata method from the DB helper class, passing the values entered by the user
                // This method will insert the data into the database and return 'true' if the insert is successful, otherwise 'false'
                Boolean checkinsertdata = DB.insertuserdata(nameTXT, contactTXT, dobTXT);

                // If the data insertion is successful, show a success message in a Toast
                if (checkinsertdata == true) {
                    // Show a short Toast message indicating that the new entry was inserted successfully
                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    // If the insertion was not successful, show an error message in a Toast
                    Toast.makeText(MainActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });


        update.setOnClickListener(new View.OnClickListener() { //01 change insert -> update
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();

                Boolean checkupdatedata = DB.updateuserdata(nameTXT, contactTXT, dobTXT);
                // 02 change here to update method, and name checkinsertdata->checkupdatedata

                if (checkupdatedata == true) { //03
                    Toast.makeText(MainActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show(); //04 Entry Updated
                } else {
                    Toast.makeText(MainActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show(); //05
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() { //01 change update->delete
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                //String contactTXT = contact.getText().toString();
                //String dobTXT = dob.getText().toString();

                Boolean checkdeletedata = DB.deletedata(nameTXT); //omit , contactTXT, dobTXT, add checkdeletedata
                // 02 change here to update method, and name checkinsertdata->checkupdatedata

                if (checkdeletedata == true) { //03
                    Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show(); //04 Entry Updated
                } else {
                    Toast.makeText(MainActivity.this, "New Entry Not Deleted", Toast.LENGTH_SHORT).show(); //05
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() { //01 change delete->view
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No Entry Exisits", Toast.LENGTH_SHORT).show();
                    return;

                } //if clause
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Name :" + res.getString(0) + "\n");
                    buffer.append("Contact :" + res.getString(1) + "\n");
                    buffer.append("Date of Birth :" + res.getString(2) + "\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            } //onclick close
        });
    }
}