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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Document;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;

/**
 * Parse and build XML.<br>
 *
 * @version 1.0.0
 * @author okosheep
 */
public class Xml {

  /** JDOM {@link Document} object */
  private Document document;

  /**
   * Create {@link Xml} instance.<br>
   * Input stream will be closed on finally.<br>
   *
   * @param in
   *          Input stream
   * @throws IOException
   *           I/O exception
   * @throws ParseException
   *           Exception in parsing
   */
  public Xml(InputStream in) throws IOException, ParseException {
    try {
      DOMBuilder domBuilder = new DOMBuilder();
      document = domBuilder.build(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in));
    } catch (SAXException | ParserConfigurationException e) {
      throw new ParseException(e);
    } finally {
      in.close();
    }
  }

  /**
   * Create {@link Xml} instance.<br>
   * Parse XML text with UTF-8 encoding.<br>
   *
   * @param text
   *          XMl text
   * @throws IOException
   *           I/O exception
   * @throws ParseException
   *           Exception in parsing
   */
  public Xml(String text) throws IOException, ParseException {
    this(text, StandardCharsets.UTF_8);
  }

  /**
   * Create {@link Xml} instance.<br>
   * Parse XML text with specified encoding.<br>
   * 
   * @param text
   *          XMl text
   * @param charset
   *          Character set
   * @throws IOException
   *           I/O exception.
   * @throws ParseException
   *           Exception in parsing.
   */
  public Xml(String text, Charset charset) throws IOException, ParseException {
    this(new ByteArrayInputStream(text.getBytes(charset)));
  }

  /**
   * Get root node.<br>
   *
   * @return root node
   */
  public Node root() {
    NodeCreator node = NodeCreatorLoader.load();
    return node.create(document.getRootElement(), true);
  }

  /**
   * Write XML file by UTF-8.<br>
   * Overwrite file if exists.<br>
   *
   * @param file
   *          {@link File} object
   * @throws FileNotFoundException
   *           File not found
   * @throws IOException
   *           I/O exception
   */
  public void save(File file) throws FileNotFoundException, IOException {
    save(new FileOutputStream(file, false), StandardCharsets.UTF_8);
  }

  /**
   * Write XML file.<br>
   * Overwrite file if exists.<br>
   *
   * @param file
   *          {@link File} object
   * @param charset
   *          Character set
   * @throws FileNotFoundException
   *           File not found
   * @throws IOException
   *           I/O exception
   */
  public void save(File file, Charset charset) throws FileNotFoundException, IOException {
    save(new FileOutputStream(file, false), charset);
  }

  /**
   * Write XML to output stream by UTF-8.<br>
   * Output stream will be closed on finally.<br>
   *
   * @param out
   *          Output stream
   * @throws IOException
   *           I/O exception
   */
  public void save(OutputStream out) throws IOException {
    save(out, StandardCharsets.UTF_8);
  }

  /**
   * Write XML to output stream.<br>
   * Output stream will be closed on finally.<br>
   *
   * @param out
   *          Output stream
   * @param charset
   *          Character set
   * @throws IOException
   *           I/O exception
   */
  public void save(OutputStream out, Charset charset) throws IOException {
    try {
      Format jdomFormat = Format.getPrettyFormat();
      jdomFormat.setEncoding(charset.name());
      XMLOutputter outputter = new XMLOutputter();
      outputter.setFormat(jdomFormat);
      outputter.output(document, out);
    } finally {
      out.close();
    }
  }

  /**
   * XML to text.<br>
   * Same as #text(FormatTypes.STANDARD).<be>
   * 
   * @return XML text
   */
  public String text() {
    return text(FormatTypes.STANDARD, StandardCharsets.UTF_8);
  }

  /**
   * XML to text.<br>
   *
   * @param charset
   *          Character set
   * @return XML text
   */
  public String text(Charset charset) {
    return text(FormatTypes.STANDARD, charset);
  }

  /**
   * XML to text.<br>
   *
   * @param types
   *          Type of formatting
   * @return XML text
   */
  public String text(FormatTypes types) {
    return text(types, StandardCharsets.UTF_8);
  }

  /**
   * XML to text.<br>
   *
   * @param types
   *          Type of formatting
   * @param charset
   *          Character set
   * @return XML text
   */
  public String text(FormatTypes types, Charset charset) {
    XMLOutputter outputter = new XMLOutputter();
    outputter.setFormat(types.jdomFormat(charset));
    return outputter.outputString(document);
  }
}
