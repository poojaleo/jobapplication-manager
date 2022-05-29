import React from "react";
import jobsmarterLogo from "../Home/jobsmarter_logo.png";
import {GoToSignUpPage} from "../login/GoToSignUpPage";
import "./UserNavbar.css";

class UserNavbar extends React.Component {
    render() {
        return (
            <div className={"user"}>
                <div className={"d-flex flex-row justify-content-end"}>

                    <div className={"mt-2"}>
                        <GoToSignUpPage />
                    </div>
                    <a href="/home">
                        <div className={"user logo"}>
                            <img src={jobsmarterLogo} alt={"company logo"} width={"50%"}/>
                        </div>
                    </a>
                </div>
            </div>
        );
    }
}

export default UserNavbar;