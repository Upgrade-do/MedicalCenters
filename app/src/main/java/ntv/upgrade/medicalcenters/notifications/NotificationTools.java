package ntv.upgrade.medicalcenters.notifications;

import android.os.Looper;
import android.text.TextUtils;

/**
 * Created by xeros on 9/2/2016.
 */

public class NotificationTools {

    public static <T> T zzy(T var0) {
        if (var0 == null) {
            throw new NullPointerException("null reference");
        } else {
            return var0;
        }
    }

    public static String zzcG(String var0) {
        if (TextUtils.isEmpty(var0)) {
            throw new IllegalArgumentException("Given String is empty or null");
        } else {
            return var0;
        }
    }

    public static String zzh(String var0, Object var1) {
        if (TextUtils.isEmpty(var0)) {
            throw new IllegalArgumentException(String.valueOf(var1));
        } else {
            return var0;
        }
    }

    public static <T> T zzb(T var0, Object var1) {
        if (var0 == null) {
            throw new NullPointerException(String.valueOf(var1));
        } else {
            return var0;
        }
    }

    public static int zza(int var0, Object var1) {
        if (var0 == 0) {
            throw new IllegalArgumentException(String.valueOf(var1));
        } else {
            return var0;
        }
    }

    public static int zzbX(int var0) {
        if (var0 == 0) {
            throw new IllegalArgumentException("Given Integer is zero");
        } else {
            return var0;
        }
    }

    public static void zzaa(boolean var0) {
        if (!var0) {
            throw new IllegalStateException();
        }
    }

    public static void zza(boolean var0, Object var1) {
        if (!var0) {
            throw new IllegalStateException(String.valueOf(var1));
        }
    }

    public static void zza(boolean var0, String var1, Object... var2) {
        if (!var0) {
            throw new IllegalStateException(String.format(var1, var2));
        }
    }

    public static void zzb(boolean var0, Object var1) {
        if (!var0) {
            throw new IllegalArgumentException(String.valueOf(var1));
        }
    }

    public static void zzb(boolean var0, String var1, Object... var2) {
        if (!var0) {
            throw new IllegalArgumentException(String.format(var1, var2));
        }
    }

    public static void zzab(boolean var0) {
        if (!var0) {
            throw new IllegalArgumentException();
        }
    }

    public static void zzcx(String var0) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException(var0);
        }
    }

    public static void zzcy(String var0) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException(var0);
        }
    }
}

