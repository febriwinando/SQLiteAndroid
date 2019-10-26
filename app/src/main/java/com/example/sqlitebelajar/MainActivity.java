package com.example.sqlitebelajar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editUsername, editMarks, editID;
    Button btnAddData, btnView, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        myDb = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.editText_name);
        editUsername = (EditText)findViewById(R.id.editText_username);
        editMarks = (EditText)findViewById(R.id.editText_marks);
        editID = (EditText)findViewById(R.id.editText_id);

        btnAddData = (Button)findViewById(R.id.button_add);
        btnView = (Button)findViewById(R.id.button_view);
        btnUpdate = (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById( R.id.button_delete );

        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }
    public void DeleteData(){
        btnDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = myDb.deleteData(editID.getText().toString());
                if (deleteRows > 0)
                    Toast.makeText(MainActivity.this, "Data Delete", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText( MainActivity.this, "Data Not Delete", Toast.LENGTH_LONG ).show();
            }
        } );
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(), editUsername.getText().toString(), editMarks.getText().toString());

                        if (isInserted = true)
                        {
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }

    public void viewAll(){
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount()==0){
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id : "+res.getString(0)+"\n");
                    buffer.append("Name : "+res.getString(1)+"\n");
                    buffer.append("Username : "+res.getString(2)+"\n");
                    buffer.append("Marks : "+res.getString(3)+"\n\n");
                }

                showMessage("Data", buffer.toString());

            }
        });
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void UpdateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(editID.getText().toString(), editName.getText().toString(), editUsername.getText().toString(), editMarks.getText().toString());
                if (isUpdate = true)
                {
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
