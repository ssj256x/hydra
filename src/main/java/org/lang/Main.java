package org.lang;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static boolean showTree = false;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Basic REPL");
        while (true) {
            System.out.print("> ");
            var choice = sc.nextLine();
            switch (choice) {
                case "help":
                    printHelp();
                    break;
                case "showTree":
                    showTree = !showTree;
                    System.out.println(showTree ? "showing parse trees" : "not showing parse trees");
                    continue;
                case "exit":
                    System.exit(0);
                default:
                    parse(choice);
            }
        }
    }

    private static void printHelp() {
        System.out.println("This is a simple REPL for evaluating expressions");
        System.out.println("Supported characters are - [0-9], +, -, *, /, (, )");
    }

    private static void eval(String text) {
        Lexer lexer = new Lexer(text);

        while (true) {
            var token = lexer.nextToken();

            if (token.getKind() == SyntaxKind.EndOfFileToken) {
                break;
            }
            System.out.println(token);
        }
    }

    private static void parse(String text) throws Exception {

        var syntaxTree = SyntaxTree.parse(text);

        if (showTree) {
            prettyPrint(syntaxTree.getRoot(), "", true);
        }

        if (!syntaxTree.getDiagnostics().isEmpty()) {
            syntaxTree.getDiagnostics().forEach(ColorPrint.RED::print);
        } else {
            var e = new Evaluator(syntaxTree.getRoot());
            ColorPrint.YELLOW.print(e.evaluate());
        }
    }

    private static void prettyPrint(SyntaxNode node, String indent, boolean isLast) {

        var marker = isLast ? "└──" : "├──";

        System.out.print(indent);
        System.out.print(marker);
        System.out.print(node.getKind().name());
        if (node instanceof SyntaxToken t && t.getValue() != null) {
            System.out.print(" ");
            System.out.print(t.getValue());
        }
        System.out.println();
        indent += isLast ? "   " : "│  ";

        var lastChild = !node.getChildren().isEmpty() ?
                node.getChildren().getLast() :
                null;

        for (var child : node.getChildren()) {
            prettyPrint(child, indent, child == lastChild);
        }
    }
}
