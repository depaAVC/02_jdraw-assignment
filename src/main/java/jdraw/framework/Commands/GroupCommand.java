package jdraw.framework.Commands;

import jdraw.framework.DrawCommand;

import java.util.ArrayList;

/**
 * Created by degonas on 16.12.2018.
 */
public class GroupCommand implements DrawCommand {

    private ArrayList<DrawCommand> parts = new ArrayList<>();

    public void add(DrawCommand dc){
        if (dc != null){
            parts.add(dc);
        }
    }

    @Override
    public void redo() {
        //from first command to last command
        for (DrawCommand dc : parts) {
            dc.redo();
        }

        //"Do first / last command" - strategy:
        /*
        if (!parts.isEmpty()){
            getLastCommand().redo();
        }*/
    }

    @Override
    public void undo() {
        //from last command to first command
        for (int i = parts.size() - 1; i >= 0; i--) {
            parts.get(i).undo();
        }

        //"Do first / last command" - strategy:
        /*
        if (!parts.isEmpty()){
            getFirstCommand().undo();
        }*/
    }

    private DrawCommand getFirstCommand() {
        return parts.get(0);
    }

    private DrawCommand getLastCommand() {
        return parts.get( parts.size() - 1);
    }

    public boolean isEmpty(){
        return parts.isEmpty();
    }
}
