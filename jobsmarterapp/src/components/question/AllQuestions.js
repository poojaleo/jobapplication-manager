import React from "react";
import {Button, Table, Input} from "reactstrap";
import "./AllQuestions.css";
import {Multiselect} from "multiselect-react-dropdown";
import QuestionNavbar from "../NavBar/QuestionNavbar";
import AuthService from "../services/AuthService";
import "../jobapplication/AllJobApplications.css"
import CreateQuestionModal from "./CreateQuestionModal";

const baseUrl = "https://x9zyk5z39b.execute-api.us-west-2.amazonaws.com/jobtracker";


class AllQuestions extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            username: "",
            questionLoaded: false,
            isCreate: true,
            createModal: false,
            allQuestions: [],
            selectedTags: [],
            allTags:[{"tag":"technical"}, {"tag": "behavioral"}, {"tag":"coding"}, {"tag":"frontend"}, {"tag":"backend"}, {"tag":"oop"}, {"tag":"ml"}, {"tag":"datascience"}, {"tag":"theory"}, {"tag":"application"}],        
            specificQuestion: {"username":this.props.username,"questionId":"","question":"",
                "answer":"","needsWork":"true", "tags": []},
            emptyQuestion: {"username":this.props.username,"questionId":"","question":"",
            "answer":"","needsWork":"true", "tags": []},
        }

        this.viewQuestion = this.viewQuestion.bind(this);
        this.viewQuestionsNeedWork = this.viewQuestionsNeedWork.bind(this);
        this.clickOnCreateQuestion = this.clickOnCreateQuestion.bind(this);
        this.onQuestionCreateOrUpdate = this.onQuestionCreateOrUpdate.bind(this);
        this.handleTagChangeAdd = this.handleTagChangeAdd.bind(this)
        this.handleTagChangeRemove = this.handleTagChangeRemove.bind(this);
        this.createQuestion = this.createQuestion.bind(this);
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

    async viewQuestion(id){
        const questionResponse = await fetch(`${baseUrl}/users/${this.state.username}/questions/${id}`);
        const questionBody = await questionResponse.json();
        this.setState({specificQuestion : questionBody.question, questionLoaded: true, isCreate: false})
        console.log(questionBody);
        let itemChange = {...this.state.specificQuestion};
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
        this.setState({selectedTags : newList});
        this.setState({specificQuestion : itemChange});
    }


    async createQuestion(event) {
        event.preventDefault();
        console.log("Creating Question...")

        const questionToCreate = {
            "question": this.state.specificQuestion.question,
            "answer": this.state.specificQuestion.answer,
            "needsWork": this.state.specificQuestion.needsWork,
            "tags": this.state.specificQuestion.tags
        }

        const response = await fetch(`${baseUrl}/users/${this.state.username}/questions/`, {
            method: 'POST',
            headers : {
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify(questionToCreate)
        });
        const content = await response.json();
        window.location.reload(false, {
            state:{username:this.state.username}
        }
        );

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

    async updateQuestion(event) {
        event.preventDefault();
        console.log("Updating Question...")

        const questionToUpdate = {
            "question": this.state.specificQuestion.jobTitle,
            "answer": this.state.specificQuestion.answer,
            "needsWork": this.state.specificQuestion.needsWork,
            "tags": this.state.specificQuestion.tags
        }
        console.log(questionToUpdate);
        const response = await fetch(`${baseUrl}/users/${this.state.username}/questions/${this.state.specificQuestion.questionId}`, {
            method: 'PUT',
            headers: {
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify(questionToUpdate)
        });

        const content = await response.json();
       // console.log(content);
        window.location.reload(false, {
            state:{username:this.state.username}
        });

    }



    render() {
        const username = this.state.username;
        const allQuestions = this.state.allQuestions;

        let questionRows = allQuestions.map((question) => {
            let questionTags = question.tags
                .map((item, index) => {
                    if (index === question.tags.length - 1) {
                        return `${item}`;
                    } else {
                        return `${item}, `;
                    }
                })
                .join("");
            return (
                <tr className={"questionRows"} key={question.questionId}>
                <td>{question.question}</td>
                <td>{question.answer}</td>
                <td>{question.needsWork.toString()}</td>
                <td>{questionTags}</td>
                <td><Button className={"viewButton"} size={"sm"} onClick={() => this.viewQuestion(question.questionId)}>View Question</Button></td>
                    <td><Button className={"deleteButton"} size={"sm"} color={"danger"}
                                onClick={() => this.deleteQuestion(question.questionId)}>Delete Question</Button></td>
                </tr>
                
            )    
            
        })

        let specificQuestion = (question) => {
            if(!this.state.isCreate) {
                return (
                    <div className={"questForm"}>
                        <div className={"questionForm"}>
                        <text>QuestionId</text>
                        <input type={"text"} value={question.questionId}/>
                        <text>Question</text>
                        <textarea rows={2} columns={50} name={"question"} defaultValue={question.question} required={true}
                                onChange={this.onQuestionCreateOrUpdate} />
                        <text>Answer</text>
                        <textarea rows={4} columns={50} name={"answer"} defaultValue={question.answer} required={false}
                                onChange={this.onQuestionCreateOrUpdate} />
                        </div>
                        
                        <div className={"questionForm"}>
                        <text>Tags (select at least one) </text>
                        <div className={"tags"}>
                        <Multiselect options={this.state.allTags} showCheckbox={true} displayValue="tag" onSelect={this.handleTagChangeAdd} 
                            onRemove={this.handleTagChangeRemove} selectedValues={this.state.selectedTags}/>
                    </div>   
                        <text>Needs Work</text>
                        <Input type={"select"} name={"needsWork"} value={question.needsWork} required={true} onChange={this.onQuestionCreateOrUpdate}>
                            <option value={"false"}>False</option>
                            <option value={"true"}>True</option>
                        </Input>
                        
                        </div>

                        <div className={"questionCreate"}>
                        <Button  className={"createButton but"} size={"sm"} onClick={(event) => this.updateQuestion(event)}>Update</Button>
                        <br />
                        <Button  className={"createButton but"} size={"sm"}onClick={(event) => this.deleteQuestion(event)}>Delete</Button>
                        </div>

                    </div>
                )

            }

        }

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
                this.state.specificQuestion.question, this.state.specificQuestion.answer, "false",
                this.state.specificQuestion.tags, this.state.allTags)}

            </div>
        )
    }


}

export default AllQuestions;