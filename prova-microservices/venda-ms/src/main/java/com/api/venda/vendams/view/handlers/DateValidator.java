package com.api.venda.vendams.view.handlers;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateValidation, String>
{
    public boolean isValid(String dataVenda, ConstraintValidatorContext cxt) {
            // nenhuma data foi passada
            
            System.out.println("\n\n\n entrou \n\n\n");

            if(dataVenda == null || dataVenda.isEmpty() || dataVenda.isBlank()) {
                return true;
            }
            
            boolean dataValida = dataVenda.matches("^(\\d{2})[/](\\d{2})[/](\\d{4})$");
            
            if (!dataValida) { 
                System.out.println("\n\n\n entrou 2 \n\n\n");
                return false;
            }
            
            System.out.println("\n\n\n entrou 3 \n\n\n");
            String[] arrayData = dataVenda.split("/");
            
            int dia = Integer.parseInt(arrayData[0]);
            int mes = Integer.parseInt(arrayData[1]);
            int ano = Integer.parseInt(arrayData[2]);
            
            if ((dia > 0 && dia <= 31) &&
            (mes > 0 && mes <= 12) &&
            (ano > 1900 && ano <= 2050)) {
                System.out.println("\n\n\n entrou 4 true \n\n\n");
                return true;
            } else {
                System.out.println("\n\n\n entrou 4 falso \n\n\n");
                return false;
            }
    }
}
