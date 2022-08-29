/*
 * Copyright (C) 2016  Tobias Bielefeld
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * If you want to contact me, send me an e-mail at tobias.bielefeld@gmail.com
 */

package com.multiplyone.handheld.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.util.Locale;

import com.google.android.material.card.MaterialCardView;
import com.multiplyone.handheld.BuildConfig;
import com.multiplyone.handheld.R;
import com.multiplyone.handheld.ui.TabBaseFragment;

/**
 * Shows some info about my app
 */

public class InformationFragment extends TabBaseFragment implements View.OnClickListener {

    private PrivacyBtnClickListener privacyBtnClickListener;
    private MaterialCardView privacyBtn;

    public static InformationFragment newInstance() {
        InformationFragment fragment = new InformationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_about_tab1, container, false);

        TextView about_text_build = (TextView) view.findViewById(R.id.about_text_build);
        TextView about_text_version = (TextView) view.findViewById(R.id.about_text_version);

        TextView aboutTextPulseBoyLicense = (TextView) view.findViewById(R.id.aboutTextPulseBoyLicense);
        TextView aboutTextBFXRLicense = (TextView) view.findViewById(R.id.aboutTextBFXRLicense);
        aboutTextPulseBoyLicense.setMovementMethod(LinkMovementMethod.getInstance());
        aboutTextBFXRLicense.setMovementMethod(LinkMovementMethod.getInstance());

//        String buildDate =  DateFormat.getDateInstance().format(BuildConfig.TIMESTAMP);             //get the build date in locale time format
//
//        if (about_text_build!=null && about_text_version!=null) {
//            about_text_version.setText(String.format(Locale.getDefault(),"%s: %s",
//                    getString(R.string.app_version),BuildConfig.VERSION_NAME));
//            about_text_build.setText(String.format(Locale.getDefault(), "%s: %s",
//                    getResources().getString(R.string.about_build_date), buildDate));
//        }

        TextView aboutTextViewGitHubLink = (TextView) view.findViewById(R.id.aboutTextViewGitHubLink);
        aboutTextViewGitHubLink.setMovementMethod(LinkMovementMethod.getInstance());


        privacyBtn = view.findViewById(R.id.privacy_card);
        privacyBtnClickListener = new PrivacyBtnClickListener(new SoftReference<>(this));
        privacyBtn.setOnClickListener(privacyBtnClickListener);
        return view;
    }

    @Override
    public void onClick(View v) {
        //nothing
    }

    private static class PrivacyBtnClickListener implements View.OnClickListener {

        private SoftReference<Fragment> fragmentSoftReference;

        public PrivacyBtnClickListener(SoftReference<Fragment> fragmentSoftReference) {
            this.fragmentSoftReference = fragmentSoftReference;
        }

        @Override
        public void onClick(View v) {
            String url = "https://partha4u21.github.io/handheld_docs/handheld/privacy_policy.html";
            launchLinkInBrowser(fragmentSoftReference, url);
        }
    }

    private static void launchLinkInBrowser(SoftReference<Fragment> fragmentSoftReference,
                                            String url) {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        fragmentSoftReference.get().startActivity(i);
    }

    @Override
    public String getTitle() {
        return "Information";
    }

    @Override
    public void onDestroy() {
        if (privacyBtn != null) {
            privacyBtn.setOnClickListener(null);
            privacyBtnClickListener = null;
        }
        super.onDestroy();
    }
}