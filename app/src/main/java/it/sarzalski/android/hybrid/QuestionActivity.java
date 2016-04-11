package it.sarzalski.android.hybrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class QuestionActivity extends ActionBarActivity {

    public static final String INPUT_PARAMETER_GUID = "it.sarzalski.android.hybrid.QuestionActivity.IN_GUID";
    public static final String OUTPUT_PARAMETER_GUID = "it.sarzalski.android.hybrid.QuestionActivity.OUT_GUID";
    public static final String OUTPUT_PARAMETER_VALUE = "it.sarzalski.android.hybrid.QuestionActivity.OUT_VALUE";
    private String guid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        guid = intent.getStringExtra(INPUT_PARAMETER_GUID);
        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(guid);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText);
                Intent result = new Intent();
                result.putExtra(OUTPUT_PARAMETER_GUID, guid);
                result.putExtra(OUTPUT_PARAMETER_VALUE, editText.getText().toString());
                setResult(RESULT_OK, result);
                finish();
            }
        });

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
