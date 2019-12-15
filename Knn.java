package com.yuexi1;
import java.util.HashMap;
public class Knn {

	private int nNeighbours;
	private KnnNode dist[];
	private int bucketLeft;

	public Knn(int n) {
		this.nNeighbours=n;//n个临近数字
		dist=new KnnNode[n];
		bucketLeft=n;//容量，下面存储数据作为数组下标的量

	}

	public double distanceCal(int sample[][],int pixel[][],int dimens) {
		int sum=0;
		for(int i=0;i<dimens;i++) {

			for(int j=0;j<dimens;j++) {

				int s1=sample[i][j];
				int s2=pixel[i][j];
				sum+=(s1-s2)*(s1-s2);//样本和手写输入比对，求欧拉距离

			}
		}
		return Math.sqrt(sum);

	}

	

	private int index=0;//遍历数组dist
	
//分类，找出最像的数字，并返回
	public void sort(int sample[][],int pixel[][],int dimens,String firstCharacter) {

		KnnNode temp=new KnnNode(distanceCal(sample,pixel,dimens),firstCharacter);

		//将距离和数字封装成一个类存储下来，便于比较，记录该次取出的样本与本次输入的比对值

		if(bucketLeft>0) {
			dist[index++]=temp;
			bucketLeft--;//将距离数据和数字存储下来
		}
		else{

			int flag=0;

			double max=0;

			for(int i=0;i<this.nNeighbours;i++) {

				if(dist[i].distance>max) {

					max=dist[i].distance;
					flag=i;//记录最大数下标
				}
			}
			if(max>temp.distance) dist[flag]=temp;
		}
		System.out.println(distanceCal(sample,pixel,dimens));

	}

	public String predict() {
		int max=0;
		String maxNumber="";
		HashMap<String, Integer> map=new HashMap<String,Integer>(); 
		// K 为首字母，V 为出现字数
		for(int i=0;i<this.nNeighbours;i++) {
			map.put(dist[i].firstCharacter, 0);
		}
//找出dist中出现次数最多的数字
		for(int i=0;i<this.nNeighbours;i++) {

			String tmp=dist[i].firstCharacter;
			int val=map.get(tmp)+1;

			if(val>max) {
				max=val;
				maxNumber=tmp;
			}
			map.put(tmp, val);
		}

//打印出来dist中的数据
		for(int i=0;i<this.nNeighbours;i++) {
			System.out.print(dist[i].distance+" "+dist[i].firstCharacter+" ");
		}
		return maxNumber;

	}

	

}

