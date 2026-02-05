package model.statement;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expression.Exp;
import model.type.BoolType;
import model.type.Type;

public class RepeatStmt implements IStmt {
    private final IStmt stmt;
    private final Exp exp;

    public RepeatStmt(IStmt stmt, Exp exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        // Create the transformed statement: stmt1; (while(exp2) stmt1)
        IStmt converted = new CompStmt(stmt, new WhileStmt(exp, stmt));

        // Push the new statement onto the execution stack
        state.getStk().push(converted);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);

        if (typeExp.equals(new BoolType())) {
            // Check body in a copy of the environment (loops don't leak scope in this context)
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MyException("Repeat: Expression must be of type bool");
        }
    }

    @Override
    public String toString() {
        return "repeat " + stmt.toString() + " as " + exp.toString();
    }
}