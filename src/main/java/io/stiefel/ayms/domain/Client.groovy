package io.stiefel.ayms.domain

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIdentityReference
import com.fasterxml.jackson.annotation.JsonView
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import groovy.transform.Canonical
import org.hibernate.validator.constraints.NotEmpty

import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

/**
 * @author jason@stiefel.io
 */
@Entity
@Table(name = 'aym_client')
@Canonical(includes = 'id')
@NamedQueries([
        @NamedQuery(name = 'Client.findByCompanyAndId', query = 'select c from Client c where c.company = :company and c.id = :id'),
        @NamedQuery(name = 'Client.findAllByCompany', query = 'select c from Client c where c.company = :company'),
        @NamedQuery(name = 'Client.findAllByCompanyAndState', query = 'select c from Client c where c.company = :company and c.address.state = :state')
])
class Client {

    @Id
    @GeneratedValue
    @JsonView(View.Summary)
    Long id

    @ManyToOne(optional = false)
    @JoinColumn(name = 'company_id')
    @JsonView(View.Summary)
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    Company company

    @Column(nullable = false, length = 75)
    @JsonView(View.Summary)
    @NotEmpty
    String firstName

    @Column(nullable = false, length = 75)
    @JsonView(View.Summary)
    @NotEmpty
    String lastName

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonView(View.Summary)
    @NotNull
    Date dateOfBirth

    @Column
    @JsonView(View.Summary)
    @NotEmpty
    @Pattern(regexp = '\\d{3}-\\d{2}-\\d{4}')
    String ssn

    @Embedded
    @JsonView(View.Summary)
    @Valid
    Address address

    @OneToMany(fetch = FetchType.LAZY, mappedBy = 'client', orphanRemoval = true)
    List<Service> services;

}
