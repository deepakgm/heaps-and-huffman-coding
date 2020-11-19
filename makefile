JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java

CLASSES = BinaryMinHeap.java decoder.java encoder.java FourWayMinHeap.java MinHeap.java Node.java PairingHeapNode.java PairingMinHeap.java PerformanceCheck.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class encoded.bin code_table.txt decoded.txt