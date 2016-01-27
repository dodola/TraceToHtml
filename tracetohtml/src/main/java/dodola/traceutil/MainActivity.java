package dodola.traceutil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

import dodola.watcher.utils.TraceUtils;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) || Intent.ACTION_VIEW.equals(action)) {
            Uri data = intent.getData();
            if (data != null) {
                processTrace(data.getEncodedPath());
            }
        }

    }

    private void processTrace(final String filePath) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void...params) {
                TraceUtils.analysisTraceFile(filePath);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.hide();
                Uri uri = Uri.fromFile(new File(filePath + ".html"));
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("html", uri.toString());
                startActivity(intent);
                finish();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }
        };
        task.execute();
    }
}
