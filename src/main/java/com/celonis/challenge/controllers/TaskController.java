package com.celonis.challenge.controllers;

import com.celonis.challenge.model.ProjectGenerationTask;
import com.celonis.challenge.model.TaskProgressResult;
import com.celonis.challenge.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "task", description = "Task Service API")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "List available tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get tasks Successful",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    @GetMapping(value = "/")
    public List<ProjectGenerationTask> listTasks() {
        return taskService.listTasks();
    }

    @Operation(summary = "Create a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Creation Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGenerationTask.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    @PostMapping(value = "/")
    public ProjectGenerationTask createTask(@RequestBody @Valid ProjectGenerationTask projectGenerationTask) {
        return taskService.createTask(projectGenerationTask);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get task based on task id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get tasks Successful",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    public ProjectGenerationTask getTask(
            @Parameter(
                    description = "Task id",
                    required = true,
                    style = ParameterStyle.FORM,
                    explode = Explode.FALSE,
                    schema = @Schema(implementation = String.class)
            )
            @PathVariable String taskId) {
        return taskService.getTask(taskId);
    }

    @Operation(summary = "Update the current task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update tasks Successful",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    @PutMapping("/{taskId}")
    public ProjectGenerationTask updateTask(
            @Parameter(
                    description = "Task id",
                    required = true,
                    style = ParameterStyle.FORM,
                    explode = Explode.FALSE,
                    schema = @Schema(implementation = String.class)
            )@PathVariable String taskId, @RequestBody @Valid ProjectGenerationTask projectGenerationTask) {
        return taskService.update(taskId, projectGenerationTask);
    }

    @Operation(summary = "Delete the task based on id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete tasks Successful",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(
            @Parameter(
                    description = "Task id",
                    required = true,
                    style = ParameterStyle.FORM,
                    explode = Explode.FALSE,
                    schema = @Schema(implementation = String.class)
            )
            @PathVariable String taskId) {
        taskService.delete(taskId);
    }

    @Operation(summary = "Execute the task based on id and num_of_seconds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Execute tasks Successful",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    @PostMapping("/{taskId}/execute")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void executeTask(
            @Parameter(
                    description = "Task id",
                    required = true,
                    style = ParameterStyle.FORM,
                    explode = Explode.FALSE,
                    schema = @Schema(implementation = String.class)
            )
            @PathVariable String taskId,
            @Parameter(
                    description = "Number of seconds the task should run",
                    required = false,
                    style = ParameterStyle.FORM,
                    explode = Explode.FALSE,
                    schema = @Schema(implementation = String.class)
            )
            @RequestParam(name = "run_for_seconds", defaultValue = "30") String noOfSeconds) {
        taskService.executeTask(taskId, noOfSeconds);
    }

    @Operation(summary = "Get the result based on task id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetching result Successful",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    @GetMapping("/{taskId}/result")
    public ResponseEntity<FileSystemResource> getResult(
            @Parameter(
                    description = "Task id",
                    required = true,
                    style = ParameterStyle.FORM,
                    explode = Explode.FALSE,
                    schema = @Schema(implementation = String.class)
            )
            @PathVariable String taskId) {
        return taskService.getTaskResult(taskId);
    }

    @Operation(summary = "Get the progress of task based on taskId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetching task progress Successful",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    @GetMapping(value = "/{taskId}/progress",produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskProgressResult getProgress(
            @Parameter(
                    description = "Task id",
                    required = true,
                    style = ParameterStyle.FORM,
                    explode = Explode.FALSE,
                    schema = @Schema(implementation = String.class)
            )
            @PathVariable String taskId) {
        return taskService.getProgress(taskId);
    }

    @Operation(summary = "Cancel a running task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cancel Successful",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    @GetMapping("/{taskId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelTask(
            @Parameter(
                    description = "Task id",
                    required = true,
                    style = ParameterStyle.FORM,
                    explode = Explode.FALSE,
                    schema = @Schema(implementation = String.class)
            )
            @PathVariable String taskId) {
        taskService.cancelTask(taskId);
    }

}
