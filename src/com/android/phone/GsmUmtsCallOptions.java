/*
 * Copyright (c) 2011-2014 The Linux Foundation. All rights reserved.
 * Not a Contribution.
 *
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.phone;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.MenuItem;

import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;

import static com.android.internal.telephony.PhoneConstants.SUBSCRIPTION_KEY;

public class GsmUmtsCallOptions extends PreferenceActivity {
    private static final String LOG_TAG = "GsmUmtsCallOptions";
    private final boolean DBG = (PhoneGlobals.DBG_LEVEL >= 2);

    private static final String BUTTON_CF_EXPAND_KEY = "button_cf_expand_key";
    private static final String BUTTON_MORE_EXPAND_KEY = "button_more_expand_key";

    private PreferenceScreen subscriptionPrefCFE;

    private Phone mPhone;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.gsm_umts_call_options);

        // getting selected subscription
        mPhone = PhoneUtils.getPhoneFromIntent(getIntent());

        // setting selected subscription for GsmUmtsCallForwardOptions.java
        subscriptionPrefCFE  = (PreferenceScreen) findPreference(BUTTON_CF_EXPAND_KEY);
        subscriptionPrefCFE.getIntent().putExtra(SUBSCRIPTION_KEY, mPhone.getSubId());
        // setting selected subscription for GsmUmtsAdditionalCallOptions.java
        PreferenceScreen subscriptionPrefAdditionSettings =
                (PreferenceScreen) findPreference(BUTTON_MORE_EXPAND_KEY);
        subscriptionPrefAdditionSettings.getIntent().putExtra(SUBSCRIPTION_KEY, mPhone.getSubId());

        Log.d(LOG_TAG, "Getting GsmUmtsCallOptions subscription =" + mPhone.getSubId());

        if (mPhone.getPhoneType() != PhoneConstants.PHONE_TYPE_GSM) {
            Log.d(LOG_TAG, "Non GSM Phone!");
            //disable the entire screen
            getPreferenceScreen().setEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
