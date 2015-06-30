package jp.levelfive.komori.experiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class Main {

	public static void main(String[] args) {
		int maxIndex = 0;
		List<List<String>> list = new ArrayList<>();
		try {
            FileReader fileReader = new FileReader("data.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            StringTokenizer token;
            while ((line = bufferedReader.readLine()) != null) {
                token = new StringTokenizer(line, ",");
                List<String> array = new ArrayList<>();
            	int index=0;
                while(token.hasMoreTokens()){
                	String str = token.nextToken();
                	array.add(str);

                	if(maxIndex<index-2){
                		maxIndex++;
                	}
                }
                list.add(array);
            }
            bufferedReader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		List<SampleDataList> dataList = new ArrayList<>();
		for(int index=0; index<maxIndex; index++){
			dataList.add(new SampleDataList());
		}
		for(List<String> array:list){
			for(int i=1;i<array.size() -1;i++){
				SampleData data = new SampleData();
				data.setSample(Integer.parseInt(array.get(i)));
				data.setResult(Integer.parseInt(array.get(array.size()-1)));
				dataList.get(i-1).add(data);
			}
		}
	}


}

class SampleData{
	private int sample;
	private int result;

	int getSample(){
		return sample;
	}
	int getResulet(){
		return result;
	}

	void setSample(int sample){
		this.sample=sample;
	}
	void setResult(int result){
		this.result=result;
	}
}

class SampleDataList{
	private List<SampleData> dataList = new ArrayList<>();
	private List<Double> errorList = new ArrayList<>();
	SimpleRegression regression = new SimpleRegression();

	void add(SampleData data){
		dataList.add(data);
	}

	List<Double> getErrorList(){
		for(SampleData data:dataList){
			regression.addData(data.getSample(), data.getResulet());
		}
		double slope = regression.getSlope();
		double intercept = regression.getIntercept();

		for(SampleData data:dataList){
			double error = slope * data.getSample() + intercept;
			errorList.add(error);
		}
		return errorList;
	}
}