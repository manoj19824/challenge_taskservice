package com.celonis.challenge.service;

import com.celonis.challenge.model.ProjectGenerationTask;
import com.celonis.challenge.model.ProjectGenerationTaskRepository;
import com.celonis.challenge.services.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URL;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    private ProjectGenerationTaskRepository repository;

    @InjectMocks
    private FileService fileService;

    @Test
    public void testStoreResult() throws IOException {
        URL url = new URL("http://baeldung.com/challenge.zip");
        ProjectGenerationTask task = new ProjectGenerationTask();
        fileService.storeResult("task1221",url,task);
        Mockito.verify(repository).save(task);
    }
}
