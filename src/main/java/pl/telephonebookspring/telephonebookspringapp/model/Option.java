package pl.telephonebookspring.telephonebookspringapp.model;


import lombok.Getter;
import lombok.Setter;
import pl.telephonebookspring.telephonebookspringapp.exception.OptionNotFoundException;

import java.util.Arrays;

@Getter
public enum Option {
    ADD_NEW_RECORD(1, "Dodawanie nowego rekordu"),
    SEARCH_BY_NAME(2, "Szukanie po nazwie"),
    SEARCH_BY_PHONE_NUMBER(3, "Szukanie po numerze telefonu"),
    DELETE(4, "Usuwanie"),
    EXIT(5, "Wyjscie z programu");

    private final Integer id;
    private final String description;

    Option(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString(){
        return id + ". " + description;
    }

    public static Option convertNumberToOption(int optionId){
        return Arrays.stream(Option.values())
                .filter(option -> option.id.equals(optionId))
                .findAny()
                .orElseThrow(() -> new OptionNotFoundException(optionId));
    }
}
