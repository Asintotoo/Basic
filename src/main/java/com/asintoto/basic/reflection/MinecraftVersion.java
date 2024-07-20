package com.asintoto.basic.reflection;

import org.bukkit.Bukkit;



public class MinecraftVersion {

    private static String serverVersion;
    private static V current;
    private static int subversion;

    public static int getSubversion() {
        return subversion;
    }

    public static V getCurrent() {
        return current;
    }

    public enum V {
        v1_22(22),
        v1_21(21),
        v1_20(20),
        v1_19(19),
        v1_18(18),
        v1_17(17),
        v1_16(16),
        v1_15(15),
        v1_14(14),
        v1_13(13),
        v1_12(12),
        v1_11(11),
        v1_10(10),
        v1_9(9),
        v1_8(8),
        v1_7(7),
        v1_6(6),
        v1_5(5),
        v1_4(4),
        v1_3_AND_BELOW(3);


        private final int minorVersionNumber;


        V(int version) {
            this.minorVersionNumber = version;
        }


        protected static V parse(int number) {
            for (final V v : values())
                if (v.minorVersionNumber == number)
                    return v;

            throw new RuntimeException();
        }


        @Override
        public String toString() {
            return "1." + this.minorVersionNumber;
        }
    }


    public static boolean equals(V version) {
        return compareWith(version) == 0;
    }


    public static boolean olderThan(V version) {
        return compareWith(version) < 0;
    }


    public static boolean newerThan(V version) {
        return compareWith(version) > 0;
    }


    public static boolean atLeast(V version) {
        return equals(version) || newerThan(version);
    }


    private static int compareWith(V version) {
        try {
            return getCurrent().minorVersionNumber - version.minorVersionNumber;

        } catch (final Throwable t) {
            t.printStackTrace();

            return 0;
        }
    }


    public static String getFullVersion() {
        return current.toString() + (subversion > 0 ? "." + subversion : "");
    }


    @Deprecated
    public static String getServerVersion() {
        return serverVersion.equals("craftbukkit") ? "" : serverVersion;
    }

    static {

        final String packageName = Bukkit.getServer() == null ? "" : Bukkit.getServer().getClass().getPackage().getName();
        final String curr = packageName.substring(packageName.lastIndexOf('.') + 1);
        serverVersion = !"craftbukkit".equals(curr) && !"".equals(packageName) ? curr : "";

        final String bukkitVersion = Bukkit.getServer().getBukkitVersion(); // 1.20.6-R0.1-SNAPSHOT
        final String versionString = bukkitVersion.split("\\-")[0]; // 1.20.6
        final String[] versions = versionString.split("\\.");

        final int version = Integer.parseInt(versions[1]); // 20

        current = version < 3 ? V.v1_3_AND_BELOW : V.parse(version);
        subversion = versions.length == 3 ? Integer.parseInt(versions[2]) : 0;

    }
}
