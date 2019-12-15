package com.yuexi1;
import java.awt.*;
import javax.swing.*;

public class ClassGUI extends JPanel{ 
	//把需要的swing组件创建在这里
	JButton jb1=null;
	public static void main(String args[]) {
	
		ClassGUI gui=new ClassGUI();
		
		
	}
	
	public ClassGUI() {

		JFrame jf=new JFrame();//顶层容器类
		jf.setLayout(new FlowLayout());
		jf.setResizable(false);//不可以改变大小
		jf.setPreferredSize(null);
		jf.setDefaultCloseOperation(3);//关闭窗口
		jf.setSize(500, 600);
		jf.setLocationRelativeTo(null);//相对于指定组件的位置
		
		//创建两个按钮
		JButton button1=new JButton("identify");
		JButton button2=new JButton("Save this sample");
		//设置按钮大小
		button1.setPreferredSize(new Dimension(100,30));
		button2.setPreferredSize(new Dimension(100,30));
		//可以识别的数字集
		String[] itemArray= {"0","1","2","3","4","5"};
		
		//创建一个下拉菜单
		JComboBox<String> cbItem = new JComboBox<String>(itemArray);
		
		
		this.setBackground(Color.black);
		this.setPreferredSize(new Dimension(400,400));
		
		//将组件加入到窗口中
		jf.add(button1);
		jf.add(button2);
		jf.add(cbItem);
		jf.add(this);
		jf.setVisible(true);
		
		//监听下拉菜单和画布
		Listener listener=new Listener(this,cbItem);
		
		//监听按钮点击
		button1.addActionListener(listener);
		button2.addActionListener(listener);
		
		//监听鼠标动作
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		
	}

}
