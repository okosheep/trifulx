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
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl} unit test.<br>
 *
 * @version 1.0.0
 * @author okosheep
 */
@Ignore
public class NodeImplTest {

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#attr(info.okoshi.trifulx.Attribute)}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testAttrAttribute$VALIDATE_GETTING_ATTRIBUTE() throws Exception {
    Xml xml = new Xml("<root><data foo=\"bar\" hello=\"world\">data</data><data2>good</data2></root>");
    assertThat(xml.root().node("data").attr("hello").getValue().stringValue(), is("world"));
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#attr(info.okoshi.trifulx.Attribute)}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test(expected = ParseException.class)
  public void testAttrAttribute$VALIDATE_NON_EXISTING_ATTRIBUTE() throws Exception {
    Xml xml = new Xml("<root><data foo=\"bar\" hello=\"world\">data</data><data2>good</data2></root>");
    xml.root().node("data").attr("key");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#attrNames()}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testAttrNames$VALIDATE_ATTRIBUTE_KEYS() throws Exception {
    Xml xml = new Xml("<root><data key1=\"value1\" key2=\"value2\">data</data><data2>good</data2></root>");
    List<String> expected = xml.root().node("data").attrNames();
    assertThat(expected.size(), is(2));
    assertThat(expected, hasItems("key1", "key2"));
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#attrs()}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testAttrs$VALIDATE_MAKING_A_LIST() throws Exception {
    Xml xml = new Xml("<root><data key1=\"value1\" key2=\"value2\">data</data><data2>good</data2></root>");
    List<String> expected = xml.root().node("data").attrs().stream().map(a -> a.getName()).collect(Collectors.toList());
    assertThat(expected.size(), is(2));
    assertThat(expected, hasItems("key1", "key2"));
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#attrs(java.util.function.Predicate)}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testAttrsPredicateOfAttribute$VALIDATE_MAKING_A_LIST() throws Exception {
    Xml xml = new Xml("<root><data key1=\"value1\" key2=\"value2\">data</data><data2>good</data2></root>");
    List<String> expected = xml.root().node("data").attrs(a -> "key2".equals(a.getName())).stream()
        .map(a -> a.getName()).collect(Collectors.toList());
    assertThat(expected.size(), is(1));
    assertThat(expected, hasItems("key2"));
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#attr(java.lang.String)}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testAttrString$VALIDATE_GETTING_ATTRIBUTE() throws Exception {
    Xml xml = new Xml("<root><data1>data</data1><data2 key1=\"value1\" key2=\"value2\">good</data2></root>");
    Attribute expected = xml.root().node("data2").attr("key2");
    assertThat(expected.getName(), is("key2"));
    assertThat(expected.getValue().stringValue(), is("value2"));
  }

  /**
   * Test for
   * {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#attr(java.lang.String, info.okoshi.trifulx.Value)}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testAttrStringValue$VALIDATE_APPEND_ATTRIBUTE() throws Exception {
    Xml xml = new Xml("<root><data1>data</data1><data2 key1=\"value1\" key2=\"value2\">good</data2></root>");
    xml.root().node("data2").attr("key3", Value.valueOf("value3"));
    assertThat(xml.text(FormatTypes.COMPACT), is("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
        + "<root><data1>data</data1><data2 key1=\"value1\" key2=\"value2\" key3=\"value3\">good</data2></root>\r\n"));
  }

  /**
   * Test for
   * {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#attr(java.lang.String, info.okoshi.trifulx.Value)}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAttrStringValue$VALIDATE_APPEND_NULL_NAME_ATTRIBUTE() throws Exception {
    Xml xml = new Xml("<root><data1>data</data1><data2 key1=\"value1\" key2=\"value2\">good</data2></root>");
    xml.root().node("data2").attr(null, Value.valueOf("value3"));
  }

  /**
   * Test for
   * {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#attr(java.lang.String, info.okoshi.trifulx.Value)}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAttrStringValue$VALIDATE_APPEND_NULL_VALUE_ATTRIBUTE() throws Exception {
    Xml xml = new Xml("<root><data1>data</data1><data2 key1=\"value1\" key2=\"value2\">good</data2></root>");
    xml.root().node("data2").attr("key3", null);
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#copyTo(info.okoshi.trifulx.Node)}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testCopyTo$VALIDATE_COPY() throws Exception {
    Xml xml = new Xml("<root><data1></data1><data2><target /></data2></root>");
    Node to = xml.root().node("data1");

    xml.root().node("data2").node("target").copyTo(to).value(Value.valueOf("Hello"));
    assertThat(xml.text(FormatTypes.COMPACT), is(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<root><data1><target>Hello</target></data1><data2><target /></data2></root>\r\n"));
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#copyTo(info.okoshi.trifulx.Node)}.<br>
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testCopyTo$VALIDATE_MOVE_NOT_EXISTING_OBJECT_FOR_FROM() throws Exception {
    Xml xml = new Xml("<root><data1></data1><data2><target /></data2></root>");
    Node to = xml.root().node("data1");

    xml.root().node("data2").tryNode("non-target").copyTo(to);
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#copyTo(info.okoshi.trifulx.Node)}.<br>
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCopyTo$VALIDATE_MOVE_NOT_EXISTING_OBJECT_FOR_TO() throws Exception {
    Xml xml = new Xml("<root><data1></data1><data2><target /></data2></root>");
    Node to = xml.root().tryNode("non-exitence-element");

    xml.root().node("data2").node("target").copyTo(to);
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#exists()}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testExists$VALIDATE_EXISTENCE() throws Exception {
    Xml xml = new Xml("<root><data1></data1><data2><target /></data2></root>");
    assertThat(xml.root().tryNode("data1").exists(), is(true));
    assertThat(xml.root().tryNode("data3").exists(), is(false));
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#moveTo(info.okoshi.trifulx.Node)}.<br>
   */
  @Test
  public void testMoveTo$VALIDATE_MOVE_EXISTING_OBJECT() throws Exception {
    Xml xml = new Xml("<root><data1></data1><data2><target /></data2></root>");
    Node to = xml.root().node("data1");

    xml.root().node("data2").node("target").moveTo(to).value(Value.valueOf("Hello"));
    assertThat(xml.text(FormatTypes.COMPACT), is(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<root><data1><target>Hello</target></data1><data2 /></root>\r\n"));
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#moveTo(info.okoshi.trifulx.Node)}.<br>
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testMoveTo$VALIDATE_MOVE_NOT_EXISTING_OBJECT_FOR_FROM() throws Exception {
    Xml xml = new Xml("<root><data1></data1><data2><target /></data2></root>");
    Node to = xml.root().node("data1");

    xml.root().node("data2").tryNode("non-target").moveTo(to);
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#moveTo(info.okoshi.trifulx.Node)}.<br>
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveTo$VALIDATE_MOVE_NOT_EXISTING_OBJECT_FOR_TO() throws Exception {
    Xml xml = new Xml("<root><data1></data1><data2><target /></data2></root>");
    Node to = xml.root().tryNode("non-exitence-element");

    xml.root().node("data2").node("target").moveTo(to);
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#node(info.okoshi.trifulx.Node)}.<br>
   */
  @Test
  public void testNodeNode() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#nodes()}.<br>
   */
  @Test
  public void testNodes() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#nodes(java.util.function.Predicate)}.<br>
   */
  @Test
  public void testNodesPredicateOfNode() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#nodes(java.lang.String)}.<br>
   */
  @Test
  public void testNodesString() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#node(java.lang.String)}.<br>
   */
  @Test
  public void testNodeString() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#parent()}.<br>
   */
  @Test
  public void testParent() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#remove()}.<br>
   */
  @Test
  public void testRemove() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#removeAttr(info.okoshi.trifulx.Attribute)}.<br>
   */
  @Test
  public void testRemoveAttrAttribute() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#removeAttrs(java.util.function.Predicate)}.<br>
   */
  @Test
  public void testRemoveAttrs() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#removeAttr(java.lang.String)}.<br>
   */
  @Test
  public void testRemoveAttrString() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#removeAttr(java.lang.String[])}.<br>
   */
  @Test
  public void testRemoveAttrStringArray() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#removeChildren()}.<br>
   */
  @Test
  public void testRemoveChildren() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#removeChildren(java.util.function.Predicate)}.<br>
   */
  @Test
  public void testRemoveChildrenPredicateOfNode() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#removeChildren(java.lang.String)}.<br>
   */
  @Test
  public void testRemoveChildrenString() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#tagName()}.<br>
   */
  @Test
  public void testTagName() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#tryAttr(java.lang.String)}.<br>
   */
  @Test
  public void testTryAttrString() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#tryAttr(java.lang.String[])}.<br>
   */
  @Test
  public void testTryAttrStringArray() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#tryNode(java.lang.String)}.<br>
   */
  @Test
  public void testTryNodeString() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#tryNode(java.lang.String[])}.<br>
   */
  @Test
  public void testTryNodeStringArray() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#value()}.<br>
   */
  @Test
  public void testValue() {
    fail("Not implemented.");
  }

  /**
   * Test for {@link info.okoshi.trifulx.NodeImplCreator.NodeImpl#value(info.okoshi.trifulx.Value)}.<br>
   */
  @Test
  public void testValueValue() {
    fail("Not implemented.");
  }
}
