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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jdom2.Element;

/**
 * {@link Node} implementation creator.<br>
 *
 * @version 1.0.0
 * @author okosheep
 */
public class NodeImplCreator implements NodeCreator {

  /**
   * {@link Node} implementaion.<be>
   *
   * @version 1.0.0
   * @author okosheep
   */
  public static class NodeImpl implements Node {

    /** Constants of empty string */
    public static final String EMPTY = "";

    /** Element */
    private Element element;

    /** Existence */
    private boolean exists;

    /**
     * Create {@link NodeImpl} instance.<br>
     *
     * @param element
     *          element
     * @param exists
     *          existence
     */
    private NodeImpl(Element element, boolean exists) {
      this.element = element;
      this.exists = exists;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#attr(info.okoshi.trifulx.Attribute)
     */
    @Override
    public Node attr(Attribute attribute) {
      element.setAttribute(attribute.getName(), attribute.getValue().stringValue());
      return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#attr(java.lang.String)
     */
    @Override
    public Attribute attr(String name) {
      Value value = Value.valueOf(element.getAttributeValue(name));
      return new Attribute(name, value);
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#attr(java.lang.String, info.okoshi.trifulx.Value)
     */
    @Override
    public Node attr(String name, Value attribute) {
      element.setAttribute(name, attribute.stringValue());
      return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#attrNames()
     */
    @Override
    public List<String> attrNames() {
      List<String> list = new ArrayList<>();
      element.getAttributes().forEach(a -> list.add(a.getName()));
      return list;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#attrs()
     */
    @Override
    public List<Attribute> attrs() {
      List<Attribute> list = new ArrayList<>();
      element.getAttributes().forEach(a -> list.add(new Attribute(a.getName(), Value.valueOf(a.getValue()))));
      return list;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#attrs(java.util.function.Predicate)
     */
    @Override
    public List<Attribute> attrs(Predicate<Attribute> predicate) {
      return attrs().stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#copyTo(info.okoshi.trifulx.Node)
     */
    @Override
    public Node copyTo(Node node) {
      Element copied = element.clone();
      copied.detach();
      node.node(new NodeImpl(copied, exists));
      return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#exists()
     */
    @Override
    public boolean exists() {
      return exists;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#moveTo(info.okoshi.trifulx.Node)
     */
    @Override
    public Node moveTo(Node node) {
      node.node(this);
      remove();
      return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#node(info.okoshi.trifulx.Node)
     */
    @Override
    public Node node(Node node) {
      if (!node.exists()) {
        return this;
      }

      // If developer uses NodeImpl class, developer can only use NodeImpl class.
      return ((NodeImpl) node).append(element);
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#node(java.lang.String)
     */
    @Override
    public Node node(String name) {
      Element child = element.getChild(name);
      if (child == null) {
        throw new ParseException("\"" + name + "\" node is not found.");
      }
      return new NodeImpl(child, true);
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#nodes()
     */
    @Override
    public List<Node> nodes() {
      List<Node> list = new ArrayList<>();
      for (Element element : element.getChildren()) {
        list.add(new NodeImpl(element, true));
      }
      return list;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#nodes(java.util.function.Predicate)
     */
    @Override
    public List<Node> nodes(Predicate<Node> predicate) {
      return nodes().stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#nodes(java.lang.String)
     */
    @Override
    public List<Node> nodes(String name) {
      return nodes(node -> node.tagName().equals(name));
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#parent()
     */
    @Override
    public Node parent() {
      Element parent = element.getParentElement();
      return new NodeImpl(parent, true);
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#remove()
     */
    @Override
    public Node remove() throws UnsupportedOperationException {
      if (element.isRootElement()) {
        throw new UnsupportedOperationException("Can't remove root node.");
      }
      Element parent = element.getParentElement();
      parent.removeContent(element);
      return new NodeImpl(parent, true);
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#removeAttr(info.okoshi.trifulx.Attribute)
     */
    @Override
    public Node removeAttr(Attribute attribute) {
      removeAttr(attribute.getName());
      return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#removeAttr(java.lang.String)
     */
    @Override
    public Node removeAttr(String name) {
      element.removeAttribute(name);
      return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#removeAttr(java.lang.String[])
     */
    @Override
    public Node removeAttr(String... candidateNames) {
      for (String name : candidateNames) {
        removeAttr(name);
      }
      return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#removeAttrs(java.util.function.Predicate)
     */
    @Override
    public Node removeAttrs(Predicate<Attribute> predicate) {
      attrs().stream().filter(predicate).forEach(a -> removeAttr(a.getName()));
      return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#removeChildren()
     */
    @Override
    public Node removeChildren() {
      List<Element> children = element.getChildren();
      Set<String> names = new HashSet<>();
      for (Element child : children) {
        names.add(child.getName());
      }
      for (String name : names) {
        element.removeChildren(name);
      }
      return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#removeChildren(java.util.function.Predicate)
     */
    @Override
    public Node removeChildren(Predicate<Node> predicate) {
      nodes().stream().filter(predicate).forEach(n -> removeChildren(n.tagName()));
      return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#removeChildren(java.lang.String)
     */
    @Override
    public Node removeChildren(String name) {
      element.removeChildren(name);
      return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#tagName()
     */
    @Override
    public String tagName() {
      return element.getName();
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#tryAttr(java.lang.String[])
     */
    @Override
    public Attribute tryAttr(String... candidateNames) {
      if (candidateNames.length == 0) {
        throw new IllegalArgumentException("Argument is empty.");
      }
      for (String name : candidateNames) {
        Attribute attr = tryAttr(name);
        if (attr.exists()) {
          return attr;
        }
      }
      return tryAttr(candidateNames[0]);
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#tryAttr(java.lang.String)
     */
    @Override
    public Attribute tryAttr(String name) {
      Value v = Value.valueOf(Optional.ofNullable(element.getAttributeValue(name)));
      return new Attribute(name, v);
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#tryNode(java.lang.String[])
     */
    @Override
    public Node tryNode(String... candidateNames) {
      if (candidateNames.length == 0) {
        throw new IllegalArgumentException("Argument is empty.");
      }
      for (String name : candidateNames) {
        Node node = tryNode(name);
        if (node.exists()) {
          return node;
        }
      }
      return tryNode(candidateNames[0]);
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#tryNode(java.lang.String)
     */
    @Override
    public Node tryNode(String name) {
      Element child = element.getChild(name);
      if (child == null) {
        return new NodeImpl(createFakeElement(name), false);
      }
      return new NodeImpl(child, true);
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#value()
     */
    @Override
    public Value value() {
      return Value.valueOf(Optional.ofNullable(element.getTextNormalize()));
    }

    /**
     * {@inheritDoc}
     *
     * @see info.okoshi.trifulx.Node#value(info.okoshi.trifulx.Value)
     */
    @Override
    public Node value(Value value) {
      element.setText(value.stringValue());
      return this;
    }

    private Node append(Element target) {
      Element newElement = element.clone();
      newElement.detach();
      target.addContent(newElement);
      return new NodeImpl(newElement, true);
    }

    /**
     * Create fake element instance.<br>
     *
     * @param name
     *          element name
     * @return fake instance
     */
    private Element createFakeElement(String name) {
      Element fake = new Element(name);
      fake.setText(EMPTY);
      return fake;
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see info.okoshi.trifulx.NodeCreator#create(org.jdom2.Element, boolean)
   */
  @Override
  public Node create(Element element, boolean exists) {
    return new NodeImpl(element, exists);
  }
}
