# word-frequency
This program is designed to accept a file or list of files and return the 100 most common 3-word combinations within the text.

## Running The program
The entry point class can be found at word-frequency>src>main>WordFrequency
   - You can execute the program within IntelliJ by right clicking on the class and selecting "Run"
   - Alternatively you can execute the program by entering the project folder in your terminal and executing the following commands:
     - first to compile: `javac src/main/WordFrequency.java`
     - then to execute `java src/main/WordFrequency.java`

## The Rules for the program are as follows:
1. The program accepts as arguments a list of one or more file paths (e.g. ./solution.rb file1.txt file2.txt ...). 
   - You must either provide the absolute path of the file in the given Operating System, or add the file to the textFiles directory in Word-frequency>src>textFiles
at which point you can simply reference the name of the file.
   - The file list you provide must be separated by a space. This could cause a problem with files that have a space in their name.
2. The program outputs a list of the 100 most common three word sequences in the text (per file), along with a count of how many times each occurred in the text in ascending order. For example: “File1.txt” 231 - i will not, 116 - i do not, 105 - there is no, 54 - i know not, 37 - i am not … 
   - If there are less than 100 3-word combinations in the file it will simply provide all of them.
3. The program ignores punctuation, line endings, and is case-insensitive (e.g. “I love\nsandwiches.” should be treated the same as "(I LOVE SANDWICHES!!)"). 
4. Unique files should be kept distinct (don’t cross file boundaries). 
5. The program should be able to support more than just English. 
6. The program is capable of processing large files and runs as fast as possible. 
7. The program should be tested. Provide a test file for your solution. 
8. The program should have detailed instructions that will guide someone new to coding on how to execute the program.


