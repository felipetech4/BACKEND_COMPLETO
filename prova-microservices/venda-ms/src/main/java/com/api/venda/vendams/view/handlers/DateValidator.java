package com.api.venda.vendams.view.handlers;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateValidation, String> {
    public boolean isValid(String dataVenda, ConstraintValidatorContext cxt) {
        // nenhuma data foi passada

        if (dataVenda == null || dataVenda.isEmpty() || dataVenda.isBlank()) {
            return true;
        }

        boolean dataValida = dataVenda.matches("^(\\d{2})[/](\\d{2})[/](\\d{4})$");

        if (!dataValida) {
            return false;
        }

        String[] arrayData = dataVenda.split("/");

        int dia = Integer.parseInt(arrayData[0]);
        int mes = Integer.parseInt(arrayData[1]);
        int ano = Integer.parseInt(arrayData[2]);

        if ((dia > 0 && dia <= 31) &&
                (mes > 0 && mes <= 12) &&
                (ano > 1900 && ano <= 2050)) {
            return true;
        } else {
            return false;
        }
    }
}
