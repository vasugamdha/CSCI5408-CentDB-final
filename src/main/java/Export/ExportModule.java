package Export;

import java.io.*;
import java.util.*;

public class ExportModule {
	public void ExportWithoutValues(String name) throws IOException 
	{
		File file_dir = new File(".//src//main//java//Collect_Data//"+name);
		String dump_path=".//src//main//java//Collect_Data//SQLdump//"+name+"_No_value_"+System.nanoTime()+".sql";
		FileWriter fw = new FileWriter(dump_path);

		if(file_dir.exists()) {
			File[] list_of_files = file_dir.listFiles();
			for (int i=0; i < list_of_files.length; i++) {
				String filename=list_of_files[i].getName();
				if(filename.contains("-meta_data_file"))
				{
					try (BufferedReader br = new BufferedReader(new FileReader(".//src//main//java//Collect_Data//"+name+"//"+filename)))
					{		        
						String str;
						List<String> tmp = new ArrayList<String>();
						br.readLine();
						while ((str = br.readLine()) != null) {
							tmp.add(str);
						}
						String createQ="Create table "+filename.split("-")[0]+"(";
						for(int j=tmp.size()-1;j>=0;j--) {
							String cline=tmp.get(j);
							String[] Arr_line = cline.split("\\#");
							createQ+=Arr_line[1]+" "+Arr_line[2]+",";
						}
						createQ=createQ.substring(0,createQ.length()-1)+");\n";
						fw.write(createQ);
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}							
				}
			}
		}
		fw.close();
	}
	public void ExportWithValues(String name) throws IOException 
	{
		File file_dir = new File(".//src//main//java//Collect_Data//"+name);
		String dump_path=".//src//main//java//Collect_Data//SQLdump//"+name+"_with_value_"+System.nanoTime()+".sql";
		FileWriter fw = new FileWriter(dump_path);
		
		if(file_dir.exists()) {
			File[] list_of_files = file_dir.listFiles();
			for (int i=0; i < list_of_files.length; i++) {
				String filename=list_of_files[i].getName();
				Map<String, String> fieldDataType= new HashMap<>();

				if(filename.contains("-meta_data_file"))
				{
					try (BufferedReader br = new BufferedReader(new FileReader(".//src//main//java//Collect_Data//"+name+"//"+filename)))
					{		    
						String str;
						List<String> tmp = new ArrayList<String>();
						br.readLine();
						while ((str = br.readLine()) != null) {
							String[] Arr_line = str.split("\\#");
							tmp.add(str);
							fieldDataType.put(Arr_line[1],Arr_line[2]);
						}
						String createQ="Create table "+filename.split("-")[0]+"(";

						for(int j=tmp.size()-1;j>=0;j--) {
							String cline=tmp.get(j);
							String[] Arr_line = cline.split("\\#");
							createQ+=Arr_line[1]+" "+Arr_line[2]+",";
						}
						createQ=createQ.substring(0,createQ.length()-1)+");\n";
						fw.write(createQ);
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}							
					String insertfilePath=".//src//main//java//Collect_Data//"+name+"//"+filename.split("-")[0]+".txt";
					try (BufferedReader br = new BufferedReader(new FileReader(insertfilePath))) 
					{		       
						String cline=br.readLine();
						String[] cline_arr=cline.replaceAll("\\#", ",").split(",");
						String cline_rev="";
						for(int k=cline_arr.length-1;k>=0;k--)
							cline_rev+=cline_arr[k]+",";	
						cline_rev=cline_rev.substring(0,cline_rev.length()-1);
						while ((cline = br.readLine()) != null) 
						{	
							String[] cline_arr2=cline.split("\\#");
							String insertQ="insert into "+filename.split("-")[0]+"("+cline_rev+") values (";							
							for(int n=cline_arr2.length-1;n>=0;n--)
							{
								String current_field_type=fieldDataType.get(cline_arr[n]);
								if(current_field_type.equalsIgnoreCase("int") 
										|| current_field_type.equalsIgnoreCase("decimal") || current_field_type.equalsIgnoreCase("float") || current_field_type.equalsIgnoreCase("double")
										|| current_field_type.equalsIgnoreCase("boolean"))
									insertQ+=cline_arr2[n].replaceAll("'", "")+",";
								else if(current_field_type.equalsIgnoreCase("varchar") || current_field_type.equalsIgnoreCase("date")
										|| current_field_type.equalsIgnoreCase("datetime") || current_field_type.equalsIgnoreCase("text") || current_field_type.equalsIgnoreCase("longtext")
										|| current_field_type.equalsIgnoreCase("time") || current_field_type.equalsIgnoreCase("timestamp")) 
									insertQ+=""+cline_arr2[n]+",";
							}
							insertQ=insertQ.substring(0,insertQ.length()-1)+");\n";
							fw.write(insertQ);
						}						
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}

			}
		}
		fw.close();
	}
}