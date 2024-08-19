package com.eightaugusto.util

final class StringUtil {

  public static String WHITESPACE = " "
  public static String COMMA = ","

  static boolean isNotBlank(String arg) {
    return arg != null && !arg.isAllWhitespace()
  }

  static boolean isBlank(String arg) {
    return arg == null || arg.isAllWhitespace()
  }

}