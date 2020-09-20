import java.util.*;
import java.io.*;
class Table{
	String name;
	List<String> tablet=new ArrayList<>();
	public Table(String first,String t){
		name=first;
		tablet.add(t);
	}
}
public class PharmacyCounting{
	public static void main(String args[]) throws Exception{
		File file = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st = br.readLine();
		List<Table> customers=new ArrayList<>();
		Map<String,List<Integer>> m=new TreeMap<>();
		while ((st = br.readLine()) != null) {
			String split_st[]=st.split(",");
			boolean f=true,f1=true;
			for(Table i:customers){
				if(i.name.equals(split_st[1]+split_st[2])){
					if(i.tablet.contains(split_st[3])){
						m.get(split_st[3]).add(1,m.get(split_st[3]).get(1)+Integer.parseInt(split_st[4]));
						f1=false;
					}else{
						i.tablet.add(split_st[3]);
					}
					f=false;
					break;
				}
			}
			if(f1){
				List<Integer> d=new ArrayList<>();
				if(m.containsKey(split_st[3])){
					d.add(m.get(split_st[3]).get(0)+1);
					d.add(m.get(split_st[3]).get(1)+Integer.parseInt(split_st[4]));
				}else{
					d.add(1);
					d.add(Integer.parseInt(split_st[4]));
				}
				m.put(split_st[3],d);
			}
			if(f)
				customers.add(new Table(split_st[1]+split_st[2],split_st[3]));
		}
		Integer a[]=new Integer[m.size()];
		int k=0;
		for(Map.Entry<String,List<Integer>> i:m.entrySet()){
			a[k++]=i.getValue().get(1);
		}
		Arrays.sort(a);
		String s="drug_name,num_prescriber,total_cost\n";
		for(int j=k-1;j>=0;j--){
			for(Map.Entry<String,List<Integer>> i:m.entrySet()){
				if(!s.contains(i.getKey())&&a[j]==i.getValue().get(1)){
					s=s+i.getKey()+","+i.getValue().get(0)+","+i.getValue().get(1)+"\n";
				}
			}
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
		writer.write(s);
		writer.close();

	}
}
