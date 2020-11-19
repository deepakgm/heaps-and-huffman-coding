import java.io.*;
import java.util.*;

public class encoder {
    public static final String encodedFileName = "encoded.bin";
    public static final String codeTableFileName = "code_table.txt";

    public static void main(String[] args) {
        if(args.length<1){
            System.out.println("not enough arguments");
            System.exit(0);
        }
        String inputFile=args[0];

        Map<String,Integer> frequencyMap= new HashMap<>();
        try {
            FileReader reader=new FileReader(inputFile);
            BufferedReader bufferedReader=new BufferedReader(reader);
            String line= bufferedReader.readLine();
            while(line!=null && !line.isEmpty()){
                line=line.trim();
                frequencyMap.put(line,frequencyMap.getOrDefault(line,0)+1);
                line= bufferedReader.readLine();
            }
        }catch (Exception e){
            System.out.println("Error while processing input file!: "+e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("building huffman tree..");
        Node root = buildTreeUsing4WayHeap(frequencyMap);

        Map<String,String> codeTableMap = new HashMap<>();
        buildCodeTable(root,codeTableMap,"");
        StringBuilder sb=new StringBuilder();

        System.out.println("encoding input file..");
        try {
            FileReader reader=new FileReader(inputFile);
            BufferedReader bufferedReader=new BufferedReader(reader);
            String line= bufferedReader.readLine();
            while(line!=null && !line.isEmpty()){
                line=line.trim();
                sb.append(codeTableMap.get(line));
                line= bufferedReader.readLine();
            }
        }catch (Exception e){
            System.out.println("Error while reading input file!: "+e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("writing to output files..");
        writeToBinaryFile(sb);
        writeCodeTableToFile(codeTableMap);
        System.out.println("huffman encoding finished successfully!");
    }

    public static void writeToBinaryFile(StringBuilder sb) {
        BitSet bitSet=new BitSet();
        for(int i=0;i<sb.length();i++) {
            if(sb.charAt(i)=='1')
                bitSet.set(i);
        }
        if(!bitSet.get(sb.length()-1))
            bitSet.set(sb.length());

        byte[] byteArray= bitSet.toByteArray();

        if(!bitSet.get(sb.length()-1) && byteArray[sb.length()/8]==1)
            byteArray=Arrays.copyOf(byteArray, byteArray.length-1);

        try {
            File file = new File(encodedFileName);
            FileOutputStream outputStream=new FileOutputStream(file);
            outputStream.write(byteArray);
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Error while writing encoded byte array to binary file: "+e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void writeCodeTableToFile(Map<String,String> codeTable) {
        try {
            File file=new File(codeTableFileName);
            FileWriter fileWriter=new FileWriter(file);
            for(String key:codeTable.keySet()){
                fileWriter.write(String.format("%s %s\n",key,codeTable.get(key)));
            }
            fileWriter.close();
        }catch (Exception e){
            System.out.println("Error while writing code-table to output file: "+e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void buildCodeTable(Node root,Map<String,String> codeTableMap, String code) {
        if (root.getLeft()==null) {
            codeTableMap.put(root.getValue(),code);
        }else {
            buildCodeTable(root.getLeft(),codeTableMap,code+"0");
            buildCodeTable(root.getRight(),codeTableMap,code+"1");
        }
    }

    public static Node buildTreeUsing4WayHeap(Map<String,Integer> map){
        MinHeap<Node> minHeap=new FourWayMinHeap<>(map.size());
        return buildHuffmanTree(map,minHeap);
    }

    public static Node buildHuffmanTree(Map<String,Integer> map,MinHeap<Node> minHeap){
        for(String key:map.keySet()){
            minHeap.insert(new Node(key,map.get(key)));
        }

        while(minHeap.getSize()>=2){
            Node node1=minHeap.getMin();
            Node node2=minHeap.getMin();
            Node node = new Node("",(node1.getFrequency()+node2.getFrequency()),node1,node2);
            minHeap.insert(node);
        }
        return minHeap.getMin();
    }
}
