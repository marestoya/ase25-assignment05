package de.unibayreuth.se.taskboard.data.mapper;

import de.unibayreuth.se.taskboard.business.domain.Task;
import de.unibayreuth.se.taskboard.data.persistence.TaskEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-12T17:34:36+0100",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class TaskEntityMapperImpl implements TaskEntityMapper {

    @Override
    public TaskEntity toEntity(Task source) {
        if ( source == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setId( source.getId() );
        taskEntity.setCreatedAt( source.getCreatedAt() );
        taskEntity.setUpdatedAt( source.getUpdatedAt() );
        taskEntity.setTitle( source.getTitle() );
        taskEntity.setDescription( source.getDescription() );
        taskEntity.setStatus( source.getStatus() );
        taskEntity.setAssigneeId( source.getAssigneeId() );

        return taskEntity;
    }

    @Override
    public Task fromEntity(TaskEntity source) {
        if ( source == null ) {
            return null;
        }

        String title = null;
        String description = null;

        title = source.getTitle();
        description = source.getDescription();

        Task task = new Task( title, description );

        task.setId( source.getId() );
        task.setCreatedAt( source.getCreatedAt() );
        task.setUpdatedAt( source.getUpdatedAt() );
        task.setStatus( source.getStatus() );
        task.setAssigneeId( source.getAssigneeId() );

        return task;
    }
}
