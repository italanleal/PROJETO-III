package br.upe.operations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

class CRUDInterfaceTest {

    @Test
    void testNewEventCRUD() {
        EventCRUD eventCRUD = CRUDInterface.newEventCRUD();
        assertNotNull(eventCRUD, "Deveria retornar uma instância de EventCRUD");
        assertInstanceOf(EventCRUD.class, eventCRUD, "A instância deveria ser do tipo EventCRUD");
    }

    @Test
    void testNewSessionCRUD() {
        SessionCRUD sessionCRUD = CRUDInterface.newSessionCRUD();
        assertNotNull(sessionCRUD, "Deveria retornar uma instância de SessionCRUD");
        assertInstanceOf(SessionCRUD.class, sessionCRUD, "A instância deveria ser do tipo SessionCRUD");
    }

    @Test
    void testNewSubmissionCRUD() {
        SubmissionCRUD submissionCRUD = CRUDInterface.newSubmissionCRUD();
        assertNotNull(submissionCRUD, "Deveria retornar uma instância de SubmissionCRUD");
        assertInstanceOf(SubmissionCRUD.class, submissionCRUD, "A instância deveria ser do tipo SubmissionCRUD");
    }

    @Test
    void testNewSubscriptionCRUD() {
        SubscriptionCRUD subscriptionCRUD = CRUDInterface.newSubscriptionCRUD();
        assertNotNull(subscriptionCRUD, "Deveria retornar uma instância de SubscriptionCRUD");
        assertInstanceOf(SubscriptionCRUD.class, subscriptionCRUD, "A instância deveria ser do tipo SubscriptionCRUD");
    }

    @Test
    void testNewUserCRUD() {
        UserCRUD userCRUD = CRUDInterface.newUserCRUD();
        assertNotNull(userCRUD, "Deveria retornar uma instância de UserCRUD");
        assertInstanceOf(UserCRUD.class, userCRUD, "A instância deveria ser do tipo UserCRUD");
    }
}
