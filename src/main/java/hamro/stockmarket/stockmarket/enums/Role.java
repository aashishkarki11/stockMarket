package hamro.stockmarket.stockmarket.enums;

import io.micrometer.common.util.StringUtils;

import java.util.Optional;

/**
 * Enum representing roles in the stock market system. This enum defines two roles: USER
 * and ADMIN. It also provides a utility method to retrieve a role based on its string
 * value, converting it to uppercase. Author: [Aashish Karki]
 */
public enum Role {
  USER, ADMIN;

  /**
   * Retrieves a Role enum value based on the provided string value.
   *
   * @param value The string value representing a role.
   * @return An Optional containing the Role enum value if found, or empty if not found or
   * if the input value is empty.
   */
  public static Optional<Role> get(String value) {
    if (StringUtils.isEmpty(value))
      return Optional.empty();
    try {
      return Optional.of(Role.valueOf(value.toUpperCase()));
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }
}