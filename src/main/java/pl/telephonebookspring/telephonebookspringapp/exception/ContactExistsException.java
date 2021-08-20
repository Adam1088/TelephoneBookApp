package pl.telephonebookspring.telephonebookspringapp.exception;


import pl.telephonebookspring.telephonebookspringapp.model.Contact;

public class ContactExistsException extends RuntimeException {

    public ContactExistsException(Contact contact) {
        super("Kontakt o numerze: " + contact.getPhoneNumber() + " juz istnieje w bazie.");
    }
}
