import React from "react";
import {Button, Form, FormGroup, Modal} from "react-bootstrap";
import {FormText, Input, Label} from "reactstrap";
import "react-datepicker/dist/react-datepicker.css";
import moment from "moment";
import AuthService from "../services/AuthService";

const CreateJobApplicationModal = (show, handleClose, jobTitle, company, location, status, nextReminder, jobUrlLink, notes) => {
    const baseUrl = "https://x9zyk5z39b.execute-api.us-west-2.amazonaws.com/jobtracker";
    let message = "";
    const selected = Date.parse(moment(nextReminder, 'MM-dd-yyyy').toISOString());

    const createJobApplication = async (event) => {

        event.preventDefault();
        console.log("Creating Application....")
        const username = AuthService.getCurrentUsername();
        message = "Creating new application...";
        const applicationToCreate = {
            "jobTitle": jobTitle,
            "company": company,
            "location": location,
            "status": status,
            "nextReminder": nextReminder,
            "jobUrlLink": jobUrlLink,
            "notes": notes
        }

        const response = await fetch(`${baseUrl}/users/${username}/jobapplications/`, {
            method: 'POST',
            headers : {
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify(applicationToCreate)
        });

        const content = await response.json();
        console.log(content);
        handleClose();
        window.location.reload(true);
    }

    return (
        <div>

            <Modal show={show} onHide={handleClose}>
                <Form onSubmit={createJobApplication}>
                    <Modal.Header closeButton>
                        <Modal.Title>Create Application</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
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
                            <Input type={"select"} name={"status"} id={"status"} required={true}
                                   onChange={event => status=event.target.value} >
                                <option disabled={true} selected>-- select an option --</option>
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
                            {/*<DatePicker dateFormat="MM-dd-yyyy" selected = {selected}
                                        onChange={date => nextReminder = date} />*/}
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
                        <FormText>{message}</FormText>

                        <div className={"d-flex justify-content-end"}>
                            <Button size={"lg"} type={"submit"} className={"createButton but"}>Create Application</Button>

                        </div>

                    </Modal.Body>

                </Form>

            </Modal>
        </div>
    )
}

export default CreateJobApplicationModal;