**Design a DynamoDB table key schema that allows items to be uniquely
identified.** Describe the primary key structure for your DynamoDB table with
the most interesting primary key. In a sentence or two, explain your choice of
partition/sort key(s).

1. Our JobApplication table has a partition key of username and a sort key of applicationId.
We did this in order to 

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
|needsWork // boolean | The user is able to indicate whether they feel their answer still needs work   |  User is able to sort their questions by this attribute to view first those that are completed (needsWork: false) or those that they feel are still in progress (needsWork is true).             |
|tags // stringList   |  The user is able to record any helpful tags for their question    | As this service grows, we want to be able to sort or filter by tags. This is currently out of scope, but included with an eye towards the future.                  |


|Team Member |Something new the team member learned |   
|---   |---                                   |
|  Beatrice    |    React, API Design                                  |   
|      |                                      |     
|      |                                      |     
|      |                                      |