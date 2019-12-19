package com.example.androidart.messenger;

import android.os.Bundle;

class Shared {

    static final int MSG_WHAT = 0x0f;

    static final String KEY_TEXT = "key_text";

    @Deprecated static Bundle wrapWithBundle(String string) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TEXT, string);
        return bundle;
    }
}
