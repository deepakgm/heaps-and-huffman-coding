import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class PerformanceCheck {
    public static void main(String[] args) {
        if(args.length<1){
            System.out.println("not enough arguments");
            System.exit(0);
        }
        String inputFile = args[0];

        Map<String,Integer> map= new HashMap<>();
        try {
            FileReader reader=new FileReader(inputFile);
            BufferedReader bufferedReader=new BufferedReader(reader);
            String line= bufferedReader.readLine();
            while(line!=null && !line.isEmpty()){
                line=line.trim();
                map.put(line,map.getOrDefault(line,0)+1);
                line= bufferedReader.readLine();
            }
        }catch (Exception e){
            System.out.println("Error while parsing input file!: "+e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        long startTime,endTime;

        startTime = System.currentTimeMillis();
        buildTreeUsingBinaryHeap(map);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken by binary heap: "+(endTime-startTime));


        startTime = System.currentTimeMillis();
        buildTreeUsing4WayHeap(map);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken by 4 way heap: "+(endTime-startTime));

        startTime = System.currentTimeMillis();
        buildTreeUsingPairingHeap(map);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken by pairing heap: "+(endTime-startTime));
    }

    public static Node buildTreeUsingBinaryHeap(Map<String,Integer> map){
        MinHeap<Node> minHeap=new BinaryMinHeap<>(map.size());
        return buildHuffmanTree(map,minHeap);
    }

    public static Node buildTreeUsing4WayHeap(Map<String,Integer> map){
        MinHeap<Node> minHeap=new FourWayMinHeap<>(map.size());
        return buildHuffmanTree(map,minHeap);
    }

    public static Node buildTreeUsingPairingHeap(Map<String,Integer> map){
        MinHeap<Node> minHeap=new PairingMinHeap<>();
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
