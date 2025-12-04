package com.example.mathematicsclassproject;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.mathematicsclassproject.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String thumbnail = getIntent().getStringExtra("img");
        String pdfLink = getIntent().getStringExtra("pdfLink");
        String videoLink = getIntent().getStringExtra("videoLink");

        if (thumbnail != null && !thumbnail.isEmpty()) {
            Glide.with(this).load(thumbnail).into(binding.thumbnail);
        }

        binding.play.setOnClickListener(v -> {
            if (videoLink != null && !videoLink.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vidéo non disponible", Toast.LENGTH_SHORT).show();
            }
        });


        if (pdfLink != null && !pdfLink.isEmpty()) {
            loadPdfInWebView(pdfLink);
        } else {
            Toast.makeText(this, "PDF non disponible", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPdfInWebView(String pdfUrl) {
        String googleDocsUrl = "https://docs.google.com/gview?embedded=true&url=" + pdfUrl;

        WebView webView = binding.webView;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(googleDocsUrl);
    }
}
