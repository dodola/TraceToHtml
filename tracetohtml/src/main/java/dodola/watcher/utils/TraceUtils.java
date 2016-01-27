package dodola.watcher.utils;

import java.util.List;

/**
 * Created by sunpengfei on 16/1/26.
 */
public class TraceUtils {
    static {
        System.loadLibrary("ndk");
    }

    public static native int analysisTraceFile(String path);
}
