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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test for {@link info.okoshi.trifulx.Xml Xml} unit test.<br>
 *
 * @version 1.0.0
 * @author okosheep
 */
public class XmlTest {

  /**
   * Test for {@link info.okoshi.trifulx.Xml#root()}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testRoot$VALIDATE_ROOT_ELEMENT() throws Exception {
    Xml xml = new Xml("<root><data>foobar</data></root>");
    assertThat(xml.root().tagName(), is("root"));
  }

  /**
   * Test for {@link info.okoshi.trifulx.Xml#save(java.io.File)}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testSaveFile$VALIDATE_OUTPUT() throws Exception {
    File file = File.createTempFile(getClass().getName(), ".testdata");
    file.deleteOnExit();
    Xml xml = new Xml("<root><data>foobar</data></root>");
    xml.save(file);

    try (BufferedReader breader = new BufferedReader(new FileReader(file))) {
      assertThat(breader.readLine(), is("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
      assertThat(breader.readLine(), is("<root>"));
      assertThat(breader.readLine(), is("  <data>foobar</data>"));
      assertThat(breader.readLine(), is("</root>"));
    }
  }

  /**
   * Test for {@link info.okoshi.trifulx.Xml#save(java.io.OutputStream)}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testSaveOutputStream$VALIDATE_OUTPUT() throws Exception {
    File file = File.createTempFile(getClass().getName(), ".testdata");
    file.deleteOnExit();
    Xml xml = new Xml("<root><data>foobarfoobar</data></root>");
    xml.save(new FileOutputStream(file));

    try (BufferedReader breader = new BufferedReader(new FileReader(file))) {
      assertThat(breader.readLine(), is("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
      assertThat(breader.readLine(), is("<root>"));
      assertThat(breader.readLine(), is("  <data>foobarfoobar</data>"));
      assertThat(breader.readLine(), is("</root>"));
    }
  }

  /**
   * Test for {@link info.okoshi.trifulx.Xml#text()}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testText$VALIDATE_TEXTIZE() throws Exception {
    Xml xml = new Xml("<root><data>foobar</data></root>");
    assertThat(xml.text(),
        is("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<root>\r\n  <data>foobar</data>\r\n</root>\r\n"));
  }

  /**
   * Test for {@link info.okoshi.trifulx.Xml#text(info.okoshi.trifulx.FormatTypes)}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testTextFormatTypes$VALIDATE_TEXTIZE_BY_FORMAT_TYPES() throws Exception {
    Xml xml = new Xml("<root><data>foobar</data></root>");
    assertThat(xml.text(FormatTypes.STANDARD),
        is("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<root>\r\n  <data>foobar</data>\r\n</root>\r\n"));
    assertThat(xml.text(FormatTypes.COMPACT),
        is("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<root><data>foobar</data></root>\r\n"));
  }

  /**
   * Test for {@link info.okoshi.trifulx.Xml#Xml(java.io.InputStream)}.<br>
   */
  @Test
  public void testXmlInputStream$VALIDATE_INSTANTIATION() throws Exception {
    Xml xml = new Xml(new ByteArrayInputStream("<root></root>".getBytes(StandardCharsets.UTF_16)));
    assertThat(xml.text(FormatTypes.COMPACT, StandardCharsets.UTF_16),
        is("<?xml version=\"1.0\" encoding=\"UTF-16\"?>\r\n<root />\r\n"));
  }

  /**
   * Test for {@link info.okoshi.trifulx.Xml#Xml(java.lang.String)}.<br>
   */
  @Test
  public void testXmlString$VALIDATE_INSTANTIATION() throws Exception {
    Xml xml = new Xml("<root></root>");
    assertThat(xml.text(FormatTypes.COMPACT), is("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<root />\r\n"));
  }

  /**
   * Test for {@link info.okoshi.trifulx.Xml#Xml(java.lang.String, java.nio.charset.Charset)}.<br>
   * 
   * @throws Exception
   *           Any exception
   */
  @Test
  public void testXmlStringCharset$VALIDATE_INSTANTIATION() throws Exception {
    Xml xml = new Xml("<root></root>");
    assertThat(xml.text(FormatTypes.COMPACT, StandardCharsets.UTF_16LE),
        is("<?xml version=\"1.0\" encoding=\"UTF-16LE\"?>\r\n<root />\r\n"));
  }
}
