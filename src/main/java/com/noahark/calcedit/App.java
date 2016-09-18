package com.noahark.calcedit;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.noahark.calcedit.analysis.AnalyzerMember;
import com.noahark.calcedit.db.HspObject;
import com.noahark.calcedit.file.ReadFile;

/**
 * Hello world!
 *
 */
public class App extends JFrame implements ActionListener {

	private JLabel jLabel, jLabel2, jLabel3, jLabel4;
	private JTextField jTextField;
	private JButton jButton, jButton1, jButton2, jButton3, jButton4;
	private JTextArea jtextarea;
	private JScrollPane sp;
	private JComboBox<String> jcomBox;

	// private Map<String,File> dimFiles = new HashMap<String,File>();

	private Map<String, String> codeObj = new HashMap<String, String>();
	private Map<String, String> nameObj = new HashMap<String, String>();

	public App() {
		super();
		this.setSize(1024, 768);
		setLayout(new BorderLayout(5, 5));
		JPanel jp = new JPanel();

		jp.setLayout(new GridLayout(1, 10));

		// jp.set
		jp.add(getJLabel3());
		// jp.add(getJComboBox());
		// jp.add(getJLabel2());
		//jp.add(new JLabel());
		//jp.add(getJButtonzero());
		
		//jp.add(new JLabel());
		jp.add(getJButtoncancel());
		jp.add(new JLabel());
		jp.add(getJButton4());

		jp.add(getJButtonok());
		jp.add(new JLabel());

		jp.add(getJButton3());

		getContentPane().add(jp, "North");
		getContentPane().add(getJTextArea(), "Center");

		// ImageLabel label = new ImageLabel(new
		// ImageIcon("images/reactor.png"));
		this.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 18));
		this.setTitle("小马哥");
		
		/*
		Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/img/yn.png"));
			this.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		

	}

		
	private JComboBox<String> getJComboBox() {
		if (jcomBox == null) {
			jcomBox = new JComboBox<String>();
			jcomBox.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 18));

		}
		return jcomBox;
	}

	private JScrollPane getJTextArea() {
		if (jtextarea == null) {
			jtextarea = new JTextArea();
			jtextarea.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 16));
			// jtextarea.setBounds(5, 45, 650, 400);
		}
		// jtextarea.setLineWrap(true);
		sp = new JScrollPane(jtextarea);
		// sp.set
		// sp.setBounds(5, 45, 1000, 650);
		// sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return sp;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			// jLabel3.setBounds(10, 10, 150, 30);
			// jLabel3.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 16));
			jLabel3.setText("wangmh@noahark.com");

			/*
			BufferedImage image = null;

			try {
				image = ImageIO.read(this.getClass().getResource("/img/yn.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jLabel3.setIcon(new ImageIcon(image));
			*/

		}

		return jLabel3;
	}

	private JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new javax.swing.JLabel();
			// jLabel.setBounds(10, 10, 150, 30);
			jLabel.setText("                        ");
		}

		return jLabel;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			// jLabel.setBounds(10, 10, 150, 30);
			jLabel4.setText("                        ");
		}

		return jLabel4;
	}

	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new javax.swing.JTextField();
			// jTextField.setBounds(150, 10, 160, 30);
		}
		return jTextField;
	}

	private JButton getJButtonok() {
		if (jButton == null) {
			jButton = new javax.swing.JButton();
			// jButton.setBounds(400, 10, 100, 30);
			jButton.setText("编码补充别名");
			jButton.setActionCommand("fillNameByCode");
			jButton.addActionListener(this);
		}
		return jButton;
	}

	private JButton getJButtoncancel() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			// jButton1.setBounds(530, 10, 100, 30);
			jButton1.setText("清除所有单行注释");
			jButton1.setActionCommand("clearSingleLineComments");
			jButton1.addActionListener(this);
		}
		return jButton1;
	}
	
	
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			// jButton1.setBounds(530, 10, 100, 30);
			jButton4.setText("清除别名注释**");
			jButton4.setActionCommand("clearSingleLineNewComments");
			jButton4.addActionListener(this);
		}
		return jButton4;
	}

	private JButton getJButtonzero() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			// jButton2.setBounds(310, 10, 80, 30);
			jButton2.setText("清除");
			jButton2.setActionCommand("clearAreaText");
			jButton2.addActionListener(this);
		}
		return jButton2;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			// jButton3.setBounds(310, 10, 80, 30);
			jButton3.setText("名称转编码");
			jButton3.setActionCommand("nameToCode");

			jButton3.addActionListener(this);
		}
		return jButton3;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		InputStream in = null;

		Properties prop = new Properties();

		try {
			in = new BufferedInputStream(new FileInputStream("cfg.properties"));
			prop.load(in); /// 加载属性列表

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
		ReadFile readFile = new ReadFile();

		App w = new App();

		// read files
		File[] fs = readFile.getFiles("data");

		for (int i = 0; i < fs.length; i++) {
			// String key = fs[i].getName().replaceAll("[.][^.]+$", "");
			try {
				List<HspObject> list = readFile.readFile(fs[i], prop);
				w.initMap(list);				
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		
	
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//open form
		w.setVisible(true);

	}

	public void initMap(List<HspObject> list){
		for (int i = 0;i < list.size();i++){
			HspObject obj = list.get(i);
			codeObj.put(obj.getObjectCode(), obj.getObjectName());
			nameObj.put(obj.getObjectName(), obj.getObjectCode());
			
		}
	}
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		System.out.println("ActionCommand=" + source);

		// 转换1
		if (source.equals("fillNameByCode")) {

			String tmp = jtextarea.getText();

			if (tmp != null && tmp.length() > 0) {

				// Database conn = new Database();
				// Map<String, String> objects = conn.queryObjectsByApp("HPYW",
				// "code");
				// conn.close();

				Map<String, String> keyWord = new HashMap<String, String>();

				String clean = AnalyzerMember.clearSingleLineNewComments(tmp);

				String clean2 = AnalyzerMember.clearAllComments(clean);

				AnalyzerMember.getKeyWordByQuotation(clean2, keyWord, codeObj);
				AnalyzerMember.getKeyWordByComplexRegex(clean2, keyWord, codeObj);

				// System.out.println(keyWord);

				for (Entry<String, String> entryset : keyWord.entrySet()) {
					StringBuffer sbr = new StringBuffer();
					String key = entryset.getKey().replaceAll("\\{", "\\\\{").replaceAll("\\.", "\\\\.");
					String value = entryset.getValue();
					
					System.out.println("key=" + key + ", value=" + value);
					
					
					Pattern pattern = Pattern.compile("([\\s+\\-/%\\*>=\\(,;]*)(" + key + ")([\\)\\s+\\-/%\\*>=,;]{1,})");
					//                                "([\\s+\\-/%\\*><=\\(,;]*)([^,\"@()\\.\\s]+?)([\\)\\s+\\-/%\\*><=,;]{1,})"
					Matcher matcher = pattern.matcher(clean);

					while (matcher.find()) {
						System.out.println("found " + key);;
						matcher.appendReplacement(sbr, matcher.group(1) + value + matcher.group(3));
					}

					matcher.appendTail(sbr);

					clean = sbr.toString();					
				
				}

				//System.out.println("data = " + clean);
				jtextarea.setText(clean);
				
			}

		}

		// 清除注释
		if (source.equals("clearSingleLineComments")) {

			// System.out.println("你按了按钮stop");
			String tmp = jtextarea.getText();
			if (tmp != null && tmp.length() > 0) {
				String clean = AnalyzerMember.clearSingleLineComments(tmp);
				jtextarea.setText(clean);
			}

		}

		if (source.equals("clearSingleLineNewComments")){
			
			String tmp = jtextarea.getText();
			if (tmp != null && tmp.length() > 0) {
				String clean = AnalyzerMember.clearSingleLineNewComments(tmp);
				jtextarea.setText(clean);
			}
		}
		// 清除文本
		if (source.equals("clearAreaText")) {
			jtextarea.setText("");
		}

		// 名称转编码
		if (source.equals("nameToCode")) {
			String tmp = jtextarea.getText();
			if (tmp != null && tmp.length() > 0) {
				Map<String, String> keyWord = new HashMap<String, String>();
				AnalyzerMember.tranNameToCodeByQuotation(tmp, keyWord, nameObj);

				for (Entry<String, String> entryset : keyWord.entrySet()) {
					StringBuffer sbr = new StringBuffer();
					String key = entryset.getKey().replaceAll("\\{", "\\\\{").replaceAll("\\.", "\\\\.");
					String value = entryset.getValue();
					Pattern pattern = Pattern.compile("([\\s+-/%\\*>=\\(,;]*)(" + key + ")([\\)\\s+-/%\\*>=,;]{1,})");
					Matcher matcher = pattern.matcher(tmp);

					System.out.println("key=" + key + ", value=" + value);
					
					while (matcher.find()) {
						matcher.appendReplacement(sbr, matcher.group(1) + value + matcher.group(3));
					}

					matcher.appendTail(sbr);

					tmp = sbr.toString();
				}

				// System.out.println("data = " + clean);
				jtextarea.setText(tmp);

			}
		}

	}
}
