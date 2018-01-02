package com.myelin.builder.core.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.CharUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * DESC : 臾몄옄�뿴 愿��젴 寃��깋, 蹂��솚, 移섑솚, �쑀�슚�꽦 泥댄겕 �벑�쓽 湲곕뒫�쓣 �젣怨듯븳�떎.<br><br>
 *
 * Apache LICENSE-2.0�쓽 Anyframe Utils(Version 1.0.1)�쓣 湲곕컲�쑝濡� �옉�꽦�맂 �겢�옒�뒪�엫�쓣 紐낆떆�븳�떎.<br>
 * �삉�븳, Spring�쓽 StringUtils, commons-lang�쓽 StringUtils�쓽 �옒�띁 湲곕뒫�쓣 �젣怨듯븳�떎.
 *
 */
public final class StringUtil {

	private StringUtil() { }
	
	public static final String DEFAULT_EMPTY_STRING = "";

	/** UTF-8�슜 1諛붿씠�듃 罹먮┃�꽣 �뀑 */
	private static final int ONE_BYTE = 0x00007F;

	/** UTF-8�슜 2諛붿씠�듃 罹먮┃�꽣 �뀑 */
	private static final int TWO_BYTE = 0x0007FF;

	/** UTF-8�슜 3諛붿씠�듃 罹먮┃�꽣 �뀑 */
	private static final int THREE_BYTE = 0x00FFFF;
	
	/**
     * The empty String <code>""</code>.
     * @since 2.0
     */
    public static final String EMPTY = "";
	
    /**
     * Represents a failed index search.
     * @since 2.1
     */
    public static final int INDEX_NOT_FOUND = -1;

    /** 肄붾뱶瑜� 諛쏆븘 臾몄옄�뿴濡� 蹂��솚�뿉 �궗�슜 INT*/
	private static final int HEX_TO_STRING_INT = 16;
	
	
	public static String nullToZero(String value) {
		String returnVal = "0";
		if (value == null) {
			returnVal = "0";
		}else {
			returnVal = value;
		}
		return returnVal;
	}
	/**
	 * 臾몄옄�뿴�쓣 "��"媛� �룷�븿�맂 吏��젙�븳 �궗�씠利덈줈 臾몄옄�뿴�쓣 異뺤냼�븳�떎 理쒕��겕湲곕뒗 4蹂대떎 而ㅼ빞�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.abbreviate(null, *)      = null
	 * StringUtils.abbreviate("", 4)        = ""
	 * StringUtils.abbreviate("abcdefg", 6) = "abc..."
	 * StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
	 * StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
	 * StringUtils.abbreviate("abcdefg", 4) = "a..."
	 * StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param maxWidth 理쒕��겕湲�
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String abbreviate(String str, int maxWidth) {
		return org.apache.commons.lang.StringUtils.abbreviate(str, maxWidth);
	}
	
	/**
	 * 臾몄옄�뿴�쓣 "��"媛� �룷�븿�맂 吏��젙�븳 �궗�씠利덈줈 臾몄옄�뿴�쓣 �븵/�뮘濡� 異뺤냼�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.abbreviate(null, *, *)                = null
	 * StringUtils.abbreviate("", 0, 4)                  = ""
	 * StringUtils.abbreviate("abcdefghijklmno", -1, 10) = "abcdefg..."
	 * StringUtils.abbreviate("abcdefghijklmno", 0, 10)  = "abcdefg..."
	 * StringUtils.abbreviate("abcdefghijklmno", 1, 10)  = "abcdefg..."
	 * StringUtils.abbreviate("abcdefghijklmno", 4, 10)  = "abcdefg..."
	 * StringUtils.abbreviate("abcdefghijklmno", 5, 10)  = "...fghi..."
	 * StringUtils.abbreviate("abcdefghijklmno", 6, 10)  = "...ghij..."
	 * StringUtils.abbreviate("abcdefghijklmno", 8, 10)  = "...ijklmno"
	 * StringUtils.abbreviate("abcdefghijklmno", 10, 10) = "...ijklmno"
	 * StringUtils.abbreviate("abcdefghijklmno", 12, 10) = "...ijklmno"
	 * StringUtils.abbreviate("abcdefghij", 0, 3)        = IllegalArgumentException
	 * StringUtils.abbreviate("abcdefghij", 5, 6)        = IllegalArgumentException
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param offset  left edge of source String
	 * @param maxWidth 理쒕��겕湲�
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String abbreviate(String str, int offset, int maxWidth) {
		return org.apache.commons.lang.StringUtils.abbreviate(str, offset, maxWidth);
	}

	/**
	 * 臾몄옄�뿴以� 泥ル쾲吏� 臾몄옄瑜� ��臾몄옄濡� 留뚮뱺�떎.<br />
	 *
	 * <pre>
	 * StringUtils.capitalize(null)  = null
	 * StringUtils.capitalize("")    = ""
	 * StringUtils.capitalize("cat") = "Cat"
	 * StringUtils.capitalize("cAt") = "CAt"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String capitalize(String str) {
		return org.apache.commons.lang.StringUtils.capitalize(str);
	}

	/**
	 * 臾몄옄�뿴�쓽 湲몄씠媛� 蹂��솚湲몄씠媛� �릺�룄濡� 臾몄옄�뿴 醫뚯슦�뿉 怨듬갚臾몄옄瑜� �슦痢〓��꽣 �븯�굹�뵫 異붽��븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.center(null, *)   = null
	 * StringUtils.center("", 4)     = "    "
	 * StringUtils.center("ab", -1)  = "ab"
	 * StringUtils.center("ab", 4)   = " ab "
	 * StringUtils.center("abcd", 2) = "abcd"
	 * StringUtils.center("a", 4)    = " a  "
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param size 蹂��솚湲몄씠
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String center(String str, int size) {
		return org.apache.commons.lang.StringUtils.center(str, size);
	}

	/**
	 * 臾몄옄�뿴�쓽 湲몄씠媛� 蹂��솚湲몄씠媛� �릺�룄濡� 臾몄옄�뿴 醫뚯슦�뿉 �궫�엯臾몄옄瑜� �븯�굹�뵫 �슦痢〓��꽣 異붽��븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.center(null, *, *)     = null
	 * StringUtils.center("", 4, ' ')     = "    "
	 * StringUtils.center("ab", -1, ' ')  = "ab"
	 * StringUtils.center("ab", 4, ' ')   = " ab"
	 * StringUtils.center("abcd", 2, ' ') = "abcd"
	 * StringUtils.center("a", 4, ' ')    = " a  "
	 * StringUtils.center("a", 4, 'y')    = "yayy"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param size 蹂��솚湲몄씠
	 * @param padChar �궫�엯臾몄옄
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String center(String str, int size, char padChar) {
		return org.apache.commons.lang.StringUtils.center(str, size, padChar);
	}

	/**
	 * 臾몄옄�뿴�쓽 湲몄씠媛� 蹂��솚湲몄씠媛� �릺�룄濡� 臾몄옄�뿴 醫뚯슦�뿉 �궫�엯臾몄옄�뿴�쓣 �븯�굹�뵫 �슦痢〓��꽣 異붽��븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.center(null, *, *)     = null
	 * StringUtils.center("", 4, " ")     = "    "
	 * StringUtils.center("ab", -1, " ")  = "ab"
	 * StringUtils.center("ab", 4, " ")   = " ab"
	 * StringUtils.center("abcd", 2, " ") = "abcd"
	 * StringUtils.center("a", 4, " ")    = " a  "
	 * StringUtils.center("a", 4, "yz")   = "yayz"
	 * StringUtils.center("abc", 7, null) = "  abc  "
	 * StringUtils.center("abc", 7, "")   = "  abc  "
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param size 蹂��솚湲몄씠
	 * @param padStr �궫�엯臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String center(String str, int size, String padStr) {
		return org.apache.commons.lang.StringUtils.center(str, size, padStr);
	}

	/**
	 * 臾몄옄�뿴 留� �걹�뿉�엳�뒗 &quot;<code>\n</code>&quot;,<br />
	 * &quot;<code>\r</code>&quot;, or &quot;<code>\r\n</code>&quot;�쓣 �젣嫄고븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.chomp(null)          = null
	 * StringUtils.chomp("")            = ""
	 * StringUtils.chomp("abc \r")      = "abc "
	 * StringUtils.chomp("abc\n")       = "abc"
	 * StringUtils.chomp("abc\r\n")     = "abc"
	 * StringUtils.chomp("abc\r\n\r\n") = "abc\r\n"
	 * StringUtils.chomp("abc\n\r")     = "abc\n"
	 * StringUtils.chomp("abc\n\rabc")  = "abc\n\rabc"
	 * StringUtils.chomp("\r")          = ""
	 * StringUtils.chomp("\n")          = ""
	 * StringUtils.chomp("\r\n")        = ""
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String chomp(String str) {
		return org.apache.commons.lang.StringUtils.chomp(str);
	}

	/**
	 * 臾몄옄�뿴 留� �걹�뿉 援щ텇�옄媛� �엳�쑝硫� �씠瑜� �젣嫄고븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.chomp(null, *)         = null
	 * StringUtils.chomp("", *)           = ""
	 * StringUtils.chomp("foobar", "bar") = "foo"
	 * StringUtils.chomp("foobar", "baz") = "foobar"
	 * StringUtils.chomp("foo", "foo")    = ""
	 * StringUtils.chomp("foo ", "foo")   = "foo "
	 * StringUtils.chomp(" foo", "foo")   = " "
	 * StringUtils.chomp("foo", "foooo")  = "foo"
	 * StringUtils.chomp("foo", "")       = "foo"
	 * StringUtils.chomp("foo", null)     = "foo"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param separator 援щ텇�옄
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String chomp(String str, String separator) {
		return org.apache.commons.lang.StringUtils.chomp(str, separator);
	}

	/**
	 * 臾몄옄�뿴 留� �걹�뿉�엳�뒗 臾몄옄 �븯�굹瑜� �젣嫄고븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.chop(null)          = null
	 * StringUtils.chop("")            = ""
	 * StringUtils.chop("abc \r")      = "abc "
	 * StringUtils.chop("abc\n")       = "abc"
	 * StringUtils.chop("abc\r\n")     = "abc"
	 * StringUtils.chop("abc")         = "ab"
	 * StringUtils.chop("abc\nabc")    = "abc\nab"
	 * StringUtils.chop("a")           = ""
	 * StringUtils.chop("\r")          = ""
	 * StringUtils.chop("\n")          = ""
	 * StringUtils.chop("\r\n")        = ""
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String chop(String str) {
		return org.apache.commons.lang.StringUtils.chop(str);
	}

	/**
	 * �몢�떒�뼱瑜� �궗�쟾 �렪李� �닚�꽌��濡� 鍮꾧탳�븳�떎.<br><br>
	 *
	 * StringUtils.compareTo("Anyframe Java Test", "Anyframe Java Test") = 0
	 *
	 * @param sourceStr �떒�뼱1
	 * @param anotherStr �떒�뼱2
	 * @return �몢 �떒�뼱媛� 媛숈쓣 寃쎌슦 0
	 *         �떒�뼱1�씠 �떒�뼱2蹂대떎 �옉�쓣 寃쎌슦 0 誘몃쭔
	 *         �떒�뼱1�씠 �떒�뼱2蹂대떎 �겢 寃쎌슦 0 珥덇낵
	 * @see String#compareTo(String)
	 */
	public static int compareTo(String sourceStr, String anotherStr) {
		if (sourceStr == null || anotherStr == null) {
			return -1;
		}
		return sourceStr.compareTo(anotherStr);
	}

	/**
	 * �몢�떒�뼱瑜� ���냼 臾몄옄 臾댁떆�븯怨� �궗�쟾 �렪李� �닚�꽌��濡� 鍮꾧탳�븳�떎.<br><br>
	 *
	 * StringUtils.compareToIgnoreCase("anyframe java test", "Anyframe Java Test") = 0
	 *
	 * @param sourceStr �떒�뼱1
	 * @param anotherStr �떒�뼱2
	 * @return �몢 �떒�뼱媛� 媛숈쓣 寃쎌슦 0
	 *         �떒�뼱1�씠 �떒�뼱2蹂대떎 �옉�쓣 寃쎌슦 0 誘몃쭔
	 *         �떒�뼱1�씠 �떒�뼱2蹂대떎 �겢 寃쎌슦 0 珥덇낵
	 * @see String#compareToIgnoreCase(String)
	 */
	public static int compareToIgnoreCase(String sourceStr, String anotherStr) {
		if (sourceStr == null || anotherStr == null) {
			return -1;
		}
		return sourceStr.compareToIgnoreCase(anotherStr);
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 寃��깋臾몄옄媛� �룷�븿�븯怨� �엳�뒗吏� �솗�씤�븳�떎.<br />
	 * 
	 * <code>null</code> �삉�뒗 怨듬갚�씪寃쎌슦 <code>false</code>瑜� 諛섑솚�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.contains(null, *)    = false
	 * StringUtils.contains("", *)      = false
	 * StringUtils.contains("abc", 'a') = true
	 * StringUtils.contains("abc", 'z') = false
	 * </pre>
	 * 
	 * @param str 臾몄옄�뿴
	 * @param searchChar 寃��깋臾몄옄
	 * @return 寃��깋臾몄옄媛� �룷�븿�릺�뼱�엳�뒗 寃쎌슦 true ,
	 *  �룷�븿�릺�뼱�엳吏� �븡嫄곕굹 �엯�젰 臾몄옄�뿴�씠 <code>null</code>�씤寃쎌슦 false
	 */
	public static boolean contains(String str, char searchChar) {
		return org.apache.commons.lang.StringUtils.contains(str, searchChar);
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 寃��깋臾몄옄�뿴�씠 �룷�븿�븯怨� �엳�뒗吏� �솗�씤�븳�떎.<br />
	 * 
	 * <code>null</code>�씪寃쎌슦 <code>false</code>瑜� 諛섑솚�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.contains(null, *)     = false
	 * StringUtils.contains(*, null)     = false
	 * StringUtils.contains("", "")      = true
	 * StringUtils.contains("abc", "")   = true
	 * StringUtils.contains("abc", "a")  = true
	 * StringUtils.contains("abc", "z")  = false
	 * </pre>
	 * 
	 * @param str 臾몄옄�뿴
	 * @param searchStr 寃��깋臾몄옄�뿴
	 * @return 寃��깋臾몄옄媛� �룷�븿�릺�뼱�엳�뒗 寃쎌슦 true ,
	 *  �룷�븿�릺�뼱�엳吏� �븡嫄곕굹 �엯�젰 臾몄옄�뿴�씠 <code>null</code>�씤寃쎌슦 false
	 */
	public static boolean contains(String str, String searchStr) {
		return org.apache.commons.lang.StringUtils.contains(str, searchStr);
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 寃��깋臾몄옄�뿴�씠 ���냼臾몄옄瑜� 臾댁떆�븯怨� �룷�븿�븯怨� �엳�뒗吏� �솗�씤�븳�떎.<br />
	 * 
	 * <code>null</code>�씪寃쎌슦 <code>false</code>瑜� 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.containsIgnoreCase(null, *) = false
	 * StringUtils.containsIgnoreCase(*, null) = false
	 * StringUtils.containsIgnoreCase("", "") = true
	 * StringUtils.containsIgnoreCase("abc", "") = true
	 * StringUtils.containsIgnoreCase("abc", "a") = true
	 * StringUtils.containsIgnoreCase("abc", "z") = false
	 * StringUtils.containsIgnoreCase("abc", "A") = true
	 * StringUtils.containsIgnoreCase("abc", "Z") = false
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param searchStr 寃��깋臾몄옄�뿴
	 * @return 寃��깋臾몄옄媛� �룷�븿�릺�뼱�엳�뒗 寃쎌슦 true ,
	 *  �룷�븿�릺�뼱�엳吏� �븡嫄곕굹 �엯�젰 臾몄옄�뿴�씠 <code>null</code>�씤寃쎌슦 false
	 */
	public static boolean containsIgnoreCase(String str, String searchStr) {
		return org.apache.commons.lang.StringUtils.containsIgnoreCase(str, searchStr);
	}

	/**
	 * �엯�젰�맂 臾몄옄�뿴�씠 二쇱뼱吏� character�뱾�쓣 �룷�븿�븯�뒗吏� 泥댄겕<br><br>
	 *
	 * StringUtils.containsInvalidChars("abc/", new char[] { '*', '/' }) = true
	 *
	 * @param str 臾몄옄�뿴
	 * @param invalidChars 泥댄겕�븷 罹먮┃�꽣�뱾
	 * @return 臾몄옄�뿴�뿉 罹먮┃�꽣�뱾�씠 �룷�븿�릺�뼱 �엳�쓣 寃쎌슦 true
	 */
	public static boolean containsInvalidChars(String str, char[] invalidChars) {
		if (str == null || invalidChars == null) {
			return false;
		}
		int strSize = str.length();
		int validSize = invalidChars.length;
		for (int i = 0; i < strSize; i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < validSize; j++) {
				if (invalidChars[j] == ch) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * �엯�젰�맂 臾몄옄�뿴�씠 二쇱뼱吏� character�뱾�쓣 �룷�븿�븯�뒗吏� 泥댄겕<br><br>
	 *
	 * StringUtils.containsInvalidChars("abc*abc", "*") = true
	 *
	 * @param str 臾몄옄�뿴
	 * @param invalidChars 泥댄겕�븷 罹먮┃�꽣�뱾
	 * @return 臾몄옄�뿴�뿉 罹먮┃�꽣�뱾�씠 �룷�븿�릺�뼱 �엳�쓣 寃쎌슦 true
	 */
	public static boolean containsInvalidChars(String str, String invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		return containsInvalidChars(str, invalidChars.toCharArray());
	}

	/**
	 * �엯�젰�맂 臾몄옄�뿴�뿉 ���빐�꽌 媛숈� character媛� �룞�씪�븯寃� 諛섎났�븯�뒗吏� 泥댄겕<br><br>
	 *
	 * StringUtils.containsMaxSequence("abbbbc", "4") = true
	 *
	 * @param str 臾몄옄�뿴
	 * @param maxSeqNumber 媛숈� 罹먮┃�꽣媛� 諛섎났�릺�뒗 �슏�닔
	 * @return 媛숈� 罹먮┃�꽣媛� 二쇱뼱吏� �슏�닔留뚰겮 諛섎났�맆 寃쎌슦 true
	 */
	public static boolean containsMaxSequence(String str, String maxSeqNumber) {
		int occurence = 1;
		int max = Integer.valueOf(maxSeqNumber);
		if (str == null) {
			return false;
		}
	
		int sz = str.length();
		for (int i = 0; i < (sz - 1); i++) {
			if (str.charAt(i) == str.charAt(i + 1)) {
				occurence++;
	
				if (occurence == max) {
					return true;
				}
			} else {
				occurence = 1;
			}
		}
		return false;
	}

	/**
	 * �엯�젰�맂 underscore �삎�깭�쓽 臾몄옄�뿴�쓣 camel case �삎�깭濡� 蹂��솚<br><br>
	 *
	 * StringUtils.convertToCamelCase("anyframe_java_test") = "anyframeJavaTest"
	 *
	 * @param underscore underscore �삎�깭�쓽 臾몄옄�뿴
	 * @return camel case �삎�깭濡� 蹂��솚�맂 臾몄옄�뿴
	 */
	public static String convertToCamelCase(String underscore) {
		return convertToCamelCase(underscore, "_");
	}

	/**
	 * 二쇱뼱吏� char�쓽 �삎�깭�뿉 留욊쾶 �옒�씪吏� 臾몄옄�뿴�쓣 camel case �삎�깭濡� 蹂��솚<br><br>
	 *
	 * StringUtils.convertToCamelCase("anyframe-java-test", "-") = "anyframeJavaTest"
	 *
	 * @param targetString ���긽 臾몄옄�뿴
	 * @param posChar 臾몄옄�뿴�쓣 �옄瑜� 湲곗� 罹먮┃�꽣
	 * @return camel case �삎�깭濡� 蹂��솚�맂 臾몄옄�뿴
	 */
	public static String convertToCamelCase(String targetString, String posChar) {
		StringBuilder result = new StringBuilder();
		boolean nextUpper = false;
		String allLower = targetString.toLowerCase();
	
		for (int i = 0; i < allLower.length(); i++) {
			char currentChar = allLower.charAt(i);
			if (currentChar == CharUtils.toChar(posChar)) {
				nextUpper = true;
			} else {
				if (nextUpper) {
					currentChar = Character.toUpperCase(currentChar);
					nextUpper = false;
				}
				result.append(currentChar);
			}
		}
		return result.toString();
	}

	/**
	 * camel case �삎�깭�쓽 臾몄옄�뿴�쓣 underscore �삎�깭�쓽 臾몄옄�뿴濡� 蹂��솚<br><br>
	 *
	 * StringUtils.convertToUnderScore("anyframeJavaTest") = "anyframe_java_test"
	 *
	 * @param camelCase  camel case �삎�깭�쓽 臾몄옄�뿴
	 * @return underscore �삎�깭濡� 蹂��솚�맂 臾몄옄�뿴
	 */
	public static String convertToUnderScore(String camelCase) {
		String result = "";
		for (int i = 0; i < camelCase.length(); i++) {
			char currentChar = camelCase.charAt(i);
			// This is starting at 1 so the result does not end up with an
			// underscore at the begin of the value
			if (i > 0 && Character.isUpperCase(currentChar)) {
				result = result.concat("_");
			}
			result = result.concat(Character.toString(currentChar)
					.toLowerCase());
		}
		return result;
	}

	/**
	 * 臾몄옄�뿴以묒뿉 寃��깋臾몄옄�뿴�씠 �룷�븿�릺�뼱�엳�뒗 媛��닔瑜� 諛섑솚�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.countMatches(null, *)       = 0
	 * StringUtils.countMatches("", *)         = 0
	 * StringUtils.countMatches("abba", null)  = 0
	 * StringUtils.countMatches("abba", "")    = 0
	 * StringUtils.countMatches("abba", "a")   = 2
	 * StringUtils.countMatches("abba", "ab")  = 1
	 * StringUtils.countMatches("abba", "xxx") = 0
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param sub 寃��깋臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static int countMatches(String str, String sub) {
		return org.apache.commons.lang.StringUtils.countMatches(str, sub);
	}

	/**
	 * �븳 String 媛앹껜(sub)�쓽 �뙣�꽩�씠 �떎瑜� String 媛앹껜(main)�븞�뿉�꽌 紐� 踰� �벑�옣�븯�뒗吏� 怨꾩궛�븳�떎. - �벑�옣 �뙣�꽩�쓽 �쐞移섎뒗<br>
	 * 醫뚯륫�뿉�꽌遺��꽣 怨꾩궛�븯怨� 寃뱀튂吏� �븡�뒗 �삎�깭濡� 怨꾩궛�븳�떎. + �삁瑜� �뱾�뼱, "aa"�뒗 "aaa"�뿉�꽌 �몢 踰� �벑�옣�븯�뒗 寃껋씠 �븘�땲�씪, <br>
	 * �븳 踰� �벑�옣�븯�뒗 寃껋쑝濡� 怨꾩궛�븳�떎. <br><br>
	 * 
	 * StringUtils.countPattern("aaa", "aa") = 1
	 * 
	 * @param str
	 *            the String to check
	 * @param pattern
	 *            the pattern to count
	 * @return the number of occurrences
	 */
	public static int countPattern(String str, String pattern) {
		if (str == null || pattern == null || "".equals(pattern)) {
			return 0;
		}
		int count = 0;
		int pos = 0;
		
		while (str.indexOf(pattern, pos) != -1) {
			int index = str.indexOf(pattern, pos);
			count++;
			pos = index + pattern.length();
		}
		return count;
	}

	/**
	 * 泥ル쾲吏� 臾몄옄�뿴怨� �몢踰덉㎏ 臾몄옄�뿴�쓣 鍮꾧탳�빐�꽌 媛숈쑝硫� �꽭踰덉㎏ 臾몄옄�뿴�쓣 �떎瑜대㈃ �꽕踰덉옱 臾몄옄�뿴�쓣 諛섑솚�븳�떎.<br><br>
	 *
	 * StringUtils.decode("Java", "Test", "Good", "Bad") = "bad"
	 *
	 * @param source 泥ル쾲吏� 臾몄옄�뿴
	 * @param target �몢踰덉㎏ 臾몄옄�뿴
	 * @param result �몢 臾몄옄�뿴�씠 媛숈쓣 �븣 諛섑솚�븷 臾몄옄�뿴
	 * @param base �몢 臾몄옄�뿴�씠 �떎瑜� �븣 諛섑솚�븷 臾몄옄�뿴
	 * @return �몢 臾몄옄�뿴�씠 媛숈쑝硫� �꽭踰덉㎏ 臾몄옄�뿴�쓣 �떎瑜대㈃ �꽕踰덉옱 臾몄옄�뿴�쓣 諛섑솚
	 */
	public static String decode(String source, String target, String result, String base) {
		if (source == null && target == null) {
			return result;
		} else if (source == null && target != null) {
			return base;
		} else if (source.trim().equals(target)) {
			return result;
		}
		return base;
	}

	/**
	 * 臾몄옄�뿴�씠 <code>null</code> �삉�뒗 怨듬갚 �삉�뒗 怨듬갚臾몄옄 �씠硫� <code>defaultStr</code>�쓣 諛섑솚�븯怨�<br />
	 * �븘�땲硫� <code>str</code>�쓣 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.defaultIfBlank(null, "NULL")  = "NULL"
	 * StringUtils.defaultIfBlank("", "NULL")    = "NULL"
	 * StringUtils.defaultIfBlank(" ", "NULL")   = "NULL"
	 * StringUtils.defaultIfBlank("bat", "NULL") = "bat"
	 * StringUtils.defaultIfBlank("", null)      = null
	 * </pre>
	 * @param str 臾몄옄�뿴
	 * @param defaultStr 珥덇린�꽕�젙臾몄옄
	 * @return 寃곌낵臾몄옄�뿴
	 */
	public static String defaultIfBlank(String str, String defaultStr) {
		return org.apache.commons.lang.StringUtils.defaultIfEmpty(str, defaultStr);
	}

	/**
	 * 臾몄옄�뿴�씠 <code>null</code> �삉�뒗 怨듬갚�씠硫� <code>defaultStr</code>�쓣 諛섑솚�븯怨�<br />
	 * �븘�땲硫� <code>str</code>�쓣 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.defaultIfEmpty(null, "NULL")  = "NULL"
	 * StringUtils.defaultIfEmpty("", "NULL")    = "NULL"
	 * StringUtils.defaultIfEmpty("bat", "NULL") = "bat"
	 * StringUtils.defaultIfEmpty("", null)      = null
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param defaultStr 珥덇린�꽕�젙臾몄옄
	 * @return 寃곌낵臾몄옄�뿴
	 */
	public static String defaultIfEmpty(String str, String defaultStr) {
		return org.apache.commons.lang.StringUtils.defaultIfEmpty(str, defaultStr);
	}

	/**
	 * 臾몄옄�뿴�씠 <code>null</code>�씠硫� 怨듬갚瑜� 諛섑솚�븯怨� �븘�땲硫� <code>str</code>�쓣 諛섑솚�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.defaultString(null)  = ""
	 * StringUtils.defaultString("")    = ""
	 * StringUtils.defaultString("bat") = "bat"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 怨듬갚
	 */
	public static String defaultString(String str) {
		return org.apache.commons.lang.StringUtils.defaultString(str);
	}

	/**
	 * 臾몄옄�뿴�씠 <code>null</code>�씠硫� <code>defaultStr</code>�쓣 諛섑솚�븯怨�<br />
	 * �븘�땲硫� <code>str</code>�쓣 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.defaultString(null, "NULL")  = "NULL"
	 * StringUtils.defaultString("", "NULL")    = ""
	 * StringUtils.defaultString("bat", "NULL") = "bat"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param defaultStr 珥덇린�꽕�젙臾몄옄
	 * @return 寃곌낵臾몄옄�뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 珥덇린�꽕�젙臾몄옄
	 */
	public static String defaultString(String str, String defaultStr) {
		return org.apache.commons.lang.StringUtils.defaultString(str, defaultStr);
	}

	/**
	 * �븳 String 媛앹껜 �븞�뿉�꽌 �듅�젙 �뙣�꽩 �븞�뿉 �룷�븿�맂 紐⑤뱺 character�뱾�쓣 �젣嫄고븳�떎.<br><br>
	 * 
	 * StringUtils.deleteChars("zzAccBxx", "AB") = "zzccxx"
	 *
	 * @param str
	 *            the source String to search
	 * @param chars
	 *            the char to search for and remove
	 * @return the substring with the char removed if found
	 */
	public static String deleteChars(String str, String chars) {
		if (str == null || chars == null) {
			return str;
		}
		String value = str;
		for (int i = 0; i < chars.length(); i++) {
			value = removeChar(value, chars.charAt(i));
		}
		return value;
	}

	/**
	 * �븳 String 媛앹껜 �븞�뿉�꽌 �듅�젙 �뙣�꽩�쓣 �젣嫄고븳�떎. - �벑�옣 �뙣�꽩�쓽 �쐞移섎뒗 醫뚯륫�뿉�꽌遺��꽣 怨꾩궛�븯怨�<br>
	 * 寃뱀튂吏� �븡�뒗 �삎�깭濡� 怨꾩궛�븳�떎. + �뵲�씪�꽌, �젣嫄곕맂 �썑�뿉�룄 old �뙣�꽩�� �궓�븘 �엳�쓣 �닔 �엳�떎.<br><br>
	 * 
	 * StringUtils.deletePattern("aababa", "aba")�뒗 "aba"<br>
	 * StringUtils.deletePattern("zzABCcc", "ABC") => "zzcc"
	 *
	 * @param str
	 *            the source String to search
	 * @param pattern
	 *            the String to search for and remove
	 * @return the substring with the string removed if found
	 */
	public static String deletePattern(String str, String pattern) {
		return replacePattern(str, pattern, "");
	}

	/**
	 * 臾몄옄�뿴以� 怨듬갚臾몄옄媛� �엳�쑝硫� 紐⑤몢 �젣嫄고븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.deleteWhitespace(null)         = null
	 * StringUtils.deleteWhitespace("")           = ""
	 * StringUtils.deleteWhitespace("abc")        = "abc"
	 * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, 臾몄옄�뿴�씠 null�씪寃⑥슦 <code>null</code>
	 */
	public static String deleteWhitespace(String str) {
		return org.apache.commons.lang.StringUtils.deleteWhitespace(str);
	}

	/**
	 * StringTokenizer瑜� �씠�슜�븯吏� �븡怨� 泥섎━�븯�뿬, �뿰�냽�맂 delimiter �궗�씠�뒗 鍮꾩뼱 �엳�뒗 token�쑝濡� 媛꾩＜�맂�떎. <br>
	 * 二쇱뼱吏� String�씠 null�씪 寃쎌슦, null�쓣 return�븳�떎. <br>
	 * delimiter媛� null�씪 寃쎌슦, 二쇱뼱吏� String�쓣 �븯�굹�쓽 element濡� 媛�吏��뒗 String[]瑜� return�븳�떎.<br><br>
	 * 
	 * StringUtils.delimitedStringToStringArray("aaa.bbb.ccc.ddd", "."); = test[0]="aaa", test[1]="bbb"...
	 *
	 * @param str 
	 *            the silgle String to convert
	 * @param delimiter
	 *            delimiter for conversioin
	 * @return array of String values
	 */
	public static String[] delimitedStringToStringArray(String str,
			String delimiter) {
		if (str == null) {
			return null;
		}
		if (delimiter == null) {
			return new String[] {str};
		}
		List<String> tokens = new ArrayList<String>();
		int pos = 0;
		
		while (str.indexOf(delimiter, pos) != -1) {
			int index = str.indexOf(delimiter, pos);
			tokens.add(str.substring(pos, index));
			pos = index + delimiter.length();
		}
		
		if (pos <= str.length()) {
			tokens.add(str.substring(pos));
		}
		return tokens.toArray(new String[tokens.size()]);
	}

	/**
	 * �몢臾몄옄�뿴瑜� 鍮꾧탳�븯�뿬 �떎瑜몃�遺꾩쓣 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.difference(null, null) = null
	 * StringUtils.difference("", "") = ""
	 * StringUtils.difference("", "abc") = "abc"
	 * StringUtils.difference("abc", "") = ""
	 * StringUtils.difference("abc", "abc") = ""
	 * StringUtils.difference("ab", "abxyz") = "xyz"
	 * StringUtils.difference("abcde", "abxyz") = "xyz"
	 * StringUtils.difference("abcde", "xyz") = "xyz"
	 * </pre>
	 *
	 * @param str1 臾몄옄�뿴1
	 * @param str2 臾몄옄�뿴2
	 * @return 寃곌낵臾몄옄�뿴
	 */
	public static String difference(String str1, String str2) {
		return org.apache.commons.lang.StringUtils.difference(str1, str2);
	}

	/**
	 * 臾몄옄�뿴�씠 寃��깋臾몄옄濡� �걹�굹�뒗吏� �솗�씤�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.endsWith(null, null)      = true
	 * StringUtils.endsWith(null, "def")     = false
	 * StringUtils.endsWith("abcdef", null)  = false
	 * StringUtils.endsWith("abcdef", "def") = true
	 * StringUtils.endsWith("ABCDEF", "def") = false
	 * StringUtils.endsWith("ABCDEF", "cde") = false
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param suffix 寃��깋臾몄옄
	 * @return 臾몄옄�뿴�씠 寃��깋臾몄옄濡� �걹�굹�뒗寃쎌슦�� 臾몄옄�뿴 �뼇履쎈え�몢
	 *  <code>null</code>�씤寃쎌슦 <code>true</code> 
	 */
	public static boolean endsWith(String str, String suffix) {
		return org.apache.commons.lang.StringUtils.endsWith(str, suffix);
	}

	/**
	 * 臾몄옄�뿴�씠 寃��깋臾몄옄濡� ���냼臾몄옄 援щ텇�뾾�씠 �걹�굹�뒗吏� �솗�씤�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.endsWithIgnoreCase(null, null)      = true
	 * StringUtils.endsWithIgnoreCase(null, "def")     = false
	 * StringUtils.endsWithIgnoreCase("abcdef", null)  = false
	 * StringUtils.endsWithIgnoreCase("abcdef", "def") = true
	 * StringUtils.endsWithIgnoreCase("ABCDEF", "def") = true
	 * StringUtils.endsWithIgnoreCase("ABCDEF", "cde") = false
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param suffix 寃��깋臾몄옄
	 * @return 臾몄옄�뿴�씠 寃��깋臾몄옄濡� ���냼臾몄옄 援щ텇�뾾�씠 �걹�굹�뒗寃쎌슦�� 臾몄옄�뿴 �뼇履쎈え�몢
	 *  <code>null</code>�씤寃쎌슦 <code>true</code> 
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		return org.apache.commons.lang.StringUtils.endsWithIgnoreCase(str, suffix);
	}

	/**
	 * �몢媛쒖쓽 臾몄옄�뿴�씠 媛숈�吏� 鍮꾧탳�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.equals(null, null)   = true
	 * StringUtils.equals(null, "abc")  = false
	 * StringUtils.equals("abc", null)  = false
	 * StringUtils.equals("abc", "abc") = true
	 * StringUtils.equals("abc", "ABC") = false
	 * </pre>
	 *
	 * @param str1 泥ル쾲吏� 臾몄옄�뿴
	 * @param str2 �몢踰덉㎏ 臾몄옄�뿴
	 * @return �몢 媛쒖쓽 臾몄옄�뿴�쓣 鍮꾧탳�븯�뿬 媛숈쑝硫� true, �븘�땲硫� false瑜� 諛섑솚
	 */
	public static boolean equals(String str1, String str2) {
		return org.apache.commons.lang.StringUtils.equals(str1, str2);
	}

	/**
	 * �몢媛쒖쓽 臾몄옄�뿴�쓣 �쁺臾� ���냼臾몄옄瑜� 臾댁떆�븯怨� 媛숈�吏� 鍮꾧탳�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.equalsIgnoreCase(null, null)   = true
	 * StringUtils.equalsIgnoreCase(null, "abc")  = false
	 * StringUtils.equalsIgnoreCase("abc", null)  = false
	 * StringUtils.equalsIgnoreCase("abc", "abc") = true
	 * StringUtils.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 *
	 * @param str1 泥ル쾲吏� 臾몄옄�뿴
	 * @param str2 �몢踰덉㎏ 臾몄옄�뿴
	 * @return �몢 媛쒖쓽 臾몄옄�뿴�쓣 �쁺臾� ���냼臾몄옄瑜� 臾댁떆�븯怨� 鍮꾧탳�븯�뿬 媛숈쑝硫� true, �븘�땲硫� false瑜� 諛섑솚
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return org.apache.commons.lang.StringUtils.equalsIgnoreCase(str1, str2);
	}

	/**
	 * �몢媛쒖쓽 臾몄옄�뿴�씠 �떎瑜몄� 鍮꾧탳�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.notEquals(null, null)   = false
	 * StringUtils.notEquals(null, "abc")  = true
	 * StringUtils.notEquals("abc", null)  = true
	 * StringUtils.notEquals("abc", "abc") = false
	 * StringUtils.notEquals("abc", "ABC") = true
	 * </pre>
	 *
	 * @param str1 泥ル쾲吏� 臾몄옄�뿴
	 * @param str2 �몢踰덉㎏ 臾몄옄�뿴
	 * @return �몢 媛쒖쓽 臾몄옄�뿴�쓣 鍮꾧탳�븯�뿬 �떎瑜대㈃ true, �븘�땲硫� false瑜� 諛섑솚
	 */
	public static boolean notEquals(String str1, String str2) {
		return !org.apache.commons.lang.StringUtils.equals(str1, str2);
	}

	/**
	 * �몢媛쒖쓽 臾몄옄�뿴�쓣 �쁺臾� ���냼臾몄옄瑜� 臾댁떆�븯怨� �떎瑜몄� 鍮꾧탳�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.notEqIgnoreCase(null, null)   = false
	 * StringUtils.notEqIgnoreCase(null, "abc")  = true
	 * StringUtils.notEqIgnoreCase("abc", null)  = true
	 * StringUtils.notEqIgnoreCase("abc", "abc") = false
	 * StringUtils.notEqIgnoreCase("abc", "ABC") = false
	 * </pre>
	 *
	 * @param str1 泥ル쾲吏� 臾몄옄�뿴
	 * @param str2 �몢踰덉㎏ 臾몄옄�뿴
	 * @return �몢 媛쒖쓽 臾몄옄�뿴�쓣 �쁺臾� ���냼臾몄옄瑜� 臾댁떆�븯怨� 鍮꾧탳�븯�뿬 �떎瑜대㈃ true, �븘�땲硫� false瑜� 諛섑솚
	 */
	public static boolean notEqIgnoreCase(String str1, String str2) {
		return !org.apache.commons.lang.StringUtils.equalsIgnoreCase(str1, str2);
	}

	/**
	 * �빐�떦�븯�뒗 臾몄옄�뿴�뿉 ���빐�꽌 byte �떒�쐞�뿉 ���빐�꽌 湲몄씠 怨꾩궛�빐�꽌 珥� 湲몄씠 諛섑솚<br><br>
	 *
	 * StringUtils.getByteLength("Anyframe Java Test") = 18
	 *
	 * @param str 臾몄옄�뿴
	 * @return 臾몄옄�뿴�쓽 byte �떒�쐞 湲몄씠
	 */
	public static int getByteLength(String str) {
		if (str == null) {
			return -1;
		}
		int size = 0;
	
		for (int i = 0; i < str.length(); i++) {
			size += getByteLength(str.charAt(i));
		}
		return size;
	}
	
	private static int getByteLength(char charat) {
		int charCode = charat;
	
		if (charCode <= ONE_BYTE) {
			return 1;
		} else if (charCode <= TWO_BYTE) {
			return 2;
		} else if (charCode <= THREE_BYTE) {
			return 3;
		} else {
			return 4;
		}
	}

	/**
	 * 諛곗뿴�븞�쓽 臾몄옄�뿴瑜� 鍮꾧탳�븯�뿬 媛숈�遺�遺꾩쓽 臾몄옄�뿴�쓣 諛섑솚�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.getCommonPrefix(null) = ""
	 * StringUtils.getCommonPrefix(new String[] {}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"abc"}) = "abc"
	 * StringUtils.getCommonPrefix(new String[] {null, null}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"", ""}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"", null}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"abc", null, null}) = ""
	 * StringUtils.getCommonPrefix(new String[] {null, null, "abc"}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"", "abc"}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"abc", ""}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"abc", "abc"}) = "abc"
	 * StringUtils.getCommonPrefix(new String[] {"abc", "a"}) = "a"
	 * StringUtils.getCommonPrefix(new String[] {"ab", "abxyz"}) = "ab"
	 * StringUtils.getCommonPrefix(new String[] {"abcde", "abxyz"}) = "ab"
	 * StringUtils.getCommonPrefix(new String[] {"abcde", "xyz"}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"xyz", "abcde"}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) = "i am a "
	 * </pre>
	 *
	 * @param strs 臾몄옄諛곗뿴
	 * @return 寃곌낵臾몄옄�뿴
	 */
	public static String getCommonPrefix(String[] strs) {
		return org.apache.commons.lang.StringUtils.getCommonPrefix(strs);
	}


	/**
	 * 二쇱뼱吏� 臾몄옄�뿴�뿉 ���빐�꽌 �빐�떦�븯�뒗 臾몄옄�뿴�씠 �룷�븿�릺�뼱 �엳�뒗 �닽�옄 諛섑솚<br><br>
	 *
	 * StringUtils.getContainsCount("Anyframe Java Test", "a") = 3
	 *
	 * @param str 二쇱뼱吏� 臾몄옄�뿴
	 * @param sub 寃��깋�븷 臾몄옄�뿴
	 * @return 臾몄옄�뿴�씠 �룷�븿�릺�뼱 �엳�뒗 �닽�옄
	 */
	public static int getContainsCount(String str, String sub) {
		return org.springframework.util.StringUtils.countOccurrencesOf(str, sub);
	}

	/**
	 * ���냼臾몄옄 援щ텇�뾾�씠 二쇱뼱吏� 臾몄옄�뿴�뿉 ���빐�꽌 �빐�떦�븯�뒗 臾몄옄�뿴�씠 �룷�븿�릺�뼱 �엳�뒗 媛��닔瑜� 諛섑솚<br><br>
	 *
	 * StringUtils.getContainsCountIgnoreCase("Anyframe Java Test", "test") = 1
	 *
	 * @param str 二쇱뼱吏� 臾몄옄�뿴
	 * @param sub 寃��깋�븷 臾몄옄�뿴
	 * @return 臾몄옄�뿴�씠 �룷�븿�릺�뼱 �엳�뒗 媛��닔
	 * @see org.springframework.util.StringUtils#countOccurrencesOf(String,
	 *      String)
	 */
	public static int getContainsCountIgnoreCase(String str, String sub) {
		return org.springframework.util.StringUtils.countOccurrencesOf(str.toLowerCase(), sub.toLowerCase());
	}

	/**
	 * �엯�젰�맂 臾몄옄�뿴�쓣 二쇱뼱吏� token�뿉 ���빐�꽌 遺꾨━ �썑 留덉�留� 臾몄옄�뿴 諛섑솚<br><br>
	 *
	 * StringUtils.getLastString("Anyframe_Java_Test", "_") = "Test"
	 *
	 * @param origStr 臾몄옄�뿴
	 * @param strToken 遺꾨━�븷 token
	 * @return token�뿉 ���빐�꽌 遺꾨━�맂 留덉�留� 臾몄옄�뿴
	 */
	public static String getLastString(String origStr, String strToken) {
		StringTokenizer str = new StringTokenizer(origStr, strToken);
		String lastStr = "";
		while (str.hasMoreTokens()) {
			lastStr = str.nextToken();
		}
		return lastStr;
	}


	/**
	 * 臾몄옄�뿴1�쓣 臾몄옄�뿴2濡� 蹂��솚�븯湲곗쐞�빐 移섑솚,�궫�엯,�궘�젣�븯吏� �븡�쑝硫� �븡�릺�뒗 理쒖냼�쓽 臾몄옄�닔瑜� 諛섑솚�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.getLevenshteinDistance(null, *)             = IllegalArgumentException
	 * StringUtils.getLevenshteinDistance(*, null)             = IllegalArgumentException
	 * StringUtils.getLevenshteinDistance("","")               = 0
	 * StringUtils.getLevenshteinDistance("","a")              = 1
	 * StringUtils.getLevenshteinDistance("aaapppp", "")       = 7
	 * StringUtils.getLevenshteinDistance("frog", "fog")       = 1
	 * StringUtils.getLevenshteinDistance("fly", "ant")        = 3
	 * StringUtils.getLevenshteinDistance("elephant", "hippo") = 7
	 * StringUtils.getLevenshteinDistance("hippo", "elephant") = 7
	 * StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz") = 8
	 * StringUtils.getLevenshteinDistance("hello", "hallo")    = 1
	 * </pre>
	 *
	 * @param s 臾몄옄�뿴1
	 * @param t 臾몄옄�뿴2
	 * @return 臾몄옄�뿴1�쓣 臾몄옄�뿴2濡� 蹂��솚�븯湲곗쐞�빐 移섑솚,�궫�엯,�궘�젣�븯吏� �븡�쑝硫� �븡�릺�뒗 理쒖냼�쓽 臾몄옄�닔
	 */
	public static int getLevenshteinDistance(String s, String t) {
		return org.apache.commons.lang.StringUtils.getLevenshteinDistance(s, t);
	    }

	/**
	 * �엯�젰�맂 臾몄옄�뿴�쓣 二쇱뼱吏� token�뿉 ���빐�꽌 遺꾨━ �썑 arraylist �삎�깭濡� 諛섑솚<br><br>
	 *
	 * StringUtils.getStringArray("Anyframe/Java/Test", "/")
	 *
	 * @param str 臾몄옄�뿴
	 * @param strToken 遺꾨━�븷 token
	 * @return token�뿉 ���빐�꽌 遺꾨━�맂 arraylist
	 */
	public static String[] getStringArray(String str, String strToken) {
		if (str.indexOf(strToken) != -1) {
			StringTokenizer st = new StringTokenizer(str, strToken);
			String[] stringArray = new String[st.countTokens()];
			for (int i = 0; st.hasMoreTokens(); i++) {
				stringArray[i] = st.nextToken();
			}
			return stringArray;
		}
		return new String[] {str};
	}

	/**
	 * �엯�젰�맂 臾몄옄�뿴�쓣 ,(肄ㅻ쭏)�뿉 ���빐�꽌 遺꾨━ �썑 List<String>�쑝濡� 諛섑솚<br><br>
	 *
	 * @param lst 臾몄옄�뿴
	 * @return ,(肄ㅻ쭏)濡� 遺꾨━�맂 List
	 */
	public static List<String> getTokens(String lst) {
		return getTokens(lst, ",");
	}

	/**
	 * �엯�젰�맂 臾몄옄�뿴�쓣 二쇱뼱吏� separator�뿉 ���빐�꽌 遺꾨━ �썑 List<String>�쑝濡� 諛섑솚<br><br>
	 *
	 * StringUtils.getTokens("Anyframe/Java/Test", "/")
	 *
	 * @param lst 臾몄옄�뿴
	 * @param separator 遺꾨━�븷 湲곗� 臾몄옄�뿴
	 * @return 湲곗� 臾몄옄�뿴濡� 遺꾨━�맂 List
	 */
	public static List<String> getTokens(String lst, String separator) {
		List<String> tokens = new ArrayList<String>();
	
		if (lst != null) {
			StringTokenizer st = new StringTokenizer(lst, separator);
			while (st.hasMoreTokens()) {
				String en = st.nextToken().trim();
				tokens.add(en);
			}
		}
		return tokens;
	}

	/**
	 * 肄붾뱶瑜� 諛쏆븘 臾몄옄�뿴濡� 蹂��솚�븳�떎(�쑀�땲肄붾뱶)
	 *
	 * @param str
	 *            the String to convert
	 * @return UniCode String
	 */
	public static String hexToString(String str) {
	
		String inStr = str;
		char[] inChar = inStr.toCharArray();
		StringBuffer sb = new StringBuffer();
	
		for (int i = 0; i < inChar.length; i += 4) {
			String hex = str.substring(i, i + 4);
			sb.append((char) Integer.parseInt(hex, HEX_TO_STRING_INT));
		}
		return sb.toString();
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 �떆�옉�씤�뜳�뒪濡쒕��꽣 泥ル쾲吏� 寃��깋臾몄옄�쓽 �씤�뜳�뒪瑜� 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.indexOf(null, *, *)          = -1
	 * StringUtils.indexOf("", *, *)            = -1
	 * StringUtils.indexOf("aabaabaa", 'b', 0)  = 2
	 * StringUtils.indexOf("aabaabaa", 'b', 3)  = 5
	 * StringUtils.indexOf("aabaabaa", 'b', 9)  = -1
	 * StringUtils.indexOf("aabaabaa", 'b', -1) = 2
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param searchChar  李얠쓣臾몄옄
	 * @param startPos  �떆�옉�씤�뜳�뒪
	 * @return null �삉�뒗 鍮� 臾몄옄�뿴�� INDEX_NOT_FOUND (-1)瑜� 諛섑솚
	 */
	public static int indexOf(String str, char searchChar, int startPos) {
		return org.apache.commons.lang.StringUtils.indexOf(str, searchChar, startPos);
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 �떆�옉�씤�뜳�뒪濡쒕��꽣 泥ル쾲吏� 寃��깋臾몄옄�뿴�쓽 �씤�뜳�뒪瑜� 諛섑솚�븳�떎.<br />
	 *
	 * Null 臾몄옄�뿴��-1�쓣 諛섑솚<br />
	 * 遺��젙�쟻�씤 �떆�옉 �쐞移섎뒗 0�쑝濡� 泥섎━<br />
	 * 鍮� 寃��깋 臾몄옄�뿴 �빆�긽 �씪移�<br />
	 * �떆�옉 �쐞移섍� 臾몄옄�뿴 湲몄씠 蹂대떎 �겙 �씪移�<br />
	 * 鍮� 寃��깋 臾몄옄�뿴<br />
	 *
	 * <pre>
	 * StringUtils.indexOf(null, *, *)          = -1
	 * StringUtils.indexOf(*, null, *)          = -1
	 * StringUtils.indexOf("", "", 0)           = 0
	 * StringUtils.indexOf("", *, 0)            = -1 (except when * = "")
	 * StringUtils.indexOf("aabaabaa", "a", 0)  = 0
	 * StringUtils.indexOf("aabaabaa", "b", 0)  = 2
	 * StringUtils.indexOf("aabaabaa", "ab", 0) = 1
	 * StringUtils.indexOf("aabaabaa", "b", 3)  = 5
	 * StringUtils.indexOf("aabaabaa", "b", 9)  = -1
	 * StringUtils.indexOf("aabaabaa", "b", -1) = 2
	 * StringUtils.indexOf("aabaabaa", "", 2)   = 2
	 * StringUtils.indexOf("abc", "", 9)        = 3
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param searchStr 寃��깋臾몄옄�뿴
	 * @param startPos �떆�옉�씤�뜳�뒪
	 * @return 寃��깋 臾몄옄�뿴�쓽 泥� 踰덉㎏ �씤�뜳�뒪 �뾾�뒗 寃쎌슦-1 �씪移� �븯嫄곕굹 null 臾몄옄�뿴 �엯�젰
	 */
	public static int indexOf(String str, String searchStr, int startPos) {
		return org.apache.commons.lang.StringUtils.indexOf(str, searchStr, startPos);
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 寃��깋臾몄옄諛곗뿴以� �룷�븿�븯怨� �엳�뒗吏� 寃��깋�썑<br />
	 * 寃��깋�맂 泥ル쾲吏� �씤�뜳�뒪瑜� 諛섑솚�븳�떎.<br />
	 * 
	 * 臾몄옄�뿴�씠 <code>null</code> �씪寃쎌슦 <code>-1</code>�쓣 諛섑솚.<br />
	 * 寃��깋諛곗뿴�씠 <code>null</code> �삉�뒗 怨듬갚諛곗뿴�씪寃쎌슦 <code>-1</code>�쓣 諛섑솚.<br />
	 * 諛곗뿴�궡 寃��깋臾몄옄�뿴�씠 怨듬갚�씪寃쎌슦 <code>0</code>�쓣 諛섑솚. <br />
	 *
	 * <pre>
	 * StringUtils.indexOfAny(null, *)                     = -1
	 * StringUtils.indexOfAny(*, null)                     = -1
	 * StringUtils.indexOfAny(*, [])                       = -1
	 * StringUtils.indexOfAny("zzabyycdxx", ["ab","cd"])   = 2
	 * StringUtils.indexOfAny("zzabyycdxx", ["cd","ab"])   = 2
	 * StringUtils.indexOfAny("zzabyycdxx", ["mn","op"])   = -1
	 * StringUtils.indexOfAny("zzabyycdxx", ["zab","aby"]) = 1
	 * StringUtils.indexOfAny("zzabyycdxx", [""])          = 0
	 * StringUtils.indexOfAny("", [""])                    = 0
	 * StringUtils.indexOfAny("", ["a"])                   = -1
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param searchStrs 寃��깋臾몄옄�뿴
	 * @return 寃��깋�맂 泥ル쾲吏몄씤�뜳�뒪, 寃��깋�릺吏��븡�� 寃쎌슦 -1諛섑솚.
	 */
	public static int indexOfAny(String str, String[] searchStrs) {
		return org.apache.commons.lang.StringUtils.indexOfAny(str, searchStrs);
	}

	/**
	 * �몢臾몄옄�뿴瑜� 鍮꾧탳�븯�뿬 �떎瑜몃�遺꾩쓽 �씤�뜳�뒪瑜� 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.indexOfDifference(null, null) = -1
	 * StringUtils.indexOfDifference("", "") = -1
	 * StringUtils.indexOfDifference("", "abc") = 0
	 * StringUtils.indexOfDifference("abc", "") = 0
	 * StringUtils.indexOfDifference("abc", "abc") = -1
	 * StringUtils.indexOfDifference("ab", "abxyz") = 2
	 * StringUtils.indexOfDifference("abcde", "abxyz") = 2
	 * StringUtils.indexOfDifference("abcde", "xyz") = 0
	 * </pre>
	 *
	 * @param str1 臾몄옄�뿴1
	 * @param str2 臾몄옄�뿴2
	 * @return 寃곌낵臾몄옄�뿴, 鍮꾧탳�븯�뿬 媛숈쑝硫� -1
	 */
	public static int indexOfDifference(String str1, String str2) {
		return org.apache.commons.lang.StringUtils.indexOfDifference(str1, str2);
	}

	/**
	 * 諛곗뿴�븞�쓽 臾몄옄�뿴瑜� 鍮꾧탳�븯�뿬 �떎瑜몃�遺꾩쓽 �씤�뜳�뒪瑜� 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.indexOfDifference(null) = -1
	 * StringUtils.indexOfDifference(new String[] {}) = -1
	 * StringUtils.indexOfDifference(new String[] {"abc"}) = -1
	 * StringUtils.indexOfDifference(new String[] {null, null}) = -1
	 * StringUtils.indexOfDifference(new String[] {"", ""}) = -1
	 * StringUtils.indexOfDifference(new String[] {"", null}) = 0
	 * StringUtils.indexOfDifference(new String[] {"abc", null, null}) = 0
	 * StringUtils.indexOfDifference(new String[] {null, null, "abc"}) = 0
	 * StringUtils.indexOfDifference(new String[] {"", "abc"}) = 0
	 * StringUtils.indexOfDifference(new String[] {"abc", ""}) = 0
	 * StringUtils.indexOfDifference(new String[] {"abc", "abc"}) = -1
	 * StringUtils.indexOfDifference(new String[] {"abc", "a"}) = 1
	 * StringUtils.indexOfDifference(new String[] {"ab", "abxyz"}) = 2
	 * StringUtils.indexOfDifference(new String[] {"abcde", "abxyz"}) = 2
	 * StringUtils.indexOfDifference(new String[] {"abcde", "xyz"}) = 0
	 * StringUtils.indexOfDifference(new String[] {"xyz", "abcde"}) = 0
	 * StringUtils.indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
	 * </pre>
	 *
	 * @param strs 臾몄옄諛곗뿴
	 * @return 寃곌낵臾몄옄�뿴, 鍮꾧탳�븯�뿬 媛숈쑝硫� -1
	 * @since 2.4
	 */
	public static int indexOfDifference(String[] strs) {
		return org.apache.commons.lang.StringUtils.indexOfDifference(strs);
	}

	/**
	 * ���냼臾몄옄瑜� 援щ텇�뾾�씠 湲곗� 臾몄옄�뿴�뿉�꽌 李얘퀬�옄 �븯�뒗 臾몄옄�뿴�씠 �룷�븿�릺�뼱 �엳�뒗 寃쎌슦 洹� 泥ル쾲吏� 臾몄옄�뿴�쓽 �씤�뜳�뒪瑜� 諛섑솚�븳�떎.<br><br>
	 *
	 * StringUtils.indexOfIgnoreCase("Anyframe Java Test", "java") = 9
	 *
	 * @param str 湲곗� 臾몄옄�뿴
	 * @param search 寃��깋�븷 臾몄옄�뿴
	 * @return 泥ル쾲吏� 臾몄옄�뿴�쓽 �씤�뜳�뒪
	 * @see String#indexOf(String)
	 */
	public static int indexOfIgnoreCase(String str, String search) {
		if (str == null || search == null) {
			return -1;
		}
		return str.toLowerCase().indexOf(search.toLowerCase());
	}

	/**
	 * �엯�젰�맂 臾몄옄�뿴�씠 �쑀�땲肄붾뱶 臾몄옄濡쒕쭔 援ъ꽦�릺�뿀�뒗吏� 泥댄겕<br><br>
	 *
	 * StringUtils.isAlpha("abcfds") = true
	 *
	 * @param str 臾몄옄�뿴
	 * @return 臾몄옄�뿴�씠 �쑀�땲肄붾뱶 臾몄옄濡쒕쭔 援ъ꽦�릺�뼱�엳�쓣 寃쎌슦 true
	 */
	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		if (sz == 0) {
			return false;
		}
		for (int i = 0; i < sz; i++) {
			if (!Character.isLetter(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 臾몄옄�뿴�씠 紐⑤몢 臾몄옄 �삉�뒗 �닽�옄�씤吏� �솗�씤�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.isAlphanumeric(null)   = false
	 * StringUtils.isAlphanumeric("")     = true
	 * StringUtils.isAlphanumeric("  ")   = false
	 * StringUtils.isAlphanumeric("abc")  = true
	 * StringUtils.isAlphanumeric("ab c") = false
	 * StringUtils.isAlphanumeric("ab2c") = true
	 * StringUtils.isAlphanumeric("ab-c") = false
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 臾몄옄 �삉�뒗 �닽�옄 �삉�뒗 臾몄옄�뿴�씠 null�씠 �븘�땺寃쎌슦 <code>true</code>
	 */
	public static boolean isAlphanumeric(String str) {
		return org.apache.commons.lang.StringUtils.isAlphanumeric(str);
	}

	/**
	 * 臾몄옄�뿴�씠 紐⑤몢 臾몄옄 �삉�뒗 �닽�옄 �씠嫄곕굹 <br />
	 * 怨듬갚臾몄옄�씤吏� �솗�씤�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.isAlphanumericSpace(null)   = false
	 * StringUtils.isAlphanumericSpace("")     = true
	 * StringUtils.isAlphanumericSpace("  ")   = true
	 * StringUtils.isAlphanumericSpace("abc")  = true
	 * StringUtils.isAlphanumericSpace("ab c") = true
	 * StringUtils.isAlphanumericSpace("ab2c") = true
	 * StringUtils.isAlphanumericSpace("ab-c") = false
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 臾몄옄�뿴�쓽 臾몄옄媛� 臾몄옄 �삉�뒗 �닽�옄�씠嫄곕굹 怨듬갚臾몄옄 �삉�뒗 臾몄옄�뿴�씠 null�씠 �븘�땺寃쎌슦 <code>true</code>
	 */
	public static boolean isAlphanumericSpace(String str) {
		return org.apache.commons.lang.StringUtils.isAlphanumericSpace(str);
	}

	/**
	 * 臾몄옄�뿴�씠 紐⑤몢 臾몄옄 �씠嫄곕굹 怨듬갚臾몄옄�씤寃껋쓣 �솗�씤�븳�떎.<br />
	 *
	 * <code>null</code> will return <code>false</code>
	 * An empty String (length()=0) will return <code>true</code>.<br />
	 *
	 * <pre>
	 * StringUtils.isAlphaSpace(null)   = false
	 * StringUtils.isAlphaSpace("")     = true
	 * StringUtils.isAlphaSpace("  ")   = true
	 * StringUtils.isAlphaSpace("abc")  = true
	 * StringUtils.isAlphaSpace("ab c") = true
	 * StringUtils.isAlphaSpace("ab2c") = false
	 * StringUtils.isAlphaSpace("ab-c") = false
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 臾몄옄�뿴�쓽 臾몄옄媛� 紐⑤몢 臾몄옄�씠嫄곕굹 怨듬갚臾몄옄 �삉�뒗 臾몄옄�뿴�씠 null�씠 �븘�땺寃쎌슦 <code>true</code>
	 */
	public static boolean isAlphaSpace(String str) {
		return org.apache.commons.lang.StringUtils.isAlphaSpace(str);
	}

	/**
	 * printable�븳 臾몄옄�뿴�씤媛�瑜� �솗�씤�븳�떎.<br />
	 * 諛섑솚移섍� �똱eturn ch >= 32 && ch < 127�띻꼍�슦 �삉�뒗 怨듬갚臾몄옄�뒗 true<br />
	 * 
	 * <pre>
	 * StringUtils.isAsciiPrintable(null)     = false
	 * StringUtils.isAsciiPrintable("")       = true
	 * StringUtils.isAsciiPrintable(" ")      = true
	 * StringUtils.isAsciiPrintable("Ceki")   = true
	 * StringUtils.isAsciiPrintable("ab2c")   = true
	 * StringUtils.isAsciiPrintable("!ab-c~") = true
	 * StringUtils.isAsciiPrintable("\u0020") = true
	 * StringUtils.isAsciiPrintable("\u0021") = true
	 * StringUtils.isAsciiPrintable("\u007e") = true
	 * StringUtils.isAsciiPrintable("\u007f") = false
	 * StringUtils.isAsciiPrintable("Ceki G\u00fclc\u00fc") = false
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 臾몄옄�뿴�쓽 紐⑤뱺 臾몄옄媛�  printable�븳 臾몄옄�씪寃쎌슦 <code>true</code> 
	 */
	public static boolean isAsciiPrintable(String str) {
		return org.apache.commons.lang.StringUtils.isAsciiPrintable(str);
	}

	/**
	 * 臾몄옄�뿴�씠 鍮� 怨듬갚 �삉�뒗 NULL�씤吏� �솗�씤�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return null �씪寃쎌슦 true
	 *         怨듬갚臾몄옄 �씪寃쎌슦  true
	 *         �씠�쇅�쓽 寃쎌슦 false 
	 */
	public static boolean isBlank(String str) {
	    return org.apache.commons.lang.StringUtils.isBlank(str);
	}

	//以묐났�릺嫄곕굹 遺덊븘�슂�븳 硫붿냼�뱶 �뿬湲곌퉴吏�...�솗�씤. -> 
	/**
	 * 二쇱뼱吏� String�씠 '�닽�옄'濡쒕쭔 援ъ꽦�릺�뼱 �엳�뒗吏�瑜� �뙋蹂꾪븳�떎.<br>
	 * �닽�옄�씤吏��쓽 �뙋蹂꾩� Java�쓽 湲곕낯 �뙋蹂� 湲곗��쓣 以��닔�븳�떎.<br> 
	 * 二쇱뼱吏� String�씠 null�씪 寃쎌슦, false瑜� return�븳�떎.<br><br>
	 * 
	 * StringUtils.isDigit("1234") = true<br>
	 * StringUtils.isDigit("1234A") = false
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return true if String contains only digits, false if not or null string
	 *         input
	 */
	public static boolean isDigit(String str) {
		if (str == null) {
			return false;
		}
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 二쇱뼱吏� 臾몄옄�뿴�씠 null �삉�뒗 怨듬갚�씪 寃쎌슦 李� 諛섑솚<br><br>
	 *
	 * StringUtils.isEmpty("") = true
	 *
	 * @param str 臾몄옄�뿴
	 * @return null �삉�뒗 怨듬갚�씪 寃쎌슦 true
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}

	// Mid
	//-----------------------------------------------------------------------
	/**
	 * trim�븳 臾몄옄�뿴�씠 null �삉�뒗 怨듬갚�씪 寃쎌슦 李� 諛섑솚<br><br>
	 *
	 * StringUtils.isEmptyTrimmed(" ") = true
	 *
	 * @param str 臾몄옄�뿴
	 * @return trim�븳 臾몄옄�뿴�씠 null �삉�뒗 怨듬갚�씪 寃쎌슦 true
	 */
	public static boolean isEmptyTrimmed(String str) {
		return (str == null || str.trim().length() == 0);
	}

	// Mid
	//-----------------------------------------------------------------------
	/**
	 * 二쇱뼱吏� String�씠 �듅�젙�븳 �룷留룹쑝濡� 援ъ꽦�릺�뿀�뒗吏�瑜� 寃��궗�븳�떎.
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param pattern
	 *            the pattern to check, may be null
	 * @return true if String contains the given pattern, false if not or null
	 *         string input
	 */
	public static boolean isFormattedString(String str, String pattern) {
		if (str == null || pattern == null) {
			return false;
		} else {
			return str.matches(pattern);
		}
	}

	/**
	 * 二쇱뼱吏� character媛� �븳湲��씤吏��쓽 �뿬遺�瑜� �뙋蹂꾪븳�떎.<br><br>
	 *
	 * StringUtils.isHangul('媛�') = true<br>
	 * StringUtils.isHangul('T') = false
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return true if the String contains only Korean Language, false if not
	 */
	public static boolean isHangul(char str) {
		String unicodeBlock = Character.UnicodeBlock.of(str).toString();
		return unicodeBlock.equals("HANGUL_JAMO")
				|| unicodeBlock.equals("HANGUL_SYLLABLES")
				|| unicodeBlock.equals("HANGUL_COMPATIBILITY_JAMO");
	}

	/**
	 * 二쇱뼱吏� String�뿉 ���빐�꽌, �븳湲�濡쒕쭔 �릺�뼱 �엳�뒗吏� �샊�� �븳湲��씠 �룷�븿�릺�뼱 �엳�뒗吏�瑜� �뙋蹂꾪븳�떎.<br>
	 * full�쓣 true濡� �꽕�젙�븷 寃쎌슦, �븳湲�濡쒕쭔 �릺�뼱 �엳�뒗吏�瑜� �뙋蹂꾪븳�떎.<br>
	 * '�븳湲�濡쒕쭔 �릺�뼱 �엳�뒗吏�'�뒗 �쁺�뼱�굹 湲고� �뼵�뼱媛� �븘�떂�쓣 �쓽誘명븯�뒗 寃껋씠 �븘�땲怨�,<br>
	 * �닽�옄�굹 湲고� 湲고샇 臾몄옄 �벑�룄 �뾾�쓬�쓣 �쓽誘명븳�떎.<br>
	 * full�쓣 false濡� �꽕�젙�븷 寃쎌슦, �븳湲��씠 �븯�굹�씪�룄 �룷�븿�릺�뼱 �엳�뒗吏�瑜� �뙋蹂꾪븳�떎.<br><br>
	 *
	 * StringUtils.isHangul("媛��굹�떎", true) = true<br>
	 * StringUtils.isHangul("媛��굹�떎abc", true) = false<br>
	 * StringUtils.isHangul("媛�abc", false) = true<br>
	 * StringUtils.isHangul("abcd", false) = false
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param checkForAll
	 *            flag for check only Korean characters(true) or any Korean
	 *            characters(false)
	 * @return true if the String contains only Korean Language(when checkForAll
	 *         is true) or any Korean characters(when checkForAll is false),
	 *         false if not
	 */
	public static boolean isHangul(String str, boolean checkForAll) {
		char[] chars = str.toCharArray();
		if (!checkForAll) {
			for (int i = 0; i < chars.length; i++) {
				if (isHangul(chars[i])) {
					return true;
				}
			}
			return false;
		} else {
			for (int i = 0; i < chars.length; i++) {
				if (!isHangul(chars[i])) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * 二쇱뼱吏� String�씠 '臾몄옄'濡쒕쭔 援ъ꽦�릺�뼱 �엳�뒗吏�瑜� �뙋蹂꾪븳�떎.<br>
	 * 臾몄옄�씤吏��쓽 �뙋蹂꾩� Java�쓽 湲곕낯 �뙋蹂� 湲곗��쓣 以��닔�븳�떎.<br>
	 * 二쇱뼱吏� String�씠 null�씪 寃쎌슦, false瑜� return�븳�떎.<br><br>
	 *
	 * StringUtils.isLetter("test") = true<br>
	 * StringUtils.isLetter("test媛��굹")= true<br>
	 * StringUtils.isLetter("test#$%") = false<br>
	 * StringUtils.isLetter("test123") = false
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return true if String contains only letters, false if not or null string
	 *         input
	 */
	public static boolean isLetter(String str) {
		if (str == null) {
			return false;
		}
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isLetter(chars[i])) {
				return false;
			}
		}
		return true;
	}

	// Mid
	//-----------------------------------------------------------------------
	/**
	 * 二쇱뼱吏� String�씠 '臾몄옄'�굹 '�닽�옄'濡쒕쭔 援ъ꽦�릺�뼱 �엳�뒗吏�瑜� �뙋蹂꾪븳�떎.<br>
	 * 臾몄옄�굹 �닽�옄�씤吏��쓽 �뙋蹂꾩� Java�쓽 湲곕낯 �뙋蹂� 湲곗��쓣 以��닔�븳�떎.<br>
	 * 二쇱뼱吏� String�씠 null�씪 寃쎌슦, false瑜� return�븳�떎.<br><br>
	 * 
	 * StringUtils.isLetterOrDigit("12媛��굹") = true<br>
	 * StringUtils.isLetterOrDigit("12媛��굹@#%") = false
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return true if String contains only letters or digits, false if not or
	 *         null string input
	 */
	public static boolean isLetterOrDigit(String str) {
		if (str == null) {
			return false;
		}
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isLetterOrDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 臾몄옄�뿴�씠 鍮� 怨듬갚 �삉�뒗 NULL�씠 �븘�땶吏� �솗�씤�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return null�씠 �븘�땺寃쎌슦 true
	 *         怨듬갚臾몄옄媛� �븘�땺寃쎌슦  true
	 *         �씠�쇅�쓽 寃쎌슦 false 
	 */
	public static boolean isNotBlank(String str) {
	    return org.apache.commons.lang.StringUtils.isNotBlank(str);
	}

	// Mid
	//-----------------------------------------------------------------------
	/**
	 * 二쇱뼱吏� 臾몄옄�뿴�씠 null �삉�뒗 怨듬갚�씠 �븘�땺 寃쎌슦 李� 諛섑솚<br><br>
	 *
	 * StringUtils.isNotEmpty("abc") = true
	 *
	 * @param str 臾몄옄�뿴
	 * @return null �삉�뒗 怨듬갚�씠 �븘�땺 寃쎌슦 true
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * �엯�젰�씤�옄濡� �쟾�떖�맂 臾몄옄�뿴�씠 �닽�옄媛� �븘�땶 臾몄옄媛� �룷�븿�릺�뼱�엳�뒗吏� �뿬遺�瑜� 由ы꽩�븳�떎.<br><br>
	 *
	 * StringUtils.isNotNumeric("12345") = false<br>
	 * StringUtils.isNumeric("12345ABC") = true
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return true if String contains any letters, false if not or null string
	 *         input
	 */
	public static boolean isNotNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return true;
			}
		}
	
		return false;
	}
	
	// Mid
	//-----------------------------------------------------------------------
	/**
	 * 臾몄옄�뿴�씠 紐⑤몢 �닽�옄�씤媛�瑜� �솗�씤�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.isNumeric(null)   = false
	 * StringUtils.isNumeric("")     = true
	 * StringUtils.isNumeric("  ")   = false
	 * StringUtils.isNumeric("123")  = true
	 * StringUtils.isNumeric("12 3") = false
	 * StringUtils.isNumeric("ab2c") = false
	 * StringUtils.isNumeric("12-3") = false
	 * StringUtils.isNumeric("12.3") = false
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 臾몄옄�뿴�쓽 紐⑤뱺 臾몄옄媛� �닽�옄 �삉�뒗 臾몄옄�뿴�씠 null�씠 �븘�땺寃쎌슦<code>true</code>
	 */
	public static boolean isNumeric(String str) {
		return org.apache.commons.lang.StringUtils.isNumeric(str);
	}

	/**
	 * 臾몄옄�뿴�씠 �닽�옄 �삉�뒗 怨듬갚臾몄옄�씤媛�瑜� �솗�씤�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.isNumericSpace(null)   = false
	 * StringUtils.isNumericSpace("")     = true
	 * StringUtils.isNumericSpace("  ")   = true
	 * StringUtils.isNumericSpace("123")  = true
	 * StringUtils.isNumericSpace("12 3") = true
	 * StringUtils.isNumericSpace("ab2c") = false
	 * StringUtils.isNumericSpace("12-3") = false
	 * StringUtils.isNumericSpace("12.3") = false
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 臾몄옄�뿴�씠 �닽�옄 �삉�뒗 怨듬갚臾몄옄 �씠嫄곕굹 臾몄옄�뿴�씠 null�씠 �븘�땺寃쎌슦<code>true</code>
	 */
	public static boolean isNumericSpace(String str) {
		return org.apache.commons.lang.StringUtils.isNumericSpace(str);
	}
	
	// Mid
	//-----------------------------------------------------------------------
	/**
	 * 二쇱뼱吏� String�씠 Space留뚯쓣 媛�吏�怨� �엳�뒗吏�瑜� 寃��궗�븳�떎.<br>
	 * Space�쓽 �뙋蹂� 湲곗���, String.trim()�뿉�꽌 �젣嫄곕릺�뒗 ���긽�쓣 湲곗��쑝濡� �븳�떎.<br>
	 * 二쇱뼱吏� String�씠 null�씠硫�, false瑜� return�븳�떎.<br><br>
	 *
	 * StringUtils.isSpaceOnly("   ") = true<br>
	 * StringUtils.isSpaceOnly("") = true<br>
	 * StringUtils.isSpaceOnly("test") = false
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return true if String contains only whitespace, false if not or null
	 *         string input
	 */
	public static boolean isSpaceOnly(String str) {
		if (str == null) {
			return false;
		} else {
			return str.trim().length() <= 0;
		}
	}

	/**
	 * 臾몄옄�뿴�씠 怨듬갚 �삉�뒗 怨듬갚臾몄옄�씤媛�瑜� �솗�씤�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.isWhitespace(null)   = false
	 * StringUtils.isWhitespace("")     = true
	 * StringUtils.isWhitespace("  ")   = true
	 * StringUtils.isWhitespace("abc")  = false
	 * StringUtils.isWhitespace("ab2c") = false
	 * StringUtils.isWhitespace("ab-c") = false
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 臾몄옄�뿴�씠 怨듬갚 �삉�뒗 怨듬갚臾몄옄 �씠嫄곕굹 臾몄옄�뿴�씠 null�씠 �븘�땺寃쎌슦<code>true</code>
	 */
	public static boolean isWhitespace(String str) {
		return org.apache.commons.lang.StringUtils.isWhitespace(str);
	}
	
	// Mid
	//-----------------------------------------------------------------------
	/**
	 * collection�뿉�꽌 臾몄옄�뿴�쓣 �씫�뼱�� 援щ텇�옄濡� �뿰寃곗떆�궓�떎.<br />
	 *
	 * @param collection Collection
	 * @param separator 援щ텇�옄
	 * @return 寃곌낵臾몄옄�뿴, collection媛� null�씪寃쎌슦 <code>null</code>
	 */
	@SuppressWarnings("rawtypes")
	public static String join(Collection collection, char separator) {
		return org.apache.commons.lang.StringUtils.join(collection, separator);
	}
	
	// Mid
	//-----------------------------------------------------------------------
	/**
	 * collection�뿉�꽌 臾몄옄�뿴�쓣 �씫�뼱�� 援щ텇�옄濡� �뿰寃곗떆�궓�떎.<br />
	 *
	 * @param collection Collection
	 * @param separator 援щ텇�옄
	 * @return 寃곌낵臾몄옄�뿴, collection媛� null�씪寃쎌슦 <code>null</code>
	 */
	@SuppressWarnings("rawtypes")
	public static String join(Collection collection, String separator) {
		return org.apache.commons.lang.StringUtils.join(collection, separator);
	}

	/**
	 * iterator�뿉�꽌 臾몄옄�뿴�쓣 �씫�뼱�� 援щ텇�옄濡� �뿰寃곗떆�궓�떎.<br />
	 * 
	 * @param iterator Iterator
	 * @param separator 援щ텇�옄
	 * @return 寃곌낵臾몄옄�뿴, iterator媛� null�씪寃쎌슦 <code>null</code>
	 */
	@SuppressWarnings("rawtypes")
	public static String join(Iterator iterator, char separator) {
		return org.apache.commons.lang.StringUtils.join(iterator, separator);
	}

	// Mid
	//-----------------------------------------------------------------------
	/**
	 * iterator�뿉�꽌 臾몄옄�뿴�쓣 �씫�뼱�� 援щ텇�옄濡� �뿰寃곗떆�궓�떎.<br />
	 *
	 * @param iterator Iterator
	 * @param separator 援щ텇�옄
	 * @return 寃곌낵臾몄옄�뿴, iterator媛� null�씪寃쎌슦 <code>null</code>
	 */
	@SuppressWarnings("rawtypes")
	public static String join(Iterator iterator, String separator) {
		return org.apache.commons.lang.StringUtils.join(iterator, separator);
	}

	/**
	 * 諛곗뿴�뿉�꽌 臾몄옄�뿴�쓣 �씫�뼱�� 紐⑤몢 �뿰寃곗떆�궓�떎.<br />
	 *
	 * <pre>
	 * StringUtils.join(null)            = null
	 * StringUtils.join([])              = ""
	 * StringUtils.join([null])          = ""
	 * StringUtils.join(["a", "b", "c"]) = "abc"
	 * StringUtils.join([null, "", "a"]) = "a"
	 * </pre>
	 *
	 * @param array 諛곗뿴
	 * @return 寃곌낵臾몄옄�뿴, 諛곗뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String join(Object[] array) {
		return org.apache.commons.lang.StringUtils.join(array);
	}

	/**
	 * 諛곗뿴�뿉�꽌 臾몄옄�뿴�쓣 �씫�뼱�� 紐⑤몢 �뿰寃곗떆�궓�떎.<br />
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 *
	 * @param array 諛곗뿴
	 * @param separator 援щ텇�옄
	 * @return 寃곌낵臾몄옄�뿴, 諛곗뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String join(Object[] array, char separator) {
		return org.apache.commons.lang.StringUtils.join(array, separator);
	}

	/**
	 * 諛곗뿴�뿉�꽌 臾몄옄�뿴�쓣 �씫�뼱�� 諛곗뿴�쓽 �떆�옉�씤�뜳�뒪遺��꽣 �걹�씤�뜳�뒪�쟾源뚯� <br />
	 * 援щ텇�옄瑜� �궗�씠�뿉�몢怨� �뿰寃곗떆�궓�떎.<br />
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 *
	 * @param array 諛곗뿴
	 * @param separator 援щ텇�옄
	 * @param startIndex �떆�옉�씤�뜳�뒪
	 * @param endIndex �걹�씤�뜳�뒪
	 * @return 寃곌낵臾몄옄�뿴, 諛곗뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String join(Object[] array, char separator, int startIndex, int endIndex) {
		return org.apache.commons.lang.StringUtils.join(array, separator, startIndex, endIndex);
	}

	/**
	 * 諛곗뿴�뿉�꽌 臾몄옄�뿴�쓣 �씫�뼱�� 紐⑤몢 援щ텇�옄瑜� �궗�씠�뿉�몢怨� �뿰寃곗떆�궓�떎.<br />
	 *
	 * <pre>
	 * StringUtils.join(null, *)                = null
	 * StringUtils.join([], *)                  = ""
	 * StringUtils.join([null], *)              = ""
	 * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
	 * StringUtils.join(["a", "b", "c"], null)  = "abc"
	 * StringUtils.join(["a", "b", "c"], "")    = "abc"
	 * StringUtils.join([null, "", "a"], ',')   = ",,a"
	 * </pre>
	 *
	 * @param array 諛곗뿴
	 * @param separator 援щ텇�옄
	 * @return 寃곌낵臾몄옄�뿴, 諛곗뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String join(Object[] array, String separator) {
		return org.apache.commons.lang.StringUtils.join(array, separator);
	}

	/**
	 * 諛곗뿴�뿉�꽌 臾몄옄�뿴�쓣 �씫�뼱�� 諛곗뿴�쓽 �떆�옉�씤�뜳�뒪遺��꽣 �걹�씤�뜳�뒪�쟾源뚯� <br />
	 * 援щ텇�옄瑜� �궗�씠�뿉�몢怨� �뿰寃곗떆�궓�떎.<br />
	 *
	 * <pre>
	 * StringUtils.join(null, *)                = null
	 * StringUtils.join([], *)                  = ""
	 * StringUtils.join([null], *)              = ""
	 * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
	 * StringUtils.join(["a", "b", "c"], null)  = "abc"
	 * StringUtils.join(["a", "b", "c"], "")    = "abc"
	 * StringUtils.join([null, "", "a"], ',')   = ",,a"
	 * </pre>
	 *
	 * @param array 諛곗뿴
	 * @param separator 援щ텇�옄
	 * @param startIndex �떆�옉�씤�뜳�뒪
	 * @param endIndex �걹�씤�뜳�뒪
	 * @return 寃곌낵臾몄옄�뿴, 諛곗뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String join(Object[] array, String separator, int startIndex, int endIndex) {
		return org.apache.commons.lang.StringUtils.join(array, separator, startIndex, endIndex);
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 留덉�留� 寃��깋臾몄옄�쓽 �씤�뜳�뒪瑜� 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.lastIndexOf(null, *)         = -1
	 * StringUtils.lastIndexOf("", *)           = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'a') = 7
	 * StringUtils.lastIndexOf("aabaabaa", 'b') = 5
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param searchChar 寃��깋臾몄옄
	 * @return 寃��깋 臾몄옄 留덉�留� �씤�뜳�뒪 �뾾�뒗 寃쎌슦-1 �씪移� �삉�뒗 null 臾몄옄�뿴 �엯�젰
	 */
	public static int lastIndexOf(String str, char searchChar) {
		return org.apache.commons.lang.StringUtils.lastIndexOf(str, searchChar);
	}

	// Mid
	//-----------------------------------------------------------------------
	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 �떆�옉�씤�뜳�뒪濡쒕��꽣 留덉�留� 寃��깋臾몄옄�쓽 �씤�뜳�뒪瑜� 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.lastIndexOf(null, *, *)          = -1
	 * StringUtils.lastIndexOf("", *,  *)           = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 8)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 4)  = 2
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 0)  = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 9)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'a', 0)  = 0
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param searchChar 寃��깋臾몄옄
	 * @param startPos �떆�옉�씤�뜳�뒪
	 * @return 寃��깋 臾몄옄 留덉�留� �씤�뜳�뒪 �뾾�뒗 寃쎌슦-1 �씪移� �삉�뒗 null 臾몄옄�뿴 �엯�젰
	 */
	public static int lastIndexOf(String str, char searchChar, int startPos) {
		return org.apache.commons.lang.StringUtils.lastIndexOf(str, searchChar, startPos);
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 留덉�留� 寃��깋臾몄옄�뿴�쓽 �씤�뜳�뒪瑜� 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.lastIndexOf(null, *)          = -1
	 * StringUtils.lastIndexOf(*, null)          = -1
	 * StringUtils.lastIndexOf("", "")           = 0
	 * StringUtils.lastIndexOf("aabaabaa", "a")  = 7
	 * StringUtils.lastIndexOf("aabaabaa", "b")  = 5
	 * StringUtils.lastIndexOf("aabaabaa", "ab") = 4
	 * StringUtils.lastIndexOf("aabaabaa", "")   = 8
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param searchStr 寃��깋臾몄옄�뿴
	 * @return 寃��깋 臾몄옄�뿴�쓽 留덉�留� �씤�뜳�뒪 �뾾�뒗 寃쎌슦-1 �씪移� �삉�뒗 null 臾몄옄�뿴 �엯�젰
	 */
	public static int lastIndexOf(String str, String searchStr) {
		return org.apache.commons.lang.StringUtils.lastIndexOf(str, searchStr);
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 �떆�옉�씤�뜳�뒪濡쒕��꽣 �븵�쑝濡� 寃��깋臾몄옄�뿴�씤�뜳�뒪瑜� 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.lastIndexOf(null, *, *)          = -1
	 * StringUtils.lastIndexOf(*, null, *)          = -1
	 * StringUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
	 * StringUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
	 * StringUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", "b", -1) = -1
	 * StringUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
	 * StringUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param searchStr 寃��깋臾몄옄�뿴
	 * @param startPos �떆�옉�씤�뜳�뒪
	 * @return 寃��깋 臾몄옄�뿴�쓽 留덉�留� �씤�뜳�뒪 �뾾�뒗 寃쎌슦-1 �씪移� �삉�뒗 null 臾몄옄�뿴 �엯�젰
	 */
	public static int lastIndexOf(String str, String searchStr, int startPos) {
		return org.apache.commons.lang.StringUtils.lastIndexOf(str, searchStr, startPos);
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 寃��깋臾몄옄諛곗뿴以� �룷�븿�븯怨� �엳�뒗吏� 寃��깋�썑<br />
	 * 理쒗썑�뿉 寃��깋�맂 �씤�뜳�뒪瑜� 諛섑솚�븳�떎.<br />
	 * 
	 * 臾몄옄�뿴�씠 <code>null</code> �씪寃쎌슦 <code>-1</code>�쓣 諛섑솚.<br />
	 * 寃��깋諛곗뿴�씠 <code>null</code> �삉�뒗 怨듬갚諛곗뿴�씪寃쎌슦 <code>-1</code>�쓣 諛섑솚.<br />
	 * 諛곗뿴�궡 寃��깋臾몄옄�뿴�씠 <code>null</code> �삉�뒗 怨듬갚�씪寃쎌슦 <code>-1</code>�쓣 諛섑솚. <br />
	 *
	 * <pre>
	 * StringUtils.lastIndexOfAny(null, *)                   = -1
	 * StringUtils.lastIndexOfAny(*, null)                   = -1
	 * StringUtils.lastIndexOfAny(*, [])                     = -1
	 * StringUtils.lastIndexOfAny(*, [null])                 = -1
	 * StringUtils.lastIndexOfAny("zzabyycdxx", ["ab","cd"]) = 6
	 * StringUtils.lastIndexOfAny("zzabyycdxx", ["cd","ab"]) = 6
	 * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn","op"]) = -1
	 * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn","op"]) = -1
	 * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn",""])   = 10
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param searchStrs 寃��깋臾몄옄�뿴
	 * @return 寃��깋�맂 理쒗썑�쓽 �씤�뜳�뒪, 寃��깋�릺吏��븡�� 寃쎌슦 -1諛섑솚.
	 */
	public static int lastIndexOfAny(String str, String[] searchStrs) {
		return org.apache.commons.lang.StringUtils.lastIndexOfAny(str, searchStrs);
	}

	/**
	 * 二쇱뼱吏� String 媛앹껜�뿉 ���빐�꽌 二쇱뼱吏� 湲몄씠留뚰겮 �쇊履� 遺�遺꾩쓣 �뼹�뼱 諛섑솚�븳�떎.<br>
	 * 二쇱뼱吏� 湲몄씠蹂대떎 二쇱뼱吏� String�쓽 湲몄씠媛� �옉�쓣 寃쎌슦�뿉�뒗 二쇱뼱吏� String�쓣 洹몃�濡� 諛섑솚�븳�떎.<br>
	 * "..."�쓣 遺숈씠吏� �븡�뒗 �젏�쓣 �젣�쇅�븯怨좊뒗 splitHead()�� �룞�씪�븯�떎.<br><br>
	 *
	 * StringUtils.left("1234567", 3) = "123"
	 *
	 * @param str
	 *            the String to get the leftmost characters from, may be null
	 * @param len
	 *            the length of the required String
	 * @return the leftmost characters, null if null String input
	 */
	public static String left(String str, int len) {
		if (str == null) {
			return null;
		} else if (len <= 0 || str.length() <= len) {
			return str;
		} else {
			return str.substring(0, len);
		}
	}

	/**
	 * �빐�떦�븯�뒗 臾몄옄�뿴�뿉 ���빐�꽌 �엯�젰�맂 湲몄씠留뚰겮 遺�議깊븳 湲몄씠瑜� �쇊履쎈��꽣 怨듬갚�쑝濡� 梨꾩썙�꽔�뒗�떎.<br><br>
	 *
	 * StringUtils.leftPad("Anyframe", 12) = "    Anyframe"
	 *
	 * @param str 臾몄옄�뿴
	 * @param size 怨듬갚�씠 梨꾩썙吏� 臾몄옄�뿴�쓽 �쟾泥� 湲몄씠
	 * @return 遺�議깊븳 湲몄씠留뚰겮 �쇊履쎈��꽣 怨듬갚�씠 梨꾩썙吏� 臾몄옄�뿴
	 */
	public static String leftPad(String str, int size) {
		return leftPad(str, size, ' ');
	}

	/**
	 * �빐�떦�븯�뒗 臾몄옄�뿴�뿉 ���빐�꽌 �엯�젰�맂 湲몄씠留뚰겮 遺�議깊븳 湲몄씠瑜� �쇊履쎈��꽣 吏��젙�맂 character濡� 梨꾩썙�꽔�뒗�떎.<br><br>
	 *
	 * StringUtils.leftPad("Anyframe", 12, 'a') = "aaaaAnyframe"
	 *
	 * @param str 臾몄옄�뿴
	 * @param size 罹먮┃�꽣媛� 梨꾩썙吏� 臾몄옄�뿴�쓽 �쟾泥� 湲몄씠
	 * @param padChar 梨꾩썙�꽔�쓣 罹먮┃�꽣
	 * @return 遺�議깊븳 湲몄씠留뚰겮 �쇊履쎈��꽣 罹먮┃�꽣媛� 梨꾩썙吏� 臾몄옄�뿴
	 */
	public static String leftPad(String str, int size, char padChar) {
		return padChar(str, size, padChar, true);
	}
	
	// Mid
	//-----------------------------------------------------------------------
	/**
	 * �빐�떦�븯�뒗 臾몄옄�뿴�뿉 ���빐�꽌 �엯�젰�맂 湲몄씠留뚰겮 遺�議깊븳 湲몄씠瑜� �쇊履쎈��꽣 吏��젙�맂 臾몄옄�뿴濡� 梨꾩썙�꽔�뒗�떎.<br><br>
	 *
	 * StringUtils.leftPad("Anyframe", 12, "Java") = "JavaAnyframe"
	 *
	 * @param str 臾몄옄�뿴
	 * @param size 臾몄옄�뿴�씠 梨꾩썙吏� 臾몄옄�뿴�쓽 �쟾泥� 湲몄씠
	 * @param padStr 梨꾩썙�꽔�쓣 臾몄옄�뿴
	 * @return 遺�議깊븳 湲몄씠留뚰겮 �쇊履쎈��꽣 臾몄옄�뿴�씠 梨꾩썙吏� 臾몄옄�뿴
	 */
	public static String leftPad(String str, int size, String padStr) {
		return padString(str, size, padStr, true);
	}

	/**
	 * 臾몄옄�뿴�쓽 �쇊履쎌쓽 怨듬갚 臾몄옄�뿴 �젣嫄�<br><br>
	 *
	 * StringUtils.leftTrim(" Anyframe Java Test") = "Anyframe Java Test"
	 *
	 * @param str 臾몄옄�뿴
	 * @return �쇊履� 怨듬갚�쓣 �젣嫄고븳 臾몄옄�뿴
	 * @see org.springframework.util.StringUtils#trimLeadingWhitespace(String)
	 */
	public static String leftTrim(String str) {
		return org.springframework.util.StringUtils.trimLeadingWhitespace(str);
	}

	/**
	 * 臾몄옄�뿴�쓣 �냼臾몄옄濡� 蹂��솚�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.lowerCase(null)  = null
	 * StringUtils.lowerCase("")    = ""
	 * StringUtils.lowerCase("aBc") = "abc"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String lowerCase(String str) {
		return org.apache.commons.lang.StringUtils.lowerCase(str);
	}

	/**
	 * 臾몄옄�뿴�쓽 pos �씤�뜳�뒪遺��꽣 len 湲몄씠留뚰겮�쓽 臾몄옄�뿴�쓣 援ы븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.mid(null, *, *)    = null
	 * StringUtils.mid(*, *, -ve)     = ""
	 * StringUtils.mid("", 0, *)      = ""
	 * StringUtils.mid("abc", 0, 2)   = "ab"
	 * StringUtils.mid("abc", 0, 4)   = "abc"
	 * StringUtils.mid("abc", 2, 4)   = "c"
	 * StringUtils.mid("abc", 4, 2)   = ""
	 * StringUtils.mid("abc", -2, 2)  = "ab"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param pos �떆�옉�씤�뜳�뒪
	 * @param len 湲몄씠 the length of the required String
	 * @return 寃곌낵 臾몄옄�뿴, 臾몄옄�뿴�씠 null�쓽 寃쎌슦 <code>null</code>
	 */
	public static String mid(String str, int pos, int len) {
		return org.apache.commons.lang.StringUtils.mid(str, pos, len);
	}

	/**
	 * CRLF(newLine)媛� �룷�븿�맂 臾몄옄�뿴�쓣 �엯�젰�씤�옄濡� 諛쏆븘 CRLF(媛쒗뻾臾몄옄)瑜� SPACE濡� 蹂��솚�븯�뿬 由ы꽩�븳�떎.<br><br>
	 * 
	 * StringUtils.newLineToSpace("\r\ntest") = " test"
	 *
	 * @param str
	 *            the String to convert
	 * @return the converted string
	 */
	public static String newLineToSpace(String str) {
		String output;
	
		output = str.replace("\r\n", " ");
	
		return output;
	}

	// Mid
	//-----------------------------------------------------------------------
	/**
	 * 二쇱뼱吏� String 媛앹껜瑜� 寃��궗�븯�뿬 null�씪 寃쎌슦 "" �쓣 諛섑솚�븯怨�, �븘�땲硫� �썝蹂몄쓣 諛섑솚�븳�떎.<br><br>
	 *
	 * StringUtils.nullToEmpty("test") = "test"<br>
	 * StringUtils.nullToEmpty(null) = ""
	 *
	 * @param str
	 *            the String to check
	 * @return empty string if the given String is null, given string if not
	 */
	public static String nullToEmpty(String str) {
		if (str == null || str.length() <= 0) {
			return DEFAULT_EMPTY_STRING;
		} else {
			return str;
		}
	}

	/**
	 * 二쇱뼱吏� Object媛� null�씠 �븘�땺 寃쎌슦 洹� Object瑜� 諛섑솚�븯怨�, null�씪 寃쎌슦 default Object瑜� 諛섑솚�븳�떎.<br><br>
	 *
	 * StringUtils.nvl(null, "NULL TEST") = "NULL TEST"<br>
	 * StringUtils.nvl("test", "NULL TEST") = "test"
	 *
	 * @param inputObject
	 *            the Object to check
	 * @param defaultObject
	 *            the default Object
	 * @return Returns the default Object if the given Object is null, returns
	 *         the given Object if not
	 */
	public static Object nvl(Object inputObject, Object defaultObject) {
		return inputObject != null ? inputObject : defaultObject;
	}

	/**
	 * 二쇱뼱吏� String�씠 null�씠 �븘�땺 寃쎌슦 洹� String�쓣 諛섑솚�븯怨�, null�씪 寃쎌슦 default String�쓣 諛섑솚�븳�떎.<br><br>
	 *
	 * StringUtils.nvl(null, "NULL TEST") = "NULL TEST"<br>
	 * StringUtils.nvl("test", "NULL TEST")) = "test"
	 *
	 * @param inputString
	 *            the String to check
	 * @param defaultString
	 *            the default String
	 * @return Returns the default String if the given String is null, returns
	 *         the given String if not
	 */
	public static String nvl(String inputString, String defaultString) {
		return (String) nvl((Object) inputString, (Object) defaultString);
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 寃��깋臾몄옄�뿴�쓽 n踰덉㎏ �빐�떦�븯�뒗 �씤�뜳�뒪瑜� 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.ordinalIndexOf(null, *, *)          = -1
	 * StringUtils.ordinalIndexOf(*, null, *)          = -1
	 * StringUtils.ordinalIndexOf("", "", *)           = 0
	 * StringUtils.ordinalIndexOf("aabaabaa", "a", 1)  = 0
	 * StringUtils.ordinalIndexOf("aabaabaa", "a", 2)  = 1
	 * StringUtils.ordinalIndexOf("aabaabaa", "b", 1)  = 2
	 * StringUtils.ordinalIndexOf("aabaabaa", "b", 2)  = 5
	 * StringUtils.ordinalIndexOf("aabaabaa", "ab", 1) = 1
	 * StringUtils.ordinalIndexOf("aabaabaa", "ab", 2) = 4
	 * StringUtils.ordinalIndexOf("aabaabaa", "", 1)   = 0
	 * StringUtils.ordinalIndexOf("aabaabaa", "", 2)   = 0
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param searchStr 寃��깋臾몄옄�뿴
	 * @param ordinal n踰덉㎏
	 * @return 寃��깋 臾몄옄�뿴�쓽 n 踰덉㎏ �씤�뜳�뒪-1 (INDEX_NOT_FOUND) �뾾�쑝硫� �씪移� �삉�뒗 null 臾몄옄�뿴 �엯�젰
	 */
	public static int ordinalIndexOf(String str, String searchStr, int ordinal) {
		return org.apache.commons.lang.StringUtils.ordinalIndexOf(str, searchStr, ordinal);
	}

	/**
	 * 臾몄옄�뿴�쓽 �떆�옉�젏遺��꽣 醫낅즺�젏源뚯� 蹂��솚臾몄옄濡� 蹂��솚�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.overlay(null, *, *, *)            = null
	 * StringUtils.overlay("", "abc", 0, 0)          = "abc"
	 * StringUtils.overlay("abcdef", null, 2, 4)     = "abef"
	 * StringUtils.overlay("abcdef", "", 2, 4)       = "abef"
	 * StringUtils.overlay("abcdef", "", 4, 2)       = "abef"
	 * StringUtils.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
	 * StringUtils.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
	 * StringUtils.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
	 * StringUtils.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
	 * StringUtils.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
	 * StringUtils.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param overlay 蹂��솚臾몄옄
	 * @param start �떆�옉�젏
	 * @param end 醫낅즺�젏
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String overlay(String str, String overlay, int start, int end) {
		return org.apache.commons.lang.StringUtils.overlay(str, overlay, start, end);
	}

	private static String padChar(String str, int size, char padChar, boolean isLeft) {
		if (str == null) {
			return null;
		}
		int originalStrLength = str.length();
	
		if (size < originalStrLength) {
			return str;
		}
	
		int difference = size - originalStrLength;
	
		StringBuilder strBuf = new StringBuilder();
		if (!isLeft) {
			strBuf.append(str);
		}
	
		for (int i = 0; i < difference; i++) {
			strBuf.append(padChar);
		}
	
		if (isLeft) {
			strBuf.append(str);
		}
	
		return strBuf.toString();
	}

	// Mid
	//-----------------------------------------------------------------------
	/**
	 * �듅�젙�븳 臾몄옄(char)�� �씪�젙�븳 湲몄씠 媛믪쓣 �엯�젰�쑝濡� 諛쏆븘 �빐�떦 �겕湲곕쭔�겮 臾몄옄媛� 諛섎났�릺�뒗 臾몄옄�뿴�쓣 �깮�꽦�븳�떎.<br>
	 * 二쇱뼱吏� 湲몄씠 媛믪씠 0�씠硫� ""�쓣 二쇱뼱吏� 湲몄씠 媛믪씠 0蹂대떎 �옉�쑝硫� null�쓣 由ы꽩�븳�떎.<br>
	 * length�뒗 String.getBytes().length 湲곗��씠 �븘�땶 String.length() 湲곗��엫�쓣 �쑀�쓽�븳�떎.<br><br>
	 *
	 * StringUtils.padding(5, 'e') = "eeeee"
	 *
	 * @param size
	 *            the length to pad to
	 * @param padChar
	 *            the character to pad with
	 * @return padded String
	 */
	public static String padding(int size, char padChar) {
		if (size < 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer(size);
		for (int i = 0; i < size; i++) {
			buffer.insert(i, padChar);
		}
		return buffer.toString();
	}
	
	// Mid
	//-----------------------------------------------------------------------
	private static String padString(String str, int size, String padStr, boolean isLeft) {
		if (str == null) {
			return null;
		}
		int originalStrLength = str.length();
	
		if (size < originalStrLength) {
			return str;
		}
	
		int difference = size - originalStrLength;
	
		String tempPad = "";
		if (difference > 0) {
			if (padStr == null || "".equals(padStr)) {
				padStr = " ";
			}
			do {
				for (int j = 0; j < padStr.length(); j++) {
					tempPad += padStr.charAt(j);
					if (str.length() + tempPad.length() >= size) {
						break;
					}
				}
			} while (difference > tempPad.length());
			if (isLeft) {
				str = tempPad + str;
			} else {
				str = str + tempPad;
			}
		}
	
		return str;
	}

	// Mid
	//-----------------------------------------------------------------------
	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 �궘�젣臾몄옄媛� �룷�븿�릺�뼱�엳�뒗 遺�遺꾩쓣 �궘�젣�썑 諛섑솚�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.remove(null, *)       = null
	 * StringUtils.remove("", *)         = ""
	 * StringUtils.remove("queued", 'u') = "qeed"
	 * StringUtils.remove("queued", 'z') = "queued"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param remove �궘�젣臾몄옄
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String remove(String str, char remove) {
		return org.apache.commons.lang.StringUtils.remove(str, remove);
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 �궘�젣臾몄옄�뿴�씠 �룷�븿�릺�뼱�엳�뒗 遺�遺꾩쓣 �궘�젣�썑 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.remove(null, *)        = null
	 * StringUtils.remove("", *)          = ""
	 * StringUtils.remove(*, null)        = *
	 * StringUtils.remove(*, "")          = *
	 * StringUtils.remove("queued", "ue") = "qd"
	 * StringUtils.remove("queued", "zz") = "queued"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param remove �궘�젣臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String remove(String str, String remove) {
		return org.apache.commons.lang.StringUtils.remove(str, remove);
	}

	/**
	 * �엯�젰�맂 臾몄옄�뿴�뿉 ���빐�꽌 �젣嫄고븷 臾몄옄�뿴�쓣 紐⑤몢 �젣嫄�<br><br>
	 *
	 * StringUtils.removeAll("Anyframe Java Test", "Java") = "Anyfrme Test"
	 *
	 * @param str 臾몄옄�뿴
	 * @param charsToDelete �젣嫄고븷 臾몄옄�뿴
	 * @return 臾몄옄�뿴�쓣 �젣嫄고븳 臾몄옄�뿴
	 * @see org.springframework.util.StringUtils#deleteAny(String, String)
	 */
	public static String removeAll(String str, String charsToDelete) {
		return org.springframework.util.StringUtils.deleteAny(str,
				charsToDelete);
	}

	/**
	 * �븳 String 媛앹껜 �븞�뿉�꽌 二쇱뼱吏� 臾몄옄(char)瑜� �젣嫄고븳�떎.<br><br>
	 *
	 * StringUtils.removeChar("ABBBBBC", 'B') = "AC"
	 *
	 * @param str
	 *            the source String to search
	 * @param remove
	 *            the char to search for and remove
	 * @return the substring with the char removed if found
	 */
	public static String removeChar(String str, char remove) {
		return replacePattern(str, String.valueOf(remove), "");
	}
	
	// Mid
	//-----------------------------------------------------------------------
	/**
	 * �븳 String 媛앹껜 �븞�뿉�꽌 �듅�젙 臾몄옄�뱾�쓣 �젣嫄고븳�떎.<br>
	 * �젣嫄고븷 ���긽 臾몄옄�뒗 �떎�쓬怨� 媛숇떎. {'/', '-', ':', ',', '.', '%' }<br><br>
	 *
	 * StringUtils.removeCharAll("test/-") = "test"
	 *
	 * @param str
	 *            the source String to search
	 * @return the substring with specified chars removed if found
	 */
	public static String removeCharAll(String str) {
		char[] targetCharacters = {'/', '-', ':', ',', '.', '%'};
		return removeCharAll(str, targetCharacters);
	}

	/**
	 * �븳 String 媛앹껜 �븞�뿉�꽌 二쇱뼱吏� 臾몄옄�뱾�쓣 �젣嫄고븳�떎.<br><br>
	 *
	 * StringUtils.removeCharAll("AbbzzB", new char[]{'b','z'}) = "AB"
	 *
	 * @param str
	 *            the source String to search
	 * @param remove
	 *            chars to search for (case insensitive) and remove
	 * @return the substring with given chars removed if found
	 */
	public static String removeCharAll(String str, char[] remove) {
		String value = str;
		for (int i = 0; i < remove.length; i++) {
			value = removeChar(value, remove[i]);
		}
		return value;
	}

	/**
	 * 臾몄옄�뿴�쓽 �뮘�뿉�꽌遺��꽣 �궘�젣臾몄옄�뿴怨� 媛숈� 遺�遺꾩쓣 �궘�젣�썑 �굹癒몄�瑜� 諛섑솚�븳�떎.<br /> 
	 *
	 * <pre>
	 * StringUtils.removeEnd(null, *)      = null
	 * StringUtils.removeEnd("", *)        = ""
	 * StringUtils.removeEnd(*, null)      = *
	 * StringUtils.removeEnd("www.domain.com", ".com.")  = "www.domain.com"
	 * StringUtils.removeEnd("www.domain.com", ".com")   = "www.domain"
	 * StringUtils.removeEnd("www.domain.com", "domain") = "www.domain.com"
	 * StringUtils.removeEnd("abc", "")    = "abc"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param remove �궘�젣臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String removeEnd(String str, String remove) {
		return org.apache.commons.lang.StringUtils.removeEnd(str, remove);
	}

	/**
	 * 臾몄옄�뿴�쓽 �뮘�뿉�꽌遺��꽣 �궘�젣臾몄옄�뿴怨� �쁺臾몃��냼臾몄옄 援щ텇�뾾�씠 媛숈� 遺�遺꾩쓣 �궘�젣�썑 �굹癒몄�瑜� 諛섑솚�븳�떎.<br /> 
	 *
	 * <pre>
	 * StringUtils.removeEndIgnoreCase(null, *)      = null
	 * StringUtils.removeEndIgnoreCase("", *)        = ""
	 * StringUtils.removeEndIgnoreCase(*, null)      = *
	 * StringUtils.removeEndIgnoreCase("www.domain.com", ".com.")  = "www.domain.com"
	 * StringUtils.removeEndIgnoreCase("www.domain.com", ".com")   = "www.domain"
	 * StringUtils.removeEndIgnoreCase("www.domain.com", "domain") = "www.domain.com"
	 * StringUtils.removeEndIgnoreCase("abc", "")    = "abc"
	 * StringUtils.removeEndIgnoreCase("www.domain.com", ".COM") = "www.domain")
	 * StringUtils.removeEndIgnoreCase("www.domain.COM", ".com") = "www.domain")
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param remove �궘�젣臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String removeEndIgnoreCase(String str, String remove) {
		return org.apache.commons.lang.StringUtils.removeEndIgnoreCase(str, remove);
	}

	/**
	 * unescaped�맂 臾몄옄�뿴�뿉 ���빐 HTML tag �삎�깭濡� 諛붽퓭以��떎.<br><br>
	 *
	 * StringUtils.removeEscapeChar("&lt;html&gt;Anyframe Java Test&lt;html&gt;") = "<html>Anyframe Java Test<html>"
	 *
	 * @param input unescaped�맂 臾몄옄�뿴
	 * @return HTML tag �삎�깭濡� 諛붾�� 臾몄옄�뿴
	 * @see HtmlUtils#htmlUnescape(String)
	 */
	public static String removeEscapeChar(String input) {
		return HtmlUtils.htmlUnescape(input);
	}

	/**
	 * 臾몄옄�뿴�뿉�꽌 �궘�젣臾몄옄�뿴�쓣 �궘�젣�븳�굹癒몄� 臾몄옄�뿴�쓣 諛섑솚�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.removeStart(null, *)      = null
	 * StringUtils.removeStart("", *)        = ""
	 * StringUtils.removeStart(*, null)      = *
	 * StringUtils.removeStart("www.domain.com", "www.")   = "domain.com"
	 * StringUtils.removeStart("domain.com", "www.")       = "domain.com"
	 * StringUtils.removeStart("www.domain.com", "domain") = "www.domain.com"
	 * StringUtils.removeStart("abc", "")    = "abc"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param remove �궘�젣臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String removeStart(String str, String remove) {
		return org.apache.commons.lang.StringUtils.removeStart(str, remove);
	}

	/**
	 * 臾몄옄�뿴�쓽 �떆�옉遺��꽣 �궘�젣臾몄옄�뿴怨� �쁺臾몃��냼臾몄옄 援щ텇�뾾�씠 媛숈� 遺�遺꾩쓣 �궘�젣�썑 �굹癒몄�瑜� 諛섑솚�븳�떎.<br /> 
	 *
	 * <pre>
	 * StringUtils.removeStartIgnoreCase(null, *)      = null
	 * StringUtils.removeStartIgnoreCase("", *)        = ""
	 * StringUtils.removeStartIgnoreCase(*, null)      = *
	 * StringUtils.removeStartIgnoreCase("www.domain.com", "www.")   = "domain.com"
	 * StringUtils.removeStartIgnoreCase("www.domain.com", "WWW.")   = "domain.com"
	 * StringUtils.removeStartIgnoreCase("domain.com", "www.")       = "domain.com"
	 * StringUtils.removeStartIgnoreCase("www.domain.com", "domain") = "www.domain.com"
	 * StringUtils.removeStartIgnoreCase("abc", "")    = "abc"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param remove �궘�젣臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String removeStartIgnoreCase(String str, String remove) {
		return org.apache.commons.lang.StringUtils.removeStartIgnoreCase(str, remove);
	}

	/**
	 * 臾몄옄�뿴�쓽 紐⑤뱺 怨듬갚 臾몄옄�뿴 �젣嫄�<br><br>
	 *
	 * StringUtils.removeWhitespace("Anyframe Java Test") = "AnyframeJavaTest"
	 *
	 * @param str 臾몄옄�뿴
	 * @return 怨듬갚�쓣 �젣嫄고븳 臾몄옄�뿴
	 * @see org.springframework.util.StringUtils#trimAllWhitespace(String)
	 */
	public static String removeWhitespace(String str) {
		return org.springframework.util.StringUtils.trimAllWhitespace(str);
	}

	/**
	 * 臾몄옄�뿴�쓣 諛섎났�슏�닔留뚰겮 諛섎났�븯�뿬 諛섑솚�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.repeat(null, 2) = null
	 * StringUtils.repeat("", 0)   = ""
	 * StringUtils.repeat("", 2)   = ""
	 * StringUtils.repeat("a", 3)  = "aaa"
	 * StringUtils.repeat("ab", 2) = "abab"
	 * StringUtils.repeat("a", -2) = ""
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param repeat 諛섎났�슏�닔
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String repeat(String str, int repeat) {
		return org.apache.commons.lang.StringUtils.repeat(str, repeat);
	}

	/**
	 * �엯�젰�맂 臾몄옄�뿴�뿉 ���빐�꽌 �빐�떦�븯�뒗 character瑜� 李얠븘 二쇱뼱吏� 臾몄옄�뿴濡� 蹂�寃�<br><br>
	 *
	 * StringUtils.replace("Anyframe/Common", "/", "|") = "Anyframe|Common"
	 *
	 * @param str 臾몄옄�뿴
	 * @param replacedStr 寃��깋�븷 臾몄옄�뿴
	 * @param replaceStr 蹂�寃쏀븷 臾몄옄�뿴
	 * @return 寃��깋�맂 臾몄옄�뿴�쓣 蹂�寃쏀븳 臾몄옄�뿴
	 */
	public static String replace(String str, String replacedStr, String replaceStr) {
		String newStr = "";
		if (str.indexOf(replacedStr) != -1) {
			String s1 = str.substring(0, str.indexOf(replacedStr));
			String s2 = str.substring(str.indexOf(replacedStr) + replacedStr.length());
			newStr = s1 + replaceStr + s2;
		}
		return newStr;
	}

	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 寃��깋臾몄옄�뿴�쓣 寃��깋�썑 移섑솚理쒕�移� 留뚰겮 移섑솚臾몄옄�뿴濡� 移섑솚�븯�뿬 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.replace(null, *, *, *)         = null
	 * StringUtils.replace("", *, *, *)           = ""
	 * StringUtils.replace("any", null, *, *)     = "any"
	 * StringUtils.replace("any", *, null, *)     = "any"
	 * StringUtils.replace("any", "", *, *)       = "any"
	 * StringUtils.replace("any", *, *, 0)        = "any"
	 * StringUtils.replace("abaa", "a", null, -1) = "abaa"
	 * StringUtils.replace("abaa", "a", "", -1)   = "b"
	 * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
	 * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
	 * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
	 * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
	 * </pre>
	 *
	 * @param text 臾몄옄�뿴
	 * @param searchString 寃��깋臾몄옄�뿴
	 * @param replacement 移섑솚臾몄옄�뿴
	 * @param max 移섑솚理쒕�移�
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String replace(String text, String searchString, String replacement, int max) {
		return org.apache.commons.lang.StringUtils.replace(text, searchString, replacement, max);
	}

	/**
	 * �엯�젰�맂 臾몄옄�뿴�씠 二쇱뼱吏� 臾몄옄�뿴怨� �씪移섑븯�뒗 紐⑤뱺 臾몄옄�뿴�쓣 諛붽퓭�빞�븷 臾몄옄�뿴濡� 蹂�寃�<br><br>
	 *
	 * StringUtils.replaceAll("Anyframe Java Test Anyframe Java Test", "Anyframe", "Enterprise") = "Enterprise Java Test Enterprise Java Test"
	 *
	 * @param source 臾몄옄�뿴
	 * @param regex 寃��깋�븷 臾몄옄�뿴
	 * @param replacement 蹂�寃쏀븷 臾몄옄�뿴
	 * @return 寃��깋�맂 紐⑤뱺 臾몄옄�뿴�쓣 蹂�寃쏀븳 臾몄옄�뿴
	 * @see String#replaceAll(String, String)
	 */
	public static String replaceAll(String source, String regex, String replacement) {
		if (source == null) {
			return null;
		}
		return source.replaceAll(regex, replacement);
	}

	// Replace, character based
	//-----------------------------------------------------------------------
	/**
	 * 臾몄옄�뿴�뿉�꽌 寃��깋臾몄옄瑜� 移섑솚臾몄옄濡� 紐⑤몢 蹂��솚�븳�떎.<br />
	 * 
	 * <pre>
	 * StringUtils.replaceChars(null, *, *)        = null
	 * StringUtils.replaceChars("", *, *)          = ""
	 * StringUtils.replaceChars("abcba", 'b', 'y') = "aycya"
	 * StringUtils.replaceChars("abcba", 'z', 'y') = "abcba"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param searchChar 寃��깋臾몄옄
	 * @param replaceChar 移섑솚臾몄옄
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String replaceChars(String str, char searchChar, char replaceChar) {
		return org.apache.commons.lang.StringUtils.replaceChars(str, searchChar, replaceChar);
	}

	/**
	 * 臾몄옄�뿴�뿉�꽌 寃��깋臾몄옄�뿴瑜� 移섑솚臾몄옄�뿴濡� 紐⑤몢 蹂��솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.replaceChars(null, *, *)           = null
	 * StringUtils.replaceChars("", *, *)             = ""
	 * StringUtils.replaceChars("abc", null, *)       = "abc"
	 * StringUtils.replaceChars("abc", "", *)         = "abc"
	 * StringUtils.replaceChars("abc", "b", null)     = "ac"
	 * StringUtils.replaceChars("abc", "b", "")       = "ac"
	 * StringUtils.replaceChars("abcba", "bc", "yz")  = "ayzya"
	 * StringUtils.replaceChars("abcba", "bc", "y")   = "ayya"
	 * StringUtils.replaceChars("abcba", "bc", "yzx") = "ayzya"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param searchChars 寃��깋臾몄옄�뿴
	 * @param replaceChars 移섑솚臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String replaceChars(String str, String searchChars, String replaceChars) {
		return org.apache.commons.lang.StringUtils.replaceChars(str, searchChars, replaceChars);
	}

	/**
	 * 臾몄옄�뿴�쓣 寃��깋由ъ뒪�듃 諛� 移섑솚由ъ뒪�듃�쓽 媛곴컖 媛숈� �씤�뜳�뒪�쓽 臾몄옄�뿴濡� 移섑솚�븯�뿬 諛섑솚�븳�떎.<br />
	 * 
	 * <pre>
	 *  StringUtils.replaceEach(null, *, *)        = null
	 *  StringUtils.replaceEach("", *, *)          = ""
	 *  StringUtils.replaceEach("aba", null, null) = "aba"
	 *  StringUtils.replaceEach("aba", new String[0], null) = "aba"
	 *  StringUtils.replaceEach("aba", null, new String[0]) = "aba"
	 *  StringUtils.replaceEach("aba", new String[]{"a"}, null)  = "aba"
	 *  StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""})  = "b"
	 *  StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"})  = "aba"
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"})  = "wcte"
	 *  (example of how it does not repeat)
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"})  = "dcte"
	 * </pre>
	 * 
	 * @param text 臾몄옄�뿴
	 * @param searchList 寃��깋由ъ뒪�듃
	 * @param replacementList 移섑솚由ъ뒪�듃
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String replaceEach(String text, String[] searchList, String[] replacementList) {
		return org.apache.commons.lang.StringUtils.replaceEach(text, searchList, replacementList);
	}

	/**
	 * 臾몄옄�뿴�쓣 寃��깋由ъ뒪�듃 諛� 移섑솚由ъ뒪�듃�쓽 媛곴컖 媛숈� �씤�뜳�뒪�쓽 臾몄옄�뿴濡� 移섑솚�븯�뒗 �옉�뾽�쓣<br /> 
	 * 寃��깋由ъ뒪�듃�쓽 湲몄씠留뚰겮 諛섎났�븯�뿬 諛섑솚�븳�떎.<br />
	 * 
	 * <pre>
	 *  StringUtils.replaceEachRepeatedly(null, *, *) = null
	 *  StringUtils.replaceEachRepeatedly("", *, *) = ""
	 *  StringUtils.replaceEachRepeatedly("aba", null, null) = "aba"
	 *  StringUtils.replaceEachRepeatedly("aba", new String[0], null) = "aba"
	 *  StringUtils.replaceEachRepeatedly("aba", null, new String[0]) = "aba"
	 *  StringUtils.replaceEachRepeatedly("aba", new String[]{"a"}, null) = "aba"
	 *  StringUtils.replaceEachRepeatedly("aba", new String[]{"a"}, new String[]{""}) = "b"
	 *  StringUtils.replaceEachRepeatedly("aba", new String[]{null}, new String[]{"a"}) = "aba"
	 *  StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}) = "wcte"
	 *  StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}) = "tcte"
	 *  StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}) = IllegalArgumentException
	 * </pre>
	 * 
	 * @param text 臾몄옄�뿴
	 * @param searchList 寃��깋由ъ뒪�듃
	 * @param replacementList 移섑솚由ъ뒪�듃
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
		return org.apache.commons.lang.StringUtils.replaceEachRepeatedly(text, searchList, replacementList);
	}

	/**
	 * �엯�젰�맂 臾몄옄�뿴�씠 二쇱뼱吏� 臾몄옄�뿴怨� �씪移섑븯�뒗 泥ル쾲吏� 臾몄옄�뿴�쓣 諛붽퓭�빞�븷 臾몄옄�뿴濡� 蹂�寃�<br><br>
	 *
	 * StringUtils.replaceFirst("Anyframe Java Test Anyframe Java Test", "Anyframe", "Enterprise") = "Enterprise Java Test Anyframe Java Test"
	 *
	 * @param source 臾몄옄�뿴
	 * @param regex 寃��깋�븷 臾몄옄�뿴
	 * @param replacement 蹂�寃쏀븷 臾몄옄�뿴
	 * @return 寃��깋�맂 臾몄옄�뿴 以� 泥ル쾲吏� 臾몄옄�뿴�쓣 蹂�寃쏀븳 臾몄옄�뿴
	 * @see String#replaceFirst(String, String)
	 */
	public static String replaceFirst(String source, String regex, String replacement) {
		if (source == null) {
			return null;
		}
		return source.replaceFirst(regex, replacement);
	}
	
	// Mid
	//-----------------------------------------------------------------------
	/**
	 * HTML tag媛� �뱾�뼱�엳�뒗 臾몄옄�뿴�뿉 ���빐 unescape�빐以��떎.<br><br>
	 *
	 * StringUtils.replaceHtmlEscape("<html>Anyframe Java Test<html>") = "&lt;html&gt;Anyframe Java Test&lt;html&gt;"
	 *
	 * @param input HTML tag媛� �뱾�뼱�엳�뒗 臾몄옄�뿴
	 * @return HTML tag瑜� unescape�븳 臾몄옄�뿴
	 * @see HtmlUtils#htmlEscape(String)
	 */
	public static String replaceHtmlEscape(String input) {
		return HtmlUtils.htmlEscape(input);
	}

	/**
	 * �엯�젰�맂 臾몄옄�뿴�씠 二쇱뼱吏� 臾몄옄�뿴怨� �씪移섑븯�뒗 留덉�留� 臾몄옄�뿴�쓣 諛붽퓭�빞�븷 臾몄옄�뿴濡� 蹂�寃�<br><br>
	 *
	 * StringUtils.replaceLast("Anyframe Java Test Anyframe Java Test", "Anyframe", "Enterprise") = "Anyframe Java Test Enterprise Java Test"
	 *
	 * @param source 臾몄옄�뿴
	 * @param regex 寃��깋�븷 臾몄옄�뿴
	 * @param replacement 蹂�寃쏀븷 臾몄옄�뿴
	 * @return 寃��깋�맂 臾몄옄�뿴 以� 留덉�留� 臾몄옄�뿴�쓣 蹂�寃쏀븳 臾몄옄�뿴
	 */
	public static String replaceLast(String source, String regex, String replacement) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		if (!matcher.find()) {
			return source;
		}
		int lastMatchStart = 0;
		do {
			lastMatchStart = matcher.start();
		} while (matcher.find());
		matcher.find(lastMatchStart);
		StringBuffer sb = new StringBuffer(source.length());
		matcher.appendReplacement(sb, replacement);
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	// Mid
	//-----------------------------------------------------------------------
	/**
	 * 臾몄옄�뿴 �궡�뿉�꽌 寃��깋臾몄옄�뿴�쓣 寃��깋�썑 移섑솚臾몄옄�뿴濡� 移섑솚�븯�뿬 諛섑솚�븳�떎.<br />
	 *
	 * <pre>
	 * StringUtils.replaceOnce(null, *, *)        = null
	 * StringUtils.replaceOnce("", *, *)          = ""
	 * StringUtils.replaceOnce("any", null, *)    = "any"
	 * StringUtils.replaceOnce("any", *, null)    = "any"
	 * StringUtils.replaceOnce("any", "", *)      = "any"
	 * StringUtils.replaceOnce("aba", "a", null)  = "aba"
	 * StringUtils.replaceOnce("aba", "a", "")    = "ba"
	 * StringUtils.replaceOnce("aba", "a", "z")   = "zba"
	 * </pre>
	 *
	 * @param text 臾몄옄�뿴
	 * @param searchString 寃��깋臾몄옄�뿴
	 * @param replacement 移섑솚臾몄옄�뿴
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String replaceOnce(String text, String searchString, String replacement) {
		return org.apache.commons.lang.StringUtils.replaceOnce(text, searchString, replacement);
	}

	/**
	 * �븳 String 媛앹껜 �븞�뿉�꽌 �듅�젙 �뙣�꽩(old)�쓣 �떎瑜� �뙣�꽩(new)�쑝濡� 蹂��솚�븳�떎.<br>
	 * �벑�옣 �뙣�꽩�쓽 �쐞移섎뒗 醫뚯륫�뿉�꽌遺��꽣 怨꾩궛�븯怨� 寃뱀튂吏� �븡�뒗 �삎�깭濡� 怨꾩궛�븳�떎.<br>
	 * �뵲�씪�꽌, 蹂��솚�맂 �썑�뿉�룄 old �뙣�꽩�� �궓�븘 �엳�쓣 �닔 �엳�떎.<br><br>
	 * 
	 * StringUtils.replacePattern("abaa", "aba", "bab") = "baba"
	 *
	 * @param text
	 *            text to search and replace in, may be null
	 * @param searchString
	 *            the String to search for, may be null
	 * @param replacement
	 *            the String to replace it with, may be null
	 * @return the text with any replacements processed, null if null String
	 *         input
	 */
	public static String replacePattern(String text, String searchString,
			String replacement) {
		if (text == null) {
			return null;
		}
		if (searchString == null || replacement == null) {
			return text;
		}
		StringBuffer sbuf = new StringBuffer();
		int pos = 0;
		int index = text.indexOf(searchString);
		int patLen = searchString.length();
		for (; index >= 0; index = text.indexOf(searchString, pos)) {
			sbuf.append(text.substring(pos, index));
			sbuf.append(replacement);
			pos = index + patLen;
		}
		sbuf.append(text.substring(pos));
		return sbuf.toString();
	}
	
	// Mid
	//-----------------------------------------------------------------------
	/**
	 * �엯�젰�맂 臾몄옄�뿴�쓽 �닚�꽌瑜� 諛섎�濡� 諛붽퓞<br><br>
	 *
	 * StringUtils.reverse("Anyframe Java Test") = "tseT avaJ emarfynA"
	 *
	 * @param str 臾몄옄�뿴
	 * @return �닚�꽌媛� 諛섎�濡� 諛붾�� 臾몄옄�뿴
	 */
	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		return new StringBuilder(str).reverse().toString();
	}

	/**
	 * 援щ텇�옄瑜� 援щ텇�쑝濡� 臾몄옄�뿴�쓣 �굹�늿 �썑 �굹�닠吏� �떒�뼱�뱾�쓣 �뿭�닚�쑝濡� 諛붽씔�떎.<br />
	 *
	 * <pre>
	 * StringUtils.reverseDelimited(null, *)      = null
	 * StringUtils.reverseDelimited("", *)        = ""
	 * StringUtils.reverseDelimited("a.b.c", 'x') = "a.b.c"
	 * StringUtils.reverseDelimited("a.b.c", ".") = "c.b.a"
	 * </pre>
	 *
	 * @param str 臾몄옄�뿴
	 * @param separatorChar 援щ텇�옄
	 * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
	 */
	public static String reverseDelimited(String str, char separatorChar) {
		return org.apache.commons.lang.StringUtils.reverseDelimited(str, separatorChar);
	}
	
	// Mid
	//-----------------------------------------------------------------------
	/**
	 * 二쇱뼱吏� String 媛앹껜�뿉 ���빐�꽌 二쇱뼱吏� 湲몄씠留뚰겮 �삤瑜몄そ 遺�遺꾩쓣 �뼹�뼱 諛섑솚�븳�떎.<br>
	 * 二쇱뼱吏� 湲몄씠蹂대떎 二쇱뼱吏� String�쓽 湲몄씠媛� �옉�쓣 寃쎌슦�뿉�뒗 二쇱뼱吏� String�쓣 洹몃�濡� 諛섑솚�븳�떎.<br>
	 * "..."�쓣 遺숈씠吏� �븡�뒗 �젏�쓣 �젣�쇅�븯怨좊뒗 splitTail()�� �룞�씪�븯�떎.<br><br>
	 *
	 * StringUtils.right("1234567", 3) = "567"
	 *
	 * @param str
	 *            the String to get the rightmost characters from, may be null
	 * @param len
	 *            the length of the required String
	 * @return the rightmost characters, null if null String input
	 */
	public static String right(String str, int len) {
		if (str == null) {
			return null;
		} else if (len <= 0 || str.length() <= len) {
			return str;
		} else {
			return str.substring(str.length() - len);
		}
	}

	/**
	 * �빐�떦�븯�뒗 臾몄옄�뿴�뿉 ���빐�꽌 �엯�젰�맂 湲몄씠留뚰겮 遺�議깊븳 湲몄씠瑜� �삤瑜몄そ遺��꽣 怨듬갚�쑝濡� 梨꾩썙�꽔�뒗�떎.<br><br>
	 *
	 * StringUtils.rightPad("Anyframe", 12) = "Anyframe    "
	 *
	 * @param str 臾몄옄�뿴
	 * @param size 怨듬갚�씠 梨꾩썙吏� 臾몄옄�뿴�쓽 �쟾泥� 湲몄씠
	 * @return 遺�議깊븳 湲몄씠留뚰겮 �삤瑜몄そ遺��꽣 怨듬갚�씠 梨꾩썙吏� 臾몄옄�뿴
	 */
	public static String rightPad(String str, int size) {
		return rightPad(str, size, ' ');
	}

	/**
	 * �빐�떦�븯�뒗 臾몄옄�뿴�뿉 ���빐�꽌 �엯�젰�맂 湲몄씠留뚰겮 遺�議깊븳 湲몄씠瑜� �삤瑜몄そ遺��꽣 吏��젙�맂 臾몄옄濡� 梨꾩썙�꽔�뒗�떎.<br><br>
	 *
	 * StringUtils.rightPad("Anyframe", 12, 'a') = "Anyframeaaaa"
	 *
	 * @param str 臾몄옄�뿴
	 * @param size 罹먮┃�꽣媛� 梨꾩썙吏� 臾몄옄�뿴�쓽 �쟾泥� 湲몄씠
	 * @param padChar 梨꾩썙�꽔�쓣 罹먮┃�꽣
	 * @return 遺�議깊븳 湲몄씠留뚰겮 �삤瑜몄そ遺��꽣 罹먮┃�꽣媛� 梨꾩썙吏� 臾몄옄�뿴
	 */
	public static String rightPad(String str, int size, char padChar) {
		return padChar(str, size, padChar, false);
	}

	/**
	 * �빐�떦�븯�뒗 臾몄옄�뿴�뿉 ���빐�꽌 �엯�젰�맂 湲몄씠留뚰겮 遺�議깊븳 湲몄씠瑜� �삤瑜몄そ遺��꽣 吏��젙�맂 臾몄옄�뿴濡� 梨꾩썙�꽔�뒗�떎.<br><br>
	 *
	 * StringUtils.rightPad("Anyframe", 12, "Java") = "AnyframeJava"
	 *
	 * @param str 臾몄옄�뿴
	 * @param size 臾몄옄�뿴�씠 梨꾩썙吏� 臾몄옄�뿴�쓽 �쟾泥� 湲몄씠
	 * @param padStr 梨꾩썙�꽔�쓣 臾몄옄�뿴
	 * @return 遺�議깊븳 湲몄씠留뚰겮 �삤瑜몄そ遺��꽣 臾몄옄�뿴�씠 梨꾩썙吏� 臾몄옄�뿴
	 */
	public static String rightPad(String str, int size, String padStr) {
		return padString(str, size, padStr, false);
	}

	/**
	 * 臾몄옄�뿴�쓽 �삤瑜몄そ�쓽 怨듬갚 臾몄옄�뿴 �젣嫄�<br><br>
	 *
	 * StringUtils.rightTrim("Anyframe Java Test ") = "Anyframe Java Test"
	 *
	 * @param str 臾몄옄�뿴
	 * @return �삤瑜몄そ 怨듬갚�쓣 �젣嫄고븳 臾몄옄�뿴
	 * @see org.springframework.util.StringUtils#trimTrailingWhitespace(String)
	 */
	public static String rightTrim(String str) {
		return org.springframework.util.StringUtils.trimTrailingWhitespace(str);
	}

	/**
     * 怨듬갚臾몄옄瑜� 援щ텇�옄濡� �궗�슜�븯�뿬 臾몄옄�뿴�쓣 遺꾨━�븳�떎.<br />
     *
     * <pre>
     * StringUtils.split(null)       = null
     * StringUtils.split("")         = []
     * StringUtils.split("abc def")  = ["abc", "def"]
     * StringUtils.split("abc  def") = ["abc", "def"]
     * StringUtils.split(" abc ")    = ["abc"]
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String[] split(String str) {
    	return org.apache.commons.lang.StringUtils.split(str);
    }

	/**
	 * 二쇱뼱吏� String�뿉 ���빐�꽌 separator(char)瑜� �씠�슜�븯�뿬 tokenize�븳 �썑 String[]濡� 戮묒븘�궦�떎.<br>
	 * �뿰�냽�맂 separator �궗�씠�뒗 token�씠 �릺吏� �븡�뒗�떎.<br>
	 * 二쇱뼱吏� String�씠 null�씪 寃쎌슦, null�쓣 return�븳�떎.<br><br>
	 *
	 * StringUtils.split("aaVbbVcc", 'V') = {"aa", "bb", "cc"}
	 *
	 * @param str
	 *            the String to parse
	 * @param separator
	 *            the character used as the delimiter
	 * @return an array of parsed Strings
	 */
	public static String[] split(String str, char separator) {
		StringBuffer tempStringBuffer = new StringBuffer();
		tempStringBuffer.append(separator);
		return tokenizeToStringArray(str, tempStringBuffer.toString(), false,
				false);
	}

	/**
     * 臾몄옄�뿴�쓣 援щ텇�옄濡� �궗�슜�븯�뿬 遺꾨━�븳�떎.<br />
     *
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("abc def", null) = ["abc", "def"]
     * StringUtils.split("abc def", " ")  = ["abc", "def"]
     * StringUtils.split("abc  def", " ") = ["abc", "def"]
     * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separatorChars 援щ텇�옄
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String[] split(String str, String separatorChars) {
    	return org.apache.commons.lang.StringUtils.split(str, separatorChars);
    }
	
	// Mid
	//-----------------------------------------------------------------------
	/**
     * 臾몄옄�뿴�쓣 援щ텇�옄瑜� �궗�슜�븯�뿬 理쒕��겕湲� 留뚰겮�쓽 諛곗뿴濡� 遺꾨━�븳�떎.<br />
     *
     * <pre>
     * StringUtils.split(null, *, *)            = null
     * StringUtils.split("", *, *)              = []
     * StringUtils.split("ab de fg", null, 0)   = ["ab", "cd", "ef"]
     * StringUtils.split("ab   de fg", null, 0) = ["ab", "cd", "ef"]
     * StringUtils.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     * StringUtils.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separatorChars 援щ텇�옄
     * @param max 理쒕��겕湲�
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String[] split(String str, String separatorChars, int max) {
    	return org.apache.commons.lang.StringUtils.split(str, separatorChars, max);
    }

	/**
     * 臾몄옄�뿴�쓣 <code>java.lang.Character.getType(char)</code>�쓽 由ы꽩���엯�쑝濡� 援щ텇�븯�뿬 <br />
     * 諛곗뿴濡� 遺꾪븷�븳�떎.�쁺臾몃��냼臾몄옄���엯援щ텇<br />
     * 
     * <pre>
     * StringUtils.splitByCharacterType(null)         = null
     * StringUtils.splitByCharacterType("")           = []
     * StringUtils.splitByCharacterType("ab de fg")   = ["ab", " ", "de", " ", "fg"]
     * StringUtils.splitByCharacterType("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
     * StringUtils.splitByCharacterType("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
     * StringUtils.splitByCharacterType("number5")    = ["number", "5"]
     * StringUtils.splitByCharacterType("fooBar")     = ["foo", "B", "ar"]
     * StringUtils.splitByCharacterType("foo200Bar")  = ["foo", "200", "B", "ar"]
     * StringUtils.splitByCharacterType("ASFRules")   = ["ASFR", "ules"]
     * </pre>
     * @param str 臾몄옄�뿴
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String[] splitByCharacterType(String str) {
    	return org.apache.commons.lang.StringUtils.splitByCharacterType(str);
    }

	// Mid
	//-----------------------------------------------------------------------
	/**
     * 臾몄옄�뿴�쓣 <code>java.lang.Character.getType(char)</code>�쓽 由ы꽩���엯�쑝濡� 援щ텇�븯�뿬 <br />
     * 諛곗뿴濡� 遺꾪븷�븳�떎.�쁺臾몃��냼臾몄옄���엯援щ텇�븯吏��븡�쓬<br />
     * 
     * <pre>
     * StringUtils.splitByCharacterTypeCamelCase(null)         = null
     * StringUtils.splitByCharacterTypeCamelCase("")           = []
     * StringUtils.splitByCharacterTypeCamelCase("ab de fg")   = ["ab", " ", "de", " ", "fg"]
     * StringUtils.splitByCharacterTypeCamelCase("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
     * StringUtils.splitByCharacterTypeCamelCase("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
     * StringUtils.splitByCharacterTypeCamelCase("number5")    = ["number", "5"]
     * StringUtils.splitByCharacterTypeCamelCase("fooBar")     = ["foo", "Bar"]
     * StringUtils.splitByCharacterTypeCamelCase("foo200Bar")  = ["foo", "200", "Bar"]
     * StringUtils.splitByCharacterTypeCamelCase("ASFRules")   = ["ASF", "Rules"]
     * </pre>
     * @param str 臾몄옄�뿴
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String[] splitByCharacterTypeCamelCase(String str) {
    	return org.apache.commons.lang.StringUtils.splitByCharacterTypeCamelCase(str);
    }

	/**
     * 臾몄옄�뿴�쓣 援щ텇�옄瑜� �궗�슜�븯�뿬 諛곗뿴濡� 遺꾨━�븳�떎.<br />
     *
     * <pre>
     * StringUtils.splitByWholeSeparator(null, *)               = null
     * StringUtils.splitByWholeSeparator("", *)                 = []
     * StringUtils.splitByWholeSeparator("ab de fg", null)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab   de fg", null)    = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separator 援щ텇�옄
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String[] splitByWholeSeparator(String str, String separator) {
    	return org.apache.commons.lang.StringUtils.splitByWholeSeparator(str, separator);
    }

	/**
     * 臾몄옄�뿴�쓣 援щ텇�옄瑜� �궗�슜�븯�뿬 理쒕��겕湲� 留뚰겮�쓽 諛곗뿴濡� 遺꾨━�븳�떎.<br />
     *
     * <pre>
     * StringUtils.splitByWholeSeparator(null, *, *)               = null
     * StringUtils.splitByWholeSeparator("", *, *)                 = []
     * StringUtils.splitByWholeSeparator("ab de fg", null, 0)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab   de fg", null, 0)    = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
     * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separator 援щ텇�옄
     * @param max 理쒕��겕湲�
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
	public static String[] splitByWholeSeparator(String str, String separator, int max) {
    	return org.apache.commons.lang.StringUtils.splitByWholeSeparator(str, separator, max);
    }

	/**
     * 臾몄옄�뿴�쓣 援щ텇�옄瑜� �궗�슜�븯�뿬 諛곗뿴濡� 遺꾨━�븳�떎.<br />
     *
     * <pre>
     * StringUtils.splitByWholeSeparatorPreserveAllTokens(null, *)               = null
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("", *)                 = []
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab de fg", null)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null)    = ["ab", "", "", "de", "fg"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separator 援щ텇�옄
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
    	return org.apache.commons.lang.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator);
    }

	/**
     * 臾몄옄�뿴�쓣 援щ텇�옄瑜� �궗�슜�븯�뿬 理쒕��겕湲� 留뚰겮�쓽 諛곗뿴濡� 遺꾨━�븳�떎.<br />
     *
     * <pre>
     * StringUtils.splitByWholeSeparatorPreserveAllTokens(null, *, *)               = null
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("", *, *)                 = []
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab de fg", null, 0)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null, 0)    = ["ab", "", "", "de", "fg"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separator 援щ텇�옄
     * @param max 理쒕��겕湲�
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
    	return org.apache.commons.lang.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator, max);
    }

	/**
	 * �빐�떦臾몄옄�뿴�쓽 �궗�씠利� 留뚰겮 �븵�뿉�꽌遺��꽣 �옄瑜몃떎.<br><br>
	 *
	 * StringUtils.splitHead("Anyframe Java Test", 3) = "Any"
	 *
	 * @param str 臾몄옄�뿴
	 * @param size 臾몄옄�뿴�쓣 �옄瑜� 湲몄씠
	 * @return 湲몄씠留뚰겮 �븵�뿉�꽌遺��꽣 �옄瑜� 臾몄옄�뿴
	 */
	public static String splitHead(String str, int size) {
		if (str == null) {
			return "";
		}
		if (str.length() > size) {
			str = str.substring(0, size);
		}
		return str;
	}

	/**
	 * 二쇱뼱吏� String 媛앹껜�뿉 ���빐�꽌 二쇱뼱吏� 湲몄씠留뚰겮 �븵遺�遺꾩쓣 �뼹�뼱 諛섑솚�븳�떎.<br>
	 * 二쇱뼱吏� 湲몄씠蹂대떎 二쇱뼱吏� String�쓽 湲몄씠媛� �옉�쓣 寃쎌슦�뿉�뒗 二쇱뼱吏� String�쓣 洹몃�濡� 諛섑솚�븳�떎.<br>
	 * �뼹�뼱�궦 �븵遺�遺꾩쓽 �뮘�뿉 "..."�쓣 遺숈뿬�꽌 諛섑솚�븳�떎.<br><br>
	 *
	 * StringUtils.splitHead("12345678", 3) = "123..."
	 *
	 * @param str
	 *            the String to get the leftmost characters from, may be null
	 * @param len
	 *            the length of the required String
	 * @return the leftmost characters with ellipsis, null if null String input
	 */
	public static String splitHeadWithEllipsis(String str, int len) {
		if (str == null) {
			return null;
		} else if (len <= 0 || str.length() <= len) {
			return str;
		} else {
			return str.substring(0, len) + "...";
		}
	}

	/**
     * 怨듬갚臾몄옄瑜� 援щ텇�옄濡� �궗�슜�븯�뿬 臾몄옄�뿴�쓣 遺꾨━�븳�떎.<br />
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null)       = null
     * StringUtils.splitPreserveAllTokens("")         = []
     * StringUtils.splitPreserveAllTokens("abc def")  = ["abc", "def"]
     * StringUtils.splitPreserveAllTokens("abc  def") = ["abc", "", "def"]
     * StringUtils.splitPreserveAllTokens(" abc ")    = ["", "abc", ""]
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String[] splitPreserveAllTokens(String str) {
    	return org.apache.commons.lang.StringUtils.splitPreserveAllTokens(str);
    }

	/**
     * 臾몄옄�뿴�쓣 援щ텇�옄濡� �궗�슜�븯�뿬 諛곗뿴濡� 遺꾨━�븳�떎.<br />
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null, *)         = null
     * StringUtils.splitPreserveAllTokens("", *)           = []
     * StringUtils.splitPreserveAllTokens("a.b.c", '.')    = ["a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a..b.c", '.')   = ["a", "", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a:b:c", '.')    = ["a:b:c"]
     * StringUtils.splitPreserveAllTokens("a\tb\nc", null) = ["a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a b c", ' ')    = ["a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a b c ", ' ')   = ["a", "b", "c", ""]
     * StringUtils.splitPreserveAllTokens("a b c  ", ' ')   = ["a", "b", "c", "", ""]
     * StringUtils.splitPreserveAllTokens(" a b c", ' ')   = ["", a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("  a b c", ' ')  = ["", "", a", "b", "c"]
     * StringUtils.splitPreserveAllTokens(" a b c ", ' ')  = ["", a", "b", "c", ""]
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separatorChar 援щ텇�옄
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String[] splitPreserveAllTokens(String str, char separatorChar) {
    	return org.apache.commons.lang.StringUtils.splitPreserveAllTokens(str, separatorChar);
    }

	/**
     * 臾몄옄�뿴�쓣 援щ텇�옄濡� �궗�슜�븯�뿬 諛곗뿴濡� 遺꾨━�븳�떎.<br />
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null, *)           = null
     * StringUtils.splitPreserveAllTokens("", *)             = []
     * StringUtils.splitPreserveAllTokens("abc def", null)   = ["abc", "def"]
     * StringUtils.splitPreserveAllTokens("abc def", " ")    = ["abc", "def"]
     * StringUtils.splitPreserveAllTokens("abc  def", " ")   = ["abc", "", def"]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":")   = ["ab", "cd", "ef"]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef:", ":")  = ["ab", "cd", "ef", ""]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef::", ":") = ["ab", "cd", "ef", "", ""]
     * StringUtils.splitPreserveAllTokens("ab::cd:ef", ":")  = ["ab", "", cd", "ef"]
     * StringUtils.splitPreserveAllTokens(":cd:ef", ":")     = ["", cd", "ef"]
     * StringUtils.splitPreserveAllTokens("::cd:ef", ":")    = ["", "", cd", "ef"]
     * StringUtils.splitPreserveAllTokens(":cd:ef:", ":")    = ["", cd", "ef", ""]
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separatorChars 援щ텇�옄
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String[] splitPreserveAllTokens(String str, String separatorChars) {
    	return org.apache.commons.lang.StringUtils.splitPreserveAllTokens(str, separatorChars);
    }

	/**
     * 臾몄옄�뿴�쓣 援щ텇�옄瑜� �궗�슜�븯�뿬 理쒕��겕湲� 留뚰겮�쓽 諛곗뿴濡� 遺꾨━�븳�떎.<br />
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null, *, *)            = null
     * StringUtils.splitPreserveAllTokens("", *, *)              = []
     * StringUtils.splitPreserveAllTokens("ab cd ef", null, 0)   = ["ab", "cd", "ef"]
     * StringUtils.splitPreserveAllTokens("ab   cd ef", null, 0) = [ab, , , cd, ef]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     * StringUtils.splitPreserveAllTokens("ab   de fg", null, 2) = ["ab", "  de fg"]
     * StringUtils.splitPreserveAllTokens("ab   de fg", null, 3) = ["ab", "", " de fg"]
     * StringUtils.splitPreserveAllTokens("ab   de fg", null, 4) = ["ab", "", "", "de fg"]
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separatorChars 援щ텇�옄
     * @param max 理쒕��겕湲�
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
    	return org.apache.commons.lang.StringUtils.splitPreserveAllTokens(str, separatorChars, max);
    }

	/**
	 * �빐�떦臾몄옄�뿴�쓽 �궗�씠利� 留뚰겮 �뮘�뿉�꽌遺��꽣 �옄瑜몃떎.<br><br>
	 *
	 * StringUtils.splitTail("Anyframe Java Test", 3) = "est"
	 *
	 * @param str 臾몄옄�뿴
	 * @param size 臾몄옄�뿴�쓣 �옄瑜� 湲몄씠
	 * @return 湲몄씠留뚰겮 �뮘�뿉�꽌遺��꽣 �옄瑜� 臾몄옄�뿴
	 */
	public static String splitTail(String str, int size) {
		if (str == null) {
			return "";
		}
		if (str.length() > size) {
			str = str.substring(str.length() - size);
		}
		return str;
	}

    // Mid
	//-----------------------------------------------------------------------
	/**
	 * 二쇱뼱吏� String 媛앹껜�뿉 ���빐�꽌 二쇱뼱吏� 湲몄씠留뚰겮 �뮮遺�遺꾩쓣 �뼹�뼱 諛섑솚�븳�떎.<br>
	 * 二쇱뼱吏� 湲몄씠蹂대떎 二쇱뼱吏� String�쓽 湲몄씠媛� �옉�쓣 寃쎌슦�뿉�뒗 二쇱뼱吏� String�쓣 洹몃�濡� 諛섑솚�븳�떎.<br>
	 * �뼹�뼱�궦 �뮮遺�遺꾩쓽 �븵�뿉 "..."�쓣 遺숈뿬�꽌 諛섑솚�븳�떎.<br><br>
	 *
	 * StringUtils.splitTail("12345678", 3) = "...678"
	 *
	 * @param str
	 *            the String to get the rightmost characters from, may be null
	 * @param len
	 *            the length of the required String
	 * @return the rightmost characters with ellipsis, null if null String input
	 */
	public static String splitTailWithEllipsis(String str, int len) {
		if (str == null) {
			return null;
		} else if (len <= 0 || str.length() <= len) {
			return str;
		} else {
			return "..." + str.substring(str.length() - len);
		}
	}

    /**
     * 臾몄옄�뿴�씠 寃��깋臾몄옄濡� �떆�옉�릺�뒗吏� �솗�씤�븳�떎.<br />
     *
     * <pre>
     * StringUtils.startsWith(null, null)      = true
     * StringUtils.startsWith(null, "abc")     = false
     * StringUtils.startsWith("abcdef", null)  = false
     * StringUtils.startsWith("abcdef", "abc") = true
     * StringUtils.startsWith("ABCDEF", "abc") = false
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param prefix 寃��깋臾몄옄�뿴
     * @return 臾몄옄�뿴�씠 寃��깋臾몄옄濡� �떆�옉�릺�뒗寃쎌슦�� 臾몄옄�뿴 �뼇履쎈え�몢 <code>null</code>�씤寃쎌슦 <code>true</code> 
     */
    public static boolean startsWith(String str, String prefix) {
    	return org.apache.commons.lang.StringUtils.startsWith(str, prefix);
    }
    
    /**
     * 臾몄옄�뿴�씠 寃��깋臾몄옄濡� ���냼臾몄옄 援щ텇�뾾�씠 �떆�옉�릺�뒗吏� �솗�씤�븳�떎.<br />
     *
     * <pre>
     * StringUtils.startsWithIgnoreCase(null, null)      = true
     * StringUtils.startsWithIgnoreCase(null, "abc")     = false
     * StringUtils.startsWithIgnoreCase("abcdef", null)  = false
     * StringUtils.startsWithIgnoreCase("abcdef", "abc") = true
     * StringUtils.startsWithIgnoreCase("ABCDEF", "abc") = true
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param prefix 寃��깋臾몄옄
     * @return 臾몄옄�뿴�씠 寃��깋臾몄옄濡� ���냼臾몄옄 援щ텇�뾾�씠 �떆�옉�릺�뒗寃쎌슦�� 臾몄옄�뿴 �뼇履쎈え�몢
     *  <code>null</code>�씤寃쎌슦 <code>true</code> 
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
    	return org.apache.commons.lang.StringUtils.startsWithIgnoreCase(str, prefix);
    }

    /**
	 * 臾몄옄�뿴�쓣 �닽�옄濡� 蹂��솚�븯�뿬 由ы꽩�븳�떎.<br><br>
	 *
	 * StringUtils.string2integer("14") = 14
	 *
	 * @param str
	 *            string representation of a number
	 * @return integer integer type of string
	 */
	public static int string2integer(String str) {
		int ret = Integer.parseInt(str.trim());

		return ret;
	}

    /**
	 * 臾몄옄�뿴�쓣 BigDecimal濡� 蹂��솚�븯�뿬 由ы꽩�븳�떎.<br><br>
	 * 
	 * StringUtils.stringToBigDecimal("14") = 14
	 *
	 * @param str
	 *            the String value to convert
	 * @return the converted BigDecimal
	 */
	public static BigDecimal stringToBigDecimal(String str) {
		if ("".equals(rightTrim(str))) {
			return new BigDecimal(0);
		} else {
			return new BigDecimal(str);
		}
	}

    /**
	 * 臾몄옄�뿴�쓽 �궡�슜 以� �젙�빐吏� �쐞移섏쓽 �씪遺� 臾몄옄�뿴�쓣 BigDecimal濡� 蹂��솚�븯�뿬 由ы꽩�븳�떎.<br><br>
	 *
	 * StringUtils.stringToBigDecimal("123456", 0, 2) = 12
	 *
	 * @param str 臾몄옄�뿴
	 * @param pos �떆�옉�쐞移�
	 * @param len 湲몄씠
	 * @return 蹂��솚�맂 BigDecimal 媛�
	 */
	public static BigDecimal stringToBigDecimal(String str, int pos, int len) {
		if ("".equals(rightTrim(str))) {
			return new BigDecimal(0);
		} else if (str.length() < pos + len) {
			return stringToBigDecimal(leftPad(str, pos + len, "0"));
		} else {
			return stringToBigDecimal(str.substring(pos, pos + len));
		}
	}

    /**
	 * 臾몄옄�뿴�쓣 諛쏆븘 �빐�떦�븯�뒗 hex 肄붾뱶濡� 留뚮뱾�뼱 諛섑솚�븳�떎. (�쑀�땲肄붾뱶)<br><br>
	 *
	 * StringUtils.stringToHex("123") = "003100320033"
	 *
	 * @param str 臾몄옄�뿴
	 * @return 蹂��솚�맂 hex 臾몄옄�뿴
	 */
	public static String stringToHex(String str) {

		String inStr = str;

		char[] inChar = inStr.toCharArray();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < inChar.length; i++) {
			String hex = Integer.toHexString((int) inChar[i]);
			if (hex.length() == 2) {
				hex = "00" + hex;
			}
			sb.append(hex);
		}
		return sb.toString();
	}

    /**
	 * 臾몄옄�뿴�쓣 �젙�닔�삎�쑝濡� 蹂��솚�븯�뿬 由ы꽩�븳�떎.<br><br>
	 *
	 * StringUtils.stringToNumn("123") = 123
	 *
	 * @param str 臾몄옄�뿴
	 * @return �젙�닔�삎�쑝濡� 蹂��솚�맂 媛�
	 */
	public static int stringToNumn(String str) {
		if ("".equals(rightTrim(str))) {
			return 0;
		} else {
			return Integer.parseInt(str);
		}
	}

    /**
	 * 臾몄옄�뿴�쓽 �궡�슜 以� �젙�빐吏� �쐞移섏쓽 �씪遺� 臾몄옄�뿴�쓣 �젙�닔�삎�쑝濡� 蹂��솚�븯�뿬 由ы꽩�븳�떎.<br><br>
	 *
	 * StringUtils.stringToNumn("123456789", 5, 3) = 678
	 *
	 * @param str 臾몄옄�뿴
	 * @param pos �떆�옉�쐞移�
	 * @param len 湲몄씠
	 * @return 臾몄옄�뿴�쓽 �씪遺�媛� �젙�닔�삎�쑝濡� 蹂��솚�맂 媛�
	 */
	public static int stringToNumn(String str, int pos, int len) {
		if ("".equals(rightTrim(str))) {
			return 0;
		} else if (str.length() < pos + len) {
			return stringToNumn(leftPad(str, pos + len, "0"));
		} else {
			return stringToNumn(str.substring(pos, pos + len));
		}
	}

    /**
     * 臾몄옄�뿴�쓽 �떆�옉怨� �걹�뿉�꽌 �뒪�럹�씠�뒪�쓣 �젣嫄고븳�떎.<br />
     *
     * <pre>
     * StringUtils.strip(null)     = null
     * StringUtils.strip("")       = ""
     * StringUtils.strip("   ")    = ""
     * StringUtils.strip("abc")    = "abc"
     * StringUtils.strip("  abc")  = "abc"
     * StringUtils.strip("abc  ")  = "abc"
     * StringUtils.strip(" abc ")  = "abc"
     * StringUtils.strip(" ab c ") = "ab c"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @return null 臾몄옄�뿴 �씪寃쎌슦 null
     * 		   "" �씪寃쎌슦 ""
     */
    public static String strip(String str) {
    	return org.apache.commons.lang.StringUtils.strip(str);
    }

    /**
     * 臾몄옄�뿴 醫뚯슦�뿉 stripChars�뿉 議댁옱�븯�뒗 臾몄옄瑜� �젣嫄고븳�떎.<br />
     *
     * <pre>
     * StringUtils.strip(null, *)          = null
     * StringUtils.strip("", *)            = ""
     * StringUtils.strip("abc", null)      = "abc"
     * StringUtils.strip("  abc", null)    = "abc"
     * StringUtils.strip("abc  ", null)    = "abc"
     * StringUtils.strip(" abc ", null)    = "abc"
     * StringUtils.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param stripChars 怨듬갚�쑝濡� 痍④툒 �븯�뒗 臾몄옄�뿴
     * @return null 臾몄옄�뿴 �씪寃쎌슦 null
     * 		   "" �씪寃쎌슦 ""
     */
    public static String strip(String str, String stripChars) {
    	return org.apache.commons.lang.StringUtils.strip(str, stripChars);
    }

    /**
     * 諛곗뿴�뿉 �엳�뒗 紐⑤뱺 臾몄옄�뿴�뱾�쓣 �븵�뮘�뿉 �엳�뒗 �뒪�럹�씠�뒪瑜� �젣嫄고븳�떎.<br />
     *
     * <pre>
     * StringUtils.stripAll(null)             = null
     * StringUtils.stripAll([])               = []
     * StringUtils.stripAll(["abc", "  abc"]) = ["abc", "abc"]
     * StringUtils.stripAll(["abc  ", null])  = ["abc", null]
     * </pre>
     *
     * @param strs 臾몄옄�뿴
     * @return strip寃곌낵 臾몄옄�뿴
     */
    public static String[] stripAll(String[] strs) {
    	return org.apache.commons.lang.StringUtils.stripAll(strs);
    }
    
    // Mid
    //-----------------------------------------------------------------------
    /**
     * 諛곗뿴�뿉 �엳�뒗 紐⑤뱺 臾몄옄�뿴�쓣 ���긽�쑝濡� �븵�뮘�뿉 議댁옱�븯�뒗 �젣嫄곕Ц�옄�뱾�쓣 �젣嫄고븳�떎.<br />
     *
     * <pre>
     * StringUtils.stripAll(null, *)                = null
     * StringUtils.stripAll([], *)                  = []
     * StringUtils.stripAll(["abc", "  abc"], null) = ["abc", "abc"]
     * StringUtils.stripAll(["abc  ", null], null)  = ["abc", null]
     * StringUtils.stripAll(["abc  ", null], "yz")  = ["abc  ", null]
     * StringUtils.stripAll(["yabcz", null], "yz")  = ["abc", null]
     * </pre>
     *
     * @param strs 臾몄옄�뿴
     * @param stripChars �젣嫄곕Ц�옄�뱾
     * @return strip 寃곌낵 臾몄옄�뿴
     */
    public static String[] stripAll(String[] strs, String stripChars) {
    	return org.apache.commons.lang.StringUtils.stripAll(strs, stripChars);
    }

    /**
     * 臾몄옄�뿴�쓣 �뮘�뿉�꽌遺��꽣 �젣嫄곕Ц�옄�뱾 �븯�굹�떇 �젣嫄고븳�떎.<br />
     *
     * <pre>
     * StringUtils.stripEnd(null, *)          = null
     * StringUtils.stripEnd("", *)            = ""
     * StringUtils.stripEnd("abc", "")        = "abc"
     * StringUtils.stripEnd("abc", null)      = "abc"
     * StringUtils.stripEnd("  abc", null)    = "  abc"
     * StringUtils.stripEnd("abc  ", null)    = "abc"
     * StringUtils.stripEnd(" abc ", null)    = " abc"
     * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
     * StringUtils.stripEnd("120.00", ".0")   = "12"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param stripChars 怨듬갚�쑝濡� 痍④툒 �븯�뒗 臾몄옄�뿴
     * @return null 臾몄옄�뿴 �씪寃쎌슦 null
     * 		   "" �씪寃쎌슦 ""
     */
    public static String stripEnd(String str, String stripChars) {
    	return org.apache.commons.lang.StringUtils.stripEnd(str, stripChars);
    }

    /**
     * 臾몄옄�뿴�쓣 �븵�뿉�꽌遺��꽣 �젣嫄곕Ц�옄�뱾 �븯�굹�떇 �젣嫄고븳�떎.<br />
     *
     * <pre>
     * StringUtils.stripStart(null, *)          = null
     * StringUtils.stripStart("", *)            = ""
     * StringUtils.stripStart("abc", "")        = "abc"
     * StringUtils.stripStart("abc", null)      = "abc"
     * StringUtils.stripStart("  abc", null)    = "abc"
     * StringUtils.stripStart("abc  ", null)    = "abc  "
     * StringUtils.stripStart(" abc ", null)    = "abc "
     * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param stripChars  �젣嫄곕Ц�옄�뱾
     * @return null 臾몄옄�뿴 �씪寃쎌슦 null
     * 		   "" �씪寃쎌슦 ""
     */
    public static String stripStart(String str, String stripChars) {
    	return org.apache.commons.lang.StringUtils.stripStart(str, stripChars);
    }

    /**
     * 泥섎━臾몄옄媛� NULL�씪寃쎌슦 怨듬갚�쓣 諛섑솚, �븘�땺寃쎌슦 醫뚯슦�뿉 �엳�뒗<br />
     * �뒪�럹�씠�뒪瑜� �젣嫄고븳�떎.<br />  
     *
     * <pre>
     * StringUtils.stripToEmpty(null)     = ""
     * StringUtils.stripToEmpty("")       = ""
     * StringUtils.stripToEmpty("   ")    = ""
     * StringUtils.stripToEmpty("abc")    = "abc"
     * StringUtils.stripToEmpty("  abc")  = "abc"
     * StringUtils.stripToEmpty("abc  ")  = "abc"
     * StringUtils.stripToEmpty(" abc ")  = "abc"
     * StringUtils.stripToEmpty(" ab c ") = "ab c"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @return null 臾몄옄�뿴 �삉�뒗 "" �씪寃쎌슦 ""
     */
    public static String stripToEmpty(String str) {
    	return org.apache.commons.lang.StringUtils.stripToEmpty(str);
    }

    /**
     * 臾몄옄�뿴 醫뚯슦�뿉 �엳�뒗 �뒪�럹�씠�뒪瑜� �젣嫄고븳�떎.<br />
     *
     * <pre>
     * StringUtils.stripToNull(null)     = null
     * StringUtils.stripToNull("")       = null
     * StringUtils.stripToNull("   ")    = null
     * StringUtils.stripToNull("abc")    = "abc"
     * StringUtils.stripToNull("  abc")  = "abc"
     * StringUtils.stripToNull("abc  ")  = "abc"
     * StringUtils.stripToNull(" abc ")  = "abc"
     * StringUtils.stripToNull(" ab c ") = "ab c"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @return null 臾몄옄�뿴 �삉�뒗 "" �씪寃쎌슦 null
     */
    public static String stripToNull(String str) {
    	return org.apache.commons.lang.StringUtils.stripToNull(str);
    }

    /**
     * 臾몄옄�뿴 �궡�뿉�꽌 臾몄옄�뿴�쓣 痍⑤뱷�븳�떎.<br />
     * 
     * <p> 
     * 臾몄옄�뿴�쓽 留덉씠�꼫�뒪�룄 �궗�슜媛��뒫�븳 <code>n</code>踰덉㎏�뿉�꽌 遺��꽣 留덉�留됯퉴吏��쓽 臾몄옄�뿴�쓣 痍⑤뱷.
     * </p>
     * 
     * <p>
     * 臾몄옄�뿴�씠 <code>null</code>�씪寃쎌슦 <code>null</code>�쓣 諛섑솚.
     * 臾몄옄�뿴�씠 怨듬갚�씪寃쎌슦 怨듬갚�쓣 諛섑솚.
     * </p>
     *
     * <pre>
     * StringUtils.substring(null, *)   = null
     * StringUtils.substring("", *)     = ""
     * StringUtils.substring("abc", 0)  = "abc"
     * StringUtils.substring("abc", 2)  = "c"
     * StringUtils.substring("abc", 4)  = "
     * "
     * StringUtils.substring("abc", -2) = "bc"
     * StringUtils.substring("abc", -4) = "abc"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param start �떆�옉�젏
     * @return �떆�옉�젏�쑝濡쒕��꽣�쓽 臾몄옄�뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String substring(String str, int start) {
    	return org.apache.commons.lang.StringUtils.substring(str, start);
    }

    /**
     * 臾몄옄�뿴 �궡�뿉�꽌 臾몄옄�뿴�쓣 痍⑤뱷�븳�떎.<br />
     * 
     * <p> 
     * 臾몄옄�뿴�쓽 留덉씠�꼫�뒪�룄 �궗�슜媛��뒫�븳 <code>n</code>踰덉㎏�뿉�꽌 遺��꽣 留덉�留됯퉴吏��쓽 臾몄옄�뿴�쓣 痍⑤뱷.
     * </p>
     * 
     * <p>
     * 臾몄옄�뿴�씠 <code>null</code>�씪寃쎌슦 <code>null</code>�쓣 諛섑솚.
     * 臾몄옄�뿴�씠 怨듬갚�씪寃쎌슦 怨듬갚�쓣 諛섑솚.
     * </p>
     *
     * <pre>
     * StringUtils.substring(null, *, *)    = null
     * StringUtils.substring("", * ,  *)    = "";
     * StringUtils.substring("abc", 0, 2)   = "ab"
     * StringUtils.substring("abc", 2, 0)   = ""
     * StringUtils.substring("abc", 2, 4)   = "c"
     * StringUtils.substring("abc", 4, 6)   = ""
     * StringUtils.substring("abc", 2, 2)   = ""
     * StringUtils.substring("abc", -2, -1) = "b"
     * StringUtils.substring("abc", -4, 2)  = "ab"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param start �떆�옉�젏
     * @param end 醫낅즺�젏
     * @return �떆�옉�젏�쑝濡쒕��꽣 醫낅즺�젏源뚯��쓽 臾몄옄�뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String substring(String str, int start, int end) {
    	return org.apache.commons.lang.StringUtils.substring(str, start, end);
    }

    /**
     * 臾몄옄�뿴�쓽 泥섏쓬 SEPARATOR遺�遺꾨��꽣 臾몄옄�뿴�쓣 援ы븳�떎.<br />
     *
     * <pre>
     * StringUtils.substringAfter(null, *)      = null
     * StringUtils.substringAfter("", *)        = ""
     * StringUtils.substringAfter(*, null)      = ""
     * StringUtils.substringAfter("abc", "a")   = "bc"
     * StringUtils.substringAfter("abcba", "b") = "cba"
     * StringUtils.substringAfter("abc", "c")   = ""
     * StringUtils.substringAfter("abc", "d")   = ""
     * StringUtils.substringAfter("abc", "")    = "abc"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separator SEPARATOR
     * @return 寃곌낵臾몄옄�뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String substringAfter(String str, String separator) {
    	return org.apache.commons.lang.StringUtils.substringAfter(str, separator);
    }

    /**
     * 臾몄옄�뿴�쓽 留덉�留� separator遺�遺꾨��꽣 臾몄옄�뿴�쓣 援ы븳�떎.<br />
     *
     * <pre>
     * StringUtils.substringAfterLast(null, *)      = null
     * StringUtils.substringAfterLast("", *)        = ""
     * StringUtils.substringAfterLast(*, "")        = ""
     * StringUtils.substringAfterLast(*, null)      = ""
     * StringUtils.substringAfterLast("abc", "a")   = "bc"
     * StringUtils.substringAfterLast("abcba", "b") = "a"
     * StringUtils.substringAfterLast("abc", "c")   = ""
     * StringUtils.substringAfterLast("a", "a")     = ""
     * StringUtils.substringAfterLast("a", "z")     = ""
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separator SEPARATOR
     * @return 寃곌낵臾몄옄�뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String substringAfterLast(String str, String separator) {
    	return org.apache.commons.lang.StringUtils.substringAfterLast(str, separator);
    }

    // SubStringAfter/SubStringBefore
    //-----------------------------------------------------------------------
    /**
     * 臾몄옄�뿴�쓽 泥섏쓬 SEPARATOR遺�遺꾧퉴吏� 臾몄옄�뿴�쓣 援ы븳�떎.<br />
     *
     * <pre>
     * StringUtils.substringBefore(null, *)      = null
     * StringUtils.substringBefore("", *)        = ""
     * StringUtils.substringBefore("abc", "a")   = ""
     * StringUtils.substringBefore("abcba", "b") = "a"
     * StringUtils.substringBefore("abc", "c")   = "ab"
     * StringUtils.substringBefore("abc", "d")   = "abc"
     * StringUtils.substringBefore("abc", "")    = ""
     * StringUtils.substringBefore("abc", null)  = "abc"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separator SEPARATOR
     * @return 寃곌낵臾몄옄�뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String substringBefore(String str, String separator) {
    	return org.apache.commons.lang.StringUtils.substringBefore(str, separator);
    }

    /**
     * 臾몄옄�뿴�쓽 留덉�留� separator遺�遺꾧퉴吏� 臾몄옄�뿴�쓣 援ы븳�떎.<br />
     * 
     * <pre>
     * StringUtils.substringBeforeLast(null, *)      = null
     * StringUtils.substringBeforeLast("", *)        = ""
     * StringUtils.substringBeforeLast("abcba", "b") = "abc"
     * StringUtils.substringBeforeLast("abc", "c")   = "ab"
     * StringUtils.substringBeforeLast("a", "a")     = ""
     * StringUtils.substringBeforeLast("a", "z")     = "a"
     * StringUtils.substringBeforeLast("a", null)    = "a"
     * StringUtils.substringBeforeLast("a", "")      = "a"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param separator SEPARATOR
     * @return 寃곌낵臾몄옄�뿴, 臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String substringBeforeLast(String str, String separator) {
    	return org.apache.commons.lang.StringUtils.substringBeforeLast(str, separator);
    }

    // Substring between
    //-----------------------------------------------------------------------
    /**
     * 臾몄옄�뿴�뿉�꽌 TAG�궗�씠�뿉 �엳�뒗 臾몄옄�뿴�쓣 援ы븳�떎.<br />
     * 
     * <pre>
     * StringUtils.substringBetween(null, *)            = null
     * StringUtils.substringBetween("", "")             = ""
     * StringUtils.substringBetween("", "tag")          = null
     * StringUtils.substringBetween("tagabctag", null)  = null
     * StringUtils.substringBetween("tagabctag", "")    = ""
     * StringUtils.substringBetween("tagabctag", "tag") = "abc"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param tag TAG
     * @return 寃곌낵臾몄옄�뿴, 臾몄옄�뿴�뿉 TAG媛� 議댁옱�븯吏��븡�쓣 寃쎌슦<code>null</code>
     */
    public static String substringBetween(String str, String tag) {
    	return org.apache.commons.lang.StringUtils.substringBetween(str, tag);
    }

    /**
     * 臾몄옄�뿴�뿉�꽌 OPEN遺��꽣 CLOSE源뚯� �궗�씠�뿉 �엳�뒗 臾몄옄�뿴�쓣 援ы븳�떎.<br />
     * 
     * <pre>
     * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
     * StringUtils.substringBetween(null, *, *)          = null
     * StringUtils.substringBetween(*, null, *)          = null
     * StringUtils.substringBetween(*, *, null)          = null
     * StringUtils.substringBetween("", "", "")          = ""
     * StringUtils.substringBetween("", "", "]")         = null
     * StringUtils.substringBetween("", "[", "]")        = null
     * StringUtils.substringBetween("yabcz", "", "")     = ""
     * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
     * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param open OPEN
     * @param close CLOSE
     * @return 寃곌낵臾몄옄�뿴, 臾몄옄�뿴�뿉 TAG媛� 議댁옱�븯吏��븡�쓣 寃쎌슦<code>null</code>
     */
    public static String substringBetween(String str, String open, String close) {
    	return org.apache.commons.lang.StringUtils.substringBetween(str, open, close);
    }

    /**
     * 臾몄옄�뿴濡쒕��꽣 OPEN怨� CLOSE�뿉 媛먯떥吏� 遺�遺꾨Ц�옄�뿴�쓣 諛곗뿴濡� 援ы븳�떎.<br />
     *
     * <pre>
     * StringUtils.substringsBetween("[a][b][c]", "[", "]") = ["a","b","c"]
     * StringUtils.substringsBetween(null, *, *)            = null
     * StringUtils.substringsBetween(*, null, *)            = null
     * StringUtils.substringsBetween(*, *, null)            = null
     * StringUtils.substringsBetween("", "[", "]")          = []
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @param open OPEN
     * @param close CLOSE
     * @return 寃곌낵臾몄옄諛곗뿴, 臾몄옄�뿴怨� OPEN 諛� CLOSE以� null�씤 寃쎌슦<code>null</code>
     */
	public static String[] substringsBetween(String str, String open, String close) {
    	return org.apache.commons.lang.StringUtils.substringsBetween(str, open, close);
    }

    /**
     * ��臾몄옄�뒗 �냼臾몄옄濡� 蹂��솚�븯怨� �냼臾몄옄�뒗 ��臾몄옄濡� 蹂��솚�븳�떎.<br />
     *
     * <pre>
     * StringUtils.swapCase(null)                 = null
     * StringUtils.swapCase("")                   = ""
     * StringUtils.swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
     * </pre>
     *
     * @param str  the String to swap case, may be null
     * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String swapCase(String str) {
    	return org.apache.commons.lang.StringUtils.swapCase(str);
    }

    /**
	 * 泥ル쾲吏� 臾몄옄瑜� ��臾몄옄濡� 蹂�寃�<br><br>
	 *
	 * StringUtils.swapFirstLetterCase("java") = "Java"
	 *
	 * @param str 臾몄옄�뿴
	 * @return 泥ル쾲吏� 臾몄옄瑜� ��臾몄옄濡� 蹂�寃쏀븳 臾몄옄�뿴
	 */
	public static String swapFirstLetterCase(String str) {
		StringBuilder sbuf = new StringBuilder(str);
		sbuf.deleteCharAt(0);
		if (Character.isLowerCase(str.substring(0, 1).toCharArray()[0])) {
			sbuf.insert(0, str.substring(0, 1).toUpperCase());
		} else {
			sbuf.insert(0, str.substring(0, 1).toLowerCase());
		}
		return sbuf.toString();
	}

    /**
	 * 二쇱뼱吏� String�뿉 ���빐�꽌 delimiter瑜� �씠�슜�븯�뿬 tokenize�븳 �썑 String[]濡� 戮묒븘�궦�떎.<br>
	 * Java�쓽 StringTokenizer瑜� �씠�슜�븯�뿬 泥섎━�븳�떎.<br>
	 * �샃�뀡�뿉 �뵲�씪 怨듬갚(space)�뿉 ���븳 泥섎━(trim), 媛믪씠 ""�씤 token�뿉 ���븳 �룷�븿 �뿬遺�瑜� 寃곗젙�븷 �닔 �엳�떎.<br>
	 * StringTokenizer瑜� �씠�슜�븯誘�濡�, �뿰�냽�맂 delimiter �궗�씠�뒗 token�씠 �릺吏� �븡�뒗�떎.<br>
	 * 二쇱뼱吏� String�씠 null�씪 寃쎌슦 null�쓣 return�븳�떎.<br>
	 * delimiter媛� null�씪 寃쎌슦 二쇱뼱吏� String�쓣 �븯�굹�쓽 element濡� 媛�吏��뒗 String[]瑜� return�븳�떎.<br><br>
	 *
	 * StringUtils.tokenizeToStringArray("aaa.bbb.ccc.ddd", ".", true, true) = {"aaa", "bbb", "ccc", "ddd"}
	 *
	 * @param str 臾몄옄�뿴
	 * @param separator 援щ텇�옄
	 * @param trimTokens 怨듬갚�젣嫄� �뿬遺�
	 * @param ignoreEmptyTokens 鍮� �넗�겙 臾댁떆 �뿬遺�
	 * @return an array of parsed Strings
	 */
	public static String[] tokenizeToStringArray(String str, String separator, boolean trimTokens, boolean ignoreEmptyTokens) {
		if (str == null) {
			return null;
		}
		if (separator == null) {
			return new String[] {str};
		}
		StringTokenizer st = new StringTokenizer(str, separator);
		List<String> tokens = new ArrayList<String>();
		do {
			if (!st.hasMoreTokens()) {
				break;
			}
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() != 0) {
				tokens.add(token);
			}
		} while (true);
		return tokens.toArray(new String[tokens.size()]);
	}

    /**
	 * �엯�젰�맂 臾몄옄�뿴濡� 遺��꽣 �닽�옄留� 異붿텧�븯�뿬 '-'媛� �룷�븿�맂 �쟾�솕踰덊샇 �삎�깭�쓽 臾몄옄�뿴濡� �룷留ㅽ똿�븯�뿬 由ы꽩�븳�떎.<br><br>
	 *
	 * StringUtils.toTelephoneNumberFormat("032-123-4567") = "032-123-4567"<br>
	 * StringUtils.toTelephoneNumberFormat("021234567") = "02-123-4567"<br>
	 * StringUtils.toTelephoneNumberFormat("0212345678") = "02-1234-5678"<br>
	 * StringUtils.toTelephoneNumberFormat("1234567") = "123-4567"
	 *
	 * @param str 臾몄옄�뿴
	 * @return �쟾�솕踰덊샇 �삎�깭濡� 蹂��솚�맂 臾몄옄�뿴
	 */
	public static String toTelephoneNumberFormat(String str) {

		int endNumberDigit = 4;
		int minNumberDigit = 7;

		if (StringUtil.isEmpty(str)) {
			return null;
		}

		String origin = str.trim();
		String tempNumber;

		int originLength = origin.length();

		// extract numeric chars only
		if (StringUtil.isNotNumeric(origin)) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < originLength; i++) {
				if (Character.isDigit(origin.charAt(i))) {
					sb.append(origin.charAt(i));
				}
			}
			tempNumber = sb.toString();
		} else {
			tempNumber = origin;
		}

		int numberLength = tempNumber.length();

		if (numberLength < minNumberDigit) {
			return tempNumber;
		}

		String firstNumber = "";
		String secondNumber = "";
		String thirdNumber = "";

		if (tempNumber.charAt(0) == '0') { // local number or mobile number
			if (tempNumber.charAt(1) == '2') { // Seoul
				firstNumber = tempNumber.substring(0, 2);
				secondNumber = tempNumber.substring(2, numberLength
						- endNumberDigit);
				thirdNumber = tempNumber.substring(numberLength
						- endNumberDigit, numberLength); // split last 4 digits
			} else { // local number or mobile number
				firstNumber = tempNumber.substring(0, 3);
				secondNumber = tempNumber.substring(3, numberLength
						- endNumberDigit);
				thirdNumber = tempNumber.substring(numberLength
						- endNumberDigit, numberLength); // split last 4 digits
			}
			return firstNumber + "-" + secondNumber + "-" + thirdNumber;
		} else { // telephone number without local number
			firstNumber = tempNumber
					.substring(0, numberLength - endNumberDigit);
			secondNumber = tempNumber.substring(numberLength - endNumberDigit,
					numberLength);

			return firstNumber + "-" + secondNumber;
		}

	}

    /**
	 * 二쇱뼱吏� 6�옄由� �닽�옄 String�쓣 "111-111" �삎�깭�쓽 �슦�렪踰덊샇 �룷留룹쑝濡� 蹂��솚�븳�떎.<br>
	 * 二쇱뼱吏� String�씠 6�옄由ш� �븘�땺 寃쎌슦 ""瑜� return�븳�떎.<br>
	 * 二쇱뼱吏� String�씠 �닽�옄留뚯쑝濡� 援ъ꽦�릺�뼱 �엳吏� �븡�쓣 寃쎌슦 ""�쓣 return�븳�떎.<br><br>
	 *
	 * StringUtils.toZipCodePattern("111111") = "111-111"
	 *
	 * @param str 臾몄옄�뿴
	 * @return �슦�렪踰덊샇 �삎�깭濡� 蹂��솚�맂 臾몄옄�뿴
	 */
	public static String toZipCodePattern(String str) {
		if (str == null) {
			return "";
		}
		if (str.length() != 6 || !isDigit(str)) {
			return "";
		} else {
			StringBuffer buffer = new StringBuffer();
			buffer.append(str.substring(0, 3));
			buffer.append('-');
			buffer.append(str.substring(3, 6));
			return buffer.toString();
		}
	}

    /**
     * 臾몄옄�뿴�쓽 醫뚯슦�뿉 �엳�뒗 �뒪�럹�씠�뒪瑜� �젣嫄고븳�떎.<br />
     * 
     * <pre>
     * StringUtils.trim(null)          = null
     * StringUtils.trim("")            = ""
     * StringUtils.trim("     ")       = ""
     * StringUtils.trim("abc")         = "abc"
     * StringUtils.trim("    abc    ") = "abc"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @return null 臾몄옄�뿴 �씪寃쎌슦 null
     * 		   "" �씪寃쎌슦 ""
     */
    public static String trim(String str) {
        return org.apache.commons.lang.StringUtils.trim(str);
    }

    /**
	 * �엯�젰�맂 臾몄옄�뿴�뿉�꽌 二쇱뼱吏� 臾몄옄�뿴怨� �씪移섑븯�뒗 遺�遺꾩쓣 trim�븳�떎.<br><br>
	 *
	 * StringUtils.trim("Anyframe*Java", "*") = "AnyframeJava"
	 *
	 * @param origString 臾몄옄�뿴
	 * @param trimString trim�븷 臾몄옄�뿴
	 * @return 二쇱뼱吏� 臾몄옄�뿴�뿉�꽌 trim�븷 臾몄옄�뿴�쓣 trim�븳 臾몄옄�뿴
	 */
	public static String trim(String origString, String trimString) {
		int startPosit = origString.indexOf(trimString);
		if (startPosit != -1) {
			int endPosit = trimString.length() + startPosit;
			return origString.substring(0, startPosit)
					+ origString.substring(endPosit);
		}
		return origString;
	}

    /**
     * 泥섎━臾몄옄媛� NULL�씪寃쎌슦 怨듬갚�쓣 諛섑솚, �븘�땺寃쎌슦 醫뚯슦�뿉 �엳�뒗<br />
     * �뒪�럹�씠�뒪瑜� �젣嫄고븳�떎.<br />
     * <pre>
     * StringUtils.trimToEmpty(null)          = ""
     * StringUtils.trimToEmpty("")            = ""
     * StringUtils.trimToEmpty("     ")       = ""
     * StringUtils.trimToEmpty("abc")         = "abc"
     * StringUtils.trimToEmpty("    abc    ") = "abc"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @return null 臾몄옄�뿴 �삉�뒗 ""�씪 寃쎌슦 ""
     */
    public static String trimToEmpty(String str) {
    	return org.apache.commons.lang.StringUtils.trimToEmpty(str);
    }

    /**
     * 臾몄옄�뿴�쓽 醫뚯슦�뿉 �엳�뒗 �뒪�럹�씠�뒪瑜� �젣嫄고븳�떎.<br />
     * �젣嫄고썑 寃곌낵媛� 怨듬갚�씪寃쎌슦 NULL瑜� 諛섑솚<br />
     *
     * <pre>
     * StringUtils.trimToNull(null)          = null
     * StringUtils.trimToNull("")            = null
     * StringUtils.trimToNull("     ")       = null
     * StringUtils.trimToNull("abc")         = "abc"
     * StringUtils.trimToNull("    abc    ") = "abc"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @return null 臾몄옄�뿴 �삉�뒗 ""�씪 寃쎌슦 null
     */
    public static String trimToNull(String str) {
    	return org.apache.commons.lang.StringUtils.trimToNull(str);
    }

    /**
     * 臾몄옄�뿴�쓽 泥ルЦ�옄瑜� �냼臾몄옄濡� 蹂��솚�븳�떎.<br />
     *
     * <pre>
     * StringUtils.uncapitalize(null)  = null
     * StringUtils.uncapitalize("")    = ""
     * StringUtils.uncapitalize("Cat") = "cat"
     * StringUtils.uncapitalize("CAT") = "cAT"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String uncapitalize(String str) {
    	return org.apache.commons.lang.StringUtils.uncapitalize(str);
    }

    /**
     * 臾몄옄�뿴�쓣 ��臾몄옄濡� 蹂��솚�븳�떎.<br />
     *
     * <pre>
     * StringUtils.upperCase(null)  = null
     * StringUtils.upperCase("")    = ""
     * StringUtils.upperCase("aBc") = "ABC"
     * </pre>
     *
     * @param str 臾몄옄�뿴
     * @return 寃곌낵臾몄옄�뿴, �엯�젰臾몄옄�뿴�씠 null�씪寃쎌슦 <code>null</code>
     */
    public static String upperCase(String str) {
    	return org.apache.commons.lang.StringUtils.upperCase(str);
    }


	// �뙆�씪誘명꽣 -> Map
	public static HashMap<String, Object> convertStringToQueryMap(String query)  
	{  
	    String[] params = query.split("&");  
	    HashMap<String, Object> map = new HashMap<String, Object>();  
	    for (String param : params)  
	    {  
	        String param_array[] = param.split("=");  
	        String name = "";
	        String value = "";
	        try{
	        	name = param_array[0];  
	        	value = param_array[1];  
	        	map.put(name.trim(), value.trim());
	        }catch(Exception e){
	        	
	        }
	    }  
	    return map;  
	}
	
	public static String createLicenseCode(){
		String ramdom_code = getRandom("ABC123",16);
		String st1=ramdom_code.substring(0,4);
		String st2=ramdom_code.substring(4,8);
		String st3=ramdom_code.substring(8,12);
		String st4=ramdom_code.substring(12,16);
		String return_code = st1+"-"+st2+"-"+st3+"-"+st4;
		return return_code;
	}
	
	// �옖�뜡 �븿�닔 �깮�꽦
	public static String getRandom(String type, int loopCount) {
		
		String dummyString="1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijlmnopqrstuvwxyz";
		if(type != null){
			if(type.equals("123"))dummyString = "1234567890";
			else if(type.equals("abc"))dummyString = "abcdefghijlmnopqrstuvwxyz";
			else if(type.equals("ABC"))dummyString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			else if(type.equals("ABC123") || type.equals("123ABC"))dummyString = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			else if(type.equals("abc123") || type.equals("123abc"))dummyString = "1234567890abcdefghijklmnopqrstuvwxyz";
			else if(type.equals("aA1!"))dummyString = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@#$%_-";
		}
		Random random=new Random();
		// char 48=0 65=A 90=Z 97=a 122=z
		StringBuilder tempBuilder=new StringBuilder(100);
		int randomInt;
		char tempChar;
		for(int loop=0;loop<loopCount;loop++) {
			randomInt=random.nextInt(dummyString.length());
			tempChar=dummyString.charAt(randomInt);
			tempBuilder.append(tempChar);
		}
		//System.out.println(tempBuffer);
		return tempBuilder.toString();
	}
	
	public static String convertTimestampToDate(int timestamp)
	{
		String dd=null;
		String hh=null;
		String ii=null;
		String ss=null;
		
		try{
			int temp=timestamp;

			dd=String.valueOf((int)Math.floor(temp/(3600*24)));

			temp=temp%(3600*24);
			hh=String.format("%02d", (int)Math.floor(temp/(60*60)));

			temp=temp%(60*60);
			ii=String.format("%02d", (int)Math.floor(temp/60));

			temp=temp%60;
			ss=String.format("%02d", (int)Math.floor(temp));

		}catch (Exception e){
			return "-";
		}
		
		return String.format("%sd %s:%s:%s", dd,hh,ii,ss);
	}
	
	public static String createYMDDir(){
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd",Locale.KOREA);
		Date currentTime=new Date();
		String mTime=mSimpleDateFormat.format(currentTime);
		return mTime;
	}
	
	public static String avoidNull(String s0, String s1)
    {
        return StringUtil.isEmptyTrimmed(s0) ? s1 : s0;
    }
	
	
	/**
	 * �옖�뜡�궎瑜� �깮�꽦�븳�떎. �뜑誘� �뒪�듃留곸뿉 �엳�뒗 �쑀�삎�쓣 戮묒븘�꽌 �깮�꽦.
	 * loopCount�뒗 �옄由우닔瑜� �쓽誘명븿.
	 */
	public static String createKey(int loopCount) {
		String dummyString="1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_";
		
		Random random=new Random();
		// char 48=0 65=A 90=Z 97=a 122=z
		StringBuilder tempBuilder=new StringBuilder(100);
		int randomInt;
		char tempChar;
		for(int loop=0;loop < loopCount;loop++) {
			randomInt=random.nextInt(dummyString.length());
			tempChar=dummyString.charAt(randomInt);
			tempBuilder.append(tempChar);
		}
		
		return tempBuilder.toString();
	}


}