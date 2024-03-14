package org.lang;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Basic REPL");
        while (true) {
            System.out.print("> ");
            var choice = sc.nextLine();
            switch (choice) {
                case "help":
                    printHelp();
                    break;
                case "exit":
                    System.exit(0);
                default:
                    eval(choice);
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
}
