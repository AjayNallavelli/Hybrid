package it.sarzalski.android.hybrid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;


public class PdfReaderActivity extends ActionBarActivity {

    public static final String INPUT_PARAMETER_FILENAME = "it.sarzalski.android.hybrid.PdfReaderActivity.IN_FILENAME";
    public static final String OUTPUT_PARAMETER_PAGES = "it.sarzalski.android.hybrid.PdfReaderActivity.OUT_PAGES";


    boolean doubleBackToExitPressedOnce = false;
    Integer numberOfPages = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);

        doubleBackToExitPressedOnce = false;

        Intent intent = getIntent();
        String fileName = intent.getStringExtra(INPUT_PARAMETER_FILENAME);

        PDFView pdfView = (PDFView) findViewById(R.id.pdfview);
        pdfView.fromAsset(fileName).defaultPage(1).onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int pages) {
                numberOfPages = pages;
            }
        }).load();

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.clickTwiceToExit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

        Intent result = new Intent();
        result.putExtra(OUTPUT_PARAMETER_PAGES, numberOfPages);
        setResult(RESULT_OK, result);
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
}
