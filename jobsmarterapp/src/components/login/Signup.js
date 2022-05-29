import React, {useState} from "react";

import {Container} from "react-bootstrap";
import {Form, FormGroup, Input, Label, FormFeedback, FormText, Button} from "reactstrap";
import {useNavigate} from "react-router-dom";
import jobhomepage from "../Home/jobhomepage.png";
import UserCreateNavbar from "../NavBar/UserCreateNavbar";
import "./Signup.css";
import {GoToLoginPage} from "./GoToLoginPage";

const Signup = () => {
    const [username, setUsername] = useState('');
    const [firstname, setFirstname] = useState('')
    const [lastname, setLastname] = useState('')
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [validPassword, setValidPassword] = useState(false);
    const [validEmail, setValidEmail] = useState(false);
    const [signupSuccessful, setSignup] = useState(false);
    const [consoleMessage, setConsoleMessage] = useState('');

    const baseUrl = "https://x9zyk5z39b.execute-api.us-west-2.amazonaws.com/jobtracker";

    /*function SignUpClick() {
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

            console.log("Creating User....")

            const userToCreate = {
                "username": name,
                "firstname": first,
                "lastname": last,
                "emailAddress": mail,
                "password": pass
            }

            const userResponse = await fetch(`${baseUrl}/users/`, {
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
    };*/

    async function handleFormSubmit(event) {
        event.preventDefault();
        setConsoleMessage("Registering User in progress");
        const requestBody = {
            "username": username,
            "firstname": firstname,
            "lastname": lastname,
            "emailAddress": email,
            "password": password
        }

        const userBody = await fetch(`${baseUrl}/users/`, {
            method: 'POST',
            headers : {
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify(requestBody)
        }).then(response => {
            if(response.ok) {
                setConsoleMessage("");
                setSignup(true);
                return response.json();
            }
        })
        console.log(userBody);
        if (userBody === undefined) {
            setSignup(false);
            setConsoleMessage("Username already exist.");
            alert("Username already exist.")
        }
    }

    function handleUsernameChange(event) {
        setUsername(event.target.value);
    }

    function handlePasswordChange(event) {
        validatePassword(event);
        setPasswordValue(event);
    }

    const validatePassword = (event) => {
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if(passwordRegex.test(event.target.value)) {
            setValidPassword(true);
        } else {
            setValidPassword(false);
        }
    }

    const setPasswordValue = (event) => {
        setPassword(event.target.value);
    }

    function handleFirstnameChange(event) {
        setFirstname(event.target.value);
    }

    function handleLastnameChange(event) {
        setLastname(event.target.value);
    }

    function handleEmailChange(event) {
        validateEmail(event);
        setEmailValue(event);
    }

    const validateEmail = (event) => {
        const emailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

        if(emailRegex.test(event.target.value)) {
            setValidEmail(true);
        } else {
            setValidEmail(false);
        }
    }

    const setEmailValue = (event) => {
        setEmail(event.target.value);
    }

    return (
        <Container className={"pt-3"}>
            <UserCreateNavbar />
            <div className={"d-flex flex-row justify-content-around"}>
                <div>
                    <h3 className={"greeting"}>Sign up for JobSmarter</h3>
                    <Form className="form" onSubmit={handleFormSubmit}>
                        <FormGroup>
                            <Label for="exampleUsername">Username*</Label>
                            <Input type="text" name="username" id="exampleUsername" required={true} placeholder="Username" onChange={handleUsernameChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="examplePassword">Password*</Label>
                            <Input type="password" name="password" required={true} id="examplePassword" placeholder="********"
                                   valid={validPassword}

                                   onChange={handlePasswordChange}/>
                            <FormFeedback valid>
                                Password meets the requirements.
                            </FormFeedback>
                            <FormText>Your password needs to be minimum 8 characters, 1 uppercase, 1 lowercase and 1 special character.</FormText>
                        </FormGroup>
                        <FormGroup>
                            <Label for="exampleFirstname">First Name*</Label>
                            <Input type="text" name="email" id="exampleFirstname" required={true} placeholder="First Name" onChange={handleFirstnameChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="exampleLastname">Last Name</Label>
                            <Input type="text" name="firstname" id="exampleLastname" placeholder="Last Name" onChange={handleLastnameChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="exampleEmail">Email*</Label>
                            <Input type="text" name="lastname" id="exampleEmail" required={true} placeholder="Email Address"
                                   valid={validEmail}

                                   onChange={handleEmailChange}/>
                        </FormGroup>
                        <button className={"button signInClick mx-2 mt-4"}>Signup</button>

                    </Form>
                    <div>
                        {consoleMessage}
                    </div>
                    <div>
                        {signupSuccessful ? (
                                <div className={"text-center"}>
                                    <h4>{username} successfully registered!!</h4>
                                    <h6>Go to the Login page to login</h6>
                                    <GoToLoginPage />

                                </div>
                            ) :
                            (
                                <h3></h3>
                            )}

                    </div>
                </div>
                <div className={"homeImage"}>
                    <img src={jobhomepage} alt={"home page icon"}/>
                </div>
            </div>


        </Container>
    )
}

export default Signup;