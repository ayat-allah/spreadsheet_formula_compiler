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
│   ├── formulas.txt #the one you run the project with
│   ├── formulas.docx #only for the form since it only accepts word / pdf format
│   ├── invalid test case output.png
│   └── valid test case output.png
└── README.md
```

## Step by step walkthrough of project:

The Main class orchestrates the full pipeline: it reads input (hardcoded test cases or from a file via --file), invokes the Lexer and Parser in sequence, and formats output as both an AST tree and JSON object. A hasErrors() helper method checks and reports errors from both stages with clear diagnostic labels.
