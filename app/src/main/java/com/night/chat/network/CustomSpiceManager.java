package com.night.chat.network;

import android.util.Log;

import com.night.chat.BuildConfig;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.SpiceService;

import roboguice.util.temp.Ln;

import static android.util.Log.*;

/**
 * Created by nightrain on 10/18/15.
 */
public class CustomSpiceManager extends SpiceManager {
    /**
     * Creates a {@link SpiceManager}. Typically this occurs in the construction
     * of an Activity or Fragment. This method will check if the service to bind
     * to has been properly declared in AndroidManifest.
     *
     * @param spiceServiceClass the service class to bind to.
     */
    public CustomSpiceManager(Class<? extends SpiceService> spiceServiceClass) {
        super(spiceServiceClass);
        Ln.getConfig().setLoggingLevel(VERBOSE);
    }
}
