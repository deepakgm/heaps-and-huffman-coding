import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class PerformanceCheck {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("not enough arguments");
            System.exit(0);
        }
        String inputFile = args[0];

        Map<String, Integer> frequencyMap = new HashMap<>();
        try {
            FileReader reader = new FileReader(inputFile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line != null && !line.isEmpty()) {
                line = line.trim();
                frequencyMap.put(line, frequencyMap.getOrDefault(line, 0) + 1);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error while parsing input file!: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        long startTime, endTime;

        startTime = System.currentTimeMillis();
        buildTreeUsingBinaryHeap(frequencyMap);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken by binary heap: " + (endTime - startTime) + "ms");


        startTime = System.currentTimeMillis();
        buildTreeUsing4WayHeap(frequencyMap);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken by 4 way heap: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        buildTreeUsingPairingHeap(frequencyMap);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken by pairing heap: " + (endTime - startTime) + "ms");
    }

    public static Node buildTreeUsingBinaryHeap(Map<String, Integer> frequencyMap) {
        MinHeap<Node> minHeap = new BinaryMinHeap<>(frequencyMap.size());
        return buildHuffmanTree(frequencyMap, minHeap);
    }

    public static Node buildTreeUsing4WayHeap(Map<String, Integer> frequencyMap) {
        MinHeap<Node> minHeap = new FourWayMinHeap<>(frequencyMap.size());
        return buildHuffmanTree(frequencyMap, minHeap);
    }

    public static Node buildTreeUsingPairingHeap(Map<String, Integer> frequencyMap) {
        MinHeap<Node> minHeap = new PairingMinHeap<>();
        return buildHuffmanTree(frequencyMap, minHeap);
    }

    public static Node buildHuffmanTree(Map<String, Integer> frequencyMap, MinHeap<Node> minHeap) {
        for (String key : frequencyMap.keySet()) {
            minHeap.insert(new Node(key, frequencyMap.get(key)));
        }

        while (minHeap.getSize() >= 2) {
            Node node1 = minHeap.removeMin();
            Node node2 = minHeap.removeMin();
            Node node = new Node(node1, node2);
            minHeap.insert(node);
        }
        return minHeap.removeMin();
    }
}
