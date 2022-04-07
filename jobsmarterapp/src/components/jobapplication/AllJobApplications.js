import React, { useState } from "react";
import {Button, Container, Form, Table, Input} from "reactstrap";
import "./AllJobApplications.css";
import "react-datepicker/dist/react-datepicker.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import moment from "moment";
import {ErrorBoundary} from 'react-error-boundary'
import {useNavigate} from "react-router-dom";
import {Multiselect} from "multiselect-react-dropdown";
import JobApplicationNavbar from "../NavBar/JobApplicationNavbar";

let usernameToPass = "";
// const baseUrl = "https://7ehv0ovn6e.execute-api.us-west-2.amazonaws.com/prod";

function QuestionClick(name) {

    let navigate = useNavigate();
    const routeChange = (event) => {
        event.preventDefault();
        navigate('/questions', {
            state:{username:usernameToPass}
        });
    }
    return <button onClick={routeChange} className={"questionsButton"}>Interview Preparation</button>
}

class AllJobApplications extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            username: this.props.username,
            applicationLoaded: false,
            isCreate: true,
            allQuestions: [],
            selectedQuestions: [],
            allJobApplications : [],
            specificJobApplication: {"username":this.props.username,"applicationId":"","jobTitle":"",
                "company":"","location":"","status":"", "nextReminder":"", "jobUrlLink": "",
                "notes": "" ,"questionsList":[]},
            emptyJobApplication: {"username":this.props.username,"applicationId":"","jobTitle":"",
                "company":"","location":"","status":"", "nextReminder":"", "jobUrlLink": "",
                "notes": "" ,"questionsList":[""]},
            questionsList: {"username":"","questionId":"","question":"",
                "answer":"","needsWork":false, "tags": [""]}
        }

        usernameToPass = this.state.username;

        this.viewApplication = this.viewApplication.bind(this);
        this.clickOnCreateApplication = this.clickOnCreateApplication.bind(this);
        this.onApplicationUpdateOrCreate = this.onApplicationUpdateOrCreate.bind(this);
        this.createApplication = this.createApplication.bind(this);
        this.handleDateChange = this.handleDateChange.bind(this);
        this.handleQuestionChangeAdded = this.handleQuestionChangeAdded.bind(this);
        this.handleQuestionChangeRemove = this.handleQuestionChangeRemove.bind(this);
    }

    aErrorHandler({error}) {
        return (
            <div role="alert">
                <p>An error occurred:</p>
                <pre>{error.message}</pre>
            </div>
        )
    }

    async componentDidMount() {
        console.log("Getting applications")
        const applicationsResponse = await fetch(`/users/${this.state.username}/jobapplications`);
        const applicationsBody = await applicationsResponse.json();

        const questionsResponse = await fetch(`/users/${this.state.username}/questions`);
        const questionsBody = await questionsResponse.json();
        this.setState({allJobApplications : applicationsBody.jobApplicationList, allQuestions: questionsBody.questions});
    }

    clickOnCreateApplication() {
        const application = this.state.emptyJobApplication;
        this.setState({isCreate: true, applicationLoaded: false, specificJobApplication : application});
    }

    async onApplicationUpdateOrCreate(event) {
        //onChange={(date:Date) => setStartDate(date)}
        event.preventDefault();
        const value = event.target.value;
        const name = event.target.name;
        let itemChange = {...this.state.specificJobApplication};
        itemChange[name] = value;
        this.setState({specificJobApplication : itemChange});

    }

    async handleDateChange(date) {
        let itemChange = {...this.state.specificJobApplication};
        itemChange["nextReminder"] = date;
        this.setState({specificJobApplication : itemChange});
    }

    async handleQuestionChangeAdded(selectedList, selectedItem) {
        let itemChange = {...this.state.specificJobApplication};
        itemChange["questionsList"].push(selectedItem["questionId"]);
        this.setState({specificJobApplication : itemChange});
    }

    async handleQuestionChangeRemove(selectedList, removedItem) {
        let itemChange = {...this.state.specificJobApplication};
        let list = itemChange["questionsList"];
        let listAfterRemove = list.filter(element => {
            return element != removedItem.questionId;
        });
        itemChange["questionsList"] = listAfterRemove;
        this.setState({specificJobApplication : itemChange});
    }


    async viewApplication(id) {

        const applicationResponse = await fetch(`/users/${this.state.username}/jobapplications/${id}`);
        const applicationBody = await applicationResponse.json();
        this.setState({specificJobApplication : applicationBody.jobApplicationModel, applicationLoaded: true, isCreate: false})
        let itemChange = {...this.state.specificJobApplication};
        if(itemChange["questionsList"] == undefined) {
            itemChange["questionsList"] = new Array();
        }

        this.setState({specificJobApplication : itemChange})

        let fullQuestionsList = this.state.allQuestions;
        let questionsToKeepOnlyId = itemChange["questionsList"];

        let newList = fullQuestionsList.filter(ques => {
            return questionsToKeepOnlyId.includes(ques.questionId);
        })

        this.setState({selectedQuestions : newList});

    }

    async createApplication(event) {
        event.preventDefault();
        console.log("Creating Application....")

        const applicationToCreate = {
            "jobTitle": this.state.specificJobApplication.jobTitle,
            "company": this.state.specificJobApplication.company,
            "location": this.state.specificJobApplication.location,
            "status": this.state.specificJobApplication.status,
            "nextReminder": this.state.specificJobApplication.nextReminder,
            "jobUrlLink": this.state.specificJobApplication.jobUrlLink,
            "notes": this.state.specificJobApplication.notes
        }

        const response = await fetch(`/users/${this.state.username}/jobapplications/`, {
            method: 'POST',
            headers : {
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify(applicationToCreate)
        });

        const content = await response.json();
        console.log(content);
        window.location.reload(false, {
            state:{username:this.state.username}
        });
    }

    async updateApplication(event) {
        event.preventDefault();
        console.log("Updating Application....")

        const applicationToUpdate = {
            "jobTitle": this.state.specificJobApplication.jobTitle,
            "company": this.state.specificJobApplication.company,
            "location": this.state.specificJobApplication.location,
            "status": this.state.specificJobApplication.status,
            "nextReminder": this.state.specificJobApplication.nextReminder,
            "jobUrlLink": this.state.specificJobApplication.jobUrlLink,
            "notes": this.state.specificJobApplication.notes,
            "questionsList": this.state.specificJobApplication.questionsList
        }

        const response = await fetch(`/users/${this.state.username}/jobapplications/${this.state.specificJobApplication.applicationId}`, {
            method: 'PUT',
            headers : {
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify(applicationToUpdate)
        });

        const content = await response.json();
        console.log(content);
        window.location.reload(false, {
            state:{username:this.state.username}
        });

    }

    async deleteApplication(event) {
        event.preventDefault();
        console.log("Deleting Application....")


        const response = await fetch(`/users/${this.state.username}/jobapplications/${this.state.specificJobApplication.applicationId}`, {
            method: 'DELETE'
        });

        const content = await response.json();
        console.log(content);
        window.location.reload(false, {
            state:{username:this.state.username}
        });
    }

    render() {
        const username = this.props.username;
        const allJobApplication = this.state.allJobApplications;
        const allQuestions = this.state.allQuestions;
        const selected = Date.parse(moment(this.state.specificJobApplication.nextReminder, 'MM-dd-yyyy').toISOString());
        let questionsList = allQuestions?.map(question => {
            return (
                <div>
                    <input type={"checkbox"} id={question.questionId} key={question.questionId}/>
                    <label form={question.questionId}>{question.question}</label>
                </div>
            )
        })

        let jobApplicationRows = allJobApplication?.map((application) => {
                return (
                    <tr className={"jobRows"} key={application.applicationId}>
                        <td>{application.jobTitle}</td>
                        <td>{application.company}</td>
                        <td>{application.location}</td>
                        <td>{application.status}</td>
                        <td>{application.nextReminder}</td>
                        <td><Button className={"viewButton"} size={"sm"} onClick={() => this.viewApplication(application.applicationId)}>View Application</Button></td>
                    </tr>
                )
        })

        let specificJobApplication = (application) => {
            if(!this.state.isCreate) {
                return (
                    <Form className={"appForm"}>
                        <div className={"applicationForm"}>
                            <text>ApplicationId</text>
                            <Input type={"text"} name={"applicationId"} value={application.applicationId}/>
                            <text>Job Title</text>
                            <Input type={"text"} name={"jobTitle"} defaultValue={application.jobTitle} required={true}
                                   onChange={this.onApplicationUpdateOrCreate}/>
                            <text>Company</text>
                            <Input type={"text"} name={"company"} defaultValue={application.company} required={true}
                                   onChange={this.onApplicationUpdateOrCreate}/>
                            <text>Location</text>
                            <Input type={"text"} name={"location"} defaultValue={application.location} required={true}
                                   onChange={this.onApplicationUpdateOrCreate}/>

                        </div>
                        <div className={"applicationForm"}>
                            <text>Status</text>
                            <Input type={"select"} name={"status"} defaultValue={application.status} required={true}
                                   onChange={this.onApplicationUpdateOrCreate}>
                                <option>INTERESTED</option>
                                <option>APPLIED</option>
                                <option>CONTACTED</option>
                                <option>INTERVIEW_SCHEDULED</option>
                                <option>OFFER</option>
                                <option>NOT_MOVING_FORWARD</option>
                            </Input>
                            <text>Reminder</text>
                            {/*<Input type={"text"} name={"nextReminder"} defaultValue={application.nextReminder}
                                   onChange={this.onApplicationUpdateOrCreate}/>*/}
                            <DatePicker dateFormat="MM-dd-yyyy" selected = {selected} onChange={this.handleDateChange} />
                            <text>Job URL</text>
                            <Input type={"text"} name={"jobUrlLink"} defaultValue={application.jobUrlLink}
                                   onChange={this.onApplicationUpdateOrCreate}/>
                            <text>Notes</text>
                            <Input type={"text"} name={"notes"} defaultValue={application.notes}
                                   onChange={this.onApplicationUpdateOrCreate}/>
                        </div>
                        <div className={"questionForm"}>

                            <text>Questions</text>
                            {/*<Input type={"text"} name={"questionsList"} defaultValue={application.questionsList}
                                   onChange={this.onApplicationUpdateOrCreate}/>*/}
                            <div className={"checkboxQuestions"}>
                                <Multiselect options={this.state.allQuestions} displayValue="question"
                                             selectedValues={this.state.selectedQuestions} showCheckbox={true} onSelect={this.handleQuestionChangeAdded}
                                             onRemove={this.handleQuestionChangeRemove}/>
                            </div>
                            <div className={"updateDeleteButton"}>
                                <Button className={"createButton but"} size={"md"}  onClick={(event) => this.updateApplication(event)}>UPDATE</Button>{' '}
                                <Button className={"createButton but"} size={"md"}  onClick={(event) => this.deleteApplication(event)}>DELETE</Button>
                            </div>
                        </div>
                    </Form>
                )
            }

            return (
                <Form className={"appForm"} >
                    <div className={"applicationForm"}>
                        <text>Job Title</text>
                        <Input type={"text"} name={"jobTitle"} defaultValue={application.jobTitle} required={true}
                               onChange={this.onApplicationUpdateOrCreate} />
                        <text>Company</text>
                        <Input type={"text"} name={"company"} defaultValue={application.company} required={true}
                            onChange={this.onApplicationUpdateOrCreate}/>
                        <text>Location</text>
                        <Input type={"text"} name={"location"} defaultValue={application.location} required={true}
                               onChange={this.onApplicationUpdateOrCreate}/>
                        <text>Status</text>
                        <Input type={"select"} name={"status"} defaultValue={application.status} required={true}
                               onChange={this.onApplicationUpdateOrCreate}>
                            <option>INTERESTED</option>
                            <option>APPLIED</option>
                            <option>CONTACTED</option>
                            <option>INTERVIEW_SCHEDULED</option>
                            <option>OFFER</option>
                            <option>NOT_MOVING_FORWARD</option>
                        </Input>
                    </div>
                    <div className={"applicationForm"}>
                        <text>Reminder</text>
                        {/*<Input type={"text"} name={"nextReminder"} defaultValue={application.nextReminder}
                               onChange={this.onApplicationUpdateOrCreate}/>*/}
                        <DatePicker dateFormat="MM-dd-yyyy" selected = {selected} onChange={this.handleDateChange} />

                        <text>Job URL</text>
                        <Input type={"text"} name={"jobUrlLink"} defaultValue={application.jobUrlLink}
                               onChange={this.onApplicationUpdateOrCreate}/>
                        <text>Notes</text>
                        <Input type={"text"} name={"notes"} defaultValue={application.notes}
                               onChange={this.onApplicationUpdateOrCreate}></Input>
                    </div>
                    <div className={"applicationForm createBtn"}>
                        <div>
                            <br />
                            <br />
                            <Button block size={"lg"} className={"createButton but"} onClick={(event) => this.createApplication(event)}>Create Application</Button>
                        </div>
                    </div>
                </Form>
            )
        }

        return (
            <div className={"jobs"}>
                <ErrorBoundary FallbackComponent={this.aErrorHandler}>
                <JobApplicationNavbar />

                    <div className={"jobsHeading"}>
                        <h4> Hi {username}</h4>
                    </div>
                    <div className={"jobsCreate"}>
                        <div className={"jobTopRow"}>
                            <div className={"questionNav"}>
                                <h3>Job Applications</h3>
                                <QuestionClick />
                            </div>
                            <div className={"createApp"}>
                                <Button className={"createButton but"} size={"lg"} onClick={() => this.clickOnCreateApplication()}>Create Application</Button>
                            </div>
                        </div>

                        <div className={"jobApplicationsTable"}>
                            <Table className={"displayTable"}  >
                                <thead >
                                <tr>
                                    <th width={"20%"}>Job Title</th>
                                    <th width={"15%"}>Company</th>
                                    <th width={"15%"}>Location</th>
                                    <th width={"15%"}>Status</th>
                                    <th width={"20%"}>Reminder</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                {jobApplicationRows}
                                </tbody>

                            </Table>
                        </div>
                </div>
                <div className={"jobForm"}>
                    {specificJobApplication(this.state.specificJobApplication)}
                </div>
                </ErrorBoundary>
            </div>
        )
    }

}

export default AllJobApplications;

