package com.example.damien.projetir.CustomEdit;

/**
 * Created by Damien on 03/01/2016.
 * Class pour logger
 */
public class Logger
{
    public enum LogAction {LOG_ACTION_TYPE,LOG_ACTION_DELETE, LOG_ACTION_MOVE, LOG_ACTION_AUTOMOVE, LOG_ACTION_START, LOG_ACTION_STOP, LOG_ACTION_RETURN, LOG_ACTION_INIT}

    private LogAction previousLogAction;
    private String dataLogs;



    public void setPreviousLogAction(LogAction action)
    {
        previousLogAction = action;
    }

    public LogAction getPreviousLogAction()
    {
        return this.previousLogAction;
    }

    public String getDataLogs()
    {
        return dataLogs;
    }

    public void setDataLogs(String dataLogs)
    {
        this.dataLogs = dataLogs;
    }
    public void writeSingleDataLogs(String data)
    {

        setDataLogs(getDataLogs() + data);
    }

    public void writeDataLogs(LogAction currentAction,String msg, float currentTime)
    {
        if(getPreviousLogAction() == null)
        {
            writeSingleDataLogs("Error : No Previous Action");
        }
        else if(!getPreviousLogAction().name().matches(currentAction.name()))
        {
            writeSingleDataLogs("</"+ getPreviousLogAction().name()+ ">");
            writeSingleDataLogs("\n");
            writeSingleDataLogs("<" + currentAction.name() + ">");
            writeSingleDataLogs("\n");
        }

        if(!currentAction.name().matches(LogAction.LOG_ACTION_STOP.name()))
        {
            writeSingleDataLogs(Float.toString(currentTime / 1000000000) + " : " + msg);
            writeSingleDataLogs("\n");
        }
        else
        {
            writeSingleDataLogs("</"+LogAction.LOG_ACTION_STOP.name()+">");
            writeSingleDataLogs("\n");
        }
        setPreviousLogAction(currentAction);
    }
    public void resetDataLogs()
    {
        this.dataLogs = "";
    }
}


