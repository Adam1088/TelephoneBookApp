package pl.telephonebookspring.telephonebookspringapp.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import pl.telephonebookspring.telephonebookspringapp.exception.ContactExistsException;
import pl.telephonebookspring.telephonebookspringapp.model.Contact;
import pl.telephonebookspring.telephonebookspringapp.model.Option;
import pl.telephonebookspring.telephonebookspringapp.model.TeleBook;
import pl.telephonebookspring.telephonebookspringapp.service.FileService;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

@Getter
@Setter
@Controller
public class TeleBookController {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int DEFAULT = 0;
    private static final int ADD_CONTACT = 1;
    private static final int SEARCH_BY_NAME = 2;
    private static final int SEARCH_BY_PHONE = 3;
    private static final int DELETE_CONTACT = 4;
    private static final int EXIT = 5;

    private TeleBook teleBook;
    private FileService fileService;

    public TeleBookController(TeleBook teleBook, FileService fileService) {
        this.teleBook = teleBook;
        this.fileService = fileService;
    }

    public void loop() {
        int optionId;
        do {
            showOptions();
            optionId = getOption();
            executeOption(optionId);
        } while (optionId != EXIT);
    }

    private static int getOption() {
        int optionId = DEFAULT;
        try {
            optionId = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.err.println("Niepoprawna opcja.");
        }
        return optionId;
    }

    private static void showOptions() {
        System.out.println(">>>Opcje:");
        Arrays.stream(Option.values()).forEach(System.out::println);
    }

    private void executeOption(int optionId) {
        switch (optionId) {
            case ADD_CONTACT:
                addNewContact();
                break;
            case SEARCH_BY_NAME:
                searchByName();
                break;
            case SEARCH_BY_PHONE:
                searchByPhoneNumber();
                break;
            case DELETE_CONTACT:
                deleteContact();
                break;
            case EXIT:
                close();
                break;
            default:
                System.err.println("Zla opcja.");
                break;
        }
    }

    private void close(){
        scanner.close();
        Set<Contact> contacts = teleBook.getContacts();
        try {
            fileService.writeContactsToFile(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("Koniec programu.");
    }

    private void deleteContact() {
        scanner.nextLine();
        System.out.println("Podaj numer telefonu:");
        String phoneNumber = scanner.nextLine();
        boolean isContactDeleted = teleBook.deleteContact(phoneNumber);
        if (isContactDeleted) {
            System.err.println("Kontakt usunięty.");
        } else {
            System.err.println("Nie znaleziono kontaktu.");
        }
    }

    private void searchByName() {
        scanner.nextLine();
        System.out.println("Podaj fragment nazwy:");
        String pieceOfName = scanner.nextLine();
        Set<Contact> foundContacts = teleBook.findContactsContainingName(pieceOfName);
        printFoundContacts(foundContacts);
    }

    private void searchByPhoneNumber() {
        scanner.nextLine();
        System.out.println("Podaj fragment numeru telefonu:");
        String pieceOfNumber = scanner.nextLine();
        Set<Contact> foundContacts = teleBook.findContactsContainingPhoneNumber(pieceOfNumber);
        printFoundContacts(foundContacts);
    }

    private void printFoundContacts(Set<Contact> foundContacts) {
        if (!foundContacts.isEmpty()) {
            System.err.println("Znalezione kontakty:");
            foundContacts.forEach(System.out::println);
        } else {
            System.err.println("Nie znaleziono kontaktow.");
        }
    }

    private void addNewContact() {
        scanner.nextLine();
        System.out.println("Podaj nazwę kontaktu:");
        String name = scanner.nextLine();
        System.out.println("Podaj numer: ");
        String phoneNumber = scanner.nextLine();
        if (name != null && phoneNumber != null && !name.isBlank() && !phoneNumber.isBlank()) {
            try {
                teleBook.addNewContact(new Contact(name, phoneNumber));
                System.err.println("Rekord dodany.");
            } catch (ContactExistsException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.err.println("Nazwa ani numer telefonu nie mogą być puste.");
        }
    }


}
