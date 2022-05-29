import React from "react";
import {Button, Form, FormGroup, Modal} from "react-bootstrap";
import {FormText, Input, Label} from "reactstrap";
import "react-datepicker/dist/react-datepicker.css";
import moment from "moment";
import AuthService from "../services/AuthService";
import {Multiselect} from "multiselect-react-dropdown";

const createQuestionModal = (show, handleClose, question, answer, needsWork, tags, allTags) => {
    const baseUrl = "https://x9zyk5z39b.execute-api.us-west-2.amazonaws.com/jobtracker";
    let message = "";
    const username = AuthService.getCurrentUsername();

    const createQuestion = async(event) => {
        event.preventDefault();
        console.log("Creating Question...")

        const questionToCreate = {
            "question": question,
            "answer": answer,
            "needsWork": needsWork,
            "tags": tags
        }

        const response = await fetch(`${baseUrl}/users/${username}/questions/`, {
            method: 'POST',
            headers : {
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify(questionToCreate)
        });
        const content = await response.json();
        console.log(content);
        handleClose();
        window.location.reload();
    }

    const handleTagChangeAdd = (selectedList, selectedItem) => {
        tags.push(selectedItem["tag"]);
    }

    const handleTagChangeRemove = (selectedList, removedItem) => {
        let listAfterRemove = tags.filter(element=> {
            return element != removedItem.tag;
        });
       tags = listAfterRemove;
    }

    return (
        <div>

            <Modal show={show} onHide={handleClose}>
                <Form onSubmit={createQuestion}>
                    <Modal.Header closeButton>
                        <Modal.Title>Create Question</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"question"}>Question</Label>
                            <Input type={"textarea"} rows={2} column={50} name={"question"} id={"question"}
                                   defaultValue={question} required={true}
                                   onChange={event => question=event.target.value}/>
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"answer"}>Answer</Label>
                            <Input type={"textarea"} name={"answer"} id={"answer"} defaultValue={answer}
                                   onChange={event => answer=event.target.value}/>
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"tags"}>Tags (select at least one)</Label>
                            <Multiselect options={allTags} showCheckbox={true} displayValue="tag" onSelect={handleTagChangeAdd}
                                         onRemove={handleTagChangeRemove} />
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"needsWork"}>Needs Work</Label>
                            <Input type={"select"} name={"needsWork"} value={question.needsWork} required={true}
                                   onChange={event => needsWork=event.target.value}>
                                <option value={"false"}>False</option>
                                <option value={"true"}>True</option>
                            </Input>
                        </FormGroup>
                        <FormText>{message}</FormText>

                        <div className={"d-flex justify-content-end"}>
                            <Button size={"lg"} type={"submit"} className={"createButton but"}>Create Question</Button>

                        </div>

                    </Modal.Body>

                </Form>

            </Modal>
        </div>
    )
}

export default createQuestionModal;