import React from "react";
import {Button, Form, FormGroup, Modal} from "react-bootstrap";
import {FormText, Input, Label} from "reactstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import moment from "moment";
import AuthService from "../services/AuthService";
import questionsButton from "../login/QuestionsButton";
import {Multiselect} from "multiselect-react-dropdown";

const UpdateJobApplicationModal = (show, handleClose, applicationId, jobTitle, company, location, status, nextReminder,
                                   jobUrlLink, notes, questionsList, allQuestions, selectedQuestions) => {
    const baseUrl = "https://x9zyk5z39b.execute-api.us-west-2.amazonaws.com/jobtracker";
    let message = "";

    const updateJobApplication = async (event) => {

        event.preventDefault();
        console.log("Updating Application....")
        const username = AuthService.getCurrentUsername();
        message = "Updating application...";

        const applicationToUpdate = {
            "jobTitle": jobTitle,
            "company": jobUrlLink,
            "location": location,
            "status": status,
            "nextReminder": nextReminder,
            "jobUrlLink": jobUrlLink,
            "notes": notes,
            "questionsList": questionsList
        }

        const response = await fetch(`${baseUrl}/users/${username}/jobapplications/${applicationId}`, {
            method: 'PUT',
            headers : {
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify(applicationToUpdate)
        });

        const content = await response.json();
        console.log(content);
        handleClose();
        window.location.reload();
    }

    const handleQuestionChangeAdded = (selectedList, selectedItem) => {
        questionsList.push(selectedItem["questionId"]);
    }

    const handleQuestionChangeRemove = (selectedList, removedItem) => {
        let listAfterRemove = questionsList.filter(element => {
            return element != removedItem.questionId;
        });
        questionsList = listAfterRemove;
    }

    return (
        <div>

            <Modal show={show} onHide={handleClose}>
                <Form onSubmit={updateJobApplication}>
                    <Modal.Header closeButton>
                        <Modal.Title>Job Application - {jobTitle}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"applicationId"}>Application Id</Label>
                            <Input type={"text"} name={"applicationId"} id={"applicationId"} value={applicationId}
                                   readOnly={true}/>
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"jobTitle"}>Job Title</Label>
                            <Input type={"text"} name={"jobTitle"} id={"jobTitle"} defaultValue={jobTitle} required={true}
                                   onChange={event => jobTitle=event.target.value}/>
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"company"}>Company</Label>
                            <Input type={"text"} name={"company"} id={"company"} defaultValue={company} required={true}
                                   onChange={event => company=event.target.value}/>
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"location"}>Location</Label>
                            <Input type={"text"} name={"location"} id={"location"} defaultValue={location} required={true}
                                   onChange={event => location=event.target.value} />
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"status"}>Status</Label>
                            <Input type={"select"} name={"status"} id={"status"} defaultValue={status} required={true}
                                   onChange={event => status=event.target.value} >
                                <option>INTERESTED</option>
                                <option>APPLIED</option>
                                <option>CONTACTED</option>
                                <option>INTERVIEW_SCHEDULED</option>
                                <option>OFFER</option>
                                <option>NOT_MOVING_FORWARD</option>
                            </Input>
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"cost"}>Reminder</Label>
                            <Input type={"date"} name={"nextReminder"} id={"nextReminder"}
                                   defaultValue={nextReminder} onChange={event => nextReminder=event.target.value} />
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"jobUrlLink"}>Job URL</Label>
                            <Input type={"text"} name={"jobUrlLink"} id={"jobUrlLink"} defaultValue={jobUrlLink}
                                   onChange={event => jobUrlLink=event.target.value}/>
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"notes"}>Notes</Label>
                            <Input type={"text"} name={"notes"} id={"notes"} defaultValue={notes}
                                   onChange={event => notes=event.target.value} />
                        </FormGroup>
                        <FormGroup>
                            <Label>Questions</Label>
                            <div className={"checkboxQuestions"}>
                                <Multiselect options={allQuestions} displayValue="question"
                                             selectedValues={selectedQuestions} showCheckbox={true} onSelect={handleQuestionChangeAdded}
                                             onRemove={handleQuestionChangeRemove}/>
                            </div>
                        </FormGroup>
                        <FormText>{message}</FormText>

                        <div className={"d-flex justify-content-end"}>
                            <Button size={"lg"} type={"submit"} className={"createButton but"}>Update Application</Button>

                        </div>

                    </Modal.Body>

                </Form>

            </Modal>
        </div>
    )
}

export default UpdateJobApplicationModal;