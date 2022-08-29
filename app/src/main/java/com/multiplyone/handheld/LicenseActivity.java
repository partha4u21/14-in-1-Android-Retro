package com.multiplyone.handheld;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Window;

import androidx.fragment.app.FragmentActivity;

import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.google.android.vending.licensing.ServerManagedPolicy;

import java.lang.ref.SoftReference;

public abstract class LicenseActivity extends FragmentActivity {

    //TODO
    //Insert License checker functions in this activity
}
