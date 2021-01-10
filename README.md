# heaps-and-huffman-coding
Contains Java implementation of BinaryHeap, PairingHeap, FourWayHeap and Huffman encoding

First we check which heap implementation gives better performance, then we use it encode and decode given input file. 

##### INSTRUCTIONS TO EXECUTE: 

Project is written in Java using OpenJDK 11.0.9.1 

Execute make clean command to remove any existing class/output files 
```shell script
$ make clean 
```

Execute make command to compile all Java files 
```shell script
$ make  
```
Execute encoder program by providing input file name in the argument 
```shell script
$ java encoder <input_file_name> 
```

This will generate the output files "encoded.bin" and "code_table.txt".  

Execute the decoder program by providing encoded_file_name and code_table_file_name in the argument 
```shell script
$ java decoder <encoded_file_name> <code_table_file_name> 
```

To execute the Performance check class for heaps providing input file name in the argument 
```shell script
$ java PerformanceCheck <input_file_name> 
```
 
 Project report can be found [here](Report.pdf)
 
This project was done as part of the course [Advanced Data Structures](https://www.cise.ufl.edu/~sahni/cop5536/) at the University of Florida.
Thanks to [Prof. Sartaj Sahni](https://www.cise.ufl.edu/~sahni/) for the guidance.