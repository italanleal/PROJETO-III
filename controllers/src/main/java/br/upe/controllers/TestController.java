package br.upe.controllers;

public class TestController {
    public static void main(String[] args){
        StateController state = new StateController();
        CRUDController crud = new CRUDController();

        AuthController auth = new AuthController(state, crud);

        if(!auth.createNewAdmin("italan.leal@upe.br", "password")){
            System.out.println("Usuario ja existe\n");
        }
        auth.login("italan.leal@upe.br", "password");

        if(state.getCurrentUser() != null){
            System.out.println(state.getCurrentUser().getEmail());
        } else {
            System.out.println("faca login primeiro\n");
        }
        auth.logout();

        if(state.getCurrentUser() != null){
            System.out.println(state.getCurrentUser().getEmail());
        } else {
            System.out.println("faca login primeiro\n");
        }
    }
}
