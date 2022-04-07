# JobSmarter Reflection

## Instructions

1. *Create a new folder within this current folder, and name the new folder
   after your team name*
2. *If you prefer keeping this reflection private to your team, you may change
   the permissions on your folder so that only your teammates + unit instructors
   can view the contents*
3. *Create a copy of this reflection template, and put it in your newly created
   folder *

*Please answer at least one question from each section below. Answers here will
likely be inspiration for the “lessons learned” section of your project
presentation at the end of the unit.*

## Design

* How closely did you follow your design document after the review was complete?
  Did your implementation need to change based on what you learned once you were
  underway?

    We followed about 80% of our design. 
    We changed: 
* As we worked we decided that the attribute
createdAt for JobApplication was unnecessary
* We decided that returning an empty list instead of throwing an 
exception in the "getall" features better met our needs
* We added password validation to get requests for users
* We added a check for duplicate questions, and duplicate applicationIds to our design
* We adjusted the scope of our project to exclude session management, password encryption 
and uploading and saving documents.

## Project

* How did you handle the ambiguity of defining your own project and user
  stories? What strategies did you use to decide on concrete work to do to
  satisfy your requirements?
* How did you deal with getting stuck on a problem? What strategies did you
  employ to get yourself unblocked?
* Did any of your commitments you made in your team charter help navigate a
  difficult decision, situation, or collaboration challenge? How?

We were very clear on deciding our priorities of our features. We knew that we
wanted the JobApplication feature to be robust. This helped us prioritize as
challenges arose. We communicated frequently and early when we found blockers.
We were generous in the help we offered each other. We asked for and offered help
regularly. This prevented us from having surprises at the end of a sprint. 
We were flexible in our timing, adjusting when and how long we met to work on certain
collaborative parts of the project. This was helpful to us as we are in a learning
mindset, but might not be realistic in the future in a work-context. 

We read each other's code frequently. This helped us to develop consistency, and learn
from each other.

We were generally successful estimating small tasks and time to complete task in the backend work, 
however this was more challenging for us when completing the frontend as we did not have as good 
of a sense of how complex certain tasks would be. 


## Scrum

* What did you find to be the most valuable part of daily stand-ups? Is there
  anything you would want to do differently at stand-up to make it more useful
  to you?
* Did you over or underestimate the work you could complete during your sprints?
  What have you learned that will help you better estimate work next time?

The communication of giving daily updates was an integral part of our success. 

In Sprint 2 our initial estimates were less accurate due to still have guided projects
that furthered our understanding and increased our ability to accurately estimate moving
forward. When this happened, we were able to more clearly define the tasks and added to our
backlog instead of dropping what we had planned for the Sprint, effectively following the 
work-flow described in Sprint 2, Module 1 materials. 

We were diligent in our use of Trello, which helped us stay on the same page. We also used
Trello to share some of our documents, which helped organize our resources. We made 
excellent use of checklists in Trello for our backend tasks which helped us accurately
estimate our times. Being less familiar with frontend, we were not able to develop as
useful checklists for that work leading to less accurate time estimates. 

At the beginning of the project we developed guidelines for our workflow and definition of tasks
which helped us to achieve a workflow with near zero merge conflicts. 

We had not anticipated that the lambda functions on aws where we had been testing our code
would behave differently than the API gateway. When we realized this, it required going back
to the code to accommodate these differences, extending the time spent on backend.

## Looking ahead

* If you were to start this unit over again, what would you do differently? How
  do you see yourself applying that to your work in later units, the capstone,
  or your internship?

While we are proud of the clarity of our backend code, if we were to start again we 
would want to first establish and then follow clean code guidelines to better organize
our frontend code. Frontend was a big learning area for us as a team. 

Given the timeframe of the project, we would have divided our time differently between
documentation, backend and frontend. The time we had reserved for frontend was most
cramped.

Going forward we want to learn more about java design patterns.

We want to learn more about how to improve the latency we experience in our project
currently. 
