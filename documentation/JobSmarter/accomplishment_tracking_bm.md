[Beatrice] - [JobSmarter] Accomplishment Tracking
Each team member should have their own version of this document.

Background
It's a great habit to record accomplishments and progress throughout your SDE career. It's useful to reflect on what you've worked on in the past and comes in handy during performance reviews and promotion cycles.

Instructions
Save a copy of this document in your “private” folder.

Using the below template, keep track of what you’ve worked on each week during the unit. 1-3 bullets under each section for each week should suffice. This should only take 5 - 10 minutes of reflection each week.

As you track your work, think about how it relates to the SDE fundamental skills laid out in the syllabus and how you are practicing them.

Converts a design into code and delivers it using best practices
Writes secure, testable, maintainable code
Understands when to use (or not) a broad range of data structures and algorithms
Creates unit tests that thoroughly test functionality
Troubleshoots by debugging and reviewing errors, logfiles, and metrics
Contributes to planning and design
Escalates when projects hit roadblocks and risks
The important work samples don’t only include the things you authored, but should include things like key CRs you reviewed that you are proud of as well!

You will submit your completed Accomplishment Tracking Document to your instructors by the end of the unit.

Week 1
Goals: Establish clear vision for design of project

Activity: Brainstorming, committing to a shared vision, outlining project structure
Developed our Team Charter

Important Docs, Commits, or Code Reviews: Created SDs for JobApplication Activities, 
wrote descriptions of the endpoints. Created mock-up of user flow through the front end (rough sketch)
Updated Rubric document to include links to submitted documents for  Sprint 1. 

Things learned: Following brainstorming it was important to identify what would be our "need to have" priorities, 
and what functionality would be "nice to have". Thinking through the class architecture, planning exceptions,
and sequence diagrams takes a lot of time. 

Week 2
Goals: 80% of backend, lambda functions, API Gateways set up

Activity: Worked primarily on the request, response and activity classes for JobApplications as well
as the unit tests for JobApplications. 

Important Docs, Commits, or Code Reviews: JobApplication POJO with annotations, JobApplicationDao, and the
request, response and activity classes for JobApplications as well
as the unit tests for JobApplications.

Things learned: It quickly became clear to me the amount of work needed to set-up our project 
and to account for this time in the future. Building out the folder structure, and establishing a
shared gitHub collaboration workflow were really important to our work.  Our pre-planning meant that 
I could annotate the JobApplications POJO before our tables were created because we had clearly identified
attribute names in our planning. I knew that whoever made the yaml file would be working off of the same document.

Week 3
Goals: Minimal frontend up running and functional

Activity: Adjusted how our code handles empty strings, null values in response to differences in 
lambda functions and API gateway. 

Important Docs, Commits, or Code Reviews: Updates to Update and GetAll for JobApplications. 
Questions frontend: ability to get, get all, create and update questions for string attributes.

Things learned: Testing out each of the connections between additional layers of technology is crucial.
Our API gateways behaved slightly differently than lambda, and through testing we were able to establish
how our backend needed to change to accommodate these differences.
Learning React quickly has been both challenging and rewarding. 

Week 4
Goals: Host project, increase functionality of frontend, prepare presentation 

Activity: Collaborative troubleshooting for frontend issues, researching handling CORS errors,
hosting, presentation practice.

Important Docs, Commits, or Code Reviews:  Completed  questions frontend to include deleting questions
and updating non-string attributes (tags, needsWork). Collaboratively planned and practiced our presentation.

Things learned: Increased my ability to debug frontend to identify what is going wrong, I learned 
a lot from my teammates on this front. Going from our locally hosted version of our project to hosting 
on AWS Amplify or Netlify was a big challenge for our group. Planning our presentation was also an 
opportunity to celebrate what we were proud of. 