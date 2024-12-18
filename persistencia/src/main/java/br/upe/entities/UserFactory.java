package br.upe.entities;

public interface UserFactory {
    static SystemUser createSystemUser() {
        SystemUser user = new SystemUser();
        user.setSu(false);
        return user;
    }

    static SystemAdmin createSystemAdmin() {
        SystemAdmin admin = new SystemAdmin();
        admin.setSu(true);
        return admin;
    }
}
