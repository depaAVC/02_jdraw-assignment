package jdraw.std;

import jdraw.framework.Commands.GroupCommand;
import jdraw.framework.DrawCommand;
import jdraw.framework.DrawCommandHandler;

import java.util.Stack;

/**
 * Created by degonas on 15.12.2018.
 */
public class StdDrawCommandHandler implements DrawCommandHandler {

    private Stack<DrawCommand> past = new Stack<>();
    private Stack<DrawCommand> future = new Stack<>();

    private boolean hasToRecordMacroCommand = false;
    private GroupCommand currentGroupCommand = null;

    @Override
    public void addCommand(DrawCommand cmd) {
        if (hasToRecordMacroCommand){
            currentGroupCommand.add(cmd);
        } else {
            past.add(cmd);
        }
        future.clear();
    }

    @Override
    public void undo() {
        System.out.println("About to undo");
        if (undoPossible()) {
            DrawCommand dc = past.pop();
            dc.undo();
            future.push( dc );
            System.out.println("undone");
        }
    }

    @Override
    public void redo() {
        System.out.println("About to redo");
        if (redoPossible()) {
            DrawCommand dc = future.pop();
            dc.redo();
            past.push( dc );
            System.out.println("redone");
        }
    }

    @Override
    public boolean undoPossible() {
        return !past.isEmpty();
    }

    @Override
    public boolean redoPossible() {
        return !future.isEmpty();
    }

    @Override
    public void beginScript() {
        hasToRecordMacroCommand = true;
        currentGroupCommand = new GroupCommand();
    }

    @Override
    public void endScript() {
        hasToRecordMacroCommand = false;
        //prevents empty commands.
        if (!currentGroupCommand.isEmpty()) {
            past.add( currentGroupCommand);
        }
        currentGroupCommand = null;
    }

    @Override
    public void clearHistory() {
        past.clear();
        future.clear();
        endScript();
    }
}
