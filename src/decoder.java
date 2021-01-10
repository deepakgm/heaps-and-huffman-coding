import java.io.*;
import java.util.BitSet;

public class decoder {
    public static final String decodedFileName = "decoded.txt";

    public static void main(String[] args) {
        if(args.length<2){
            System.out.println("not enough arguments");
            System.exit(0);
        }
        String encodedFile = args[0];
        String codeTableFile = args[1];

        System.out.println("reading coding table..");
        Node huffmanTreeRoot = buildHuffmanTree(codeTableFile);

        System.out.println("reading binary file..");
        byte[] encodedByteArray = readEncodedFile(encodedFile);

        System.out.println("decoding and writing to output file..");
        decodeAndWriteToFile(encodedByteArray, huffmanTreeRoot);

        System.out.println("huffman decoding completed successfully!");
    }

    public static void decodeAndWriteToFile(byte[] byteArray, Node root) {
        try {
            File file = new File(decodedFileName);
            FileWriter writer = new FileWriter(file);

            BitSet bitSet = BitSet.valueOf(byteArray);
            Node currentNode = root;
            for (int bitIndex = 0; bitIndex < (byteArray.length * 8); bitIndex++) {
                if (currentNode == null)//not expected to enter this if condition
                    currentNode = root;

                if (bitSet.get(bitIndex)) {
                    currentNode = currentNode.getRight();
                } else {
                    currentNode = currentNode.getLeft();
                }
                if (currentNode.getValue() != null && !currentNode.getValue().isBlank()) {
                    writer.write(String.format("%s\n", currentNode.getValue()));
                    currentNode = root;
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while writing decoded values to output file:" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static byte[] readEncodedFile(String encodedFile) {
        File file = new File(encodedFile);
        byte[] byteArray = new byte[(int) file.length()];
        try {
            InputStream inputStream = new FileInputStream(file);
            if (inputStream.read(byteArray) == -1) {
                System.out.println("Error while reading encoded binary file!: unexpected EOF");
            }
            inputStream.close();
        } catch (Exception e) {
            System.out.println("Error while reading encoded binary file!: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        return byteArray;
    }

    public static Node buildHuffmanTree(String codeTableFile) {
        Node root = new Node();
        try {
            FileReader reader = new FileReader(codeTableFile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line != null && !line.isEmpty()) {
                String[] splitResult = line.split(" ");
                Node leaf = root;
                for (char c : splitResult[1].toCharArray()) {
                    if (c == '1') {
                        if (leaf.getRight() == null) {
                            leaf.setRight(new Node());
                        }
                        leaf = leaf.getRight();
                    } else {
                        if (leaf.getLeft() == null) {
                            leaf.setLeft(new Node());
                        }
                        leaf = leaf.getLeft();
                    }
                }
                leaf.setValue(splitResult[0]);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            reader.close();
        } catch (Exception e) {
            System.out.println("Error while reading code-table file!: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        return root;
    }
}
