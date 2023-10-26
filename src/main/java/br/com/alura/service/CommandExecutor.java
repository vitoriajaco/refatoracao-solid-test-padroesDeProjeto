package br.com.alura.service;

public class CommandExecutor {

    public void executeCommand(Command command){
        command.execute();
    }
}
