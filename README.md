# spreadsheet_formula_compiler

## How to run:

```bash
git clone https://github.com/ayat-allah/spreadsheet_formula_compiler.git
cd spreadsheet_formula_compiler
cd SpreadsheetCompiler
javac .\src\compiler\Main.java
java -cp .\src compiler.Main --file .\formulas.txt
```

## Project structure:

```bash
spreadsheet_formula_compiler/
├── SpreadsheetCompiler/
│   ├── src/
│   │   └── compiler/
│   │       ├── Main.java
│   │       ├── Lexer.java
│   │       ├── Token.java
│   │       ├── Parser.java
│   │       └── ASTNode.java
│   ├── Compiler_Coversheet.docx
│   ├── Report.pdf
│   ├── formulas.txt
│   ├── invalid test case output.png
│   └── valid test case output.png
└── README.md
```

# Step by step walkthrough of project:
