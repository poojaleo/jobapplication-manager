import React, {useState} from "react";
import UserNavbar from "../NavBar/UserNavbar";
import {Form, FormGroup, Label, Input, FormText} from "reactstrap";
import {useNavigate} from "react-router-dom";
import {Container} from "reactstrap";
import "./Login.css";
import jobhomepage from "../Home/jobhomepage.png";
import {GoToSignUpPage} from "./GoToSignUpPage";
import AuthService from "../services/AuthService";

const baseUrl = "https://x9zyk5z39b.execute-api.us-west-2.amazonaws.com/jobtracker";

const Login = (props) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [validPassword, setValidPassword] = useState(false);
    const [consoleMessage, setConsoleMessage] = useState('');
    const navigate = useNavigate();

    async function handleFormSubmit(event) {
        event.preventDefault();

        console.log("Signing in....")
        setConsoleMessage("Signing in....")
        const userResponse = await fetch(`${baseUrl}/users/${username}?password=${password}`);
        const userBody = await userResponse.json();

        console.log(userBody);

        if (userBody === undefined) {
            setConsoleMessage("Looks like there is an issue with your username or password. " +
                "Please enter valid credentials");
            alert("Invalid Username or Password. Please try again.")
        } else {
            console.log(userBody.user);
            AuthService.setUserSession(username);
            props.authenticate();
            navigate('/jobapplications');
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
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$/;

        if(passwordRegex.test(event.target.value)) {
            setValidPassword(true);
        } else {
            setValidPassword(false);
        }
    }

    const setPasswordValue = (event) => {
        setPassword(event.target.value);
    }

    return (
        <Container className={"pt-3"}>
            <UserNavbar />
            <div className={"d-flex flex-row"}>
                <div>
                    <h3 className={"greeting"}>WELCOME BACK!</h3>

                    <Form className="form" onSubmit={handleFormSubmit}>
                        <FormGroup>
                            <Label for="exampleUsername">Username</Label>
                            <Input className={"login-input"} type="text" name="username" required={true}
                                   id="exampleUsername" placeholder="Username" onChange={handleUsernameChange}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="examplePassword">Password</Label>
                            <Input className={"login-input"} type="password" name="password" id="examplePassword"
                                   required={true} placeholder="********"
                                   valid={validPassword}
                                   onChange={handlePasswordChange}/>
                            <FormText>Your password needs to be minimum 8 characters, 1 uppercase, 1 lowercase and 1 special character.</FormText>
                        </FormGroup>
                        <button className={"signInClick mx-2 mt-4"} type={"submit"}>Sign In</button>

                    </Form>
                    <div>
                        {consoleMessage}
                    </div>
                    <div className={"underscore"}>
                        <h5>Don't have an account.</h5>
                        <GoToSignUpPage />
                    </div>
                </div>

                <div className={"homeImage"}>
                    <img src={jobhomepage} alt={"home page icon"}/>
                </div>
            </div>


        </Container>
    )

}

export default Login;