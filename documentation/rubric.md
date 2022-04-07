# JobSmarter Project Rubric

## Background

*This captures the expectations that we have for your team during the unit.
This is how we will evaluate whether you have demonstrated these expectations.*

## Instructions

*As a team, complete this rubric (everything except for the Appendix) by
answering the questions below. Each question should usually only require one or
two sentences, so please be brief. The remainder of expectations will be
evaluated by instructors, so you don’t need to write anything in the Appendix.
We want you to see the full set of expectations for transparency’s sake.*

## Deliverables

*Provide links to the following project deliverables:*

|Deliverable                                                      |Due Date                  |Date Completed |URL                               |
|---                                                              |---                       |---            |---                               |
|Team name                                                        |Sprint 1 Module 1         | 3/7             |name:  JobSmarter                           |
|[Design Document - problem statement](design_document.md)        |Sprint 1 Module 2         | 3/8              |    https://github.com/BloomTechBackend/bd-team-project-jobsmarter/blob/main/project_documents/design_document.md                              |
|[Team Charter](team_charter.md)                                  |Sprint 1 Module 3         | 3/9           |   https://github.com/BloomTechBackend/bd-team-project-jobsmarter/blob/main/project_documents/team_charter.md                               |
|[Design Document](design_document.md)                            |Sprint 1 Friday by 5pm    | 3/11              |  https://github.com/BloomTechBackend/bd-team-project-jobsmarter/blob/main/project_documents/design_document.md                                |
|Project Completion (Feature Complete)                            |Sprint 3 Friday by 5pm    | 4/1              |     https://github.com/BloomTechBackend/bd-team-project-jobsmarter                             |
|[Team Reflection](JobSmarter/JobSmarterReflection.md)                                 |Sprint 4 Wednesday by 5PM | 4/1               |  https://github.com/BloomTechBackend/bd-team-project-jobsmarter/blob/main/project_documents/JobSmarter/JobSmarterReflection.md                                |
|[Accomplishment Tracking Pooja](JobSmarter/accomplishment_tracking_pp.md) |Sprint 4 Wednesday by 5PM | 4/6              | https://github.com/BloomTechBackend/bd-team-project-jobsmarter/blob/main/project_documents/JobSmarter/accomplishment_tracking_pp.md                              |
|[Accomplishment Tracking George](JobSmarter/accomplishment_tracking_gp.md) |Sprint 4 Wednesday by 5PM | 4/6               | https://github.com/BloomTechBackend/bd-team-project-jobsmarter/blob/main/project_documents/JobSmarter/accomplishment_tracking_gp.md                                 |
|[Accomplishment Tracking Beatrice](JobSmarter/accomplishment_tracking_bm.md) |Sprint 4 Wednesday by 5PM | 4/6              | https://github.com/BloomTechBackend/bd-team-project-jobsmarter/blob/main/project_documents/JobSmarter/accomplishment_tracking_bm.md                                 |
|Self Reflection (person 1)                        |Sprint 4 Wednesday by 5PM |               |n/a (will be submitted via Canvas - "Wrap-up" section) |
|Self Reflection (person 2)                        |Sprint 4 Wednesday by 5PM |               |n/a (will be submitted via Canvas - "Wrap-up" section) |
|Self Reflection (person 3)                        |Sprint 4 Wednesday by 5PM |               |n/a (will be submitted via Canvas - "Wrap-up" section) |
|Self Reflection (person 4)                        |Sprint 4 Wednesday by 5PM |               |n/a (will be submitted via Canvas - "Wrap-up" section) |

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

*Endpoint:* https://7ehv0ovn6e.execute-api.us-west-2.amazonaws.com/prod/users/{username}?/questions?onlyNeedsWork=true

*Attribute(s):* This optional query parameter "onlyNeedsWork" allows the user to view only those
questions that they have indicated still need work, instead of all questions. Using
onlyNeedsWork=true, filters the results to only include questions that are still in progress.

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

### 1. `Users`

```
username // partition key, string
firstname // string
lastname // string
emailAddress // string
password // string
```

### 2. `JobApplications`

```
username // partition key, string
applicationId // sort key, string
jobTitle // string
company // string
location // string
status // string
nextReminder // string
notes // string
jobUrlLink // string
questionsList // stringList
```

### 3. `Questions`

```
username // partition key, string
questionId // sort key, string
question // string
answer // string
tag // stringList
needsWork // boolean
```

**Design a DynamoDB table key schema that allows items to be uniquely
identified.** Describe the primary key structure for your DynamoDB table with
the most interesting primary key. In a sentence or two, explain your choice of
partition/sort key(s).

1. Our JobApplication table has a partition key of username and a sort key of applicationId.
   We did this in order to associate the jobApplication with the user that created it
  while also ensuring that each JobApplication was uniquely identified.

**Design the attributes of a DynamoDB table given a set of use cases.** List a
DynamoDB table with at least 3 attributes. List one relevant use case that uses
the attribute. In one sentence, describe why the attribute is included.

**Table name:** Questions

**Attributes:** username, questionId, question, answer, needsWork, tags

|Attribute name |(One) relevant use case |attribute purpose |
|---            |---                     |---               |
|username // partition key, string    |  User is able to view all of the questions associated with their username   |   Associates a created question with a user.                |
|questionId // sort key, string     |  User is able to view a specific question associated with their username and this questionId                      |  Creates a unique id for this question             |
|question // string   | The user is able to enter a question that they would like to practice an answer for either now or in the future. |  Without this attribute it would not be clear to the user which question they were answering.            |
|answer // string   |  The user is able to enter and update their answer to the question      |  This attribute allows the user to update their answer as they practice and revise.                |
|needsWork // boolean | The user is able to indicate whether they feel their answer still needs work   |  User is both able to sort their questions by this attribute, and filter to view only those that are completed (needsWork: false) or those that they feel are still in progress (needsWork is true).             |
|tags // stringList   |  The user is able to record any helpful tags for their question    | As this service grows, we want to be able to sort or filter by tags. This is currently out of scope, but included with an eye towards the future.                  |



### DynamoDB Indexes

**Design a GSI key schema and attribute projection that optimizes queries not
supported by a provided DynamoDB table.** In one or two sentences, explain why
you created one of the GSIs that your project uses, including one use case that
uses that index.

*When the user views all of his Job Applications, we wanted to avoid showing them their Job Applications sorted randomly
each time. Therefore, we utilized GSI key schema to show all Job Applications belonging to a particular username 
in a sorted order by Status. The same logic was applied to show all Questions sorted by NeedsWork.

**Table name:**

`JobApplications`
`Questions`

**Table keys:**

`JobApplications`

```
username // partition key, string
applicationId // sort key, string
```

`Questions`

```
username // partition key, string
questionId // sort key, string
```

**GSI keys:**

`JobApplications`
```
username // partition key, string
status // sort key, string
```

`Questions`

```
username // partition key, string
question // sort key, string
```

```
username // partition key, string
needsWork // sort key, string
```

**Use case for GSI:**

**Implement functionality that uses query() to retrieve items from a provided
DynamoDB's GSI.** List one of your use cases that uses `query()` on a GSI.

**Table name:**

`Questions`

**Use case for `query()` on GDI:**

*In order to avoid duplicate Question items being created with the same exact questions, we designed a GSI key schema
to be able to obtain all Questions belonging to a particular username. If the Question item being created contains
a question that already exists for the username, it will throw a QuestionAlreadyExistsException.

## Soft(er) Outcomes

**Learn a new technology.** For each team member, list something new that that
team member learned:

| Team Member | Something new the team member learned                              |   
|-------------|--------------------------------------------------------------------|
| george      | HTML, async functions, display flex, lambda logger, mvn repository |   
|  Pooja Patel | API Gateway, React,            |                                                                    |     
|    Beatrice    | React, API Gateway creation, collaborative use of github                                                                    |     
|             |                                                                    |     

**Identify key words to research to accomplish a technical goal | Use sources
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