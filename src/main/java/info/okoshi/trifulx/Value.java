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

import java.util.Optional;

/**
 * The trifulx value parser.<br>
 *
 * @version 1.0.0
 * @author okosheep
 */
public class Value {

  private static final String EMPTY = "";

  public static Value valueOf(Boolean booleanValue) {
    return valueOf(Optional.ofNullable(String.valueOf(booleanValue.booleanValue())));
  }

  public static Value valueOf(Byte byteValue) {
    return valueOf(Optional.ofNullable(String.valueOf(byteValue.byteValue())));
  }

  public static Value valueOf(Character characterValue) {
    return valueOf(Optional.ofNullable(String.valueOf(characterValue.charValue())));
  }

  public static Value valueOf(Class<?> clazz) {
    return valueOf(Optional.ofNullable(clazz.getName()));
  }

  public static Value valueOf(Double doubleValue) {
    return valueOf(Optional.ofNullable(String.valueOf(doubleValue.doubleValue())));
  }

  public static Value valueOf(Float floatValue) {
    return valueOf(Optional.ofNullable(String.valueOf(floatValue.floatValue())));
  }

  public static Value valueOf(Integer integerValue) {
    return valueOf(Optional.ofNullable(String.valueOf(integerValue.intValue())));
  }

  public static Value valueOf(Long longValue) {
    return valueOf(Optional.ofNullable(String.valueOf(longValue.longValue())));
  }

  public static Value valueOf(Short shortValue) {
    return valueOf(Optional.ofNullable(String.valueOf(shortValue.shortValue())));
  }

  public static Value valueOf(String stringValue) {
    return valueOf(Optional.ofNullable(stringValue));
  }

  public static <T extends Enum<T>> Value valueOf(T enumValue) {
    return valueOf(Optional.ofNullable(String.valueOf(enumValue.name())));
  }

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

  private Value(String value, boolean exists) {
    this.value = value;
    this.exists = exists;
  }

  public Class<?> activateClass() throws ParseException, ClassNotFoundException {
    guard();
    return Class.forName(value);
  }

  public Boolean booleanValue() throws ParseException {
    guard();
    return Boolean.valueOf(value);
  }

  public Byte byteValue() throws ParseException {
    guard();
    return Byte.valueOf(value);
  }

  public Character characterValue() throws ParseException {
    guard();
    return Character.valueOf(value.charAt(0));
  }

  public Double doubleValue() throws ParseException {
    guard();
    return Double.valueOf(value);
  }

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

  public boolean exists() throws ParseException {
    return exists;
  }

  public Float floatValue() throws ParseException {
    guard();
    return Float.valueOf(value);
  }

  public Integer integerValue() throws ParseException {
    guard();
    return Integer.valueOf(value);
  }

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

  public Node ref(Node node) {
    String[] names = value.split("\\.");
    for (String name : names) {
      node = node.tryNode(name);
    }
    return node;
  }

  public Short shortValue() throws ParseException {
    guard();
    return Short.valueOf(value);
  }

  public String stringValue() throws ParseException {
    if (value == null) {
      throw new ParseException("Can't parse null value.");
    }
    return value;
  }

  private void guard() throws ParseException {
    if (value == null || EMPTY.equals(value)) {
      throw new ParseException("Can't parse empty value.");
    }
  }
}
