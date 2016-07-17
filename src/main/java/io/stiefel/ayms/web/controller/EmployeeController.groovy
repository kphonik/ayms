package io.stiefel.ayms.web.controller

import com.fasterxml.jackson.annotation.JsonView
import io.stiefel.ayms.dao.CompanyDao
import io.stiefel.ayms.dao.EmployeeDao
import io.stiefel.ayms.domain.Company
import io.stiefel.ayms.domain.Employee
import io.stiefel.ayms.domain.View
import io.stiefel.ayms.web.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

import javax.validation.Valid

/**
 * @author jason@stiefel.io
 */
@RestController
@RequestMapping(value = '/employee')
class EmployeeController {

    @Autowired CompanyDao companyDao
    @Autowired EmployeeDao employeeDao

    @RequestMapping(method = RequestMethod.GET, produces = 'text/html')
    String index() {
        'employee'
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @JsonView(View.Summary)
    Result<List<Employee>> findAll(Long companyId) {
        if (companyId) {
            Company company = companyDao.find(companyId)
            return new Result(employeeDao.findAllByCompany(company))
        }
        new Result(employeeDao.findAll())
    }

    @RequestMapping(path = '/{employeeId}', method = RequestMethod.GET, produces = 'application/json')
    @JsonView(View.Summary)
    Result<Employee> find(@PathVariable Long employeeId) {
        new Result(employeeDao.find(employeeId))
    }

    @RequestMapping(method = RequestMethod.POST, produces = 'application/json')
    Result<Long> save(@Valid Employee employee, BindingResult binding) {
        if (binding.hasErrors())
            return new Result(false, null).binding(binding)
        employee.company = companyDao.find(companyId)
        employeeDao.save(employee)
        new Result(employee.id)
    }

}
