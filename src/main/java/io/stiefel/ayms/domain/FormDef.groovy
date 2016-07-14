package io.stiefel.ayms.domain

import com.fasterxml.jackson.annotation.JsonView
import org.hibernate.validator.constraints.NotEmpty

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

/**
 * @author jason@stiefel.io
 */
@Entity
@Table(name = 'aym_form_def')
class FormDef {

    @Id
    @GeneratedValue
    @JsonView(View.Summary)
    Long id;

    @Column(unique = true, nullable = false, length = 100)
    @JsonView(View.Summary)
    @NotEmpty
    String name;

    @Column
    @JsonView(View.Summary)
    @NotEmpty
    String description;

}
