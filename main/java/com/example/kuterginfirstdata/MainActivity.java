package com.example.kuterginfirstdata;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    SharedPreferences preferences;
    final String SAVED_TEXT = "saved_text";
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textVeiw);
        db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER)");
        db.execSQL("INSERT INTO users VALUES ('EGOR',7);");
        db.execSQL("INSERT INTO users VALUES ('DANIIL',18);");

        loadText();
    }

    void loadText(){
        preferences = getPreferences(MODE_PRIVATE);
        String savedText = preferences.getString(SAVED_TEXT, "");
        textView.setText(savedText);
        Toast.makeText(getApplicationContext(), "Text loaded", Toast.LENGTH_SHORT).show();

    }

    void saveText(){
        preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = preferences.edit();
        ed.putString(SAVED_TEXT, editText.getText().toString());
        ed.commit();
        Toast.makeText(getApplicationContext(), "Text saved", Toast.LENGTH_SHORT).show();
    }

    public void save(View view) {
        saveText();
        editText.setText("");
    }

    public void load(View view) {
        loadText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
        db.close();
    }

    public void LoadDB(View view) {
        Cursor query = db.rawQuery("SELECT * FROM users;", null);
        if(query.moveToFirst()){
            do{
                textView.append("\nName " + query.getString(0) + " Age " + query.getInt(1));
            } while (query.moveToNext());
        }
        query.close();
    }
}
