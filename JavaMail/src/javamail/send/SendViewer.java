package javamail.send;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SendViewer extends JFrame{
	
	public SendViewer(){
		
		setLayout(new GridLayout(6, 2, 5, 5));
		
		init();
		
		setTitle("sendEmail");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void init(){
		final JTextField[] jtf = new JTextField[]{
			new JTextField(), new JPasswordField(), new JTextField(),
			new JTextField(), new JTextField(),
		};
		String[] showStr = new String[]{
			"发送者邮箱:", "密码:", "接收者邮箱:", "邮件标题:", "邮件正文:"
		};
		final String[] saveTo = new String[]{
				"", "", "", "", "", "", ""
		};
		for(int i=0; i<5; i++){
			add(new JLabel(showStr[i]));
			add(jtf[i]);
		}
		final JButton jb1 = new JButton("添加附件");
		final JButton jb2 = new JButton("发送邮件");
		add(jb1);
		add(jb2);
		
		jb1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
			     fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			     int returnVal = fileChooser.showOpenDialog(fileChooser);
			     if(returnVal == JFileChooser.APPROVE_OPTION){       
			    	 saveTo[5] = fileChooser.getSelectedFile().getAbsolutePath();
	             } 	
			     if(saveTo[5].equals(""))
			    	 jb1.setText("未选择文件！");
			     else 
			    	 jb1.setText("已选择文件");
			}
		});
		
		jb2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean x = true;
				for(int i=0; i<5; i++){
					saveTo[i] = jtf[i].getText();
					if(saveTo[i].equals("")){
						x = false;
						jb2.setText("请输入完整信息！！！");
					}
				}
				for(int i=0; i<7; i++)
					System.out.println(saveTo[i]);
				if(x){
					jb2.setText("邮件发送中...");
					send(saveTo);
					jb2.setText("邮件发送成功！！！");
				}
			}
		});
	}
	public void send(String[] info){
		Sender cn = new Sender();
		//设置发件人地址、收件人地址和邮件标题
		cn.setAddress(info[0], info[2],
				info[3], info[4]+ "\nSendtime:"+getTime());
		//设置要发送附件的位置和标题
		cn.setAffix(info[5], "这是附件");
		//设置smtp服务器以及邮箱的帐号和密码
		cn.send("smtp.qq.com",info[0], info[1]);
	}

	private static String getTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return(df.format(new Date()));// new Date()为获取当前系统时间
	}
}
