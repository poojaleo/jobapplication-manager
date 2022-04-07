import React from "react";
import UserNavbar from "../NavBar/UserNavbar";
import {Form, FormGroup, Label, Input} from "reactstrap";
import {useNavigate} from "react-router-dom";

import "./Login.css";
import jobhomepage from "../Home/jobhomepage.png";
import {GoToSignUpPage} from "./GoToSignUpPage";

let username = "";
let password = "";
// const baseUrl = "https://7ehv0ovn6e.execute-api.us-west-2.amazonaws.com/prod";

function SignInClick() {
    let navigate = useNavigate();
    const routeChange = () => {
        navigate('/jobapplications', {
            state:{username:username}
        });
    }

    async function handleFormSubmit(event) {
        event.preventDefault();
        const name = username;
        const pass = password;

        console.log("Signing in....")

        //users/harrypotter?password=Harry!12345
        const userResponse = await fetch(`/users/${name}?password=${pass}`);
        const userBody = await userResponse.json();

        console.log(userBody);

        if (userBody.user === undefined) {
            alert("Invalid Username or Password. Please try again.")
        } else {
            routeChange();
        }
    }
    return <button className={"signInClick"} onClick={handleFormSubmit}>Sign In</button>
};

function UpdateClick(name) {
    let navigate = useNavigate();
    const routeChange = (event) => {
        event.preventDefault();
        navigate('/signup', {
            state:{username:username}
        });
    }
    return <button className={"updateClick"} onClick={routeChange}>Forgot password?</button>
};

function handleFormChange(event) {
    const value = event.target.value;
    username = value;
};

function handlePasswordChange(event) {
    const value = event.target.value;
    password = value;
};

export function Login() {
    return (
        <div className={"loginPage"}>
            <UserNavbar />
            <h3 className={"greeting"}>WELCOME BACK!</h3>
            <div className={"underscore"}>
                <h5>Don't have an account.</h5>
                <GoToSignUpPage />
            </div>
            <Form className="form">
                <FormGroup>
                    <Label for="exampleUsername">Username</Label>
                    <Input className={"login-input"} type="text" name="username" id="exampleUsername" placeholder="Username" onChange={handleFormChange}/>
                </FormGroup>
                <FormGroup>
                    <Label for="examplePassword">Password</Label>
                    <Input className={"login-input"} type="password" name="password" id="examplePassword" placeholder="********" onChange={handlePasswordChange}/>
                </FormGroup>
                <SignInClick />
                <div className={"button"}>
                    <UpdateClick />
                </div>
            </Form>
            <div className={"homeImage"}>
                <img src={jobhomepage} alt={"home page icon"}/>
            </div>
        </div>
    )

}

export default Login;