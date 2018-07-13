package io.loot.lootsdk.utils;
public class RegexUtil {
    public static String generateInitial(String name) {
        if(name == null) {
            name = "";
        }
        String temp = name.replaceAll("((?!(?<=^|\\s)\\p{L}).)+", "");
        String initials = "";
        if (temp.length()>=1){
            initials += temp.charAt(0);
            if (temp.length()>1) {
                initials += temp.charAt(temp.length()-1);
            }
        }
        return initials.toUpperCase();
    }

    public static String generateNameToCompare(String name) {
        if(name == null) {
            name = "";
        }
        return name.replaceAll("(?=\\P{L})\\S+", "").toUpperCase().trim();
    }
}
