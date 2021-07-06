# Celonis Programming Challenge

Dear applicant,

Congratulations, you made it to the Celonis Programming Challenge!

Ideally it should not take you more than 3-4 hours for the backend module, or 6 hours if you do the frontend as well, but feel free to work at your own pace.

Why do we ask you to complete this challenge?

First of all, we need to have some way of comparing different applicants, and we try to answer certain questions which
we cannot out-right ask in an interview - also we don't want to ask too many technical questions 
in a face-to-face interview to not be personally biased in a potentially stressful situation.
To be as transparent as possible, we want to give you some insights into what we look at and how we evaluate.
This challenge gives you the possibility to shine :)

The challenge is divided into two modules: the backend and the frontend module.
The frontend module should be completed using Angular whereas the backend module using Java 11 with Spring Boot.
You are able to finish the backend module without finishing the frontend module but not the other way around.

Note that there is nothing wrong with googling when you have certain questions or are unsure about some APIs,
but you should not outright copy code. If you decide to copy code, please mark it as such, citing the source.

## Backend module

### Challenge 1: Completing and extending the java application

For this challenge, you have received a project which has a few problems.
You first have to fix those problems in order to get the application running, and then you should extend it with the requirements below.

What we are looking into:
  - Understanding and implementation of a specification
  - Java implementation skills (Java 11, Spring Boot)
  - Understanding of scheduling jobs that run in background
  - Multithreading / locking execution
  - **Note**: performance and scalability are important,
  please apply reasonable balance between solution performance and invested time
  - Testing:
    - automated tests, both unit and integration test _examples_ are very nice to have 
      
      **Note**: appreciating the candidate's time we are not looking for complete test coverage,
      but for representing some test implementation skills 
      and an experience of applying testing frameworks, tools and libraries
      
    - manual test: the solution should be runnable, the implemented API accessible. 

      We expect to do some demo during the next technical interview, 
      so please ensure the API works and prepare some mocks 
      (Postman, curl or any preferred HTTP/REST tools)

How to understand the task:
  - consider the provided challenge as an application with some existing functionality,
    which was used to "generate" a file and download it
  - fix current issues to make the application runnable
  - keep existing behavior and API
  - extend and generalize the supplied sources according to the description below
  

#### Task 1: Dependency injection

The project you received fails to start correctly due to a problem in the dependency injection.
Identify that problem and fix it.

#### Task 2: Extending the application

The task is to extend the current functionality of the backend by
- implementing a new task type
- showing the progress of the task execution
- implementing a task cancellation mechanism.

The new task type is a simple counter which is configured with two input parameters, `x` and `y` of type `integer`.
When the task is executed, counter should start in the background and progress should be monitored.
Counting should start from `x` and get increased by one every second.
When counting reaches `y`, the task should finish successfully.

The progress of the task should be exposed via the API so that a web client can monitor it.
Canceling a task that is being executed should be possible, in which case the execution should stop.

#### Task 3: Periodically cleaning up the tasks

The API can be used to create tasks, but the user is not required to execute those tasks.
The tasks that are not executed after an extended period (e.g. a week) should be periodically cleaned up (deleted).

## Frontend module

### Challenge 1: Web client

This challenge is about building the web client for the backend module.
The requirements of this web client are demonstrated with the Figma mockup -
[Link to mockup](https://www.figma.com/proto/QU16smviKOMZek8twvbxw1ap/Full-stack---challenge-02).

The application consists of a dashboard which lists all the tasks (including the tasks that are never executed).
From the list of tasks, the user can:
-ate new tasks cre
- open an existing task
- cancel tasks that are being executed
- delete tasks

For creating new tasks, a wizard should be implemented which has multiple steps. The steps are:
1. Choose the type of the scheduled task
2. Configure the chosen scheduled task
3. Execute and monitor progress
4. Summary with results

The user might leave the wizard at any step, but he/she should still be able to continue later and finish it.
The user should be able to refresh the browser tab and continue from the step where he left.

What we are looking into:
- Technical understanding of a provided API
- Ability to execute / implement
- Frontend project setup and API usage
- Frontend implementation skills (Javascript, Typescript, HTML/CSS)
- Frontend component structure and routing

## Cloud Native Module

### Challenge 1: Running on Kubernetes
This challenge is about deploying the application you built in the previous challenges to Kubernetes.

What we are looking into:
- Familiarity with the basic Docker & Kubernetes concepts
- Ability to derive a technical solution from abstract requirements
- Understanding of how to run a distributed system in production

#### Task 1: Dockerize the application
To run the application on Kubernetes, it needs to be containerized.
Adapt the project so that it produces a Docker image of the application.

#### Task 2: Write Kubernetes manifests
Prepare manifests to run the application on Kubernetes.

In the end the deployment should fulfill the following requirements:
- Application updates should be possible with zero downtime
- Data is persisted across application restarts
- Configuration, e.g. database credentials or the header authentication secret are not baked into the container

#### Task 3: What's missing?
Describe how you would continue from here to make this deployment fully production ready.



# access to swagger UI

http://localhost:8080/swagger-ui.html

# run in docker
create image - docker build -t manoj/challenge-spring-boot-docker . 
run container - docker run -p 8080:8080 manoj/challenge-spring-boot-docker

# Unit and integration test

The test cases contains test on Mockito, SpringBootTest,DataJpaTest and Controller test


