package com.example.mathematicsclassproject;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mathematicsclassproject.Recycler.Adapter;
import com.example.mathematicsclassproject.Recycler.Model;
import com.example.mathematicsclassproject.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseFirestore firestore;
    ArrayList<Model> chapterList;
    Adapter adapter;
    TextView quoteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore = FirebaseFirestore.getInstance();
        chapterList = new ArrayList<>();
        adapter = new Adapter(this, chapterList);
        binding.rcv.setAdapter(adapter);
        binding.rcv.setLayoutManager(new LinearLayoutManager(this));
        LoadData();

        NotificationHelper.createNotificationChannel(this);

        Button showButton = findViewById(R.id.showButton);
        showButton.setOnClickListener(view -> {
            NotificationHelper.showNotification(this, "Study Reminder", " Time to study! Let’s make progress together!");
        });

        binding.logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        quoteTextView = findViewById(R.id.quoteText);
        getMotivationalQuote(quoteTextView);
        updateQuotePeriodically();
    }

    private void LoadData() {
        chapterList.clear();
        firestore.collection("Chapter").orderBy("id", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Erreur de chargement des chapitres", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            chapterList.add(dc.getDocument().toObject(Model.class));
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
    }

    private void getMotivationalQuote(TextView quoteTextView) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://zenquotes.io/api/random";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject quoteObject = response.getJSONObject(0);
                        String quote = quoteObject.getString("q") + " — " + quoteObject.getString("a");
                        quoteTextView.setText(quote);
                    } catch (JSONException e) {
                        quoteTextView.setText("Erreur lors de l'analyse de la citation.");
                    }
                },
                error -> quoteTextView.setText("Impossible de charger la citation.")
        );

        queue.add(request);
    }

    private void updateQuotePeriodically() {
        final Handler handler = new Handler();
        Runnable updateTask = new Runnable() {
            @Override
            public void run() {
                getMotivationalQuote(quoteTextView);
                handler.postDelayed(this, 20000);
            }
        };
        handler.post(updateTask);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
}
