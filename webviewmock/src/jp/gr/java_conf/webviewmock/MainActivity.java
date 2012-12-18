package jp.gr.java_conf.webviewmock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends Activity {

	private WebView webView = null;
	private EditText editText = null;
	private static final String KEY_PREFS_JAVASCRIPT_ENABLED = "javascriptEnabled";
	
    private void prepareWebView() {
		webView = (WebView) findViewById(R.id.webView);
		webView.setWebViewClient(new WebViewClient());
	}

	private void prepareEditText() {
		editText = (EditText) findViewById(R.id.enterUrl);
		editText.setOnKeyListener(new OnKeyListener(){
			@Override
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& keyCode == KeyEvent.KEYCODE_ENTER) {
					enterUrl(view);
					return true;
				}
				return false;
			}
		});
	}

	private void restoreSettings() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    boolean javascriptEnabled = prefs.getBoolean(KEY_PREFS_JAVASCRIPT_ENABLED, true);
	    webView.getSettings().setJavaScriptEnabled(javascriptEnabled);
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main);
 
        prepareEditText();
    	
    	prepareWebView();
    	restoreSettings();
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onResume() {
    	super.onResume();
        restoreSettings();
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    	return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
        		&& keyCode == KeyEvent.KEYCODE_BACK
                && webView.canGoBack() == true ) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void enterUrl(View view) {
    	String url = editText.getText().toString();
    	webView.loadUrl(url);
    }
}