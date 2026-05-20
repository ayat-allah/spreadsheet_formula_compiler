# spreadsheet formula language project summary

## How to run:

```bash
git clone https://github.com/ayat-allah/spreadsheet_formula_compiler.git
cd spreadsheet_formula_compiler
cd SpreadsheetCompiler
javac .\src\compiler\*.java
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
│   ├── docs/
│   │   ├── Compiler_Coversheet.docx
│   │   └── Report.pdf
│   ├── test_inputs/
│   │   ├── formulas.txt
│   │   └── formulas.docx
│   └── test_outputs/
│       ├── invalid/
│       │   ├── invalid_test_cases_output_1.png
│       │   └── invalid_test_cases_output_2.png
│       └── valid/
│           ├── valid_test_cases_output_1.png
│           ├── valid_test_cases_output_2.png
│           ├── valid_test_cases_output_3.png
│           ├── valid_test_cases_output_4.png
│           ├── valid_test_cases_output_5.png
│           ├── valid_test_cases_output_6.png
│           └── valid_test_cases_output_6_JSON.png
└── README.md
```

## walkthrough of project:

The Main class orchestrates the full pipeline: it reads input (hardcoded test cases or from a file via --file), invokes the Lexer which gets tokenized before sending it to Parser which then validates the format of the input and the ASTNode takes the parsed input, defines it's type and outputs it as both an AST tree and JSON object. A hasErrors() helper method checks and reports errors from both the Lexer and Parser stages with clear diagnostic labels.
