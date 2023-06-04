package nl.hkstwk.spring.SpringGuidePayrollRestService;

public class EmployeeNotFoundException extends RuntimeException {

    EmployeeNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}