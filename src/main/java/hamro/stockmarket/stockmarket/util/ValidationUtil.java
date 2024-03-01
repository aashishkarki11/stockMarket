package hamro.stockmarket.stockmarket.util;

/**
 * This is Util Class which is responsible for reusing the boilerplate code.
 * Author:[Aashish Karki]
 */
public class ValidationUtil {
  private ValidationUtil() {
  }

  /**
   * Checks if the provided value is null or empty.
   *
   * @param value The value to be checked.
   * @return True if the value is null or empty, otherwise false.
   */
  public static boolean checkIsNullAndEmpty(String value) {
    return value == null || value.isEmpty();
  }
}
