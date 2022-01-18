package Extrator.extratorLSG.api.utils;

public class Utils {
    public static String dateFormat(String data) {
        if (data == null) return null;
        String format = "";
        format = data.substring(4, 8) + "-" + data.substring(2, 4) + "-" + data.substring(0,2);

        return format;
    }

    public static String strFormat(String data) {

        if (data!= null) {
            if (data.equals("'")) data="";
            return data.replaceAll( "'", "''");
        }
        return null;
    }

    public static String intFormat(String data) {
        if (data.isEmpty()) return null;

        return data;

    }
}
