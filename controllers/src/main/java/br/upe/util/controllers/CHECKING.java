package br.upe.util.controllers;

import br.upe.util.persistencia.SystemException;

import java.time.LocalDate;

public class CHECKING {
    public static void checkDates(LocalDate startDate, LocalDate endDate) throws SystemException {
        if(startDate.isAfter(endDate) ){
            throw new InvalidDateInput("Start date set after end date", null);
        }
        if(endDate.isBefore(startDate)){
            throw new InvalidDateInput("End date set before start date", null);
        }
    }
    CHECKING(){}
}
