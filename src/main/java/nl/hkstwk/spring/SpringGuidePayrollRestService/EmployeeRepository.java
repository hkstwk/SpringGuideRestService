package nl.hkstwk.spring.SpringGuidePayrollRestService;

import org.springframework.data.jpa.repository.JpaRepository;

interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
