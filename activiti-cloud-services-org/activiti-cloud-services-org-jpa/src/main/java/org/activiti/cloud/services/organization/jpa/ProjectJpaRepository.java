/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.cloud.services.organization.jpa;

import java.util.Optional;

import org.activiti.cloud.organization.core.model.Group;
import org.activiti.cloud.organization.core.model.Project;
import org.activiti.cloud.organization.core.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * JPA Repository for {@link Project} entity
 */
@RepositoryRestResource(path = "projects",
        collectionResourceRel = "projects",
        itemResourceRel = "projects")
public interface ProjectJpaRepository extends JpaRepository<Project, String>,
                                              ProjectRepository {

    Page<Project> findAllByGroupIdIsNull(Pageable page);

    Page<Project> findAllByGroupId(String groupId,
                                   Pageable pageable);

    @Override
    default Page<Project> getTopLevelProjects(Pageable pageable) {
        return findAllByGroupIdIsNull(pageable);
    }

    @Override
    default Page<Project> getProjects(Group group,
                                      Pageable pageable) {
        return findAllByGroupId(group.getId(),
                                pageable);
    }

    @Override
    default Optional<Project> findProjectById(String projectId) {
        return findById(projectId);
    }

    @Override
    default Project createProject(Project project) {
        return save(project);
    }

    @Override
    default Project updateProject(Project projectToUpdate) {
        return save(projectToUpdate);
    }

    @Override
    default void deleteProject(Project project) {
        delete(project);
    }
}
