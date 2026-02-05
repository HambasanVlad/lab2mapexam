package main;

import model.expression.*;
import model.statement.*;
import model.type.*;
import model.value.*;
import java.util.ArrayList;
import java.util.List;

public class Interpreter {

    // Această metodă returnează lista de exemple pentru GUI
    public static List<IStmt> getExamples() {
        List<IStmt> examples = new ArrayList<>();

        // Ex 1-11 sunt comentate conform fișierului tău original
        /* ... cod vechi comentat ... */

        // Ex Repeat:
        // Construim corpul buclei: Fork(...) ; v=v+1
        IStmt body = new CompStmt(
                new ForkStmt(
                        new CompStmt(new PrintStmt(new VarExp("v")),
                                new CompStmt(new AssignStmt("x", new ArithExp('-', new VarExp("x"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("x"))))
                ),
                new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))
        );

        // Construim partea de final a firului Main: Multe Nop-uri + Print(x)
        // Folosim un FOR pentru a adăuga 50 de NopStmt-uri, asigurând întârzierea necesară
        IStmt mainEnd = new PrintStmt(new VarExp("x"));
        for (int i = 0; i < 1000; i++) {
            mainEnd = new CompStmt(new NopStmt(), mainEnd);
        }

        IStmt exRepeat = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("x", new IntType()),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(3))),
                                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(2))),
                                        new CompStmt(
                                                new RepeatStmt(
                                                        body,
                                                        new RelationalExp("==", new VarExp("v"), new ValueExp(new IntValue(0)))
                                                ),
                                                mainEnd // Aici avem cele 50 de Nop-uri + Print
                                        )
                                )
                        )
                )
        );
        examples.add(exRepeat);

        return examples;
    }

    public static void main(String[] args) {
        // Entry point
    }
}