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

import java.util.List;
import java.util.function.Predicate;

/**
 * The trifulx node interface.<br>
 * Can't mix any this implementation class on an application.<br>
 * The purpose of using this as an interface isn't for polymorphism.<br>
 *
 * @version 1.0.0
 * @author okosheep
 */
public interface Node {

  /**
   * Set attribute to node.<br>
   *
   * @param attribute
   *          Attribute
   * @return Self object
   */
  Node attr(Attribute attribute);

  /**
   * Get attribute from self node.<br>
   *
   * @param name
   *          Attribute name
   * @return Attribute
   */
  Attribute attr(String name);;

  /**
   * Set attribute to node.<br>
   *
   * @param name
   *          Name
   * @param value
   *          Value
   * @return Self object
   */
  Node attr(String name, Value value);

  /**
   * Makeing a list of attribute names.<br>
   *
   * @return List of attribute names
   */
  List<String> attrNames();

  /**
   * Making a list of attributes.<br>
   *
   * @return List of attributes
   */
  List<Attribute> attrs();

  /**
   * Making a list of filtered attributes.<br>
   *
   * @return List of attributes
   */
  List<Attribute> attrs(Predicate<Attribute> predicate);

  /**
   * Copy to child of specified node.<br>
   *
   * @param node
   *          {@link Node} object
   * @return Self object
   */
  Node copyTo(Node node);

  /**
   * Node existence.<br>
   *
   * @return <code>true</code> means exist, otherwise is <code>false</code>
   */
  boolean exists();

  /**
   * Move to child of specified node.<br>
   *
   * @param node
   *          {@link Node} object
   * @return Self object
   */
  Node moveTo(Node node);

  /**
   * Append child node.<br>
   * This method will returns self object if specified node isn't exists.<br>
   *
   * @param node
   *          {@link Node} object
   * @return Appended child node
   */
  Node node(Node node);

  /**
   * Get a first child node using specified tag name.<br>
   *
   * @param name
   *          Tag name
   * @return Child node
   */
  Node node(String name);

  /**
   * Making a list of child nodes.<br>
   *
   * @return List of child nodes
   */
  List<Node> nodes();

  /**
   * Making a list of filtered child nodes.<br>
   *
   * @param predicate
   *          Predicate
   * @return List of child nodes
   */
  List<Node> nodes(Predicate<Node> predicate);

  /**
   * Making a list of name filtered child nodes.<br>
   *
   * @param name
   *          Node name
   * @return List of child nodes
   */
  List<Node> nodes(String name);

  /**
   * Get parent node.<br>
   *
   * @return Parent node
   */
  Node parent();

  /**
   * Remove self node from parent.<be>
   * 
   * <pre>
   * {@code
   * <!-- before -->
   * <any>
   *   <xmlelement>...</xmlelement>
   * </any>
   * 
   * Node bar = foo.node("xmlelement").remove(); // <- "foo" is node object.
   * bar.equals(foo); // is true!
   * 
   * <!-- after -->
   * <any>
   * </any>
   * }
   * </pre>
   *
   * @return Parent object before removing
   * @throws UnsupportedOperationException
   *           Self object is root node
   */
  Node remove() throws UnsupportedOperationException;

  Node removeAttr(Attribute attribute);

  /**
   * Remove attribute.<br>
   *
   * @param name
   *          Attribute name
   * @return Self object
   */
  Node removeAttr(String name);

  Node removeAttr(String... candidateNames);

  Node removeAttrs(Predicate<Attribute> predicate);

  /**
   * Remove all child nodes.<br>
   *
   * @return Self object
   */
  Node removeChildren();

  Node removeChildren(Predicate<Node> predicate);

  Node removeChildren(String name);

  /**
   * Get XML tag name.<br>
   *
   * @return tag name
   */
  String tagName();

  Attribute tryAttr(String name);

  Attribute tryAttr(String... candidateNames);

  Node tryNode(String name);

  Node tryNode(String... candidateNames);

  /**
   * Get value inside tag text.<br>
   * 
   * <pre>
   * {@code
   * <any>
   *   <xmlelement>data</xmlelement>
   * </any>
   *
   * Value value = foo.node("xmlelement").value(); // "foo" is a node object.
   * "data".equals(value.stringValue()); // <- is true!
   * }
   * </pre>
   *
   * @return Value
   */
  Value value();

  /**
   * Set value to inside XML.<br>
   *
   * <pre>
   * {@code
   * <!-- before -->
   * <any>
   *   <xmlelement></xmlelement>
   * </any>
   *
   * foo.node("xmlelement").value(Value.valueOf("data")); // "foo" is a node object.
   * 
   * <!-- after -->
   * <any>
   *   <xmlelement>data</xmlelement>
   * </any>
   * }
   * </pre>
   * 
   * @param value
   *          Value
   * @return Self object
   */
  Node value(Value value);
}
