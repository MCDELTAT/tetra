package csusb.cse.tetra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView myWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
<<<<<<< HEAD
        myWebView.loadUrl("http://104.236.178.168/index2.html");
=======
        myWebView.loadUrl("https://google.com");
>>>>>>> 746cd26bfbc07e84d6fe6d85f12139187984918d
    }
}
