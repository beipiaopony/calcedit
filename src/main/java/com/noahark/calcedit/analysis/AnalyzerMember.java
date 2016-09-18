package com.noahark.calcedit.analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalyzerMember {

	private static Map<String, String> systemKeyWord = new HashMap<String, String>();
	// private Map<String,String> members;

	private static String tx = "/*\n" + "*\n" + "* 作者:xxx;\n" + "* 创建时间:\"2015-07-15\";\n"
			+ "* 脚本描述:9.61月度预算-利润表（输入表）\n" + "* 修改历史:\n" + "*\n" + "*\n" + "*/\n" + "\n"
			+ "sdfsasdf set FIX ( /*科目*//*组织*//*年*/{CurYear}/*当前年*/, /*期间*/&BudgetMonth, /*版本*/V0299/*最终版*/, /*情景*/S01/*预算*/, /*专业*/BU00/*专业缺省*/,/*综合1*/SP1Rpt00/*报表类综合1缺省*/)\n"
			+ "        /*科目*/\n" + "        @IDescendants( A020106)/*加：非邮政业的营业利润*/;\n" + "        /*科目*/\n"
			+ "        A020103 /*三、营业利润*/= /*acc*/A020101_a /*一、营业总收入*/- /*科目*/A020102/*二、营业总成本*/;\n"
			+ "        /*科目*/\n"
			+ "        A020107 /*四、经营利润*/= /*科目*/\"A020103-P\" /*三、营业利润*/+ /*科目*/A020106/*加：非邮政业的营业利润*/+A02010601+A02010602+A02010603;\n"
			+ "        /*科目*/\n"
			+ "        A020110 /*五、利润总额*/= /*科*目* /A020107 /*四、经营利润*/+ /*科目*/A020108 /*加：营业外收入*/- /*科目*/A020109/*减：营业外支出*/;\n"
			+ "        /*科目*/\n" + "        A020112 /*六、净利润*/= /*科目*/A020110 /*五、利润总额*/- /*科目*/A020111/*减：所得税费用*/;\n"
			+ "        /*科目*/\n"
			+ "        A020114 /*七、实现的超额利润或减亏额*/= /*科目*/A020112 /*六、净利润*/- /*科目*/A020113/*减：本年度利润目标*/;\n" + "ENDFIX\n"
			+ "FIX ( /*科目*/@IDescendants( A0201),/*组织*//*年*/&CurYear/*当前年*/, /*期间*/&BudgetMonth/*预算月份*/, /*版本*/V0299/*最终版*/, /*情景*//*专业*/BU00/*专业缺省*/,/*综合1*/SP1Rpt00/*报表类综合1缺省*/)\n"
			+ "S20 /*预计累计完成进度*/= ( S01 /*预算*/+ &CurMonth/*当前月*/->S09/*累计实际*/) / YearDefault/*全年*/->S01/*预算*/;";

	static {
		systemKeyWord.put("UPDATECALC", null);
		systemKeyWord.put("OFF", null);
		systemKeyWord.put("AGGMISSG", null);
		systemKeyWord.put("ON", null);
		systemKeyWord.put("AND", null);
		systemKeyWord.put("NOT", null);
		systemKeyWord.put("OR", null);
		systemKeyWord.put("CLEARBLOCK", null);
		systemKeyWord.put("CLEARCCTRACK", null);
		systemKeyWord.put("CLEARDATA", null);
		systemKeyWord.put("DATACOPY", null);
		systemKeyWord.put("DATAEXPORT", null);
		systemKeyWord.put("DATAEXPORTCOND", null);
		systemKeyWord.put("DATAIMPORTBIN", null);
		systemKeyWord.put("DATAEXPORTOPTIONS", null);
		systemKeyWord.put("DATAIMPORTIGNORETIMESTAMP", null);
		systemKeyWord.put("AGGMISSG", null);
		systemKeyWord.put("CACHE", null);
		systemKeyWord.put("CCTRACKCALC", null);
		systemKeyWord.put("CLEARUPDATESTATUS", null);
		systemKeyWord.put("FRMLBOTTOMUP", null);
		systemKeyWord.put("FRMLRTDYNAMIC", null);
		systemKeyWord.put("LOCKBLOCK", null);
		systemKeyWord.put("MSG", null);
		systemKeyWord.put("NOTICE", null);
		systemKeyWord.put("REMOTECALC", null);
		systemKeyWord.put("RUNTIMESUBVARS", null);
		systemKeyWord.put("UPDATECALC", null);
		systemKeyWord.put("UPTOLOCAL", null);
		systemKeyWord.put("CALC", null);
		systemKeyWord.put("ALL", null);
		systemKeyWord.put("AVERAGE", null);
		systemKeyWord.put("FIRST", null);
		systemKeyWord.put("LAST", null);
		systemKeyWord.put("TWOPASS", null);
		systemKeyWord.put("CCONV", null);
		systemKeyWord.put("DETAIL", null);
		systemKeyWord.put("SUMMARY", null);
		systemKeyWord.put("ALL", null);
		systemKeyWord.put("UPPER", null);
		systemKeyWord.put("NONINPUT", null);
		systemKeyWord.put("DYNAMIC", null);
		systemKeyWord.put("EMPTY", null);
		systemKeyWord.put("HIGH", null);
		systemKeyWord.put("DEFAULT", null);
		systemKeyWord.put("LOW", null);
		systemKeyWord.put("CALCPARALLEL", null);
		systemKeyWord.put("CALCTASKDIMS", null);
		systemKeyWord.put("AFTER", null);
		systemKeyWord.put("ONLY", null);
		systemKeyWord.put("COPYMISSINGBLOCK", null);
		systemKeyWord.put("CREATENONMISSINGBLK", null);
		systemKeyWord.put("CREATEBLOCKONEQ", null);
		systemKeyWord.put("EMPTYMEMBERSETS", null);
		systemKeyWord.put("ERROR", null);
		systemKeyWord.put("INFO", null);
		systemKeyWord.put("NONE", null);
		systemKeyWord.put("VAR", null);
		systemKeyWord.put("FIX", null);
		systemKeyWord.put("ENDIFX", null);
		systemKeyWord.put("IF", null);
		systemKeyWord.put("ENDIF", null);
		systemKeyWord.put("ELSE", null);
		systemKeyWord.put("ELSEIF", null);
		systemKeyWord.put("SET", null);
		systemKeyWord.put("LOOP", null);
		systemKeyWord.put("ENDLOOP", null);

	}

	public static void main(String[] args) {
		// String text = "The Lucene PMC is pleased to announce the release of
		// the Apache Solr Reference Guide for Solr 4.4.";

		// Database conn = new Database();
		// Map<String,String> objects = conn.queryObjectsByApp("HPYW", "code");
		// conn.close();

		// System.out.println(objects);

		Map<String, String> keyWord = new HashMap<String, String>();
		System.out.println(tx);

		System.out.println(clearSingleLineComments(tx));

		System.out.println(clearAllComments(tx));

		// getKeyWordByQuotation(clearAllComments(tx),keyWord,objects);
		// System.out.println(keyWord);

		// getKeyWordByComplexRegex(clearAllComments(tx), keyWord,objects);

		// System.out.println(keyWord);

		// Pattern pattern =
		// Pattern.compile("([\\s+-/%\\*>=\\(,;]*)(\".*?\")([\\)\\s+-/%\\*>=,;]{1,})");
		/*
		 * StringBuffer sb = new StringBuffer(); String temp = null;
		 * 
		 * Pattern pattern = Pattern.compile("\\(([.\\S\\s]*)\\)"); Matcher
		 * matcher = pattern.matcher(
		 * "FIX ( A030306, \n&CurYear,\nV0299,\nS01,\nPJ00,\nSP2Biz00)\n xxxxxxx\n sssssss\n"
		 * );
		 * 
		 * while (matcher.find()){ temp = "("+matcher.group(1)+")"; temp =
		 * temp.replace("\n", ""); matcher.appendReplacement(sb, temp); }
		 * 
		 * matcher.appendTail(sb); System.out.println(sb.toString());
		 */

		clearEnterInFix("FIX ( A030306, \n&CurYear,\nV0299,\nS01,\nPJ00,\nSP2Biz00)\n xxxxxxx\n sssssss\n");

		// Pattern pattern2 =
		// Pattern.compile("([\\s+-/%\\*>=\\(,;]*)([{|&|a-zA-Z0-9|\"][.-_a-zA-Z0-9]*?[\"|}|a-zA-Z0-9])([\\)\\s+-/%\\*>=,;]{1,})");
		// Matcher matcher2 = pattern2.matcher(tx);

		// while (matcher2.find()){
		// matcher.appendReplacement(sbr, matcher.group(1) + "/*xxx*/" +
		// matcher.group(2)+"/*xxx*/" + matcher.group(3));
		// System.out.println(matcher2.group(2));
		// }

	}

		
	public static String clearEnterInFix(String str) {
		Pattern pattern = Pattern.compile("FIX\\s*\\(([\\S\\s]*?)\\)[\\s;]*\n");
		Matcher matcher = pattern.matcher(str);

		StringBuffer sb = new StringBuffer();
		String temp = null;

		while (matcher.find()) {
			temp = "FIX(" + matcher.group(1) + ")";
			temp = temp.replace("\n", "");
			matcher.appendReplacement(sb, temp + "\n");
		}

		matcher.appendTail(sb);
		// System.out.println(sb.toString());
		return sb.toString();

	}

	public static String clearSingleLineComments(String str) {
		String reg = "/\\*.*?\\*/";
		return str.replaceAll(reg, "");

	}
	
	
	public static String clearSingleLineNewComments(String str) {
		String reg = "/\\*{2,}.*?\\*/";
		return str.replaceAll(reg, "");

	}

	public static String clearAllComments(String str) {
		String reg = "/\\*[\\s\\S]*?\\*/";
		return str.replaceAll(reg, "");

	}

	public static String getKeyWordByComplexRegex(String str, Map<String, String> keyWord,
			Map<String, String> members) {
		// ([\\s+-/%\\*><=\\(,;]*)(.*?)([\\)\\s+-/%\\*><=,;]{1,})
		Pattern pattern = Pattern.compile("([\\s+\\-/%\\*><=\\(,;]*)([^,\"@()\\.\\s]+?)([\\)\\s+\\-/%\\*><=,;]{1,})");
		Matcher matcher = pattern.matcher(str);

		while (matcher.find()) {
			String key = matcher.group(2);
			String value;
			
			System.out.println("key=" + key);

			if (!systemKeyWord.containsKey(key.toUpperCase()) && !keyWord.containsKey(key)) {
				if (members.containsKey(key)) {
					value = key + "/**" + members.get(key) + "*/";
					keyWord.put(key, value);
				} 
				
			}
			// System.out.println(matcher.group(2));
		}

		return "";
	}

	public static String tranNameToCodeByQuotation(String str, Map<String, String> keyWord,
			Map<String, String> members) {

		Pattern pattern = Pattern.compile("(\".*?\")");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			String key = matcher.group(1);
			System.out.println("key=" + key);
			
			String key2 = key.replaceAll("\"", "");
			String value;
			// System.out.println(matcher.group(1));
			if (!keyWord.containsKey(key)) {
				if (members.containsKey(key2)) {
					value = "\"" + members.get(key2) + "\"";
					keyWord.put(key, value);
				} 
				
			}
		}

		return "";
	}

	public static String getKeyWordByQuotation(String str, Map<String, String> keyWord, Map<String, String> members) {
		// (\")(.*?)(\")
		Pattern pattern = Pattern.compile("(\".*?\")");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			String key = matcher.group(1);
			System.out.println("key=" + key);
			
			String key2 = key.replaceAll("\"", "");
			String value;
			// System.out.println(matcher.group(1));
			if (!keyWord.containsKey(key)) {
				if (members.containsKey(key2)) {
					value = key + "/**" + members.get(key2) + "*/";
				} else {
					value = key + "/**" + key2 + "*/";
				}

				keyWord.put(key, value);
			}
		}

		return "";
	}
}
