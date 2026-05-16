package compiler;

import java.util.ArrayList;
import java.util.List;

abstract class ASTNode {
    abstract String toJson(int indent);
    abstract List<String> toASTTree();

    protected String indent(int level) {
        return "  ".repeat(level);
    }

    void print(int indent) {
        for (String line : toASTTree()) {
            System.out.println(indent(indent) + line);
        }
    }
}

class NumberNode extends ASTNode {
    int value;

    NumberNode(String v) { value = Integer.parseInt(v); }

    @Override
    String toJson(int indent) {
        return "{ \"type\": \"Number\", \"value\": " + value + " }";
    }

    @Override
    List<String> toASTTree() {
        return List.of(String.valueOf(value));
    }
}

class CellNode extends ASTNode {
    String name;

    CellNode(String n) { name = n; }

    @Override
    String toJson(int indent) {
        return "{ \"type\": \"Cell\", \"name\": \"" + name + "\" }";
    }

    @Override
    List<String> toASTTree() {
        return List.of(String.valueOf(name));
    }
}

class RangeNode extends ASTNode {
    String start;
    String end;

    RangeNode(String start, String end) {
        this.start = start;
        this.end = end;
    }

    void print(int i) {
        System.out.println(indent(i) + "Range: " + start + ":" + end);
    }

    @Override
    String toJson(int indent) {
        return "{ \"type\": \"Range\", \"start\": \"" + start + "\", \"end\": \"" + end + "\" }";
    }

    @Override
    List<String> toASTTree() {
        List<String> result = new ArrayList<>();
        result.add("     :");
        result.add("   /   \\");
        result.add(" "+ start + "     " + end);
        return result;
    }
}

class BinaryNode extends ASTNode {
    String op;
    ASTNode left, right;

    BinaryNode(String o, ASTNode l, ASTNode r) {
        op = o; left = l; right = r;
    }

    @Override
    String toJson(int indent) {
        String childIndent = indent(indent + 1);
        String baseIndent = indent(indent);
        return "{\n" +
                childIndent + "\"type\": \"Binary\",\n" +
                childIndent + "\"op\": \"" + op + "\",\n" +
                childIndent + "\"left\": " + left.toJson(indent + 1) + ",\n" +
                childIndent + "\"right\": " + right.toJson(indent + 1) + "\n" +
                baseIndent + "}";
    }

    @Override
    List<String> toASTTree() {
        List<String> leftLines = left.toASTTree();
        List<String> rightLines = right.toASTTree();

        int lengthOfLeftSubtree = leftLines.get(0).length();
        int lengthOfRightSubtree = rightLines.get(0).length();

        int totalWidth = lengthOfLeftSubtree + lengthOfRightSubtree + 1; //total indents
        int opPos = lengthOfLeftSubtree;   // position where operator sits

        String opLine = " ".repeat(opPos) + op + " ".repeat(totalWidth - opPos - 1);

        //node connection
        String connector = " ".repeat(lengthOfLeftSubtree - 1) + "/" +
                           " ".repeat(totalWidth - lengthOfLeftSubtree - lengthOfRightSubtree) + "\\" +
                           " ".repeat(lengthOfRightSubtree - 1);

        List<String> result = new ArrayList<>();
        result.add(opLine);
        result.add(connector);

        int maxHeight = Math.max(leftLines.size(), rightLines.size());
        for (int i = 0; i < maxHeight; i++) {
            String leftPart = i < leftLines.size() ? leftLines.get(i) : " ".repeat(lengthOfLeftSubtree);
            String rightPart = i < rightLines.size() ? rightLines.get(i) : " ".repeat(lengthOfRightSubtree);
            result.add(leftPart + " ".repeat(totalWidth - lengthOfLeftSubtree - lengthOfRightSubtree) + rightPart);
        }
        return result;
    }
}

class FuncNode extends ASTNode {
    String name;
    List<ASTNode> args;

    FuncNode(String n, List<ASTNode> a) {
        name = n; args = a;
    }

    @Override
    String toJson(int indent) {
        String childIndent = indent(indent + 1);
        String baseIndent = indent(indent);
        StringBuilder sb = new StringBuilder();
        sb.append("{\n")
                .append(childIndent).append("\"type\": \"Function\",\n")
                .append(childIndent).append("\"name\": \"").append(name).append("\",\n")
                .append(childIndent).append("\"args\": [");

        for (int i = 0; i < args.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(args.get(i).toJson(indent + 2));
        }
        sb.append("]\n")
                .append(baseIndent).append("}");
        return sb.toString();
    }

    @Override
    List<String> toASTTree() {
        List<String> result = new ArrayList<>();
        result.add(name + "()");
        for (ASTNode arg : args) {
            List<String> argLines = arg.toASTTree();
            for (String line : argLines) {
                result.add("  " + line);
            }
        }
        return result;
    }
}

class FormulaNode extends ASTNode {
    ASTNode expr;

    FormulaNode(ASTNode e) { expr = e; }

    @Override
    String toJson(int indent) {
        String childIndent = indent(indent + 1);
        String baseIndent = indent(indent);
        return "{\n" +
                childIndent + "\"type\": \"Formula\",\n" +
                childIndent + "\"expression\": " + expr.toJson(indent + 1) + "\n" +
                baseIndent + "}";
    }

    @Override
    List<String> toASTTree() {
        List<String> result = new ArrayList<>();
        result.add("Formula");
        List<String> exprLines = expr.toASTTree();
        for (String line : exprLines) {
            result.add("  " + line);
        }
        return result;
    }
}