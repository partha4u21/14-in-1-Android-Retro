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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.multiplyone.handheld.ui.TabBaseFragment;

import org.jetbrains.annotations.NotNull;

/**
 * Adapter for the tabs
 */

public class TabsPagerAdapter extends FragmentStateAdapter {

    public TabBaseFragment[] fragmentList = new TabBaseFragment[]{
            InformationFragment.newInstance(),
            LicenseFragment.newInstance(),
            ChangeLogFragment.newInstance()
    };

    public TabsPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        if (position < fragmentList.length) {
            return fragmentList[position];
        }
        return InformationFragment.newInstance();
    }

    public String getTitle(int position) {
        if (fragmentList != null && position < fragmentList.length) {
            return fragmentList[position].getTitle();
        }
        return "";
    }

    @Override
    public int getItemCount() {
        if (fragmentList != null) {
            return fragmentList.length;
        }
        return 0;
    }
}