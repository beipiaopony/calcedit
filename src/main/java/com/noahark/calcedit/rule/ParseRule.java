package com.noahark.calcedit.rule;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Stack;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.noahark.calcedit.analysis.AnalyzerMember;

public class ParseRule {

	private Stack<String> stack = new Stack<String>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ParseRule rule = new ParseRule();

		rule.readRuleFromXml("rule/ywPlan1.xml");

		// String tmp ="FIX ( A030306,
		// \n&CurYear,\n(V0299\n),\nS01,\nPJ00,\nSP2Biz00)
		// \n1xxxxxxx\n2sssssss\ndens(sfesdf\n);\n11";

		// System.out.println(AnalyzerMember.clearEnterInFix(tmp));

	}

	private void readRuleFromXml(String fileName) {
		//
		SAXReader reader = new SAXReader();
		try {

			Document document = reader.read(new File(fileName));
			Element root = document.getRootElement();

			listNodes(root);

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void listNodes(Element node) {

		// components
		Element coms = node.element("components");
		for (Iterator i = coms.elementIterator(); i.hasNext();) {
			Element element = (Element) i.next();
			Attribute att = element.attribute("type");
			Attribute attName = element.attribute("name");

			if (att != null) {
				String type = att.getStringValue();
				if (attName != null) {
					System.out.println(attName.getStringValue());
				}
				if (type.equals("script")) {
					Element script = element.element("script");
					if (script != null) {
						String tmp = AnalyzerMember
								.clearEnterInFix(AnalyzerMember.clearAllComments(script.getStringValue()));
						// String[] scr = tmp.split(regex)
						// System.out.println(tmp);
						parseRule(tmp);
					}
				}
			}
		}

		// components
		Element rules = node.element("rules");
		for (Iterator i = rules.elementIterator(); i.hasNext();) {
			Element element = (Element) i.next();
			//Attribute att = element.attribute("type");
			Attribute attName = element.attribute("name");

			if (attName != null) {
				System.out.println(attName.getStringValue());
			}
			
			Element script = element.element("script");
			if (script != null) {
				String tmp = AnalyzerMember
						.clearEnterInFix(AnalyzerMember.clearAllComments(script.getStringValue()));
				parseRule(tmp);
			}
			
		}

	}

	private void parseRule(String str) {
		String[] scr = str.split("\n");
		for (int i = 0; i < scr.length; i++) {

			if (scr[i] != null && scr[i].length() > 0) {
				String tmp = scr[i].toUpperCase();
				// System.out.println(scr[i]);

				if (tmp.indexOf("ENDFIX") != -1) {
					// System.out.println(scr[i]);

					stack.pop();

				} else if (tmp.indexOf("FIX") != -1) {
					// System.out.println(scr[i]);
					stack.push(scr[i]);

				} else if (tmp.indexOf("=") >= 0 && tmp.matches(" *S11 *=.*")) {

					printStack(stack);

					System.out.print(scr[i]);
					System.out.println();
				}
				// System.out.println(scr[i]);
			}

		}
	}

	private void printStack(Stack<String> stack) {
		if (stack.empty())
			System.out.println("堆栈是空的，没有元素");
		else {
			// System.out.print("堆栈中的元素：");
			Enumeration<String> items = stack.elements(); // 得到 stack 中的枚举对象
			while (items.hasMoreElements()) // 显示枚举（stack ） 中的所有元素
				System.out.print(items.nextElement() + " ");
		}
		// System.out.println(); //换行
	}

}
