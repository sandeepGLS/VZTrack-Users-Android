package vztrack.gls.com.vztrack_user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

/**
 * Created by Santosh on 06-Oct-15.
 */
public class WebviewActivity extends AppCompatActivity {

    private static final FrameLayout.LayoutParams ZOOM_PARAMS =
            new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
    WebView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("VZTrack");
        setContentView(R.layout.web_view);
        view = (WebView) findViewById(R.id.wvForgetPassword);
        //webViewForgotPassword.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new WebViewClient());
        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBuiltInZoomControls(true);

        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath());
        settings.setDatabaseEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);
        settings.setSaveFormData(true);
        //     view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        //view.loadUrl("http://vimeo.com");


        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        view.setWebViewClient(new MyWebViewClient(this));
        String link = getResources().getString(R.string.forgot_password_link);
        view.loadUrl(link);

        view.setWebChromeClient(new WebChromeClient() {
            private ProgressDialog mProgress;

            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (mProgress == null) {
                    mProgress = new ProgressDialog(WebviewActivity.this);
                    mProgress.show();
                }
                mProgress.setMessage("Loading " + String.valueOf(progress) + "%");
                if (progress == 100) {
                    mProgress.dismiss();
                    mProgress = null;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               this.finish();
                break;
        }
        return true;
    }
}
