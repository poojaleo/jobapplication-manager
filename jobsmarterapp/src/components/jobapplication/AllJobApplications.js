import React from "react";
import {Button, Table} from "reactstrap";
import "./AllJobApplications.css";
import "react-datepicker/dist/react-datepicker.css";
import "react-datepicker/dist/react-datepicker.css";
import JobApplicationNavbar from "../NavBar/JobApplicationNavbar";
import AuthService from "../services/AuthService";
import CreateJobApplicationModal from "./CreateJobApplicationModal";
import './AllJobApplications.css';
import UpdateJobApplicationModal from "./UpdateJobApplicationModal";

let usernameToPass = "";
const baseUrl = "https://x9zyk5z39b.execute-api.us-west-2.amazonaws.com/jobtracker";

class AllJobApplications extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            username: '',
            applicationLoaded: false,
            isCreate: true,
            allQuestions: [],
            selectedQuestions: [],
            allJobApplications : [],
            createModal : false,
            updateModal: false,
            specificJobApplication: {"username":"","applicationId":"","jobTitle":"",
                "company":"","location":"","status":"", "nextReminder":"", "jobUrlLink": "",
                "notes": "" ,"questionsList":[]},
            emptyJobApplication: {"username":"","applicationId":"","jobTitle":"",
                "company":"","location":"","status":"", "nextReminder":"", "jobUrlLink": "",
                "notes": "" ,"questionsList":[""]},
            questionsList: {"username":"","questionId":"","question":"",
                "answer":"","needsWork":false, "tags": [""]}
        }

        usernameToPass = this.state.username;

        this.clickOnCreateApplication = this.clickOnCreateApplication.bind(this);
        this.onApplicationUpdateOrCreate = this.onApplicationUpdateOrCreate.bind(this);
        this.componentDidMount = this.componentDidMount.bind(this);
        this.openUpdateApplicationModal = this.openUpdateApplicationModal.bind(this);
        this.updateQuestionsList = this.updateQuestionsList.bind(this);
       /*
        this.createApplication = this.createApplication.bind(this);
        this.handleQuestionChangeAdded = this.handleQuestionChangeAdded.bind(this);
        this.handleQuestionChangeRemove = this.handleQuestionChangeRemove.bind(this);
        */
    }

    async componentDidMount() {
        const user = AuthService.getCurrentUsername();
        this.setState({username : user});

        console.log("Getting applications")
        const applicationsResponse = await fetch(`${baseUrl}/users/${user}/jobapplications`);
        const applicationsBody = await applicationsResponse.json();

        const questionsResponse = await fetch(`${baseUrl}/users/${user}/questions`);
        const questionsBody = await questionsResponse.json();
        this.setState({allJobApplications : applicationsBody.jobApplicationList, allQuestions: questionsBody.questions});
    }

    clickOnCreateApplication(event) {
        event.preventDefault();
        const application = this.state.emptyJobApplication;
        this.setState({isCreate: true, applicationLoaded: false, specificJobApplication : application,
            createModal: true});
    }

    openUpdateApplicationModal(event, applicationId, jobTitle, company, location, status, nextReminder, jobUrlLink, notes, questionsList) {
        event.preventDefault();
        let itemChange = {...this.state.specificJobApplication};
        itemChange["username"] = AuthService.getCurrentUsername();
        itemChange["applicationId"] = applicationId;
        itemChange["jobTitle"] = jobTitle;
        itemChange["company"] = company;
        itemChange["location"] = location;
        itemChange["status"] = status;
        itemChange["nextReminder"] = nextReminder;
        itemChange["jobUrlLink"] = jobUrlLink;
        itemChange["notes"] = notes;
        itemChange["questionsList"] = questionsList;
        if(itemChange["questionsList"] == undefined) {
            itemChange["questionsList"] = new Array();
        }

        this.setState({specificJobApplication : itemChange, applicationLoaded: true, isCreate: false})

        let fullQuestionsList = this.state.allQuestions;
        let questionsToKeepOnlyId = itemChange["questionsList"];

        let newList = fullQuestionsList.filter(ques => {
            return questionsToKeepOnlyId.includes(ques.questionId);
        })

        this.setState({selectedQuestions : newList});
        console.log(this.state.specificJobApplication);
        console.log(this.state.selectedQuestions);
        this.setState({updateModal : true})
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


    async updateQuestionsList() {
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
        await console.log(this.state.specificJobApplication);
    }


    async deleteApplication(id) {
        console.log("Deleting Application....")


        const response = await fetch(`${baseUrl}/users/${this.state.username}/jobapplications/${id}`, {
            method: 'DELETE'
        });

        const content = await response.json();
        console.log(content);
        window.location.reload();
    }

    render() {

        const allJobApplication = this.state.allJobApplications;
       /* const allQuestions = this.state.allQuestions;*/
        /*let questionsList = allQuestions?.map(question => {
            return (
                <div>
                    <input type={"checkbox"} id={question.questionId} key={question.questionId}/>
                    <label form={question.questionId}>{question.question}</label>
                </div>
            )
        })*/

        let jobApplicationRows = allJobApplication?.map((application) => {
                return (
                    <tr className={"jobRows"} key={application.applicationId}>
                        <td>{application.jobTitle}</td>
                        <td>{application.company}</td>
                        <td>{application.location}</td>
                        <td>{application.status}</td>
                        <td>{application.nextReminder}</td>
                        <td><Button className={"viewButton"} size={"sm"}
                                    onClick={(event) =>
                                        this.openUpdateApplicationModal(event, application["applicationId"],
                                            application["jobTitle"], application["company"], application["location"],
                                            application["status"], application["nextReminder"], application["jobUrlLink"],
                                            application["notes"], application["questionsList"])}>View Application</Button></td>
                        <td><Button className={"deleteButton"} size={"sm"} color={"danger"} onClick={() => this.deleteApplication(application.applicationId)}>Delete Application</Button></td>
                    </tr>
                )
        })

        return (
            <div className={"jobs"}>
                <JobApplicationNavbar />
                    <div className={"jobsCreate"}>
                            <div className={"createApp"}>
                                <Button className={"createButton but"} size={"lg"}
                                        onClick={event => this.clickOnCreateApplication(event)}>Create Application</Button>
                            </div>

                        <div className={"jobApplicationsTable"}>
                            <Table className={"displayTable px-4"} >
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
                {CreateJobApplicationModal(this.state.createModal,
                    ()=>this.setState({createModal : false}), this.state.specificJobApplication.jobTitle,
                    this.state.specificJobApplication.company, this.state.specificJobApplication.location,
                    this.state.specificJobApplication.status, this.state.specificJobApplication.nextReminder,
                    this.state.specificJobApplication.jobUrlLink, this.state.specificJobApplication.notes)}
                {UpdateJobApplicationModal(this.state.updateModal, ()=>this.setState({updateModal : false}),
                    this.state.specificJobApplication.applicationId, this.state.specificJobApplication.jobTitle,
                    this.state.specificJobApplication.company, this.state.specificJobApplication.location,
                    this.state.specificJobApplication.status, this.state.specificJobApplication.nextReminder,
                    this.state.specificJobApplication.jobUrlLink, this.state.specificJobApplication.notes,
                    this.state.specificJobApplication.questionsList, this.state.allQuestions,
                    this.state.selectedQuestions)}
            </div>
        )
    }

}

export default AllJobApplications;

