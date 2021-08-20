package pl.telephonebookspring.telephonebookspringapp.service;


import org.springframework.stereotype.Service;
import pl.telephonebookspring.telephonebookspringapp.model.Contact;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final static String fileName = "contacts.csv";

    public Set<Contact> createContactsFromFile() throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        } else {
            try (var reader = new BufferedReader(new FileReader(fileName))) {
                return reader.lines()
                        .map(line -> line.split(";"))
                        .map(words -> new Contact(words[0], words[1]))
                        .collect(Collectors.toSet());
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
        return new HashSet<>();
    }

    public void writeContactsToFile(Set<Contact> contacts) throws IOException {
        try (var writer = new BufferedWriter(new FileWriter(fileName))) {
            List<String> lines = contacts.stream()
                    .map(contact -> contact.getName() + ";" + contact.getPhoneNumber())
                    .collect(Collectors.toList());

            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

}
