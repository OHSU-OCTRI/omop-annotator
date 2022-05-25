package org.octri.omop_annotator.service.omop;

import java.util.Optional;

import org.octri.omop_annotator.domain.omop.Person;
import org.octri.omop_annotator.repository.omop.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;

	public Optional<Person> findById(Integer id) {
		return repository.findById(id);
	}

}
