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

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.multiplyone.handheld.R;
import com.multiplyone.handheld.ui.TabBaseFragment;

/*
 * Shows the GPL License, which is simply loaded from a webView. The About activity disables recreation
 * after orientation change, so don't need to handle that.
 */

public class LicenseFragment extends TabBaseFragment implements View.OnClickListener {

    public static LicenseFragment newInstance() {
        LicenseFragment fragment = new LicenseFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_about_tab2, container, false);

        WebView webView = (WebView) view.findViewById(R.id.about_tab2_webview);
        webView.loadUrl("file:///android_asset/license.html");

        return view;
    }

    @Override
    public void onClick(View v) {
        //nothing
    }

    @Override
    public String getTitle() {
        return "License";
    }
}