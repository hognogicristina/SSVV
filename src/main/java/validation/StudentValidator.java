package validation;

import domain.Student;

public class StudentValidator implements Validator<Student> {
    public void validate(Student student) throws ValidationException {
        String idStr = student.getID();
        if (idStr == null || idStr.isEmpty()) {
            throw new ValidationException("ID invalid! \n");
        } else {
            try {
                int id = Integer.parseInt(idStr);
                if (id < 0) {
                    throw new ValidationException("ID must be a non-negative integer! \n");
                }
            } catch (NumberFormatException e) {
                throw new ValidationException("ID must be a valid integer! \n");
            }
        }

        if (student.getNume() == null || student.getNume().equals("")) {
            throw new ValidationException("Nume invalid! \n");
        }

        String grupaStr = String.valueOf(student.getGrupa());
        if (grupaStr == null || grupaStr.equals("")) {
            throw new ValidationException("Grupa invalida! \n");
        } else {
            try {
                int grupa = Integer.parseInt(grupaStr);
                if (grupa < 111 || grupa > 937) {
                    throw new ValidationException("Grupa invalida! Grupa trebuie sa fie intre 111 si 937. \n");
                }
            } catch (NumberFormatException e) {
                throw new ValidationException("Grupa trebuie sa fie un numar intreg valid! \n");
            }
        }
    }
}