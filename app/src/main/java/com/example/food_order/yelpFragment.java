package com.example.food_order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

// Tutorial to use Webview in fragments
// https://www.youtube.com/watch?v=0Xf4Dz8tO6c&ab_channel=LearntoCodewithSasasushiq
// Tutorial for fragments https://www.youtube.com/watch?v=HZYYjY2NSKk&ab_channel=CodinginFlow
public class yelpFragment extends Fragment {
    private static final String ARG_URL = "url";

    private String url;

    public static yelpFragment newInstance(String url) {
        // Basically use this method to create fragment, pass in arguments into the OnCreate and pass the args
        yelpFragment fragment = new yelpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_yelp, container, false);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_URL);
        }
        WebView myWebView = myView.findViewById(R.id.yelp_page);
        myWebView.loadUrl(url);
        // Display page directly in app instead of actually opening it
        myWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return false;
//            }
        });
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Inflate the layout for this fragment
        return myView;
    }
}
