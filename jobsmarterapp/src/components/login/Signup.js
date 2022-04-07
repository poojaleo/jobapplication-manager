import React from "react";
import App from "../../App";
import {Form, FormGroup, Input, Label} from "reactstrap";
import {useNavigate} from "react-router-dom";
import jobhomepage from "../Home/jobhomepage.png";
import UserCreateNavbar from "../NavBar/UserCreateNavbar";

let username = "";
let email = "";
let firstname = "";
let lastname = "";
let password = "";
// const baseUrl = "https://7ehv0ovn6e.execute-api.us-west-2.amazonaws.com/prod";

function SignUpClick() {
    let navigate = useNavigate();
    const routeChange = () => {
        navigate('/login');
    }

    async function handleFormSubmit(event) {
        event.preventDefault();
        const name = username;
        const pass = password;
        const first = firstname;
        const last = lastname;
        const mail = email;

        //users/
        console.log("Creating User....")

        const userToCreate = {
            "username": name,
            "firstname": first,
            "lastname": last,
            "emailAddress": mail,
            "password": pass
        }

        const userResponse = await fetch(`/users/`, {
            method: 'POST',
            headers : {
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify(userToCreate)
        });
        const userBody = await userResponse.json();

        console.log(userBody);

        if (userBody.user === undefined) {
            alert("Invalid fields. Please try again.")
        } else {
            routeChange();
        }
    }
    return <button className={"signInClick"} onClick={handleFormSubmit}>Create Account</button>
};

function UpdateUserClick() {
    let navigate = useNavigate();
    const routeChange = () => {
        navigate('/login');
    }

    async function handleFormSubmit(event) {
        event.preventDefault();
        const name = username;
        const pass = password;
        const first = firstname;
        const last = lastname;

        console.log("Updating user details....")

        const userToCreate = {
            "username": name,
            "firstname": first,
            "lastname": last,
            "password": pass
        }

        //users/{username}
        const updateResponse = await fetch(`/users/${name}`, {
            method: 'PUT',
            headers : {
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify(userToCreate)
        });
        const userBody = await updateResponse.json();

        console.log(userBody);

        if (userBody.user === undefined) {
            alert("Invalid fields. Please try again.")
        } else {
            routeChange();
        }
    }
    return <button className={"updateClick"} onClick={handleFormSubmit}>Update Account</button>
};

function handleUsernameChange(event) {
    const value = event.target.value;
    username = value;
};

function handlePasswordChange(event) {
    const value = event.target.value;
    password = value;
};

function handleFirstnameChange(event) {
    const value = event.target.value;
    firstname = value;
};

function handleLastnameChange(event) {
    const value = event.target.value;
    lastname = value;
};

function handleEmailChange(event) {
    const value = event.target.value;
    email = value;
};

export function Signup() {
    return (
        <div className={"loginPage"}>
            <UserCreateNavbar />
            <h3 className={"greeting"}>Sign up for JobSmarter</h3>
            <Form className="form">
                <FormGroup>
                    <Label for="exampleUsername">Username*</Label>
                    <Input type="text" name="username" id="exampleUsername" placeholder="Username" onChange={handleUsernameChange}/>
                </FormGroup>
                <FormGroup>
                    <Label for="examplePassword">Password* (min. 8 characters, 1 uppercase, 1 lowercase and 1 special character)</Label>
                    <Input type="password" name="password" id="examplePassword" placeholder="********" onChange={handlePasswordChange}/>
                </FormGroup>
                <FormGroup>
                    <Label for="exampleFirstname">First Name*</Label>
                    <Input type="text" name="email" id="exampleFirstname" placeholder="First Name" onChange={handleFirstnameChange}/>
                </FormGroup>
                <FormGroup>
                    <Label for="exampleLastname">Last Name</Label>
                    <Input type="text" name="firstname" id="exampleLastname" placeholder="Last Name" onChange={handleLastnameChange}/>
                </FormGroup>
                <FormGroup>
                    <Label for="exampleEmail">Email*</Label>
                    <Input type="text" name="lastname" id="exampleEmail" placeholder="Email Address" onChange={handleEmailChange}/>
                </FormGroup>

                <div className={"button"}>
                    <SignUpClick />
                </div>
                <UpdateUserClick />
            </Form>
            <div className={"homeImage"}>
                <img src={jobhomepage} alt={"home page icon"}/>
            </div>
        </div>
    )
}

export default Signup;