package pl.telephonebookspring.telephonebookspringapp.exception;

public class OptionNotFoundException extends RuntimeException {

    public OptionNotFoundException(int optionId){
        super("Nie znaleziono opcji o ID: " + optionId);
    }
}
