package dev.rivaldi.springbooth2file;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends PagingAndSortingRepository<Student, String>, CrudRepository<Student, String> {
}
