package com.yuexi1;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.rmi.server.UID;

public class Listener extends MouseAdapter implements ActionListener  {
	private ClassGUI gui;
	private Graphics2D g;
	private int[][] pixel=new int[40][40];//手写输入数字的数组
	private JComboBox<String> cbItem;
	private Knn knn;//Knn算法
	public Listener(ClassGUI gui,JComboBox<String> cbItem) {
		this.gui=gui;
		this.cbItem=cbItem;
		g=(Graphics2D)gui.getGraphics();
	}
	private int[][] sample=new int[40][40];//样本数字的数组
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Save this sample")) {
			String selectedNumber=cbItem.getSelectedItem().toString();
			UID id=new UID();//生成一个对于主机来说独一无二的标识符
			String rootPath="C:\\Users\\liu_1\\eclipse-workspace\\111\\src\\com\\TrainingData\\";
			
			String fileName=selectedNumber+"-"+id.hashCode();//
			String absoluteFile=rootPath+fileName+".txt";
			File file=new File(absoluteFile);
			try {
				System.out.println("创建一个数字"+selectedNumber+"样本");
				if(!file.exists())
					file.createNewFile();
				FileWriter out = new FileWriter(file);
				for(int i=0;i<40;i++) {
					for(int j=0;j<40;j++) {
						out.write(pixel[i][j]+"");//将数据写入文件
					}
				}
				out.flush();
				out.close();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
			gui.repaint();
		}
		else if(e.getActionCommand().equals("identify")) {
			knn=new Knn(3);//设置取样3个临近数字
			File fileDir=new File("C:\\Users\\liu_1\\eclipse-workspace\\111\\src\\com\\TrainingData\\");

			String[] fileList=fileDir.list();
			//遍历样本文件夹
			for(int i=0;i<fileList.length;i++) {

				File file=new File("C:\\Users\\liu_1\\eclipse-workspace\\111\\src\\com\\TrainingData\\"+fileList[i]);

				String number=file.getName().substring(0, 1);//截取文件名下标从0开始到1的内容，获取该文件表示的数字
				FileReader in;

				try {
					in = new FileReader(file);
					for(int j=0;j<40;j++){

						for(int k=0;k<40;k++) {
							sample[j][k]=in.read()-'0';  //逐单位数字读取
						}

					}
					in.close();

				} catch (FileNotFoundException e2) {

					// TODO Auto-generated catch block

					e2.printStackTrace();

				} catch (IOException e1) {

                    e1.printStackTrace();

                }

			//开始进行 欧拉距离和KNN 运算；

				knn.sort(sample, pixel, 40, number);

			}
			JOptionPane.showMessageDialog(gui,"预测数字为："+knn.predict());
			gui.repaint();
		}
	}
	

	private int  x1,y1,x2, y2;

	public void pixelReset() {

		for (int i = 0; i < 40; i++) {

			for (int j = 0; j < 40; j++) {
				pixel[i][j] = 0;
			}
		}
	}


//每当鼠标重新点击的时候，刷新pixel中储存的数据点
	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		
		pixelReset();
	}



	public void mouseDragged(MouseEvent e) {
		g.setColor(Color.WHITE);//画出图形
		x2 = e.getX();
		y2 = e.getY();
		
		g.fillRect(x2, y2, 20, 20);//用指定画刷填充矩形
		//将画出来的痕迹用1来代替0，将痕迹记录下来
		for (int i = x2; i < x2 + 20; i++) {
			for (int j = y2; j < y2 + 20; j++) {
					pixel[i/10][j/10] = 1;
			}
		}
	}

}
	
	
	


