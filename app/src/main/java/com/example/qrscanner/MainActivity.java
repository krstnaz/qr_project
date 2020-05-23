package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FirebaseDatabaseHelper().readComponents(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void dataIsLoaded(List<Component> components, List<String> keys) {
                TableLayout components_table = findViewById(R.id.ComponentListTable);
                int count = components_table.getChildCount();
                for (int i = 1; i < count; i++) {
                    View child = components_table.getChildAt(i);
                    if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                }
                for (Component component : components) {
                    TableRow tr = new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    tr.setLayoutParams(lp);
                    TextView title = new TextView(getApplicationContext());
                    TextView vendor_code = new TextView(getApplicationContext());
                    title.setText(component.getTitle());
                    vendor_code.setText(component.getVendor_code());
                    tr.addView(title, 190, 60);
                    tr.addView(vendor_code, 190, 60);
                    components_table.addView(tr);
                }
            }

            @Override
            public void dataIsInserted() {

            }

            @Override
            public void dataIsUpdated() {

            }

            @Override
            public void dataIsDeleted() {

            }
        });

    }

    public void addComponentBtnListener(View view) {
        Intent intent = new Intent(this, AddComponent.class);
        startActivity(intent);
    }
}
