package pl.telephonebookspring.telephonebookspringapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.telephonebookspring.telephonebookspringapp.exception.ContactExistsException;
import pl.telephonebookspring.telephonebookspringapp.service.FileService;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
public class TeleBook {
    private Set<Contact> contacts;
    private FileService fileService;

    public TeleBook(FileService fileService) {
        this.fileService = fileService;
        try {
            contacts = fileService.createContactsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewContact(Contact contact) {
        if(contacts.contains(contact)){
            throw new ContactExistsException(contact);
        }
        contacts.add(contact);
    }

    public boolean deleteContact(String name) {
        Optional<Contact> optionalContact = findContactByPhone(name);
        if(optionalContact.isPresent()){
            contacts.remove(optionalContact.get());
            return true;
        }
        return false;
    }

    public Optional<Contact> findContactByPhone(String phoneNumber) {
        return contacts.stream()
                .filter(contact -> contact.getPhoneNumber().equalsIgnoreCase(phoneNumber))
                .findAny();
    }

    public Set<Contact> findContactsContainingName(String name){
        return contacts.stream()
                .filter(contact -> contact.getName().contains(name))
                .collect(Collectors.toSet());
    }

    public Set<Contact> findContactsContainingPhoneNumber(String phoneNumber){
        return contacts.stream()
                .filter(contact -> contact.getPhoneNumber().contains(phoneNumber))
                .collect(Collectors.toSet());
    }

}
