package br.com.alura.service;

public interface Command {

   void execute(); //Command é um padrao de projeto para tornar o codigo mais limpo
    //Nao é necessario colocar o public pois a interface ja é public by default
}
