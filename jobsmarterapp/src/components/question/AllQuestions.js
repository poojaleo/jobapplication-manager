import React from "react";
import {Button, Table, Input} from "reactstrap";
import "./AllQuestions.css";
import QuestionNavbar from "../NavBar/QuestionNavbar";
import AuthService from "../services/AuthService";
import "../jobapplication/AllJobApplications.css"
import CreateQuestionModal from "./CreateQuestionModal";
import UpdateQuestionModal from "./UpdateQuestionModal";

const baseUrl = "https://x9zyk5z39b.execute-api.us-west-2.amazonaws.com/jobtracker";


class AllQuestions extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            username: "",
            questionLoaded: false,
            isCreate: true,
            createModal: false,
            updateModal: false,
            allQuestions: [],
            selectedTags: [],
            allTags:[{"tag":"technical"}, {"tag": "behavioral"}, {"tag":"coding"}, {"tag":"frontend"}, {"tag":"backend"}, {"tag":"oop"}, {"tag":"ml"}, {"tag":"datascience"}, {"tag":"theory"}, {"tag":"application"}],        
            specificQuestion: {"username":"","questionId":"","question":"",
                "answer":"","needsWork":"true", "tags": []},
            emptyQuestion: {"username":"","questionId":"","question":"",
            "answer":"","needsWork":"true", "tags": []},
        }

        this.openUpdateQuestionModal = this.openUpdateQuestionModal.bind(this);
        this.viewQuestionsNeedWork = this.viewQuestionsNeedWork.bind(this);
        this.clickOnCreateQuestion = this.clickOnCreateQuestion.bind(this);
        this.onQuestionCreateOrUpdate = this.onQuestionCreateOrUpdate.bind(this);
        this.handleTagChangeAdd = this.handleTagChangeAdd.bind(this)
        this.handleTagChangeRemove = this.handleTagChangeRemove.bind(this);
    }

    async componentDidMount() {
        const user = AuthService.getCurrentUsername();
        console.log("Getting questions")
        const questionsResponse = await fetch(`${baseUrl}/users/${user}/questions`);
        const questionsBody = await questionsResponse.json();
        this.setState({allQuestions : questionsBody.questions, username: user})
    }

    async viewAllQuestions(event) {
        console.log("Getting all questions...")
        const questionsResponse = await fetch(`${baseUrl}/users/${this.state.username}/questions`);
        const questionsBody = await questionsResponse.json();
        this.setState({allQuestions : questionsBody.questions})
    }

    async viewQuestionsNeedWork(event) {
        console.log("Getting needsWork questions..")
        const questionsResponse = await fetch(`${baseUrl}/users/${this.state.username}/questions?onlyNeedsWork=true`);
        const questionsBody = await questionsResponse.json();
        this.setState({allQuestions : questionsBody.questions})
    }

    clickOnCreateQuestion(event) {
        event.preventDefault();
        const question = this.state.emptyQuestion; 
        this.setState({isCreate: true, questionLoaded: false, specificQuestion : question, createModal : true});
    }

    async onQuestionCreateOrUpdate(event) {
        event.preventDefault();
        const value = event.target.value;
        const name = event.target.name;
        let itemChange = {...this.state.specificQuestion};
        itemChange[name] = value;
        this.setState({specificQuestion : itemChange});
    }

    async handleTagChangeAdd(selectedList, selectedItem) {
        let itemChange = {...this.state.specificQuestion};
        
        itemChange["tags"].push(selectedItem["tag"]);
        this.setState({specificQuestion : itemChange});
    }

    async handleTagChangeRemove(selectedList, removedItem) {
        let itemChange = {...this.state.specificQuestion};
        let list = itemChange["tags"];
        let listAfterRemove = list.filter(element=> {
            return element != removedItem.tag;
        });
        itemChange["tags"]= listAfterRemove;
        this.setState({specificQuestion : itemChange});

    }

    openUpdateQuestionModal(event, quesId, ques, ans, needWork, tags) {
        event.preventDefault();
        let itemChange = {...this.state.specificQuestion};
        itemChange["username"] = AuthService.getCurrentUsername();
        itemChange["questionId"] = quesId;
        itemChange["question"] = ques;
        itemChange["answer"] = ans;
        itemChange["needsWork"] = needWork;
        itemChange["tags"] = tags;
        if(itemChange["tags"] == undefined) {
            itemChange["tags"] = new Array();
        }
        if(itemChange["needsWork"] == false) {
            itemChange["needsWork"] = "false";
        } else {
            itemChange["needsWork"] = "true";
        }
        let fullTagsList = this.state.allTags;
        let tagsToKeep = itemChange["tags"];
        let newList = fullTagsList.filter(keeptag => {
            return tagsToKeep.includes(keeptag.tag);
        })

        console.log(this.state.specificQuestion);
        console.log(this.state.selectedTags);

        this.setState({selectedTags : newList, specificQuestion :itemChange, questionLoaded: true,
            isCreate: false, updateModal: true})
    }

    async deleteQuestion(id) {
        console.log("Deleting Question...")

        const deleteQuestionResponse = await fetch(`${baseUrl}/users/${this.state.username}/questions/${id}`, {
            method: 'DELETE'
        });

        const content = await deleteQuestionResponse.json();
        console.log(content);
        window.location.reload();
    }





    render() {
        const username = this.state.username;
        const allQuestions = this.state.allQuestions;

        let questionRows = allQuestions.map((ques) => {
            let questionTags = ques.tags
                .map((item, index) => {
                    if (index === ques.tags.length - 1) {
                        return `${item}`;
                    } else {
                        return `${item}, `;
                    }
                })
                .join("");
            return (
                <tr className={"questionRows"} key={ques.questionId}>
                <td>{ques.question}</td>
                <td>{ques.answer}</td>
                <td>{ques.needsWork.toString()}</td>
                <td>{questionTags}</td>
                <td><Button className={"viewButton"} size={"sm"} onClick={(event) =>
                    this.openUpdateQuestionModal(event, ques.questionId, ques.question, ques.answer,
                        ques.needsWork, ques.tags)}>View Question</Button></td>
                    <td><Button className={"deleteButton"} size={"sm"} color={"danger"}
                                onClick={() => this.deleteQuestion(ques.questionId)}>Delete Question</Button></td>
                </tr>
                
            )    
            
        })

        return (
            <div className={"interviewPrep"}>
                <QuestionNavbar />
                <div className={"jobsCreate"}>
                    <div className={"d-flex flex-row justify-content-between"}>
                        <div className={"createApp"}>
                            <Button className={"createButton but"} size={"lg"}
                                    onClick={(event)=> this.clickOnCreateQuestion(event)}>Create Question</Button>
                        </div>
                        <div className={"d-flex flex-row"}>
                            <Button className={"createButton but"} size={"sm"} onClick={() => this.viewAllQuestions()}>View all questions</Button>
                            <Button className={"createButton but"} size={"sm"} onClick={() => this.viewQuestionsNeedWork()}>View Only Needs Work </Button>
                        </div>
                    </div>
                    <div className={"jobApplicationsTable"}>
                        <Table className={"displayTable px-4"}>
                            <thead>
                            <tr>
                                <th width={"25%"}>Question</th>
                                <th width={"30%"}>Answer</th>
                                <th width={"10%"}>Needs Work</th>
                                <th width={"15%"}>Tags</th>
                                <th >Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            {questionRows}
                            </tbody>

                        </Table>
                    </div>
                </div>
                {CreateQuestionModal(this.state.createModal, ()=>this.setState({createModal : false}),
                this.state.specificQuestion.question, this.state.specificQuestion.answer, "true",
                this.state.specificQuestion.tags, this.state.allTags)}
                {UpdateQuestionModal(this.state.updateModal, ()=> this.setState({updateModal: false}),
                this.state.specificQuestion.questionId, this.state.specificQuestion.question, this.state.specificQuestion.answer,
                this.state.specificQuestion.needsWork, this.state.specificQuestion.tags, this.state.allTags, this.state.selectedTags)}
            </div>
        )
    }


}

export default AllQuestions;