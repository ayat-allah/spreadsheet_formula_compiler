# spreadsheet_formula_compiler

#How to run:
git clone https://github.com/ayat-allah/spreadsheet_formula_compiler.git
cd spreadsheet_formula_compiler
cd SpreadsheetCompiler
javac .\src\compiler\Main.java
java -cp .\src compiler.Main --file .\formulas.txt

#Project structure:
spreadsheet_formula_compiler/
├── SpreadsheetCompiler/
│   └── .idea/
|   |___ src/compiler/
|        |___ Main.java
|        |___ Lexer.java
|        |___ Token.java
|        |___ Parser.java
|        |___ ASTNode.java
|   |___ Compiler_Coversheet.docx
|   |___ Report.pdf
|   |___ formulas.txt
|   |___ invalid test case output.png
|   |___ valid test case output.png
└── README.md

#Step by step walkthrough of project:
