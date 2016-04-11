package it.sarzalski.android.hybrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.pdfview.PDFView;


public class PdfReaderActivity extends ActionBarActivity {

    public static final String INPUT_PARAMETER_FILENAME = "it.sarzalski.android.hybrid.PdfReaderActivity.IN_FILENAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);

        Intent intent = getIntent();
        String fileName = intent.getStringExtra(INPUT_PARAMETER_FILENAME);

        PDFView pdfView = (PDFView) findViewById(R.id.pdfview);
        pdfView.fromAsset(fileName).defaultPage(1).load();

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
