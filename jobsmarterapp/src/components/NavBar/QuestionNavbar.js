import React, {useEffect, useState} from "react";
import jobsmarterLogo from "../Home/jobsmarter_logo.png";
import "./JobApplicationNavbar.css";
import AuthService from "../services/AuthService";
import SignoutButton from "../login/SignoutButton";
import QuestionsButton from "../login/QuestionsButton";
import JobApplicationsButton from "../login/JobApplicationsButton";

const QuestionNavbar = () => {
    const [username, setUsername] = useState('');

    useEffect(() => {
        setUsername(AuthService.getCurrentUsername);
    }, []);

    return (
        <div className={"top-bar"}>
            <div className={"d-flex flex-row"}>
                <a href="/home">
                    <div className={"logo"}>
                        <img src={jobsmarterLogo} alt={"company logo"}/>
                    </div>
                </a>
                <div className={"mt-3"}>
                    <h4>Hi {username}</h4>
                </div>

            </div>
            <div className={"mt-3"}>
                <h3 className="fw-bold">Job Preparation Questions</h3>
            </div>
            <div className={"d-flex flex-column justify-content-end align-content-end"}>

                <div>
                    <SignoutButton />
                </div>
                <div className={"mt-3"}>
                    <JobApplicationsButton />
                </div>

            </div>

        </div>
    );
}

export default QuestionNavbar;