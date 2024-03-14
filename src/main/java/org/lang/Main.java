package org.lang;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Compiler compiler = new Compiler();

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
                    compiler.eval();
            }
        }
    }

    private static void printHelp() {
        System.out.println("This is a simple REPL for evaluating expressions");
        System.out.println("Supported characters are - [0-9], +, -, *, /, (, )");
    }
}
