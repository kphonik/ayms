package io.stiefel.ayms.web.controller

import com.fasterxml.jackson.annotation.JsonView
import io.stiefel.ayms.dao.CompanyDao
import io.stiefel.ayms.dao.UserDao
import io.stiefel.ayms.domain.Company
import io.stiefel.ayms.domain.User
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
@RequestMapping(value = '/company/{companyId}/user')
class UserController {

    @Autowired CompanyDao companyDao
    @Autowired UserDao userDao

    @RequestMapping(method = RequestMethod.GET, produces = 'text/html')
    ModelAndView index(@PathVariable Long companyId) {
        new ModelAndView('user', [companyId: companyId])
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @JsonView(View.Summary)
    Result<List<User>> findAll(@PathVariable Long companyId) {
        Company company = companyDao.find(companyId)
        new Result(userDao.findAllByCompany(company))
    }

    @RequestMapping(path = '/{userId}', method = RequestMethod.GET, produces = 'application/json')
    @JsonView(View.Summary)
    Result<User> find(@PathVariable Long companyId, @PathVariable Long userId) {
        Company company = companyDao.find(companyId)
        new Result(userDao.findByCompanyAndId(company, userId))
    }

    @RequestMapping(method = RequestMethod.POST, produces = 'application/json')
    Result<Long> save(@PathVariable Long companyId, @Valid User user, BindingResult binding) {
        if (binding.hasErrors())
            return new Result(false, null).binding(binding)
        user.company = companyDao.find(companyId)
        userDao.save(user)
        new Result(user.id)
    }

}
