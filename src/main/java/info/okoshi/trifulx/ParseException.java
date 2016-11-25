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

/**
 * Exception in parsing.<br>
 *
 * @version 1.0.0
 * @author okosheep
 */
public class ParseException extends TrifulxException {

  /** Serial version. */
  private static final long serialVersionUID = 3048365317600485383L;

  /**
   * Create {@link ParseException} instance.<br>
   *
   * @param message
   *          message
   */
  public ParseException(String message) {
    super(message);
  }

  /**
   * Create {@link ParseException} instance.<br>
   *
   * @param message
   *          message
   * @param cause
   *          cause
   */
  public ParseException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Create {@link ParseException} instance.<br>
   *
   * @param cause
   *          cause
   */
  public ParseException(Throwable cause) {
    super(cause);
  }
}
