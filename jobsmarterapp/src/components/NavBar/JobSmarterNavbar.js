import React from "react";
import jobsmarterLogo from "../Home/jobsmarter_logo.png";
import {GoToSignUpPage} from "../login/GoToSignUpPage";
import {GoToLoginPage} from "../login/GoToLoginPage";
import "./JobSmarterNavbar.css";

class JobSmarterNavbar extends React.Component {
    render() {
        return (
            <div className={"d-flex flex-row justify-content-between"}>
                <a href="/home">
                    <div className={"logo"}>
                        <img src={jobsmarterLogo} alt={"company logo"}/>
                    </div>
                </a>
                <h1>JobSmarter</h1>
                <div className={"d-flex flex-row"}>
                    <div className={"mt-3"}>
                        <GoToSignUpPage />
                    </div>
                   <div className={"mt-3"}>
                       <GoToLoginPage />
                   </div>

                </div>
            </div>
        );
    }
}

export default JobSmarterNavbar;