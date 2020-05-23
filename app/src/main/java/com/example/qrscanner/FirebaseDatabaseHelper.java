package com.example.qrscanner;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceComponents;
    private List<Component> components = new ArrayList<>();

    public interface DataStatus {
        void dataIsLoaded(List<Component> components, List<String> keys);
        void dataIsInserted();
        void dataIsUpdated();
        void dataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceComponents = mDatabase.getReference();
    }

    public void readComponents(final DataStatus dataStatus) {
        mReferenceComponents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                components.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Component component = keyNode.getValue(Component.class);
                    components.add(component);
                }
                dataStatus.dataIsLoaded(components, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
