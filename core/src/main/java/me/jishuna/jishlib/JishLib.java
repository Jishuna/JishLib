package me.jishuna.jishlib;

import me.jishuna.jishlib.adapter.Adapter;
import me.jishuna.jishlib.adapter.BukkitFallbackAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JishLib {
    private static JishlibPlugin instance;
    private static Adapter adapter;

    public static JishlibPlugin instance() {
        return instance;
    }

    public static Logger logger() {
        return instance().getLogger();
    }

    public static Adapter adapter() {
        if (adapter == null) {
            adapter = initAdapter();
            logger().log(Level.INFO, "Active adapter: {0}", adapter.getAdapterName());
        }

        return adapter;
    }

    static void setInstance(JishlibPlugin instance) {
        JishLib.instance = instance;
    }

    private static Adapter initAdapter() {
        return new BukkitFallbackAdapter();
    }
}
