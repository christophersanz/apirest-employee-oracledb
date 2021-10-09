package pe.com.tarjetaoh.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.tarjetaoh.app.exception.ResourceNotFoundException;
import pe.com.tarjetaoh.app.model.Employee;
import pe.com.tarjetaoh.app.repository.EmployeeRepository;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EmployeeController
{
	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping(value = "/healthcheck", produces = "application/json; charset=utf-8")
	public String getHealthCheck() {
		return "{ \"todoOk\" : true }";
	}

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado - id :: " + id));
		return ResponseEntity.ok().body(employee);
	}

	@PostMapping("/employee")
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee updateEmployee, @PathVariable Long id) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado - id :: " + id));
		employee.setNombres(updateEmployee.getNombres());
		employee.setApellidos(updateEmployee.getApellidos());
		employee.setEdad(updateEmployee.getEdad());
		employee.setFechaNacimiento(updateEmployee.getFechaNacimiento());
		employee.setSalario(updateEmployee.getSalario());
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping(value = "/employee/{id}", produces = "application/json; charset=utf-8")
	public String deleteEmployee(@PathVariable Long id) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado - id :: " + id));
		employeeRepository.deleteById(id);
		return "{ \"operacionExitosa\" : "+ (employee!=null ? "true" : "false") +" }";
	}

}
