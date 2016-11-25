/*
 * MIT License
 *
 * Copyright (c) 2016 okosheep
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package info.okoshi.trifulx;

import java.io.Serializable;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The trifulx value parser.<br>
 *
 * @version 1.0.0
 * @author okosheep
 */
@ToString
@EqualsAndHashCode
public class Value implements Serializable {

  /** Constants of empty string */
  private static final String EMPTY = "";

  /** Serial version */
  private static final long serialVersionUID = 3665368380556518285L;

  /**
   * Convert to {@link Value} as {@link Boolean}.<br>
   *
   * @param booleanValue
   *          {@link Boolean} value
   * @return {@link Value} object
   */
  public static Value valueOf(Boolean booleanValue) {
    return valueOf(Optional.ofNullable(String.valueOf(booleanValue.booleanValue())));
  }

  /**
   * Convert to {@link Value} as {@link Byte}.<br>
   *
   * @param byteValue
   *          {@link Byte} value
   * @return {@link Value} object
   */
  public static Value valueOf(Byte byteValue) {
    return valueOf(Optional.ofNullable(String.valueOf(byteValue.byteValue())));
  }

  /**
   * Convert to {@link Value} as {@link Character}.<br>
   *
   * @param characterValue
   *          {@link Character} value
   * @return {@link Value} object
   */
  public static Value valueOf(Character characterValue) {
    return valueOf(Optional.ofNullable(String.valueOf(characterValue.charValue())));
  }

  /**
   * Convert to {@link Value} as {@link Class} type.<br>
   *
   * @param clazz
   *          {@link Class} type value
   * @return {@link Value} object
   */
  public static Value valueOf(Class<?> clazz) {
    return valueOf(Optional.ofNullable(clazz.getName()));
  }

  /**
   * Convert to {@link Value} as {@link Double}.<br>
   *
   * @param doubleValue
   *          {@link Double} value
   * @return {@link Value} object
   */
  public static Value valueOf(Double doubleValue) {
    return valueOf(Optional.ofNullable(String.valueOf(doubleValue.doubleValue())));
  }

  /**
   * Convert to {@link Value} as {@link Float}.<br>
   *
   * @param floatValue
   *          {@link Float} value
   * @return {@link Value} object
   */
  public static Value valueOf(Float floatValue) {
    return valueOf(Optional.ofNullable(String.valueOf(floatValue.floatValue())));
  }

  /**
   * Convert to {@link Value} as {@link Integer}.<br>
   *
   * @param integerValue
   *          {@link Integer} value
   * @return {@link Value} object
   */
  public static Value valueOf(Integer integerValue) {
    return valueOf(Optional.ofNullable(String.valueOf(integerValue.intValue())));
  }

  /**
   * Convert to {@link Value} as {@link Long}.<br>
   *
   * @param longValue
   *          {@link Long} value
   * @return {@link Value} object
   */
  public static Value valueOf(Long longValue) {
    return valueOf(Optional.ofNullable(String.valueOf(longValue.longValue())));
  }

  /**
   * Convert to {@link Value} as {@link Short}.<br>
   *
   * @param shortValue
   *          {@link Short} value
   * @return {@link Value} object
   */
  public static Value valueOf(Short shortValue) {
    return valueOf(Optional.ofNullable(String.valueOf(shortValue.shortValue())));
  }

  /**
   * Convert to {@link Value} as {@link String}.<br>
   *
   * @param stringValue
   *          {@link String} value
   * @return {@link Value} object
   */
  public static Value valueOf(String stringValue) {
    return valueOf(Optional.ofNullable(stringValue));
  }

  /**
   * Convert to {@link Value} as {@link Enum} type.<br>
   *
   * @param enumValue
   *          {@link Enum} type value
   * @return {@link Value} object
   */
  public static <T extends Enum<T>> Value valueOf(T enumValue) {
    return valueOf(Optional.ofNullable(String.valueOf(enumValue.name())));
  }

  /**
   * Convert to {@link Value} as {@link Optional}.<br>
   *
   * @param optionalStringValue
   *          {@link Optional} value
   * @return {@link Value} object
   */
  static Value valueOf(Optional<String> optionalStringValue) {
    if (optionalStringValue.isPresent()) {
      return new Value(optionalStringValue.get(), true);
    }
    return new Value(null, false);
  }

  /** Existence */
  private boolean exists;

  /** The value */
  private String value;

  /**
   * Create {@link Value} instance.<br>
   *
   * @param value
   *          Value
   * @param exists
   *          Existence
   */
  private Value(String value, boolean exists) {
    this.value = value;
    this.exists = exists;
  }

  /**
   * Activate class type.<br>
   *
   * @return {@link Class} object
   * @throws ParseException
   *           Value is empty
   * @throws ClassNotFoundException
   *           Class name is not found in this class loader
   */
  public Class<?> activateClass() throws ParseException, ClassNotFoundException {
    guard();
    return Class.forName(value);
  }

  /**
   * Parse value as {@link Boolean}.<br>
   *
   * @return Value
   * @throws ParseException
   *           Value is empty
   */
  public Boolean booleanValue() throws ParseException {
    guard();
    return Boolean.valueOf(value);
  }

  /**
   * Parse value as {@link Byte}.<br>
   *
   * @return Value
   * @throws ParseException
   *           Value is empty
   */
  public Byte byteValue() throws ParseException {
    guard();
    return Byte.valueOf(value);
  }

  /**
   * Parse value as {@link Character}.<br>
   *
   * @return Value
   * @throws ParseException
   *           Value is empty
   */
  public Character characterValue() throws ParseException {
    guard();
    return Character.valueOf(value.charAt(0));
  }

  /**
   * Parse value as {@link Double}.<br>
   *
   * @return Value
   * @throws ParseException
   *           Value is empty
   */
  public Double doubleValue() throws ParseException {
    guard();
    return Double.valueOf(value);
  }

  /**
   * Parse value as {@link Enum} type.<br>
   *
   * @return Value
   * @throws ParseException
   *           Value is empty
   */
  public <T extends Enum<T>> T enumValue(Class<T> enumType, T defaultValue) throws ParseException {
    if (enumType == null) {
      return defaultValue;
    }
    try {
      guard();
      return Enum.valueOf(enumType, value);
    } catch (IllegalArgumentException e) {
      return defaultValue;
    }
  }

  /**
   * Existence.<br>
   *
   * @return <code>true</code> means exist, otherwise is <code>false</code>
   */
  public boolean exists() {
    return exists;
  }

  /**
   * Parse value as {@link Float}.<br>
   *
   * @return Value
   * @throws ParseException
   *           Value is empty
   */
  public Float floatValue() throws ParseException {
    guard();
    return Float.valueOf(value);
  }

  /**
   * Parse value as {@link Integer}.<br>
   *
   * @return Value
   * @throws ParseException
   *           Value is empty
   */
  public Integer integerValue() throws ParseException {
    guard();
    return Integer.valueOf(value);
  }

  /**
   * Parse value as {@link Long}.<br>
   *
   * @return Value
   * @throws ParseException
   *           Value is empty
   */
  public Long longValue() throws ParseException {
    guard();
    return Long.valueOf(value);
  }

  /**
   * Create new instance from element value.<br>
   *
   * @param type
   *          java type
   * @return object
   * @throws InstantiationException
   *           instantiation failed
   * @throws IllegalAccessException
   *           illegal access
   * @throws ParseException
   *           can't parse
   * @throws ClassNotFoundException
   *           class not found
   */
  @SuppressWarnings("unchecked")
  public <T> T newInstance(Class<T> type)
      throws InstantiationException, IllegalAccessException, ParseException, ClassNotFoundException {
    Class<?> clazz = activateClass();
    return (T) clazz.newInstance();
  }

  /**
   * Reference another node.<br>
   * Value is must be dot separated object graph.<br>
   *
   * @param node
   *          {@link Node} for evaluate by value
   * @return Referenced {@link Node} object
   */
  public Node ref(Node node) {
    String[] names = value.split("\\.");
    for (String name : names) {
      node = node.tryNode(name);
    }
    return node;
  }

  /**
   * Parse value as {@link Short}.<br>
   *
   * @return Value
   * @throws ParseException
   *           Value is empty
   */
  public Short shortValue() throws ParseException {
    guard();
    return Short.valueOf(value);
  }

  /**
   * Parse value as {@link String}.<br>
   *
   * @return Value
   * @throws ParseException
   *           Value is null
   */
  public String stringValue() throws ParseException {
    if (value == null) {
      throw new ParseException("Can't parse null value.");
    }
    return value;
  }

  /**
   * Guard for empty value.<br>
   *
   * @throws ParseException
   *           Value is empty
   */
  private void guard() throws ParseException {
    if (value == null || EMPTY.equals(value)) {
      throw new ParseException("Can't parse empty value.");
    }
  }
}
