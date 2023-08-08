package com.disl.dbfile.repositories;

import com.disl.dbfile.entities.FileObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileObjectRepository extends JpaRepository<FileObject, Long> {

}
