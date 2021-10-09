package pe.com.tarjetaoh.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.tarjetaoh.app.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
