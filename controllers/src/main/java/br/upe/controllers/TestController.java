package br.upe.controllers;

import br.upe.pojos.Submission;
import java.util.Collection;

public class TestController {
    public static void main(String[] args){
        StateController state = new StateController();
        CRUDController crud = new CRUDController();

        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);
        SubmissionController sub = new SubmissionController(crud);

        auth.createNewAdmin("italan.leal@upe.br", "password");
        auth.createNewUser("julio.mota@upe.br", "senhas");

        auth.login("italan.leal@upe.br", "password");

        event.createNewEvent("SUPER | 2024", "Tárcio");
        auth.logout();


        auth.login("julio.mota@upe.br", "senhas");
        event.addEventSubmission("Algorítimo genético para análise de subgrupo");

        Collection<Submission> sub1 = sub.getAllSubmissionsByUser(state.getCurrentUser().getUuid());
        sub.removeSubmission(sub1.iterator().next().getUuid());
    }
}
