
## Technical Learning Objectives

### API Design

**Design an API to meet the needs of your application.** Provide a link to the
definition for your endpoints (can be code/configuration, or can be
documentation). List one thing that your team learned about designing a good
API.

*Endpoint definition location:*   https://7ehv0ovn6e.execute-api.us-west-2.amazonaws.com/prod/

*Swagger Documentation:*

*What we learned:*
* Creating a Swagger API documentation before creating the API on the Amazon API Gateway 
ensured that we had a clear understanding on how the API needs to be designed 
and what the user experience should be
* Having clear documentation also ensured that our variable names are correct, 
our request body and response body are structured as per our code in the backend. 
* The aim was to keep to simple and easy to use.
    

**Develop a service endpoint definition that uses complex inputs and outputs.**
Select one of your endpoints and list the operation’s input and output objects.
Under each, list its attributes.

**Application Update Request Endpoint:** https://7ehv0ovn6e.execute-api.us-west-2.amazonaws.com/prod/users/{username}/jobapplications/{applicationId}


*Input object(s):*

* jobTitle: string
* company: string
* location: string
* status: string
* nextReminder: string
* notes: string
* jobUrlLink: string
* questionsList: array


*Output object(s):*

* username: string
* applicationId: string
* jobTitle: string
* company: string
* location: string
* status: string
* nextReminder: string
* notes: string
* jobUrlLink: string
* questionsList: array


**Given a user story that requires a user to provide values to a service
endpoint, design a service endpoint including inputs, outputs, and errors.**
Select one of your endpoints that accepts input values from a client. List the
error cases you identified and how the service responds in each case. Provide at
most 3 error cases.

| **Update Question Endpoint:** | https://7ehv0ovn6e.execute-api.us-west-2.amazonaws.com/prod/users/{username}/questions/{questionId} |
|-------------------------------|-----------------------------------------------------------------------------------------------------|
| **Error case**                | **Service response**                                                                                |
| 400                           | Invalid parameters                                                                                  |
| 404                           | Question not found                                                                                  |
| 500                           | Internal Server Error                                                                               |

**Develop a service endpoint definition that uses query parameters to determine
how results are sorted or filtered.** List at least one endpoint that allows
sorting or filtering of results. Which attribute(s) can be sorted/filtered on?

*Endpoint:* https://7ehv0ovn6e.execute-api.us-west-2.amazonaws.com/prod/users/{username}?password=string 

*Attribute(s):* password

**Determine whether a given error condition should result in a client or server
exception.** List one client exception and one server exception that your
service code throws. List one condition in which this exception is thrown.

|                       | **Exception**                  | **One case in which it is thrown**                                                            |
|---	                |--------------------------------|-----------------------------------------------------------------------------------------------|
|**Client exception:**  | QuestionAlreadyExistsException | If user sends a create question request for the question that already exists in the database	 |
|**Service exception:** | Internal Server Exception	     | When unable to reach the service	                                                             |

### DynamoDB Table Design

**Decompose a given set of use cases into a set of DynamoDB tables that provides
efficient data access.** List the DynamoDB tables in your project:


## Soft(er) Outcomes

**Learn a new technology.** For each team member, list something new that that
team member learned:

| Team Member | Something new the team member learned |   
|-------------|---------------------------------------|
| Pooja Patel | API Gateway, React,                   |   
|             |                                       |     
|             |                                       |     
|             |                                       |     

**Identify keywords to research to accomplish a technical goal | Use sources
like sage and stack overflow to solve issues encountered while programming.**
Give an example of a search term that your team might have used to find an
answer to a technical question/obstacle that your team ran into. List the
resource that you found that was helpful.

**Search terms:**      
**Helpful resource:** Java Documentation, Stack overflow, 

**Find material online to learn new technical topics.** List one resource that
your team found on your own that helped you on your project.

**Topic:**
Reactstrap
**Resource:**
https://reactstrap.github.io/?path=/story/home-installation--page

**Share what was worked on yesterday, the plan for today, and any blockers as a
part of a scrum standup.** In one or two sentences, describe what your team
found to be the most useful outcome from holding daily standups.

1. Roles and responsibility of each individual was clearly defined to help us achieve the task
2. Brainstorming on blockers as a group

