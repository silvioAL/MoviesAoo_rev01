package com.example.silvio.moviesaoo.viewholder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.silvio.moviesaoo.R;

/**
 * Created by silvioallgayertrindade on 16/03/2018.
 */

public class TrailerCardViewHolder extends RecyclerView.ViewHolder {

    String movieTrailerUri;
    ViewDataBinding binding;


    public TrailerCardViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);

    }

    public void bind(String trailerKey) {

        WebView webView = itemView.getRootView().findViewById(R.id.wbView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webView.canGoBack();
        webView.canGoForward();
        webView.loadUrl("http://www.youtube.com/embed/" + trailerKey);


    }

}
