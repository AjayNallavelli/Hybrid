package it.sarzalski.android.hybrid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends ActionBarActivity {

    public static final int REQUEST_CODE_QUESTION = 1;
    public static final int REQUEST_CODE_PDFREADER = 2;

    public static final String PARAMETER_GUID = "guid";
    public static final String PARAMETER_FILE = "file";
    public static final String PREFIX_DEMO_QUESTION = "demo://question";
    public static final String PREFIX_DEMO_PDFREADER = "demo://pdfreader";
    public static final String INDEX_FILE = "file:///android_asset/index.html";

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.i(MainActivity.class.getSimpleName(), url);

                if (url.startsWith(PREFIX_DEMO_QUESTION)) {
                    UrlQuerySanitizer sanitizer = new UrlQuerySanitizer(url);
                    String guid = sanitizer.getValue(PARAMETER_GUID);
                    openQuestionActivity(guid);
                    return true;
                } else if (url.startsWith(PREFIX_DEMO_PDFREADER)) {
                    UrlQuerySanitizer sanitizer = new UrlQuerySanitizer(url);
                    String fileName = sanitizer.getValue(PARAMETER_FILE);
                    openPdfReaderActivity(fileName);
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
        };

        webView.addJavascriptInterface(new JavascriptObject(), "JO");

        webView.setWebViewClient(webViewClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(INDEX_FILE);
    }

    private void openQuestionActivity(String guid) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(QuestionActivity.INPUT_PARAMETER_GUID, guid);
        startActivityForResult(intent, REQUEST_CODE_QUESTION);
    }

    private void openPdfReaderActivity(String file) {
        Intent intent = new Intent(this, PdfReaderActivity.class);
        intent.putExtra(PdfReaderActivity.INPUT_PARAMETER_FILENAME, file);
        startActivityForResult(intent, REQUEST_CODE_PDFREADER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_QUESTION) {
            Log.i(MainActivity.class.getSimpleName(), String.valueOf(resultCode));

            if (resultCode == RESULT_OK) {
                String guid = data.getStringExtra(QuestionActivity.OUTPUT_PARAMETER_GUID);
                String value = data.getStringExtra(QuestionActivity.OUTPUT_PARAMETER_VALUE);
                Log.i(MainActivity.class.getSimpleName(), "Result OK" +  value);

                if (webView != null) {
                    webView.loadUrl("javascript:afterQuestion(\"" + guid + "\",\"" + value + "\");");
                }

            } else if (resultCode == RESULT_CANCELED) {
                Log.i(MainActivity.class.getSimpleName(), "Result CANCELED");
            }
        } else if (requestCode == REQUEST_CODE_PDFREADER) {
            if (resultCode == RESULT_OK) {
                int pages = data.getIntExtra(PdfReaderActivity.OUTPUT_PARAMETER_PAGES, -1);

                if (webView != null) {
                    webView.loadUrl("javascript:afterPdfReader(\"" + pages + "\");");
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class JavascriptObject {

        @JavascriptInterface
        public void openQuestion(String guid) {
            openQuestionActivity(guid);
        }

        @JavascriptInterface
        public void openPdfReader(String file) {
            openPdfReaderActivity(file);
        }

    }
}
